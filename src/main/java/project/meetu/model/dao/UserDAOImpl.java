package project.meetu.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.meetu.model.dto.College;
import project.meetu.model.dto.Department;
import project.meetu.model.dto.Professor;
import project.meetu.model.dto.ServiceUser;
import project.meetu.model.dao.mapper.UserMapper;

@Component
public class UserDAOImpl implements UserDAO {
	
	@Autowired
    private UserMapper userMapper;

	// 회원 조회
	@Override
	public ServiceUser findUser(String userId) {
		return userMapper.selectServiceUser(userId);
	}
	
	// 전체 단대 조회
	@Override
	public List<College> findCollegeList() {
		return userMapper.selectCollege();
	}

	// 전체 학과 조회
	@Override
	public List<Department> findDepartmentList() {
		return userMapper.selectDepartment();
	}

	// 학과별 교수 조회
	@Override
	public List<Professor> findDeptProfessorList(String deptNo) {
		return userMapper.selectProfessorByDept(deptNo);
	}

	// memberNo에 해당하는 회원 조회
	@Override
	public ServiceUser findUserByMemberNo(String memberNo) {
		return userMapper.selectServiceUserByMemberNo(memberNo);
	}

	// 교수 검색
	@Override
	public List<Professor> findProfessorListByKeyword(String keyword) {
		return userMapper.selectProfessorByKeyword(keyword);
	}	
}
