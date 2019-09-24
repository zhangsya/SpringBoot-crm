package com.tedu.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.common.vo.JsonResult;
import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysDept;
import com.tedu.sys.service.SysDeptService;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {

	@Autowired
	SysDeptService sysDeptService;

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysDept dept) {
		sysDeptService.updateObject(dept);
		return new JsonResult("修改成功");
	}
	
	/**
	 * 新增部门信息
	 * @param dept
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysDept dept) {
		sysDeptService.saveObject(dept);
		return new JsonResult("新增成功");
	}

	/**
	 * 获取部门节点对象,并封装返回.
	 * 
	 * @return
	 */
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes() {
		List<Node> nodeList = sysDeptService.findZTreeNodes();
		return new JsonResult(nodeList);
	}

	/**
	 * 点击添加/修改按钮跳转此页面
	 * 
	 * @return
	 */
	@RequestMapping("doDeptEditUI")
	public String doDeptEditUI() {
		return "sys/dept_edit";
	}

	/**
	 * 基于id删除部门信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysDeptService.deleteObject(id);
		return new JsonResult("删除成功");
	}

	/**
	 * 查询数据库中全部部门信息
	 * 
	 * @return
	 */
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<Map<String, Object>> deptList = sysDeptService.findObjects();
		return new JsonResult(deptList);
	}

	/**
	 * 跳转部门信息页面
	 * 
	 * @return
	 */
	@RequestMapping("doDeptListUI")
	public String doDeptListUI() {
		return "sys/dept_list";
	}

}
