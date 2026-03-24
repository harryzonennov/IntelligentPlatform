package com.company.IntelligentPlatform.common.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
// TODO-LEGACY: import org.springframework.web.servlet.handler.HandlerInterceptorAdapter; (removed in Spring 5.3+)

public class AccessKeyInterceptor implements org.springframework.web.servlet.HandlerInterceptor { // TODO-LEGACY: was extends HandlerInterceptorAdapter
	
	private static final Log log = LogFactory.getLog(AccessKeyInterceptor.class);
	
	private final String accessKeyParameterName = "acessKey";

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {		
		// no-op
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// no-op
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Max-Age", "1000");
		response.addHeader("Access-Control-Allow-Origin", "*");
		String accessKey = request.getParameter(accessKeyParameterName);
//		String referer = request.getHeader("Referer");
//		URL url = new URL(referer);
//		String host = url.getHost().toLowerCase();
		if(accessKey == null){
			log.debug("===============ILLEGAL ACCESS:ACCESS_KEY_MISSING!======================");
		} else {
			log.debug("===============ACCESS WITH Access KEY:" + "================");

		}
		return true;
	}
	
	

}
