package com.niit.collaboration.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		logger.debug("Starting of the method getRootConfigClasses");		
		return new Class[]{AppConfig.class};
		
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
logger.debug("Starting of the method getServletConfigClasses");		
return new Class[]{AppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		logger.debug("Starting of the method getServletMappings");		
		return new String[]{"/"};
	}

}
