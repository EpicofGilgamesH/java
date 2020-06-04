package com.epic.concurrency.dubboprovider1.ServiceImpl;

import api.HelloService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 17:23
 */
@Slf4j
@Service
@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        log.info("someone is calling me......");
        return "say hello to: " + name;
    }
}
