package split;

import cn.hutool.core.collection.CollectionUtil;
import com.yl.locker.api.dto.TimeConditionDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2019<br>
 * <p>
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_
 * //                         o8888888o
 * //                         88" . "88
 * //                         (| ^_^ |)
 * //                         O\  =  /O
 * //                      ____/`---'\____
 * //                    .'  \\|     |//  `.
 * //                   /  \\|||  :  |||//  \
 * //                  /  _||||| -:- |||||-  \
 * //                  |   | \\\  -  /// |   |
 * //                  | \_|  ''\---/''  |   |
 * //                  \  .-\__  `-`  ___/-. /
 * //                ___`. .'  /--.--\  `. . ___
 * //              ."" '<  `.___\_<|>_/___.'  >'"".
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //      ========`-.____`-.___\_____/___.-`____.-'========
 * //                           `=---='
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //         佛祖保佑       永无BUG     永不修改
 * ////////////////////////////////////////////////////////////////////
 *
 * @Description: 16:57:45.227 [main] INFO StringTest - FastString-all转换(2147483647次)：结果=567891011, 耗时=6ms
 * 16:58:17.427 [main] INFO StringTest - Integer.parseInt转换(2147483647次)：结果=567891011, 耗时=32200ms
 * 16:58:17.432 [main] INFO StringTest - FastString-substring转换(2147483647次)：结果=567891011, 耗时=5ms
 * 16:58:31.192 [main] INFO StringTest - String.substring转换(2147483647次)：结果=567891011, 耗时=13760ms
 * @date 2020-12-13 14:39
 * @Author: <a href= "765741668@qq.com">yangzonghua</a>
 */
public class FastString {

