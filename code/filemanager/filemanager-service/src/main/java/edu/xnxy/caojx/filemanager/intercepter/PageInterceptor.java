package edu.xnxy.caojx.filemanager.intercepter;

import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * Description: mybatis分页拦截插件
 *
 * @author caojx
 *         Created by caojx on 2017年04月23 上午10:03:03
 */
//type指向需要拦截的接口，method指向需要拦截的方法，args指向方法中的参数
//StatementHandler是一个接口，实现类是BaseStatementHanlder，实现了其中的prepare方法，实现方法中instantiateStatement(connection)
//返回Statement
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    private static final Logger log = org.apache.log4j.Logger.getLogger(PageInterceptor.class);

    private static String defaultDialect = "oracle";   //默认数据库类型
    private String dialect;                          //数据库类型

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            //代理业务员获取代理对象的携带的信息（需要买票的人哪里获取信息）
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            //使用metaObject中获取statementHandler属性的值
            MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
            //BaseStatementHandler-->mappedStatement(delegate)
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");

            //获取数据库类型
            dialect = configuration.getVariables().getProperty("dialect");
            if (null == dialect || "".equals(dialect)) {
                dialect = defaultDialect;
            }

            //获取boundSql对象，用户获取原始sql和分页参数
            BoundSql boundSql = statementHandler.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            PageParameter page = null;
            if (parameterObject != null) {
                //获取分页参数
                page = (PageParameter) metaObject.getValue("delegate.boundSql.parameterObject.page");
                if (page != null) {
                    //获取原始sql
                    String sql = boundSql.getSql();
                    Connection connection = (Connection) invocation.getArgs()[0];
                    //设置PageParameter属性和改造成分页的sql
                    setPageParameter(page, connection, metaObject, sql);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return invocation.proceed();
    }


    /**
     * 2.定位客户  @Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
     * 如果拦截成功，会返回一个代理类（业务员），然后会执行intercept方法
     * 就好比拦截到买票的人，需不需要买票，不需要放行，return就是放行
     * 需要就代表一种协议Plugin.wrap(target, this);
     * target就好比拦截住的需要买票的人,this就是公司的业务员，返回后this就变成可以代理target去买票的业务员
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 设置PageParameter的相关属性
     */
    public void setPageParameter(PageParameter page, Connection connection, MetaObject metaObject, String sql) {
        ResultSet rs = null;
        PreparedStatement countStatement = null;
        try {
            //获取数据总条数
            String countSql = "select count(1) from (" + sql + ")";
            countStatement = connection.prepareStatement(countSql);
            //获取原始的sql参数信息
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            //设置参数值
            parameterHandler.setParameters(countStatement);
            //执行获取总条数
            rs = countStatement.executeQuery();
            if (rs.next()) {
                page.setTotalCount(rs.getInt(1));
            }
            //改造成带分页查询的sql
            String pageSql = buildPageSql(sql, page);
            //替换员来的sql
            //第一参数：找到源码中的sql
            //第二个参数：改造后的sql
            metaObject.setValue("delegate.boundSql.sql", pageSql);        //买票
        } catch (Exception e) {
            log.error("分页拦截器出现异常", e);
        } finally {
            try {
                rs.close();
                countStatement.close();
            } catch (SQLException e) {
                log.error("分页拦截器关闭资源出现异常", e);
            }
        }
    }

    /**
     * 改造成带分页查询的sql
     * sql原始sql(不带分页功能)
     * Page page分页实体类对象
     **/
    public String buildPageSql(String sql, PageParameter page) {
        StringBuilder builder = new StringBuilder();
        if (dialect.equals("oracle")) {
            builder = pageSqlForOracle(sql, page);
        }
        if (dialect.equals("mysql")) {
            pageSqlForMysql(sql, page);
        }
        return builder.toString();
    }

    public StringBuilder pageSqlForMysql(String sql, PageParameter page) {
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(" limit " + ((page.getCurrentPage() - 1) * page.getPageSize()) + "," + page.getPageSize());
        builder.append(" order by id");
        return builder;
    }

    /**
     * 实现oracle分页
     */
    public StringBuilder pageSqlForOracle(String sql, PageParameter page) {
        StringBuilder builder = new StringBuilder();
        builder.append("select * from ( select temp.*, rownum r from (");
        builder.append(sql);
        builder.append(") temp where rownum <= ").append(((page.getCurrentPage() - 1) * page.getPageSize()) + page.getPageSize());
        builder.append(") where r > ").append((page.getCurrentPage() - 1) * page.getPageSize());
        System.out.println(builder.toString());
        return builder;
    }
}
