package com.tedu.sys.service;

import java.util.Map;

import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysUserDeptVo;
import com.tedu.sys.entity.SysUser;

public interface SysUserService {

	/**
	 * 
	 * 获取所有用户信息以及分页数据
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

	/**
	 * 新增用户
	 * @param userDeptVo
	 * @return
	 */
	int saveObject(SysUser user,Integer[] roleIds);

	/**
	 * 用户的禁用启用
	 * @param sysUser
	 * @return
	 */
	int validById(SysUser sysUser);
	
	/**
	 * 基于id查询用户及部门信息
	 * @param id
	 * @return
	 */
	Map<String,Object> findObjectById(Integer id);

	/**
	 * 更新用户信息
	 * @param user
	 * @param roleIds
	 * @return
	 */
	int updateObject(SysUser user, Integer[] roleIds);

	/**
	 * 修改用户密码
	 * @param password
	 * @param newPassword
	 * @param cfgPassword
	 * @return
	 */
	int updatePassword(String password, String newPassword, String cfgPassword);


}
