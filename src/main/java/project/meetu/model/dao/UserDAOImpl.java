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

//	@Override
//	public Member save(Member member) {
//		member.setId(++sequence);
//		store.put(member.getId(), member);
//		return member; // 저장된 결과 반환
//	}
//
//	@Override
//	public Optional<Member> findById(Long id) {
//		return Optional.ofNullable(store.get(id));
//	}
//
//	@Override
//	public Optional<Member> findByName(String name) {
//		return store.values().stream()
//				.filter(member -> member.getName().equals(name))
//				.findAny();
//	}
//
//	@Override
//	public List<Member> findAll() {
//		return memberMapper.getList();
//	}
//	
//	public void clearStore() {
//		store.clear();
//	}
	
}
