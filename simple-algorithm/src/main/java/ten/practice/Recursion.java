package ten.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 简单递归算法
 * @Date 2020/7/7 14:19
 * @Created by wangjie
 */
public class Recursion {
    //思路:1.一个问题可以分解成多个子问题,eg:求解自己在哪一排,可以分解为求解前一排的人再哪一排,一直往前推动.
    //    2.这个问题除了数据规模不一样,求解思路是完全一样的.
    //    3.递归有终止条件.


    //假如这里有 n 个台阶，每次你可以跨 1 个台阶或者 2 个台阶，
    // 请问走这 n 个台阶有多少种走法？
    // 如果有 7 个台阶，你可以 2，2，2，1 这样子上去，
    // 也可以 1，2，1，1，2 这样子上去，总之走法有很多，
    // 那如何用编程求得总共有多少种走法呢？
    // 我们仔细想下，实际上，可以根据第一步的走法把所有走法分为两类，
    // 第一类是第一步走了 1 个台阶，另一类是第一步走了 2 个台阶。
    // 所以 n 个台阶的走法就等于先走 1 阶后，n-1 个台阶的走法 加上先走 2 阶后，n-2 个台阶的走法。用公式表示就是
    // f(n)=f(n-1)+f(n-2)
    // 终止条件是,最后的一步台阶有两种方式,最有只有一个台阶,最后有两个台阶。只有一个台阶有一种走法,两个台阶有两种走法。
    // 则最后的递推公司为: f(n)=f(n-1)+f(n-2);f(1)=1;f(2)=2;

    public static int fun1(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        return fun1(n - 1) + fun1(n - 2);
    }

    //写递归代码的关键是找到如何将大问题分解为小问题的规律,
    // 并基于此写出推导公式,然后退出终止条件,最后翻译成代码.

    //递归中会遇到的问题
    //1.递归深度太深,导致堆栈溢出:解决办法,在层次不深的情况下,可以限制递归深度
    //2.递归时产生重复计算:解决办法,通过一个数据结构来保存,比如map

    private static Map<Integer, Integer> map = new HashMap<>();

    public static int fun2(int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        int res = fun2(n - 1) + fun2(n - 2);
        map.put(n, res);
        return res;
    }

    //将f(n)=f(n-1)+f(n-2);递推公式转换成非递归
    public static int fun4(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        int ret = 0;
        int pre = 2;
        int prepre = 1;
        for (int i = 3; i <= n; ++i) {
            ret = pre + prepre;
            prepre = pre;
            pre = ret;
        }
        return ret;
    }


    //递归空间复杂度是O(n)
    //将f(x)=f(x-1)+1;递推公式转换成非递归
    public static int fun3(int n) {
        int ret = 1;
        for (int i = 2; i <= n; ++i) {
            ret = ret + 1;
        }
        return ret;
    }


    public static void main(String[] args) {
        Integer dep1 = 0, dep2 = 0;
        int step = fun1(7);
        System.out.println("7层阶梯共有" + step + "种走法");
        System.out.println(fun2(7));
        System.out.println(fun4(7));
    }
}
