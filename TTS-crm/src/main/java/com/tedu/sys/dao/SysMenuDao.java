package com.tedu.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysMenu;

@Mapper
public interface SysMenuDao {
	
	/**
	 * 基于菜单id查询子菜单
	 */
	int getChildCount(@Param("id")Integer id);
	/**
	 * 基于菜单id删除菜单信息
	 * @param id
	 * @return
	 */
	int deleteObject(@Param("id")Integer id);
	/**
	 * 查询所有菜单，按钮信息
	 * @return
	 */
	List<Map<String, Object>> findObjects();
	
	/**
	 * 基于请求获取数据库对应的菜单表中的所有菜单信息(id,name,parentId)
	 * @return
	 */
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 新增菜单或按钮
	 * @param menu
	 * @return
	 */
	int saveObject(SysMenu menu);
	/**
	 * 修改菜单或按钮信息
	 * @param menu
	 * @return
	 */
	int updateObject(SysMenu menu);
	
	/**
	 * 基于菜单ids查询对应的标识符
	 * @param array
	 * @return
	 */
	List<String> findPermissions(@Param("menuIds")Integer[] menuIds);
}
