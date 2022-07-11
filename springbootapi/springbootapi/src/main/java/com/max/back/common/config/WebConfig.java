package com.max.back.common.config;

import com.max.back.common.sercurity.intercept.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private AuthInterceptor authInter;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInter).addPathPatterns("/api/**");
	}
}
