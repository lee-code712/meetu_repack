package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.meetu.model.dto.Consult;

@Mapper
public interface ConsultMapper {

	public List<Consult> selectConsultByUser(String userId);
	
}
