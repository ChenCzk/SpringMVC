package com.SSM.Config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWeb extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*SpringIOC容器配置*/
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringConfig.class};
    }

    /*Dispatcher文件配置*/
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{SpringMVCConfig.class};
    }

    /*DispatcherServlet拦截内容*/
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }
}
