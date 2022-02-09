package project.meetu.model.service;
/* 메소드명이 좀 더 비즈니스에 가까움 */

import java.util.List;

import org.apache.catalina.filters.CsrfPreventionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.service.exception.LoginException;
import project.meetu.model.dao.UserDAO;

@Service
public class UserManager {

	private final UserDAO userDao;
	
	// 외부에서 가져와 값을 넣어주는 경우 - dependency injection(의존성 주입)
	@Autowired
	public UserManager(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	/* 로그인 */
	public ServiceUser login(ServiceUser user) throws LoginException {
		
		ServiceUser findUser = userDao.findUser(user.getUserId());

		if (findUser == null) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		else if (user.getMemberInfo().getRole() != findUser.getMemberInfo().getRole()) {
			throw new LoginException("소속이 일치하지 않습니다.");
		}
		else if (!user.getPassword().equals(findUser.getPassword())) {
			throw new LoginException("비밀번호가 일치하지 않습니다.");
		}
		
		return findUser;
	}
	
	/* 전체 단대 조회 */
	public List<College> getColleges() {
		return userDao.findCollegeList();
	}
	
	/* 전체 학과 조회 */
	public List<Department> getDepartments() {
		return userDao.findDepartmentList();
	}
	
	/* 학과별 교수 목록 조회 */
	public List<Professor> getDeptProfessors(String deptNo) {
		List<Professor> professorList = userDao.findDeptProfessorList(deptNo);
	
		if (professorList != null) {
			for(Professor p : professorList) {
				ServiceUser findUser = userDao.findUserByMemberNo(p.getMemberNo());
				if (findUser != null) {
					p.setIsUser(true);
				}
				else {
					p.setIsUser(false);
				}
			}
		}
		return professorList;
		
	}
}
