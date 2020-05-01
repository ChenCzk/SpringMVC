package com.SSM.Interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 定义拦截器
* 在SpringMVCConfig中使用拦截器
* */
public class CzkInterceptor extends HandlerInterceptorAdapter {
    /*
    * 1.前置方法
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        return true;//2.true执行控制器方法
    }

    /*
    * 3.后置方法
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        super.postHandle(request, response, handler, modelAndView);
    }

    /*
    * 4.肯定执行的方法。
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("asd");
        super.afterCompletion(request, response, handler, ex);
    }
}
