package com.imooc.service;

import com.imooc.bean.Message;
import com.imooc.dao.MessageDao;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description:
 *
 * @author caojx Message业务相关功能
 * Created by caojx on 2017年02月26 下午6:56:56
 */
public class MessageService {

    private Logger logger = Logger.getLogger(MessageService.class);
    /**
     * Description:根据条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command, String description) throws Exception{
        MessageDao messageDao = new MessageDao();
        return messageDao.queryMessageList(command,description);
    }

    /**
     * Description:实现单条数据删除
     * @param id 消息id
     */
    public void deleteOne(Integer id) throws Exception{
        MessageDao messageDao = new MessageDao();
        messageDao.deleteOne(id);
    }
}
