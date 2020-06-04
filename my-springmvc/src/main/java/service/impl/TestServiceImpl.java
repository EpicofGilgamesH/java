package service.impl;

import annotation.MyService;
import service.TestService;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/11 10:46
 */
@MyService
public class TestServiceImpl implements TestService {

    @Override
    public String getInfo(String a, Integer b) {
        return "通过service方法,返回a" + a + ",b" + b;
    }
}
