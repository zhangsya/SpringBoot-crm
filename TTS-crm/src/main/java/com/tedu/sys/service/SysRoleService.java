package com.tedu.sys.service;

import java.util.List;

import com.tedu.common.vo.CheckRolesBox;
import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysRoleMenuVo;
import com.tedu.sys.entity.SysRole;

public interface SysRoleService {

	PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);

	int saveObject(SysRole role, Integer[] menuIds);

	int deleteObject(Integer id);

	SysRoleMenuVo findObjectById(Integer id);

	int updateObject(SysRole role, Integer[] menuIds);
	
	/**
	 * 查询角色id与名称
	 * @return
	 */
	List<CheckRolesBox> findRoles();

}
