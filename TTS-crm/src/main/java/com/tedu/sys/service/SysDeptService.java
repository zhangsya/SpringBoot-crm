package com.tedu.sys.service;

import java.util.List;
import java.util.Map;

import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysDept;

public interface SysDeptService {

	List<Map<String, Object>> findObjects();

	int deleteObject(Integer id);

	int saveObject(SysDept dept);

	List<Node> findZTreeNodes();

	int updateObject(SysDept dept);

}
