package com.epic.concurrency.dubboprovider2;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class DubboProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvider2Application.class, args);
    }

}
