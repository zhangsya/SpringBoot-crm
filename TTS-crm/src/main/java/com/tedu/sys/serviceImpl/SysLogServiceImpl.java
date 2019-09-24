package com.tedu.sys.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tedu.common.anno.RequiredLog;
import com.tedu.common.exception.ServiceException;
import com.tedu.common.vo.PageObject;
import com.tedu.sys.dao.SysLogDao;
import com.tedu.sys.entity.SysLog;
import com.tedu.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	SysLogDao sysLogDao;

	@Override
	@RequiredLog("查询日志")
	public PageObject<SysLog> findPageObjects(String name, Integer pageCurrent) {
		// 1..验证参数是否合法
		if (pageCurrent == null || pageCurrent < 1) {
			throw new IllegalArgumentException("页码值错误!");
		}
		// 2.基于条件查询总记录数
		int rows = sysLogDao.getRowCount(name);
		// 2.1验证查询结果
		if (rows == 0) {
			throw new ServiceException("系统没有查到对应记录");
		}
		// 3.基于条件查询当前页记录
		// 3.1定义pageSize
		int pageSize = 5;
		// 3.2利用pageSize计算startIndex
		int startIndex = (pageCurrent - 1) * pageSize;
		// 3.3调用dao层方法查询记录
		List<SysLog> records = sysLogDao.findPageObjects(name, startIndex, pageSize);

		// 4.对分页信息以及当前记录进行封装
		PageObject<SysLog> pageObject = new PageObject<>();
		pageObject.setRowCount(rows);
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);

		return pageObject;
	}

	@Override
	public int deleteObjects(Integer... ids) {
		//校验参数是否合法
		if (ids==null || ids.length==0) {
			throw new IllegalArgumentException("请选择日志");
		}
		int rows;
		//执行删除
		try {
			rows = sysLogDao.deleteObjects(ids);
		} catch (Throwable  e) {
			e.printStackTrace();
			//发出报警信息(例如给运维人员发短信)
			throw new ServiceException("系统故障，正在恢复中...");
		}
		//验证结果
		if (rows==0) {
			throw new ServiceException("记录可能已经不存在");
		}
		return rows;
	}

}
