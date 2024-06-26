package test.greedy;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 贪心
 */
public class LeetCodeCase {

	/**
	 * 122. 买卖股票的最佳时机 II
	 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
	 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
	 * 返回 你能获得的 最大 利润 。
	 * 示例 1：
	 * 输入：prices = [7,1,5,3,6,4]
	 * 输出：7
	 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
	 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
	 * 总利润为 4 + 3 = 7 。
	 * 示例 2：
	 * 输入：prices = [1,2,3,4,5]
	 * 输出：4
	 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
	 * 总利润为 4 。
	 * 示例 3：
	 * 输入：prices = [7,6,4,3,1]
	 * 输出：0
	 * 解释：在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。
	 * 提示：
	 * 1 <= prices.length <= 3 * 104
	 * 0 <= prices[i] <= 104
	 */
	public static class MaxProfit {

		/**
		 * 个人思路:
		 * 按时间顺序的买入和卖出,动作机制其实很简单,但是要将卖出-买入最大化
		 * 按贪心的思路,时刻都要最大化
		 * 1.未买入,则进行买入,如果第二天小,则延迟到第二天买入,依次类推;如果第二天大,则继续往后找到下一次最大的值,进行卖出
		 * ERROR  很难找到合适的办法处理边界问题  *******************
		 *
		 * @param prices
		 * @return
		 */
		public static int maxProfit(int[] prices) {
			int in = prices[0], prefit = 0, max = -1;
			boolean flag = true;
			for (int i = 1; i < prices.length; ++i) {
				// 已买入,则考虑卖出
				if (prices[i] < in) {  // 后面比已买入小,则卖出继续买入更小的,连续的小才行
					if (!flag) {  // 非连续的小,直接卖出
						prefit += prices[i] - in;
					}
					in = prices[i];
				} else {  // 后面比已买入大
					if (prices[i] <= max) {  // 可以卖出
						prefit += max - in;
						in = prices[i];
					} else if (i == prices.length - 1) { // 最后一个还是比较大时,直接卖出
						prefit += prices[i] - in;
					}
					max = prices[i];
					flag = false;
				}
			}
			return prefit;
		}

		/**
		 * 上面那种方式很难处理边界问题,换成每天都有买入和卖出呢?
		 * 这样思路就会非常清晰
		 * <p>
		 * 实际上贪心只注重结果而没有关心过程,对于一直上升的数组;贪心思路中进行了分化的卖出和买入,但实际情况是第一天买入最后一天卖出
		 *
		 * @param prices
		 * @return
		 */
		public static int maxProfitI(int[] prices) {
			int in = prices[0], prefit = 0;
			for (int i = 1; i < prices.length; ++i) {
				// 盈利时:第一天买入,第二天卖出 不盈利时:当天买入当天卖出
				if (prices[i] < in) {  // 不盈利,当天买入即卖出
					in = prices[i];
				} else {  // 盈利,卖出后再买入
					prefit += prices[i] - in;
					in = prices[i];
				}
			}
			return prefit;
		}

		/**
		 * 官方思路,动态规划
		 * dp[i][0] 表示第i天交易玩后手中没有股票,那么状态转移是前一天手中没有股票(dp[i-1][0])或者有股票(dp[i-1][1])
		 * 前一天手中没有股票,那么今天跟前一天一样;前一天手中有股票,那就是今天卖出去了,获得收益price[i]
		 * 状态转移方程:dp[i][0]=max{dp[i-1][0],(dp[i-1][1]+price[i]}
		 * <p>
		 * dp[i][1] 表示第i天交易后手中持有股票,那么状态转移是前一天手中没有股票(dp[i-1][0])或者有股票(dp[i-1][1])
		 * 前一天手中没有股票,那就是今天买入了,减少收益price[i];前一天手中有股票,那就是未做操作
		 * 状态转移方程:dp[i][1]=max{(dp[i-1][0]-price[i]),dp[i-1][1]}
		 * <p>
		 * 初始状态:dp[0][0]=0;dp[0][i]=-price[0];
		 * 依次往后计算最大收益,分别为持有股票和不持有股票的收入,到最后一天处理完后,手中如果还持有股票,则会多出成本;
		 * 所以dp[n-1][0]才是最大收益值
		 *
		 * @param prices
		 * @return
		 */
		public static int maxProfitDp(int[] prices) {
			int n = prices.length, dp0 = 0, dp1 = -prices[0];
			for (int i = 1; i < n; ++i) {
				int max0 = Math.max(dp0, dp1 + prices[i]);
				int max1 = Math.max(dp0 - prices[i], dp1);
				dp0 = max0;
				dp1 = max1;
			}
			return dp0;
		}

		public static void main(String[] args) {
			int i = maxProfitDp(new int[]{1, 2, 3, 4, 5});
			System.out.println(i);
		}
	}

