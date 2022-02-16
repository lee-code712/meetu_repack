package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.meetu.model.dto.Consult;
import project.meetu.model.dto.ConsultableTime;

@Mapper
public interface ConsultMapper {

	public List<Consult> selectConsultByUser(String userId);
	
	public Consult selectConsultByConsultId(String consultId);
	
	public int updateStatus(Consult reservation);
	
	public List<Consult> selectConsultByStuIdAndProfId(@Param("stuId") String stuId, @Param("profId") String profId);
	
	public List<ConsultableTime> selectConsultableTimeByUser(String profId);

	public List<Consult> selectConsultByDate(@Param("stuId") String stuId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	public int insertConsult(Consult consult);
	
	public int insertConsultBackup(String consultId);
}
