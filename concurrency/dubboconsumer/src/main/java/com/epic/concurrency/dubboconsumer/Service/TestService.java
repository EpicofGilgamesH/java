package com.epic.concurrency.dubboconsumer.Service;

import entity.Person;

import java.util.concurrent.ExecutionException;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 11:17
 */
public interface TestService {

    String test(String name);

    String syncTest(String name) throws ExecutionException, InterruptedException;
}
