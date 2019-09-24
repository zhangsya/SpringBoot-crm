package com.tedu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tedu.common.vo.SysUserDeptVo;

@Mapper
public interface SysUserDeptDao {

	/**
	 * 获取对应页用户与部门信息
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptVo> findPageObjects(String username, int startIndex, int pageSize);
	
	/**
	 * 基于id查询用户及部门信息
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(@Param("id")Integer id);

}
