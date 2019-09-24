package com.tedu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tedu.common.vo.CheckRolesBox;
import com.tedu.common.vo.SysRoleMenuVo;
import com.tedu.sys.entity.SysRole;

@Mapper
public interface SysRoleDao {

	/**
	 * 查询总记录数
	 * @param name
	 * @return
	 */
	int getRowCount(@Param("name")String name);

	/**
	 * 基于条件查询role记录
	 * @param pageSize 
	 * @param startIndex 
	 * @param name 
	 * @return
	 */
	List<SysRole> findPageObjects(@Param("name")String name,
									@Param("startIndex")int startIndex, 
									@Param("pageSize")int pageSize);

	/**
	 * 新增角色信息
	 * @param role
	 * @return
	 */
	int saveObject(SysRole role);

	/**
	 * 基于id删除角色信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id=#{id} ")
	int deleteObjectById(@Param("id")Integer id);

	/**
	 * 基于roleId查询角色名称,备注以及拥有的菜单ID
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(@Param("id")Integer id);

	/**
	 * 基于roleid修改role数据
	 * @param role
	 * @return
	 */
	int updateObject(SysRole role);
	
	/**
	 * 查询角色id与名称
	 * @return
	 */
	List<CheckRolesBox> findRoles();

}
