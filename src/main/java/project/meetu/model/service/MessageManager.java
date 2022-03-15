package project.meetu.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.MessageDAO;

@Service
public class MessageManager {

	private final MessageDAO messageDao;

	// 외부에서 가져와 값을 넣어주는 경우 - dependency injection(의존성 주입)
	@Autowired
	public MessageManager(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}
}
