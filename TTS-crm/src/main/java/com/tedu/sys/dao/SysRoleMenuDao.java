package com.tedu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {

	/**
	 * 根据角色id与menuIds新增角色与菜单的关系数据
	 * @param id
	 * @param menuIds
	 * @return
	 */
	int saveRoleMenu(@Param("id")Integer id, @Param("menuIds")Integer[] menuIds);

	/**
	 * 基于角色id删除角色与菜单关系
	 * @param id
	 * @return
	 */
	@Delete(" delete from sys_role_menus where role_id=#{id}")
	int deleteByRoleId(@Param("id")Integer id);

	/**
	 * 基于角色ids获取菜单ids
	 * @param array
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);

}
