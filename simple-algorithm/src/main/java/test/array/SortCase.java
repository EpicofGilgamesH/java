package test.array;

/**
 * Leetcode 数组-排序 案例
 */
public class SortCase {

    /**
     * Leetcode 704
     * 题目:给定一个n个元素有序的（升序）整型数组nums 和一个目标值target ，写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。
     * <p>
     * <p>
     * 示例 1:
     * <p>
     * 输入: nums = [-1,0,3,5,9,12], target = 9
     * 输出: 4
     * 解释: 9 出现在 nums 中并且下标为 4
     * <p>
     * <p>
     * 思路:
     * 通过二分查找,每次在指定的范围查找其中位数,然后判断target在左边还是右边,继续查找,直到范围为0
     */
    public static int sort(int[] nums, int t) {
        //如果t<nums[0] || t>nums[length - 1] 直接给出结果
        if (t < nums[0] || t > nums[nums.length - 1]) {
            return -1;
        }
        if (t == nums[0]) return 0;
        if (t == nums[nums.length - 1]) return nums.length - 1;
        int l = nums.length, s = 0, e = l - 1, p;
        while (s <= e) {
            p = s + (e - s) / 2;
            if (nums[p] > t)
                e = p - 1;
            else if (nums[p] < t)
                s = p + 1;
            else return p;
        }
        return -1;
    }


    /**
     * Leetcode 27
     * 给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * <p>
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 思路：
     * 输入： nums = [3,2,2,3], val = 3  输出： 2, nums = [2,2]
     * 注意关键点是'原地'移除,所以需要使用交换,将数组分为剩余区和移除区,双指针 左边指向剩余区的最后,右边指向移除区的最前
     * 交换思路错误***** 能够解出答案,但解题方式很复杂,容易出错
     * <p>
     * 官方思路：快慢指针 slow指向0 fast从0开始向后移动;当fast指针指向值不需要移除时,slow指针指向当前fast指针指向的值,fast指针向后
     * 移动一位,重复以上操作
     */
    public static int removeElement(int[] nums, int val) {
        int l = nums.length, s = 0, e = l - 1, n = l;
        while (s <= e && s <= l - 1) {
            if (nums[s] == val) {  //需要移除的元素
                if (s == e) {
                    n--;
                    break;
                }
                while (e > 0 && e > s && nums[e] == val) { //未处理区的最后一个元素也需要移除时
                    e--;
                    n--;
                }
                if (s > e) {
                    break;
                }
                swap(nums, s, e);
                n--;
                s++;
                e--;
            } else {
                s++;
            }
        }
        return n;
    }

    /**
     * 官方思路
     * 判断,标记的简单思路
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElementOfficial(int[] nums, int val) {
        int f, s = 0, l = nums.length;
        for (f = 0; f < l; f++) {
            if (nums[f] != val) {
                nums[s] = nums[f];
                s++;
            }
        }
        return s;
    }

    /**
     * Leetcode 977
     * 题目:给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     * 思路: 原本为已排序数组,分出负数和正数  两个指针分别指向负数最大和正数最小;然后比大小后放入数组
     * <p>
     * 官方思路:通过巧妙的处理,可以不用单独处理边界问题
     */
    public static int[] sort(int[] nums) {
        int l = nums.length, q = l;
        for (int i = 0; i < l; i++) {
            if (nums[i] >= 0) {
                q = i;
                break;
            }
        }
        int p = q - 1;
        int[] r = new int[l];
        int i = 0;
        while (p >= 0 && q < l) {
            int ps = nums[p] * nums[p];
            int qs = nums[q] * nums[q];
            if (ps > qs) {
                r[i++] = qs;
                q++;
            } else {
                r[i++] = ps;
                p--;
            }
        }
        while (p >= 0) {
            r[i++] = nums[p] * nums[p];
            p--;
        }
        while ((q < l)) {
            r[i++] = nums[q] * nums[q];
            q++;
        }
        return r;
    }

    private static void swap(int[] nums, int p, int q) {
        int s = nums[p];
        nums[p] = nums[q];
        nums[q] = s;
    }

