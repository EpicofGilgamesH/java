package twelve.practice;

import java.util.Arrays;

/**
 * @Description 快排和归并排序
 * @Date 2020/7/8 11:12
 * @Created by wangjie
 */
public class SortTwo {

    public static void quickSort(int[] a) {
        quickSortDetail(a, 0, a.length - 1);

    }

    public static void quickSortDetail(int[] a, int p, int r) {
        if (p >= r) return;
        int q = partition(a, p, r);
        quickSortDetail(a, p, q - 1);
        quickSortDetail(a, q + 1, r);
    }

    //获取分区点pivot的位置(一般为末尾元素),将p...r区间的元素进行排序:将>pivot的放在右边,<pivot的放在左边
    //1.空间利用率不好的方法:新建两个数数组,将>pivot的元素放在B数组中,<pivot的元素放在C数组中,最后两个数组放回原数组
    //这种转移数组值的方式实际操作中也相当复杂,应该有更好的处理方式,待思考
    public static int partition(int[] a, int p, int r) {
        int n = r - p + 1;
        int[] b = new int[n];
        int[] c = new int[n];
        int povit = a[r];
        int indexC = 0;
        int indexB = 0;
        //分别放入B,C两个数组中
        for (int i = p; i < r; ++i) {
            if (a[i] < povit) {
                c[indexC] = a[i];
                indexC++;
            } else {
                b[indexB] = a[i];
                indexB++;
            }
        }
        //将B,C两个数组合并到A数组
        int t = -1 + p;
        for (int i = 0; i < indexC; ++i) {
            a[i + p] = c[i];
            t = i + p;
        }
        a[t + 1] = povit;
        int poIndex = t + 1;
        for (int i = 0; i < indexB; ++i) {
            a[t + 2] = b[i];
            t++;
        }
        return poIndex;
    }

    //打印array
    private static void outPutArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
    }

    //2.空间复杂度O(n)的方法
    //我们通过游标 i 把 A[p…r-1]分成两部分。A[p…i-1]的元素都是小于 pivot 的，我们暂且叫它“已处理区间”，A[i…r-1]是“未处理区间”。
    //我们每次都从未处理的区间 A[i…r-1]中取一个元素 A[j]，与 pivot 对比，如果小于 pivot，则将其加入到已处理区间的尾部，也就是 A[i]的位置。
    private static void quick(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }
        int pivot = a[r];
        int i = p, j = p;
        while (j < r) {
            if (a[j] < pivot) {
                swap(a, i, j); // 交换
                i++;
            }
            j++;
        }
        swap(a, i, j); // 交换
        quick(a, p, i - 1);
        quick(a, i + 1, r);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    //从一组数据中心,选择出第K小的数据,要求时间复杂度为O(n)
    private static int select(int[] a, int k, int p, int r) {
        //p+1=k 如果p+1>k 则出现在a[p+1...r]  如果p+1<k 则出现在a[0...p-1]
        int pivot = a[r];
        int i = p, j = p;
        while (j < r) {
            if (a[j] < pivot) {
                swap(a, i, j); // 交换
                i++;
            }
            j++;
        }
        swap(a, i, j); // 交换
        if (i + 1 > k) {
            return select(a, k, p, i - 1);
        } else if (i + 1 < k) {
            return select(a, k, i + 1, r);
        } else {
            return i;
        }
    }


    public static void main(String[] args) {
        int[] a = {8, 10, 2, 3, 6, 1, 5};
//       partition(a, 0, 6);

        /*quickSort(a);
        outPutArray(a);*/
        /*quick(a, 0, a.length - 1);
        outPutArray(a);*/
        int index = select(a, 4, 0, a.length - 1);
        Arrays.sort(a);
        System.out.println(a[index]);
    }
}
