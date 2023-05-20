package com.example.site.web;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionTrackingMode;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
//
//        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
//        servletContext.getSessionCookieConfig().setHttpOnly(true);
//        servletContext.getSessionCookieConfig().setSecure(true);
//
//        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
//        dispatcherServlet.register(WebAppContext.class);
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
//        servlet.addMapping("/");
//        servlet.setLoadOnStartup(1);

    }
}