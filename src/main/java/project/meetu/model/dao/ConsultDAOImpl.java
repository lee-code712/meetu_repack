package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dao.mapper.ConsultMapper;
import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;

@Component
public class ConsultDAOImpl implements ConsultDAO {

	@Autowired
	private ConsultMapper consultMapper;

	// 회원의 전체 상담목록 조회
	@Override
	public List<Consult> findConsultList(String userId) {
		return consultMapper.selectConsultByUser(userId);
	}

	// 전체 단대 조회
	@Override
	public List<College> findCollegeList() {
		return consultMapper.selectCollege();
	}

	// 전체 학과 조회
	@Override
	public List<Department> findDepartmentList() {
		return consultMapper.selectDepartment();
	}
	
	
}
