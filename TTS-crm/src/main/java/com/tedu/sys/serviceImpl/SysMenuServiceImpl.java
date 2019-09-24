package com.tedu.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tedu.common.exception.ServiceException;
import com.tedu.common.vo.Node;
import com.tedu.sys.dao.SysMenuDao;
import com.tedu.sys.dao.SysMenuRoleDao;
import com.tedu.sys.entity.SysMenu;
import com.tedu.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	SysMenuDao sysMenuDao;
	@Autowired
	SysMenuRoleDao sysMenuRoleDao;

	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> menuList = sysMenuDao.findObjects();
		//验证查询结果
		if (menuList == null || menuList.size() == 0) {
			throw new ServiceException("没有对应菜单信息！");
		}
		return menuList;
	}

	@Override
	public int deleteObject(Integer id) {
		//1.验证参数是否合法
		if (id == null || id <= 0) {
			throw new ServiceException("请选择一个菜单");
		}
		//2.基于id查询是否含有子菜单
		int childCount = sysMenuDao.getChildCount(id);
		if (childCount > 0 ) {
			throw new ServiceException("请先删除子菜单");
		}
		//3.基于id删除menu信息
		int row = sysMenuDao.deleteObject(id);
		if (row == 0) {
			throw new ServiceException("次数据已经不存在");
		}
		//4.基于菜单id删除menu与Role关系数据
		sysMenuRoleDao.deleteObjectsByMenuId(id);
		return row;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> nodeList = sysMenuDao.findZtreeMenuNodes();
		return nodeList;
	}

	@Override
	public int saveObject(SysMenu menu) {
		//验证参数是否合法
		if (menu == null) {
			throw new ServiceException("保存对象不能为空");
		}
		if (StringUtils.isEmpty(menu.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		//保存数据
		int row;
		try {
			row = sysMenuDao.saveObject(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public int updateObject(SysMenu menu) {
		//验证参数是否合法
		if (menu == null) {
			throw new ServiceException("保存对象不能为空");
		}
		if (StringUtils.isEmpty(menu.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		if (menu.getId().intValue() == menu.getParentId().intValue()) {
			throw new ServiceException("上级菜单不能为本菜单");
		}
		//保存数据
		int row;
		try {
			row = sysMenuDao.updateObject(menu);
			if (row == 0) {
				throw new ServiceException("该记录可能已经不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

}
