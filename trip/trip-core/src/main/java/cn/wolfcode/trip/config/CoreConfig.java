package cn.wolfcode.trip.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//读取配置类
@Configuration
@PropertySource("classpath:core.properties")
@MapperScan(value = {"cn.wolfcode.trip.mapper"})
public class CoreConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor=new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor=new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
