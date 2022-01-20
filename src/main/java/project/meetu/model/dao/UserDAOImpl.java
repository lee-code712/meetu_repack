package project.meetu.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
}
