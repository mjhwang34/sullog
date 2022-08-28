package com.wit.sullog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
	/*
	protected void configure(HttpSecurity http) throws Exception {
	  http
	    .authorizeRequests()
	    	.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
	}*/ 
}