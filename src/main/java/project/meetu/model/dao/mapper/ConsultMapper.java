package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;

@Mapper
public interface ConsultMapper {

	public List<Consult> selectConsultByUser(String userId);
	
	
	
}
