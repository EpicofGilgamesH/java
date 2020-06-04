package com.epic.concurrency.dubboprovider1;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class DubboProvider1Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvider1Application.class, args);
    }

}
