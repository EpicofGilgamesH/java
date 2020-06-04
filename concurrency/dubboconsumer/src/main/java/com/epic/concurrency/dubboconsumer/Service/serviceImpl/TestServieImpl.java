/*
package com.epic.concurrency.dubboconsumer.Service.serviceImpl;

import api.DubboService1;
import api.DubboService2;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.epic.concurrency.dubboconsumer.Service.TestService;
import entity.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

*/
/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 11:18
 *//*

@Service
public class TestServieImpl implements TestService {

    @Reference
    private DubboService1 dubboService1;
    @Reference
    private DubboService2 dubboService2;

    public String test(String name) {
        Person p1 = null;
        Person p2 = null;
        try {
            p1 = dubboService1.getPerson(name);
            p2 = dubboService2.getPerson(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str1 = JSON.toJSONString(p1);
        String str2 = JSON.toJSONString(p2);
        return str1 + str2;
    }

    public String syncTest(String name) throws ExecutionException, InterruptedException {
        //创建Callable,封装到FutrueTask中,供创建的Thread线程进行调用
        Callable<String> callable1 = () -> {
            Person p1 = dubboService1.getPerson(name);
            return JSON.toJSONString(p1);
        };

        Callable<String> callable2 = () -> {
            Person p2 = dubboService2.getPerson(name);
            return JSON.toJSONString(p2);
        };

        FutureTask<String> future1 = new FutureTask<>(callable1);
        FutureTask<String> future2 = new FutureTask<>(callable2);

        new Thread(future1).start();
        new Thread(future2).start();

        String str1 = future1.get(); //Future的get() 方法会阻塞主线程,直到futrue的值返回
        String str2 = future2.get();

        return str1 + str2;
    }
}
*/
