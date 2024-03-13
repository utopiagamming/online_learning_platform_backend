package cn.exam.config;

import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DuridSessionIPFilter {
    @Bean
    public WebStatFilter druidStatFilter2() {

        WebStatFilter filter = new WebStatFilter();
        filter.setSessionStatEnable(false);
        return filter;
    }

}
