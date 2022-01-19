package project.meetu.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.meetu.model.dto.Member;
import project.meetu.model.dao.MemberRepository;
/* 메소드명이 좀 더 비즈니스에 가까움 */
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	
	// 외부에서 가져와 값을 넣어주는 경우 - dependency injection(의존성 주입)
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/* 회원가입 */
	public Long join(Member member) {
		vaildateDuplicateMember(member); // 중복회원 검증
		memberRepository.save(member);
		return member.getId();
	}
	
	private void vaildateDuplicateMember(Member member) {
		// 같은 이름이 있는 중복 회원X
		memberRepository.findByName(member.getName()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
		
//		// 따로 변수를 선언하는 경우	
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m -> {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		});
	}
	
	/* 전체 회원 조회 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/* id로 특정 회원 조회 */
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
