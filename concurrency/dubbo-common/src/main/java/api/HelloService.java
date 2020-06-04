package api;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/13 17:20
 */
public interface HelloService {
    /**
     * 问好
     *
     * @param name 姓名
     * @return 问好
     */
    String sayHello(String name);
}
