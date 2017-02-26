package com.imooc.dao;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:和Message表相关的数据库操作
 * Created by caojx on 17-2-1.
 */
public class MessageDao {

    private Logger logger = Logger.getLogger(MessageDao.class);
    /**
     * 根据查询条件查询消息列表
     * @param command 指令名称
     * @param description 描述
     * */
    public List<Message> queryMessageList(String command,String description) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            //将参数封装到Message对象中
            Message message = new Message();
            message.setCommand(command);
            message.setDescription(description);
            /*
             * 通过sqlSession执行sql语句,执行那个映射文件中的sql由传入的内容决定一般是namespace+id
             * selectList，查询的时候是可以传入查询参数的，不过只能传入一个参数，所以需要将参数封装到某个对象中
             * 第一个参数格式：namespace.id(方法名)
             * 第二个额参数：sql需要传入的参数
             */
            messages = sqlSession.selectList("com.imooc.bean.Message.queryMessageList",message);
        }catch (Exception e){
            logger.error("查询Message出错",e);
            throw new Exception("查询Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
        return messages;
    }

    /**
     * Description：通过messageId对消息进行当条删除
     * @param id 消息id
     */
    public void deleteOne(Integer id) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            sqlSession.delete("com.imooc.bean.Message.deleteOne",id);
            sqlSession.commit();
        }catch (Exception e){
            logger.error("删除单条Message出错",e);
            throw new Exception("删除单条Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }

    /**
     * 批量数据删除
     * @param messageList 消息集合list
     * @throws Exception
     */
    public void deleteBatch(List<Message> messageList) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            sqlSession.delete("com.imooc.bean.Message.deleteBatch",messageList);
            sqlSession.commit();
        }catch (Exception e){
            logger.error("批量删除Message出错",e);
            throw new Exception("批量删除Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }
}
