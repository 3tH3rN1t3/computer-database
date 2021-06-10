package com.excilys.cdb.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class MainWebAppInitializer extends AbstractDispatcherServletInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(DBConfig.class, SpringConfig.class, WebConfig.class);
		return rootContext;
	}

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
		servletContext.register(ServletConfig.class);
		return servletContext;
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}