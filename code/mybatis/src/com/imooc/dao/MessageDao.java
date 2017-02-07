package com.imooc.dao;

import com.imooc.bean.Message;

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


    /**
     * Description:根据查询条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command,String description){
        List<Message> messages = null;
        try {
            //连接数据库
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "myoracle", "myoracle");
            //String sql = "select id,command,description,content from message";
            //拼接sql
            StringBuilder sql = new StringBuilder("select id,command,description,content from message where 1=1");
            List<String> paramList = new ArrayList<String>();

            if (command != null && !"".equals(command.trim())) {
                sql.append(" and command = ?");
                paramList.add(command.trim());
            }
            if (description != null && !"".equals(description.trim())) {
                sql.append(" and description like '%' ? '%'");
                paramList.add(description.trim());
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            //设置参数
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setString(i + 1, paramList.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            messages = new ArrayList<Message>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getString("id"));
                message.setCommand(resultSet.getString("command"));
                message.setDescription(resultSet.getString("description"));
                message.setContent(resultSet.getString("content"));
                messages.add(message);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return messages;
    }

}
