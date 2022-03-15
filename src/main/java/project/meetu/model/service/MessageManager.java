package project.meetu.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.MessageDAO;
import project.meetu.model.dto.Message;

@Service
public class MessageManager {

	private final MessageDAO messageDao;

	// 외부에서 가져와 값을 넣어주는 경우 - dependency injection(의존성 주입)
	@Autowired
	public MessageManager(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}

	public ArrayList<Message> getMessages(String id, String memMsgId) {
		ArrayList<Message> messages = messageDao.findMessages(id, memMsgId);
		
		Collections.sort(messages, new SortByDate());
		
		return messages;
	}
	
	static class SortByDate implements Comparator<Message> {
        @Override
        public int compare(Message a, Message b) {
            return a.getSentDate().compareTo(b.getSentDate());
        }
    }

	public int changeRead(String id, String memMsgId) {
		return messageDao.changeRead(id, memMsgId);
	}
}
