package com.tedu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDao {
	/**
	 * 基于角色id解除用户与角色关系
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_user_roles where role_id=#{id}")
	int deleteByRoleId(@Param("id")Integer id);

	/**
	 * 添加用户与角色关系数据
	 * @param id
	 * @param roleIds
	 * @return
	 */
	int insertObject(@Param("id")Integer id, @Param("roleIds")Integer[] roleIds);

	/**
	 * 基于用户id查询角色ids
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(@Param("id")Integer id);

	
}
