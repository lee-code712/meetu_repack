package project.meetu.model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.meetu.model.dto.Member;

@Mapper
public interface MemberMapper {

	public List<Member> getList();
}
