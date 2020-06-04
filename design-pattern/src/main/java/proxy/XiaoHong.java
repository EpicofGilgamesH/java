package proxy;

import lombok.Data;

import java.lang.reflect.Proxy;

/**
 * @Author lizzy
 * @Date 2020/5/27 9:33
 * @Version 1.0
 */
@Data
public class XiaoHong implements XiangQing {

    private final String NAME = "小红";

    @Override
    public void haveDinner() {
        System.out.println("与" + NAME + "共进晚餐");
    }

    @Override
    public void shopping() {
        System.out.println("与" + NAME + "一起购物");
    }

    public static void main(String[] args) {
        //动态代理
        XiaoHong xiaoHong = new XiaoHong();
        DynamicProxy proxy = new DynamicProxy(xiaoHong);
        XiangQing proxyInvoker = (XiangQing) Proxy.newProxyInstance(XiaoHong.class.getClassLoader(), new Class[]{XiangQing.class}, proxy);
        proxyInvoker.haveDinner();
        proxyInvoker.shopping();
    }
}
