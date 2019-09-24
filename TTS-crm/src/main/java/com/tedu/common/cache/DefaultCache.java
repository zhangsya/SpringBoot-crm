package com.tedu.common.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DefaultCache {
	public DefaultCache() {
		System.out.println("DefaultCache.DefaultCache()");
	}
	
	
	@PostConstruct
	public void init() {
		System.out.println("DefaultCache.init()");
	}
	
	@PreDestroy
	public void destory() {
		System.out.println("DefaultCache.destory()");
	}
}
