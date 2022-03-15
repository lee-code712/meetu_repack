package project.meetu.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.MessageMapper;

@Component
public class MessageDAOImpl implements MessageDAO {
	@Autowired
    private MessageMapper messageMapper;

}
