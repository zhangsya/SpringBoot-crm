package com.tedu.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tedu.common.anno.RequiredLog;
import com.tedu.common.utils.IPUtils;
import com.tedu.sys.dao.SysLogDao;
import com.tedu.sys.entity.SysLog;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class SysLogAspect {

	@Autowired
	SysLogDao sysLogDao;
	
	@Around("@annotation(com.tedu.common.anno.RequiredLog)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		//执行目标方法(result为目标方法的执行结果)
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long totalTime= endTime-startTime;
		log.info("方法执行时长为：" + totalTime);
		saveSysLog(joinPoint,totalTime);
		
		return result;
		
	}

	private void saveSysLog(ProceedingJoinPoint point, long totalTime) throws NoSuchMethodException, SecurityException, JsonProcessingException {
	 	//1.获取日志信息
    	MethodSignature ms=
    	(MethodSignature)point.getSignature();
    	Class<?> targetClass=
    	point.getTarget().getClass();
    	String className=targetClass.getName();
    	//获取接口声明的方法
    	String methodName=ms.getMethod().getName();
    	Class<?>[] parameterTypes=ms.getMethod().getParameterTypes();
    	//获取目标对象方法(AOP版本不同,可能获取方法对象方式也不同)
    	Method targetMethod=targetClass.getDeclaredMethod(
    			    methodName,parameterTypes);
    	 //获取用户名,学完shiro再进行自定义实现,没有就先给固定值
    	String username="SysLogAspect.saveSysLog()zsy";
    			//ShiroUtils.getPrincipal().getUsername();
    	//获取方法参数
    	Object[] paramsObj=point.getArgs();
    	System.out.println("paramsObj="+paramsObj);
    	//将参数转换为字符串
    	String params=new ObjectMapper()
    	.writeValueAsString(paramsObj);
    	//2.封装日志信息
    	SysLog log=new SysLog();
    	log.setName(username);//登陆的用户
    	//假如目标方法对象上有注解,我们获取注解定义的操作值
    	RequiredLog requestLog=
    	targetMethod.getDeclaredAnnotation(RequiredLog.class);
 	if(requestLog!=null){
    	log.setOperation(requestLog.value());
    	}
    	log.setMethod(className+"."+methodName);//className.methodName()
    	log.setParams(params);//method params
    	log.setIp(IPUtils.getIpAddr());//ip 地址
    	log.setTime(totalTime);//
    	log.setCreatedTime(new Date());
    	//3.保存日志信息
    	sysLogDao.insertObject(log);
	}
}
