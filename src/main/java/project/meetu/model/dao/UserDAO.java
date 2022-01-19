/* 메소드명이 좀 더 직관적이고 단순함 */
package project.meetu.model.dao;

import project.meetu.model.dto.ServiceUser;

public interface UserDAO {
	
	// 회원 조회
	ServiceUser findUser(String userId);

}
