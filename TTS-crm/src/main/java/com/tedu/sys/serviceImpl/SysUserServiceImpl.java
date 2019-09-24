package com.tedu.sys.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tedu.common.anno.RequiredLog;
import com.tedu.common.exception.ServiceException;
import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysUserDeptVo;
import com.tedu.sys.dao.SysUserDao;
import com.tedu.sys.dao.SysUserDeptDao;
import com.tedu.sys.dao.SysUserRoleDao;
import com.tedu.sys.entity.SysUser;
import com.tedu.sys.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	SysUserDao sysUserDao;
	@Autowired
	SysUserDeptDao sysUserDeptDao;
	@Autowired
	SysUserRoleDao sysUserRoleDao;

	/**
	 * 返回用户信息以及分页数据
	 */
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		// 校验参数是否合法
		if (pageCurrent == null || pageCurrent < 1) {
			throw new ServiceException("页码值错误");
		}
		// 获取总记录数
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount <= 0) {
			throw new ServiceException("没有查到系统记录");
		}
		// 定义页面大小
		int pageSize = 5;
		// 确定起始页位置
		int startIndex = (pageCurrent - 1) * pageSize;
		// 确定总页数
		int pageCount = (rowCount - 1) / pageSize + 1;
		// 获取对应页用户信息
		List<SysUserDeptVo> userDeptVo = sysUserDeptDao.findPageObjects(username, startIndex, pageSize);
		// 创建分页对象并赋值
		PageObject<SysUserDeptVo> pageObject = new PageObject<>();
		pageObject.setPageCount(pageCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(userDeptVo);
		return pageObject;
	}

	/**
	 * 新增用户
	 */
	@Override
	public int saveObject(SysUser user, Integer[] roleIds) {
		// 校验参数
		if (user == null) {
			throw new ServiceException("用户信息不能为空");
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new ServiceException("用户密码不能为空");
		}
		if (roleIds == null || roleIds.length == 0) {
			throw new ServiceException("必须为用户分配角色");
		}

		// 用户密码加密处理
		// 生成盐值
		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);
		// 利用Shior的MD5加密
		SimpleHash sHash = new SimpleHash("MD5", user.getPassword(), salt);
		user.setPassword(sHash.toHex());
		// 新增用户入库
		int row;
		try {
			row = sysUserDao.saveObject(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("用户信息新增失败");
		}
		// 添加用户与角色关系数据
		try {
			sysUserRoleDao.insertObject(user.getId(), roleIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("用户与角色关系数据插入失败");
		}
		return row;
	}

	/**
	 * 基于用户id修改禁用启用状态
	 */
	@RequiresPermissions("sys:user:valid")
	@RequiredLog("禁用启用")
	@Override
	public int validById(SysUser sysUser) {
		// 1.校验参数是否合法
		if (sysUser.getId() == null || sysUser.getId() <= 0)
			throw new ServiceException("参数不合法,id=" + sysUser.getId());
		if (sysUser.getValid() != 1 && sysUser.getValid() != 0)
			throw new ServiceException("参数不合法,valie=" + sysUser.getValid());

		// if(StringUtils.isEmpty(modifiedUser))
		// throw new ServiceException("修改用户不能为空");

		// 2.执行禁用或启用操作
		int rows = 0;
		try {
			rows = sysUserDao.validById(sysUser);
		} catch (Throwable e) {
			e.printStackTrace();
			// 报警,给维护人员发短信
			throw new ServiceException("底层正在维护");
		}
		// 3.判定结果,并返回
		if (rows == 0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}

	/**
	 * 基于id查询用户及部门信息
	 */
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		// 校验参数是否合法
		if (id == null || id <= 0) {
			throw new ServiceException("参数不合法,id=" + id);
		}
		SysUserDeptVo userDeptVo = sysUserDeptDao.findObjectById(id);
		if (userDeptVo == null) {
			throw new ServiceException("此用户已经不存在");
		}
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		// 3.数据封装
		Map<String, Object> map = new HashMap<>();
		map.put("user", userDeptVo);
		map.put("roleIds", roleIds);
		return map;
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public int updateObject(SysUser user, Integer[] roleIds) {
		// 校验参数是否合法
		if (user == null) {
			throw new ServiceException("用户信息不能为空");
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			throw new ServiceException("用户名不能为空");
		}
		if (roleIds == null || roleIds.length == 0) {
			throw new ServiceException("必须为用户赋予角色");
		}
		int row = sysUserDao.updateObject(user);

		sysUserRoleDao.deleteByRoleId(user.getId());
		try {
			sysUserRoleDao.insertObject(user.getId(), roleIds);
		} catch (Exception e) {
			throw new ServiceException("新增用户关系数据失败");
		}
		return row;
	}

	/**
	 * 修改用户密码
	 */
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		// 校验参数
		if (password == null || StringUtils.isEmpty(password)) {
			throw new ServiceException("原密码不能为空");
		}
		if (newPassword == null || StringUtils.isEmpty(newPassword)) {
			throw new ServiceException("新原密码不能为空");
		}
		// 获取登陆用户
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		// 与原密码对比（加密算法，加密对象，盐值，加密次数）
		SimpleHash sh = new SimpleHash("MD5", password, user.getSalt(), 1);
		if (!user.getPassword().equals(sh.toHex())) {
			throw new IllegalArgumentException("原密码不正确");
		}
		//两次新密码作对比
		if (!newPassword.equals(cfgPassword)) {
			throw new IllegalArgumentException("两次输入的密码不相等");
		}
		//新密码加密
		String salt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5", newPassword, salt, 1);
		//更新数据库中密码
		int row = sysUserDao.updatePassword(sh.toHex(),salt,user.getId());
		if (row == 0) {
			throw new ServiceException("密码修改失败");
		}
		return row;
	}

}
