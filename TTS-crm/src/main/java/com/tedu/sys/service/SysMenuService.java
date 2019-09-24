package com.tedu.sys.service;

import java.util.List;
import java.util.Map;

import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysMenu;

public interface SysMenuService {
	
	int deleteObject(Integer id);
	
	List<Map<String, Object>> findObjects();

	List<Node> findZtreeMenuNodes();

	int saveObject(SysMenu menu);

	int updateObject(SysMenu menu);
}
