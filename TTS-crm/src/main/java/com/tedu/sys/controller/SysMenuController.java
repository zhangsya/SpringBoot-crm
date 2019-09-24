package com.tedu.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.common.vo.JsonResult;
import com.tedu.common.vo.Node;
import com.tedu.sys.entity.SysMenu;
import com.tedu.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {

	@Autowired
	SysMenuService sysMenuService;
	/**
	 * 修改菜单或按钮信息
	 * @param menu
	 * @return
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu menu) {
		sysMenuService.updateObject(menu);
		return new JsonResult("修改成功");
	}
	
	/**
	 * 新增菜单或按钮
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu menu) {
		sysMenuService.saveObject(menu);
		return new JsonResult("新增成功");
	}
	/**
	 *获取数据库对应的菜单表中的所有菜单信息(id,name,parentId)
	 * @return
	 */
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> nodeList = sysMenuService.findZtreeMenuNodes();
		return new JsonResult(nodeList);
		
	}
	
	/**
	 * 跳转菜单添加页面
	 * @return
	 */
	@RequestMapping("doMenuEditUI")
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}

	/**
	 * 基于菜单id删除菜单信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObjectById(Integer id) {
		sysMenuService.deleteObject(id);
		return new JsonResult("删除成功");
	}

	/**
	 * 查询全部菜单信息
	 * @return
	 */
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<Map<String, Object>> menuList = sysMenuService.findObjects();
		return new JsonResult(menuList);
	}

	/**
	 * 跳转菜单页面
	 * @return
	 */
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
}
