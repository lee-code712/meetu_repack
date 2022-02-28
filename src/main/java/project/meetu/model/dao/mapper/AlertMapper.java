package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.meetu.model.dto.Alert;

@Mapper
public interface AlertMapper {

	public int selectAlertCountByIsRead(String userId);
	
	public List<Alert> selectAlertByIsRead(String userId);
	
	public int updateIsRead(String userId);
	
	public int insertAlert(@Param("alert") Alert alert, @Param("role") int role, 
			@Param("consultId") int consultId);
	
	public int deleteAlertByIsRead(String userId);
	
}
