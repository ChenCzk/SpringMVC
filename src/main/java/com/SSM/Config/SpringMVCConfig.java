package com.SSM.Config;

import com.SSM.Interceptor.CzkInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.*")  //扫描包
@EnableWebMvc  //启动SpringWebMVC
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义拦截器
        CzkInterceptor interceptor = new CzkInterceptor();
        registry.addInterceptor(interceptor).addPathPatterns("/my/*.do");

    }

    /*
     * 视图解析器
     * */
    @Bean(name = "viewResolver")
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/JSP/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }



}
