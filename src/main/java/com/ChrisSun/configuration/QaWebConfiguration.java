package com.ChrisSun.configuration;

import com.ChrisSun.interceptor.LoginRequiredInterceptor;
import com.ChrisSun.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Chris on 2016/8/6.
 */
@Component
public class QaWebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private PassportInterceptor passportInterceptor;
    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
