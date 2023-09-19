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
		 *
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

	public static void main(String[] args) {
		int[] nums = new int[]{1, 2, 3};
		List<List<Integer>> permute = permute(nums);
		System.out.println(permute);
	}
}
}
