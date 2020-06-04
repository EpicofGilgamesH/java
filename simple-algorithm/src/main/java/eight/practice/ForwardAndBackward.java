package eight.practice;

/**
 * 基于栈实现浏览器的前进和后退
 * 1.设置两个栈容器,分别代表后退浏览记录和前进浏览记录
 * 2.当后退时,A容器出栈->B容器进栈 当前进时,B容器出栈->A容器进栈
 * 3.当新入栈数据时,B容器数据清空
 *
 * @Author lizzy
 * @Date 2020/6/3 13:18
 * @Version 1.0
 */
public class ForwardAndBackward {
    /**
     * 前进栈容器
     */
    private final LinkedStack forwardStack = new LinkedStack();
    /**
     * 后退栈容器
     */
    private final LinkedStack backwardStack = new LinkedStack();

    /**
     * 浏览页面
     *
     * @param page
     * @return
     */
    public boolean browse(String page) {
        boolean result = backwardStack.push(page);
        System.out.println("浏览页面,当前页面为：" + page);
        //清空前进栈
        forwardStack.clear();
        return result;
    }

    /**
     * 前进 forward栈中弹出数据,压入backward栈中
     *
     * @return
     */
    public String forward() {
        if (forwardStack.size() <= 0) {
            System.out.println("没有继续的前进页面了");
            return "0";
        }
        String page = forwardStack.pop();
        backwardStack.push(page);
        System.out.println("前进页面,当前页面为：" + backwardStack.get());
        return page;
    }

    /**
     * 后退
     *
     * @return
     */
    public String backward() {
        if (backwardStack.size() <= 1) {
            System.out.println("没有继续的后退页面了");
            return "0";
        }
        String page = backwardStack.pop();
        forwardStack.push(page);
        System.out.println("后退页面,当前页面为：" + backwardStack.get());
        return page;
    }

    public static void main(String[] args) {
        ForwardAndBackward forwardAndBackward = new ForwardAndBackward();
        forwardAndBackward.browse("a");
        forwardAndBackward.browse("b");
        forwardAndBackward.browse("c");

        forwardAndBackward.backward();
        forwardAndBackward.backward();
        forwardAndBackward.backward();

        forwardAndBackward.forward();
        forwardAndBackward.forward();
        forwardAndBackward.forward();

        forwardAndBackward.browse("d");
        forwardAndBackward.forward();
        forwardAndBackward.backward();
        forwardAndBackward.backward();
        forwardAndBackward.backward();
        forwardAndBackward.backward();
        forwardAndBackward.backward();
    }


}
