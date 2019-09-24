package com.tedu.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tedu.sys.entity.SysLog;

@Mapper
public interface SysLogDao {
	
	@Delete("delete from sys_logs where id = #{id}")
	int deleteObject(Integer id);
	/**
	 * 根据id删除日志信息
	 * @param ids
	 * @return
	 */
	int deleteObjects(@Param("ids") Integer... ids);
	/**
	 * 基于条件分页查询日志分页信息
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	

	/**
	 * 基于条件查询总记录数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	
	/**
	 * 日志入库
	 * @param log
	 * @return
	 */
	int insertObject(SysLog log);

}
