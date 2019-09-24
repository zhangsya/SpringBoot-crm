package com.tedu.sys.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMenuRoleDao {
	int deleteObjectsByMenuId(Integer menuId);
}
