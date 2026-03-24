package com.company.IntelligentPlatform.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Connector class to Spring application context
 * 
 * @author Zhang,Hang
 * @date 2013-2-26
 * 
 */
@Service
public class SpringContextBeanService {

	private ApplicationContext ctx;
	
	private Map<String, ApplicationContext> ctxMap;
	
	public ApplicationContext getContext(){
		if (this.ctx == null) {
			ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/*-spring.xml");
		}
		return ctx;
	}

	public Object getBean(String beanName) {
		if (this.ctx == null) {
			ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/*-spring.xml");
		}
		try{
			return ctx.getBean(beanName);
		}catch(NoSuchBeanDefinitionException ex){
			return null;
		}
	}
	

	public Object checkBeanValid(String beanName) throws NoSuchBeanDefinitionException{
		if (this.ctx == null) {
			ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/*-spring.xml");
		}
		return ctx.getBean(beanName);
	}
	
	public Object getBean(String beanName, String classPath) {
		ApplicationContext ctx = this.registerApplication(classPath);
		try{
			return ctx.getBean(beanName);
		}catch(NoSuchBeanDefinitionException ex){
			return null;
		}
	}
	
	public ApplicationContext registerApplication(String classPath){
		if (this.ctxMap == null) {
			ctxMap = new HashMap<String, ApplicationContext>();			
		}
		if(!this.ctxMap.containsKey(classPath)){
			ApplicationContext ctx = new ClassPathXmlApplicationContext(classPath);
			ctxMap.put(classPath, ctx);
			return ctx;
		} else {
			return ctxMap.get(classPath);
		}
	}

}