    /**
     * @Description: 种植一个基于2的幂次方最大的种子基数,为后续运算JDK底层API优化铺路
     *
     * @date 2021-06-26 10:16
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    private static final long MAX_SEED = (Long.MAX_VALUE >> 1) + 1;

    /**
     * @Description: string转int
     * 极致的字符转换器，比一般转换高出数千倍
     * 本地CPU(Intel(R)_Core(TM)_i7-8700_CPU_@_3.20GHz)
     * Integer.parseInt：21亿次(Integer.MAX_VALUE)转换，耗时：27706ms
     * FastString.parseInt(String s, int l)：21亿次(Integer.MAX_VALUE)转换，耗时：4~7ms
     * 性能差距3,958~6,926.5倍
     * <p>
     * 从CPU层面出发，使用底层ASCII码本质出发进行底层运算，绕开传统思想，吊打一切数据结构的循环遍历的转换结果
     * 直接使用CPU浮点运算能力去转换，可达几乎无损转换,CPU硬件架构浮点运算能力越强此处性能越好
     * 国之重器-天河二号：峰值计算速度5.49京次/每秒
     * 一般CPU也是数十亿次，高端点的的万亿次级别
     * 本地CPU(Intel(R)_Core(TM)_i7-8700_CPU_@_3.20GHz)运算能力：21+亿次(Integer.MAX_VALUE)->5~7ms
     * @params s: 字符串
     * @params l: 长度(为什么要传？只因为不必要的方法调用造成无脑的性能损耗)
     * @date 2020-12-13 14:40
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    public static int parseInt(String s, int l) {
        int v = 0;
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            v = v * 10 + c - 48;
        }
        return v;
    }

    public static int parseInt(String s, int start, int end) {
        int v = 0;
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            v = v * 10 + c - 48;
        }
        return v;
    }

    public static long parseLong(String s, int start, int end) {
        long v = 0;
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            v = v * 10 + c - 48;
        }
        return v;
    }

    /**
     * @Description: substring代替方案
     * @date 2020-12-13 19:55
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    public static int parseInt(String s, int l, int start, int end) {
        int v = 0;
        for (int i = 0; i < l; i++) {
            if (i < start || i > end) {
                continue;
            }
            char c = s.charAt(i);
            v = v * 10 + c - 48;

        }
        return v;
    }

    /**
     * @param s 运单号
     * @return 返回数组，使用数组的高速随机访问性能
     * @Description: 获取Roaring key value算法
     * @date 2020-12-13 16:32
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    public static int[] getRoaringV1(String s) {
        int[] v = new int[2];
        for (int i = 2; i < 6; i++) {
            char c = s.charAt(i);
            v[0] = v[0] * 10 + c - 48;
        }

        for (int j = 6; j < 15; j++) {
            char c = s.charAt(j);
            v[1] = v[1] * 10 + c - 48;
        }
        return v;
    }

    public static int[] getRoaringV2(String s) {
        int[] v = new int[2];
        for (int i = 2; i < 15; i++) {
            char c = s.charAt(i);
            if (i <= 5) {
                v[0] = v[0] * 10 + c - 48;
            }
            if (i > 5) {
                v[1] = v[1] * 10 + c - 48;
            }
        }
        return v;
    }

    /**
     * @param s 运单号
     * @return 返回数组，使用数组的高速随机访问性能
     * @Description: 获取Roaring key value算法
     * 使用switch高效随机寻址特性代替if，再提升10%
     * @date 2020-12-13 16:32
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    public static int[] getRoaringV3(String s) {
        //定义两个长度的数组用于存放roaring路由key与value
        int[] v = new int[2];
        //一次遍历运算完成，最短的时间复杂度O(1)
        for (int i = 2; i < 15; i++) {
            char c = s.charAt(i);
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                    v[0] = v[0] * 10 + c - 48;
                    break;
                default:
                    v[1] = v[1] * 10 + c - 48;
                    break;
            }
        }
        return v;
    }

    /**
     *  国内JMS单号解析
     * @param s 运单号
     * @return 返回数组，使用数组的高速随机访问性能
     * @Description: 获取Roaring key value算法
     * 使用底层最简单的运算、取值、赋值操作，时间复杂度为O(1)，性能已是目前最优解,4~6ms完成20亿级二维数组的单号拆解与反计算
     * 使用if原语性能损耗将近6000倍，使用switch原语比if原主会提高10%左右，依然无法上台面
     * 反汇编机器码分析：前面的方式
     * 1.if原语(if_icmpge)在进行布尔判断时，不满足条件的进行了跳转，引起了回环执行，造成时间复杂度成指数上升n*O(15)
     * 2.switch原语底层采用的是一个数组的跳转表, 使用选择匹配方式，访问性能很高，不像if这样存在回环执行问题，在一定程度上会比if性能高很多
     * <p>
     * 00110000	48	0
     * 00110001	49	1
     * 00110010	50	2
     * 00110011	51	3
     * 00110100	52	4
     * 00110101	53	5
     * 00110110	54	6
     * 00110111	55	7
     * 00111000	56	8
     * 00111001	57	9
     * @date 2020-12-13 16:32
     * @Author: <a href= "765741668@qq.com">yangzonghua</a>
     */
    public static int[] getRoaringV4(String s) {
        //定义两个长度的数组用于存放roaring路由key与value
        int[] v = new int[2];
        v[0] = v[0] * 10 + s.charAt(2) - 48;
        v[0] = v[0] * 10 + s.charAt(3) - 48;
        v[0] = v[0] * 10 + s.charAt(4) - 48;
        v[0] = v[0] * 10 + s.charAt(5) - 48;

        v[1] = v[1] * 10 + s.charAt(6) - 48;
        v[1] = v[1] * 10 + s.charAt(7) - 48;
        v[1] = v[1] * 10 + s.charAt(8) - 48;
        v[1] = v[1] * 10 + s.charAt(9) - 48;
        v[1] = v[1] * 10 + s.charAt(10) - 48;
        v[1] = v[1] * 10 + s.charAt(11) - 48;
        v[1] = v[1] * 10 + s.charAt(12) - 48;
        v[1] = v[1] * 10 + s.charAt(13) - 48;
        v[1] = v[1] * 10 + s.charAt(14) - 48;
        return v;
    }

