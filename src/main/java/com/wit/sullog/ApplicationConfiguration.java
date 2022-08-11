package com.wit.sullog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
	@Autowired
	private Interceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/alchol/**", "/record/**");
		//.excludePathPatterns("/public/**" ,"/css/**" ,"/images/**" ,"/js/**" ,"/error/**" ,"**.ico" );  
	}
}