import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;

/**
 * @author wangjie
 * @version v1.0
 * @description test
 * @date 2020/5/11 14:09
 */

public class Test {

    @org.junit.Test
    public void test1() {

        URL url = this.getClass().getClassLoader().getResource("");
        System.out.println(url.getPath());

        System.out.println(getClass().getSimpleName());
    }

    @org.junit.Test
    public void test2() {
        Method method = null;
        try {
            method = this.getClass().getMethod("mytest", String.class, Integer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Class<?>[] classes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();
    }

    public void mytest(String a, Integer b) {
        System.out.println(a + b);
    }
}
