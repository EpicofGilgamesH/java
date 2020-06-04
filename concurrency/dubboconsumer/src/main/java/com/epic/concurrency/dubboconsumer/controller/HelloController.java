package com.epic.concurrency.dubboconsumer.controller;

import api.HelloService;
import com.epic.concurrency.dubboconsumer.Service.OrderService;
import entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 17:21
 */
@RestController
@Slf4j
public class HelloController {
    private HelloService helloService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam(defaultValue = "xkcoding") String name) {
        log.info("i'm ready to call someone......");
        return helloService.sayHello(name);
    }

    @RequestMapping("/generatorOrder")
    public String order() {
        Boolean result = orderService.generatorOrder();
        return result.toString();
    }

    @RequestMapping("/getOrder/{id}")
    public String order(@PathVariable(value = "id") Integer id) {
        Order order = orderService.getOrderById(id);
        return order.toString();
    }

    @RequestMapping("/submitOrder/{id}")
    public String submit(@PathVariable(value = "id") Integer id) throws InterruptedException {

        CountDownLatch count = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                orderService.submitOrderTrans(id);
                count.countDown();
            }).start();

        }
        count.await();

        TimeUnit.SECONDS.sleep(50);
        return "ok";
    }

}
