package com.example.staffjoy.faraday.config;

import com.example.staffjoy.faraday.filter.FavinconFliter;
import com.example.staffjoy.faraday.filter.HealthCheckFliter;
import com.example.staffjoy.faraday.view.AsessLoader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties(FaradayPropertise.class)
public class FaradayConfiguration {


    /**
     * 注册健康检查过滤器
     * @return
     */
    public FilterRegistrationBean<HealthCheckFliter> healthCheckFliterFilterRegistrationBean(){
        FilterRegistrationBean<HealthCheckFliter> healthCheckFliterFilterRegistrationBean =
                new FilterRegistrationBean<>(new HealthCheckFliter());
        //指定filter顺序
        healthCheckFliterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 70);
        return healthCheckFliterFilterRegistrationBean;
    }
    /**
     * 注册favincon 过滤器
     * @return
     */
    public FilterRegistrationBean<FavinconFliter> favinconFliterFilterRegistrationBean(){
        FilterRegistrationBean<FavinconFliter> favinconFliterFilterRegistrationBean = new
                FilterRegistrationBean<>(new FavinconFliter(AsessLoader.favinconBytes));
        favinconFliterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 78);
        return favinconFliterFilterRegistrationBean;
    }


}
