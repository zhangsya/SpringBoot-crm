package com.tedu.sys.service;

import com.tedu.common.vo.PageObject;
import com.tedu.sys.entity.SysLog;

public interface SysLogService {
	/**
	 * 实现分页查询
	 * @param name
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysLog> findPageObjects(String name,Integer pageCurrent);
	
	/**
	 * 删除日志信息
	 * @param ids
	 * @return
	 */
	int deleteObjects(Integer...ids);
}
