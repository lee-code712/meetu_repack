package project.meetu.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dao.ConsultDAO;
import project.meetu.model.dto.College;
import project.meetu.model.dto.Consult;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;

@Service
public class ConsultManager {

	private final ConsultDAO consultDao;
	
	@Autowired
	public ConsultManager(ConsultDAO consultDao) {
		this.consultDao = consultDao;
	}
	
	/* 회원의 전체 상담목록 조회 */
	public List<Consult> getUserConsults(String userId) {
		return consultDao.findConsultList(userId);
	}
	
	/* 전체 단대 조회 */
	public List<College> getColleges() {
		return consultDao.findCollegeList();
	}
	
	/* 전체 학과 조회 */
	public List<Department> getDepartments() {
		return consultDao.findDepartmentList();
	}
	
	/* 학과별 교수 목록 조회 */
	public List<Professor> getDeptProfessors(String deptNo) {
		return consultDao.findDeptProfessorList(deptNo);
	}
}
