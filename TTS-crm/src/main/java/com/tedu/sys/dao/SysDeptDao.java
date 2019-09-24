package com.tedu.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysDept;
@Mapper
public interface SysDeptDao {
	/**
	 * 查询数据库中所有部门信息
	 * @return
	 */
	List<Map<String, Object>> findObjects();

	/**
	 * 统计下属部门数量
	 * @param id
	 * @return
	 */
	@Select("select count(*) from sys_depts where parentId = #{id}")
	int getChildCount(@Param("id")Integer id);

	/**
	 * 删除部门信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_depts where id = #{id}")
	int deleteObject(Integer id);

	/**
	 * 新增部门信息
	 * @param dept
	 * @return
	 */
	int saveObject(SysDept dept);

	/**
	 * 获取节点对象
	 * @return
	 */
	@Select("select id,name,parentId from sys_depts")
	List<Node> findZTreeNodes();

	/**
	 * 修改部门信息
	 * @param dept
	 * @return
	 */
	@Update("  update sys_depts\r\n" + 
			"         set\r\n" + 
			"           name=#{name},\r\n" + 
			"           note=#{note},\r\n" + 
			"           sort=#{sort},\r\n" + 
			"           parentId=#{parentId},\r\n" + 
			"           modifiedUser=#{modifiedUser},\r\n" + 
			"           modifiedTime=now()\r\n" + 
			"        where id=#{id}")
	int updateObject(SysDept dept);
}
