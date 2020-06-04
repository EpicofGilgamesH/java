package com.epic.concurrency.dubboconsumer.controller;


import api.DubboService1;
import api.DubboService2;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.epic.concurrency.dubboconsumer.MyFutureTask;
import com.epic.concurrency.dubboconsumer.MyFutureTaskUserVolatile;
import entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 11:08
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:20880", timeout = 10000)
    private DubboService1 dubboService1;
    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:20881", timeout = 10000)
    private DubboService2 dubboService2;

    private Executor threadpool = Executors.newFixedThreadPool(2);

    @RequestMapping(value = "/action")
    @ResponseBody
    public String action() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Person person1 = dubboService1.getPerson("abc");
        Person person2 = dubboService1.getPerson("def");
        String result = JSON.toJSONString(person1) + JSON.toJSONString(person2);
        stopWatch.stop();
        System.out.println("action请求时间：" + stopWatch.getTotalTimeMillis());
        return result;
    }

    @RequestMapping(value = "/asyncAction")
    @ResponseBody
    public String asyncAction() throws ExecutionException, InterruptedException {
        //在控制器中要进行多个业务的调用
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String result = getResult("abc", "def");
        stopWatch.stop();
        System.out.println("asyncAction:" + stopWatch.getTotalTimeMillis());
        return result;
    }

    @RequestMapping(value = "/asyncAction1")
    @ResponseBody
    public String asyncAction1() throws ExecutionException, InterruptedException {
        //在控制器中要进行多个业务的调用
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String result = getResult2("abc", "def");
        stopWatch.stop();
        System.out.println("asyncAction:" + stopWatch.getTotalTimeMillis());
        return result;
    }


    //自定义FutureTask 采用await-notify 等待通知机制
    private String getResult(String name1, String name2) {
        //创建Callable,封装到FutrueTask中,供创建的Thread线程进行调用
        Callable<String> callable1 = () -> {
            Person p1 = dubboService1.getPerson(name1);
            return JSON.toJSONString(p1);
        };

        Callable<String> callable2 = () -> {
            Person p2 = dubboService2.getPerson(name2);
            return JSON.toJSONString(p2);
        };

        MyFutureTask<String> future1 = new MyFutureTask<>(callable1);
        MyFutureTask<String> future2 = new MyFutureTask<>(callable2);

        new Thread(future1).start();
        new Thread(future2).start();

        String str1 = null; //Future的get() 方法会阻塞主线程,直到futrue的值返回
        String str2 = null;
        try {
            str1 = future1.get();
            str2 = future2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return str1 + str2;
    }

    //自定义FutureTask 采用自旋机制
    private String getResult2(String name1, String name2) {
        //创建Callable,封装到FutrueTask中,供创建的Thread线程进行调用
        Callable<String> callable1 = () -> {
            Person p1 = dubboService1.getPerson(name1);
            return JSON.toJSONString(p1);
        };

        Callable<String> callable2 = () -> {
            Person p2 = dubboService2.getPerson(name2);
            return JSON.toJSONString(p2);
        };

        MyFutureTaskUserVolatile<String> future1 = new MyFutureTaskUserVolatile<>(callable1);
        MyFutureTaskUserVolatile<String> future2 = new MyFutureTaskUserVolatile<>(callable2);

        new Thread(future1).start();
        new Thread(future2).start();

        String str1 = null; //Future的get() 方法会阻塞主线程,直到futrue的值返回
        String str2 = null;
        try {
            str1 = future1.get();
            str2 = future2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return str1 + str2;
    }

    @RequestMapping(value = "/callableAction")
    @ResponseBody
    public Callable<String> callableAction() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Callable<String> callable = () -> getCallable("abc", "def");
        stopWatch.stop();
        System.out.println("callableAction:" + stopWatch.getTotalTimeMillis());
        return callable;
    }

    private String getCallable(String name1, String name2) throws ExecutionException, InterruptedException {
        Callable<String> callable1 = () -> {
            System.out.println("子线程" + Thread.currentThread().getName() + "开始:" + System.currentTimeMillis());
            Person p1 = dubboService1.getPerson(name1);
            System.out.println("子线程" + Thread.currentThread().getName() + "结束:" + System.currentTimeMillis());
            return JSON.toJSONString(p1);
        };

        Callable<String> callable2 = () -> {
            System.out.println("子线程" + Thread.currentThread().getName() + "开始:" + System.currentTimeMillis());
            Person p2 = dubboService2.getPerson(name2);
            System.out.println("子线程" + Thread.currentThread().getName() + "结束:" + System.currentTimeMillis());
            return JSON.toJSONString(p2);
        };

        /*FutureTask<String> future1 = new FutureTask<>(callable1);
        FutureTask<String> future2 = new FutureTask<>(callable2);*/
        /*MyFutureTask<String> future1 = new MyFutureTask<String>(callable1);
        MyFutureTask<String> future2 = new MyFutureTask<String>(callable2);*/
        MyFutureTaskUserVolatile<String> future1 = new MyFutureTaskUserVolatile<>(callable1);
        MyFutureTaskUserVolatile<String> future2 = new MyFutureTaskUserVolatile<>(callable2);
        /*new Thread(future1).start();
        new Thread(future2).start();*/
        threadpool.execute(future1);
        threadpool.execute(future2);
        String str1 = future1.get();
        String str2 = future2.get();
        return str1 + str2;
    }
}
