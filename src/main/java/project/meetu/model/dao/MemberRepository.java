package project.meetu.model.dao;

import java.util.List;
import java.util.Optional;

import project.meetu.model.dto.Member;
/* 메소드명이 좀 더 직관적이고 단순함 */
public interface MemberRepository {
	Member save(Member member);
	Optional<Member> findById(Long id); // null이 반환되는 경우 optional로 감싸줌(java8 기능)
	Optional<Member> findByName(String name);
	List<Member> findAll();
}
