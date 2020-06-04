
package api;

import entity.Person;


/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 11:11
 */

public interface DubboService2 {
    Person getPerson(String name);
}

