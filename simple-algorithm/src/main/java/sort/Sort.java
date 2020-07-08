package sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjie
 * @version v1.0
 * @description 排序算法
 * @date 2020/3/11 10:36
 */
public class Sort {

    /**
     * 选择排序
     * 思路:
     * 现有数组 int [] array 中有n个数据;
     * 1.第一次排序在array[0]~array[n-1]中逐一比较出最小的元素，然后将该元素与array[0]进行交换（交换方式有两种）
     * 2.第二次排序在array[1]~array[n-1]中逐一比较出最小的元素，然后将该元素与array[1]进行交换
     * i.通项式 第i次排序在array[i]~array[n-1]中逐一比较出最小的元素，然后将该元素与array[i]进行交换,直到i=n-1,即最后只剩一个元素。
     *
     * @param int数组
     * @author wangjie
     * @date 11:25 2020/3/11
     */
    public static void selectSort(int[] array) {
        int exchangeCount = 0;
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }
            //进行交换
            int temp = array[min];
            array[min] = array[i];
            array[i] = temp;
            exchangeCount++;
        }
        outPutArray(array);
        System.out.println("交换次数:" + exchangeCount);
    }

    /**
     * 冒泡排序
     * 思路:
     * 现有数组 int [] array 中有n个数据;
     * 1.第一轮比较时,第一个元素和第二个元素进行比较,若第一个元素较大则与第二个元素进行位置交换,
     * 一直到第n个元素,此时最大的元素出现在最末尾
     * i.第i轮比较时,第....
     * 一直到第
     * 冒泡排序是通过交换的方式,找出最大(小)的元素
     *
     * @param
     * @return
     * @author wangjie
     * @date 15:17 2020/3/11
     */
    public static void bubbleSort(int[] array) {
        int exchangeCount = 0;
        //每一轮比较时,将最小的值交换到第一位 类似于选择排序的思路
        /*for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    exchangeCount++;
                }
            }
        }*/

        //满交换次数
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    exchangeCount++;
                }
            }
        }
        outPutArray(array);
        System.out.println("交换次数:" + exchangeCount);
    }

    public static void bubbleSort1(int[] array) {
        int n = array.length;
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n; ++i) {
            boolean flag = false;
            for (int j = 0; j < n - 1 - i; ++j) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        outPutArray(array);
        System.out.println("");
    }


    /**
     * 插入排序
     * 思路：
     * 现有数组 int [] array 中有n个数据;
     * 1.分为已排序区和待排序区,第一个元素默认为已排序区,后n-1个元素为未排序区
     * 将array[0]与array[1]进行比较,array[0]>array[1],则array[0]向后移动一位,array[1]插入到array[0]的位置
     * 2.array[0],array[1]为已排序区,array[2]-array[n]为未排序区
     * 将temp=array[2]与array[1]进行比较,array[1]>temp,则array[1]向后移动一位,继续比较,若array[0]>temp,则array[0]向后移动一位
     * 3.array[0]-array[i]为已排序区,array[i+1]-array[n]未排序区
     * 将temp=array[i+1] 与 已排序区的元素逐一进行比较,>temp,则往后移,<temp,则寻找该元素插入点
     *
     * @param
     * @return
     * @author wangjie
     * @date 14:38 2020/4/23
     */
    public static void InsertionSort(int[] array) {

        for (int i = 0; i < array.length; i++) {
            int current = array[i + 1];
            boolean isMin = true;
            for (int j = i + 1; j > 0; j--) {
                //已排序区 array[0-i] 未排序区array[j-n]
                //取未排序区第一个数与排序区数进行逐一比较,>temp右移位置,<temp插入temp,且标识不是最小值,并退出比较
                if (current < array[j]) {
                    array[j + 1] = array[j];
                } else {
                    array[j] = current;
                    isMin = false;
                    break;
                }
            }
            if (isMin)
                array[0] = current;
        }
    }

    public static void insertionSort1(int[] array) {
        int n = array.length;
        if (n <= 1) return;
        for (int i = 1; i < n; ++i) {
            int value = array[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (array[j] > value) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            //找到插入点
            array[j + 1] = value;
        }
        outPutArray(array);
        System.out.println("");
    }


    private static void outPutArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{8, 10, 9, 2, 5, 7, 6, 20, 1, 32};
        selectSort(array);

         /*List<String> list = null;
        System.out.println(list.size());*/
        //前三位
        /*ArrayList<Integer> list = new ArrayList<>();
        String[] pre = {"00", "01", "10", "11", "20"};
        Random random = new Random();
        //后七位
        long start = System.currentTimeMillis();
        Random random1 = new Random();
        for (int i = 0; i < 80000; i++) {
            int a = random.nextInt(pre.length) % (pre.length + 1);
            int b = random1.nextInt(99999999) % (99999999 - 10000 + 1) + 10000;
            String c = String.valueOf(b);
            int len = c.length();
            if (len < 8) {
                int zeroCount = 8 - len;
                c = generateZeroString(zeroCount).concat(c);
            }
            String d = "-".concat(pre[a]).concat(c);
            list.add(Integer.valueOf(d));
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("生成随机数时间：" + (end - start));

        Integer sc = arraySameCount(list);
        System.out.println("重复量：" + sc);

        DecimalFormat format = new DecimalFormat("0.000000");
        String l = format.format((float) sc / list.size() * 100);
        System.out.println("冲突概率：" + l + "%");*/

        int[] array1 = new int[]{8, 10, 9, 2, 5, 7, 6, 20, 1, 32};
        int[] array2 = new int[]{8, 10, 9, 2, 5, 7, 6, 20, 1, 32};
        bubbleSort(array1);
        bubbleSort1(array2);
        int[] array3 = new int[]{8, 10, 9, 2, 5, 7, 6, 20, 1, 32};
        insertionSort1(array3);

        /*Map<Integer, Integer> map = new HashMap<>();
        map.put(1,null);
        map.get(1);*/
    }

    private static String generateZeroString(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result.concat("0");
        }
        return result;
    }


    /**
     * 一个ArrayList中重复元素的比例
     *
     * @return
     */
    private static Integer arraySameCount(List<Integer> list) {
        long start = System.currentTimeMillis();
        Integer sameCount = 0;
        while (list != null && list.size() > 0) {
            final Integer first = list.get(0);
            List<Integer> list1 = list.stream()
                    .filter(x -> !x.equals(first)).collect(Collectors.toList());
            sameCount += list.size() - list1.size() - 1;
            list = list1;
        }
        long end = System.currentTimeMillis();
        System.out.println("计算重复数花费时间：" + (end - start));
        return sameCount;
    }
}
