package test.backtracking;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 回溯算法
 */
public class LeetCodeCase {

	/**
	 * 八皇后放置 回溯-递归
	 */
	public static class EightQueue {

		// 数组的index表示row,值表示column
		private static final int[] arr = new int[8];

		public static void find(int row) {
			if (row == 8) {  // 说明已经到第8行了
				printQueen(arr);
				return;
			}
			// 每一行的皇后都有8个列可以选择放置,但需要判断哪一个列可以放置
			for (int i = 0; i < 8; i++) {
				if (isOk(row, i)) {  // 如果该行找到了位置放置皇后,那么进行下一行的放置
					arr[row] = i;
					find(row + 1);  // 该处传参不能写++row,应该在回溯的过程中,回到原递归栈时,应拿到原栈中的row值,这个row不能+1
				}
			}
		}

		/**
		 * 当前改行改列是否能放置;由于行是从上到下的顺序依次查找的,那么已经放置的行就是已放置条件
		 * 列的查找也是从前往后,前面已经查找过的,即不需要再次查找;所以查找是否能放置的方式是:当前位置,行->向上查找 列->向右查找
		 * 判断行、列、斜线是否已经有皇后了,怎么判断呢?需要一行行往上进行查找,然后判断其每一行的左斜线和右斜线
		 * <p>
		 */
		public static boolean isOk(int row, int column) {
			int leftUpSlash = column - 1, rightUpSlash = column + 1;
			for (int i = row - 1; i >= 0; --i, --leftUpSlash, ++rightUpSlash) {
				if (arr[i] == column) return false; // 往上遍历时,如果该列存在皇后,肯定会有arr[i]==column
				if (leftUpSlash >= 0) {  // 上一行的左边斜线column不能小于0
					if (arr[i] == leftUpSlash) return false;
				}
				if (rightUpSlash < 8) { // 上一行的右边斜线column不能大于7
					if (arr[i] == rightUpSlash) return false;
				}
			}
			return true;
		}

		public static void printQueen(int[] arr) {
			for (int r = 0; r < 8; r++) {
				int c = arr[r];
				for (int i = 0; i < 8; i++) {
					if (i == c) System.out.print("q ");
					else System.out.print("* ");
				}
				System.out.println();
			}
			System.out.println();
		}

		public static void main(String[] args) {
			find(0);
		}
	}

	/**
	 * 51. N 皇后
	 * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
	 * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
	 * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
	 * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
	 * 示例 1：
	 * 输入：n = 4
	 * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
	 * 解释：如上图所示，4 皇后问题存在两个不同的解法。
	 * 示例 2：
	 * 输入：n = 1
	 * 输出：[["Q"]]
	 * 提示：
	 * 1 <= n <= 9
	 * <p>
	 * 直接思考回溯思路,根本没有头绪,先思考八皇后的解题过程
	 */
	public static class SolveNQueens {

		private static int n;
		private static int[] arr; // index表示行号,值表示列号
		private static List<List<String>> list;

		public static List<List<String>> solveNQueens(int r) {
			n = r;
			arr = new int[n];
			list = new ArrayList<>(n);
			find(0);
			return list;
		}

		/**
		 * 个人思路:
		 * 题意要求在n x n 的宫格中,摆放n个皇后,要求在行、列、斜线上只能有一个皇后;然后遍历出所有的情况
		 */
		public static void find(int row) {
			if (row == n) { // 找到一种情况中n个皇后的位置
				List<String> rowList = new ArrayList<>(n);
				for (int i = 0; i < n; i++) {
					int index = arr[i];
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < n; j++) {
						if (j == index) sb.append("Q");
						else sb.append(".");
					}
					rowList.add(sb.toString());
				}
				list.add(rowList);
				return;
			}
			for (int i = 0; i < n; i++) {
				if (canPut(row, i)) {
					arr[row] = i;
					find(row + 1);
				}
			}
		}

		/**
		 * 每一行寻找可以放置的情况
		 */
		public static boolean canPut(int row, int column) {
			int leftUpSlash = column - 1, rightUpSlash = column + 1;
			for (int i = row - 1; i >= 0; --i, --leftUpSlash, ++rightUpSlash) {
				// row这行中,寻找每个column位置是否满足条件
				if (arr[i] == column) return false;
				if (leftUpSlash >= 0) {
					if (arr[i] == leftUpSlash) return false;
				}
				if (rightUpSlash < n) {
					if (arr[i] == rightUpSlash) return false;
				}
			}
			return true; // 说明可以放入到column这个位置
		}

