package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author lizzy
 * @Date 2020/5/27 9:37
 * @Version 1.0
 */
public class DynamicProxy implements InvocationHandler {

    public DynamicProxy(XiaoHong xiaoHong) {
        this.xiaoHong = xiaoHong;
    }

    private XiaoHong xiaoHong;

    /**
     * 代理增强
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理增强----------begain----------");
        Object object = method.invoke(xiaoHong, args);
        System.out.println("代理增强----------end----------");
        return object;
    }
}
