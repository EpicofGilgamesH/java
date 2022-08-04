package test.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description LeeCode案例
 * @Date 2022-08-01 17:50
 * @Created by wangjie
 */
public class LeeCodeCase {

	//三数之和
	//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
	//注意：答案中不可以包含重复的三元组。
	public static List<List<Integer>> threeNumSum(int arr[]) {
		int n = arr.length;
		Arrays.sort(arr);
		List<List<Integer>> list = new ArrayList<>();
		//f:first  s:second   t:three
		//枚举f
		for (int f = 0; f < n; ++f) {
			//f在枚举的过程中,如果与前一个重复,则跳过
			if (arr[f] > 0 || (f > 0 && arr[f] == arr[f - 1])) {
				continue;
			}
			//s指针从f+1的位置开始  t在数组的尾端
			int s = f + 1, t = n - 1, target = -arr[f];
			//枚举s
			for (; s < n; ++s) {
				//s向右枚举,如果与前一个重复,则跳过
				if (s > f + 1 && arr[s] == arr[s - 1]) {
					continue;
				}
				//保证s指针在t指针的左侧,且s+t > -f,这时候才左移t,才有可能s+t =-f
				//因为f<=s<=t t左移,s+t减小,才能可能达成 s+t =-f
				while (s < t && arr[s] + arr[t] > target) {
					--t;
				}
				//如果s和t指针重合,不满足题目条件.此时s继续后移 场景:s+t >= -f  s=t, s移动到t的右边,此时 s>t
				//s+t 继续加大,那继续有 s+t >= -f,直接进行下一个f的枚举
				if (s == t) {
					break;
				}
				//满足条件的情况
				if (arr[s] + arr[t] == target) {
					List<Integer> l = new ArrayList<>();
					l.add(arr[f]);
					l.add(arr[s]);
					l.add(arr[t]);
					list.add(l);
				}
			}
		}
		return list;
	}

	/**
	 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素.
	 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素.
	 * <p>
	 * 投票选举-思路
	 * 枚举数组,初始状态下 拟定候选人为arr[0] 投票次数count=0
	 * 往后枚举时,匹配到与候选人相同,则count+1 不同则count-1
	 * 当count=0时,在下一次枚举时替换候选人
	 * 最后得到的候选人即是 major
	 * <p>
	 * 当候选人不是major时,大于n/2的major会投反对票,那么最后count肯定会等于0,该候选人落选
	 * 当候选人是major时,大于n/2的major会投赞成票,那么最后count肯定会>0,最终major会当选
	 */
	public static int major(int[] arr) {
		int n = arr.length, candidate = 0, count = 0;
		for (int i = 0; i < n; ++i) {
			if (count == 0) candidate = arr[i];
			count += arr[i] == candidate ? 1 : -1;
		}
		return candidate;
	}

	/**
	 * 有条件,major必须 > n/2的个数，所以在该数组排序之后,n/2的位置一定是major,无论奇偶
	 */
	public static int major1(int[] arr) {
		Arrays.sort(arr); //一般来说 log n的时间复杂度
		int n = arr.length;
		return arr[n / 2];
	}

	/**
	 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
	 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
	 * 思路:
	 * 1.首先剔除所有的负数,将负数设置成Integer.MAX
	 * 2.出现的正数元素,将其值对应下标位置的元素变成[绝对值相同的负数]
	 * 3.枚举完所有的元素后,相当于数组长度的元素连续下标中,为负数的下标即已出现(下标其实就是出现了那个正数,就记录下来)
	 * 为正数的下标即未出现,+1即得最小正数
	 */
	public static int firstMissingPositive(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			if (arr[i] <= 0) arr[i] = n + 1;
		}
		for (int i = 0; i < n; i++) {
			int num = Math.abs(arr[i]);
			if (num <= n) {
				arr[num - 1] = -Math.abs(arr[num - 1]); //将index-1的值转成其绝对值相同的负数
			}
		}
		for (int i = 0; i < n; i++) {
			if (arr[i] > 0) return i + 1;
		}
		return n + 1;
	}


	public static void main(String[] args) {
		int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
		List<List<Integer>> list = threeNumSum(arr);
		System.out.println(list);

		int[] arr1 = new int[]{7, 7, 5, 7, 5, 1, 5, 7, 5, 5, 7, 7, 7, 7, 7, 7};
		int major = major(arr1);
		System.out.println(major);

		int major1 = major1(arr1);
		System.out.println(major1);

		int[] arr2 = new int[]{8, 7, 9, 11, 12};
		int fmp = firstMissingPositive(arr2);
		System.out.println(fmp);
	}
}