		public static void main(String[] args) {
			List<List<String>> lists = solveNQueens(6);
			System.out.println(lists);
		}
	}

	/**
	 * 52. N 皇后 II
	 * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
	 * <p>
	 * 给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
	 */
	public static class TotalNQueens {
		private static int m;
		private static int[] arr;
		private static int count = 0;

		public static int totalNQueens(int n) {
			m = n;
			arr = new int[n];
			find(0);
			return count;
		}

		public static void find(int row) {
			if (row == m) { // 找到方案
				count++;
			}
			for (int i = 0; i < m; i++) {
				if (canPut(row, i)) {
					arr[row] = i;
					find(row + 1);
				}
			}
		}

		public static boolean canPut(int row, int column) {
			int leftDiagonal = column - 1, rightDiagonal = column + 1;
			for (int i = row - 1; i >= 0; --i, --leftDiagonal, ++rightDiagonal) {
				if (arr[i] == column) return false;
				if (leftDiagonal >= 0 && arr[i] == leftDiagonal) return false;

				if (rightDiagonal < m && arr[i] == rightDiagonal) return false;
			}
			return true;
		}

		public static void main(String[] args) {
			int i = totalNQueens(5);
			System.out.println(i);
		}
	}

	/**
	 * 77. 组合
	 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
	 * 你可以按 任何顺序 返回答案。
	 * 示例 1：
	 * 输入：n = 4, k = 2
	 * 输出：
	 * [
	 * [2,4],
	 * [3,4],
	 * [2,3],
	 * [1,2],
	 * [1,3],
	 * [1,4],
	 * ]
	 * 示例 2：
	 * <p>
	 * 输入：n = 1, k = 1
	 * 输出：[[1]]
	 * 提示：
	 * <p>
	 * 1 <= n <= 20
	 * 1 <= k <= n
	 */
	public static class Combine {

		private static List<List<Integer>> result = new ArrayList<>();

		/**
		 * 个人思路:
		 * 枚举遍历所有的情况
		 * 从[1...n]个数中选出k个数,进行组合
		 * 第一个数选择1
		 * 第二个数选择2
		 * ...
		 * 第k个数选择k;此后第m个数还可以选择 [k+1...n]
		 * 回溯------------------
		 * 第k-1个数选择k
		 * 第k个数选择k+1;此后第n个数还可以选择[k+2...n]
		 * 依次回溯,枚举的过程中选择的数量必须等于k,当小于k时即进行回溯
		 * 也就是说,第k-1个数的枚举范围为[k-1,n-1] 第k个数的枚举范围为[k,n] 第m个数的枚举范围为[m,m+n-k] *****
		 * <p>
		 * 个人想到的思路是对的,但是要进一步画出递归树是必须的,这样才能清晰的写出代码
		 */
		public static List<List<Integer>> combine(int n, int k) {
			// 个人思路总是实现不了第一个元素的回溯?????
			for (int i = 1; i <= n - k + 1; i++) {
				enumerate(n, k, i, new ArrayList<>());  // i为第一个数的位置
			}
			return result;
		}

		public static void enumerate(int n, int k, int m, List<Integer> list) {
			list.add(m);
			if (list.size() == k) {  // 枚举到k个数
				result.add(new ArrayList<>(list));
				return;
			}
			for (int i = m; i < n; i++) {
				enumerate(n, k, i + 1, list);
				// 找到一个符合条件的场景后,会跳回之前的递归栈;list应该回溯
				list.remove(list.size() - 1);
			}
		}


		private static List<List<Integer>> lists = new ArrayList<>();

		public static List<List<Integer>> combineII(int n, int k) {
			dfs(0, n, k, new ArrayList<>());
			return lists;
		}

		/**
		 * 二叉搜索树,前序遍历
		 */
		public static void dfs(int c, int n, int k, List<Integer> path) {
			if (c != 0) path.add(c);
			if (path.size() == k) {  // path怎么回溯
				lists.add(new ArrayList<>(path));
				path.remove(path.size() - 1);
				return;
			}
			// 向下遍历
			for (int i = c + 1; i <= n; i++) {
				dfs(i, n, k, path);
			}
			if (path.size() >= 1) path.remove(path.size() - 1);
		}

		private static List<List<Integer>> lists1 = new ArrayList<>();

		public static List<List<Integer>> combineIII(int n, int k) {
			dfs1(1, n, k, new ArrayList<>());
			return lists1;
		}

		/**
		 * 学习他人思路,需要调整的点
		 * 1.c可以从1开始,不用从0开始么???? 不需要根节点么?如何回溯到2呢?
		 * 2.在遍历当前节点的子节点时;换个思维直接遍历子节点,当前节点忽略,因为没有根节点0
		 * 类似二叉树的深度优先遍历思维
		 *
		 * @param c    开始节点
		 * @param n    最后节点
		 * @param k    遍历路径结束个数
		 * @param path 遍历路径
		 */
		public static void dfs1(int c, int n, int k, List<Integer> path) {
			if (path.size() == k) {  // path怎么回溯
				lists.add(new ArrayList<>(path));
				return;
			}
			// 向下遍历
			for (int i = c; i <= n; i++) {
				path.add(i);
				System.out.println("path:" + path);
				dfs1(i + 1, n, k, path); // 遍历完一个子节点,需要回溯;实际上这里在遍历时只考虑子节点,因为跟节点0本身就不存在
				path.remove(path.size() - 1);
				System.out.println("path:" + path);

			}
		}

		// 递归树的剪枝
		// 例如:n=6,k=4;当已搜索的节点path.size()==1时,那么剩下的3个数,起点最大是4,[4,5,6]
		// 当已搜索的节点path.size()==2时,那么剩下的2个数,起点最大是5 [5,6]
		// 当已搜索的节点path.size()==3时,那么剩下的1个数,起点最大就是6
		// 以上总结:下一次搜索的起点= n - (k- path.size())+1
		public static void dfsIV(int c, int n, int k, Deque<Integer> path, List<List<Integer>> res) {
			if (path.size() == k) {
				res.add(new ArrayList<>(path));
				return;
			}
			for (int i = c; i <= n - (k - path.size()) + 1; i++) {
				path.add(i);
				dfsIV(i + 1, n, k, path, res);
				path.removeLast();
			}
		}

		public static List<List<Integer>> combineIV(int n, int k) {
			List<List<Integer>> res = new ArrayList<>();
			if (k <= 0 || n < k) return res;
			Deque<Integer> path = new ArrayDeque<>();
			dfsIV(1, n, k, path, res);
			return res;
		}

		/**
		 * 转换成选与不选的二叉树
		 * 当选的起点是begin时,满足begin> n-k+1 选择数就终止;需要画图进行理解
		 */
		public static List<List<Integer>> combineV(int n, int k) {
			List<List<Integer>> res = new ArrayList<>();
			if (k <= 0 || n < k) return res;
			dfsVI(1, n, k, new ArrayList<>(k), res);
			return res;
		}

		/**
		 * 起点是c的选与不选搜索树
		 * k为剩余需要选的元素,太妙了,但是总是感觉自己一知半解的
		 */
		public static void dfsV(int c, int n, int k, List<Integer> path, List<List<Integer>> res) {
			if (k == 0) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (c > n - k + 1) {
				return;
			}
			// 不选c,直接递归到下一层,k的值不变
			dfsV(c + 1, n, k, path, res);
			// 选c,递归到下一层,k的值要-1
			path.add(c);
			dfsV(c + 1, n, k - 1, path, res);
			// 回溯当前过程
			path.remove(path.size() - 1);
		}

		public static void dfsVI(int c, int n, int k, List<Integer> path, List<List<Integer>> res) {
			if (k == 0) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (c > n - k + 1) {
				return;
			}
			// 选c,递归到下一层,k的值要-1
			path.add(c);
			dfsVI(c + 1, n, k - 1, path, res);
			// 回溯当前过程
			path.remove(path.size() - 1);
			// 不选c,直接递归到下一层,k的值不变
			dfsVI(c + 1, n, k, path, res);
		}

		public static void main(String[] args) {
			// List<List<Integer>> combine = combine(4, 2);
			List<List<Integer>> lists1 = combineV(5, 3);
			System.out.println(lists1);
		}
	}

	/**
	 * 46.全排列
	 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
	 * <p>
	 * 示例 1：
	 * 输入：nums = [1,2,3]
	 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
	 * 示例 2：
	 * 输入：nums = [0,1]
	 * 输出：[[0,1],[1,0]]
	 * 示例 3：
	 * 输入：nums = [1]
	 * 输出：[[1]]
	 * <p>
	 * 提示：
	 * 1 <= nums.length <= 6
	 * -10 <= nums[i] <= 10
	 * nums 中的所有整数 互不相同
	 */
	public static class Permute {
		/**
		 * 个人思路:
		 * 个人思路，通过画出二叉搜索树，然后找到终止的条件；其中每个节点的子节点遍历范围是借助队列来实现。
		 * 由于叶子节点都是单节点存在，所以递归树只到倒数第二层就终止。
		 * 总感觉这个思路有点繁琐,集合类型反复的转来转去
		 */
		public static List<List<Integer>> permute(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			Deque<Integer> source = new ArrayDeque<>(nums.length);
			for (int i = 0; i < nums.length; i++) {
				source.add(nums[i]);
			}
			dfs(nums.length, path, source, res);
			return res;
		}

		public static void dfs(int n, Deque<Integer> path, Deque<Integer> source, List<List<Integer>> res) {
			if (path.size() == n - 1) {
				List<Integer> re = new ArrayList<>(path);
				re.add(source.getFirst());
				res.add(re);
				return;
			}
			List<Integer> list = new ArrayList<>(source);
			for (int i = 0; i < list.size(); i++) {
				Integer c = list.get(i);
				source.remove(c);
				path.add(c);
				dfs(n, path, source, res);
				path.removeLast();
				source.addLast(c);
			}
		}

		public static List<List<Integer>> permuteI(int[] nums) {
			List<List<Integer>> list = new ArrayList<>();
			if (nums.length <= 0) return list;
			if (nums.length == 1) {
				list.add(Collections.singletonList(nums[0]));
				return list;
			}
			boolean[] use = new boolean[nums.length];
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			dfsI(nums, path, use, list);
			return list;
		}

		/**
		 * 官方思路:
		 * 不使用队列,通过一个数组来记录指定下标的元素是否已经被搜索过了
		 * 终止条件是已经遍历到叶子节点;叶子节点都是单节点(即叶子节点的根节点只有一个子节点)
		 * <p>
		 * 为什么自己思考的思路,只搜索到倒数第二层呢?实际上终止条件是当前节点的子节点范围,然后在for循环中进行遍历;
		 * 所以当叶子节点遍历完后进行回退后,这个节点已经没有子节点可以遍历了,所以实际上是一个优化的点
		 */
		public static void dfsI(int[] nums, Deque<Integer> path, boolean[] use, List<List<Integer>> res) {
			if (path.size() == nums.length) {
				res.add(new ArrayList<>(path));
				return;
			}
			for (int i = 0; i < nums.length; i++) {
				if (!use[i]) {
					path.addLast(nums[i]);
					use[i] = true;
					dfsI(nums, path, use, res);
					path.removeLast(); // 回溯,FILO 栈特性
					use[i] = false;
				}
			}
		}

		public static void main(String[] args) {
			int[] nums = new int[]{1};
			List<List<Integer>> permute = permuteI(nums);
			System.out.println(permute);
		}

	}

	/**
	 * 47. 全排列 II
	 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
	 * 示例 1：
	 * 输入：nums = [1,1,2]
	 * 输出：
	 * [[1,1,2],
	 * [1,2,1],
	 * [2,1,1]]
	 * 示例 2：
	 * 输入：nums = [1,2,3]
	 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
	 * 提示：
	 * 1 <= nums.length <= 8
	 * -10 <= nums[i] <= 10
	 */
	public static class PermuteUnique {

		/**
		 * 有无重复数据应该是一样的解法,要求不重复
		 * 重点在于,当前节点遍历其子节点时,子节点需要去重复;那么在遍历当前节点时,记录已经遍历过的节点,
		 * 当遍历重复节点时,直接跳过
		 */
		public static List<List<Integer>> permuteUnique(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			boolean[] use = new boolean[nums.length];
			dfs(nums, use, path, res);
			return res;
		}

		public static void dfs(int[] nums, boolean[] use, Deque<Integer> path, List<List<Integer>> res) {
			if (path.size() == nums.length) {
				res.add(new ArrayList<>(path));
				return;
			}
			HashSet<Integer> layerUse = new HashSet<>(nums.length); // 每个节点在遍历子节点时,都会创建已经遍历过的节点集合
			for (int i = 0; i < nums.length; i++) {            // 当前节点的子节点个数(遍历当前节点的子节点)
				if (!use[i] && !layerUse.contains(nums[i])) {
					path.addLast(nums[i]);
					use[i] = true;
					layerUse.add(nums[i]);
					dfs(nums, use, path, res);
					path.removeLast();
					use[i] = false;
				}
			}
			layerUse.clear();
		}

		public static List<List<Integer>> permuteUniqueI(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			boolean[] use = new boolean[nums.length];
			Arrays.sort(nums);
			dfsI(nums, use, path, res);
			return res;
		}


		/**
		 * 官方思路,将nums数组进行排序,然后每次当前节点的子节点时,判断是否跟前一个元素是否相同
		 * 重复遍历到子节点的条件 -> 当前节点与上一个节点相同,并且
		 */
		public static void dfsI(int[] nums, boolean[] use, Deque<Integer> path, List<List<Integer>> res) {
			if (path.size() == nums.length) {
				res.add(new ArrayList<>(path));
				return;
			}
			for (int i = 0; i < nums.length; i++) {
				// 我们要达到的目的是,在当前节点遍历其子节点时,过滤掉重复的节点;但是一旦过滤掉重复的节点后,
				// 一次深度优先遍历根本没法到达叶子节点
				// 但我们的重点是,遍历当前节点的子节点时,过滤重复,也就是同一层的for循环中,不能重复****;
				// 那么第一次dfs肯定能到达叶子节点,然后回溯之后,到倒数第二层时,选择叶子节点时,不能与第一次dfs遍历的叶子节点相同,
				// 实际上就是要控制dfs往下一个节点遍历的顺序,起到去重的作用
				if (use[i] || (i > 0 && nums[i] == nums[i - 1] && !use[i - 1])) {
					continue;
				}
				path.addLast(nums[i]);
				use[i] = true;
				dfsI(nums, use, path, res);
				path.removeLast();
				use[i] = false;
			}
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = permuteUnique(new int[]{1, 1, 2});
			System.out.println(lists);
		}
	}

	/**
	 * 39. 组合总和
	 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的
	 * 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
	 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
	 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
	 * 示例 1：
	 * 输入：candidates = [2,3,6,7], target = 7
	 * 输出：[[2,2,3],[7]]
	 * 解释：
	 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
	 * 7 也是一个候选， 7 = 7 。
	 * 仅有这两种组合。
	 * 示例 2：
	 * 输入: candidates = [2,3,5], target = 8
	 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
	 * 示例 3：
	 * 输入: candidates = [2], target = 1
	 * 输出: []
	 * 提示：
	 * 1 <= candidates.length <= 30
	 * 2 <= candidates[i] <= 40
	 * candidates 的所有元素 互不相同
	 * 1 <= target <= 40
	 */
	public static class CombinationSum {

		/**
		 * 个人思路:
		 * 画出搜索树,每个节点在遍历其子节点时,可选取的子节点都是所有元素;递归终止条件是路径的path和 >= 7
		 * 至少一个数字的被选数量不同,则两种组合是不同的;核心点在于如果避免所有数字都相同的重复呢???
		 * 那么得保证每个节点在遍历其子节点时,从其自身开始往后进行遍历**** --->>>
		 * 解决办法是:然后记录当前节点遍历的子节点时遍历的上一个子节点下标 pre;每下一层的子节点遍历;都从其顶节点本身开始
		 */
		public static List<List<Integer>> combinationSum(int[] candidates, int target) {
			List<List<Integer>> res = new ArrayList<>();
			if (candidates == null || candidates.length == 0) return res;
			// Arrays.sort(candidates);
			Deque<Integer> path = new ArrayDeque<>();
			dfs(candidates, path, 0, target, 0, res);
			return res;
		}

		/**
		 * 递归的参数中有值类型,比如int 递归栈在每一个栈函数中为其指定一个不同的副本;而引用类型是同一个副本
		 */
		public static void dfs(int[] nums, Deque<Integer> path, int v, int target, int pre, List<List<Integer>> res) {
			if (v == target) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (v > target) return;
			for (; pre < nums.length; pre++) {
				path.addLast(nums[pre]);
				dfs(nums, path, v + nums[pre], target, pre, res);
				path.removeLast();
			}
		}

		public static List<List<Integer>> combinationSumI(int[] candidates, int target) {
			List<List<Integer>> res = new ArrayList<>();
			if (candidates == null || candidates.length == 0) return res;
			Arrays.sort(candidates);
			Deque<Integer> path = new ArrayDeque<>();
			dfsI(candidates, path, 0, target, 0, res);
			return res;
		}

		/**
		 * 递归搜索树的剪枝思考,当遍历一个节点在子节点时,如果其要遍历的子节点有序且范围为[s...n]
		 * 如果存在 该节点之前的和 V+nums[s]>target;则子节点[s+1...n]都不需要在遍历了
		 */
		public static void dfsI(int[] nums, Deque<Integer> path, int v, int target, int pre, List<List<Integer>> res) {
			if (v == target) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (v > target) return;
			for (; pre < nums.length; pre++) {
				if (v + nums[pre] > target) break;
				path.addLast(nums[pre]);
				dfsI(nums, path, v + nums[pre], target, pre, res);
				path.removeLast();
			}
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = combinationSumI(new int[]{2, 3, 6, 7}, 7);
			System.out.println(lists);
		}
	}

	/**
	 * 40. 组合总和 II
	 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
	 * <p>
	 * candidates 中的每个数字在每个组合中只能使用 一次 。
	 * 注意：解集不能包含重复的组合。
	 * 示例 1:
	 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
	 * 输出:
	 * [
	 * [1,1,6],
	 * [1,2,5],
	 * [1,7],
	 * [2,6]
	 * ]
	 * 示例 2:
	 * 输入: candidates = [2,5,2,1,2], target = 5,
	 * 输出:
	 * [
	 * [1,2,2],
	 * [5]
	 * ]
	 * 提示:
	 * <p>
	 * 1 <= candidates.length <= 100
	 * 1 <= candidates[i] <= 50
	 * 1 <= target <= 30
	 */
	public static class CombinationSum2 {

		/**
		 * 个人思路:
		 * 题意中的重点 1)原集合可以用重复数字 2)每个数字只能使用一次 3)结果中不能有重复的集合
		 * 那么递归搜索树中,当前节点的子节点遍历时,只能遍历其后面的集合;且遍历子节点集合时需要去重
		 */
		public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
			List<List<Integer>> res = new ArrayList<>();
			if (candidates == null || candidates.length == 0) return res;
			Arrays.sort(candidates);
			Deque<Integer> path = new ArrayDeque<>(candidates.length);
			dfs(candidates, path, 0, 0, target, res);
			return res;
		}

		public static void dfs(int[] nums, Deque<Integer> path, int v, int pre, int target, List<List<Integer>> res) {
			if (v == target) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (v > target) return;
			// 当前节点的子节点遍历时,去重操作
			HashSet<Integer> set = new HashSet<>(nums.length);
			for (; pre < nums.length; pre++) {
				int s = v + nums[pre];
				if (s > target) break;
				if (set.contains(nums[pre])) continue;
				path.addLast(nums[pre]);
				set.add(nums[pre]);
				dfs(nums, path, s, pre + 1, target, res);
				path.removeLast();
			}
			set.clear();
		}

		public static List<List<Integer>> combinationSum2I(int[] candidates, int target) {
			List<List<Integer>> res = new ArrayList<>();
			if (candidates == null || candidates.length == 0) return res;
			Arrays.sort(candidates);
			Deque<Integer> path = new ArrayDeque<>(candidates.length);
			dfsI(candidates, path, 0, 0, target, res);
			return res;
		}

		/**
		 * 不使用set来进行去重操作
		 */
		public static void dfsI(int[] nums, Deque<Integer> path, int v, int pre, int target, List<List<Integer>> res) {
			if (v == target) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (v > target) return;
			// 当前节点的子节点遍历时,去重操作
			// 这里for循环用的i是精髓所在,为了保证每个节点的子节点在遍历时不能重复,也就是i在正常遍历时(i>pre),不能与上一个元素相同
			// pre参数是为了确保当前节点的子节点遍历时,不能出现其父节点以上的节点出现过的元素;
			// i参数是为了确保当前节点的子节点遍历时,从第二个子节点开始判断是否与前一个节点元素的值相同;这样就确保了同一层节点在遍历时,不会重复
			for (int i = pre; i < nums.length; i++) {
				int s = v + nums[i];
				if (s > target) break;
				if (i > pre && nums[i] == nums[i - 1]) { // 跟上一个元素一样,则退出
					continue;
				}
				path.addLast(nums[i]);
				dfsI(nums, path, s, i + 1, target, res);
				path.removeLast();
			}
		}

		public static List<List<Integer>> combinationSum2II(int[] candidates, int target) {
			List<List<Integer>> res = new ArrayList<>();
			if (candidates == null || candidates.length == 0) return res;
			Arrays.sort(candidates);
			Deque<Integer> path = new ArrayDeque<>(candidates.length);
			boolean[] use = new boolean[candidates.length];
			dfsII(candidates, path, 0, 0, use, target, res);
			return res;
		}

		/**
		 * 还是可以通过use数组和pre值来进行去重处理
		 */
		public static void dfsII(int[] nums, Deque<Integer> path, int v, int pre, boolean[] use, int target, List<List<Integer>> res) {
			if (v == target) {
				res.add(new ArrayList<>(path));
				return;
			}
			if (v > target) return;
			// 当前节点的子节点遍历时,去重操作
			// 这里for循环用的i是精髓所在,为了保证每个节点的子节点在遍历时不能重复,也就是i在正常遍历时(i>pre),不能与上一个元素相同
			// pre参数是为了确保当前节点的子节点遍历时,不能出现其父节点以上的节点出现过的元素;
			// i参数是为了确保当前节点的子节点遍历时,从第二个子节点开始判断是否与前一个节点元素的值相同;这样就确保了同一层节点在遍历时,不会重复
			for (int i = pre; i < nums.length && v + nums[i] <= target; i++) {
				if (i > 0 && (nums[i] == nums[i - 1] && !use[i - 1])) { // 跟上一个元素一样,则退出
					continue;
				}
				path.addLast(nums[i]);
				use[i] = true;
				dfsII(nums, path, v + nums[i], i + 1, use, target, res);
				path.removeLast();
				use[i] = false;
			}
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = combinationSum2II(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
			System.out.println(lists);
		}
	}

	/**
	 * 78. 子集
	 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
	 * <p>
	 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
	 * 示例 1：
	 * 输入：nums = [1,2,3]
	 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
	 * 示例 2：
	 * 输入：nums = [0]
	 * 输出：[[],[0]]
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 10
	 * -10 <= nums[i] <= 10
	 * nums 中的所有元素 互不相同
	 */
	public static class Subsets {

		/**
		 * 个人思路:
		 * 题意可见 要打印所有的path场景
		 * 遍历条件是 1)每个节点x在遍历其子节点时,其遍历范围是 [x...n],所以需要记录其节点x的index
		 */
		public static List<List<Integer>> subsets(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Deque<Integer> path = new ArrayDeque<>();
			dfs(nums, path, 0, res);
			return res;
		}

		public static void dfs(int[] nums, Deque<Integer> path, int pre, List<List<Integer>> res) {
			res.add(new ArrayList<>(path));
			for (; pre < nums.length; pre++) {
				path.addLast(nums[pre]);
				dfs(nums, path, pre + 1, res);
				path.removeLast();
			}
		}

		/**
		 * 官方思路:迭代法实现子集的枚举
		 * 一个长度为n的数组,其每一个元素ai都可能有两种情况,[在子集中]和[不在子集中]
		 * 用1表示在子集中,用0表示不在子集中;那么用二进制表示,其总位数为n
		 * n=[1,2,3]
		 * 000 {}      0
		 * 001 {3}     1
		 * 010 {2}     2
		 * 011 {2,3}   3
		 * 100 {1}     4
		 * 101 {1,3}   5
		 * 110 {1,2}   6
		 * 111 {1,2,3} 7
		 * <p>
		 * 每个位置上的元素,都可能为0或者1,则枚举了所有情况,所以其子集个数为: 2^n
		 * <p>
		 * 这种方式的是不容易想到的,主要原因是该数组中的每一个元素,都可能有两种情况:[在子集中,不在子集中];
		 * 那么可以用该数组位数的二进制来枚举出所有的情况,这一点不好理解.
		 */
		public static List<List<Integer>> subsetsOfficial(int[] nums) {
			// 枚举n的所有二进制,从0开始
			// 第3位 001  1
			// 第2位 010  2
			// 第1为 100  3
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			int n = (int) Math.pow(2, nums.length);
			for (int i = 0; i < n; i++) {
				// eg 010怎么得到其n位中每位是0还是1呢?
				List<Integer> list = new ArrayList<>();
				for (int j = nums.length - 1; j >= 0; j--) {
					if ((i >> j & 1) == 1) {  // 任何数 &1 运算只取其最后一位是否为1或0
						list.add(nums[nums.length - j - 1]);
					}
				}
				res.add(list);
			}
			return res;
		}

		public static void main(String[] args) {
			/*List<List<Integer>> subsets = subsets(new int[]{1, 2, 3});
			System.out.println(subsets);*/

			List<List<Integer>> lists = subsetsOfficial(new int[]{1, 2, 3});
			System.out.println(lists);
		}
	}

	/**
	 * 90. 子集 II
	 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
	 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
	 * 示例 1：
	 * 输入：nums = [1,2,2]
	 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
	 * 示例 2：
	 * 输入：nums = [0]
	 * 输出：[[],[0]]
	 * 提示：
	 * 1 <= nums.length <= 10
	 * -10 <= nums[i] <= 10
	 */
	public static class SubsetsWithDup {

		/**
		 * 个人思路:
		 * 画出搜索树,注意关键点是:数组元素能重复,但结果集不能重复.此时说明,一个顶节点在遍历子节点时,其子节点需要去重复;
		 * 同一层去重复有两种方式 1: 同一层设定Hash进行去重 2:对元素进行排序,同一层的元素不能与上一个相同
		 */
		public static List<List<Integer>> subsetsWithDup(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Arrays.sort(nums); // 避免当前节点的子节点遍历时与之前的遍历数据重复,需要将原数组进行排序
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			dfs(nums, 0, path, res);
			return res;
		}

		public static void dfs(int[] nums, int pre, Deque<Integer> path, List<List<Integer>> res) {
			res.add(new ArrayList<>(path));
			Set<Integer> set = new HashSet<>(); // 任意一个节点的子节点遍历时,去掉重复元素
			for (; pre < nums.length; pre++) {
				if (set.contains(nums[pre])) continue;
				path.addLast(nums[pre]);
				set.add(nums[pre]);
				dfs(nums, pre + 1, path, res);
				path.removeLast();
			}
			set.clear();
		}

		public static List<List<Integer>> subsetsWithDupI(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Arrays.sort(nums);
			Deque<Integer> path = new ArrayDeque<>(nums.length);
			dfsI(nums, 0, path, res);
			return res;
		}

		/**
		 * 对原数组进行排序,然后对任意一个节点的子节点遍历时,不能与上一个元素相同
		 */
		public static void dfsI(int[] nums, int start, Deque<Integer> path, List<List<Integer>> res) {
			res.add(new ArrayList<>(path));
			for (int i = start; i < nums.length; i++) {
				if (i > start && nums[i] == nums[i - 1]) continue;  // 遍历当前节点的子节点时,从start的位置开始,且不能与上一个遍历过的子节点相同
				path.addLast(nums[i]);
				dfsI(nums, i + 1, path, res);
				path.removeLast();
			}
		}

		/**
		 * 通过二进制位遍历出所有的情况
		 * <p>
		 * 首先二进制位从0开始逐步+1,那么肯定是第一位先选,然后第二位再选,然后再第三位 (从低位开始)
		 * 那么,在判断每一位是否被选中时,也从二进制位第一位开始;此时当第一位和第二位的数相同时,
		 * 比如 1,2,2 这样的情况;当枚举时,二进制位每次+1,那么 001 肯定出现在010的前面一个;
		 * 而101 肯定 出现在110的前面一个.根据题意,后面那种情况需要排除,可以发现排除的都是10的情况;
		 * 如果是 1,2,2,2 这种该情况呢? 0001,0010,0100 需要排除后两种; 1001,1010,1100 也需要排除后两种
		 * 所以可以总结:当枚举到这个数与上个数相等,那么如果上个数二进制位是0,那么被排除,值保留第一位
		 * <p>
		 * 与运算,位移后同位相同则为1不相同则为0
		 */
		public static List<List<Integer>> subsetsWithDupII(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			if (nums == null || nums.length == 0) return res;
			Arrays.sort(nums);
			int n = 1 << nums.length;
			for (int i = 0; i < n; i++) {
				boolean flag = true;
				List<Integer> list = new ArrayList<>(nums.length);
				for (int j = 0; j < nums.length; j++) {
					if (((i >> j) & 1) == 1) {  // 从低位开始
						if (j > 0 && nums[j] == nums[j - 1] && (i >> (j - 1) & 1) == 0) {
							flag = false;
							break;
						}
						list.add(nums[j]);
					}
				}
				if (flag) res.add(list);
			}
			return res;
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = subsetsWithDupII(new int[]{4, 4, 4, 4, 1});
			System.out.println(lists);
		}
	}

	/**
	 * 60. 排列序列
	 * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
	 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
	 * "123"
	 * "132"
	 * "213"
	 * "231"
	 * "312"
	 * "321"
	 * 给定 n 和 k，返回第 k 个排列。
	 * 示例 1：
	 * 输入：n = 3, k = 3
	 * 输出："213"
	 * 示例 2：
	 * 输入：n = 4, k = 9
	 * 输出："2314"
	 * 示例 3：
	 * 输入：n = 3, k = 1
	 * 输出："123"
	 * <p>
	 * 提示：
	 * 1 <= n <= 9
	 * 1 <= k <= n!
	 */
	public static class GetPermutation {

		private static int sk;
		private static String str;

		/**
		 * 个人思路:
		 * use 数组的使用,本题中n个数的排列,不要求顺序.在构造搜索树时,要求当前节点的子节点在遍历时,不能与此分支上的节点相同;
		 * 即整条分支遍历下来,不能有相同的元素;use数组伴随着整个分支的遍历和回溯;那么它记录的是每一个分支的使用元素情况
		 * <p>
		 * 但是我们拿到想要的结果就可以返回了,根本不需要枚举所有的情况;那我们是否可以直接找到第k大的分支呢?
		 * 举例 [1,2,3,4]
		 * 当第一个数选1,则1分支会有3!种小分支;同理选2、3、4 都会有3!种小分支;这样我们可以初步判断k输入那个根节点的分支中
		 * 然后同理,可以一步步确定最后的分支上
		 */
		public static String getPermutation(int n, int k) {
			if (n == 0) return "";
			sk = k;
			Deque<Integer> path = new ArrayDeque<>(n);
			boolean[] use = new boolean[n + 1];
			dfs(1, n, path, use);
			return str;
		}

		public static void dfs(int c, int n, Deque<Integer> path, boolean[] use) {
			if (path.size() == n) {
				sk--;
			}
			if (sk == 0) {  // 如何退出搜索呢????不需要回溯,不行;因为在k>0时需要回溯;k==0时需要立刻终止
				str = join(path);
				sk--;
				return;
			}
			for (int i = 0; i < n; i++) {
				if (use[i]) {
					continue;
				}
				path.addLast(i + 1);
				use[i] = true;
				dfs(c + 1, n, path, use);
				path.removeLast();
				use[i] = false;
			}
		}

		private static String join(Deque<Integer> path) {
			StringBuilder p = new StringBuilder();
			for (Integer i : path) {
				p.append(i);
			}
			return p.toString();
		}

		private static int n_2;
		private static int k_2;
		private static int[] factorial = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800};

		/**
		 * 递归方式
		 */
		public static String getPermutationI(int n, int k) {
			n_2 = n;
			k_2 = k;
			boolean[] use = new boolean[n + 1];
			StringBuilder sb = new StringBuilder();
			dfsI(0, use, sb);
			return sb.toString();
		}

		/**
		 * @param index 递归树的层级
		 * @param use   已搜索数组
		 * @param path  路径字符串
		 */
		public static void dfsI(int index, boolean[] use, StringBuilder path) {
			if (index == n_2) return;
			// 第一次进入的全排列个数,就是n-1的阶乘 index=0
			int v = factorial[n_2 - 1 - index];
			for (int i = 1; i <= n_2; i++) {
				if (use[i]) continue;
				if (v < k_2) {
					k_2 -= v;
					continue;  // 不是这个分支,直接丢弃
				}
				path.append(i);
				use[i] = true;
				dfsI(index + 1, use, path);
				// 第一层子节点遍历完成就结束
				return;
			}
		}

		/**
		 * 计算阶乘
		 */
		public static String printFactorial(int n) {
			String str = "int[] factorial=new int[] {";
			int factorial = 1;
			for (int i = 1; i <= n; i++) {
				factorial = factorial * i;
				str += factorial + ",";
			}
			return str.substring(0, str.length() - 1) + "};";
		}

		public static void main(String[] args) {
			// String permutation = getPermutation(3, 3);
			String permutationI = getPermutationI(3, 3);
			System.out.println(permutationI);

			String s = printFactorial(10);
			System.out.println(s);
		}
	}

	/**
	 * 93. 复原 IP 地址
	 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
	 * <p>
	 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
	 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
	 * 示例 1：
	 * 输入：s = "25525511135"
	 * 输出：["255.255.11.135","255.255.111.35"]
	 * 示例 2：
	 * 输入：s = "0000"
	 * 输出：["0.0.0.0"]
	 * 示例 3：
	 * 输入：s = "101023"
	 * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
	 * 提示：
	 * 1 <= s.length <= 20
	 * s 仅由数字组成
	 */
	public static class RestoreIpAddresses {

		/**
		 * 个人思路:
		 * 搜索树,首先这棵树的层级永远是4,每一次搜索的值只能在[0,255];可以初步通过s的长度来判断每一次搜索值的长度[1-4]之间
		 * 当搜索节点遇见0时,那么这个数只能是0;如果搜索节点的值不在[0,255]直接return;如果搜索层级超过4层,return;如果搜索层级不够4层,则忽略
		 */
		public static List<String> restoreIpAddresses(String s) {
			List<String> list = new ArrayList<>();
			if (s == null) return list;
			if (s.length() < 4 || s.length() > 12) return list;
			// s的长度4-5 可选位数是[1-2] ; s的长度 6-10 可选位数是 [1-3] ; s的长度11以上 s的长度是[2-3]
			int min, max;
			if (s.length() <= 5) {
				min = 1;
				max = 2;
			} else if (s.length() <= 10) {
				min = 1;
				max = 3;
			} else {
				min = 2;
				max = 3;
			}

			Deque<String> path = new ArrayDeque<>();
			List<String> res = new ArrayList<>();
			dfs(s, min, max, 0, path, res);
			return res;
		}

		/**
		 * dfs 感觉还有更加优秀的剪枝,实时判断下一次只能选几位数,也就是实时的计算min和max的值
		 *
		 * @param s     原字符串
		 * @param min   节点遍历子节点时最小位数
		 * @param max   节点遍历子节点时最大位数
		 * @param start 节点遍历子节点时的开始位置
		 * @param path  遍历搜索树分支路径
		 */
		public static void dfs(String s, int min, int max, int start, Deque<String> path, List<String> res) {
			if (path.size() == 4) {
				if (start == s.length())
					res.add(String.join(".", new ArrayList<>(path)));
				return;
			}
			for (int i = min; i <= max; i++) {
				// 切割字符
				if (i + start > s.length()) return;
				String cur = s.substring(start, i + start);
				if (cur.startsWith("0") && cur.length() > 1) return;
				if (Integer.parseInt(cur) > 255) return;
				path.addLast(cur);
				dfs(s, min, max, start + i, path, res);
				path.removeLast();
			}
		}

		public static void main(String[] args) {
			List<String> strings = restoreIpAddresses("101023");
			System.out.println(strings);
		}
	}

	/**
	 * 733. 图像渲染
	 * 有一幅以 m x n 的二维整数数组表示的图画 image ，其中 image[i][j] 表示该图画的像素值大小。
	 * 你也被给予三个整数 sr ,  sc 和 newColor 。你应该从像素 image[sr][sc] 开始对图像进行 上色填充 。
	 * 为了完成 上色工作 ，从初始像素开始，记录初始坐标的 上下左右四个方向上 像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应 四个方向上 像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为 newColor 。
	 * 最后返回 经过上色渲染后的图像 。
	 * 示例 1:
	 * <p>
	 * 输入: image = [[1,1,1],[1,1,0],[1,0,1]]，sr = 1, sc = 1, newColor = 2
	 * 输出: [[2,2,2],[2,2,0],[2,0,1]]
	 * 解析: 在图像的正中间，(坐标(sr,sc)=(1,1)),在路径上所有符合条件的像素点的颜色都被更改成2。
	 * 注意，右下角的像素没有更改为2，因为它不是在上下左右四个方向上与初始点相连的像素点。
	 * 示例 2:
	 * 输入: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, newColor = 2
	 * 输出: [[2,2,2],[2,2,2]]
	 * 提示:
	 * <p>
	 * m == image.length
	 * n == image[i].length
	 * 1 <= m, n <= 50
	 * 0 <= image[i][j], newColor < 216
	 * 0 <= sr < m
	 * 0 <= sc < n
	 */
	public static class FloodFill {

		/**
		 * 个人思路:
		 * 找到初始点[sr,sc]后,然后向四周发散;发散的过程中找到已经修改过的元素
		 * 初始化点,需要发散到[上,左,下,右]四个方向;而后续的每个点则只需要发散两个方向
		 * 上->[上,左] 下->[下,右] 左->[左,下] 右->[右,上] 就可以全部覆盖避免重复发散 该方式错误,会形成无限递归*****
		 * 要是需要有一个二位数组记录 已经遍历过的坐标点
		 */
		public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
			image[sr][sc] = color;
			// 重点是需要记录点坐标
			// 上
			if (sc - 1 >= 0) {
				image[sr][sc - 1] = color;
				dfs(image, sr, sc - 1, color, 1);
			}
			// 下
			if (sc + 1 < image[0].length) {
				image[sr][sc + 1] = color;
				dfs(image, sr, sc + 1, color, 2);
			}
			// 左
			if (sr - 1 >= 0) {
				image[sr - 1][sc] = color;
				dfs(image, sr - 1, sc, color, 3);
			}
			// 右
			if (sr + 1 < image.length) {
				image[sr + 1][sc] = color;
				dfs(image, sr + 1, sc, color, 4);
			}
			return image;
		}

		public static void dfs(int[][] image, int sr, int sc, int color, int v) {
			if (v == 1) { // 上
				if (sc - 1 >= 0) {
					image[sr][sc - 1] = color;
					dfs(image, sr, sc - 1, color, 1);
				}
				if (sr - 1 >= 0) {
					image[sr - 1][sc] = color;
					dfs(image, sr - 1, sc, color, 3);
				}
			} else if (v == 2) { // 下
				if (sc + 1 < image[0].length) {
					image[sr][sc + 1] = color;
					dfs(image, sr, sc + 1, color, 2);
				}
				if (sr + 1 < image.length) {
					image[sr + 1][sc] = color;
					dfs(image, sr + 1, sc, color, 4);
				}
			} else if (v == 3) {  // 左
				if (sr - 1 >= 0) {
					image[sr - 1][sc] = color;
					dfs(image, sr - 1, sc, color, 3);
				}
				if (sc + 1 < image[0].length) {
					image[sr][sc + 1] = color;
					dfs(image, sr, sc + 1, color, 2);
				}
			} else {  // 右
				if (sr + 1 < image.length) {
					image[sr + 1][sc] = color;
					dfs(image, sr + 1, sc, color, 4);
				}
				if (sc - 1 >= 0) {
					image[sr][sc - 1] = color;
					dfs(image, sr, sc - 1, color, 1);
				}
			}
		}

		/**
		 * 通过use数组记录是否已经遍历过的元素
		 * 看了官方的解决思路,这是一道简单题.并不需要去记录是不是已经遍历过,题意是指,其节点的上下左右节点的值相同的才需要发散
		 */
		public static int[][] floodFillI(int[][] image, int sr, int sc, int color) {
			if (image == null) return null;
			boolean[][] use = new boolean[image.length][image[0].length];
			int v = image[sr][sc];
			dfsI(image, sr, sc, color, v, use);
			return image;
		}

		public static void dfsI(int[][] image, int sr, int sc, int color, int v, boolean[][] use) {
			if (use[sr][sc]) return;
			if (image[sr][sc] != v) {
				return;
			}
			image[sr][sc] = color;
			use[sr][sc] = true;
			if (sr + 1 < image.length)
				dfsI(image, sr + 1, sc, color, v, use);
			if (sr - 1 >= 0)
				dfsI(image, sr - 1, sc, color, v, use);
			if (sc + 1 < image[0].length)
				dfsI(image, sr, sc + 1, color, v, use);
			if (sc - 1 >= 0)
				dfsI(image, sr, sc - 1, color, v, use);
		}

		public static int[][] floodFillII(int[][] image, int sr, int sc, int color) {
			if (image == null) return null;
			int v = image[sr][sc];
			if (v != color)
				dfsII(image, sr, sc, color, v);
			return image;
		}

		public static void dfsII(int[][] image, int sr, int sc, int color, int v) {
			if (image[sr][sc] != v) {
				return;
			}
			image[sr][sc] = color;
			if (sr + 1 < image.length)
				dfsII(image, sr + 1, sc, color, v);
			if (sr - 1 >= 0)
				dfsII(image, sr - 1, sc, color, v);
			if (sc + 1 < image[0].length)
				dfsII(image, sr, sc + 1, color, v);
			if (sc - 1 >= 0)
				dfsII(image, sr, sc - 1, color, v);
		}

		// x,y坐标的方式表示原地的上下左右四个位置的坐标
		private static int[] dx = {1, 0, 0, -1};
		private static int[] dy = {0, 1, -1, 0};

		/**
		 * bfs 广度优先遍历
		 *
		 * @param image
		 * @param sr
		 * @param sc
		 * @param color
		 * @return
		 */
		public static int[][] floodFillIII(int[][] image, int sr, int sc, int color) {
			int cur = image[sr][sc];
			if (cur == color) return image;
			// 队列中存放的是坐标点
			Deque<int[]> deque = new LinkedList<>();
			deque.add(new int[]{sr, sc}); // 0 为x坐标; 1 为y坐标
			image[sr][sc] = color;
			while (!deque.isEmpty()) {
				int[] poll = deque.poll();
				// 搜索其上下左右四个坐标点,并记录到队列中
				int x = poll[0], y = poll[1];
				for (int i = 0; i < 4; i++) {
					int mx = x + dx[i], my = y + dy[i];  // 当前节点坐标为原坐标做偏移操作
					if (mx >= 0 && mx < image.length && my >= 0 && my < image[0].length && image[mx][my] == cur) {
						deque.add(new int[]{mx, my});
						image[mx][my] = color;
					}
				}
			}
			return image;
		}

		public static void main(String[] args) {
			int[][] ints = floodFillIII(new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}, 1, 1, 2);
			// int[][] ints = floodFillII(new int[][]{{0, 0, 0}, {0, 0, 0}}, 0, 0, 2);
			System.out.println(Arrays.deepToString(ints));
		}
	}

	//---------------------------------------岛屿问题解析------------------------------------
	// 200,463,695,827

	/**
	 * 200. 岛屿数量
	 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
	 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
	 * 此外，你可以假设该网格的四条边均被水包围。
	 * 示例 1：
	 * <p>
	 * 输入：grid = [
	 * ["1","1","1","1","0"],
	 * ["1","1","0","1","0"],
	 * ["1","1","0","0","0"],
	 * ["0","0","0","0","0"]
	 * ]
	 * 输出：1
	 * 示例 2：
	 * <p>
	 * 输入：grid = [
	 * ["1","1","0","0","0"],
	 * ["1","1","0","0","0"],
	 * ["0","0","1","0","0"],
	 * ["0","0","0","1","1"]
	 * ]
	 * 输出：3
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * m == grid.length
	 * n == grid[i].length
	 * 1 <= m, n <= 300
	 * grid[i][j] 的值为 '0' 或 '1'
	 */
	public static class NumIslands {

		/**
		 * 个人思路:
		 * 从题意理解来看,本题最容易联想到的就是深度优先遍历;将网格中的某个坐标节点看作二叉树的顶节点;
		 * 那么它的上下左右四个节点就是其下级节点.当然网格的遍历要注意重复遍历和边界问题.
		 * 当遍历过是,给该节点做个标记;边做边思考,怎么才能计算出岛屿的个数...
		 * 当前遍历过程中,如果周围都不是岛屿则退出了,怎么计算有多少块岛屿呢?
		 * 首先,我们要找到岛屿,必须是递归搜索终止时(子节点全是0)才能得到;而怎么才能找到所有的岛屿呢?找到一个岛屿之后继续找下一个岛屿
		 * 将从那个节点开始呢?如果避免遗漏?官方解题中把每一个节点作为顶点进行遍历,保证不会遗漏场景,是否还有更好的方法呢?
		 */
		public static int numIslands(char[][] grid) {
			int count = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == '1') { // 找到为'1'的节点做深度优先遍历
						dfs(grid, i, j);
						count++;
					}
				}
			}
			return count;
		}

		public static void dfs(char[][] grid, int sr, int sc) {
			// 首先判断其是否在网格中,先遍历进来再判断边界,思路会清晰很多
			if (sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) {
				return;
			}
			if (grid[sr][sc] != '1') { // 不是岛屿 返回
				return;
			}
			grid[sr][sc] = '2'; // 遍历过节点做上标记
			// 遍历其上下左右
			dfs(grid, sr + 1, sc);
			dfs(grid, sr - 1, sc);
			dfs(grid, sr, sc + 1);
			dfs(grid, sr, sc - 1);
		}

		/**
		 * bfs 同样的思路,广度优先遍历到所有子节点(相邻坐标)都为'0' 即找到一个岛屿
		 */
		public static int numIslandsI(char[][] grid) {
			int count = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == '1') { // 找到为'1'的节点做深度优先遍历
						bfs(grid, i, j);
						count++;
					}
				}
			}
			return count;
		}

		// x,y坐标的方式表示原地的上下左右四个位置的坐标
		private static int[] dx = {1, 0, 0, -1};
		private static int[] dy = {0, 1, -1, 0};

		public static void bfs(char[][] grid, int sr, int sc) {
			Deque<int[]> deque = new LinkedList<>();
			deque.add(new int[]{sr, sc});
			grid[sr][sc] = '2';
			while (!deque.isEmpty()) {
				int[] cur = deque.poll();
				int x = cur[0], y = cur[1];
				for (int i = 0; i < 4; i++) {
					int mx = x + dx[i], my = y + dy[i];
					if (mx >= 0 && mx < grid.length && my >= 0 && my < grid[0].length && grid[mx][my] == '1') {
						deque.add(new int[]{mx, my});
						grid[mx][my] = 2;
					}
				}
			}
		}

		public static void main(String[] args) {
			// int i = numIslandsI(new char[][]{{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}});
			int i = numIslandsI(new char[][]{{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}});
			System.out.println(i);
		}
	}

	/**
	 * 463. 岛屿的周长
	 * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
	 * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
	 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
	 * 示例 1：
	 * 输入：grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
	 * 输出：16
	 * 解释：它的周长是上面图片中的 16 个黄色的边
	 * 示例 2：
	 * 输入：grid = [[1]]
	 * 输出：4
	 * 示例 3：
	 * 输入：grid = [[1,0]]
	 * 输出：4
	 * 提示：
	 * <p>
	 * row == grid.length
	 * col == grid[i].length
	 * 1 <= row, col <= 100
	 * grid[i][j] 为 0 或 1
	 */
	public static class IslandPerimeter {

		/**
		 * 个人思路:
		 * 根据题意,确定网格中只有一个岛屿,且岛屿的内部没有湖;我们可以通过dfs遍历这个岛屿的边界坐标
		 * 计算周长,确定边界节点,然后累加其边界节点的各个方向的边长。
		 * 关于周长的计算有两点:
		 * 1.到达边界,很显然需要累加一次边长并退出
		 * 2.到达0节点,不要进入该节点的遍历,在判断要遍历的节点是0时,则可以累加一次边长并退出;每次不同的1节点遍历到其相邻的0节点时,
		 * 都有存在一个临界的边长
		 */
		public static int islandPerimeter(int[][] grid) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						return dfs_I(grid, i, j);
					}
				}
			}
			return 0;
		}

		private static int l = 0;

		public static void dfs(int[][] grid, int sr, int sc) {
			// 遍历的节点超出边界或者为0,都需要退出并累加周长
			if ((sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) || grid[sr][sc] == 0) {
				l++;
				return;
			}
			if (grid[sr][sc] == 2) {
				return;
			}
			grid[sr][sc] = 2;
			dfs(grid, sr + 1, sc);
			dfs(grid, sr - 1, sc);
			dfs(grid, sr, sc + 1);
			dfs(grid, sr, sc - 1);
		}

		/**
		 * 带返回值的递归,运行时间稍微快一点
		 */
		public static int dfs_I(int[][] grid, int sr, int sc) {
			if ((sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) || grid[sr][sc] == 0) {
				return 1;
			}
			if (grid[sr][sc] == 2) {
				return 0;
			}
			grid[sr][sc] = 2;
			return dfs_I(grid, sr + 1, sc) +
					dfs_I(grid, sr - 1, sc) +
					dfs_I(grid, sr, sc + 1) +
					dfs_I(grid, sr, sc - 1);
		}

		/**
		 * 查看解题发现还有一个或在哪个,题中明确说了只有一个岛屿,那说明所有1节点都是相邻的
		 * 找到所有的1节点,然后找到所有的接壤边上 周长=节点数*4-接壤边数*2 首先接壤的边上不能计算到周长中,其次接壤的边还计算了2次
		 * 如何计算接壤边的条数呢?
		 * 通过行和列分别计算接壤的边数,同一行接壤则计算[x][y] x固定时y的连续次数;同一列接壤则计算[x][y] y固定时x的连续次数
		 * <p>
		 * 这里要总结下二维数组的定义:grid[x][y] x为行,y为列  如果是一维数组grid[y] y为列,数组行的序列永远是x=0
		 * <p>
		 * 方法总结,计算节点数和接壤边数的方式,时间复杂度O(m*n)  深度优先遍历的时间复杂度同样为O(m*n)
		 * 但是明显dfs的遍历中涉及递归遍历,时间复杂度和空间复杂度都更高;但是当岛屿的节点数很少时,dfs只用遍历岛屿那一块数据就完成了
		 */
		public static int islandPerimeter_I(int[][] grid) {
			int count = 0, edge = 0;
			for (int i = 0; i < grid.length; i++) {
				int preY = Integer.MIN_VALUE;
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						count++;
						// 处理同一行的接壤
						if (preY + 1 == j) {
							edge++;
						}
						preY = j;
					}
				}
			}
			// 目前只能想到两层循环来做
			for (int j = 0; j < grid[0].length; j++) {
				int preX = Integer.MIN_VALUE;
				for (int i = 0; i < grid.length; i++) {
					if (grid[i][j] == 1) {
						// 处理同一列的接壤
						if (preX + 1 == i) {
							edge++;
						}
						preX = i;
					}
				}
			}
			return 4 * count - 2 * edge;
		}

		/**
		 * 参考了评论区的求接壤边数的思路,一次循环就可以得到
		 * 网格中每个为1的节点,查找其右边和下边是否有接壤节点,这样接壤节点数不会重复计算
		 */
		public static int islandPerimeter_II(int[][] grid) {
			int count = 0, edge = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						count++;
						// 计算接壤数
						if (i < grid.length - 1 && grid[i + 1][j] == 1) {
							edge++;
						}
						if (j < grid[0].length - 1 && grid[i][j + 1] == 1) {
							edge++;
						}
					}
				}
			}
			return 4 * count - 2 * edge;
		}

		public static void main(String[] args) {
			int i = islandPerimeter_II(new int[][]{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}});
			// int i = islandPerimeter(new int[][]{{1}});
			// int i = islandPerimeter_I(new int[][]{{1, 0}});
			System.out.println(i);
		}
	}

	/**
	 * 695. 岛屿的最大面积
	 * 给你一个大小为 m x n 的二进制矩阵 grid 。
	 * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
	 * 岛屿的面积是岛上值为 1 的单元格的数目。
	 * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
	 * 示例 1：
	 * <p>
	 * 输入：grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
	 * 输出：6
	 * 解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
	 * 示例 2：
	 * 输入：grid = [[0,0,0,0,0,0,0,0]]
	 * 输出：0
	 * <p>
	 * 提示：
	 * m == grid.length
	 * n == grid[i].length
	 * 1 <= m, n <= 50
	 * grid[i][j] 为 0 或 1
	 */
	public static class MaxAreaOfIsland {

		/**
		 * 个人思路:
		 * 首先该题是在[200. 岛屿数量]这一题基础上的延伸,找出所有的岛屿,然后记录岛屿包含的节点个数,返回最大的岛屿节点个数
		 */
		public static int maxAreaOfIsland(int[][] grid) {
			int max = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						dfs(grid, i, j);
						max = Math.max(max, area);
						area = 0;
					}
				}
			}
			return max;
		}

		private static int area = 0;

		public static void dfs(int[][] grid, int sr, int sc) {
			if (sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) {
				return;
			}
			if (grid[sr][sc] != 1) {
				return;
			}
			grid[sr][sc] = 2;
			area++;
			dfs(grid, sr + 1, sc);
			dfs(grid, sr - 1, sc);
			dfs(grid, sr, sc + 1);
			dfs(grid, sr, sc - 1);
		}

		public static int maxAreaOfIsland_I(int[][] grid) {
			int max = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						int area = dfs_I(grid, i, j);
						max = Math.max(max, area);
					}
				}
			}
			return max;
		}

		/**
		 * 带返回值的递归总是更难处理一些,如何学习写出带返回值的递归方法呢?????
		 */
		public static int dfs_I(int[][] grid, int sr, int sc) {
			if (sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) {
				return 0;
			}
			if (grid[sr][sc] != 1) {
				return 0;
			}
			// 如果当前节点为1 则返回其岛屿的数量;其本身加上其上下左右四个节点的岛屿数量
			grid[sr][sc] = 2;
			return 1 + dfs_I(grid, sr + 1, sc)
					+ dfs_I(grid, sr - 1, sc)
					+ dfs_I(grid, sr, sc + 1)
					+ dfs_I(grid, sr, sc - 1);
		}

		/**
		 * bfs方式,同样的思路,找到为1的节点后,寻找其上下左右的节点放入队列,一直往外延伸性搜索
		 */
		public static int maxAreaOfIsland_II(int[][] grid) {
			int max = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 1) {
						// 存在多个岛屿,每一个都需要单独的bfs搜索,跟dfs思路类似
						int area = bfs(grid, i, j);
						max = Math.max(max, area);
					}
				}
			}
			return max;
		}

		private static int[] dx = {1, 0, 0, -1};
		private static int[] dy = {0, 1, -1, 0};

		public static int bfs(int[][] grid, int sr, int sc) {
			int area = 0;
			Deque<int[]> deque = new LinkedList<>(); // 队列中存放的是节点的坐标
			deque.add(new int[]{sr, sc});
			grid[sr][sc] = 2;
			area++;
			while (!deque.isEmpty()) {
				int[] cur = deque.poll();
				int x = cur[0], y = cur[1];
				for (int i = 0; i < 4; i++) {
					int mx = x + dx[i], my = y + dy[i];
					if (mx >= 0 && mx < grid.length && my >= 0 && my < grid[0].length && grid[mx][my] == 1) {
						deque.add(new int[]{mx, my});
						grid[mx][my] = 2;
						area++;
					}
				}
			}
			return area;
		}

		public static void main(String[] args) {
			int i = maxAreaOfIsland_II(new int[][]{
					{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
					{0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
					{0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
			});
			System.out.println(i);
		}
	}

	/**
	 * 827. 最大人工岛
	 * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
	 * 返回执行此操作后，grid 中最大的岛屿面积是多少？
	 * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
	 * 示例 1:
	 * 输入: grid = [[1, 0], [0, 1]]
	 * 输出: 3
	 * 解释: 将一格0变成1，最终连通两个小岛得到面积为 3 的岛屿。
	 * 示例 2:
	 * 输入: grid = [[1, 1], [1, 0]]
	 * 输出: 4
	 * 解释: 将一格0变成1，岛屿的面积扩大为 4。
	 * 示例 3:
	 * 输入: grid = [[1, 1], [1, 1]]
	 * 输出: 4
	 * 解释: 没有0可以让我们变成1，面积依然为 4。
	 * 提示：
	 * n == grid.length
	 * n == grid[i].length
	 * 1 <= n <= 500
	 * grid[i][j] 为 0 或 1
	 */
	public static class LargestIsland {

		/**
		 * 个人思路:
		 * 题意要求将一个节点0变成1,然后找出最大的岛屿;相当于把0变成1,然后求岛屿面积
		 * 时间复杂度/空间复杂度太高，超时了...
		 */
		public static int largestIsland(int[][] grid) {
			int max = 0;
			boolean flag = false;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] == 0) {
						flag = true;
						// 二维数组的深度拷贝,需要一行行进行拷贝
						int[][] newGrid = new int[grid.length][grid[0].length];
						for (int k = 0; k < grid.length; k++) {
							newGrid[k] = Arrays.copyOf(grid[k], grid[k].length);
						}
						newGrid[i][j] = 1;
						int area = dfs(newGrid, i, j);
						max = Math.max(max, area);
					}
				}
			}
			// 如果没有0,直接计算所有节点个数
			if (!flag) {
				max = grid.length * grid[0].length;
			}
			return max;
		}

		public static int dfs(int[][] grid, int sr, int sc) {
			if (sr < 0 || sr >= grid.length || sc < 0 || sc >= grid[0].length) {
				return 0;
			}
			if (grid[sr][sc] != 1) {
				return 0;
			}
			grid[sr][sc] = 2;
			return 1 + dfs(grid, sr + 1, sc) + dfs(grid, sr - 1, sc) + dfs(grid, sr, sc + 1) + dfs(grid, sr, sc - 1);
		}


		/**
		 * 个人思路的核心点是遍历所有为0的节点,然后将之变成1之后计算该岛屿的面积;这样会出现非常多的重复计算,并且每一次计算要还原二位数组
		 * 官方思路分为两个步骤
		 * 1.对原网格中的每个岛屿进行面积计算,即遍历网格中的每个[1],并且为这个岛屿设定一个唯一与之对应的整数值t;并存储成hash结构 Map[t,area]
		 * 然后同时存储一份该元素与t的对应关系  tag[i][j]=t实际含义是,当前[i][j]点所在的整个岛屿的tag值都是t
		 * 2.对网格中的所有0元素进行遍历,然后找到该元素周围的岛屿面值最
		 */
		public static int largestIslandOfficial(int[][] grid) {
			int n = grid.length, res = 0;
			int[][] tag = new int[n][n];
			Map<Integer, Integer> area = new HashMap<>();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j] == 1 && tag[i][j] == 0) {
						int t = i * n + j + 1;
						area.put(t, dfs(grid, i, j, tag, t));
						res = Math.max(res, area.get(t));
					}
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (grid[i][j] == 0) {
						int z = 1;
						Set<Integer> set = new HashSet<>();
						for (int k = 0; k < 4; k++) {
							int x = i + d[k], y = j + d[k + 1];
							if (!valid(n, x, y) || tag[x][y] == 0 || set.contains(tag[x][y])) {
								continue;
							}
							z += area.get(tag[x][y]);
							set.add(tag[x][y]);
						}
						res = Math.max(res, z);
					}
				}
			}
			return res;
		}

		private static int[] d = {0, -1, 0, 1, 0};

		public static int dfs(int[][] grid, int sr, int sc, int[][] tag, int t) {
			int n = grid.length, res = 1;
			tag[sr][sc] = t;
			for (int i = 0; i < 4; i++) {
				int x1 = sr + d[i], y1 = sc + d[i + 1];
				if (valid(n, sr, sc) && grid[sr][sc] == 1 && tag[sr][sc] == 0) {
					res += dfs(grid, sr, sc, tag, t);
				}
			}
			return res;
		}

		private static boolean valid(int n, int sr, int sc) {
			return sr >= 0 && sr < n && sc >= 0 && sc < n;
		}

		public static void main(String[] args) {
			int i = largestIslandOfficial(new int[][]{
					{1, 0},
					{0, 1}
			});
			System.out.println(i);
		}
	}

	/**
	 * 130. 被围绕的区域
	 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
	 * 示例 1：
	 * 输入：board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
	 * 输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
	 * 解释：被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
	 * 示例 2：
	 * 输入：board = [["X"]]
	 * 输出：[["X"]]
	 * 提示：
	 * m == board.length
	 * n == board[i].length
	 * 1 <= m, n <= 200
	 * board[i][j] 为 'X' 或 'O'
	 */
	public static class Solve {

		/**
		 * 个人思路:
		 * 另一种岛屿的思路,不挨着边界且都被'X'包围的'O'才是岛屿,另一方面说明边界都是'O'
		 * 思路错误****************** 错误的原因是,正向去思考是否有岛屿,然后dfs返回是否岛屿的结果,以搜索过的节点没法返回
		 */
		public static void solve(char[][] board) {
			int m = board.length, n = board[0].length;
			boolean[][] visited = new boolean[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] == 'O' && !visited[i][j]) { // 寻找岛屿
						HashSet<int[]> set = new HashSet<>();
						if (dfs(board, i, j, visited, set)) {
							for (int[] s : set) {
								board[s[0]][s[1]] = 'X';
							}
						}
					}
				}
			}
		}

		/**
		 * dfs
		 *
		 * @param board   原网格
		 * @param sr      横坐标
		 * @param sc      纵坐标
		 * @param visited 已遍历过的坐标点
		 * @param set     本次岛屿遍历过的元素节点
		 * @return 是否为岛屿
		 */
		public static boolean dfs(char[][] board, int sr, int sc, boolean[][] visited, Set<int[]> set) {
			if (sr < 0 || sr >= board.length || sc < 0 || sc >= board[0].length) {
				return false;
			}
			if (visited[sr][sc]) {
				return true;
			}
			if (board[sr][sc] == 'X') {
				return true;
			}
			visited[sr][sc] = true;
			set.add(new int[]{sr, sc});
			return dfs(board, sr - 1, sc, visited, set)
					&& dfs(board, sr, sc - 1, visited, set)
					&& dfs(board, sr + 1, sc, visited, set)
					&& dfs(board, sr, sc + 1, visited, set);

		}

		/**
		 * 官方思路：
		 * 逆向去思考,因为边界上的'O'和与其相连的'O'将不被覆盖,其他的'O'则要覆盖成'X'
		 * 那么就遍历所有边界上的'O'然后dfs遍历其相连的'O'将其变成'A',然后遍历所有网格将剩下的'O'变成'X';
		 * 最后将'A'还原成'O'即可.
		 * 关于DFS的标记问题,遍历过的元素需要被标记,避免重复遍历进入无限循环.当元素值不为'O'则直接退出,当元素值为'O'则标记其为'A'
		 *
		 * @param board
		 */
		public static void solve_Official(char[][] board) {
			int m = board.length, n = board[0].length;
			for (int i = 0; i < m; i++) {
				dfs(board, i, 0);
				dfs(board, i, n - 1);
			}
			for (int i = 0; i < n; i++) {
				dfs(board, 0, i);
				dfs(board, m - 1, i);
			}
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] == 'A') {
						board[i][j] = 'O';
					} else if (board[i][j] == 'O') {
						board[i][j] = 'X';
					}
				}
			}
		}

		/**
		 * dfs
		 *
		 * @param board 二位数组
		 * @param sr    row index
		 * @param sc    column index
		 */
		public static void dfs(char[][] board, int sr, int sc) {
			if (sr < 0 || sr >= board.length || sc < 0 || sc >= board[0].length || board[sr][sc] != 'O') {
				return;
			}
			board[sr][sc] = 'A';
			dfs(board, sr + 1, sc);
			dfs(board, sr - 1, sc);
			dfs(board, sr, sc + 1);
			dfs(board, sr, sc - 1);
		}

		public static void main(String[] args) {
			/*solve(new char[][]{
					{'X', 'X', 'X', 'X'},
					{'X', 'O', 'O', 'X'},
					{'X', 'X', 'O', 'X'},
					{'X', 'O', 'X', 'X'}
			});*/
			char[][] board = {
					{'O', 'O', 'O', 'O', 'X', 'X'},
					{'O', 'O', 'O', 'O', 'O', 'O'},
					{'O', 'X', 'O', 'X', 'O', 'O'},
					{'O', 'X', 'O', 'O', 'X', 'O'},
					{'O', 'X', 'O', 'X', 'O', 'O'},
					{'O', 'X', 'O', 'O', 'O', 'O'}
			};
			solve_Official(board);
			System.out.println("competed.");
		}
	}

	//************************二叉树******************

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}


	/**
	 * 104. 二叉树的最大深度
	 * 给定一个二叉树 root ，返回其最大深度。
	 * <p>
	 * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
	 * 示例 1：
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：3
	 * 示例 2：
	 * <p>
	 * 输入：root = [1,null,2]
	 * 输出：2
	 * 提示：
	 * 树中节点的数量在 [0, 104] 区间内。
	 * -100 <= Node.val <= 100
	 */
	public static class MaxDepth {

		/**
		 * 个人思路:
		 * 通过两种方式实现
		 * 1.使用栈分层遍历,并记录层度
		 * 2.使用二叉树搜索,记录深度
		 * <p>
		 * 二叉树的层序遍历核心是什么呢?栈怎么保证一层一层的去遍历呢?
		 * 最简单的方式是使用两个栈 A,B A放入上一层的节点,解析出来的子节点放入B节点,来回切换使用
		 * 较巧妙的方式是,每一层记录节点数量,在操作栈时,先记录栈中节点的数量,那就是本层需要遍历的节点的数量,遍历完的节点弹出
		 *
		 * @param root
		 * @return
		 */
		public static int maxDepth(TreeNode root) {
			Deque<TreeNode> deque = new LinkedList<>();
			if (root != null) deque.push(root);
			int deep = 0;
			while (!deque.isEmpty()) {  // 循环每进来一次,就是一下层的遍历
				int size = deque.size();
				for (; size > 0; --size) {
					TreeNode node = deque.pop();
					if (node != null && node.left != null) deque.add(node.left);
					if (node != null && node.right != null) deque.add(node.right);
				}
				deep++;
			}
			return deep;
		}

		/**
		 * 二叉树搜索
		 * 深度优先遍历时,中序遍历
		 * root二叉树的深度= Math.max(左子树的深度,右子树的深度)+1
		 * 子节点为空则退出
		 *
		 * @param root
		 * @return
		 */
		public static int maxDepthI(TreeNode root) {
			if (root == null) return 0;
			int l = maxDepthI(root.left);
			int r = maxDepthI(root.right);
			return Math.max(l, r) + 1;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode r_l = new TreeNode(9);
			TreeNode r_r = new TreeNode(20);
			TreeNode n20_l = new TreeNode(15);
			TreeNode n20_r = new TreeNode(7);
			root.left = r_l;
			root.right = r_r;
			r_l.left = n20_l;
			r_r.right = n20_r;
			int i = maxDepthI(root);
			System.out.println(i);
		}

	}

	/**
	 * 113. 路径总和 II
	 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
	 * <p>
	 * 叶子节点 是指没有子节点的节点。
	 * 示例 1：
	 * <p>
	 * <p>
	 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
	 * 输出：[[5,4,11,2],[5,8,4,5]]
	 * 示例 2：
	 * 输入：root = [1,2,3], targetSum = 5
	 * 输出：[]
	 * 示例 3：
	 * <p>
	 * 输入：root = [1,2], targetSum = 0
	 * 输出：[]
	 * 提示：
	 * <p>
	 * 树中节点总数在范围 [0, 5000] 内
	 * -1000 <= Node.val <= 1000
	 * -1000 <= targetSum <= 1000
	 */
	public static class PathSum {

		/**
		 * 个人思路：
		 * 二叉树的搜索,考虑搜索时,记录路径的回溯问题,将匹配到的结果记录下来
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		private static List<List<Integer>> list = new ArrayList<>();

		/**
		 * dfs 深度优先遍历
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
			if (root != null) {
				LinkedList<Integer> depth = new LinkedList<>();
				depth.addLast(root.val);
				treeTraverse(root, targetSum - root.val, depth);
			}
			return list;
		}

		private static void treeTraverse(TreeNode node, int target, LinkedList<Integer> depth) {
			if (node.left == null && node.right == null) { // 叶子节点
				if (target == 0) {
					list.add(new ArrayList<>(depth));
				}
				return;
			}
			if (node.left != null) {
				depth.addLast(node.left.val);
				treeTraverse(node.left, target - node.left.val, depth);
				depth.removeLast();
			}
			if (node.right != null) {
				depth.addLast(node.right.val);
				treeTraverse(node.right, target - node.right.val, depth);
				depth.removeLast();
			}
		}

		public static List<List<Integer>> pathSumI(TreeNode root, int targetSum) {
			LinkedList<Integer> depth = new LinkedList<>();
			treeTraverseI(root, targetSum, depth);
			return list;
		}

		private static void treeTraverseI(TreeNode node, int target, LinkedList<Integer> depth) {
			if (node == null) return;
			depth.addLast(node.val);
			target -= node.val;
			if (node.left == null && node.right == null && target == 0) {
				list.add(new ArrayList<>(depth));
			}
			treeTraverseI(node.left, target, depth);
			treeTraverseI(node.right, target, depth);
			depth.removeLast();
		}


		private static Map<TreeNode, TreeNode> map = new HashMap<>();

		/**
		 * 层序遍历,分层遍历时记录节点路经,并找出符合条件的节点路径;记录不了路径,因为是通过分层遍历出来的,路径只能在找到符合条件的
		 * 叶子节点之后反推出来;这里需要记录当前节点累计的值,使用栈特性 模拟进行值求和计算
		 * 分层好像没有什么作用,只需要遍历到叶子节点,然后计算是否满足条件
		 * 要使用模拟栈,并不是层序遍历 层序遍历和模拟栈 是FIFO和FILO的区别
		 * <p>
		 * 模拟栈深度优先遍历,找到符合条件的叶子节点,然后反向遍历得到结果集
		 * <p>
		 * 直接看了官方的代码,发现并不是这么回事;官方思路还是层序遍历FIFO 然后用一个FIFO的链表结构来存放sum值,该节点如果有左右子节点
		 * 这存放两个相同的sum值,同样是层序的,随着节点的遍历一起进行操作
		 * 理解起来还是有点复杂的
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static List<List<Integer>> pathSumII(TreeNode root, int targetSum) {
			if (root == null) return list;
			Deque<Integer> dequeSum = new LinkedList<>();
			Deque<TreeNode> deque = new LinkedList<>();
			deque.offer(root);
			dequeSum.offer(0);
			while (!deque.isEmpty()) {
				TreeNode node = deque.poll();
				int v = dequeSum.poll() + node.val;
				if (node.left == null && node.right == null) {
					if (v == targetSum) {
						list.add(getList(node));
					}
				} else {
					if (node.left != null) {
						deque.offer(node.left);
						dequeSum.offer(v);
						map.put(node.left, node);
					}
					if (node.right != null) {
						deque.offer(node.right);
						dequeSum.offer(v);
						map.put(node.right, node);
					}

				}
			}
			return list;
		}

		private static List<Integer> getList(TreeNode treeNode) {
			List<Integer> li = new ArrayList<>();
			while (treeNode != null) {
				li.add(treeNode.val);
				treeNode = map.get(treeNode);
			}
			Collections.reverse(li);
			return li;
		}

		public static void main(String[] args) {
			TreeNode n_5 = new TreeNode(5);
			TreeNode n_4 = new TreeNode(4);
			TreeNode n_8 = new TreeNode(8);
			n_5.left = n_4;
			n_5.right = n_8;
			TreeNode n_11 = new TreeNode(11);
			n_4.left = n_11;
			TreeNode n_7 = new TreeNode(7);
			TreeNode n_2 = new TreeNode(2);
			n_11.left = n_7;
			n_11.right = n_2;
			TreeNode n_13 = new TreeNode(13);
			TreeNode n_4_1 = new TreeNode(4);
			n_8.left = n_13;
			n_8.right = n_4_1;
			TreeNode n_5_1 = new TreeNode(5);
			TreeNode n_1 = new TreeNode(1);
			n_4_1.left = n_5_1;
			n_4_1.right = n_1;

			List<List<Integer>> lists = pathSumII(n_5, 22);
			System.out.println(lists);
		}
	}

	/**
	 * 79. 单词搜索
	 * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
	 * <p>
	 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
	 * 示例 1：
	 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
	 * 输出：true
	 * 示例 2：
	 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
	 * 输出：true
	 * 示例 3：
	 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
	 * 输出：false
	 * 提示：
	 * <p>
	 * m == board.length
	 * n = board[i].length
	 * 1 <= m, n <= 6
	 * 1 <= word.length <= 15
	 * board 和 word 仅由大小写英文字母组成
	 * 进阶：你可以使用搜索剪枝的技术来优化解决方案，使其在 board 更大的情况下可以更快解决问题？
	 */
	public static class WordSearch {


		private static boolean flag = false;

		/**
		 * 个人思路:
		 * 回溯搜索,先找到word中的符合第一个字母的元素,然后向其水平和垂直方向进行搜索
		 *
		 * @param board
		 * @param word
		 * @return
		 */
		public static boolean exist(char[][] board, String word) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j] == word.charAt(0)) {  // 第一个字母符合条件,则开始进行搜索
						Deque<Character> deque = new LinkedList<>();
						boolean[][] use = new boolean[board.length][board[0].length];
						isMatch(board, i, j, deque, use, word, 0);
						if (flag) return true;
					}
				}
			}
			return flag;
		}

		private static void isMatch(char[][] board, int m, int n, Deque<Character> deque, boolean[][] use, String word, int index) {
			if (deque.size() == word.length()) {
				flag = true;
				return;
			}
			if (m < 0 || m >= board.length || n < 0 || n >= board[0].length) return;
			if (use[m][n]) return;
			char c = board[m][n];
			if (c != word.charAt(index)) return;
			use[m][n] = true;
			deque.addLast(c);
			isMatch(board, m + 1, n, deque, use, word, index + 1);
			isMatch(board, m - 1, n, deque, use, word, index + 1);
			isMatch(board, m, n + 1, deque, use, word, index + 1);
			isMatch(board, m, n - 1, deque, use, word, index + 1);
			deque.pollLast();
			use[m][n] = false;
		}

		public static boolean existI(char[][] board, String word) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j] == word.charAt(0)) {  // 第一个字母符合条件,则开始进行搜索
						boolean[][] use = new boolean[board.length][board[0].length];
						if (match(board, i, j, use, word, 0)) {
							return true;
						}
					}
				}
			}
			return false;
		}

		private static boolean match(char[][] board, int m, int n, boolean[][] use, String word, int index) {
			if (board[m][n] != word.charAt(index)) {
				return false;
			} else if (index + 1 == word.length()) return true;
			use[m][n] = true;
			int[][] coordinate = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
			boolean result = false;
			for (int[] pos : coordinate) {
				int m1 = m + pos[0], n1 = n + pos[1];
				if (m1 >= 0 && m1 < board.length && n1 >= 0 && n1 < board[0].length) {  // 坐标范围判定
					if (!use[m1][n1]) {
						if (match(board, m1, n1, use, word, index + 1)) {
							result = true;
							break;
						}
					}
				}
			}
			// 如果该元素的水平和垂直方向找不到合适的下一个元素,则回溯到上一个元素,此时use数组在(m,n)点置为false
			use[m][n] = false;
			return result;
		}

		/**
		 * 为什么通过记录匹配字母的数量K的方式无法得到正确的结果呢,最后还得使用Deque的回溯
		 * 后续需要用这种方式实现,空间复杂度更低
		 *
		 * @param args
		 */
		public static void main(String[] args) {
			char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'E', 'S'}, {'A', 'D', 'E', 'E'}};
			boolean flag = existI(board, "ABCESEEEFS");
			// char[][] board = new char[][]{{'A'}};
			// boolean flag = exist(board, "A");
			System.out.println(flag);
		}
	}

	public static class Test {

		public static void main(String[] args) {
			String str = "12-test";
			String[] split = str.split("/");
			if (split.length > 0) {
				String s = split[0];
				System.out.println(s);
			}
		}
	}

	/**
	 * 17. 电话号码的字母组合
	 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
	 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
	 * 示例 1：
	 * 输入：digits = "23"
	 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
	 * 示例 2：
	 * 输入：digits = ""
	 * 输出：[]
	 * 示例 3：
	 * 输入：digits = "2"
	 * 输出：["a","b","c"]
	 * 提示：
	 * 0 <= digits.length <= 4
	 * digits[i] 是范围 ['2', '9'] 的一个数字。
	 */
	public static class LetterCombinations {

		/**
		 * 回溯
		 *
		 * @param digits
		 * @return
		 */
		public static List<String> letterCombinations(String digits) {
			Character[][] arr = new Character[8][4];
			arr[0] = new Character[]{'a', 'b', 'c'};
			arr[1] = new Character[]{'d', 'e', 'f'};
			arr[2] = new Character[]{'g', 'h', 'i'};
			arr[3] = new Character[]{'j', 'k', 'l'};
			arr[4] = new Character[]{'m', 'n', 'o'};
			arr[5] = new Character[]{'p', 'q', 'r', 's'};
			arr[6] = new Character[]{'t', 'u', 'v'};
			arr[7] = new Character[]{'w', 'x', 'y', 'z'};
			List<String> list = new ArrayList<>();
			if (digits.isEmpty()) return list;
			buildString(arr, new StringBuilder(), list, digits, 0);
			return list;
		}

		/**
		 * 思路跟普通的递归树不太一样,所以思路也不太好想
		 *
		 * @param arr
		 * @param sb
		 * @param list
		 * @param digits
		 * @param idx
		 */
		public static void buildString(Character[][] arr, StringBuilder sb, List<String> list, String digits, int idx) {
			if (idx == digits.length()) {
				list.add(sb.toString());
			} else {
				char c = digits.charAt(idx);
				Character[] characters = arr[Character.getNumericValue(c) - 2];
				for (int i = 0; i < characters.length; i++) {
					sb.append(characters[i]);
					buildString(arr, sb, list, digits, idx + 1);
					sb.deleteCharAt(idx);
				}
			}
		}

		/**
		 * 回顾
		 *
		 * @param digits
		 * @return
		 */
		public static List<String> letterCombinationsReview(String digits) {
			char[][] chars = new char[8][4];
			chars[0] = new char[]{'a', 'b', 'c'};
			chars[1] = new char[]{'d', 'e', 'f'};
			chars[2] = new char[]{'g', 'h', 'i'};
			chars[3] = new char[]{'j', 'k', 'l'};
			chars[4] = new char[]{'m', 'n', 'o'};
			chars[5] = new char[]{'p', 'q', 'r', 's'};
			chars[6] = new char[]{'t', 'u', 'v'};
			chars[7] = new char[]{'w', 'x', 'y', 'z'};
			List<String> list = new ArrayList<>();
			if (digits.isEmpty()) return list;
			dfs(digits, 0, chars, new StringBuilder(), list);
			return list;
		}

		/**
		 * 深度优先遍历-回溯
		 *
		 * @param digits 数字组合
		 * @param idx    遍历到第几个数字
		 * @param sb     路径path
		 * @param list   组合结果集
		 */
		private static void dfs(String digits, int idx, char[][] chars, StringBuilder sb, List<String> list) {
			int n = digits.length();
			if (sb.length() == n) {
				list.add(sb.toString());
				return;
			}
			char[] ch = chars[Character.getNumericValue(digits.charAt(idx)) - 2];
			for (char c : ch) {
				sb.append(c);
				dfs(digits, idx + 1, chars, sb, list);
				sb.deleteCharAt(idx);
			}
		}

		public static void main(String[] args) {
			List<String> strings = letterCombinationsReview("23");
			System.out.println(strings);
		}
	}

	/**
	 * 22. 括号生成
	 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
	 * 示例 1：
	 * 输入：n = 3
	 * 输出：["((()))","(()())","(())()","()(())","()()()"]
	 * 示例 2：
	 * 输入：n = 1
	 * 输出：["()"]
	 * 提示：
	 * 1 <= n <= 8
	 */
	public static class GenerateParenthesis {

		/**
		 * n对括号
		 * 这题的关键点是 比如n=3时,有一种情况 (()())
		 *
		 * @param n
		 * @return
		 */
		public static List<String> generateParenthesis(int n) {
			List<String> list = new ArrayList<>();
			buildParenthesis(list, n, new StringBuilder());
			return list;
		}

		private static void buildParenthesis(List<String> list, int n, StringBuilder sb) {
			if (n == 0) {
				list.add(sb.toString());
			}
			int m = n;
			for (; m > 0; m--) {
				sb.append(getStr(m));
				buildParenthesis(list, n - m, sb);
				sb.delete(sb.length() - m * 2, sb.length());
			}
		}

		private static String getStr(int n) {
			String res = "";
			switch (n) {
				case 1:
					res = "()";
					break;
				case 2:
					res = "(())";
					break;
				case 3:
					res = "((()))";
					break;
			}
			return res;
		}

		/**
		 * 动态规划
		 * 递推公式 f(n)='('+ f(i)+')' + f(n-i-1) i范围[0,n-1]
		 *
		 * @param n
		 * @return
		 */
		public static List<String> generateParenthesisI(int n) {
			List<String>[] list = new List[n + 1];
			list[0] = Collections.singletonList("");
			List<String> l1 = new ArrayList<>();
			l1.add("()");
			list[1] = l1;
			// 从n=2开始往后进行推进
			for (int i = 2; i <= n; i++) {
				List<String> temp = new ArrayList<>();
				for (int j = 0; j < i; j++) {
					List<String> dp1 = list[j];
					List<String> dp2 = list[i - 1 - j];
					for (String k : dp1) {
						for (String l : dp2) {
							StringBuilder sb = new StringBuilder();
							temp.add(sb.append("(").append(k).append(")").append(l).toString());
						}
					}
				}
				list[i] = temp;
			}
			return list[n];
		}

		/**
		 * 回溯的主要思路是 枚举出所有的符合规范的情况
		 * 我们在回溯时,先输入左括号,再输入右括号;这样保证一对扩号中,左括号一定要右括号的前面
		 * 那怎么才能规范其组合呢?
		 * 1.当左括号数量< n时才可以填充左括号
		 * 2.当右括号数量< 左括号数量 才可以填充右括号
		 *
		 * @param n
		 * @return
		 */
		public static List<String> generateParenthesisII(int n) {
			List<String> list = new ArrayList<>();
			backtrack(list, new StringBuilder(), 0, 0, n);
			return list;
		}

		private static void backtrack(List<String> list, StringBuilder sb, int open, int close, int max) {
			if (sb.length() == max * 2) {
				list.add(sb.toString());
				return;
			}
			if (open < max) {
				sb.append("(");
				backtrack(list, sb, open + 1, close, max);
				sb.deleteCharAt(sb.length() - 1);
			}
			if (close < open) {
				sb.append(")");
				backtrack(list, sb, open, close + 1, max);
				sb.deleteCharAt(sb.length() - 1);
			}
		}

		public static void main(String[] args) {
			List<String> strings = generateParenthesisII(3);
			System.out.println(strings);
		}

	}

	/**
	 * 994. 腐烂的橘子
	 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
	 * 值 0 代表空单元格；
	 * 值 1 代表新鲜橘子；
	 * 值 2 代表腐烂的橘子。
	 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
	 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
	 * 示例 1：
	 * 输入：grid = [[2,1,1],[1,1,0],[0,1,1]]
	 * 输出：4
	 * 示例 2：
	 * 输入：grid = [[2,1,1],[0,1,1],[1,0,1]]
	 * 输出：-1
	 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个方向上。
	 * 示例 3：
	 * 输入：grid = [[0,2]]
	 * 输出：0
	 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
	 * 提示：
	 * m == grid.length
	 * n == grid[i].length
	 * 1 <= m, n <= 10
	 * grid[i][j] 仅为 0、1 或 2
	 */
	public static class OrangesRotting {

		private static int max = 0;

		/**
		 * dfs思路
		 * 1.需要的最小分钟,即从所有的2到1的遍历过程中所进行的步数的最大值
		 * 2.存在永远不会腐烂的橘子,说明存在1这样的节点,周围都是0的岛屿
		 * <p>
		 * 这个思路不对,不同位置的2可以同时进行遍历,到达最深的遍历点时,要取最小的那个
		 * 所以本题好像没法用dfs来求解,多个2可以同时进行扩散
		 * 那bfs该如何解题呢???
		 * 1.分步骤进行遍历,每次记录需要遍历的节点个数,模拟扩散的实际场景
		 * 2.同样,在遍历完所有的节点之后,如何还存在为1的节点,返回-1
		 *
		 * @param grid
		 * @return
		 */
		public static int orangesRotting(int[][] grid) {
			int n = grid.length, m = grid[0].length;
			int[][] visited = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (visited[i][j] == 0 && grid[i][j] == 2) {
						dfs(grid, 0, 0, 0, visited);
					}
				}
			}
			for (int[] ints : grid) {
				for (int i : ints)
					if (i == 1) {  // dfs遍历完之后还存在1,则说明存在永远不会腐烂的橘子
						return -1;
					}
			}
			return max;
		}


		/**
		 * @param grid    矩阵
		 * @param i       x坐标
		 * @param j       y坐标
		 * @param depth   遍历深度
		 * @param visited 已访问的坐标
		 */
		private static void dfs(int[][] grid, int i, int j, int depth, int[][] visited) {
			int n = grid.length, m = grid[0].length;
			if (i < 0 || i >= n || j < 0 || j >= m || grid[i][j] == 0) {
				max = Math.max(max, depth);
				return;
			}
			if (visited[i][j] == 1) return;
			visited[i][j] = 1;
			if (grid[i][j] == 1) {
				grid[i][j] = 2;
				depth++;
			} else {  // 碰到一个本身就是烂橘子时,depth初始化为0,深度重新计算
				max = Math.max(max, depth);
				depth = 0;
			}
			dfs(grid, i + 1, j, depth, visited);
			dfs(grid, i - 1, j, depth, visited);
			dfs(grid, i, j + 1, depth, visited);
			dfs(grid, i, j - 1, depth, visited);
		}

		/**
		 * 广度与优先遍历
		 * 1.找到所有的2,入队列;然后向周围扩散
		 * 2.每一步的扩散,记录入队列的总节点数
		 *
		 * @param grid
		 * @return
		 */
		public static int orangesRottingBfs(int[][] grid) {
			int n = grid.length, m = grid[0].length;
			Deque<int[]> deque = new LinkedList<>();
			int[][] visited = new int[n][m];
			boolean have = false;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (grid[i][j] == 2) {
						deque.add(new int[]{i, j});
						visited[i][j] = 1;
					} else if (grid[i][j] == 1) {
						have = true;
					}
				}
			}
			if (deque.isEmpty()) return have ? -1 : 0;
			int[] dx = new int[]{1, 0, -1, 0};
			int[] dy = new int[]{0, 1, 0, -1};
			int count = -1;
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; size--) {
					int[] curr = deque.pollFirst(); // 找到2,进行周边的扩散
					for (int i = 0; i < dy.length; i++) {
						int x = curr[0] + dx[i], y = curr[1] + dy[i];
						if (x >= 0 && x < n && y >= 0 && y < m && visited[x][y] == 0) {
							visited[x][y] = 1;
							if (grid[x][y] == 1) {
								grid[x][y] = 2;
								deque.add(new int[]{x, y});
							}
						}
					}
				}
				count++;
			}
			for (int[] ints : grid) {
				for (int i : ints)
					if (i == 1) {  // dfs遍历完之后还存在1,则说明存在永远不会腐烂的橘子
						return -1;
					}
			}
			return count;
		}

		/**
		 * 新鲜的橘子树可以计数,这样可以最后判断是否存在新鲜的橘子树来判断是否返回-1
		 *
		 * @param grid
		 * @return
		 */
		public static int orangesRottingBfsI(int[][] grid) {
			int n = grid.length, m = grid[0].length;
			Deque<int[]> deque = new LinkedList<>();
			int[][] visited = new int[n][m];
			int count = 0, round = 0; // 新鲜的橘子数量;遍历的轮数
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (grid[i][j] == 2) {
						deque.add(new int[]{i, j});
						visited[i][j] = 1;
					} else if (grid[i][j] == 1) {
						count++;
					}
				}
			}
			int[] dx = new int[]{1, 0, -1, 0};
			int[] dy = new int[]{0, 1, 0, -1};
			while (count > 0 && !deque.isEmpty()) {  // 当新鲜的橘子数不为0且烂橘子树也不为0时,进行广度优先遍历
				round++;
				int size = deque.size();
				for (; size > 0; size--) {
					int[] curr = deque.pollFirst(); // 找到2,进行周边的扩散
					for (int i = 0; i < dy.length; i++) {
						int x = curr[0] + dx[i], y = curr[1] + dy[i];
						if (x >= 0 && x < n && y >= 0 && y < m && visited[x][y] == 0) {
							visited[x][y] = 1;
							if (grid[x][y] == 1) {
								grid[x][y] = 2;
								count--;
								deque.add(new int[]{x, y});
							}
						}
					}
				}
			}
			return count > 0 ? -1 : round; // 为什么round不用-1呢,因为新鲜橘子树没有了,提前就结束遍历的轮数了
		}

		/**
		 * 不需要visited数组来记录是否已经遍历
		 *
		 * @param grid
		 * @return
		 */
		public static int orangesRottingBfsII(int[][] grid) {
			int n = grid.length, m = grid[0].length;
			Deque<int[]> deque = new LinkedList<>();
			int count = 0, round = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (grid[i][j] == 2) {
						deque.add(new int[]{i, j});
					} else if (grid[i][j] == 1) {
						count++;
					}
				}
			}
			int[] dx = new int[]{1, 0, -1, 0};
			int[] dy = new int[]{0, 1, 0, -1};
			while (count > 0 && !deque.isEmpty()) {
				round++;
				int size = deque.size();
				for (; size > 0; size--) {
					int[] curr = deque.pollFirst(); // 找到2,进行周边的扩散
					for (int i = 0; i < dy.length; i++) {
						int x = curr[0] + dx[i], y = curr[1] + dy[i];
						if (x >= 0 && x < n && y >= 0 && y < m) {
							if (grid[x][y] == 1) {
								grid[x][y] = 2;
								count--;
								deque.add(new int[]{x, y});
							}
						}
					}
				}
			}
			return count > 0 ? -1 : round;
		}

		public static void main(String[] args) {
			/*int[][] grid = new int[][]{
					{2, 1, 1},
					{1, 1, 1},
					{0, 1, 2}
			};*/
			int[][] grid = new int[][]{
					{1},
					{2}
			};
			int i = orangesRottingBfsI(grid);
			System.out.println(i);
		}
	}

	/**
	 * 131. 分割回文串
	 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是
	 * 回文串
	 * 。返回 s 所有可能的分割方案。
	 * 示例 1：
	 * 输入：s = "aab"
	 * 输出：[["a","a","b"],["aa","b"]]
	 * 示例 2：
	 * 输入：s = "a"
	 * 输出：[["a"]]
	 * 提示：
	 * 1 <= s.length <= 16
	 * s 仅由小写英文字母组成
	 */
	public static class Partition {

		private static List<List<String>> res = new ArrayList<>();

		/**
		 * 分割字符串,可以还出递归树,从顶点开始,每次切割1,2,3...n个字符
		 * 每次切割完字符后,即没有剩余的字符时,查看该次切割的每个节点是否都是回文串
		 * 那么如何知道该次切割的所有节点呢,引入path来记录切割的路径,让path中有一个节点不会回文串,则终止本条线上的遍历
		 *
		 * @param s
		 */
		public static List<List<String>> partition(String s) {
			dfs(s, 0, new ArrayList<>());
			return res;
		}

		/**
		 * 设置遍历的起始位置为i,进行下次遍历时其实位置为 i+1,i+2...n ,当然起始位置为n时,代表遍历结束
		 * 所以只有记录遍历的起始位置,然后每次往后推进到j的位置
		 * 因为j(分割结束位置)在每一次的递推中被不能被确定,它是在for循环中被定义的,每次往右推一位
		 * 所以j不能定义在dfs方法中,这样反而不好写递推的业务
		 *
		 * @param s
		 * @param i
		 * @param path
		 */
		private static void dfs(String s, int i, List<String> path) {
			int n = s.length();
			if (i == n) {  // 字符已遍历完
				res.add(new ArrayList<>(path));
				return;
			}
			// 起始位置是i,然后每次往后推一个位置
			for (int j = i; j < n; j++) {
				String str = s.substring(i, j + 1);
				if (isPalindrome(str)) {
					path.add(s.substring(i, j + 1));
					dfs(s, j + 1, path);
					path.remove(path.size() - 1);
				}
			}
		}

		private static boolean isPalindrome(String str) {
			if (str == null) return true;
			if (str.length() == 1) return true;
			int i = 0, j = str.length() - 1;
			while (i < j) {
				if (str.charAt(i) != str.charAt(j)) {
					return false;
				}
				i++;
				j--;
			}
			return true;
		}


		public static List<List<String>> partitionI(String s) {
			dfs(s, 0, new LinkedList<>());
			return res;
		}

		/**
		 * dfs
		 *
		 * @param s    s字符串
		 * @param i    从i开始分割下一个字符串
		 * @param path 分割路径
		 */
		private static void dfs(String s, int i, LinkedList<String> path) {
			int n = s.length();
			if (i == n) {
				res.add(new ArrayList<>(path));
				return;
			}
			for (int j = i; j < n; j++) {
				String str = s.substring(i, j + 1);
				if (isPalindrome(str)) {
					path.addLast(str);
					dfs(s, j + 1, path); // 从i到j+1位置的分割
					path.removeLast();
				}
			}
		}

		/**
		 * 回文串使用动态规划获取
		 *
		 * @param s
		 * @return
		 */
		public static List<List<String>> partitionII(String s) {
			f = isPalindromeDp(s);
			dfsI(s, 0, new LinkedList<>());
			return res;
		}

		private static boolean[][] f;

		private static void dfsI(String s, int i, LinkedList<String> path) {
			int n = s.length();
			if (i == n) {
				res.add(new ArrayList<>(path));
				return;
			}

			for (int j = i; j < n; j++) {
				String str = s.substring(i, j + 1);
				if (f[i][j]) {
					path.addLast(str);
					dfsI(s, j + 1, path); // 从i到j+1位置的分割
					path.removeLast();
				}
			}
		}

		/**
		 * f[i][j]表示s字符串从s[i...j]所形成的字符串是否为回文串
		 * 想要f[i][j]==true,那必须满足f[i+1][j-1]==true && s[i+1]==s[j-1]
		 * 在遍历的过程中,需要线得到f[i+1][j-1]的值,然后往后递推
		 * 所以dp数组的两层循环中,j从左往右进行遍历;i从右往左进行遍历
		 *
		 * @param s
		 * @return
		 */
		private static boolean[][] isPalindromeDp(String s) {
			int n = s.length();
			boolean[][] f = new boolean[n][n];
			for (int j = 0; j < n; j++) {
				for (int i = j; i >= 0; i--) {
					if (i == j) {                // 分割字符串长度为1
						f[i][j] = true;
					} else if (j - i + 1 == 2) { // 分割字符串长度为2
						f[i][j] = s.charAt(i) == s.charAt(j);
					} else {
						f[i][j] = f[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
					}
				}
			}
			return f;
		}

		public static void main(String[] args) {
			List<List<String>> aab = partition("abbab");
			System.out.println(aab);
			boolean[][] abbs = isPalindromeDp("abb");
			System.out.println();
		}
	}

	/**
	 * 216. 组合总和 III
	 * 找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
	 * 只使用数字1到9
	 * 每个数字 最多使用一次
	 * 返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
	 * 示例 1:
	 * 输入: k = 3, n = 7
	 * 输出: [[1,2,4]]
	 * 解释:
	 * 1 + 2 + 4 = 7
	 * 没有其他符合的组合了。
	 * 示例 2:
	 * 输入: k = 3, n = 9
	 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
	 * 解释:
	 * 1 + 2 + 6 = 9
	 * 1 + 3 + 5 = 9
	 * 2 + 3 + 4 = 9
	 * 没有其他符合的组合了。
	 * 示例 3:
	 * 输入: k = 4, n = 1
	 * 输出: []
	 * 解释: 不存在有效的组合。
	 * 在[1,9]范围内使用4个不同的数字，我们可以得到的最小和是1+2+3+4 = 10，因为10 > 1，没有有效的组合。
	 * 提示:
	 * 2 <= k <= 9
	 * 1 <= n <= 60
	 */
	static class CombinationSum3 {

		/**
		 * 思路：
		 * 1,2,3,4,5,6,7,8,9
		 * 用以上9个数字,不能重复;用k个不同的数来构成和为n的组合
		 * 1.考虑不需要回溯遍历的场景,当k=1时,n的范围为[1,9];当k=2时,n的范围为[1+2,8+9]
		 * *那么当k=x时,n的范围为[1+2+3+4..+k,9+8+7...+10-k]直接过滤掉不用回溯的场景
		 * 2.回溯思路,当选第a个数时,其选择范围是a-1个数的值[v(a),9];所以在回溯时要记录当前输入的值,来确认下一次可以输入哪些值
		 * @param k
		 * @param n
		 * @return
		 */
		public static List<List<Integer>> combinationSum3(int k, int n) {
			List<List<Integer>> res = new ArrayList<>();
			int l = k, left = 0, right = 0, s = 1, e = 9;
			while (l-- > 0) {
				left += s++;
				right += e--;
			}
			if (n < left || n > right) return res;
			dfs(k, n, 1, new ArrayDeque<>(), res);
			return res;
		}

		/**
		 * dfs 这里遍历到第k次时,判断v是否等于0;存在一个优化点即,第k次遍历时,如果找到一个解,然后这次遍历可以回溯到上一位
		 * @param k
		 * @param v
		 * @param i
		 * @param path
		 * @param res
		 */
		private static void dfs(int k, int v, int i, Deque<Integer> path, List<List<Integer>> res) {
			if (v < 0 && path.size() < k) return;
			if (path.size() == k) {
				if (v == 0) {
					res.add(new ArrayList<>(path));
					return;
				}
				return;
			}
			for (int j = i; j <= 9; j++) {
				path.addLast(j);
				dfs(k, v - j, j + 1, path, res);
				path.removeLast();
			}
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = combinationSum3(3, 9);
			System.out.println(lists);
		}
	}
}
