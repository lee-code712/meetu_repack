package project.meetu.model.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.meetu.model.dto.Message;

@Mapper
public interface MessageMapper {

	public ArrayList<Message> selectMessages(@Param("id") String id, @Param("memMsgId") String memMsgId);

	public int updateMessagesRead(@Param("id") String id, @Param("memMsgId") String memMsgId);

	public int insertMessage(Message message);

}
