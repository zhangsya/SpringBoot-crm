package com.tedu.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tedu.common.exception.ServiceException;
import com.tedu.common.vo.Node;
import com.tedu.sys.dao.SysDeptDao;
import com.tedu.sys.dao.SysUserDao;
import com.tedu.sys.entity.SysDept;
import com.tedu.sys.service.SysDeptService;

@Service
public class SysDeptServiceImpl implements SysDeptService {

	@Autowired
	SysDeptDao sysDeptDao;
	@Autowired
	SysUserDao sysUserDao;

	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> deptList = sysDeptDao.findObjects();
		return deptList;
	}

	@Override
	public int deleteObject(Integer id) {
		//校验参数是否合法
		if (id == null || id <= 0) {
			throw new ServiceException("请选择一条记录");
		}
		int count = sysDeptDao.getChildCount(id);
		if (count > 0) {
			throw new ServiceException("请先删除下属部门");
		}
		//2.验证部门下是否存在员工
		int counts = sysUserDao.getUserCountByDeptId(id);
		if (counts > 0) {
			throw new ServiceException("该部门下有员工,不能删除");
		}
		int row = sysDeptDao.deleteObject(id);
		return row;
	}

	@Override
	public int saveObject(SysDept dept) {
		//1.校验参数是否合法
		if (dept == null) {
			throw new ServiceException("保存对象不能为空");
		}
		if (StringUtils.isEmpty(dept.getName())) {
			throw new ServiceException("部门名称不能为空");
		}

		int row;
		try {
			row = sysDeptDao.saveObject(dept);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public List<Node> findZTreeNodes() {
		List<Node> nodeList = sysDeptDao.findZTreeNodes();
		return nodeList;
	}

	@Override
	public int updateObject(SysDept dept) {
		//1.验证 参数是否合法
		if (dept == null) {
			throw new ServiceException("保存对象不能为空");
		}
		if (StringUtils.isEmpty(dept.getName())) {
			throw new ServiceException("部门名称不能为空");
		}
		//3.上级部门不能为本部门
		if (dept.getId().intValue() == dept.getParentId().intValue()) {
			throw new ServiceException("上级部门不能为本部门");
		}
		//2.修改数据
		int row = sysDeptDao.updateObject(dept);
		if (row <= 0) {
			throw new ServiceException("该条记录可能已经不存在");
		}
		return row;
	}

}
