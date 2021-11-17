package thirteen.practice;

/**
 * @Description 计数排序
 * @Date 2020/7/8 17:22
 * @Created by wangjie
 */
public class CountingSort {

    //计数排序只能在数据范围不大的场景中使用,如果数据范围k比要排序的数据大很多,就不适合用计数排序了。
    //而且,计数排序只能给非负整数排序,如果要排序的数据是其他类型,要将其在不改变相对大小的情况下转化为非负整数。
    //计数排序,a是数组,n是数组大小.假设数组中存储的都是非负整数
    //{2,5,3,0,2,3,0,3}
    public static void countingSort(int[] a, int n) {
        if (n <= 1) return;
        //首先得到数组的数组范围,即桶的范围
        int max = a[0];
        for (int i = 1; i < n; ++i) {
            if (a[i] > max)
                max = a[i];
        }
        //创建桶数组,统计每个分数的个数
        //{2,0,2,3,0,1}
        //{2,2,4,7,7,8}
        //{0,1,2,3,4,5}
        int[] b = new int[max + 1];
        for (int i = 0; i < max + 1; ++i) {
            b[i] = 0;
        }
        //遍历a数组,将不同分数的人数统计后放入数组b中
        for (int i = 0; i < n; ++i) {
            b[a[i]]++;
        }
        //将数组b中每个分数段的人数,转换成叠加人数
        for (int i = 1; i < max + 1; ++i) {
            b[i] = b[i] + b[i - 1];
        }
        //遍历数组a,将其通过数组b所得的值存放到新的数组c中
        int[] c = new int[n];
        for (int i = n - 1; i >= 0; --i) {
            c[b[a[i]] - 1] = a[i];
            b[a[i]]--;
        }
        //将数组c拷贝到数组a中
        System.arraycopy(c, 0, a, 0, n);
    }

    //两层for循环,在j的判断和退出循环上有点繁琐,寻找更优的方法
    public static void upperLowerSort(char[] s, int n) {
        //指针a从左往右遍历,当遇到大写字母,则停止遍历
        int j = n - 1;
        for (int i = 0; i < n; ++i) {
            if (i > j) return;
            int ascii = (int) s[i];
            if (ascii >= 65 && ascii <= 90) {
                //指针b从右往左遍历,当遇到小写字母,则停止遍历
                //当两个指针都停止遍历时,进行交换
                for (; j > 0; --j) {
                    int asciis = (int) s[j];
                    if (asciis >= 97 && asciis <= 122) {
                        swap(s, i, j);
                        --j;
                        break;
                    }
                    if (i >= j) return;
                }
            }
        }
    }

    private static void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }

    private static void outPrintArray(int[] array) {
        System.out.println("");
        for (int i = 0; i < array.length; ++i) {
            if (i == array.length - 1) {
                System.out.print(array[i]);
            } else {
                System.out.print(array[i] + ",");
            }
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        /*int[] a = new int[]{2, 5, 3, 0, 2, 3, 0, 3};
        countingSort(a, a.length);
        outPrintArray(a);*/

        char[] c = new char[]{'A', 'a', 'B', 'b', 'C', 'c', 'F', 'O',
                'o', 'd', 'D', 'A', 'a', 'B', 'b', 'C', 'c', 'F', 'O',};
        upperLowerSort(c, c.length);
        System.out.println(c);
    }
}
