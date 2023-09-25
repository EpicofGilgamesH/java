package test.backtracking;

import com.sun.tools.javac.code.Attribute;

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
}
