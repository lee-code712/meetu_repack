package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.ConsultMapper;
import project.meetu.model.dto.Consult;

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
	
}
