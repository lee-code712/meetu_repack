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
		return consultMapper.selectConsults(userId);
	}
	
	
}
