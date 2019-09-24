package com.tedu.sys.controller;

import java.util.Map;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.common.vo.JsonResult;
import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysUserDeptVo;
import com.tedu.sys.entity.SysUser;
import com.tedu.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {

	@Autowired
	SysUserService sysUserService;

	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password) {
		// 1.获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 2.通过Subject提交用户信息,交给shiro框架进行认证操作
		// 2.1对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(username, // 身份信息
				password);// 凭证信息
		// 2.2对用户信息进行身份认证
		subject.login(token);
		// 分析:
		// 1)token会传给shiro的SecurityManager
		// 2)SecurityManager将token传递给认证管理器
		// 3)认证管理器会将token传递给realm
		return new JsonResult("登录成功");
	}

	/**
	 * 修改密码
	 * 
	 * @param password
	 * @param newPassword
	 * @param cfgPassword
	 * @return
	 */
	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public JsonResult doUpdatePassword(String password, String newPassword, String cfgPassword) {
		sysUserService.updatePassword(password, newPassword, cfgPassword);
		return new JsonResult("密码修改成功");
	}

	/**
	 * 跳转密码修改页面
	 * 
	 * @return
	 */
	@RequestMapping("doUpdatePwdUI")
	public String doUpdatePwdUI() {
		return "sys/pwd_edit";
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser user, Integer[] roleIds) {
		sysUserService.updateObject(user, roleIds);
		return new JsonResult("修改成功");
	}

	/**
	 * 基于id查询用户及部门信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sysUserService.findObjectById(id);
		return new JsonResult(map);
	}

	/**
	 * 修改用户的禁用启用功能
	 * 
	 * @param sysUser
	 * @return
	 */
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(SysUser sysUser) {
		sysUserService.validById(sysUser);
		return new JsonResult("状态修改成功");
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser user, Integer[] roleIds) {
		sysUserService.saveObject(user, roleIds);
		return new JsonResult("新增成功");
	}

	/**
	 * 跳转用户信息编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}

	/**
	 * 获取所有用户信息以及分页数据
	 * 
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysUserDeptVo> userList = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(userList);
	}

	/**
	 * 跳转用户数据页面
	 * 
	 * @return
	 */
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
}
