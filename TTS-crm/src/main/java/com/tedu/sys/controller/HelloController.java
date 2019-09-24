package com.tedu.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/")
public class HelloController {
	

	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping("doLoginUI")
	public String doLoginUI(){
		return "login";
	}
	
	@RequestMapping("doSayHello")
	@ResponseBody
	public String sayHello() {
		return "Hello SpringBoot !";
		
	}

	@RequestMapping("doIndexUI")
	public String sayHelloUI() {
		log.info("doIndexUI");
		//return "hello";
		return "starter";
	}
	
    /**
     * 返回分页页面
     */
    @RequestMapping("doPageUI")
    public String doPageUI(){
  	 //...
    	 return "common/page";
    }
}