    /**
     * 给定一个含有n个正整数的数组和一个正整数 target.
     * <p>
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组[nums_l, nums_l+1, ..., nums_r-1, nums_r] ,并返回其长度。
     * 如果不存在符合条件的子数组，返回 0 。
     * <p>
     * 输入：target = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     * <p>
     * 输入：target = 4, nums = [1,4,4]
     * 输出：1
     * <p>
     * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
     * 输出：0
     * <p>
     * 思路:双指针,分别指向子数组的头(t)和尾(w);尾指针向后移动,并判断其和(s)是否满足
     * 1.当 s < target 时,w向后移动一位 +w值
     * 2.当 s > target 时,t向后移动一位 -t值
     * 3.当 s == target 时,返回w-t
     * <p>
     * 总结:这里应使用滑动窗口的思路,可以自动解决单边指针移动的场景。不然各种场景判断,代码非常繁琐
     * 并且这里看成了 ==target的最小连续子数组元素数量........
     */
    public static int minSubArrayLen(int[] arr, int target) {
        int l = arr.length;
        int m = Integer.MAX_VALUE;
        int t = 0, w = 0, s = arr[0];
        do {
            int c = 0;
            if (s > target) {
                if (w == l - 1) { //w指针已指向数组最后一个元素,此时只有t指针能移动
                    s -= arr[t];
                    t++;
                } else {
                    if (arr[w + 1] > target) { //元素比target大,直接初始化到其下一个index
                        t = w + 1;
                        w = w + 1;
                    } else if (arr[w + 1] < target) {
                        if (t == w) {
                            t++;
                            w++;
                            s = arr[t];
                        } else {
                            s -= arr[t];
                            t++;
                        }
                    } else return 1;
                }
            } else if (s < target) {
                if (w == l - 1) break;
                w++;
                s += arr[w];
            } else {
                c = w - t + 1; //t,w 同时后移,找下一个答案
                m = Math.min(m, c);
                if (w == l - 1) break;
                s += (arr[w + 1] - arr[t]);
                t++;
                w++;

            }
        } while (t <= l - 1 || w <= l - 1);
        return m == Integer.MAX_VALUE ? 0 : m;
    }

    /**
     * 螺旋矩阵 II Leetcode 59
     * //给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     * //输入：n = 3
     * //输出：[[1,2,3],[8,9,4],[7,6,5]]
     * //
     * 思路:按照选择的方式将数据填充到数组
     **/
    public static int[][] generateMatrix(int n) {
        int v = 1, i = 0, j = 0, m = n * n, a = 0;
        int[][] arr = new int[n][n];
        while (v <= m) {
            //右

            while (j < n - a) {
                arr[i][j] = v++;
                j++;
            }
            i++;
            j--;
            //下
            while (i < n - a) {
                arr[i][j] = v++;
                i++;
            }
            j--;
            i--;
            //左
            while (j >= a) {
                arr[i][j] = v++;
                j--;
            }
            //上
            j++;
            i--;
            while (i >= a + 1) {
                arr[i][j] = v++;
                i--;
            }
            j++;
            i++;
            a++;
        }
        return arr;
    }

    /**
     * 官方思路非常清晰,设定l 左 r 右 t 顶部 b 顶部 四个参数,比数组的i,j索引要更加的清晰明了
     *
     * @param n
     * @return
     */
    public static int[][] officialGenerateMatrix(int n) {
        int l = 0, r = n - 1, t = 0, b = n - 1;
        int[][] arr = new int[n][n];
        int v = 1, m = n * n;
        while (v <= m) {
            for (int i = l; i <= r; i++) {  //left to right
                arr[t][i] = v++;
            }
            t++;

            for (int i = t; i <= b; i++) { //top to button
                arr[i][r] = v++;
            }
            r--;

            for (int i = r; i >= l; i--) {  //right to left
                arr[b][i] = v++;
            }
            b--;

            for (int i = b; i >= t; i--) {  //button to top
                arr[i][l] = v++;
            }
            l++;
        }
        return arr;
    }


    public static void main(String[] args) {

        int[][] ints = officialGenerateMatrix(5);

        int[] nums3 = new int[]{1, 2, 3, 4, 5,};
        int i2 = minSubArrayLen(nums3, 11);
        System.out.println(i2);


        int[] nums = new int[]{3, 3};
        int sort = sort(nums, 3);
        System.out.println(sort);

        int[] nums1 = new int[]{2, 3, 3};
        int i = removeElement(nums1, 3);
        int i1 = removeElementOfficial(nums1, 3);
        System.out.println(i);
        System.out.println(i1);

        int[] nums2 = new int[]{-5, -3, -2, -1};
        int[] sort1 = sort(nums2);
        System.out.println(sort1);
    }
}
