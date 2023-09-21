package test.backtracking;

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
}
