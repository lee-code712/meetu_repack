package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.ConsultMapper;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.ConsultableTime;

@Component
public class ConsultDAOImpl implements ConsultDAO {

	@Autowired
	private ConsultMapper consultMapper;

	// 회원의 전체 상담목록 조회
	@Override
	public List<Consult> findConsultList(String userId) {
		return consultMapper.selectConsultByUser(userId);
	}

	// 상담id에 대한 상세정보 조회
	@Override
	public Consult findReservation(String consultId) {
		return consultMapper.selectConsultByConsultId(consultId);
	}

	// 예약 상태 변경
	@Override
	public boolean changeStatus(Consult reservation) {
		int ck = consultMapper.updateStatus(reservation);
		if (ck > 0) return true;
		return false;
	}

	// 특정 학생-교수 간 예약 레코드 존재 여부
	@Override
	public boolean checkDuplicatedConsultion(String stuId, String profId) {
		List<Consult> consultList = consultMapper.selectConsultByStuIdAndProfId(stuId, profId);
		boolean flag = false;
		
		for (Consult c : consultList) {
			if (c.getStatus() == 0 || c.getStatus() == 1) {
				flag = true;
			}
		}

		return flag;
	}

	@Override
	public List<ConsultableTime> findConsultableTimeList(String profId) {
		return consultMapper.selectConsultableTimeByUser(profId);
	}
	
}
