package cn.wolfcode.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync //开启异步处理
public class MgrApp {
    public static void main(String[] args) {
        SpringApplication.run(MgrApp.class, args);
    }
}
