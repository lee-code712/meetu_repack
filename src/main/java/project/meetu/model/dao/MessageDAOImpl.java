package project.meetu.model.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.MessageMapper;
import project.meetu.model.dto.Message;

@Component
public class MessageDAOImpl implements MessageDAO {
	@Autowired
    private MessageMapper messageMapper;

	@Override
	public ArrayList<Message> findMessages(String id, String memMsgId) {
		return messageMapper.selectMessages(id, memMsgId);
	}

	@Override
	public int changeRead(String id, String memMsgId) {
		return messageMapper.updateMessagesRead(id, memMsgId);
	}

	@Override
	public int addMessage(Message message) {
		return messageMapper.insertMessage(message);
	}

}
