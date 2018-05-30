package com.tay.ratelimitersample.ratelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	private RateLimitCheckInterceptor rateLimitCheckInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rateLimitCheckInterceptor).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
