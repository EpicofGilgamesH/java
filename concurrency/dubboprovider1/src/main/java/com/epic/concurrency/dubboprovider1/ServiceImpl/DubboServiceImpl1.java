
package com.epic.concurrency.dubboprovider1.ServiceImpl;

import api.DubboService1;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 10:36
 */

@Service(version = "1.0.0", timeout = 10000)
@Component
@Slf4j
public class DubboServiceImpl1 implements DubboService1 {

    @Override
    public Person getPerson(String name) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person = new Person();
        person.setName(name);
        person.setAge(18);
        person.setProfessional("coder");
        person.setGender(true);
        log.debug("rpc->getPerson()..reslut->{}", JSON.toJSONString(person));
        return person;
    }
}

