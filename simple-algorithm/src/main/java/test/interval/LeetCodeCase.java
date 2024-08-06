package test.interval;

import java.util.*;

import static java.lang.String.valueOf;

public class LeetCodeCase {

	/**
	 * 228. 汇总区间
	 * 给定一个  无重复元素 的 有序 整数数组 nums 。
	 * 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。
	 * 列表中的每个区间范围 [a,b] 应该按如下格式输出：
	 * "a->b" ，如果 a != b
	 * "a" ，如果 a == b
	 * 示例 1：
	 * 输入：nums = [0,1,2,4,5,7]
	 * 输出：["0->2","4->5","7"]
	 * 解释：区间范围是：
	 * [0,2] --> "0->2"
	 * [4,5] --> "4->5"
	 * [7,7] --> "7"
	 * 示例 2：
	 * 输入：nums = [0,2,3,4,6,8,9]
	 * 输出：["0","2->4","6","8->9"]
	 * 解释：区间范围是：
	 * [0,0] --> "0"
	 * [2,4] --> "2->4"
	 * [6,6] --> "6"
	 * [8,9] --> "8->9"
	 * 提示：
	 * 0 <= nums.length <= 20
	 * -231 <= nums[i] <= 231 - 1
	 * nums 中的所有值都 互不相同
	 * nums 按升序排列
	 */
	public static class SummaryRanges {

