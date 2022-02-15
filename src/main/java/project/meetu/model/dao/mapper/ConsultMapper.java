package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.meetu.model.dto.Consult;

@Mapper
public interface ConsultMapper {

	public List<Consult> selectConsultByUser(String userId);
	public Consult selectConsultByConsultId(String consultId);
	public int updateStatus(Consult reservation);
	public List<Consult> selectConsultByStuIdAndProfId(@Param("stuId") String stuId, @Param("profId") String profId);
	
}
