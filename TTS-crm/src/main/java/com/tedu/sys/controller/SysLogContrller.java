package com.tedu.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.common.vo.JsonResult;
import com.tedu.common.vo.PageObject;
import com.tedu.sys.entity.SysLog;
import com.tedu.sys.service.SysLogService;

@Controller
@RequestMapping("/log/")
public class SysLogContrller {
	
	@Autowired
	SysLogService sysLogService;
	
	/**
	 * 返回日志页面
	 * @return
	 * 
	 */
	@RequestMapping("doLogListUI")
	public String doLogListUI(){
		return "sys/log_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysLog> pageObjects = sysLogService.findPageObjects(username, pageCurrent);
		
		return new JsonResult(pageObjects);
	}
	
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids) {
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
}
