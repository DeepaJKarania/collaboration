package com.niit.collaboration.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class AppConfig extends WebMvcConfigurerAdapter{

	
	private static final Logger logger=LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public ViewResolver viewResolver()
	{
		logger.debug("Starting of the method viewResolver");
		
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".html");
		logger.debug("Ending of the method viewResolver");
		
		return viewResolver;
		
	}
	
}