	/**
	 * 240. 搜索二维矩阵 II
	 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
	 * 每行的元素从左到右升序排列。
	 * 每列的元素从上到下升序排列。
	 * 示例 1：
	 * 输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
	 * 输出：true
	 * 示例 2：
	 * 输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
	 * 输出：false
	 * 提示：
	 * m == matrix.length
	 * n == matrix[i].length
	 * 1 <= n, m <= 300
	 * -109 <= matrix[i][j] <= 109
	 * 每行的所有元素从左到右升序排列
	 * 每列的所有元素从上到下升序排列
	 * -109 <= target <= 109
	 */
	public static class SearchMatrix {

		/**
		 * 个人思路:
		 * 每行每列都是递增序列,怎么利用好这个特性来实现快速查找呢?
		 * 能想到的类似广度优先遍历,一层层往外扩散的遍历,当元素 > target 则该元素的右和下不需要再遍历;
		 * 那么从队列中去掉该值,他的所有右边和下面的数据都不会再被遍历
		 *
		 * @param matrix
		 * @param target
		 * @return
		 */
		public static boolean searchMatrix(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int[][] used = new int[m][n];
			int[][] calc = new int[][]{{0, 1}, {1, 0}};
			Deque<int[]> deque = new LinkedList<>(); // 需要遍历的节点队列
			deque.add(new int[]{0, 0});
			used[0][0] = 1;
			while (!deque.isEmpty()) {
				int[] p = deque.poll();
				if (matrix[p[0]][p[1]] < target) {  // 继续遍历,向右和向下
					for (int k = 0; k < calc.length; ++k) {
						int x = p[0] + calc[k][0];
						int y = p[1] + calc[k][1];
						if (x < m && y < n && used[x][y] == 0) {
							deque.add(new int[]{x, y});
							used[x][y] = 1;
						}
					}
				} else if (matrix[p[0]][p[1]] == target) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 充分利用行和列的递增特性,借鉴他人思路,将数组逆时针旋转45度,形成一个杨辉三角.
		 * 每个节点的左子节点都变小,右子节点都变大;这样就可以从顶点一直往下进行查找
		 * 设m=matrix[0].length-1,n=matrix.length-1;
		 * 顶点:[m][n]其左节点为:[m][n-1];右节点为:[m-1][n]
		 * 当target > p 则向右节点搜索;target < p 则向左节点搜索;搜索完成后未找到则不存在
		 */
		public static boolean searchMatrixI(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int i = 0, j = n - 1;
			while (i < m && j < n && i >= 0 && j >= 0) {
				if (matrix[i][j] < target) {
					i++;
				} else if (matrix[i][j] > target) {
					j--;
				} else return true;
			}
			return false;
		}

		/**
		 * 二分查找法
		 * 由于每行每列都是递增,那么对每行进行二分查找
		 * 时间复杂度:O(nlog n)
		 * 空间负责度:O(1)
		 *
		 * @param matrix
		 * @param target
		 * @return
		 */
		public static boolean searchMatrixII(int[][] matrix, int target) {
			for (int i = 0; i < matrix.length; ++i) {
				// 每一行进行搜索
				int s = 0, e = matrix[0].length - 1;
				while (s <= e) {
					int pos = s + (e - s) / 2;
					if (matrix[i][pos] < target) {
						s = pos + 1;
					} else if (matrix[i][pos] > target) {
						e = pos - 1;
					} else return true;
				}
			}
			return false;
		}

		public static void main(String[] args) {
			boolean b = searchMatrixII(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 6);
			System.out.println(b);
		}
	}

	/**
	 * 11. 盛最多水的容器
	 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
	 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
	 * 返回容器可以储存的最大水量。
	 * 说明：你不能倾斜容器。
	 * 示例 1：
	 * 输入：[1,8,6,2,5,4,8,3,7]
	 * 输出：49
	 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
	 * 示例 2：
	 * 输入：height = [1,1]
	 * 输出：1
	 * 提示：
	 * n == height.length
	 * 2 <= n <= 105
	 * 0 <= height[i] <= 104
	 */
	public static class MaxArea {

		/**
		 * 个人思路:
		 * 按照常规思路,先确认水池的左边,然后逐次遍历右边,计算出最大的容积
		 * 最少找出最大的容积值
		 * 时间按复杂度O(n^2) 有很多重复计算,如何减少时间复杂度呢?
		 */
		public static int maxArea(int[] height) {
			int p = 0;
			int max = Integer.MIN_VALUE;
			for (; p < height.length - 1; ++p) {
				int q = p + 1;
				for (; q < height.length; ++q) {
					max = Math.max(max, (q - p) * Math.min(height[p], height[q]));
				}
			}
			return max;
		}

		/**
		 * 两个指针一直往内进行移动,谁比较小就向内移动谁
		 *
		 * @param height
		 * @return
		 */
		public static int maxAreaI(int[] height) {
			int p = 0, q = height.length - 1, max = Integer.MIN_VALUE;
			while (p < q) {
				max = Math.max(max, (q - p) * Math.min(height[p], height[q]));
				if (height[p] < height[q]) {
					p++;
				} else {
					q--;
				}
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(maxAreaI(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
		}
	}

	/**
	 * 179. 最大数
	 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
	 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
	 * 示例 1：
	 * 输入：nums = [10,2]
	 * 输出："210"
	 * 示例 2：
	 * 输入：nums = [3,30,34,5,9]
	 * 输出："9534330"
	 * 提示：
	 * 1 <= nums.length <= 100
	 * 0 <= nums[i] <= 109
	 */
	public static class LargestNumber {

		/**
		 * 个人思路:
		 * 1.将整数转换为字符串,然后按首位大小倒序排列;首位相同的在依次比较后面的位置
		 * 2.位数不同的比较,例如 4,42 需要将缺失的位数补成'4';4,46 同理,只补一次,后面缺失的位数不用处理
		 * <p>
		 * 将首位1-9的元素全部存放在一起,然后顺序处理不同首位的数字
		 *
		 * @param nums
		 * @return
		 */
		public static String largestNumber(int[] nums) {
			// index 0-8 存在首位是1-9的数字
			Map<Integer, List<String>> map = new HashMap<>();
			for (int i = 0; i < nums.length; ++i) {
				String s = String.valueOf(nums[i]);
				int key = s.charAt(0) - '0';
				if (!map.containsKey(key)) {
					List<String> list = new ArrayList<>();
					list.add(s);
					map.put(key, list);
				} else {
					map.get(key).add(s);
				}
			}
			Map<String, String> map1 = new HashMap<>();
			StringBuilder sb = new StringBuilder();
			// 组成字符串
			map.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByKey(Comparator.comparingInt(x -> (int) x).reversed()))
					.forEach(entry -> {
						List<String> list = entry.getValue();
						// 补成同样的位数,时间复杂度太高了,后续可以使用冒泡的思路
						int max = list.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(String::length))).orElse("").length();
						list = list.stream().map(x -> {
							String newX = "";
							for (int i = 0; i < max - x.length(); ++i) {
								newX = x + entry.getKey();
							}
							if (!newX.isEmpty()) {
								// 如果补位之后相同,就出现问题了,没法比出谁在前面比较大 **********
								// 所以通过补位确实没法知道谁排在前面
								map1.put(newX, x);
							} else {
								newX = x;
							}
							return newX;
						}).collect(Collectors.toList());
						list.sort(Comparator.comparingInt(x -> Integer.parseInt((String) x)).reversed());
						String str = list.stream().map(x -> map1.get(x) == null ? x : map1.get(x))
								.collect(Collectors.joining(""));
						sb.append(str);
					});
			return sb.toString();
		}

		/**
		 * 官方思路:
		 * x Θ y = max{x*s(y)+y, y*s(x)+x}
		 * s(x) 表示大于(非负整数x)的最小的10的整数次幂 x=82,y=45; s(82) =10^2; s(45)=10^2;
		 * x*s(y)+y = 82*100+45=8245; y*s(x)+x = 45*100+82=4582;
		 * <p>
		 * x=8,y=45;x*s(y)+y = 8*100+45=845;y*s(x)+x =45*10+8=458;
		 *
		 * @param nums
		 * @return
		 */
		public static String largestNumberOfficial(int[] nums) {
			int n = nums.length;
			Long[] arr = new Long[n];
			for (int i = 0; i < n; ++i) {
				arr[i] = (long) nums[i];
			}
			Arrays.sort(arr, (x, y) -> {
				long sx = 10, sy = 10;
				while (x >= sx) {
					sx = sx * 10;
				}
				while (y >= sy) {
					sy = sy * 10;
				}
				if (-sy * x - y + sx * y + x > 0) {
					return 1;
				} else if (-sy * x - y + sx * y + x < 0) {
					return -1;
				} else return 0;
			});
			if (arr[0] == 0) {
				return "0";
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; ++i) {
				sb.append(arr[i]);
			}
			return sb.toString();
		}

		/**
		 * 通过字符串连接之后走排序
		 *
		 * @param nums
		 * @return
		 */
		public static String largestNumberI(int[] nums) {
			int n = nums.length;
			String[] arr = new String[n];
			for (int i = 0; i < n; ++i) {
				arr[i] = String.valueOf(nums[i]);
			}
			Arrays.sort(arr, (o1, o2) -> {  // 字符串可以直接比较出大小
				String sx = o1 + o2;
				String sy = o2 + o1;
				return sy.compareTo(sx);
			});
			if (arr[0].equals("0")) {
				return "0";
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; ++i) {
				sb.append(arr[i]);
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			String s = largestNumberI(new int[]{1000000000, 1000000000});
			System.out.println(s);
		}
	}
}

