package test.tree;

import org.omg.CORBA.INTERNAL;
import sun.misc.Perf;
import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * 二叉树的使用
 */
public class LeetCodeCase {

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

	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	}

	public static class PerfectNode {
		public int val;
		public PerfectNode left;
		public PerfectNode right;
		public PerfectNode next;

		public PerfectNode() {
		}

		public PerfectNode(int _val) {
			val = _val;
		}

		public PerfectNode(int _val, PerfectNode _left, PerfectNode _right, PerfectNode _next) {
			val = _val;
			left = _left;
			right = _right;
			next = _next;
		}
	}

	/**
	 * 144. 二叉树的前序遍历
	 * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
	 * <p>
	 * 示例 1：
	 * <p>
	 * <p>
	 * 输入：root = [1,null,2,3]
	 * 输出：[1,2,3]
	 * 示例 2：
	 * <p>
	 * 输入：root = []
	 * 输出：[]
	 * 示例 3：
	 * <p>
	 * 输入：root = [1]
	 * 输出：[1]
	 * 示例 4：
	 * <p>
	 * 输入：root = [1,2]
	 * 输出：[1,2]
	 * 示例 5：
	 * <p>
	 * 输入：root = [1,null,2]
	 * 输出：[1,2]
	 * <p>
	 * 提示：
	 * <p>
	 * 树中节点数目在范围 [0, 100] 内
	 * -100 <= Node.val <= 100
	 * <p>
	 * 进阶：递归算法很简单，你可以通过迭代算法完成吗？
	 */
	public static class PreorderTraversal {

		/**
		 * 前序遍历-递归
		 */
		public static List<Integer> preorderTraversal(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			preorder(root, list);
			return list;
		}

		public static void preorder(TreeNode node, List<Integer> list) {
			if (node == null) return;
			list.add(node.val);
			preorder(node.left, list);
			preorder(node.right, list);
		}

		/**
		 * 前序遍历-通过遍历的方式
		 */
		public static List<Integer> preorderTraversalII(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) {
				return list;
			}
			Deque<TreeNode> stack = new ArrayDeque<>();
			TreeNode node = root;
			while (!stack.isEmpty() || node != null) {
				while (node != null) {  // 左子节点
					list.add(node.val);
					stack.add(node);
					node = node.left;
				}
				// 左子节点完成,开始右子节点,这个好难理解
				node = stack.pollLast();
				node = node != null ? node.right : null;
			}
			return list;
		}

		/**
		 * 实际上这种方式并没有模拟递归的方法栈,只是结果刚好相符
		 */
		public static List<Integer> preorderTraversalIII(TreeNode root) {
			List<Integer> result = new ArrayList<>();
			Stack<TreeNode> st = new Stack<>();
			if (root == null) return result;
			st.push(root);
			while (!st.isEmpty()) {
				TreeNode node = st.pop();
				result.add(node.val);
				if (node.right != null) st.push(node.right);
				if (node.left != null) st.push(node.left);

			}
			return result;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			TreeNode n3 = new TreeNode(4);
			TreeNode n4 = new TreeNode(5);
			TreeNode n5 = new TreeNode(6);
			root.left = n1;
			root.right = n2;
			n1.left = n4;
			n1.right = n5;
			n2.left = n3;

			List<Integer> list = preorderTraversal(root);
			System.out.println(list);

			System.out.println(preorderTraversalII(root));
			System.out.println(preorderTraversalIII(root));
		}
	}

	public static class PreorderTraversalII {

		/**
		 * 二叉树的中序遍历 这里有点混淆了:前序遍历是 根左右 中序遍历是 左根右 后序遍历是左右根
		 */
		public static List<Integer> inorderTraversal(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			inorder(list, root);
			return list;
		}

		/**
		 * 递归思路-虚拟机构建方法调用栈,进入递归方法则调入一个新的方法栈;出该方法则回到上一个方法栈
		 */
		public static void inorder(List<Integer> list, TreeNode node) {
			if (node == null) return;
			list.add(node.val);
			inorder(list, node.left);
			inorder(list, node.right);
		}

		/**
		 * 迭代 - 模拟栈
		 * 画出递归方法栈的调用图,可以助于理解
		 */
		public static List<Integer> inorderTraversalIterate(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			Stack<TreeNode> stack = new Stack<>();
			TreeNode node = root;
			while (!stack.empty() || node != null) {
				while (node != null) { // 左子节点一直入栈
					list.add(node.val);
					stack.push(node);
					node = node.left;
				}
				// 右子节点 遍历-出栈
				node = stack.pop();
				node = node.right;
			}
			return list;
		}

		public static void main(String[] args) {
			/*TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			TreeNode n3 = new TreeNode(4);
			TreeNode n4 = new TreeNode(5);
			TreeNode n5 = new TreeNode(6);
			root.left = n1;
			root.right = n2;
			n1.left = n4;
			n1.right = n5;
			n2.left = n3;*/

			TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			root.right = n1;
			n1.left = n2;

			System.out.println(inorderTraversal(root));
			System.out.println(inorderTraversalIterate(root));
		}
	}

	/**
	 * /**
	 * * 94. 二叉树的中序遍历
	 * * <p>
	 * * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
	 * * <p>
	 * * 示例 1：
	 * * <p>
	 * * <p>
	 * * 输入：root = [1,null,2,3]
	 * * 输出：[1,3,2]
	 * * 示例 2：
	 * * <p>
	 * * 输入：root = []
	 * * 输出：[]
	 * * 示例 3：
	 * * <p>
	 * * 输入：root = [1]
	 * * 输出：[1]
	 * * <p>
	 * * 提示：
	 * * <p>
	 * * 树中节点数目在范围 [0, 100] 内
	 * * -100 <= Node.val <= 100
	 * * <p>
	 * * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
	 */
	public static class inorderTraversal {

		/**
		 * 中序遍历 - 左中右
		 */
		public static List<Integer> inorderTraversal(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			inorder(root, list);
			return list;
		}

		public static void inorder(TreeNode node, List<Integer> list) {
			if (node == null) return;
			inorder(node.left, list);
			list.add(node.val);
			inorder(node.right, list);
		}

		/**
		 * 迭代模拟递归栈
		 *
		 * @param root
		 * @return
		 */
		public static List<Integer> inorderTraversalByIterator(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			Stack<TreeNode> stack = new Stack<>();
			TreeNode node = root;
			while (!stack.empty() || node != null) {
				while (node != null) {
					stack.push(node);
					node = node.left;
				}
				node = stack.pop();
				list.add(node.val);
				node = node.right;
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			TreeNode n3 = new TreeNode(4);
			TreeNode n4 = new TreeNode(5);
			TreeNode n5 = new TreeNode(6);
			root.left = n1;
			root.right = n2;
			n1.left = n4;
			n1.right = n5;
			n2.left = n3;
			System.out.println(inorderTraversal(root));
			System.out.println(inorderTraversalByIterator(root));
		}
	}

	/**
	 * 145. 二叉树的后序遍历
	 */
	public static class PostorderTraversal {

		/**
		 * 递归遍历 后序 - 左右根
		 *
		 * @param root
		 * @return
		 */
		public static List<Integer> postorderTraversal(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			postorder(root, list);
			return list;
		}

		public static void postorder(TreeNode node, List<Integer> list) {
			if (node == null) return;
			postorder(node.left, list);
			postorder(node.right, list);
			list.add(node.val);
		}

		/**
		 * 后序-迭代 有点难以理解********
		 */
		public static List<Integer> postorderTraversalIterator(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			Stack<TreeNode> stack = new Stack<>();
			TreeNode node = root;
			TreeNode pre = null;
			while (!stack.isEmpty() || node != null) {
				while (node != null) { // 左节点一直向下迭代放入栈
					stack.push(node);
					node = node.left;
				}
				// 左节点入栈完成后,list放入右节点,后list放入当前元素
				node = stack.pop();
				if (node.right == null || node.right == pre) { // 右节点已经访问过或者右节点为空,则把根节点加入list
					list.add(node.val);
					pre = node;
					node = null; // 更新前一个节点,然后直接出栈下一个节点
				} else {
					stack.push(node);  // 右节点存在,且未访问过,则继续入栈
					node = node.right;
				}
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			TreeNode n3 = new TreeNode(4);
			TreeNode n4 = new TreeNode(5);
			TreeNode n5 = new TreeNode(6);
			root.left = n1;
			root.right = n2;
			n1.left = n4;
			n1.right = n5;
			n2.left = n3;
			System.out.println(postorderTraversal(root));
			System.out.println(postorderTraversalIterator(root));
		}
	}

	/**
	 * 102. 二叉树的层序遍历
	 * <p>
	 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
	 * <p>
	 * 示例 1：
	 * <p>
	 * <p>
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：[[3],[9,20],[15,7]]
	 * 示例 2：
	 * <p>
	 * 输入：root = [1]
	 * 输出：[[1]]
	 * 示例 3：
	 * <p>
	 * 输入：root = []
	 * 输出：[]
	 * <p>
	 * 提示：
	 * <p>
	 * 树中节点数目在范围 [0, 2000] 内
	 * -1000 <= Node.val <= 1000
	 */
	public static class LevelOrder {

		/**
		 * 广度优先遍历思路
		 * 思路:按层遍历,存储该层的左右子节点;使用两个FIFO队列,每一层从A队列出队,然后获取其左右节点入队到B队列
		 * 队列A和B 每层遍历完进行交换操作
		 */
		public static List<List<Integer>> levelOrder(TreeNode root) {
			List<List<Integer>> list = new ArrayList<>();
			if (root == null) return list;
			LinkedList<TreeNode> linkedListA = new LinkedList<>();
			LinkedList<TreeNode> linkedListB = new LinkedList<>();
			linkedListA.add(root);
			while (!linkedListA.isEmpty()) {
				List<Integer> layerList = new ArrayList<>();
				while (!linkedListA.isEmpty()) {
					TreeNode node = linkedListA.pollFirst();
					layerList.add(node.val);
					if (node.left != null) linkedListB.add(node.left);
					if (node.right != null) linkedListB.add(node.right);
				}
				list.add(layerList);
				LinkedList<TreeNode> temp = linkedListA;
				linkedListA = linkedListB;
				linkedListB = temp;
			}
			return list;
		}

		/**
		 * 如何只用一个队列来实现呢?则要记录每层的节点个数,保证每一层只遍历该数量的节点
		 * 通过队列size计数,会比设定一个变量要清晰一些
		 *
		 * @param root
		 * @return
		 */
		public static List<List<Integer>> levelOrderII(TreeNode root) {
			List<List<Integer>> list = new ArrayList<>();
			if (root == null) return list;
			LinkedList<TreeNode> linkedList = new LinkedList<>();
			linkedList.add(root);
			while (!linkedList.isEmpty()) {
				int size = linkedList.size();
				List<Integer> layerList = new ArrayList<>();
				while (size > 0) {
					TreeNode node = linkedList.pollFirst();
					layerList.add(node.val);
					if (node.left != null) linkedList.add(node.left);
					if (node.right != null) linkedList.add(node.right);
					size--;
				}
				list.add(layerList);
			}
			return list;
		}


		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n1 = new TreeNode(2);
			TreeNode n2 = new TreeNode(3);
			TreeNode n3 = new TreeNode(4);
			TreeNode n4 = new TreeNode(5);
			TreeNode n5 = new TreeNode(6);
			root.left = n1;
			root.right = n2;
			n1.left = n4;
			n1.right = n5;
			n2.left = n3;
			System.out.println(levelOrder(root));
			System.out.println(levelOrderII(root));
		}
	}

	/**
	 * 剑指 Offer 32 - I. 从上到下打印二叉树
	 * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
	 * <p>
	 * 例如:
	 * 给定二叉树: [3,9,20,null,null,15,7],
	 * <p>
	 * 3
	 * / \
	 * 9  20
	 * /  \
	 * 15   7
	 * 返回：
	 * <p>
	 * [3,9,20,15,7]
	 * <p>
	 * 提示：
	 * <p>
	 * 节点总数 <= 1000
	 */
	public static class LevelOrderOffer {

		/**
		 * 二叉树按层遍历,返回的是数组,那么数组的长度如何确定呢?-用List然后转数组
		 */
		public static int[] levelOrder(TreeNode root) {
			if (root == null) return new int[]{};
			Deque<TreeNode> queue = new LinkedList<>();
			List<Integer> list = new ArrayList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				int size = queue.size();
				while (size > 0) {
					TreeNode node = queue.pollFirst();
					list.add(node.val);
					if (node.left != null) queue.add(node.left);
					if (node.right != null) queue.add(node.right);
					size--;
				}
			}
			// return list.stream().mapToInt(Integer::intValue).toArray();
			int[] arr = new int[list.size()];
			for (int i = 0; i < list.size(); ++i) {
				arr[i] = list.get(i);
			}
			return arr;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			System.out.println(Arrays.toString(levelOrder(root)));
		}
	}

	/**
	 * 剑指 Offer 32 - III. 从上到下打印二叉树 III
	 * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
	 * <p>
	 * 例如:
	 * 给定二叉树: [3,9,20,null,null,15,7],
	 * <p>
	 * 3
	 * / \
	 * 9  20
	 * /  \
	 * 15   7
	 * 返回其层次遍历结果：
	 * <p>
	 * [
	 * [3],
	 * [20,9],
	 * [15,7]
	 * ]
	 * <p>
	 * 提示：
	 * <p>
	 * 节点总数 <= 1000
	 */
	public static class LevelOrderOfferIII {

		/**
		 * 奇数层从左往右打印,偶数层从右往左打印;本来使用队列FIFO,实现每一层从左往右打印
		 * 使用一个LinkedList支持FIFO和FILO,然后奇数行出队从队头,入队从队尾(右-左子树);偶数行出队从队尾,入队从队头(左-右子树)
		 */
		public static List<List<Integer>> levelOrder(TreeNode root) {
			List<List<Integer>> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			boolean f = true;
			while (!deque.isEmpty()) {
				int size = deque.size();
				List<Integer> layerList = new ArrayList<>();
				while (size > 0) {
					if (f) { // 奇数层打印
						TreeNode node = deque.pollLast();
						layerList.add(node.val);
						if (node.left != null) deque.addFirst(node.left);
						if (node.right != null) deque.addFirst(node.right);
					} else {
						TreeNode node = deque.pollFirst();
						layerList.add(node.val);
						if (node.right != null) deque.addLast(node.right);
						if (node.left != null) deque.addLast(node.left);
					}
					size--;
				}
				list.add(layerList);
				f = !f;
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			TreeNode n5 = new TreeNode(6);
			TreeNode n6 = new TreeNode(8);
			TreeNode n7 = new TreeNode(17);
			TreeNode n8 = new TreeNode(11);
			TreeNode n9 = new TreeNode(12);
			TreeNode n10 = new TreeNode(10);
			TreeNode n11 = new TreeNode(22);
			TreeNode n12 = new TreeNode(50);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			n1.left = n5;
			n1.right = n6;
			n5.left = n7;
			n5.right = n8;
			n6.left = n9;
			n6.right = n10;
			n4.left = n11;
			n4.right = n12;
			System.out.println(levelOrder(root));
		}
	}

	/**
	 * 107. 二叉树的层序遍历 II
	 * 给你二叉树的根节点 root ，返回其节点值 自底向上的层序遍历 。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：[[15,7],[9,20],[3]]
	 * 示例 2：
	 * <p>
	 * 输入：root = [1]
	 * 输出：[[1]]
	 * 示例 3：
	 * <p>
	 * 输入：root = []
	 * 输出：[]
	 * <p>
	 * 提示：
	 * <p>
	 * 树中节点数目在范围 [0, 2000] 内
	 * -1000 <= Node.val <= 1000
	 */
	public static class LevelOrderBottom {

		/**
		 * 个人思路:
		 * 从叶子节点层(最底层)往上进行遍历,简单点的思路可以从上往下遍历,然后逆序List集合
		 * list集合在插入的时候,也可以头插法,总之都是在返回集合的时候做文章
		 */
		public static List<List<Integer>> levelOrderBottom(TreeNode root) {
			List<List<Integer>> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			while (!deque.isEmpty()) {
				List<Integer> layerList = new ArrayList<>();
				int size = deque.size();
				for (int i = size; i > 0; --i) {
					TreeNode node = deque.poll();
					layerList.add(node.val);
					if (node.left != null) deque.add(node.left);
					if (node.right != null) deque.add(node.right);
				}
				list.add(0, layerList);
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			TreeNode n5 = new TreeNode(6);
			TreeNode n6 = new TreeNode(8);
			TreeNode n7 = new TreeNode(17);
			TreeNode n8 = new TreeNode(11);
			TreeNode n9 = new TreeNode(12);
			TreeNode n10 = new TreeNode(10);
			TreeNode n11 = new TreeNode(22);
			TreeNode n12 = new TreeNode(50);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			n1.left = n5;
			n1.right = n6;
			n5.left = n7;
			n5.right = n8;
			n6.left = n9;
			n6.right = n10;
			n4.left = n11;
			n4.right = n12;
			System.out.println(levelOrderBottom(root));
		}
	}

	/**
	 * 199. 二叉树的右视图
	 * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
	 * <p>
	 * 示例 1:
	 * <p>
	 * 输入: [1,2,3,null,5,null,4]
	 * 输出: [1,3,4]
	 * 示例 2:
	 * <p>
	 * 输入: [1,null,3]
	 * 输出: [1,3]
	 * 示例 3:
	 * <p>
	 * 输入: []
	 * 输出: []
	 * <p>
	 * 提示:
	 * <p>
	 * 二叉树的节点个数的范围是 [0,100]
	 * -100 <= Node.val <= 100
	 */
	public static class RightSideView {

		/**
		 * 个人思路：
		 * 从题意来看,是要找到二叉树的每一层中最后一个节点;然后这个节点的下级节点如果存在右节点则是右节点;如果存在左节点则是左节点
		 * 但是该层最右侧节点,可能没有子节点,所以还是要层级遍历一次,找到每一层最后一个元素
		 */
		public static List<Integer> rightSideView(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			while (!deque.isEmpty()) {
				int size = deque.size();
				Integer last = null;
				for (; size > 0; --size) {
					TreeNode node = deque.poll();
					last = node.val;
					if (node.left != null) deque.add(node.left);
					if (node.right != null) deque.add(node.right);
				}
				list.add(last);
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			TreeNode n5 = new TreeNode(6);
			TreeNode n6 = new TreeNode(8);
			TreeNode n7 = new TreeNode(17);
			TreeNode n8 = new TreeNode(11);
			TreeNode n9 = new TreeNode(12);
			TreeNode n10 = new TreeNode(10);
			TreeNode n11 = new TreeNode(22);
			TreeNode n12 = new TreeNode(50);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			n1.left = n5;
			n1.right = n6;
			n5.left = n7;
			n5.right = n8;
			n6.left = n9;
			n6.right = n10;
			n4.left = n11;
			n4.right = n12;
			System.out.println(rightSideView(root));
		}
	}

	/**
	 * 637. 二叉树的层平均值
	 * 给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10-5 以内的答案可以被接受。
	 * <p>
	 * 示例 1：
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：[3.00000,14.50000,11.00000]
	 * 解释：第 0 层的平均值为 3,第 1 层的平均值为 14.5,第 2 层的平均值为 11 。
	 * 因此返回 [3, 14.5, 11] 。
	 * 示例 2:
	 * <p>
	 * 输入：root = [3,9,20,15,7]
	 * 输出：[3.00000,14.50000,11.00000]
	 * <p>
	 * 提示：
	 * <p>
	 * 树中节点数量在 [1, 104] 范围内
	 * -231 <= Node.val <= 231 - 1
	 */
	public static class AverageOfLevels {

		/**
		 * 个人思路:
		 * 广度优先遍历
		 */
		public static List<Double> averageOfLevels(TreeNode root) {
			List<Double> list = new ArrayList<>();
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			while (!deque.isEmpty()) {
				int s = deque.size();
				double sum = 0, size = s;
				for (; s > 0; --s) {
					TreeNode poll = deque.poll();
					sum += poll.val;
					if (poll.left != null) deque.add(poll.left);
					if (poll.right != null) deque.add(poll.right);
				}
				list.add(sum / size);
			}
			return list;
		}

		/**
		 * 尝试使用深度优先遍历-DFS来解题
		 * 深度优先遍历,前序遍历;实际上是会遍历到每一层的每一个节点;但是本题要求每一层的平均值,则要记录每次遍历到的节点的层级,然后
		 * 记录该层的节点个数,最后统一求平均值
		 */
		public static List<Double> averageOfLevelsDfs(TreeNode root) {
			List<Integer> sizes = new ArrayList<>();
			List<Double> sums = new ArrayList<>();
			dfs(root, 0, sizes, sums);
			for (int i = 0; i < sums.size(); i++) {
				sums.set(i, sums.get(i) / sizes.get(i));
			}
			return sums;
		}

		/**
		 * dfs
		 *
		 * @param node  节点
		 * @param level 层级
		 * @param sizes 该层节点数量,index为层级level值
		 * @param sums  该层节点值的和
		 */
		public static void dfs(TreeNode node, int level, List<Integer> sizes, List<Double> sums) {
			if (node == null) return;
			if (level < sums.size()) {
				sizes.set(level, sizes.get(level) + 1);
				sums.set(level, sums.get(level) + node.val);
			} else { // level >= sums.size() 则为新遍历到的层,需要新插入集合;此时插入的index肯定是level+1
				sizes.add(1);
				sums.add(1.0 * node.val);
			}
			dfs(node.left, level + 1, sizes, sums);
			dfs(node.right, level + 1, sizes, sums);
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			// System.out.println(averageOfLevels(root));
			System.out.println(averageOfLevelsDfs(root));
		}
	}

	/**
	 * 429. N 叉树的层序遍历
	 * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
	 * <p>
	 * 树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：root = [1,null,3,2,4,null,5,6]
	 * 输出：[[1],[3,2,4],[5,6]]
	 * 示例 2：
	 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
	 * 输出：[[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
	 * 提示：
	 * 树的高度不会超过 1000
	 * 树的节点总数在 [0, 10^4] 之间
	 */
	public static class LevelOrderNTree {

		/**
		 * 个人思路:
		 * N叉数的层序遍历,思路还是差不多,每个节点的子节点又固定的left,right变成了list集合
		 */
		public static List<List<Integer>> levelOrder(Node root) {
			List<List<Integer>> list = new ArrayList<>();
			if (root == null) return list;
			Deque<Node> deque = new LinkedList<>();
			deque.add(root);
			while (!deque.isEmpty()) {
				int size = deque.size();
				List<Integer> layerList = new ArrayList<>();
				for (; size > 0; --size) {
					Node node = deque.poll();
					layerList.add(node.val);
					if (node.children != null && node.children.size() > 0) {
						deque.addAll(node.children);
					}
				}
				list.add(layerList);
			}
			return list;
		}

		public static void main(String[] args) {
			Node root = new Node(1);
			Node n2 = new Node(2);
			Node n3 = new Node(3);
			Node n4 = new Node(4);
			Node n5 = new Node(5);
			Node n6 = new Node(6);
			Node n7 = new Node(7);
			Node n8 = new Node(8);
			Node n9 = new Node(9);
			Node n10 = new Node(10);
			Node n11 = new Node(11);
			Node n12 = new Node(12);
			Node n13 = new Node(13);
			Node n14 = new Node(14);
			List<Node> rootChildren = new ArrayList<>();
			rootChildren.add(n2);
			rootChildren.add(n3);
			rootChildren.add(n4);
			rootChildren.add(n5);
			root.children = rootChildren;
			List<Node> n3Children = new ArrayList<>();
			n3Children.add(n6);
			n3Children.add(n7);
			n3.children = n3Children;
			n4.children = Arrays.asList(n8);
			n5.children = Arrays.asList(n9, n10);
			n7.children = Arrays.asList(n11);
			n8.children = Arrays.asList(n12);
			n9.children = Arrays.asList(n13);
			n11.children = Arrays.asList(n14);
			System.out.println(levelOrder(root));
		}
	}

	/**
	 * 515. 在每个树行中找最大值
	 * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
	 * 示例1：
	 * 输入: root = [1,3,2,5,3,null,9]
	 * 输出: [1,3,9]
	 * 示例2：
	 * 输入: root = [1,2,3]
	 * 输出: [1,3]
	 * 提示：
	 * 二叉树的节点个数的范围是 [0,104]
	 * -231 <= Node.val <= 231 - 1
	 */
	public static class LargestValues {

		/**
		 * 个人思路:
		 * 尝试用两种方式解题
		 * 1.层序遍历,借助FIFO队列
		 * 2.DFS深度优先遍历
		 */
		public static List<Integer> largestValues(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			while (!deque.isEmpty()) {
				int size = deque.size();
				int max = Integer.MIN_VALUE;
				for (; size > 0; --size) {
					TreeNode node = deque.poll();
					max = Math.max(max, node.val);
					if (node.left != null) deque.add(node.left);
					if (node.right != null) deque.add(node.right);
				}
				list.add(max);
			}
			return list;
		}

		/**
		 * dfs method
		 */
		public static List<Integer> largestValuesDFS(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			if (root == null) return list;
			dfs(root, 0, list);
			return list;
		}

		/**
		 * dfs
		 *
		 * @param node  当前节点
		 * @param level 当前层级
		 * @param maxs  层级最大值集合,index 为level,从0开始
		 */
		public static void dfs(TreeNode node, int level, List<Integer> maxs) {
			if (node == null) return;
			if (level < maxs.size()) { // 已经遍历过的层级
				maxs.set(level, Math.max(maxs.get(level), node.val));
			} else {  // 还未遍历过的层级
				maxs.add(node.val);
			}
			dfs(node.left, level + 1, maxs);
			dfs(node.right, level + 1, maxs);
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			TreeNode n3 = new TreeNode(15);
			TreeNode n4 = new TreeNode(7);
			root.left = n1;
			root.right = n2;
			n2.left = n3;
			n2.right = n4;
			// System.out.println(largestValues(root));
			System.out.println(largestValuesDFS(root));
		}
	}

	/**
	 * 116. 填充每个节点的下一个右侧节点指针
	 * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
	 * struct Node {
	 * int val;
	 * Node *left;
	 * Node *right;
	 * Node *next;
	 * }
	 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
	 * <p>
	 * 初始状态下，所有 next 指针都被设置为 NULL。
	 * 示例 1：
	 * 输入：root = [1,2,3,4,5,6,7]
	 * 输出：[1,#,2,3,#,4,5,6,7,#]
	 * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化的输出按层序遍历排列，同一层节点由 next 指针连接，'#' 标志着每一层的结束。
	 * 示例 2:
	 * 输入：root = []
	 * 输出：[]
	 * 提示：
	 * 树中节点的数量在 [0, 212 - 1] 范围内
	 * -1000 <= node.val <= 1000
	 */
	public static class ConnectTree {

		/**
		 * 个人思路:
		 * 1.层序遍历
		 * 2.dfs深度优先遍历
		 */
		public static PerfectNode connect(PerfectNode root) {
			if (root == null) return null;
			Deque<PerfectNode> deque = new LinkedList<>();
			// 完美二叉树,其实不用记录每层的size,符合2^n规律
			deque.add(root);
			int s = 1;
			while (!deque.isEmpty()) {
				PerfectNode pre = deque.poll();
				if (pre != null) {
					if (pre.left != null) deque.add(pre.left);
					if (pre.right != null) deque.add(pre.right);
					for (int i = s - 1; i > 0; --i) {  // 每层的循环
						PerfectNode current = deque.poll();
						if (current != null) {
							pre.next = current;
							if (current.left != null) deque.add(current.left);
							if (current.right != null) deque.add(current.right);
						}
						pre = current;
					}
					s *= 2;
				}
			}
			return root;
		}

		/**
		 * dfs method
		 * 完美二叉树,相邻的节点有两种情况 1)属于同一个父节点 2)属于父节点右边节点的左子节点
		 */
		public static PerfectNode connectDFS(PerfectNode root) {
			if (root == null) return null;
			dfs(root);
			return root;
		}

		public static void dfs(PerfectNode node) {
			if (node.left != null) {
				node.left.next = node.right; // 当前节点的左子节点肯定是连接到当前节点的右子节点
				// 当前节点的右子节点肯定是连接到 当前节点的下一节点的左子节点
				node.right.next = node.next != null ? node.next.left : null;
				dfs(node.left);
				dfs(node.right);
			}
		}

		public static void main(String[] args) {
			PerfectNode node1 = new PerfectNode(1);
			PerfectNode node2 = new PerfectNode(2);
			PerfectNode node3 = new PerfectNode(3);
			PerfectNode node4 = new PerfectNode(4);
			PerfectNode node5 = new PerfectNode(5);
			PerfectNode node6 = new PerfectNode(6);
			PerfectNode node7 = new PerfectNode(7);
			node1.left = node2;
			node1.right = node3;
			node2.left = node4;
			node2.right = node5;
			node3.left = node6;
			node3.right = node7;
			// PerfectNode connect = connect(node1);
			PerfectNode perfectNode = connectDFS(node1);
			System.out.println();
		}
	}

	/**
	 * 117. 填充每个节点的下一个右侧节点指针 II
	 * 给定一个二叉树：
	 * <p>
	 * struct Node {
	 * int val;
	 * Node *left;
	 * Node *right;
	 * Node *next;
	 * }
	 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL 。
	 * <p>
	 * 初始状态下，所有 next 指针都被设置为 NULL 。
	 * 示例 1：
	 * 输入：root = [1,2,3,4,5,null,7]
	 * 输出：[1,#,2,3,#,4,5,7,#]
	 * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。序列化输出按层序遍历顺序（由 next 指针连接），'#' 表示每层的末尾。
	 * 示例 2：
	 * 输入：root = []
	 * 输出：[]
	 * 提示：
	 * 树中的节点数在范围 [0, 6000] 内
	 * -100 <= Node.val <= 100
	 * 进阶：
	 * 你只能使用常量级额外空间。
	 * 使用递归解题也符合要求，本题中递归程序的隐式栈空间不计入额外空间复杂度。
	 */
	public static class ConnectTreeII {

		/**
		 * 使用dfs深度优先遍历的方式
		 * 遍历到当前节点N,那么此时处理其子节点的next指针,有以下情况:
		 * 1.N的left节点的next指针 -> 指向N的right节点,如果为空,则指向N的next指针的左节点,如果还未空则指向其右节点
		 * 2.N的right节点的next指针 -> 指向N的next指针的左节点,如果为空则指向其右节点;如果N的next指针为null,则指向null
		 * <p>
		 * 思路不正确*********** 如果N的next节点的左右节点都为空,则找到N.next的next节点,直到N节点往下的一直的next节点都为空
		 * 这题跟完全二叉树场景不一样,存在上一层的next节点还没指向的情况,所以还不太好用dfs的方式
		 * 所以还是需要用层序遍历方式
		 */
		public static PerfectNode connect(PerfectNode root) {
			dfs(root);
			return root;
		}

		public static void dfs(PerfectNode node) {
			if (node == null) return;
			PerfectNode leftNext = null, rightNext = null, n = node;
			if (node.left != null) { // N.left的next节点查找
				if (node.right != null) {
					leftNext = node.right;
				} else {
					while (n.next != null) {
						if (n.next.left != null) {
							leftNext = n.next.left;
							break;
						} else if (n.next.right != null) {
							leftNext = n.next.right;
							break;
						} else { // N的next节点左右节点都为空
							n = n.next;
						}
					}
				}
				node.left.next = leftNext;
			}
			if (node.right != null) {
				while (n.next != null) {
					if (n.next.left != null) {
						rightNext = n.next.left;
						break;
					} else if (n.next.right != null) {
						rightNext = n.next.right;
						break;
					} else { // N的next节点左右节点都为空
						n = n.next;
					}
				}
				node.right.next = rightNext;
			}
			dfs(node.left);
			dfs(node.right);
		}

		/**
		 * 使用层序遍历的方式,遍历到当前节点N时,处理其左右子节点的next指针
		 */
		public static PerfectNode connectBFS(PerfectNode root) {
			if (root == null) return null;
			Deque<PerfectNode> queue = new LinkedList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				int size = queue.size();
				for (; size > 0; size--) {
					PerfectNode node = queue.poll(); // 当前node,处理其子节点的next连接
					PerfectNode leftNext = null, rightNext = null;
					if (node.left != null) {
						if (node.right != null) {
							leftNext = node.right;
						} else {
							PerfectNode n = node;
							while (n.next != null) {  // 找到next节点元素
								if (n.next.left != null) {
									leftNext = n.next.left;
									break;
								} else if (n.next.right != null) {
									leftNext = n.next.right;
									break;
								} else {
									n = n.next;
								}
							}
						}
						node.left.next = leftNext;
						queue.add(node.left);
					}
					if (node.right != null) {
						PerfectNode n = node;
						while (n.next != null) {  // 找到next节点元素
							if (n.next.left != null) {
								rightNext = n.next.left;
								break;
							} else if (n.next.right != null) {
								rightNext = n.next.right;
								break;
							} else {
								n = n.next;
							}
						}
						node.right.next = rightNext;
						queue.add(node.right);
					}
				}
			}
			return root;
		}

		/**
		 * 层序遍历,遍历到当前节点N时,处理其当前节点的next指针,pre -> current
		 */
		public static PerfectNode connectBFSII(PerfectNode root) {
			if (root == null) return null;
			Deque<PerfectNode> queue = new LinkedList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				int size = queue.size();
				PerfectNode pre = null;
				for (; size > 0; --size) {  // 处理当前层的next指针
					PerfectNode cur = queue.poll();
					if (pre != null) pre.next = cur;
					if (cur.left != null) queue.add(cur.left);
					if (cur.right != null) queue.add(cur.right);
					pre = cur;
				}
			}
			return root;
		}

		/**
		 * 层序遍历,遍历到当前节点N时,处理当前节点N的左右子节点的next指针,不需要借助队列,因为上一层构建好后,必然可以找到下一层的next指针
		 * 每一层都是一个链表,从左到右进行链接;如果拿到链表头,则可以一直往后遍历到链尾;在这个过程中,构建下一层的链表
		 * 每一层链表设置一个虚拟的头节点,那么构建此层链表时,都是判断其当前遍历节点N的左右节点,遍历完就不停的在N节点层链表往后移动
		 */
		public static PerfectNode connectBFSIII(PerfectNode root) {
			if (root == null) return null;
			PerfectNode cur = root, pre = new PerfectNode(-1), head = pre;  // cur当前遍历的节点,pre下一层的链表起始节点,设置空节点
			while (cur != null) {
				if (cur.left != null) { // 当前节点的左节点不为空
					pre.next = cur.left;
					pre = pre.next;
				}
				if (cur.right != null) {
					pre.next = cur.right;
					pre = pre.next;
				}
				cur = cur.next;
				if (cur == null) {  // 当前节点到了链尾,则应该调到下一层的链头;而下一层链头就是刚才构建的链表头
					cur = head.next;
					pre = new PerfectNode(-1);
					head = pre;
				}
			}
			return root;
		}

		public static void main(String[] args) {
			PerfectNode n1 = new PerfectNode(2);
			PerfectNode n2 = new PerfectNode(1);
			PerfectNode n3 = new PerfectNode(3);
			PerfectNode n4 = new PerfectNode(0);
			PerfectNode n5 = new PerfectNode(7);
			PerfectNode n6 = new PerfectNode(9);
			PerfectNode n7 = new PerfectNode(1);
			PerfectNode n8 = new PerfectNode(2);
			PerfectNode n9 = new PerfectNode(1);
			PerfectNode n10 = new PerfectNode(0);
			PerfectNode n11 = new PerfectNode(8);
			PerfectNode n12 = new PerfectNode(8);
			PerfectNode n13 = new PerfectNode(7);
			n1.left = n2;
			n1.right = n3;
			n2.left = n4;
			n2.right = n5;
			n3.left = n6;
			n3.right = n7;
			n4.left = n8;
			n5.left = n9;
			n5.right = n10;
			n7.left = n11;
			n7.right = n12;
			n10.left = n13;
			PerfectNode connect = connectBFSIII(n1);
			System.out.println();
		}
	}

	/**
	 * 101. 对称二叉树
	 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
	 * 示例 1：
	 * 输入：root = [1,2,2,3,4,4,3]
	 * 输出：true
	 * 示例 2：
	 * 输入：root = [1,2,2,null,3,null,3]
	 * 输出：false
	 * 提示：
	 * <p>
	 * 树中节点数目在范围 [1, 1000] 内
	 * -100 <= Node.val <= 100
	 * <p>
	 * 进阶：你可以运用递归和迭代两种方法解决这个问题吗？
	 */
	public static class IsSymmetric {

		/**
		 * 要求用递归和迭代两种方法来解决
		 * 递归:
		 * 其实比较的是根节点的左右两个子树,细分下来,是比的每个节点的左右子节点
		 */
		public static boolean isSymmetric(TreeNode root) {
			if (root == null) return true;
			return compare(root.left, root.right);
		}

		public static boolean compare(TreeNode left, TreeNode right) {
			if (left == null && right == null) {
				return true;
			}
			if (left == null || right == null) {
				return false;
			}
			if (left.val == right.val) {
				return compare(left.left, right.right) && compare(left.right, right.left);
			}
			return false;
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n2_1 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n3_1 = new TreeNode(3);
			TreeNode n4_1 = new TreeNode(4);
			TreeNode n5 = new TreeNode(5);
			TreeNode n6 = new TreeNode(6);
			TreeNode n5_1 = new TreeNode(5);
			TreeNode n6_1 = new TreeNode(6);
			TreeNode n7 = new TreeNode(7);
			TreeNode n8 = new TreeNode(8);
			TreeNode n7_1 = new TreeNode(7);
			n6.left = n7;
			n6.right = n8;
			n6_1.right = n7_1;
			n1.left = n2;
			n1.right = n2_1;
			n2.left = n3;
			n2.right = n4;
			n2_1.left = n4_1;
			n2_1.right = n3_1;
			n3.left = n5;
			n3.right = n6;
			n3_1.left = n6_1;
			n3_1.right = n5_1;
			boolean symmetric = isSymmetric(n1);
			System.out.println(symmetric);
		}
	}
}
