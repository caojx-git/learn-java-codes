package caojx.learn.comment;


import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @description: 扩展mybatis_generator，自定义注释生成规则
 * 参考 https://blog.csdn.net/u011781521/article/details/78161201?locationNum=8&fps=1
 * @author caojx
 * @date 2018年7月6日
 * @version 1.0
 */
public class MyCommentGenerator implements CommentGenerator{

    /**
     * properties配置文件
     */
    private Properties properties;

    /**
     * properties配置文件
     */
    private Properties systemPro;

    /**
     * 父类时间
     */
    private boolean suppressDate;

    /**
     * 父类所有注释
     */
    private boolean suppressAllComments;

    /**
     * 当前时间
     */
    private String currentDateStr;

    /**
     * 作者
     */
    private String author;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    /**
     * 从该配置中的任何属性添加此实例的属性CommentGenerator配置。
     * 这个方法将在任何其他方法之前被调用。
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        author = this.properties.getProperty("author");
        if (!StringUtility.stringHasValue(author)) {
            author = systemPro.getProperty("user.name");
        }
    }

    /**
     * 自定义的javadoc标签
     * @param javaElement
     * @param markAsDoNotDelete
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * 为枚举添加注释
     * @param innerEnum
     * @param introspectedTable
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    /**
     * Java属性注释
     * @param field
     * @param introspectedTable
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * Java属性字段添加注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
        //添加注解
        if (field.isTransient()) {
            //@Column
            field.addAnnotation("@Transient");
        }
        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }
        String column = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column = introspectedColumn.getContext().getBeginningDelimiter()
                    + column
                    + introspectedColumn.getContext().getEndingDelimiter();
        }
   /*     if (!column.equals(introspectedColumn.getJavaProperty())) {
            //@Column
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
        } else if (StringUtility.stringHasValue(beginningDelimiter) || StringUtility.stringHasValue(endingDelimiter)) {
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
        } else if (forceAnnotation) {
            field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
        }*/
        if (introspectedColumn.isIdentity()) {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        } else if (introspectedColumn.isSequenceColumn()) {
            //在 Oracle 中，如果需要是 SEQ_TABLENAME，那么可以配置为 select SEQ_{1} from dual
            String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
        }
    }

    /**
     * 普通方法的注释，这里主要是XXXMapper.java里面的接口方法的注释
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    /**
     * 给Java文件加注释，这个注释是在文件的顶部，也就是package上面。
     * @param compilationUnit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
//        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
//        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
//        compilationUnit.addFileCommentLine("/**");
//        compilationUnit.addFileCommentLine(" *");
//        compilationUnit.addFileCommentLine(" * @author "+author);
//        compilationUnit.addFileCommentLine(" * @description  ");
//        compilationUnit.addFileCommentLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
//        compilationUnit.addFileCommentLine(" * @date "+sdf2.format(new Date())+"");
//        compilationUnit.addFileCommentLine(" */");
    }


    /**
     * 为模型类添加注释
     * @param topLevelClass
     * @param introspectedTable
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addAnnotation("@Data");
        FullyQualifiedJavaType fullyQualifiedJavaType = new FullyQualifiedJavaType("lombok.Data");
        FullyQualifiedJavaType fullyQualifiedJavaType2 = new FullyQualifiedJavaType("javax.persistence.*");
        topLevelClass.addImportedType(fullyQualifiedJavaType);
        topLevelClass.addImportedType(fullyQualifiedJavaType2);

        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author "+author);
        topLevelClass.addJavaDocLine(" * @description  ");
        topLevelClass.addJavaDocLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
        topLevelClass.addJavaDocLine(" * @date "+sdf2.format(new Date())+"");
        topLevelClass.addJavaDocLine(" * @version 1.0");
        topLevelClass.addJavaDocLine(" */");

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" *");
        innerClass.addJavaDocLine(" * @author "+author);
        innerClass.addJavaDocLine(" * @description  ");
        innerClass.addJavaDocLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
        innerClass.addJavaDocLine(" * @date "+sdf2.format(new Date())+"");
        innerClass.addJavaDocLine(" * @version 1.0");
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine(" *");
        innerClass.addJavaDocLine(" * @author "+author);
        innerClass.addJavaDocLine(" * @description  ");
        innerClass.addJavaDocLine(" * @Copyright (c) "+ sdf1.format(new Date())+", Lianjia Group All Rights Reserved.");
        innerClass.addJavaDocLine(" * @date "+sdf2.format(new Date())+"");
        innerClass.addJavaDocLine(" * @version 1.0");
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 为调用此方法作为根元素的第一个子节点添加注释。
     */
    @Override
    public void addRootComment(XmlElement arg0) {

    }

    /**
     *  给getter方法加注释
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     *  给setter方法加注释
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString().replace("\n", " "));
        method.addJavaDocLine(" */");
    }

    /**
     * Mybatis的Mapper.xml文件里面的注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * 获取当前时间
     * @return
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }
}