		/**
		 * 个人思路：
		 * 其实本题就是考察找出连续数字的区间 ;无重复有序数组
		 *
		 * @param nums
		 * @return
		 */
		public static List<String> summaryRanges(int[] nums) {
			List<String> result = new ArrayList<>();
			if (nums == null || nums.length == 0) return result;
			int start = 0, end = 0, pre = nums[0];
			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != pre + 1) {
					String range = start != end ? nums[start] + "->" + nums[end] : nums[start] + "";
					result.add(range);
					end = i;
					start = i;
				} else {
					end++;
				}
				pre = nums[i];
			}
			result.add(start != end ? nums[start] + "->" + nums[end] : nums[start] + "");
			return result;
		}

		public static List<String> summaryRangesI(int[] nums) {
			List<String> result = new ArrayList<>();
			if (nums == null || nums.length == 0) return result;
			int start = 0, end = 0;
			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != nums[i - 1] + 1) {
					StringBuilder sb = new StringBuilder();
					String range = start != end ? sb.append(nums[start]).append("->").append(nums[end]).toString() : sb.append(nums[start]).toString();
					result.add(range);
					end = i;
					start = i;
				} else {
					end++;
				}
			}
			StringBuilder sb = new StringBuilder();
			result.add(start != end ? sb.append(nums[start]).append("->").append(nums[end]).toString() : sb.append(nums[start]).toString());
			return result;
		}

		public static void main(String[] args) {
			System.out.println(summaryRanges(new int[]{0, 2, 3, 4, 6, 8, 9}));
		}
	}

	/**
	 * 56. 合并区间
	 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，
	 * 并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
	 * 示例 1：
	 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
	 * 输出：[[1,6],[8,10],[15,18]]
	 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
	 * 示例 2：
	 * 输入：intervals = [[1,4],[4,5]]
	 * 输出：[[1,5]]
	 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
	 * 提示：
	 * 1 <= intervals.length <= 104
	 * intervals[i].length == 2
	 * 0 <= starti <= endi <= 104
	 */
	public static class MergeRanges {

		/**
		 * 个人思路:
		 * 首先对区间进行排序,按照区间的start元素值进行正序
		 * 然后比较end(i) 和start(i+1) 的值,如果end(i) > start(i+1) 则合并i和i+1区间
		 *
		 * @param intervals
		 * @return
		 */
		public static int[][] merge(int[][] intervals) {
			int length = intervals.length;
			// 排序,二位数组,按每行的第一个元素值进行排序
			for (int i = 0; i < length; i++) {
				for (int j = i + 1; j < length; j++) {
					if (intervals[i][0] > intervals[j][0]) {
						swap(intervals, i, j);
					}
				}
			}
			int[] pre = intervals[0];
			List<int[]> result = new ArrayList<>();
			for (int i = 1; i < length; i++) {
				if (intervals[i][0] <= pre[1]) {  // 当end(i) >= start(i+1)时,合并区间,此时新区间为[start(i),max(end(i),end(i+1))]
					pre = new int[]{pre[0], Math.max(intervals[i][1], pre[1])};
				} else {
					result.add(pre);
					pre = intervals[i];
				}
			}
			result.add(pre);
			return result.toArray(new int[result.size()][]);
		}

		private static void swap(int[][] intervals, int i, int j) {
			int temp = intervals[i][0];
			intervals[i][0] = intervals[j][0];
			intervals[j][0] = temp;
			int temp1 = intervals[i][1];
			intervals[i][1] = intervals[j][1];
			intervals[j][1] = temp1;
		}

		/**
		 * 优化细节
		 * 第一个区间先加入到结果数组,然后合并就覆盖;不合并就加入
		 *
		 * @param intervals
		 * @return
		 */
		public static int[][] mergeI(int[][] intervals) {
			// 排序
			Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
			int[][] res = new int[intervals.length + 1][2];
			int[] pre = new int[]{-1, -1};
			int k = 0;
			for (int i = 0; i < intervals.length; i++) {
				if (intervals[i][0] <= pre[1]) {  // 当end(i) >= start(i+1)时,合并区间,此时新区间为[start(i),max(end(i),end(i+1))]
					pre = new int[]{pre[0], Math.max(intervals[i][1], pre[1])};
				} else {
					res[k++] = pre;
					pre = intervals[i];
				}
			}
			res[k++] = pre;
			return Arrays.copyOfRange(res, 1, k);
		}

		/**
		 * 优化2
		 * 生成结果数组之后,有合并的空间,则覆盖调被合并的数组
		 *
		 * @param intervals
		 * @return
		 */
		public static int[][] mergeII(int[][] intervals) {
			Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
			int[][] res = new int[intervals.length][2];
			int idx = -1;
			for (int i = 0; i < intervals.length; i++) {
				if (idx == -1 || intervals[i][0] > res[idx][1]) {
					res[++idx] = intervals[i]; // 不覆盖
				} else {
					res[idx][1] = Math.max(intervals[i][1], res[idx][1]); // 覆盖
				}
			}
			return Arrays.copyOf(res, idx + 1);
		}

		public static void main(String[] args) {
			// System.out.println(Arrays.deepToString(merge(new int[][]{{1, 9}, {2, 5}, {19, 20}, {10, 11}, {12, 20}, {0, 3}, {0, 1}, {0, 2}})));
			System.out.println(Arrays.deepToString(mergeI(new int[][]{{1, 3}})));
		}
	}

	/**
	 * 57. 插入区间
	 * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表 intervals，其中 intervals[i] = [starti, endi]
	 * 表示第 i 个区间的开始和结束，并且 intervals 按照 starti 升序排列。同样给定一个区间 newInterval = [start, end] 表示另一个区间的开始和结束。
	 * 在 intervals 中插入区间 newInterval，使得 intervals 依然按照 starti 升序排列，且区间之间不重叠（如果有必要的话，可以合并区间）。
	 * 返回插入之后的 intervals。
	 * 注意 你不需要原地修改 intervals。你可以创建一个新数组然后返回它。
	 * 示例 1：
	 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
	 * 输出：[[1,5],[6,9]]
	 * 示例 2：
	 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
	 * 输出：[[1,2],[3,10],[12,16]]
	 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
	 * <p>
	 * 提示：
	 * 0 <= intervals.length <= 104
	 * intervals[i].length == 2
	 * 0 <= starti <= endi <= 105
	 * intervals 根据 starti 按 升序 排列
	 * newInterval.length == 2
	 * 0 <= start <= end <= 105
	 */
	public static class InsertRanges {

		/**
		 * 个人思路：
		 * 首先构成一个新的需要合并区间的intervals,然后同56题合并区间一样的思路即可
		 *
		 * @param intervals
		 * @param newInterval
		 * @return
		 */
		public static int[][] insert(int[][] intervals, int[] newInterval) {
			if (intervals.length == 0) return new int[][]{newInterval};
			int k = intervals.length, l = intervals.length;
			for (int i = 0; i < l; i++) {
				if (intervals[i][0] >= newInterval[0]) {
					k = i;
					break;
				}
			}
			List<int[]> arr = new ArrayList<>();
			if (k != 0) {  // 前面有
				for (int i = 0; i < k; i++) {
					arr.add(intervals[i]);
				}
			}
			arr.add(newInterval);
			if (k != l) {
				for (int i = k; i < l; i++) {
					arr.add(intervals[i]);
				}
			}
			int idx = -1;
			int[][] res = new int[l + 1][2];
			for (int[] ar : arr) {
				if (idx == -1 || ar[0] > res[idx][1]) {  // 加入数组
					res[++idx] = ar;
				} else {  // 覆盖数组索引位置的元素
					res[idx][1] = Math.max(res[idx][1], ar[1]);
				}
			}
			return Arrays.copyOf(res, idx + 1);
		}

		/**
		 * 官方思路:
		 * 插入数组为[l,r]
		 * 循环一次原数组,当前current
		 * 1.current 右侧 < 插入的左侧 将current放入结果集中,
		 * 2.current 左侧 > 插入的右侧 将current放入结果集中,
		 * 3.如果都不满足,需要合并成新的区域S,放入结果集中,S应放在第1步和第2步的中间
		 * <p>
		 * 代码非常妙,思路也很清晰明了
		 *
		 * @param intervals
		 * @param newInterval
		 * @return
		 */
		public static int[][] insertI(int[][] intervals, int[] newInterval) {
			List<int[]> res = new ArrayList<>();
			int i = 0;
			while (i < intervals.length && intervals[i][1] < newInterval[0]) {
				res.add(intervals[i++]);
			}
			// S区域 合并一次后newInterval更新,查看是否集合可以往后合并,直到不能合并,那么剩下的数组直接放在后面
			while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
				newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
				newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
				i++;
			}
			res.add(newInterval);
			while (i < intervals.length) {
				res.add(intervals[i++]);
			}
			return res.toArray(new int[res.size()][]);
		}

		public static void main(String[] args) {
			System.out.println(Arrays.deepToString(insertI(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8})));
			System.out.println(Arrays.deepToString(insertI(new int[][]{{1, 5}}, new int[]{6, 8})));
		}
	}

	/**
	 * 452. 用最少数量的箭引爆气球
	 * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend]
	 * 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。
	 * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend，
	 * 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
	 * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
	 * 示例 1：
	 * 输入：points = [[10,16],[2,8],[1,6],[7,12]]
	 * 输出：2
	 * 解释：气球可以用2支箭来爆破:
	 * -在x = 6处射出箭，击破气球[2,8]和[1,6]。
	 * -在x = 11处发射箭，击破气球[10,16]和[7,12]。
	 * 示例 2：
	 * 输入：points = [[1,2],[3,4],[5,6],[7,8]]
	 * 输出：4
	 * 解释：每个气球需要射出一支箭，总共需要4支箭。
	 * 示例 3：
	 * 输入：points = [[1,2],[2,3],[3,4],[4,5]]
	 * 输出：2
	 * 解释：气球可以用2支箭来爆破:
	 * - 在x = 2处发射箭，击破气球[1,2]和[2,3]。
	 * - 在x = 4处射出箭，击破气球[3,4]和[4,5]。
	 * 提示:
	 * 1 <= points.length <= 105
	 * points[i].length == 2
	 * -231 <= xstart < xend <= 231 - 1
	 */
	public static class FindMinArrowShots {

		/**
		 * 个人思路:
		 * 寻找区域是否存交集,存在交集就覆盖成新的交集,不存在交集了则计数+1 (贪心思路保证最后结果正确)
		 * 从下一个区域又开始,
		 *
		 * @param points
		 * @return
		 */
		public static int findMinArrowShots(int[][] points) {
			// 排序
			Arrays.sort(points, (v1, v2) -> v1[0] < v2[0] ? -1 : 1);
			int[] intersection = null;
			int count = 0;
			for (int i = 0; i < points.length; i++) {
				if (intersection == null) {
					intersection = points[i];
				} else { // 是否存在交集
					if (points[i][0] <= intersection[1]) {
						intersection[0] = Math.min(intersection[0], points[i][0]);
						intersection[1] = Math.min(intersection[1], points[i][1]);
					} else { // 不存在交集
						count++;
						intersection = points[i];
					}
				}
			}
			return count + 1;
		}

		/**
		 * 思路优化
		 * 因为已经对区域的左边进行过排序,交集区域只用记录右边即可,其左边更新更新为下一个区域的左边
		 * 其实在上面的个人思路中就有发现这个现象,交集区域不需要记录左边和右边;
		 *
		 * @param points
		 * @return
		 */
		public static int findMinArrowShotsI(int[][] points) {
			Arrays.sort(points, (v1, v2) -> {
				if (v1[0] < v2[0]) return -1;
				else if (v1[0] > v2[0]) return 1;
				else return 0;
			});
			int count = 0, right = points[0][1];
			for (int i = 1; i < points.length; i++) {
				if (points[i][0] <= right) {
					right = Math.min(right, points[i][1]);
				} else { // 不存在交集
					count++;
					right = points[i][1];
				}
			}
			return count + 1;
		}

		public static void main(String[] args) {
			System.out.println(findMinArrowShotsI(new int[][]{{-2147483646, -2147483645}, {2147483646, 2147483647}}));
		}
	}

}
