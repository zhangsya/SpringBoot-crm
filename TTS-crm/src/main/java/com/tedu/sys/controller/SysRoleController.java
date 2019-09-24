package com.tedu.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.common.vo.CheckRolesBox;
import com.tedu.common.vo.JsonResult;
import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysRoleMenuVo;
import com.tedu.sys.entity.SysRole;
import com.tedu.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	
	@Autowired
	SysRoleService sysRoleService;
	
	/**
	 * 查询角色id与角色名称
	 * @return
	 */
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles() {
		List<CheckRolesBox> list = sysRoleService.findRoles();
		return new JsonResult(list);
	}
	
	/**
	 * 更新角色信息入库
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole role,Integer[] menuIds) {
		sysRoleService.updateObject(role,menuIds);
		return new JsonResult("修改成功");
	}
	/**
	 * 基于roleId查询角色名称,备注以及拥有的菜单ID
	 * @param id
	 * @return
	 */
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		SysRoleMenuVo roleMenuVo = sysRoleService.findObjectById(id);
		return new JsonResult(roleMenuVo);
	}
	/**
	 * 基于id删除角色信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("删除成功");
	}
	/**
	 * 新增角色信息
	 * @param role
	 * @param menuIds
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole role,Integer[] menuIds) {
		sysRoleService.saveObject(role,menuIds);
		return  new JsonResult("新增成功");
	}
	
	/**
	 * 跳转角色编辑页面
	 * @return
	 */
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		return "sys/role_edit";
	}
	
	/**
	 * 返回分页信息以及当前页记录
	 * @return
	 */
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent){
		PageObject<SysRole> pageObject = sysRoleService.findPageObjects(name,pageCurrent);
		return new JsonResult(pageObject);
		
	}
	
	/**
	 * 跳转角列表显示页面
	 * @return
	 */
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	
	
}
