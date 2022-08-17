package test.queue;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @Description LeeCode 案例
 * @Date 2022-08-10 17:23
 * @Created by wangjie
 */
public class LeeCodeCase {

	//239. 滑动窗口最大值
	//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
	//
	//返回 滑动窗口中的最大值 。
	//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
	//输出：[3,3,5,5,6,7]
	//解释：
	//滑动窗口的位置                最大值
	//---------------               -----
	//[1  3  -1] -3  5  3  6  7       3
	// 1 [3  -1  -3] 5  3  6  7       3
	// 1  3 [-1  -3  5] 3  6  7       5
	// 1  3  -1 [-3  5  3] 6  7       5
	// 1  3  -1  -3 [5  3  6] 7       6
	// 1  3  -1  -3  5 [3  6  7]      7

	//使用优先级队列思路
	//1.首先窗口从0开始时,第一次拿到的最大值为m
	//2.窗口向后滑动一位,最左侧数被排除,最右侧数被纳入,此事堆顶为最大值
	//依次滑动到末尾

	public static int[] maxSlidingWindow(int[] nums, int k) {
		int l = nums.length;
		//优先级队列中-大顶堆中放入的是数组结构 [num,index]
		PriorityQueue<int[]> pq = new PriorityQueue<>((pair1, pair2) -> pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1]);
		//放入第一次滑动的元素到优先级队列
		for (int i = 0; i < k; ++i) {
			pq.offer(new int[]{nums[i], i});
		}
		int[] result = new int[l - k + 1];
		result[0] = pq.peek()[0];
		for (int i = k; i < l; ++i) {
			pq.offer(new int[]{nums[i], i}); //放入滑动后的窗口末尾的位置元素
			//移除滑动窗口时,最左侧被排查的元素
			while (pq.peek()[1] <= i - k) {
				pq.poll();
			}
			result[i - k + 1] = pq.peek()[0];
		}
		return result;
	}

	/**
	 * 双端队列
	 * 队列存储元素小标,从小到达  队列中存储下标对应的元素值从大到小
	 * 在窗口滑动时,元素值与队尾元素进行比较,如果小,则入队;如果大 则依次弹出小的,并入队
	 * 这样保证窗口滑动时,涉及到的元素最大值都在队列中
	 *
	 * @param nums
	 * @param k
	 * @return
	 */
	public static int[] maxSlidingWindow1(int[] nums, int k) {
		if (nums == null || nums.length < 2) return nums;
		LinkedList<Integer> queue = new LinkedList<>();
		int[] result = new int[nums.length - k + 1];
		for (int i = 0; i < nums.length; ++i) {
			//保证队列中的元素值从大到小
			while (!queue.isEmpty() && nums[i] > nums[queue.peekLast()]) {
				queue.pollLast();//弹出队尾元素
			}
			//添加i元素下标到队尾
			queue.addLast(i);
			//判断当前队列中队首的值是否有效
			if (queue.peekFirst() <= i - k) { //i-k为滑动窗左边第一个被排除的元素索引
				queue.pollFirst();
			}
			//是否满足滑动窗的滑动值
			if (i + 1 >= k) {
				result[i - k + 1] = nums[queue.peekFirst()];
			}
		}
		return result;
	}


	//从一组数据中心,选择出第K小的数据,要求时间复杂度为O(n)
	private static int selectTopK(int[] a, int k, int p, int r) {
		//p+1=k 如果p+1>k 则出现在a[p+1...r]  如果p+1<k 则出现在a[0...p-1]
		int pivot = a[r];
		int i = p, j = p;
		while (j < r) {
			if (a[j] > pivot) {
				swap(a, i, j); // 交换
				i++;
			}
			j++;
		}
		swap(a, i, j); // 交换
		if (i + 1 > k) {
			return selectTopK(a, k, p, i - 1);
		} else if (i + 1 < k) {
			return selectTopK(a, k, i + 1, r);
		} else {
			return a[i];
		}
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}


	public static void main(String[] args) {
		int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
		int[] ints = maxSlidingWindow(nums, 3);
		System.out.println("");

		int[] ints1 = maxSlidingWindow1(nums, 3);

		int i = selectTopK(nums, 2, 0, nums.length - 1);
		System.out.println(i);
	}

}
