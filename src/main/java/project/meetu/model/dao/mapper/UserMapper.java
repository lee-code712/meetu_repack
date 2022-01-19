package project.meetu.model.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import project.meetu.model.dto.ServiceUser;

@Mapper
public interface UserMapper {

	public ServiceUser selectServiceUser(String userId);
}
