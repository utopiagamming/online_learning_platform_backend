package cn.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Component
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override//复写父类的拦截规则方法
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(checkPermissionInterceptor());
        ir.addPathPatterns("/**");
    }

    @Bean//将自己创建的拦截器交给spring 管理
    public CheckPermissionInterceptor checkPermissionInterceptor(){
        return new CheckPermissionInterceptor();
    }

}
