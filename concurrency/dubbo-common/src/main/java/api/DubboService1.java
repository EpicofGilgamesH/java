package api;

import entity.Person;


/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 10:32
 */

public interface DubboService1 {

    Person getPerson(String name);
}

