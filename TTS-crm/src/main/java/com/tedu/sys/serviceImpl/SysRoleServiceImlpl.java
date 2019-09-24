package com.tedu.sys.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tedu.common.anno.RequiredLog;
import com.tedu.common.exception.ServiceException;
import com.tedu.common.vo.CheckRolesBox;
import com.tedu.common.vo.PageObject;
import com.tedu.common.vo.SysRoleMenuVo;
import com.tedu.sys.dao.SysRoleDao;
import com.tedu.sys.dao.SysRoleMenuDao;
import com.tedu.sys.dao.SysUserRoleDao;
import com.tedu.sys.entity.SysRole;
import com.tedu.sys.service.SysRoleService;
@Service
public class SysRoleServiceImlpl implements SysRoleService {

	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	/**
	 * 基于部门名模糊查询
	 * 返回部门信息进行分页操作
	 */
	@Override
	@RequiredLog("角色查询")
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.校验页码参数是否合法
		if (pageCurrent == null || pageCurrent < 0) {
			throw new ServiceException("页码值不合法");
		}
		//2.查询总记录数
		int rows = sysRoleDao.getRowCount(name);
		//2.1验证结果
		if (rows == 0) {
			throw new ServiceException("系统没有查到对应结果");
		}
		//3.基于条件查询当前页记录,起始页位置
		//3.1定义页面大小pageSize
		int pageSize = 5;
		//3.2利用pageSize计算起始页位置
		int startIndex = (pageCurrent-1) * pageSize;
		//3.3根据name,startPage,pageSize查询记录数
		List<SysRole> pageObjects = sysRoleDao.findPageObjects(name,startIndex,pageSize);
		//4.利用工具类对分页信息以及当前记录进行封装
		PageObject<SysRole> pageObject = new PageObject<>();
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rows);
		pageObject.setRecords(pageObjects);
		return pageObject;
	}

	/**
	 * 新增角色信息
	 */
	@Transactional
	@Override
	public int saveObject(SysRole role, Integer[] menuIds) {
		//1.验证参数是否合法
		if (role == null) {
			throw new ServiceException("保存数据不能为空");
		}
		if (StringUtils.isEmpty(role.getName())) {
			throw new ServiceException("角色名不能为空");
		}
		if (menuIds == null || menuIds.length == 0) {
			throw new ServiceException("必须为角色赋予权限");
		}
		int row = sysRoleDao.saveObject(role);
		try {
			sysRoleMenuDao.saveRoleMenu(role.getId(),menuIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("角色与菜单关系数据插入失败");
		}
		
		return row;
	}

	/**
	 * 基于id删除角色信息
	 */
	@Override
	public int deleteObject(Integer id) {
		//1.验证参数是否合法
		if (id == null || id < 1) {
			throw new ServiceException("id值不正确,id = "+id);
		}
		//2.解除用户与角色关系
		try {
			sysUserRoleDao.deleteByRoleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("用户与角色关系解除失败");
		}
		//3.解除角色与菜单关系
		try {
			sysRoleMenuDao.deleteByRoleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("角色与菜单关系解除失败");
		}
		
		int row  = sysRoleDao.deleteObjectById(id);
		return row;
	}

	/**
	 * 基于roleId查询角色名称,备注以及拥有的菜单ID
	 */
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.校验参数是否合法
		if (id == null) {
			throw new ServiceException("请选择一条记录");
		}
		if (id < 1) {
			throw new ServiceException("选择的参数不合法");
		}
		SysRoleMenuVo roleMenuVo = sysRoleDao.findObjectById(id);
		if (roleMenuVo == null) {
			throw new ServiceException("此条记录可能已经不存在");
		}
		System.out.println(roleMenuVo);
		return roleMenuVo;
	}

	@Override
	public int updateObject(SysRole role, Integer[] menuIds) {
		//1.校验参数是否合法
		if (role == null || StringUtils.isEmpty(role.getName())) {
			throw new ServiceException("数据不能更新为空");
		}
		if (menuIds == null || menuIds.length == 0) {
			throw new ServiceException("必须为角色指定一个权限");
		}
		//2.参数合法之后修改数据
		//2.1修改role信息
		int row = sysRoleDao.updateObject(role);
		if (row == 0) {
			throw new ServiceException("该记录可能已经不存在");
		}
		//2.2修改权限信息
		//先基于角色id删除角色与菜单关系
		int i = sysRoleMenuDao.deleteByRoleId(role.getId());
		System.out.println("删除记录"+i);
		//根据角色id与menuIds插入角色与菜单关系数据
		sysRoleMenuDao.saveRoleMenu(role.getId(), menuIds);
		return row;
	}

	/**
	 * 查询角色id与名称
	 */
	@Override
	public List<CheckRolesBox> findRoles() {
		List<CheckRolesBox> list = sysRoleDao.findRoles();
		return list;
	}

}
