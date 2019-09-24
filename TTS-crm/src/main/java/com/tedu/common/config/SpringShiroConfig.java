package com.tedu.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @Configuration 注解描述的类为一个配置对象, 此对象也会交给spring管理
 */
@Configuration // bean
public class SpringShiroConfig {
	/**
	 * @Bean 描述的方法,其返回值会交给spring管理
	 * @Bean 一般应用在整合第三bean资源时
	 */
	@Bean
	public SecurityManager newSecurityManager(@Autowired Realm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		//对此realm在SpringShiroConfig类中注入给SecurityManager对象
		sManager.setRealm(realm);
		return sManager;
	}

	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(@Autowired SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		sfBean.setLoginUrl("/doLoginUI");
		// 定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		// 静态资源允许匿名访问:"anon"
		map.put("/bower_components/**", "anon");
		map.put("/build/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/user/doLogin","anon");
		map.put("/user/doLogout","logout");
		// 除了匿名访问的资源,其它都要认证("authc")后访问
		map.put("/**", "authc");
		sfBean.setFilterChainDefinitionMap(map);
		return sfBean;
	}

	/**
	 * 授权的配置
	 * @return
	 */
	//配置shiro框架中一些bean对象的生命周期管理器
	@Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
	//配置代理对象创建器,通过此对象为目标业务对象创建代理对象
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator newProxyCreator() {
    	return new DefaultAdvisorAutoProxyCreator();
    }
    //配置advisor对象,shiro框架底层会通过此对象的matchs方法返回值决定是否创建代理对象,进行权限控制.
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