    /**
     * 国际标快13位单号单号解析
     * @param s
     * @return
     */
    public static int[] getRoaringV5(String s) {
        //定义两个长度的数组用于存放roaring路由key与value
        int[] v = new int[2];
        v[0] = v[0] * 10 + s.charAt(0) - 48;
        v[0] = v[0] * 10 + s.charAt(1) - 48;
        v[0] = v[0] * 10 + s.charAt(2) - 48;
        v[0] = v[0] * 10 + s.charAt(3) - 48;

        v[1] = v[1] * 10 + s.charAt(4) - 48;
        v[1] = v[1] * 10 + s.charAt(5) - 48;
        v[1] = v[1] * 10 + s.charAt(6) - 48;
        v[1] = v[1] * 10 + s.charAt(7) - 48;
        v[1] = v[1] * 10 + s.charAt(8) - 48;
        v[1] = v[1] * 10 + s.charAt(9) - 48;
        v[1] = v[1] * 10 + s.charAt(10) - 48;
        v[1] = v[1] * 10 + s.charAt(11) - 48;
        v[1] = v[1] * 10 + s.charAt(12) - 48;
        return v;
    }

    public static String getRandomId(String prefix){
        return prefix + ThreadLocalRandom.current().nextLong(MAX_SEED) + System.nanoTime();
    }

    public static long getRandomId(){
        return ThreadLocalRandom.current().nextLong(MAX_SEED) + System.nanoTime();
    }

    public static String getTranceId() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    /**
     * 集合分割场景
     * in 查询时最多支持1000条,此方法对该场景的查询进行分割
     *
     * @param list 输入list
     * @param func 执行的查询
     * @param <R>  返回类型
     * @return
     */
    public static <T, R> List<R> batchSelect(List<T> list, int count, Function<List<T>, List<R>> func) {
        List<R> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            boolean flag;
            int i = 0;
            int l = list.size();
            do {
                int j = i + count;
                flag = (j < l);
                List<T> ts = list.subList(i, flag ? j : l);
                List<R> apply = func.apply(ts);
                result.addAll(apply);
                i = j;
            }
            while (flag);
        }
        return result;
    }

    /**
     * 集合分割场景
     *
     * @param list 输入list
     * @param cons 执行的Consumer
     * @return
     */
    public static <T> void batchSelect(List<T> list, int count, Consumer<List<T>> cons) {
        if (CollectionUtil.isNotEmpty(list)) {
            boolean flag;
            int i = 0;
            int l = list.size();
            do {
                int j = i + count;
                flag = (j < l);
                List<T> ts = list.subList(i, flag ? j : l);
                cons.accept(ts);
                i = j;
            }
            while (flag);
        }
    }

    /**
     * 给定的时间进行切割
     *
     * @param startTime
     * @param endTime
     * @param interval  单位 min
     * @return
     * @throws Exception
     */
    public static List<TimeConditionDTO> cutTime(LocalDateTime startTime, LocalDateTime endTime, int interval) throws Exception {
        if (startTime == null || endTime == null || interval == 0) {
            throw new Exception("时间切割方法中,参数存在问题");
        }
        if (!startTime.isBefore(endTime)) {
            throw new Exception("时间切割方法中,开始时间>结束时间");
        }
        List<TimeConditionDTO> list = new ArrayList<>();
        LocalDateTime myEndTime = startTime;
        while (endTime.isEqual(myEndTime)) {
            LocalDateTime myStartTime = myEndTime;
            myEndTime = startTime.plusMinutes(interval);
            if (myEndTime.isAfter(endTime)) {
                myEndTime = endTime;
            }
            String startTimeStr = DateUtils.dateTime2Str(myStartTime);
            String endTimeStr = DateUtils.dateTime2Str(myEndTime);
            list.add(new TimeConditionDTO(startTimeStr, endTimeStr));
        }
        return list;
    }
}
