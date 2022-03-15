package project.meetu.model.dao;

import java.util.ArrayList;

import project.meetu.model.dto.Message;

public interface MessageDAO {

	public ArrayList<Message> findMessages(String id, String memMsgId);

	public int changeRead(String id, String memMsgId);

}
