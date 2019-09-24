package com.tedu.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tedu.sys.entity.SysUser;

@Mapper
public interface SysUserDao {

	/**
	 * 基于部门id获取用户数量
	 * @param id
	 * @return
	 */
	int getUserCountByDeptId(Integer id);

	/**
	 * 基于用户模糊查询用户总数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);


	/**
	 * 新增用户
	 * @param userDeptVo
	 * @return
	 */
	int saveObject(SysUser user);

	/**
	 * 基于用户id修改禁用启用状态
	 * @param sysUser
	 * @return
	 */
	int validById(SysUser sysUser);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	int updateObject(SysUser user);

	/**
	 * 更新数据库中密码
	 * @param sh
	 * @param salt
	 * @param id
	 * @return
	 */
	int updatePassword(@Param("password")String password, @Param("salt")String salt, @Param("id")Integer id);

	/**
	 * 基于用户名查询用户信息
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(@Param("username")String username);

}
