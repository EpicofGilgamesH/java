package test.tree;

import com.alibaba.fastjson.JSON;

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

		/**
		 * 迭代法,其实也是对比根节点的左右子树;常规来说需要借助队列
		 * 队列入队之后,只能确认顺序,无法知晓其是否完全对称,必须明确比较当前节点N的左右子节点是否完全对称;
		 * 如果当前节点N的左右子树完全镜像对称了,那么左右子树放入队列也是完全对称的结构
		 */
		public static boolean isSymmetricIterator(TreeNode root) {
			if (root == null) return true;
			Deque<TreeNode> leftQueue = new LinkedList<>();
			Deque<TreeNode> rightQueue = new LinkedList<>();
			// 根节点的左右节点也需要比较
			if (root.left != null && root.right != null) {
				if (root.left.val != root.right.val) {
					return false;
				}
				leftQueue.add(root.left);
				rightQueue.add(root.right);
			} else if (!(root.left == null && root.right == null)) {
				return false;
			}
			// 超过情况的注意,这里如果为null,入队之后,会引起空指针异常;一定要多注意为空的情况
			if (root.right != null) while (!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
				int ls = leftQueue.size();
				int rs = rightQueue.size();
				if (ls != rs) return false;
				for (; ls > 0; --ls) {
					TreeNode ln = leftQueue.poll();
					TreeNode rn = rightQueue.poll();
					if (ln.left != null && rn.right != null) { // 都不为空比较是否为镜像对称
						if (ln.left.val != rn.right.val) {
							return false;
						}
						leftQueue.add(ln.left);
						rightQueue.add(rn.right);
					} else if (!(ln.left == null && rn.right == null)) {
						return false;
					}
					if (ln.right != null && rn.left != null) {
						if (ln.right.val != rn.left.val) {
							return false;
						}
						leftQueue.add(ln.right);
						rightQueue.add(rn.left);
					} else if (!(ln.right == null && rn.left == null)) {
						return false;
					}
				}
			}
			return leftQueue.isEmpty() && rightQueue.isEmpty();
		}

		/**
		 * 迭代法,借鉴官方的思路,只需要一个队列,同时把互为镜像的节点入队;注意空节点也应该入队,这样才能比较对称性
		 * 也就是说,上面借助两个队列的迭代方法,也是可以不需要比较N节点的左右节点的
		 */
		public static boolean isSymmetricIteratorOfficial(TreeNode root) {
			if (root == null) return true;
			// 根节点不存在镜像节点,入队两次
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			deque.add(root);
			while (!deque.isEmpty()) {
				TreeNode n1 = deque.poll();
				TreeNode n2 = deque.poll();
				if (n1 != null && n2 != null) {
					if (n1.val != n2.val) return false;
					deque.add(n1.left);
					deque.add(n2.right);
					deque.add(n1.right);
					deque.add(n2.left);
				} else if (!(n1 == null && n2 == null)) {
					return false;
				}
			}
			return true;
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
			TreeNode n8_1 = new TreeNode(8);
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
			n6.left = n7;
			n6.right = n8;
			n6_1.right = n7_1;
			n6_1.left = n8_1;

			/*TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n2_1 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n3_1 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n4_1 = new TreeNode(4);
			n1.left = n2;
			n1.right = n2_1;
			n2.left = n3;
			n2_1.right = n3_1;
			n2.right = n4;
			n2_1.left = n4_1;*/

			/*TreeNode n1 = new TreeNode(1);
			TreeNode n0 = new TreeNode(0);
			n1.left = n0;*/
			// System.out.println(isSymmetricIterator(n1));
			String key = "电子设备,配件,音响及音乐设备,计算机配件,计算机硬件,电脑显示器,耳机,手提电脑及电脑,网络产品,相机和摄像机,打印机和扫描仪,电视及DVD设备,适配器,功放,音频和音乐,电池,电缆,译码器,台式电脑,数码相机,无人驾驶飞机,单反相机,DVD播放器,闪存,游戏机,硬盘驱动器,耳机,家庭影院系统,键盘,笔记本电脑,内存,麦克风,调制解调器,混音器,网络,新的显示器,照相机&摄像机,打印机$扫描仪,打印机和扫描仪,投影仪,路由器,安全与监控,音响系统,演讲者,固态硬盘,工作室显示器,开关,电视和DVD,机顶盒,电视,备用电源,二手显示器,视频摄像头,网络摄像头";
			String[] split = key.split(",");
			// List<String> collect = Arrays.stream(split).distinct().collect(Collectors.toList());
			HashSet<String> set = new HashSet<>();
			for (int i = 0; i < split.length; i++) {
				if (set.contains(split[i])) {
					System.out.println("重复:" + split[i]);
				} else {
					set.add(split[i]);
				}
			}
			System.out.println();
		}


	}

	/**
	 * 559. N 叉树的最大深度
	 * 给定一个 N 叉树，找到其最大深度。
	 * <p>
	 * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
	 * <p>
	 * N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。
	 * 示例 1：
	 * 输入：root = [1,null,3,2,4,null,5,6]
	 * 输出：3
	 * 示例 2：
	 * 输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
	 * 输出：5
	 * 提示：
	 * <p>
	 * 树的深度不会超过 1000 。
	 * 树的节点数目位于 [0, 104] 之间。
	 */
	public static class MaxDepth {

		/**
		 * N叉数的最大深度,其实通过层序遍历很容易得到,要求用两种方式求解
		 * 1.dfs深度优先遍历、
		 * 2.bfs层序遍历
		 * 返回值的递归怎么写?还是需要多理解 这里的深度递归时,并不是到最后一层就退出,而是完全递归完,返回最大值
		 */
		public static int maxDepth(Node root) {
			if (root == null) return 0;
			int maxDepth = 0;
			List<Node> children = root.children;
			if (children != null && children.size() > 0) {  // 实际上递归的流程跟想象的思路是反向的,递归找到最底层,然后一层层回去的时候深度+1
				for (Node child : children) {
					maxDepth = Math.max(maxDepth(child), maxDepth); // 每个递归方法栈中都有一个maxDepth,当让发退出时,实际上就在比较出最大的深度
				}
			}
			return maxDepth + 1;
		}

		/**
		 * 层序遍历
		 */
		public static int maxDepthIterator(Node root) {
			if (root == null) return 0;
			Deque<Node> deque = new LinkedList<>();
			deque.add(root);
			int i = 0;
			while (!deque.isEmpty()) {
				i++;
				int size = deque.size();
				for (; size > 0; --size) {
					Node node = deque.poll();
					if (node.children != null) {
						for (Node c : node.children) {
							if (c != null) deque.add(c);
						}
					}
				}
			}
			return i;
		}

		public static void main(String[] args) {
			Node node1 = new Node(1);
			Node node2 = new Node(2);
			Node node3 = new Node(3);
			Node node4 = new Node(4);
			Node node5 = new Node(5);
			node1.children = Arrays.asList(node2, node3, node4, node5);
			Node node6 = new Node(6);
			Node node7 = new Node(7);
			Node node8 = new Node(8);
			Node node9 = new Node(9);
			Node node10 = new Node(10);
			node3.children = Arrays.asList(node6, node7);
			node4.children = Arrays.asList(node8);
			node5.children = Arrays.asList(node9, node10);
			Node node11 = new Node(11);
			Node node12 = new Node(12);
			Node node13 = new Node(13);
			node7.children = Arrays.asList(node11);
			node8.children = Arrays.asList(node12);
			node9.children = Arrays.asList(node13);
			Node node14 = new Node(14);
			node11.children = Arrays.asList(node14);
			// int i = maxDepth(node1);
			int i = maxDepthIterator(node1);
			System.out.println(i);
		}
	}

	/**
	 * 111. 二叉树的最小深度
	 * 给定一个二叉树，找出其最小深度。
	 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
	 * 说明：叶子节点是指没有子节点的节点。
	 * 示例 1：
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：2
	 * 示例 2：
	 * 输入：root = [2,null,3,null,4,null,5,null,6]
	 * 输出：5
	 * 提示：
	 * 树中节点数的范围在 [0, 105] 内
	 * -1000 <= Node.val <= 1000
	 */
	public static class MinDepth {

		/**
		 * dfs深度优先遍历,二叉树,寻找其左右子树的最小高度
		 * 要理解题意,最小深度是从根节点到最近叶子节点的最短路径上的节点数量,如果根节点没有左右子节点,那么根节点就是叶子节点,则返回1;
		 * 如果根节点只有一个子节点,那么根节点不算叶子节点,则是找有'子节点'的那边子树的最小高度
		 */
		public static int minDepth(TreeNode root) {
			if (root == null) return 0;
			if (root.left == null && root.right == null) return 1;
			int min;
			if (root.left != null && root.right != null) {
				min = Math.min(minDepth(root.left), minDepth(root.right)); // 从代码的走向可以判断,minDepth方法的左右节点,至少会执行一次,可以优化
			} else if (root.left == null) {
				min = minDepth(root.right);
			} else {
				min = minDepth(root.left);
			}
			return min + 1;
		}

		public static int minDepthII(TreeNode root) {
			if (root == null) return 0;
			if (root.left == null && root.right == null) return 1;
			int ml = minDepthII(root.left);
			int mr = minDepthII(root.right);
			// 如果左右子树有一个为null,则ml和mr有一个为0;实际上左右子树都为null和都不为null可以合并成三元表达式
			if (root.left == null || root.right == null) return ml + mr + 1;
			return Math.min(ml, mr) + 1;
		}

		public static int minDepthIII(TreeNode root) {
			if (root == null) return 0;
			if (root.left == null && root.right == null) return 1;
			int ml = minDepthIII(root.left);
			int mr = minDepthIII(root.right);
			// 如果左右子树有一个为null,则ml和mr有一个为0;实际上左右子树都为null和都不为null可以合并成三元表达式
			return (root.left == null || root.right == null) ? ml + mr + 1 : Math.min(ml, mr) + 1;
		}

		/**
		 * 层序遍历
		 */
		public static int minDepthIterator(TreeNode root) {
			if (root == null) return 0;
			Deque<TreeNode> deque = new LinkedList<>();
			if (root.left != null) deque.add(root.left);
			if (root.right != null) deque.add(root.right);
			if (deque.size() == 0) return 1;
			int min = 1;
			while (!deque.isEmpty()) {
				min++;
				int size = deque.size();
				for (; size > 0; --size) {
					TreeNode node = deque.poll();
					// 每层中其中有一个为叶子节点,即没有左右子节点时,这层就是最小深度
					if (node.left == null && node.right == null) {
						return min;
					}
					if (node.left != null) deque.add(node.left);
					if (node.right != null) deque.add(node.right);

				}
			}
			return min;
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(3);
			TreeNode n9 = new TreeNode(9);
			TreeNode n20 = new TreeNode(20);
			TreeNode n15 = new TreeNode(15);
			TreeNode n7 = new TreeNode(7);
			n1.left = n9;
			n1.right = n20;
			n20.left = n15;
			n20.right = n7;
			// int i = minDepth(n1);
			// int i = minDepthII(n1);
			int i = minDepthIterator(n1);
			System.out.println(i);
		}
	}

	/**
	 * 222. 完全二叉树的节点个数
	 * 给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。
	 * <p>
	 * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
	 * 并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
	 * 示例 1：
	 * 输入：root = [1,2,3,4,5,6]
	 * 输出：6
	 * 示例 2：
	 * 输入：root = []
	 * 输出：0
	 * 示例 3：
	 * 输入：root = [1]
	 * 输出：1
	 * 提示：
	 * 树中节点的数目范围是[0, 5 * 104]
	 * 0 <= Node.val <= 5 * 104
	 * 题目数据保证输入的树是 完全二叉树
	 * 进阶：遍历树来统计节点是一种时间复杂度为 O(n) 的简单解决方案。你可以设计一个更快的算法吗？
	 */
	public static class CountNodes {

		/**
		 * 个人思路:
		 * 首先要明确完全二叉树的定义:除了最底层节点可能没填满外,其余每层的节点都是满的,同时最底层的节点都集中在该层的最左边位置
		 * 首先得到完全二叉树的层数,其次找出最底层的从哪个节点结束,就是找上一层中哪个节点没有子节点
		 * 完全二叉树的层数,通过最左边节点分支可以求出
		 * 1.dfs深度优先遍历(利用完全二叉树的特性)
		 * 2.层序遍历
		 */
		public static int countNodes(TreeNode root) {
			if (root == null) return 0;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			int count = 1;
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; --size) {
					TreeNode node1 = deque.poll();
					if (node1.left != null) {
						deque.add(node1.left);
						count++;
					}
					if (node1.right != null) {
						deque.add(node1.right);
						count++;
					}
					if (node1.left == null || node1.right == null) return count;
				}
			}
			return count;
		}

		/**
		 * 官方思路,dfs深度优先遍历方式
		 * 深度优先遍历都可以分解为遍历一个节点的左右子树;现在记录遍历当前节点的左右子树高度分别为left和right
		 * 1.当left==right,则说明左、右子树的高度相同,那左子树一定是完全二叉树,右子树则不一定;所以我们可以确定左子树的节点数:sl=2^left
		 * 2.当left!=right,则说明左、右子树的高度不相同,那我们可以得到右子树一定是完全二叉树,所以我们可以确定右子树的节点数:sr=2^right
		 * 一般二叉树的高度,需要通过递归左右子树的高度,取最大的那个来获取;而完全二叉树的高度,则通过最左边一条树枝一直往下遍历即可
		 */
		public static int countNodesOfficial(TreeNode root) {
			if (root == null) return 0;
			int sl = getLevel(root.left);
			int sr = getLevel(root.right);
			if (sl == sr) {
				return countNodesOfficial(root.right) + (1 << sl);
			} else {
				return countNodesOfficial(root.left) + (1 << sr);
			}
		}

		public static int getLevel(TreeNode node) {
			int level = 0;
			while (node != null) {
				level++;
				node = node.left;
			}
			return level;
		}

		/**
		 * 发现官方解答更新了
		 * 1.首先完全二叉树的特性,通过左子节点一直遍历到叶子节点,可以得到树的高度
		 * 2.得到树的高度h,那么需要确认最后一层节点的个数,范围[2^h-2^(h+1)-1]
		 * 3.通过二分来确认范围的值,每次减少一半的区间
		 * 4.指定最后一层的节点序列,如何得知其是否存在呢?我们需要知道它从根节点到最后一层的左右节点指向。
		 * 例如h=3,那么最后一层节点序列为[8...15] 二进制表位为
		 * *  8     9   10   11   12   13   14   15
		 * * 1000 1001 1010 1011 1100 1101 1110 1111
		 * 所以可以得知根节点为1,其后每一位0代表向左节点,1代表向右节点
		 * <p>
		 * [二分查找]范例
		 *
		 * @param root
		 * @return
		 */
		public static int countNodesOfficialI(TreeNode root) {
			if (root == null) return 0;
			TreeNode node = root;
			int h = 0;
			while (node.left != null) {
				node = node.left;
				h++;
			}
			// 最后一层节点范围[2^h...2^(h+1)-1]
			// 二分技巧,要找到最有一个存在的节点,那么如果当前节点存在,则起始位置s=pos;当不存在时结束位置e=pos-1
			// 当s=e时,说明s位置存在,s+1位置不存在,所以返回s位置
			// 还要注意选取中间位置的计算,需要取中间值+1,否则会进入死循环
			int s = 1 << h, e = (s << 1) - 1;
			while (s < e) {
				int pos = s + (e - s + 1) / 2;
				boolean exist = isExist(root, pos, h);
				if (exist) {
					s = pos;
				} else {
					e = pos - 1;
				}
			}
			return s;
		}

		/**
		 * 得到pos的二进制位上某一位是否为0 即与上这一位是1的二进制位
		 * 例如 pos=7; 二进制位: 111
		 * 则需要 与上           010  得到其第二位的值
		 * 再    与上            001 得到其第三位的值
		 *
		 * @param root
		 * @param pos
		 * @param h
		 * @return
		 */
		private static boolean isExist(TreeNode root, int pos, int h) {
			TreeNode node = root;
			int bit = 1 << (h - 1);
			while (node != null && bit > 0) {
				if ((pos & bit) == 0) {
					node = node.left;
				} else {
					node = node.right;
				}
				bit = bit >> 1;
			}
			return node != null;
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n5 = new TreeNode(5);
			TreeNode n6 = new TreeNode(6);
			n1.left = n2;
			n1.right = n3;
			n2.left = n4;
			n2.right = n5;
			n3.left = n6;
			System.out.println(countNodesOfficial(n1));
			System.out.println(countNodesOfficialI(n1));
		}
	}

	/**
	 * 110. 平衡二叉树
	 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
	 * 本题中，一棵高度平衡二叉树定义为：
	 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
	 * 示例 1：
	 * 输入：root = [3,9,20,null,null,15,7]
	 * 输出：true
	 * 示例 2：
	 * 输入：root = [1,2,2,3,3,null,null,4,4]
	 * 输出：false
	 * 示例 3：
	 * 输入：root = []
	 * 输出：true
	 * 提示：
	 * 树中的节点数在范围 [0, 5000] 内
	 * -104 <= Node.val <= 104
	 */
	public static class IsBalanced {

		/**
		 * 个人思路:
		 * 平衡二叉树定义:二叉树中的每个节点,其左右两个子树的高度差的绝对值不能超过1,这是典型的dfs递归思路
		 * 如果不是平衡二叉树,是不是根节点就能判断出来呢?? 不行,[1,2,2,3,null,null,3,4,null,null,4] 这个案例可以说明*****
		 * 如果是满二叉树,高度遍历时时间复杂度是O(n),高度遍历的同时,还需要遍历每一个节点,时间复杂度为O(n^2)
		 * 从顶向叶子节点的遍历,level函数求高度时,会重复调用
		 */
		public static boolean isBalanced(TreeNode root) {
			if (root == null) return true;
			int abs = Math.abs(level(root.left) - level(root.right));
			if (abs > 1) {
				return false;
			} else {
				return isBalanced(root.left) && isBalanced(root.right);
			}
		}

		public static int level(TreeNode root) {
			if (root == null) return 0;
			return Math.max(level(root.left), level(root.right)) + 1;
		}

		/**
		 * 官方给出的从叶子节点向数顶节点遍历的方式,避免了level函数的重复调用
		 * 树的后续遍历,先遍历左右子节点,再遍历顶节点;则先判断左右子树是否平衡,再判断以当前节点为根的树是否平衡
		 * 如果一颗子树是平衡的,则返回其高度,否者返回-1
		 * <p>
		 * 这种思路是如何想到的呢???
		 */
		public static boolean isBalancedII(TreeNode root) {
			if (root == null) return true;
			return height(root) != -1;
		}

		public static int height(TreeNode root) {
			if (root == null) return 0;
			int left = height(root.left);
			int right = height(root.right);
			// 左子树不平衡 或者 右子树不平衡 或者当前遍历节点为根的树不平衡,则直接返回false
			if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
				return -1;
			}
			// 如果当前遍历节点为根的树平衡,则返回其高度
			return Math.max(left, right) + 1;
		}


		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n2_2 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n3_3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n4_4 = new TreeNode(4);
			n1.left = n2;
			n1.right = n2_2;
			n2.left = n3;
			n2_2.right = n3_3;
			n3.left = n4;
			n3_3.right = n4_4;
			boolean balanced = isBalancedII(n1);
			System.out.println(balanced);
		}
	}

	/**
	 * 404. 左叶子之和
	 * 给定二叉树的根节点 root ，返回所有左叶子之和。
	 * 示例 1：
	 * 输入: root = [3,9,20,null,null,15,7]
	 * 输出: 24
	 * 解释: 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
	 * 示例 2:
	 * 输入: root = [1]
	 * 输出: 0
	 * <p>
	 * 提示:
	 * 节点数在 [1, 1000] 范围内
	 * -1000 <= Node.val <= 1000
	 */
	public static class SumOfLeftLeaves {

		/**
		 * 左叶子的定义:即其为叶子节点-没有左右子节点,且他是父节点的左子节点
		 * 深度优先遍历
		 */
		public static int sumOfLeftLeaves(TreeNode root) {
			return sum(root, false);
		}

		/**
		 * 简单的递归思路,竟如此难写出来;最重要的还是要理清晰每一个子过程的步骤;按子过程的详细步骤来进行
		 * 从上到下的前序遍历,会有很多重复递归部分,考虑后续遍历 先遍历左右子节点再遍历父节点
		 */
		public static int sum(TreeNode root, boolean isLeft) {
			// 总数为左子树+右子树
			// 当前节点的左节点
			if (root == null) return 0;
			if (root.left == null && root.right == null) { // 叶子节点,且为左节点
				if (isLeft)
					return root.val;
				else return 0;
			}
			return sum(root.left, true) + sum(root.right, false);
		}

		/**
		 * 从下到上递归,后续遍历
		 */
		public static int sumOfLeftLeavesII(TreeNode root) {
			return sumII(root, false);
		}

		public static int sumII(TreeNode root, boolean isLeft) {
			if (root == null) return 0;
			// 先遍历左右节点,再处理顶节点
			int left = sumII(root.left, true);
			int right = sumII(root.right, false);
			if (root.left == null && root.right == null) {
				if (isLeft) return root.val;
				else return 0;
			}
			return left + right;
		}

		/**
		 * 层序遍历方式
		 */
		public static int sumOfLeftLeavesIterator(TreeNode root) {
			if (root == null) return 0;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			int sum = 0;
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; --size) {
					TreeNode node = deque.poll();
					if (node.left != null) {
						deque.add(node.left);
						if (node.left.left == null && node.left.right == null) {
							sum += node.left.val;
						}
					}
					if (node.right != null) deque.add(node.right);
				}
			}
			return sum;
		}

		public static void main(String[] args) {
			TreeNode n3 = new TreeNode(3);
			TreeNode n9 = new TreeNode(9);
			TreeNode n20 = new TreeNode(20);
			TreeNode n15 = new TreeNode(15);
			TreeNode n7 = new TreeNode(7);
			n3.left = n9;
			n3.right = n20;
			n20.left = n15;
			n20.right = n7;
			// int i = sumOfLeftLeaves(n3);
			// int i = sumOfLeftLeavesII(n3);
			int i = sumOfLeftLeavesIterator(n3);
			System.out.println(i);
		}
	}

	/**
	 * 513. 找树左下角的值
	 * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
	 * <p>
	 * 假设二叉树中至少有一个节点。
	 * 示例 1:
	 * 输入: root = [2,1,3]
	 * 输出: 1
	 * 示例 2:
	 * 输入: [1,2,3,4,null,5,6,null,null,7]
	 * 输出: 7
	 * 提示:
	 * 二叉树的节点个数的范围是 [1,104]
	 * -231 <= Node.val <= 231 - 1
	 */
	public static class FindBottomLeftValue {

		/**
		 * 个人思路:
		 * 最底层、最左边的节点定义:深度最大的左叶子节点???? 深度:从根节点到叶子节点路径上所包含的节点数
		 * 题意理解错误,但是可以作为一个新题来解,还可以将时间负责度降低到O(n) 现在两层递归是O(n^2)
		 */
		public static int findBottomLeftValue(TreeNode root) {
			if (root == null) return 0;
			info info = find(root, false);
			return info.getVal();
		}

		/**
		 * 分解成子问题:每个节点的 左子树最底层左叶子节点和 右子树最底层左叶子节点中,深度更深的节点值
		 */
		public static info find(TreeNode node, boolean isLeft) {
			if (node == null) return new info(0, 0);
			// 先得到左右子树的最深左叶子节点值
			info left = find(node.left, true);
			info right = find(node.right, false);
			if (node.left == null && node.right == null) { // 叶子节点
				if (isLeft) {
					return new info(level(node), node.val);
				}
			}
			return level(node.left) > level(node.right) ? left : right;
		}

		static class info {
			private int level;
			private int val;

			public info(int level, int val) {
				this.level = level;
				this.val = val;
			}

			public int getLevel() {
				return level;
			}

			public void setLevel(int level) {
				this.level = level;
			}

			public int getVal() {
				return val;
			}

			public void setVal(int val) {
				this.val = val;
			}
		}

		/**
		 * 节点深度 左右子树的深度较大值+1
		 */
		public static int level(TreeNode node) {
			if (node == null) return 0;
			int l = level(node.left);
			int r = level(node.right);
			return Math.max(l, r) + 1;
		}

		private static int curVal = 0;
		private static int curLevel = 0;

		/**
		 * 正确的题意:左下角值,是指最下一层,最左边的节点值;即中序比遍历遍历时,每一层第一个遍历的节点肯定是最左侧节点
		 */
		public static int findBottomLeftValueII(TreeNode root) {
			dfs(root, 0);
			return curVal;
		}

		/**
		 * 重复子问题,先遍历左节点 中序遍历;需要复习的递归思路
		 */
		public static void dfs(TreeNode root, int level) {
			if (root == null) return;
			level++;
			dfs(root.left, level);
			if (level > curLevel) { // 当新遍历到下一层时,记录当前节点值和当前的层数;其余情况不更新数据
				curLevel = level;
				curVal = root.val;
			}
			dfs(root.right, level);
		}

		/**
		 * 层序遍历 每一层元素从右向左遍历
		 */
		public static int findBottomLeftValueIII(TreeNode root) {
			if (root == null) return 0;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(root);
			TreeNode node = null;
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; --size) {
					node = deque.poll(); // 拿队头元素
					if (node.right != null) deque.add(node.right);
					if (node.left != null) deque.add(node.left);
				}
			}
			return node.val;
		}

		/**
		 * 前序遍历
		 */
		private static void preOrder(TreeNode root) {
			if (root == null) return;
			System.out.print(root.val + " ");
			preOrder(root.left);
			preOrder(root.right);
		}

		/**
		 * 中序遍历
		 */
		private static void inOrder(TreeNode root) {
			if (root == null) return;
			inOrder(root.left);
			System.out.print(root.val + " ");
			inOrder(root.right);
		}

		/**
		 * 后序遍历
		 */
		private static void postOrder(TreeNode root) {
			if (root == null) return;
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.val + " ");
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n5 = new TreeNode(5);
			TreeNode n6 = new TreeNode(6);
			TreeNode n7 = new TreeNode(7);
			n1.left = n2;
			n1.right = n3;
			n2.left = n4;
			n3.left = n5;
			n3.right = n6;
			n5.left = n7;
			// int bottomLeftValue = findBottomLeftValue(n1);
			// System.out.println(bottomLeftValue);
			preOrder(n1);
			System.out.println();
			inOrder(n1);
			System.out.println();
			postOrder(n1);
			System.out.println();
			// int bottomLeftValueII = findBottomLeftValueII(n1);
			int bottomLeftValueII = findBottomLeftValueIII(n1);
			System.out.println(bottomLeftValueII);

		}
	}

	/**
	 * 257. 二叉树的所有路径
	 * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
	 * <p>
	 * 叶子节点 是指没有子节点的节点。
	 * 示例 1：
	 * 输入：root = [1,2,3,null,5]
	 * 输出：["1->2->5","1->3"]
	 * 示例 2：
	 * 输入：root = [1]
	 * 输出：["1"]
	 * 提示：
	 * 树中节点的数目在范围 [1, 100] 内
	 * -100 <= Node.val <= 100
	 */
	public static class BinaryTreePaths {

		/**
		 * 个人思路:
		 * 这是典型的回溯逻辑,怎么实现回溯呢?每次从根节点到达叶子节点后,需要回到根节点,继续下一次遍历
		 * 针对每一个节点,其都有两种遍历方式,走往左节点的路径和走往右节点的路径
		 */
		public static List<String> binaryTreePaths(TreeNode root) {
			List<String> list = new ArrayList<>();
			dfs(root, list, "");
			return list;
		}

		/**
		 * 前序遍历 根->左->右  根->左遍历完了,如何回到根继续往右呢????
		 */
		public static void dfs(TreeNode root, List<String> list, String path) {
			if (root != null) {
				StringBuilder sb = new StringBuilder(path);
				sb.append(root.val).append("->");
				if (root.left == null && root.right == null) {// 当前节点是叶子节点
					list.add(sb.substring(0, sb.length() - 2));
				}
				dfs(root.left, list, sb.toString());
				// 回溯是指,遍历子树之后,需要退回到当前节点,怎么退回呢??
				// 当前遍历节点为1时,当没有回溯操作时,遍历完左子节点后,得到路径'1->2->4',在遍历右子节点时,路径还是1->2->4
				// 此时要将原路径存储的信息回溯到1->,则要把遍历左子节点的后续遍历全部退回;
				// 核心在左子树遍历直到叶子节点,然后回到当前节点时,路径字符串,应该记录当时栈中的值;所以不能在遍历到叶子节点时return;
				// 如果在 dfs_left后return,进入到dfs_right时,路径字符串不会回溯

				dfs(root.right, list, sb.toString());
			}
		}

		public static List<String> binaryTreePaths1(TreeNode root) {
			List<String> list = new ArrayList<>();
			dfs1(root, list, "");
			return list;
		}

		/**
		 * 为什么这样return没有回溯呢????
		 * 分析递归树
		 *      1
		 *     / \
		 *   2    3
		 *  /    / \
		 * 4    5   6
		 *     /
		 *    7
		 * 1 -> 2 -> 4 -> 左null ->
		 *           4 -> 右null ->
		 *      2->右null
		 * 1 -> 3 -> 5 -> 7 -> 左null ->
		 *                7 -> 右null ->
		 *           5 -> 右null ->
		 *      3 -> 6 -> 左null ->
		 *           6 -> 右null ->
		 *
		 * 当root走到节点4时,进入左节点的递归栈,节点4的左子节点为null,直接return;此时进入节点4的递归栈;继续下一步,进入右节点的
		 * 递归栈,直接return;此时回到节点2的递归栈,节点2的右子节点为null,直接return;此时回到节点1的递归栈,进入其右节点3的递归栈;
		 * 以此递归栈的调用,可实现回溯;此时可以看出,深度优先遍历就是回溯************
		 *
		 * 回溯很多时候应用在搜索这类问题上,类似于没有搜索;有规律的枚举所有可能的解,避免遗漏和重复.
		 */
		public static void dfs1(TreeNode root, List<String> list, String path) {
			if (root == null) return;
			StringBuilder sb = new StringBuilder(path);
			sb.append(root.val).append("->");
			if (root.left == null && root.right == null) {// 当前节点是叶子节点
				list.add(sb.substring(0, sb.length() - 2));
			}
			dfs1(root.left, list, sb.toString());
			// 回溯是指,遍历子树之后,需要退回到当前节点,怎么退回呢??
			// 当前遍历节点为1时,当没有回溯操作时,遍历完左子节点后,得到路径'1->2->4',在遍历右子节点时,路径还是1->2->4
			// 此时要将原路径存储的信息回溯到1->,则要把遍历左子节点的后续遍历全部退回;
			// 核心在左子树遍历直到叶子节点,然后回到当前节点时,路径字符串,应该记录当时栈中的值;所以不能在遍历到叶子节点时return;
			// 如果在 dfs_left后return,进入到dfs_right时,路径字符串不会回溯
			dfs1(root.right, list, sb.toString());
		}

		/**
		 * 通过层序遍历
		 * 层序遍历思路是一层层的遍历节点元素,那么如何才能记录每一层元素的路径呢?
		 * 显然我们需要一个数据结构来记录每个元素的路径,首先想到的是hash,节点为key,路径为value;
		 * 当然我们也可以用一个队列,跟元素的队列保持一样的操作,这样就达到了一一对应的效果
		 */
		public static List<String> binaryTreePathsIterator(TreeNode root) {
			List<String> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> nodeDeque = new LinkedList<>();
			nodeDeque.add(root);
			Deque<String> pathDeque = new LinkedList<>();
			pathDeque.add(root.val + "->");
			while (!nodeDeque.isEmpty()) {
				int size = nodeDeque.size();
				for (; size > 0; --size) {
					TreeNode node = nodeDeque.poll();
					String path = pathDeque.poll();
					if (node.left != null) {
						nodeDeque.add(node.left);
						pathDeque.add(path + node.left.val + "->");
					}
					if (node.right != null) {
						nodeDeque.add(node.right);
						pathDeque.add(path + node.right.val + "->");
					}
					if (node.left == null && node.right == null) {
						list.add(path.substring(0, path.length() - 2));
					}
				}
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n5 = new TreeNode(5);
			TreeNode n6 = new TreeNode(6);
			TreeNode n7 = new TreeNode(7);
			n1.left = n2;
			n1.right = n3;
			n2.left = n4;
			n3.left = n5;
			n3.right = n6;
			n5.left = n7;
			List<String> strings1 = binaryTreePaths1(n1);
			System.out.println(strings1);
			List<String> strings = binaryTreePaths(n1);
			System.out.println(strings);
			List<String> strings2 = binaryTreePathsIterator(n1);
			System.out.println(strings2);
		}
	}

	/**
	 * 226. 翻转二叉树
	 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
	 * 示例 1：
	 * <p>
	 * 输入：root = [4,2,7,1,3,6,9]
	 * 输出：[4,7,2,9,6,3,1]
	 * 示例 2：
	 * 输入：root = [2,1,3]
	 * 输出：[2,3,1]
	 * 示例 3：
	 * 输入：root = []
	 * 输出：[]
	 * 提示：
	 * 树中节点数目范围在 [0, 100] 内
	 * -100 <= Node.val <= 100
	 */
	public static class InvertTree {

		/**
		 * 个人思路:
		 * 1.层序遍历,反向构造树,无法原地进行构造新的树,思路比较简单
		 * 2.递归,可以把整个树分解成最小的处理过程:将某个节点的左右子树进行交换
		 *
		 * @param root
		 * @return
		 */
		public static TreeNode invertTree(TreeNode root) {
			invert(root);
			return root;
		}

		/**
		 * 递归方式
		 *
		 * @param node
		 */
		private static void invert(TreeNode node) {
			if (node == null) return;
			if (node.left == null && node.right == null) return;
			TreeNode temp = node.left;
			node.left = node.right;
			node.right = temp;
			if (node.left != null) invert(node.left);
			if (node.right != null) invert(node.right);
		}

		/**
		 * 分层遍历-每一层遍历节点,然后翻转其左右节点
		 *
		 * @param root
		 * @return
		 */
		public static TreeNode invertTreeI(TreeNode root) {
			if (root == null) return null;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.addLast(root);
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; --size) {
					TreeNode crr = deque.pollFirst();
					TreeNode temp = crr.left;
					crr.left = crr.right;
					crr.right = temp;
					if (crr.left != null) deque.addLast(crr.left);
					if (crr.right != null) deque.addLast(crr.right);
				}
			}
			return root;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(4);
			TreeNode n2 = new TreeNode(2);
			TreeNode n7 = new TreeNode(7);
			root.left = n2;
			root.right = n7;
			TreeNode n1 = new TreeNode(1);
			TreeNode n3 = new TreeNode(3);
			n2.left = n1;
			n2.right = n3;
			TreeNode n6 = new TreeNode(6);
			TreeNode n9 = new TreeNode(9);
			n7.left = n6;
			n7.right = n9;
			// TreeNode treeNode = invertTree(root);
			invertTreeI(root);
			System.out.println();
		}
	}

	/**
	 * 105. 从前序与中序遍历序列构造二叉树
	 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
	 * 示例 1:
	 * <p>
	 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
	 * 输出: [3,9,20,null,null,15,7]
	 * 示例 2:
	 * 输入: preorder = [-1], inorder = [-1]
	 * 输出: [-1]
	 * 提示:
	 * 1 <= preorder.length <= 3000
	 * inorder.length == preorder.length
	 * -3000 <= preorder[i], inorder[i] <= 3000
	 * preorder 和 inorder 均 无重复 元素
	 * inorder 均出现在 preorder
	 * preorder 保证 为二叉树的前序遍历序列
	 * inorder 保证 为二叉树的中序遍历序列
	 */
	public static class BuildTree {

		/**
		 * 中序遍历 左-中-右
		 *
		 * @param node
		 */
		public static void inorder(TreeNode node) {
			if (node == null) return;
			inorder(node.left);
			System.out.print(node.val + " ");
			inorder(node.right);
		}

		/**
		 * 前序遍历 中-左-右
		 *
		 * @param node
		 */
		public static void preorder(TreeNode node) {
			if (node == null) return;
			System.out.print(node.val + " ");
			preorder(node.left);
			preorder(node.right);
		}

		/**
		 * 后序遍历 左-右-根
		 *
		 * @param node
		 */
		public static void postorder(TreeNode node) {
			if (node == null) return;
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.val + " ");
		}

		/**
		 * 首先拿到这个题后,要求通过一颗树的前序遍历数组和中序遍历数组,来构造出这个树,简直毫无头绪
		 * 没有头绪的原因是对前序遍历和中序遍历没有深刻的理解,所以首先要分析这两种遍历的特点和规律
		 * 例如这颗二叉树
		 * *        3
		 * *      /  \
		 * *     9   20
		 * *    / \  / \
		 * *   4  8 15  7
		 * 前序:3 9 4 8 20 15 7  可以发现其为:[根,左子树,右子树] 而其中的左右子树,也同样符合[根,左子树,右子树]
		 * 中序:4 9 8 3 15 20 7  可以发现其为:[左子树,根,右子树] 而其中的左右子树,也同样符合[左子树,根,右子树]
		 * 所以前序中,可以得到第一个元素肯定为根其树的根节点,通过这个根节点即可以在中序中找到(题目要求节点值不能重复)
		 * 左子树和右子树,然后继续以上操作.当在中序中找到的左子树为空或者只有一个元素,则遍历结束并进行树的构造
		 * <p>
		 * 这里只记录preorder数组需要处理子树节点范围[s,e]没法完成
		 * 需要记录 preorder数组需要处理子树节点范围[ps,pe] 和 记录inorder数组需要处理子节点范围[is,ie]
		 * 第一步:
		 * 前序数组[0,n-1]
		 * 中序数组[0,n-1]
		 * 前序根节点3,在中序的index=3,那么得到左子树节点数leftCount= index-is 右子树的节点数rightCount= ie-index
		 * 那么 前序数组左[ps+1,ps+leftCount] 右[ps+leftCount+1,pe]
		 * *   中序数组左[is,index-1] 右[index+1,ie]
		 */
		public static TreeNode build(int[] preorder, int[] inorder, int ps, int pe, int is, int ie) {
			if (ps > pe) return null;
			int first = preorder[ps]; // 根节点值
			int index = indexMap.get(first); // 根节点在中序数组的位置
			TreeNode root = new TreeNode(preorder[ps]); // 构造跟节点
			int leftCount = index - is; // 在中序数组中通过根节点的位置,计算左子树的节点数量
			root.left = build(preorder, inorder, ps + 1, ps + leftCount, is, index - 1); // 递归构建左子树连接到根节点
			root.right = build(preorder, inorder, ps + leftCount + 1, pe, index + 1, ie);  // 递归构建右子树连接到根节点
			return root;
		}

		private static Map<Integer, Integer> indexMap;  //<value,index>

		public static TreeNode buildTree(int[] preorder, int[] inorder) {
			indexMap = new HashMap<>(preorder.length);
			for (int i = 0; i < preorder.length; i++) {
				indexMap.put(inorder[i], i);
			}
			return build(preorder, inorder, 0, preorder.length - 1, 0, preorder.length - 1);
		}

		private static int indexOf(int[] array, int v) {
			for (int i = 0; i < array.length; ++i) {
				if (array[i] == v) return i;
			}
			return -1;
		}

		public static void main(String[] args) {

			// 中序遍历
			TreeNode n_3 = new TreeNode(3);
			TreeNode n_9 = new TreeNode(9);
			TreeNode n_20 = new TreeNode(20);
			TreeNode n_4 = new TreeNode(4);
			TreeNode n_8 = new TreeNode(8);
			TreeNode n_15 = new TreeNode(15);
			TreeNode n_7 = new TreeNode(7);
			TreeNode n_2 = new TreeNode(2);
			TreeNode n_6 = new TreeNode(6);
			n_3.left = n_9;
			n_3.right = n_20;
			n_9.left = n_4;
			n_9.right = n_8;
			n_20.left = n_15;
			n_20.right = n_7;
			n_8.left = n_2;
			n_8.right = n_6;
			inorder(n_3);
			System.out.println();
			preorder(n_3);
			System.out.println();
			postorder(n_3);
			System.out.println();
			TreeNode treeNode = buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
			System.out.println();
		}
	}

	/**
	 * 103. 二叉树的锯齿形层序遍历
	 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
	 */
	public static class ZigzagLevelOrder {

		/**
		 * 锯齿形层序遍历,那还是层序遍历,只是每一层遍历的顺序进行交替
		 * FIFO 偶数层子节点遍历 先右后左 奇数层子节点遍历先左后右
		 * 这里发现一个问题,层序遍历肯定是先进先出,才能实现一层层的遍历.那交替反向如何实现呢?这里使用LinkedList可以遍历元素的特点来实现.
		 * 那如果只能使用Deque如何实现呢?
		 * 官方思路也差不多的技巧,在添加都list集合中时进行倒序操作,将list设置为LinkedList
		 *
		 * @param root
		 * @return
		 */
		public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
			LinkedList<TreeNode> deque = new LinkedList<>();
			List<List<Integer>> list = new ArrayList<>();
			deque.addFirst(root);
			int level = 1;
			while (!deque.isEmpty()) {
				int size = deque.size();
				list.add(getLevelList(deque, level));
				for (; size > 0; size--) {
					TreeNode crr = deque.pollLast();
					if (crr.left != null) deque.addFirst(crr.left);
					if (crr.right != null) deque.addFirst(crr.right);
				}
				level++;
			}
			return list;
		}

		private static List<Integer> getLevelList(LinkedList<TreeNode> deque, int level) {
			// 奇数层反向
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < deque.size(); i++) {
				if (level % 2 == 1) {
					list.add(deque.get(deque.size() - i - 1).val);
				} else {
					list.add(deque.get(i).val);
				}
			}
			return list;
		}

		/**
		 * @param root
		 * @return
		 */
		public static List<List<Integer>> zigzagLevelOrderOfficial(TreeNode root) {
			LinkedList<TreeNode> deque = new LinkedList<>();
			List<List<Integer>> list = new ArrayList<>();
			deque.addFirst(root);
			int level = 1;
			while (!deque.isEmpty()) {
				LinkedList<Integer> li = new LinkedList<>();
				int size = deque.size();
				for (; size > 0; size--) {
					TreeNode crr = deque.pollLast();
					if (level % 2 == 0) { // 偶数层是反向的
						li.addFirst(crr.val);
					} else {
						li.addLast(crr.val);
					}
					if (crr.left != null) deque.addFirst(crr.left);
					if (crr.right != null) deque.addFirst(crr.right);
				}
				level++;
				list.add(li);
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			root.left = n1;
			root.right = n2;
			TreeNode n4 = new TreeNode(4);
			TreeNode n10 = new TreeNode(10);
			n1.left = n4;
			n1.right = n10;
			TreeNode n15 = new TreeNode(15);
			TreeNode n7 = new TreeNode(7);
			n2.left = n15;
			n2.right = n7;
			List<List<Integer>> lists = zigzagLevelOrderOfficial(root);
			System.out.println(lists);
		}
	}

	/**
	 * 236. 二叉树的最近公共祖先
	 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
	 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
	 * 示例 1：
	 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
	 * 输出：3
	 * 解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
	 * 示例 2：
	 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
	 * 输出：5
	 * 解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
	 * 示例 3：
	 * 输入：root = [1,2], p = 1, q = 2
	 * 输出：1
	 * 提示：
	 * 树中节点数目在范围 [2, 105] 内。
	 * -109 <= Node.val <= 109
	 * 所有 Node.val 互不相同 。
	 * p != q
	 * p 和 q 均存在于给定的二叉树中。
	 */
	public static class LowestCommonAncestor {

		/**
		 * 个人思路:
		 * 最直接的思路,从根节点开始找到p和q节点的路径 a,b Deque数据结构
		 * 然后通过a,b集合从p,q节点开始在路径上找到一个最近的匹配项
		 * 二叉树搜索,得到指定节点的路径
		 *
		 * @param root
		 * @param p
		 * @param q
		 * @return
		 */
		public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
			LinkedList<TreeNode> dequeP = new LinkedList<>();
			LinkedList<TreeNode> dequeQ = new LinkedList<>();
			getPath(root, p, dequeP, true);
			getPath(root, q, dequeQ, false);
			for (int i = pList.size() - 1; i >= 0; i--) {
				TreeNode np = pList.get(i);
				for (int j = qList.size() - 1; j >= 0; j--) {
					if (np.val == qList.get(j).val) {
						return np;
					}
				}
			}
			return null;
		}

		/**
		 * 当拿到p,q节点从根节点开始的路径之后,其实不需要两层循环进行比对;
		 * 从根节点开始往下,当出现不是同一个节点时,那上一个节点就是最近公共祖先节点
		 *
		 * @param root
		 * @param p
		 * @param q
		 * @return
		 */
		public static TreeNode lowestCommonAncestorI(TreeNode root, TreeNode p, TreeNode q) {
			LinkedList<TreeNode> dequeP = new LinkedList<>();
			LinkedList<TreeNode> dequeQ = new LinkedList<>();
			getPath(root, p, dequeP, true);
			getPath(root, q, dequeQ, false);
			TreeNode ancestor = null;
			for (int i = 0; i < pList.size() && i < qList.size(); ++i) {
				if (pList.get(i) == qList.get(i)) {
					ancestor = pList.get(i);
				} else break;
			}
			return ancestor;
		}

		private static List<TreeNode> pList;
		private static List<TreeNode> qList;

		private static void getPath(TreeNode root, TreeNode node, Deque<TreeNode> path, boolean isP) {
			path.addLast(root);
			if (root.val == node.val) {
				if (isP) pList = new ArrayList<>(path);
				else qList = new ArrayList<>(path);
				return;  // 找到该节点则退出
			}
			if (root.left != null) {
				getPath(root.left, node, path, isP);
				path.removeLast();
			}
			if (root.right != null) {
				getPath(root.right, node, path, isP);
				path.removeLast();
			}
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode n1 = new TreeNode(9);
			TreeNode n2 = new TreeNode(20);
			root.left = n1;
			root.right = n2;
			TreeNode n4 = new TreeNode(4);
			TreeNode n10 = new TreeNode(10);
			n1.left = n4;
			n1.right = n10;
			TreeNode n15 = new TreeNode(15);
			TreeNode n7 = new TreeNode(7);
			n2.left = n15;
			n2.right = n7;
			TreeNode treeNode = lowestCommonAncestorI(root, n4, n10);
			System.out.println(treeNode == null ? "" : treeNode.val);
		}
	}

	/**
	 * 230. 二叉搜索树中第K小的元素
	 * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
	 * 示例 1：
	 * 输入：root = [3,1,4,null,2], k = 1
	 * 输出：1
	 * 示例 2：
	 * 输入：root = [5,3,6,2,4,null,null,1], k = 3
	 * 输出：3
	 * 提示：
	 * 树中的节点数为 n 。
	 * 1 <= k <= n <= 104
	 * 0 <= Node.val <= 104
	 * 进阶：如果二叉搜索树经常被修改（插入/删除操作）并且你需要频繁地查找第 k 小的值，你将如何优化算法？
	 */
	public static class KthSmallest {

		/**
		 * 二叉查找树的定义:即节点的左节点比其小,右节点比其大,根节点的左右子树都满足二叉查找树的特性
		 * 通过中序遍历,最后一次左节点遍历时,即为最小的节点.然后回溯遍历第k-1个节点则是第k大的
		 *
		 * @param root
		 * @param k
		 * @return
		 */
		public static int kthSmallest(TreeNode root, int k) {
			if (root == null) return -1;
			preOrder(root, k);
			return value;
		}

		private static int value;
		private static int n = 0;

		private static void preOrder(TreeNode root, int k) {
			if (root == null) return;
			preOrder(root.left, k);
			if (++n == k) {  // n是记录遍历到的节点数量
				value = root.val;
				return;
			}
			preOrder(root.right, k);
		}

		/**
		 * 模拟栈遍历-中序遍历;那怎么确定中序遍历到了最左边的节点呢?第一次从栈中弹出的元素没有字节点,这开始计数为1
		 * 也就是最左边的节点,且最小
		 *
		 * @param root
		 * @param k
		 * @return
		 */
		public static int kthSmallestI(TreeNode root, int k) {
			Deque<TreeNode> deque = new LinkedList<>();
			while (root != null || !deque.isEmpty()) {
				while (root != null) { // 先拿到最左边节点
					deque.push(root);
					root = root.left;
				}
				root = deque.pop(); // 拿出最左节点,开始往右子树遍历
				--k;
				if (k == 0) break;
				root = root.right;
			}
			return root.val;
		}

		public static void main(String[] args) {
			TreeNode n_10 = new TreeNode(10);
			TreeNode n_2 = new TreeNode(2);
			TreeNode n_14 = new TreeNode(14);
			TreeNode n_1 = new TreeNode(1);
			TreeNode n_4 = new TreeNode(4);
			TreeNode n_3 = new TreeNode(3);
			TreeNode n_5 = new TreeNode(5);
			TreeNode n_12 = new TreeNode(12);
			TreeNode n_15 = new TreeNode(15);
			n_10.left = n_2;
			n_10.right = n_14;
			n_2.left = n_1;
			n_2.right = n_4;
			n_4.left = n_3;
			n_4.right = n_5;
			n_14.left = n_12;
			n_14.right = n_15;

			int i = kthSmallestI(n_10, 3);
			System.out.println(i);
		}
	}

	/**
	 * 235. 二叉搜索树的最近公共祖先
	 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
	 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
	 * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
	 * 示例 1:
	 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
	 * 输出: 6
	 * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
	 * 示例 2:
	 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
	 * 输出: 2
	 * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
	 * 说明:
	 * 所有节点的值都是唯一的。
	 * p、q 为不同节点且均存在于给定的二叉搜索树中。
	 */
	public static class LowestCommonAncestorI {

		/**
		 * 这一题与236的思路是一模一样的
		 * 实际上不一样,二叉查找树是有大小规范的,有利于找到其最近公共祖先
		 * 不用左右子树都进行遍历了,根据与父节点的比较,来寻找p,q节点的路径
		 *
		 * @param root
		 * @param p
		 * @param q
		 * @return
		 */
		public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
			List<TreeNode> pathP = getPath(root, p);
			List<TreeNode> pathQ = getPath(root, q);
			// 得到p,q两节点的路径,实际上从上往下,遇到第一个不相同的节点时,那么上一个节点就是其最小公共祖先节点
			TreeNode ancestor = null;
			for (int i = 0; i < pathP.size() && i < pathQ.size(); i++) {
				if (pathP.get(i) == pathQ.get(i)) {
					ancestor = pathP.get(i);
				} else {
					break;
				}
			}
			return ancestor;
		}

		/**
		 * 二叉查找树在查找指定节点的路径时,不需要回溯遍历,能明确找到一条路径
		 *
		 * @param root
		 * @param n
		 * @return
		 */
		private static List<TreeNode> getPath(TreeNode root, TreeNode n) {
			List<TreeNode> list = new ArrayList<>();
			while (root != null) {
				list.add(root);
				if (root.val > n.val) { // 往左子树向下
					root = root.left;
				} else if (root.val < n.val) { // 往右子树向下
					root = root.right;
				} else {
					break;
				}
			}
			return list;
		}

		/**
		 * 上面使用寻找p,q节点的路径来匹配出最近公共祖先
		 * 当然二叉查找树的特性可以得出,从根节点开始:
		 * 1.p,q < root 那么最小公共祖先肯定在左子树
		 * 2.p,q > root 那么最小公共祖先肯定在右子树
		 * 3.p<=root<=q ||q<=root<=q 说明p,q为root本身或者是root节点的左右子树中的节点,那么root就是其最近公共祖先
		 */
		public static TreeNode lowestCommonAncestorI(TreeNode root, TreeNode p, TreeNode q) {
			while (root != null) {
				if (root.val < p.val && root.val < q.val) {
					root = root.right;
				} else if (root.val > p.val && root.val > q.val) {
					root = root.left;
				} else {
					break;
				}
			}
			return root;
		}

		public static void main(String[] args) {
			TreeNode n_10 = new TreeNode(10);
			TreeNode n_2 = new TreeNode(2);
			TreeNode n_14 = new TreeNode(14);
			TreeNode n_1 = new TreeNode(1);
			TreeNode n_4 = new TreeNode(4);
			TreeNode n_3 = new TreeNode(3);
			TreeNode n_5 = new TreeNode(5);
			TreeNode n_12 = new TreeNode(12);
			TreeNode n_15 = new TreeNode(15);
			n_10.left = n_2;
			n_10.right = n_14;
			n_2.left = n_1;
			n_2.right = n_4;
			n_4.left = n_3;
			n_4.right = n_5;
			n_14.left = n_12;
			n_14.right = n_15;

			TreeNode treeNode = lowestCommonAncestorI(n_10, n_3, n_5);
			System.out.println(treeNode.val);
		}
	}

	/**
	 * 207. 课程表
	 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
	 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
	 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
	 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：numCourses = 2, prerequisites = [[1,0]]
	 * 输出：true
	 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
	 * 示例 2：
	 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
	 * 输出：false
	 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
	 * 提示：
	 * 1 <= numCourses <= 2000
	 * 0 <= prerequisites.length <= 5000
	 * prerequisites[i].length == 2
	 * 0 <= ai, bi < numCourses
	 * prerequisites[i] 中的所有课程对 互不相同
	 */
	public static class CanFinish {

		/**
		 * 拓扑排序,广度优先遍历思路
		 * 构建拓扑图,计算每个节点的入度数.从入度为0的节点开始,遍历其相邻的节点,然后将其相邻节点的入度数减1,依次遍历(入度为0才能被选中)
		 * 当所有的节点都能完全遍历完则说明拓扑图不存在环
		 * <p>
		 * 拓扑排序-构建有向图和入度(出度)数据
		 *
		 * @param numCourses
		 * @param prerequisites
		 * @return
		 */
		public static boolean canFinish(int numCourses, int[][] prerequisites) {
			List<List<Integer>> lists = new ArrayList<>();
			for (int i = 0; i < numCourses; ++i) {
				lists.add(new ArrayList<>());
			}
			int[] indeg = new int[numCourses];
			for (int[] prerequisite : prerequisites) {
				lists.get(prerequisite[1]).add(prerequisite[0]);
				++indeg[prerequisite[0]];
			}
			Queue<Integer> queue = new LinkedList<>();
			for (int i = 0; i < indeg.length; ++i) {
				if (indeg[i] == 0) {
					queue.offer(i);
				}
			}
			int visited = 0; // 记录入度为0的元素个数
			while (!queue.isEmpty()) {
				++visited;
				Integer u = queue.poll();
				for (int v : lists.get(u)) {
					indeg[v]--;
					if (indeg[v] == 0) {
						queue.offer(v);
					}
				}
			}
			return visited == numCourses;
		}

		public static void main(String[] args) {
			int[][] prerequisites = new int[][]{{0, 1}, {2, 1}, {5, 0}, {5, 3}, {4, 0}, {4, 5}, {2, 0}, {6, 5}};
			boolean b = canFinish(7, prerequisites);
			System.out.println(b);
		}
	}

	/**
	 * 297. 二叉树的序列化与反序列化
	 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
	 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
	 * 提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。
	 * 示例 1：
	 * 输入：root = [1,2,3,null,null,4,5]
	 * 输出：[1,2,3,null,null,4,5]
	 * 示例 2：
	 * 输入：root = []
	 * 输出：[]
	 * 示例 3：
	 * 输入：root = [1]
	 * 输出：[1]
	 * 示例 4：
	 * 输入：root = [1,2]
	 * 输出：[1,2]
	 * 提示：
	 * 树中结点数在范围 [0, 104] 内
	 * -1000 <= Node.val <= 1000
	 */
	public static class Codec {

		// Encodes a tree to a single string.
		public static String serialize(TreeNode root) {
				return "";
		}

		// Decodes your encoded data to tree.
		public static TreeNode deserialize(String data) {
			return null;
		}

		public static void main(String[] args) {
			Map<String, String> map = new HashMap<>();
			map.put("otp", "1234");
			System.out.println(JSON.toJSONString(map));
		}
	}

	/**
	 * 100. 相同的树
	 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
	 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
	 * 示例 1：
	 * 输入：p = [1,2,3], q = [1,2,3]
	 * 输出：true
	 * 示例 2：
	 * 输入：p = [1,2], q = [1,null,2]
	 * 输出：false
	 * 示例 3：
	 * 输入：p = [1,2,1], q = [1,1,2]
	 * 输出：false
	 * 提示：
	 * 两棵树上的节点数目都在范围 [0, 100] 内
	 * -104 <= Node.val <= 104
	 */
	public static class IsSameTree {

		/**
		 * 个人思路：
		 * 两种方案 1:通过栈,层序遍历,然后对比两棵树是否都一样
		 * 2:通过递归,深度优先遍历,对比两棵树是否都一样
		 *
		 * @param p
		 * @param q
		 * @return
		 */
		public static boolean isSameTree(TreeNode p, TreeNode q) {
			if (p == null && q == null) return true;
			if (p == null || q == null) return false;
			Deque<TreeNode> a = new LinkedList<>();
			Deque<TreeNode> b = new LinkedList<>();
			if (p.val != q.val) return false;
			a.push(p);
			b.push(q);
			while (!a.isEmpty() && !b.isEmpty()) {
				// 得考虑左右子节点的情况
				int aSize = a.size();
				int bSize = b.size();
				if (aSize != bSize) return false;
				for (; aSize > 0; aSize--) {
					TreeNode aCur = a.pollLast();
					TreeNode bCur = b.pollLast();
					if (aCur.left != null && bCur.left != null) {
						if (aCur.left.val != bCur.left.val) return false;
						a.push(aCur.left);
						b.push(bCur.left);
					} else {
						if (aCur.left != null || bCur.left != null) {
							return false;
						}
					}
					if (aCur.right != null && bCur.right != null) {
						if (aCur.right.val != bCur.right.val) return false;
						a.push(aCur.right);
						b.push(bCur.right);
					} else {
						if (aCur.right != null || bCur.right != null) {
							return false;
						}
					}
				}
			}
			return a.isEmpty() && b.isEmpty();
		}

		/**
		 * 递归方式
		 *
		 * @param p
		 * @param q
		 * @return
		 */
		public static boolean isSameTreeI(TreeNode p, TreeNode q) {
			if (p == null && q == null) return true;
			if (p == null || q == null) return false;
			if (p.val != q.val) return false;
			return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
		}

		public static void main(String[] args) {
			TreeNode node1 = new TreeNode(1);
			TreeNode node2 = new TreeNode(2);
			TreeNode node3 = new TreeNode(3);
			TreeNode node4 = new TreeNode(4);
			node1.right = node2;
			/*node1.right = node3;
			node3.left = node4;*/

			TreeNode node1_1 = new TreeNode(1);
			TreeNode node2_2 = new TreeNode(2);
			TreeNode node3_3 = new TreeNode(3);
			node1_1.left = node2_2;
			/*node1_1.right = node3_3;*/
			System.out.println(isSameTreeI(node1, node1_1));
		}
	}

	/**
	 * 106. 从中序与后序遍历序列构造二叉树
	 * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
	 * 示例 1:
	 * 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
	 * 输出：[3,9,20,null,null,15,7]
	 * 示例 2:
	 * 输入：inorder = [-1], postorder = [-1]
	 * 输出：[-1]
	 * 提示:
	 * 1 <= inorder.length <= 3000
	 * postorder.length == inorder.length
	 * -3000 <= inorder[i], postorder[i] <= 3000
	 * inorder 和 postorder 都由 不同 的值组成
	 * postorder 中每一个值都在 inorder 中
	 * inorder 保证是树的中序遍历
	 * postorder 保证是树的后序遍历
	 */
	public static class BuildTreeI {

		private static Map<Integer, Integer> indexMap;

		/**
		 * 中序遍历 -> [左树,根,右树]
		 * 后续遍历 -> [左树,右树,根]
		 * 通过后续遍历找到根的索引,那么就可以通过中序遍历找到左树和右数的索引范围
		 *
		 * @param inorder
		 * @param postorder
		 * @return
		 */
		public static TreeNode buildTree(int[] inorder, int[] postorder) {
			indexMap = getIndexMap(inorder);
			return buildTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
		}

		public static TreeNode buildTree(int[] inorder, int[] postorder, int is, int ie, int ps, int pe) {
			if (is > ie) return null;
			// 找到左树和右树
			Integer idx = indexMap.get(postorder[pe]);
			// 构造根节点
			TreeNode root = new TreeNode(postorder[pe]);
			// 中序:左树的范围 [s,idx-1] 右数的范围[idx+1,e] 左树的长度为lsize 右树的长度为rsize
			// 后序:左树的范围 [s1,s1+lsize-1] 右数的范围[s1+lsize,rsize-1]
			int ls = idx - is, rs = ie - idx;
			root.left = buildTree(inorder, postorder, is, idx - 1, ps, ps + ls - 1);
			root.right = buildTree(inorder, postorder, idx + 1, ie, ps + ls, ps + ls + rs - 1);
			return root;
		}

		/**
		 * 因为树的所有节点存储的值都不同,可以使用map存储节点值所对应的索引
		 */
		private static Map<Integer, Integer> getIndexMap(int[] inorder) {
			Map<Integer, Integer> map = new HashMap<>(inorder.length);
			for (int i = 0; i < inorder.length; i++) {
				map.put(inorder[i], i);
			}
			return map;
		}

		public static void main(String[] args) {
			TreeNode treeNode = buildTree(new int[]{9, 3, 15, 20, 7}, new int[]{9, 15, 7, 20, 3});
			System.out.println();
		}
	}

	/**
	 * 114. 二叉树展开为链表
	 * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
	 * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
	 * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
	 * 示例 1：
	 * 输入：root = [1,2,5,3,4,null,6]
	 * 输出：[1,null,2,null,3,null,4,null,5,null,6]
	 * 示例 2：
	 * 输入：root = []
	 * 输出：[]
	 * 示例 3：
	 * 输入：root = [0]
	 * 输出：[0]
	 * 提示：
	 * 树中结点数在范围 [0, 2000] 内
	 * -100 <= Node.val <= 100
	 * 进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？
	 */
	public static class Flatten {

		/**
		 * 个人思路：
		 * 如果使用额外空间,会比较容易想到
		 * 1.将先序遍历得到的节点放在集合中,然后构造树;构造树官方思路是通过将子节点连接到根节点上
		 *
		 * @param root
		 */
		public static void flatten(TreeNode root) {
			if (root == null || (root.left == null && root.right == null)) return;
			List<TreeNode> list = new ArrayList<>();
			inorder(root, list);
			TreeNode node = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				node.right = list.get(i);
				node.left = null;
				node = node.right;
			}
		}

		private static void inorder(TreeNode root, List<TreeNode> list) {
			if (root == null) return;
			list.add(root);
			inorder(root.left, list);
			inorder(root.right, list);
		}

		/**
		 * 通过迭代模拟先序遍历
		 * @param root
		 * @return
		 */
		private static List<TreeNode> inorder(TreeNode root) {
			List<TreeNode> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> queue = new ArrayDeque<>();
			// 迭代模式先序遍历,先遍历完所有的左子树,再遍历右子树
			TreeNode node = root;
			while (node != null || !queue.isEmpty()) {
				while (node != null) {
					queue.push(node);
					list.add(node);
					node = node.left;
				}
				node = queue.pop();
				node = node.right;
			}
			return list;
		}

		/**
		 * 通过迭代模拟先序遍历2
		 * @param root
		 * @return
		 */
		private static List<TreeNode> inorderI(TreeNode root) {
			List<TreeNode> list = new ArrayList<>();
			if (root == null) return list;
			Deque<TreeNode> queue = new ArrayDeque<>();
			queue.push(root);
			while (!queue.isEmpty()) {
				TreeNode pop = queue.pop();
				list.add(pop);
				if (pop.right != null) queue.push(pop.right);
				if (pop.left != null) queue.push(pop.left);
			}
			return list;
		}

		/**
		 * 2.通过迭代的方式,而非递归
		 * @param root
		 */
		public static void flattenI(TreeNode root) {
			List<TreeNode> list = inorder(root);
			if (list.isEmpty()) return;
			TreeNode node = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				node.right = list.get(i);
				node.left = null;
				node = node.right;
			}
		}

		/**
		 * 3.先序遍历的同时,构造成链表;拿到当前节点时需要存储当前节点的左右子节点
		 * @param root
		 */
		public static void flattenOfficial(TreeNode root) {
			if (root == null) return;
			Deque<TreeNode> queue = new ArrayDeque<>();
			queue.push(root);
			TreeNode prev = null;
			while (!queue.isEmpty()) {
				TreeNode node = queue.pop();
				if (prev != null) {
					prev.right = node;
					prev.left = null;
				}
				if (node.right != null) queue.push(node.right);
				if (node.left != null) queue.push(node.left);
				prev = node;
			}
		}

		/**
		 * 4.找到当前节点左子树的最右节点,作为前驱节点;当前节点右指针指向其左节点;其右节点指向前面找到的前驱节点
		 * @param root
		 */
		public static void flattenOfficialI(TreeNode root) {
			while (root != null) {
				if (root.left == null) {
					root = root.right;
				} else {  // 当前节点的左节点不为空
					// 找到左子树的最右节点
					TreeNode pre = root.left;
					while (pre.right != null) {
						pre = pre.right;
					}
					pre.right = root.right;  // 将原找到的左子树最右节点指向当前节点的右节点
					root.right = root.left;  // 将当前节点右节点指向当前节点的左节点
					root.left = null;  // 将当前节点的左节点置为空
				}
			}
		}

		private static TreeNode prev;

		/**
		 * 后续遍历 [左树,右树,根]
		 * 在遍历到根时,一定是先遍历了左树和右树了.此时把左节点放在有节点指针上,然后把左节点置为空
		 *
		 * @param root
		 */
		public static void flattenII(TreeNode root) {
			if (root == null) return;
			flattenII(root.right);
			flattenII(root.left);
			// 定义一个前驱节点全部变量,更新当前节点右指针为prev,左指针为null
			root.right = prev;
			root.left = null;
			prev = root;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n2 = new TreeNode(2);
			TreeNode n3 = new TreeNode(3);
			TreeNode n4 = new TreeNode(4);
			TreeNode n5 = new TreeNode(5);
			TreeNode n6 = new TreeNode(6);
			root.left = n2;
			root.right = n5;
			n2.left = n3;
			n2.right = n4;
			n5.right = n6;
			flattenII(root);
			System.out.println();

			//inorder(root);
		}
	}

	/**
	 * 112. 路径总和
	 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
	 * 如果存在，返回 true ；否则，返回 false 。
	 * 叶子节点 是指没有子节点的节点。
	 * 示例 1：
	 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
	 * 输出：true
	 * 解释：等于目标和的根节点到叶节点路径如上图所示。
	 * 示例 2：
	 * 输入：root = [1,2,3], targetSum = 5
	 * 输出：false
	 * 解释：树中存在两条根节点到叶子节点的路径：
	 * (1 --> 2): 和为 3
	 * (1 --> 3): 和为 4
	 * 不存在 sum = 5 的根节点到叶子节点的路径。
	 * 示例 3：
	 * 输入：root = [], targetSum = 0
	 * 输出：false
	 * 解释：由于树是空的，所以不存在根节点到叶子节点的路径。
	 * 提示：
	 * 树中节点的数目在范围 [0, 5000] 内
	 * -1000 <= Node.val <= 1000
	 * -1000 <= targetSum <= 1000
	 */
	public static class HasPathSum {

		private static boolean flag = false;

		/**
		 * dfs
		 * 回溯记录路径数据,当遍历到叶子节点时,计算路径是否等于targetSum
		 * 首先回顾下二叉树遍历的路径,打印出路径
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static boolean hasPathSum(TreeNode root, int targetSum) {
			if (root == null) return false
					;
			Deque<Integer> depth = new LinkedList<>();
			depth.addLast(root.val);
			dfs(root, targetSum - root.val, depth);
			return flag;
		}

		/**
		 * 二叉树深度优先遍历,路径回溯  找到符合条件的结果后,还会继续遍历知道树遍历完成
		 *
		 * @param node
		 * @param sum
		 * @param depth
		 */
		private static void dfs(TreeNode node, int sum, Deque<Integer> depth) {
			if (node.left == null && node.right == null) {  // 叶子节点
				if (sum == 0) {
					flag = true;
					printfPath(depth);
					return;
				}
			}
			if (node.left != null) {
				depth.addLast(node.left.val);
				dfs(node.left, sum - node.left.val, depth);
				depth.removeLast();
			}
			if (node.right != null) {
				depth.addLast(node.right.val);
				dfs(node.right, sum - node.right.val, depth);
				depth.removeLast();
			}
		}

		/**
		 * 不需要得到遍历路径,只计算路径上的值
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static boolean hasPathSumI(TreeNode root, int targetSum) {
			if (root == null) return false;
			dfs1(root, targetSum - root.val);
			return flag;
		}

		/**
		 * 计算路径上的节点值总和时为什么不需要回溯呢?
		 * 因为递归每次进入一个新的方法时,其方法栈中都会保存一个sum变量当回到上一个方法栈时,sum中存储的是上一个方法栈中的sum变量值
		 *
		 * @param node
		 * @param sum
		 */
		private static void dfs1(TreeNode node, int sum) {
			if (node.left == null && node.right == null) {
				if (sum == 0) {
					flag = true;
					return;
				}
			}
			if (node.left != null) {
				dfs1(node.left, sum - node.left.val);
			}
			if (node.right != null) {
				dfs1(node.right, sum - node.right.val);
			}
		}

		private static void printfPath(Deque<Integer> depth) {
			Deque<Integer> path = new ArrayDeque<>(depth);
			StringBuilder sb = new StringBuilder();
			while (!path.isEmpty()) {
				sb.append(path.pollFirst()).append("->");
			}
			System.out.println(sb.substring(0, sb.length() - 2));
		}

		/**
		 * bfs思路,层序遍历的同时记录从根节点到当前节点的总和,当遍历到叶子节点时,计算值是否相等
		 * 关键点在于分层遍历时,在栈中保存节点的同时保存这个节点经历的路径和;弹出和压入保持 [一致性].
		 * 这样,遍历到叶子节点时,当前保存路路径和的栈,也始终同步
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static boolean hasPathSumII(TreeNode root, int targetSum) {
			if (root == null) return false;
			Deque<TreeNode> deque = new LinkedList<>();  // 遍历节点
			Deque<Integer> sum = new LinkedList<>(); // 存放节点路径和
			sum.push(root.val);
			deque.push(root);
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; size--) {
					// 节点的弹出和节点和的弹出保持一致性
					TreeNode cur = deque.pollLast();
					int v = sum.pollLast();
					if (cur.left != null) {
						deque.push(cur.left);
						sum.push(cur.left.val + v);
					}
					if (cur.right != null) {
						deque.push(cur.right);
						sum.push(cur.right.val + v);
					}
					if (cur.left == null && cur.right == null) {
						if (targetSum == v) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(5);
			TreeNode n4 = new TreeNode(4);
			TreeNode n11 = new TreeNode(11);
			TreeNode n7 = new TreeNode(7);
			TreeNode n2 = new TreeNode(2);
			TreeNode n8 = new TreeNode(8);
			TreeNode n13 = new TreeNode(13);
			TreeNode n4_1 = new TreeNode(4);
			TreeNode n1 = new TreeNode(1);
			root.left = n4;
			root.right = n8;
			n4.left = n11;
			n11.left = n7;
			n11.right = n2;
			n8.left = n13;
			n8.right = n4_1;
			n4_1.right = n1;
			boolean b = hasPathSumII(root, 22);
			System.out.println(b);
		}
	}

	/**
	 * 129. 求根节点到叶节点数字之和
	 * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
	 * 每条从根节点到叶节点的路径都代表一个数字：
	 * 例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
	 * 计算从根节点到叶节点生成的 所有数字之和 。
	 * 叶节点 是指没有子节点的节点。
	 * 示例 1：
	 * 输入：root = [1,2,3]
	 * 输出：25
	 * 解释：
	 * 从根到叶子节点路径 1->2 代表数字 12
	 * 从根到叶子节点路径 1->3 代表数字 13
	 * 因此，数字总和 = 12 + 13 = 25
	 * 示例 2：
	 * 输入：root = [4,9,0,5,1]
	 * 输出：1026
	 * 解释：
	 * 从根到叶子节点路径 4->9->5 代表数字 495
	 * 从根到叶子节点路径 4->9->1 代表数字 491
	 * 从根到叶子节点路径 4->0 代表数字 40
	 * 因此，数字总和 = 495 + 491 + 40 = 1026
	 * 提示：
	 * 树中节点的数目在范围 [1, 1000] 内
	 * 0 <= Node.val <= 9
	 * 树的深度不超过 10
	 */
	public static class SumNumbers {

		private static int total = 0;

		/**
		 * dfs
		 *
		 * @param root
		 * @return
		 */
		public static int sumNumbers(TreeNode root) {
			dfs(root, root.val);
			return total;
		}

		/**
		 * dfs 到叶子节点计算值
		 *
		 * @param node
		 * @param sum
		 * @return
		 */
		private static void dfs(TreeNode node, int sum) {
			if (node.left != null) dfs(node.left, sum * 10 + node.left.val);
			if (node.right != null) dfs(node.right, sum * 10 + node.right.val);
			if (node.left == null && node.right == null) {
				total += sum;
			}
		}

		/**
		 * bfs 层序遍历,同步保存节点值在栈中
		 *
		 * @param root
		 * @return
		 */
		public static int sumNumbersI(TreeNode root) {
			int total1 = 0;
			Deque<TreeNode> deque = new LinkedList<>();
			Deque<Integer> sum = new LinkedList<>();
			deque.addFirst(root);
			sum.addFirst(root.val);
			while (!deque.isEmpty()) {
				int size = deque.size();
				for (; size > 0; size--) {
					TreeNode cur = deque.pollLast();
					int v = sum.pollLast();
					if (cur.left != null) {
						deque.addFirst(cur.left);
						sum.addFirst(v * 10 + cur.left.val);
					}
					if (cur.right != null) {
						deque.addFirst(cur.right);
						sum.addFirst(v * 10 + cur.right.val);
					}
					if (cur.left == null && cur.right == null) {
						total1 += v;
					}
				}
			}
			return total1;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(4);
			TreeNode n9 = new TreeNode(9);
			TreeNode n0 = new TreeNode(0);
			TreeNode n5 = new TreeNode(5);
			TreeNode n1 = new TreeNode(1);
			root.left = n9;
			root.right = n0;
			n9.left = n5;
			n9.right = n1;
			int i = sumNumbersI(root);
			System.out.println(i);
		}
	}

	/**
	 * 124. 二叉树中的最大路径和
	 * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
	 * 路径和 是路径中各节点值的总和。
	 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
	 * 示例 1：
	 * 输入：root = [1,2,3]
	 * 输出：6
	 * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
	 * 示例 2：
	 * 输入：root = [-10,9,20,null,null,15,7]
	 * 输出：42
	 * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
	 * 提示：
	 * 树中节点数目范围是 [1, 3 * 104]
	 * -1000 <= Node.val <= 1000
	 */
	public static class MaxPathSum {

		/**
		 * 本题的关键点在于,dfs深度优先遍历,怎么样才能找到最大的路径呢?
		 * 首先需要引入一个概念:节点的贡献值
		 * 1.叶子节点的贡献值为其本身的值
		 * 2.非叶子节点的最大贡献值为 v+max(left,right)
		 * 3.经过当前节点的最大路径和 就是v+left+right
		 * <p>
		 * 有这个概念后,思路将为很清晰,我们需要先遍历叶子节点,然后遍历根节点,(后序遍历)这样就可以计算出所有节点的贡献值
		 * 然后比较每个节点的贡献值,就可得出最优路径值
		 *
		 * @param root
		 * @return
		 */
		public static int maxPathSum(TreeNode root) {
			dfs(root);
			return sum;
		}

		private static int sum = Integer.MIN_VALUE;

		/**
		 * 思路存在问题,重复计算的节点的贡献值
		 *
		 * @param node
		 * @return
		 */
		@Deprecated
		private static int dfs(TreeNode node) {
			if (node.left == null && node.right == null) return node.val;
			int lv = Integer.MIN_VALUE, rv = Integer.MIN_VALUE, cv;
			if (node.left != null) lv = dfs(node.left);
			if (node.right != null) rv = dfs(node.right);
			if (node.val >= 0) {
				// 当前节点值>0 ,左右子节点贡献值都>0   都<0
				if (lv >= 0 & rv >= 0) {
					cv = node.val + lv + rv;
				} else if (lv < 0 && rv < 0) {
					cv = node.val;
				} else {  // 有一个小于0
					cv = node.val + Math.max(lv, rv);
				}
			} else {
				cv = Math.max(Math.max(lv, rv), node.val);
			}
			sum = Math.max(sum, cv);
			return cv;
		}

		private static int dfsOfficial(TreeNode node) {
			if (node == null) return 0;
			// 当左右节点的贡献值大于0时,才选取对应的子节点
			int lv = Math.max(dfsOfficial(node.left), 0);
			int rv = Math.max(dfsOfficial(node.right), 0);
			// 当前节点的贡献值
			int price = node.val + lv + rv;
			sum = Math.max(price, sum);
			return node.val + Math.max(lv, rv);
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(1);
			TreeNode n9 = new TreeNode(-2);
			TreeNode n20 = new TreeNode(-3);
			TreeNode n15 = new TreeNode(1);
			TreeNode n7 = new TreeNode(3);
			TreeNode n8 = new TreeNode(-1);
			TreeNode n10 = new TreeNode(-1);
			root.left = n9;
			root.right = n20;
			n9.left = n15;
			n9.right = n7;
			n15.left = n8;
			n20.left = n10;
			int i = maxPathSum(root);
			System.out.println(i);
		}
	}

	/**
	 * 173. 二叉搜索树迭代器
	 * 实现一个二叉搜索树迭代器类BSTIterator ，表示一个按中序遍历二叉搜索树（BST）的迭代器：
	 * BSTIterator(TreeNode root) 初始化 BSTIterator 类的一个对象。BST 的根节点 root 会作为构造函数的一部分给出。
	 * 指针应初始化为一个不存在于 BST 中的数字，且该数字小于 BST 中的任何元素。
	 * boolean hasNext() 如果向指针右侧遍历存在数字，则返回 true ；否则返回 false 。
	 * int next()将指针向右移动，然后返回指针处的数字。
	 * 注意，指针初始化为一个不存在于 BST 中的数字，所以对 next() 的首次调用将返回 BST 中的最小元素。
	 * 你可以假设 next() 调用总是有效的，也就是说，当调用 next() 时，BST 的中序遍历中至少存在一个下一个数字。
	 * 示例：
	 * 输入
	 * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
	 * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
	 * 输出
	 * [null, 3, 7, true, 9, true, 15, true, 20, false]
	 * 解释
	 * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
	 * bSTIterator.next();    // 返回 3
	 * bSTIterator.next();    // 返回 7
	 * bSTIterator.hasNext(); // 返回 True
	 * bSTIterator.next();    // 返回 9
	 * bSTIterator.hasNext(); // 返回 True
	 * bSTIterator.next();    // 返回 15
	 * bSTIterator.hasNext(); // 返回 True
	 * bSTIterator.next();    // 返回 20
	 * bSTIterator.hasNext(); // 返回 False
	 * 提示：
	 * 树中节点的数目在范围 [1, 105] 内
	 * 0 <= Node.val <= 106
	 * 最多调用 105 次 hasNext 和 next 操作
	 * 进阶：
	 * 你可以设计一个满足下述条件的解决方案吗？next() 和 hasNext() 操作均摊时间复杂度为 O(1) ，并使用 O(h) 内存。其中 h 是树的高度。
	 */
	public static class BSTIterator {

		private int idx;
		private List<Integer> list;

		/**
		 * 非进阶的情况下,将树进行中序遍历,存放在List中
		 * 时间复杂度: next(),hasNext() 为O(1)  初始化为O(n)
		 * 空间复杂度: O(n)
		 *
		 * @param root
		 */
		public BSTIterator(TreeNode root) {
			list = new ArrayList<>();
			inorder(root);
			idx = 0;
		}

		public int next() {
			return list.get(idx++);
		}

		public boolean hasNext() {
			return idx < list.size();
		}

		private void inorder(TreeNode root) {
			if (root == null) return;
			inorder(root.left);
			list.add(root.val);
			inorder(root.right);
		}

		public static void main(String[] args) {
			TreeNode n7 = new TreeNode(7);
			TreeNode n3 = new TreeNode(3);
			TreeNode n15 = new TreeNode(15);
			TreeNode n9 = new TreeNode(9);
			TreeNode n20 = new TreeNode(20);
			n7.left = n3;
			n7.right = n15;
			n15.left = n9;
			n15.right = n20;
			BSTIterator bstIterator = new BSTIterator(n7);
			System.out.println(bstIterator.next());
			System.out.println(bstIterator.next());
			System.out.println(bstIterator.hasNext());
			System.out.println(bstIterator.next());
			System.out.println(bstIterator.hasNext());
			System.out.println(bstIterator.next());
			System.out.println(bstIterator.hasNext());
			System.out.println(bstIterator.next());
			System.out.println(bstIterator.hasNext());
		}
	}

	/**
	 * 迭代方式,做中序遍历
	 */
	public static class BSTIteratorI {

		private TreeNode cur;
		private Deque<TreeNode> stack;


		public BSTIteratorI(TreeNode root) {
			cur = root;
			stack = new LinkedList<>();
		}

		public int next() {
			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			cur = stack.poll();
			int val = cur.val;
			cur = cur.right;
			return val;
		}

		public boolean hasNext() {
			return cur != null || !stack.isEmpty();
		}
	}

	/**
	 * 530. 二叉搜索树的最小绝对差
	 * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
	 * 差值是一个正数，其数值等于两值之差的绝对值。
	 * 示例 1：
	 * 输入：root = [4,2,6,1,3]
	 * 输出：1
	 * 示例 2：
	 * 输入：root = [1,0,48,null,null,12,49]
	 * 输出：1
	 * 提示：
	 * 树中节点的数目范围是 [2, 104]
	 * 0 <= Node.val <= 105
	 */
	public static class GetMinimumDifference {

		private static int val = Integer.MAX_VALUE;
		private static int pre = Integer.MAX_VALUE;

		private static int preI = -1;

		public static int getMinimumDifference(TreeNode root) {
			dfs(root);
			return val;
		}

		/**
		 * 搜索二叉树特性,左树所有节点值小于根节点 右树所有节点值大于根节点
		 * 那么最小的节点差值应该是在一个节点与其左右子节点中产生 错误**********************
		 * 举例说明
		 * *        236
		 * *        / \
		 * *      104  701
		 * *       \     \
		 * *       227   911
		 * 两个节点间最小差 (236,227) 他们并不是一个根节点的左右子节点
		 * 那么最小的差值是产生在哪里呢?
		 * 理解搜索二叉树的特性,中序遍历后,节点值递增,那么只需要比较相邻两个节点的差值即可以找到最小差值
		 *
		 * @param root
		 */
		private static void dfs(TreeNode root) {
			if (root.left != null) dfs(root.left);
			val = Math.min(Math.abs(pre - root.val), val);
			pre = root.val;
			if (root.right != null) dfs(root.right);
		}

		public static int getMinimumDifferenceI(TreeNode root) {
			dfsI(root);
			return val;
		}

		private static void dfsI(TreeNode node) {
			if (node != null) {
				dfsI(node.left);
				val = Math.min(Math.abs(pre - node.val), val);  // 此处可以不用abs,因为二叉搜索树中序遍历后是升序的
				pre = node.val;
				dfsI(node.right);
			}
		}

		private static void dfsII(TreeNode node) {
			if (node != null) {
				dfsI(node.left);
				if (pre == -1) {
					pre = node.val;
				} else {
					val = Math.min(node.val - pre, val);
					pre = node.val;
				}
				dfsI(node.right);
			}
		}

		public static void main(String[] args) {
			TreeNode n1 = new TreeNode(236);
			TreeNode n2 = new TreeNode(104);
			TreeNode n3 = new TreeNode(701);
			TreeNode n4 = new TreeNode(227);
			TreeNode n5 = new TreeNode(911);

			n1.left = n2;
			n1.right = n3;
			n2.right = n4;
			n3.right = n5;
			System.out.println(getMinimumDifference(n1));
		}
	}

	/**
	 * 98. 验证二叉搜索树
	 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
	 * 有效 二叉搜索树定义如下：
	 * 节点的左
	 * 子树
	 * 只包含 小于 当前节点的数。
	 * 节点的右子树只包含 大于 当前节点的数。
	 * 所有左子树和右子树自身必须也是二叉搜索树。
	 * 示例 1：
	 * 输入：root = [2,1,3]
	 * 输出：true
	 * 示例 2：
	 * 输入：root = [5,1,4,null,null,3,6]
	 * 输出：false
	 * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
	 * 提示：
	 * 树中节点数目范围在[1, 104] 内
	 * -231 <= Node.val <= 231 - 1
	 */
	public static class IsValidBST {

		private static Integer pre;
		private static boolean flag = true;

		/**
		 * 验证搜索二叉树中序遍历是递增序列即可
		 * @param root
		 * @return
		 */
		public static boolean isValidBST(TreeNode root) {
			dfs(root);
			return flag;
		}

		private static void dfs(TreeNode node) {
			if (node == null) return;
			dfs(node.left);
			if (pre == null) {
				pre = node.val;
			} else {
				if (pre >= node.val) {
					flag = false;
					return;
				}
				pre = node.val;
			}
			System.out.println(node.val);
			dfs(node.right);
		}

		public static boolean isValidBSTI(TreeNode root) {
			return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
		}

		private static boolean isValidBST(TreeNode node, long lower, long upper) {
			if (node == null) return true;
			if (node.val <= lower || node.val >= upper) return false;
			return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
		}

		/**
		 * 迭代中序遍历
		 * @param root
		 * @return
		 */
		public static boolean isValidBSTII(TreeNode root) {
			Deque<TreeNode> stack = new LinkedList<>();
			while (!stack.isEmpty() || root != null) {
				while (root != null) {  // 一直到最左的左节点
					stack.push(root);
					root = root.left;
				}
				root = stack.pop();
				// 中序遍历中进行比较
				if (pre == null) {
					pre = root.val;
				} else {
					if (pre >= root.val) {
						return false;
					}
					pre = root.val;
				}
				root = root.right;
			}
			return true;
		}

		public static void main(String[] args) {
			TreeNode n5 = new TreeNode(5);
			TreeNode n1 = new TreeNode(1);
			TreeNode n4 = new TreeNode(4);
			TreeNode n3=new TreeNode(3);
			TreeNode n6=new TreeNode(6);
			n5.left=n1;
			n5.right=n4;
			n4.left=n3;
			n4.right=n6;
			System.out.println(isValidBST(n5));
		}
	}

	/**
	 * 108. 将有序数组转换为二叉搜索树
	 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵
	 * 平衡
	 * 二叉搜索树。
	 * 示例 1：
	 * 输入：nums = [-10,-3,0,5,9]
	 * 输出：[0,-3,9,-10,null,5]
	 * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
	 * 示例 2：
	 * 输入：nums = [1,3]
	 * 输出：[3,1]
	 * 解释：[1,null,3] 和 [3,1] 都是高度平衡二叉搜索树。
	 * 提示：
	 * 1 <= nums.length <= 104
	 * -104 <= nums[i] <= 104
	 * nums 按 严格递增 顺序排列
	 */
	public static class SortedArrayToBST {

		/**
		 * 平衡二叉搜索树是指这棵搜索树左右子树的高度相差不大于1
		 * 所以从中间位置找到根节点构建左右子树;而左右子树也以同样的方式构建其左右子树
		 *
		 * @param nums
		 * @return
		 */
		public static TreeNode sortedArrayToBST(int[] nums) {
			return dfs(nums, 0, nums.length - 1);
		}

		public static TreeNode dfs(int[] nums, int s, int p) {
			if (s >p) return null;
			int mid = s + (p - s + 1) / 2; // 根节点
			TreeNode root = new TreeNode(nums[mid]);
			root.left = dfs(nums, s, mid - 1);  // 左子树
			root.right = dfs(nums, mid + 1, p);  // 右子树
			return root;
		}

		public static void main(String[] args) {
			TreeNode treeNode = sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});
			System.out.println();
		}
	}

	/**
	 * 543. 二叉树的直径
	 * 给你一棵二叉树的根节点，返回该树的 直径 。
	 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
	 * 两节点之间路径的 长度 由它们之间边数表示。
	 * 示例 1：
	 * 输入：root = [1,2,3,4,5]
	 * 输出：3
	 * 解释：3 ，取路径 [4,2,1,3] 或 [5,2,1,3] 的长度。
	 * 示例 2：
	 * 输入：root = [1,2]
	 * 输出：1
	 * 提示：
	 * 树中节点数目在范围 [1, 104] 内
	 * -100 <= Node.val <= 100
	 */
	public static class DiameterOfBinaryTree {


		private static int max = 0;

		/**
		 * 二叉树中任意两个节点之间最长路径的长度,其实就是找到所有的非叶子节点,然后统计其左树和右树的高度之和
		 *
		 * @param root
		 * @return
		 */
		public static int diameterOfBinaryTree(TreeNode root) {
			if (root.left == null && root.right == null) return 0;
			dfsTree(root);
			return max;
		}

		private static void dfsTree(TreeNode root) {
			if (root == null || (root.left == null && root.right == null)) return; // 叶子节点其左右子树的高度相加肯定不是最大
			int leftHigh = dfs(root.left);
			int rightHigh = dfs(root.right);
			max = Math.max(leftHigh + rightHigh, max);
			dfsTree(root.left);
			dfsTree(root.right);
		}

		/**
		 * 给出一个节点,求出其高度
		 *
		 * @param node
		 * @return
		 */
		private static int dfs(TreeNode node) {
			if (node == null) return 0;
			return Math.max(dfs(node.left), dfs(node.right)) + 1;
		}

		private static int res = 1;

		/**
		 * 在计算一个节点左右子树的深度时,就能计算其经历过的所有节点;同时还能计算其节点的深度
		 * 每次计算一个节点的左右子树深度和,同时计算该节点的深度
		 *
		 * @param root
		 * @return
		 */
		public static int diameterOfBinaryTreeI(TreeNode root) {
			depth(root);
			return res - 1;
		}

		private static int depth(TreeNode node) {
			if (node == null) return 0;
			int ld = depth(node.left);
			int rd = depth(node.right);
			res = Math.max(res, ld + rd + 1); // 当前节点直径即左右子树的深度相加再加一
			return Math.max(ld, rd) + 1;  // 当前节点为根的子树深度
		}

		public static void main(String[] args) {
			TreeNode node1 = new TreeNode(1);
			TreeNode node2 = new TreeNode(2);
			TreeNode node3 = new TreeNode(3);
			TreeNode node4 = new TreeNode(4);
			TreeNode node5 = new TreeNode(5);
			node1.left = node2;
/*			node1.right = node3;
			node2.left = node4;
			node2.right = node5;*/
			int i = diameterOfBinaryTree(node1);
			System.out.println(i);
		}
	}

	/**
	 * 437. 路径总和 III
	 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
	 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
	 * 示例 1：
	 * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
	 * 输出：3
	 * 解释：和等于 8 的路径有 3 条，如图所示。
	 * 示例 2：
	 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
	 * 输出：3
	 * 提示:
	 * 二叉树的节点个数的范围是 [0,1000]
	 * -109 <= Node.val <= 109
	 * -1000 <= targetSum <= 1000
	 */
	public static class PathSum {

		/**
		 * 对于每一个节点而言,设置递归方法dfs(node,sum);那么他的子节点应该满足dfs(node.left,sum-node.val) 和dfs(node.right,sum-node.val)
		 * 所以依次从根节点向下遍历,其中会经历两层深度优先遍历
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static int pathSum(TreeNode root, int targetSum) {
			if (root == null) return 0;
			int res = rootSum(root, targetSum);   // 当前节点满足条件的数量
			res += pathSum(root.left, (int) (long) targetSum); // 递归其子节点并执行查找数量
			res += pathSum(root.right, (int) (long) targetSum);
			return res;
		}

		/**
		 * 计算指定节点下满足条件的节点数量
		 *
		 * @param root
		 * @param sum
		 * @return
		 */
		private static int rootSum(TreeNode root, long sum) {
			long res = 0;
			if (root == null) return 0;
			long val = root.val;
			if (val == sum) {
				res++;
			}
			res += rootSum(root.left, sum - root.val);
			res += rootSum(root.right, sum - root.val);
			return (int) res;
		}

		/**
		 * 计算根节点到当前节点的所有前缀和,存放在HashMap中,那么当遍历到一个节点node时,需要计算从root到node这些节点一直到节点node的和,
		 * sum[root,node] 表示节点root到node的和;sum[root,node]-target=val,如果val在HashMap中存在,说明sum[x,node]中存在等于target的情况
		 * 前缀和还是很不好理解的
		 *
		 * @param root
		 * @param targetSum
		 * @return
		 */
		public static int pathSumI(TreeNode root, int targetSum) {
			Map<Long, Integer> prefix = new HashMap<>();
			prefix.put(0L, 1);
			return dfs(root, prefix, 0, targetSum);
		}

		public static int dfs(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
			if (root == null) return 0;
			int ret = 0;
			curr += root.val;
			ret = prefix.getOrDefault(curr - targetSum, 0);
			prefix.put(curr, prefix.getOrDefault(curr, 0) + 1);
			ret += dfs(root.left, prefix, curr, targetSum);
			ret += dfs(root.right, prefix, curr, targetSum);
			// 回溯
			prefix.put(curr, prefix.getOrDefault(curr, 0) - 1);
			return ret;
		}

		public static void main(String[] args) {
			TreeNode node = new TreeNode(1000000000);
			TreeNode node1 = new TreeNode(1000000000);
			TreeNode node2 = new TreeNode(294967296);
			TreeNode node3 = new TreeNode(1000000000);
			TreeNode node4 = new TreeNode(1000000000);
			node.left = node1;
			node1.left = node2;
			node2.left = node3;
			node3.left = node4;
			int i = pathSumI(node, 0);
			System.out.println(i);
		}
	}

	/**
	 * 872. 叶子相似的树
	 * 请考虑一棵二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列 。
	 * 举个例子，如上图所示，给定一棵叶值序列为 (6, 7, 4, 9, 8) 的树。
	 * 如果有两棵二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
	 * 如果给定的两个根结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false
	 * 示例 1：
	 * 输入：root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
	 * 输出：true
	 * 示例 2：
	 * 输入：root1 = [1,2,3], root2 = [1,3,2]
	 * 输出：false
	 * 提示：
	 * 给定的两棵树结点数在 [1, 200] 范围内
	 * 给定的两棵树上的值在 [0, 200] 范围内
	 */
	static class LeafSimilar {

		/**
		 * 思路:
		 * 深度优先遍历输出叶子节点,然后比较叶子节点是否完全相同
		 * 能否在遍历的同时进行比较呢?
		 *
		 * @param root1
		 * @param root2
		 * @return
		 */
		public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
			List<Integer> list1 = new ArrayList<>(), list2 = new ArrayList<>();
			dfs(root1, list1);
			dfs(root2, list2);
			if (list1.size() != list2.size()) return false;
			for (int i = 0; i < list1.size(); i++) {
				if (!Objects.equals(list1.get(i), list2.get(i))) return false;
			}
			return true;
		}

		private static void dfs(TreeNode root, List<Integer> list) {
			if (root.left == null && root.right == null) { // 叶子节点
				list.add(root.val);
			}
			if (root.left != null) dfs(root.left, list);
			if (root.right != null) dfs(root.right, list);
		}

		/**
		 * 广度优先搜索  bfs
		 *
		 * @param root1
		 * @param root2
		 * @return
		 */
		public static boolean leafSimilarII(TreeNode root1, TreeNode root2) {
			List<Integer> list1 = bfs(root1);
			List<Integer> list2 = bfs(root2);
			if (list1.size() != list2.size()) return false;
			for (int i = 0; i < list1.size(); i++) {
				if (!Objects.equals(list1.get(i), list2.get(i))) return false;
			}
			return true;
		}

		private static List<Integer> bfs(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			Deque<TreeNode> stack = new LinkedList<>();
			stack.offer(root);
			while (!stack.isEmpty()) {
				int size = stack.size();
				for (; size > 0; size--) {
					TreeNode curr = stack.pop();
					if (curr.left != null) stack.offer(curr.left);
					if (curr.right != null) stack.offer(curr.right);
					if (curr.left == null && curr.right == null) list.add(curr.val);
				}
			}
			return list;
		}

		public static void main(String[] args) {
			TreeNode root1 = new TreeNode(3);
			TreeNode node5 = new TreeNode(5);
			TreeNode node1 = new TreeNode(1);
			root1.left = node5;
			root1.right = node1;
			TreeNode node6 = new TreeNode(6);
			TreeNode node2 = new TreeNode(2);
			TreeNode node9 = new TreeNode(9);
			TreeNode node8 = new TreeNode(8);
			node5.left = node6;
			node5.right = node2;
			node1.left = node9;
			node1.right = node8;
			TreeNode node7 = new TreeNode(7);
			TreeNode node4 = new TreeNode(4);
			node2.left = node7;
			node2.right = node4;

			TreeNode root2 = new TreeNode(3);
			TreeNode node5_1 = new TreeNode(5);
			TreeNode node1_1 = new TreeNode(1);
			root2.left = node5_1;
			root2.right = node1_1;
			TreeNode node6_1 = new TreeNode(6);
			TreeNode node2_1 = new TreeNode(2);
			TreeNode node9_1 = new TreeNode(9);
			TreeNode node8_1 = new TreeNode(8);
			TreeNode node7_1 = new TreeNode(7);
			TreeNode node4_1 = new TreeNode(4);
			node5_1.left = node6_1;
			node5_1.right = node7_1;
			node1_1.left = node4_1;
			node1_1.right = node2_1;
			node2_1.left = node9_1;
			node2_1.right = node8_1;
			System.out.println(leafSimilar(root1, root2));
		}
	}

	/**
	 * 1448. 统计二叉树中好节点的数目
	 * 给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
	 * 「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
	 * 示例 1：
	 * 输入：root = [3,1,4,3,null,1,5]
	 * 输出：4
	 * 解释：图中蓝色节点为好节点。
	 * 根节点 (3) 永远是个好节点。
	 * 节点 4 -> (3,4) 是路径中的最大值。
	 * 节点 5 -> (3,4,5) 是路径中的最大值。
	 * 节点 3 -> (3,1,3) 是路径中的最大值。
	 * 示例 2：
	 * 输入：root = [3,3,null,4,2]
	 * 输出：3
	 * 解释：节点 2 -> (3, 3, 2) 不是好节点，因为 "3" 比它大。
	 * 示例 3：
	 * 输入：root = [1]
	 * 输出：1
	 * 解释：根节点是好节点。
	 * 提示：
	 * 二叉树中节点数目范围是 [1, 10^5] 。
	 * 每个节点权值的范围是 [-10^4, 10^4] 。
	 */
	static class GoodNodes {

		/**
		 * 思路：
		 * 深度优先搜索树,同时保存到每个节点时,其路径上的最大值队列,以方便回溯
		 *
		 * @param root
		 * @return
		 */
		public static int goodNodes(TreeNode root) {
			Deque<Integer> deque = new LinkedList<>();
			deque.push(Integer.MIN_VALUE);
			return dfs(root, deque);
		}

		private static int dfs(TreeNode node, Deque<Integer> deque) {
			if (node == null) return 0;
			Integer currMax = deque.peek();
			int count = 0;
			if (node.val >= currMax) {
				deque.push(node.val);
				count++;
			} else {
				deque.push(currMax);
			}
			count += dfs(node.left, deque);
			count += dfs(node.right, deque);
			deque.pop();
			return count;
		}

		/**
		 * 官方思路:
		 * 路径最大值,不需要保存全部,并且回溯;设置int类值最为路径上最大值
		 * 那次每次搜索的int都是单独的,不需要回溯
		 *
		 * @param root
		 * @return
		 */
		public static int goodNodesOfficial(TreeNode root) {
			return dfs(root, Integer.MIN_VALUE);
		}

		private static int dfs(TreeNode node, int pathMax) {
			if (node == null) return 0;
			int count = 0;
			if (node.val >= pathMax) {
				pathMax = node.val;
				count++;
			}
			count += dfs(node.left, pathMax);
			count += dfs(node.right, pathMax);
			return count;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(3);
			TreeNode root1 = new TreeNode(1);
			TreeNode root4 = new TreeNode(4);
			TreeNode root3 = new TreeNode(3);
			TreeNode root1_1 = new TreeNode(1);
			TreeNode root5 = new TreeNode(5);
			TreeNode root2 = new TreeNode(2);
			/*root.left = root1;
			root.right = root4;
			root1.left = root3;
			root4.left = root1_1;
			root4.right = root5;*/
			root.left = root3;
			root3.left = root4;
			root3.right = root2;
			System.out.println(goodNodesOfficial(root));
		}
	}

	/**
	 * 1372. 二叉树中的最长交错路径
	 * 给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
	 * 选择二叉树中 任意 节点和一个方向（左或者右）。
	 * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
	 * 改变前进方向：左变右或者右变左。
	 * 重复第二步和第三步，直到你在树中无法继续移动。
	 * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
	 * 请你返回给定树中最长 交错路径 的长度。
	 * 示例 1：
	 * 输入：root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
	 * 输出：3
	 * 解释：蓝色节点为树中最长交错路径（右 -> 左 -> 右）。
	 * 示例 2：
	 * 输入：root = [1,1,1,null,1,null,null,1,1,null,1]
	 * 输出：4
	 * 解释：蓝色节点为树中最长交错路径（左 -> 右 -> 左 -> 右）。
	 * 示例 3：
	 * 输入：root = [1]
	 * 输出：0
	 * 提示：
	 * 每棵树最多有 50000 个节点。
	 * 每个节点的值在 [1, 100] 之间。
	 */
	static class LongestZigZag {

		/**
		 * 思路:
		 * 深度优先遍历时,记录上一个节点遍历的方向是向右还是向左,如果下一个节点方向相同,则初始为0,否则+1
		 *
		 * @param root
		 * @return
		 */
		private static int max;

		public static int longestZigZag(TreeNode root) {
			dfs(root.left, false, 1);
			dfs(root.right, true, 1);
			return max;
		}

		public static int longestZigZagII(TreeNode root) {
			dfs(root,true,1);
			return max;
		}

		private static void dfs(TreeNode node, boolean isRight, int len) {
			if (node == null) return;
			max = Math.max(len, max);
			dfs(node.left, false, isRight ? len + 1 : 1);
			dfs(node.right, true, isRight ? 1 : len + 1);
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(0);
			TreeNode node1 = new TreeNode(1);
			TreeNode node2 = new TreeNode(2);
			TreeNode node3 = new TreeNode(3);
			TreeNode node4 = new TreeNode(4);
			TreeNode node5 = new TreeNode(5);
			TreeNode node6 = new TreeNode(6);
			TreeNode node7 = new TreeNode(7);
			root.right = node1;
			node1.left = node2;
			node1.right = node3;
			node3.left = node4;
			node3.right = node5;
			node4.right = node6;
			node6.right = node7;
			System.out.println(longestZigZag(root));
		}
	}

	/**
	 * 1161. 最大层内元素和
	 * 给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
	 * 请返回层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。
	 * 示例 1：
	 * 输入：root = [1,7,0,7,-8,null,null]
	 * 输出：2
	 * 解释：
	 * 第 1 层各元素之和为 1，
	 * 第 2 层各元素之和为 7 + 0 = 7，
	 * 第 3 层各元素之和为 7 + -8 = -1，
	 * 所以我们返回第 2 层的层号，它的层内元素之和最大。
	 * 示例 2：
	 * 输入：root = [989,null,10250,98693,-89388,null,null,null,-32127]
	 * 输出：2
	 * 提示：
	 * 树中的节点数在 [1, 104]范围内
	 * -105 <= Node.val <= 105
	 */
	static class maxLevelSum {

		/**
		 * 思路:
		 * 层序遍历的同时,记录层的序号 同时计算这一层的和,记录最大的层内元素和,同时记录层号
		 */
		public static int maxLevelSum(TreeNode root) {
			Deque<TreeNode> deque = new LinkedList<>();
			deque.offer(root);
			int level = 0, max = Integer.MIN_VALUE, maxLevel = 0;
			while (!deque.isEmpty()) {
				int size = deque.size();
				level++;
				int sum = 0;
				for (; size > 0; --size) {
					TreeNode curr = deque.pop();
					sum += curr.val;
					if (curr.left != null) deque.offer(curr.left);
					if (curr.right != null) deque.offer(curr.right);
				}
				if (sum > max) {
					maxLevel = level;
					max = sum;
				}
			}
			return maxLevel;
		}

		/**
		 * 通过深度优先遍历要如何做呢?
		 * 即在深度优先遍历的时候,计入层数,让后把同一层的节点值累计求和
		 * 此处有一个技巧,用List来保存每一层的和,当list.size()==level 说明这一层的节点是第一次遍历到,所以创建这一次的sum=0;
		 * 如果list.size()<level 说明这一层已经有节点遍历过了,直接累计sum即可
		 *
		 * @param root
		 * @return
		 */
		public static int maxLevelSumDfs(TreeNode root) {
			List<Integer> list = new ArrayList<>();
			dfs(root, 0, list);
			int max = Integer.MIN_VALUE, maxLevel = 0;
			for (int i = 0; i < list.size(); i++) {
				int v;
				if ((v = list.get(i)) > max) {
					max = v;
					maxLevel = i;
				}
			}
			return maxLevel + 1;
		}

		private static void dfs(TreeNode node, int level, List<Integer> list) {
			if (node == null) return;
			if (level == list.size()) {
				list.add(node.val);
			} else {
				list.set(level, list.get(level) + node.val);
			}
			dfs(node.left, level + 1, list);
			dfs(node.right, level + 1, list);
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(989);
			TreeNode node1 = new TreeNode(10250);
			TreeNode node2 = new TreeNode(98693);
			TreeNode node3 = new TreeNode(-89388);
			TreeNode node4 = new TreeNode(-32127);
			root.right = node1;
			node1.left = node2;
			node1.right = node3;
			node3.right = node4;
			System.out.println(maxLevelSumDfs(root));
		}
	}

	/**
	 * 700. 二叉搜索树中的搜索
	 * 给定二叉搜索树（BST）的根节点 root 和一个整数值 val。
	 * 你需要在 BST 中找到节点值等于 val 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 null 。
	 * 示例 1:
	 * 输入：root = [4,2,7,1,3], val = 2
	 * 输出：[2,1,3]
	 * 示例 2:
	 * 输入：root = [4,2,7,1,3], val = 5
	 * 输出：[]
	 * 提示：
	 * 树中节点数在 [1, 5000] 范围内
	 * 1 <= Node.val <= 107
	 * root 是二叉搜索树
	 * 1 <= val <= 107
	 */
	static class SearchBST {

		/**
		 * 思路:
		 * 二叉搜索树: BST 即根节点左树的所有节点小于根节点;右树所有节点大于根节点
		 * 按照以上条件进行深度优先搜索
		 *
		 * @param root
		 * @param val
		 * @return
		 */
		public static TreeNode searchBST(TreeNode root, int val) {
			dfs(root, val);
			return res;
		}

		private static TreeNode res = null;

		private static void dfs(TreeNode node, int val) {
			if (node == null) return;
			if (node.val > val) {  // 在左子树中搜索
				dfs(node.left, val);
			} else if (node.val < val) {
				dfs(node.right, val);
			} else {
				res = node;
			}
		}

		/**
		 * 可以确定遍历一个节点的左子节点还是右子节点,那么直接迭代是最清晰的
		 *
		 * @param root
		 * @param val
		 * @return
		 */
		public static TreeNode searchBSTII(TreeNode root, int val) {
			while (root != null) {
				if (root.val > val) {
					root = root.left;
				} else if (root.val < val) {
					root = root.right;
				} else {
					return root;
				}
			}
			return null;
		}

		public static void main(String[] args) {
			TreeNode root = new TreeNode(4);
			TreeNode node2 = new TreeNode(2);
			TreeNode node7 = new TreeNode(7);
			TreeNode node1 = new TreeNode(1);
			TreeNode node3 = new TreeNode(3);
			root.left = node2;
			root.right = node7;
			node2.left = node1;
			node2.right = node3;
			TreeNode treeNode = searchBSTII(root, 5);
			System.out.println();
		}

		/**
		 * 450. 删除二叉搜索树中的节点
		 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
		 * 一般来说，删除节点可分为两个步骤：
		 * 首先找到需要删除的节点；
		 * 如果找到了，删除它。
		 * 示例 1:
		 * 输入：root = [5,3,6,2,4,null,7], key = 3
		 * 输出：[5,4,6,2,null,null,7]
		 * 解释：给定需要删除的节点值是 3，所以我们首先找到 3 这个节点，然后删除它。
		 * 一个正确的答案是 [5,4,6,2,null,null,7], 如下图所示。
		 * 另一个正确答案是 [5,2,6,null,4,null,7]。
		 * 示例 2:
		 * 输入: root = [5,3,6,2,4,null,7], key = 0
		 * 输出: [5,3,6,2,4,null,7]
		 * 解释: 二叉树不包含值为 0 的节点
		 * 示例 3:
		 * 输入: root = [], key = 0
		 * 输出: []
		 * 提示:
		 * 节点数的范围 [0, 104].
		 * -105 <= Node.val <= 105
		 * 节点值唯一
		 * root 是合法的二叉搜索树
		 * -105 <= key <= 105
		 * 进阶： 要求算法时间复杂度为 O(h)，h 为树的高度。
		 */
		static class DeleteNode {

			/**
			 * 思路：
			 * 1.通过迭代的方式找到被删除的节点
			 * 2.替换该节点为其右节点;没有右节点,直接指向左节点
			 * 3.迭代的查找该节点的最左节点-即子树中最左边的节点
			 *
			 * @param root
			 * @param key
			 * @return
			 */
			public static TreeNode deleteNode(TreeNode root, int key) {
				// 找到要被删除的节点,根节点也有可用被删除
				TreeNode hair = new TreeNode(-100001);
				hair.left = root;
				TreeNode node = root, prev = hair;
				boolean flag = true;
				while (node != null) {
					if (node.val > key) {
						prev = node;
						node = node.left;
						flag = true;
					} else if (node.val < key) {
						prev = node;
						node = node.right;
						flag = false;
					} else {
						break;
					}
				}
				if (node == null) return hair.left;
				if (node.right == null) { // 删除的节点没有右节点
					if (flag) prev.left = node.left;
					else prev.right = node.left;
				} else {  // 删除的节点有右节点,那么查看该右子树有没有最左叶子结点
					// 有:则prev -> left ; curr.left -> curr1.left ;  prev1.left -> null; prev1.curr1 -> curr.curr1
					TreeNode curr1 = node.right, prev1 = curr1.right;
					while (curr1.left != null) {
						prev1 = curr1;
						curr1 = curr1.left;
					}
					if (flag) prev.left = curr1;
					else prev.right = curr1;
					if (curr1 != node.right) {  // 有最左叶子节点
						prev1.left = null;
						curr1.left = node.left;
						curr1.right = node.right;
					} else {
						curr1.left = node.left;
					}
				}
				return hair.left;
			}

			public static TreeNode deleteNodeII(TreeNode root, int key) {
				TreeNode cur = root, curParent = null;
				while (cur != null && cur.val != key) {
					curParent = cur;
					if (cur.val > key) {
						cur = cur.left;
					} else {
						cur = cur.right;
					}
				}
				if (cur == null) {
					return root;
				}
				if (cur.left == null && cur.right == null) {
					cur = null;
				} else if (cur.right == null) {
					cur = cur.left;
				} else if (cur.left == null) {
					cur = cur.right;
				} else {
					TreeNode successor = cur.right, successorParent = cur;
					while (successor.left != null) {
						successorParent = successor;
						successor = successor.left;
					}
					if (successorParent.val == cur.val) {
						successorParent.right = successor.right;
					} else {
						successorParent.left = successor.right;
					}
					successor.right = cur.right;
					successor.left = cur.left;
					cur = successor;
				}
				if (curParent == null) {
					return cur;
				} else {
					if (curParent.left != null && curParent.left.val == key) {
						curParent.left = cur;
					} else {
						curParent.right = cur;
					}
					return root;
				}
			}

			public static void main(String[] args) {
				TreeNode root = new TreeNode(5);
				TreeNode node3 = new TreeNode(3);
				TreeNode node2 = new TreeNode(2);
				TreeNode node4 = new TreeNode(4);
				TreeNode node6 = new TreeNode(6);
				TreeNode node7 = new TreeNode(7);
				root.left = node3;
				node3.left = node2;
				node3.right = node4;
				root.right = node6;
				node6.right = node7;
				TreeNode treeNode = deleteNode(root, 3);
				System.out.println();
			}
		}
	}

	/**
	 * 841. 钥匙和房间
	 * 有 n 个房间，房间按从 0 到 n - 1 编号。最初，除 0 号房间外的其余所有房间都被锁住。你的目标是进入所有的房间。然而，你不能在没有获得钥匙的时候进入锁住的房间。
	 * 当你进入一个房间，你可能会在里面找到一套 不同的钥匙，每把钥匙上都有对应的房间号，即表示钥匙可以打开的房间。你可以拿上所有钥匙去解锁其他房间。
	 * 给你一个数组 rooms 其中 rooms[i] 是你进入 i 号房间可以获得的钥匙集合。如果能进入 所有 房间返回 true，否则返回 false。
	 * 示例 1：
	 * 输入：rooms = [[1],[2],[3],[]]
	 * 输出：true
	 * 解释：
	 * 我们从 0 号房间开始，拿到钥匙 1。
	 * 之后我们去 1 号房间，拿到钥匙 2。
	 * 然后我们去 2 号房间，拿到钥匙 3。
	 * 最后我们去了 3 号房间。
	 * 由于我们能够进入每个房间，我们返回 true。
	 * 示例 2：
	 * 输入：rooms = [[1,3],[3,0,1],[2],[0]]
	 * 输出：false
	 * 解释：我们不能进入 2 号房间。
	 * 提示：
	 * n == rooms.length
	 * 2 <= n <= 1000
	 * 0 <= rooms[i].length <= 1000
	 * 1 <= sum(rooms[i].length) <= 3000
	 * 0 <= rooms[i][j] < n
	 * 所有 rooms[i] 的值 互不相同
	 */
	static class CanVisitAllRooms {

		/**
		 * 思路:
		 * 1.初始时可以进入0号房间
		 * 2.每次进入房间,会得到一个钥匙集合,钥匙通向所指定的房间
		 * 3.当所有钥匙都遍历完成,统计是否所有的房间都有遍历到
		 *
		 * @param rooms
		 * @return
		 */
		public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
			boolean[] visited = new boolean[rooms.size()];
			dfs(rooms, 0, rooms.size(), visited);
			for (int i = 0; i < visited.length; ++i) {
				if (!visited[i]) return false;
			}
			return true;
		}

		/**
		 * dfs遍历
		 */
		private static void dfs(List<List<Integer>> rooms, int i, int l, boolean[] visited) {
			if (i >= l) {
				return;
			}
			if (visited[i]) {
				return;
			}
			visited[i] = true;
			List<Integer> keys = rooms.get(i);
			for (int key : keys) {
				dfs(rooms, key, l, visited);
			}
		}

		/**
		 * bfs
		 *
		 * @param rooms
		 * @return
		 */
		public static boolean canVisitAllRoomsBfs(List<List<Integer>> rooms) {
			boolean[] visited = new boolean[rooms.size()];
			Deque<Integer> deque = new LinkedList<>();
			deque.offer(0);
			while (!deque.isEmpty()) {
				Integer pop = deque.pop();
				if (pop >= rooms.size()) continue;
				visited[pop] = true;
				List<Integer> list = rooms.get(pop);
				for (int i : list) {
					if (!visited[i]) deque.offer(i);
				}
			}
			for (int i = 0; i < visited.length; ++i) {
				if (!visited[i]) return false;
			}
			return true;
		}

		public static void main(String[] args) {
			List<List<Integer>> rooms = new ArrayList<>();
			rooms.add(Arrays.asList(1));
			rooms.add(Arrays.asList(2));
			rooms.add(Arrays.asList(3));
			rooms.add(Arrays.asList());
			boolean b = canVisitAllRoomsBfs(rooms);
			System.out.println(b);
		}
	}

	/**
	 * 547. 省份数量
	 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
	 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
	 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
	 * 返回矩阵中 省份 的数量。
	 * 示例 1：
	 * 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
	 * 输出：2
	 * 示例 2：
	 * 输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
	 * 输出：3
	 * 提示：
	 * 1 <= n <= 200
	 * n == isConnected.length
	 * n == isConnected[i].length
	 * isConnected[i][j] 为 1 或 0
	 * isConnected[i][i] == 1
	 * isConnected[i][j] == isConnected[j][i]
	 */
	static class FindCircleNum {

		/**
		 * 思路:
		 * 1.遍历矩阵,当找到相连的(i,j)时,继续找j相连的坐标,直到找不到相连的坐标或者已经遍历过的坐标
		 * 2.当找到相连坐标(i,j)时,去第j行找到所有与j相连的城市,dfs遍历
		 * 3.最后通过visited坐标,来计算有多少个城市相连 -------题意理解错误,本题是要求解总共有多少个省份
		 *
		 * @param isConnected
		 * @return
		 */
		public static int findCircleNum(int[][] isConnected) {
			boolean[][] visited = new boolean[isConnected.length][isConnected[0].length];
			HashSet<Integer> set = new HashSet<>();
			for (int i = 0; i < isConnected.length; i++) {
				for (int j = 0; j < isConnected[0].length; j++) {
					if (i != j) {
						dfs(isConnected, i, j, visited, set);
					}
				}
			}
			return set.size();
		}

		private static void dfs(int[][] isConnected, int i, int j, boolean[][] visited, HashSet<Integer> set) {
			if (i >= isConnected.length || j >= isConnected[0].length) return;
			if (i == j) return;
			if (visited[i][j]) return;
			visited[i][j] = true;
			if (isConnected[i][j] == 1) {  // i和j相连,继续寻找和j相连的城市坐标
				set.add(i);
				set.add(j);
				for (int k = 0; k < isConnected[0].length; k++) {
					dfs(isConnected, j, k, visited, set);
				}
			}
		}

		/**
		 * 如何找到所有省份呢?即所有联通的城市,不能连接其他城市了为一个连通分量,也就是一个省份
		 * 所以拿到一个坐标,一直往下遍历,直到不能遍历为止,就产生一个连通分量
		 * <p>
		 * 关键点在于一个连通分量代表一个相关的所有城市集合,也就是一个省份
		 *
		 * @param isConnected
		 * @return
		 */
		public static int findCircleNumDfs(int[][] isConnected) {
			int cities = isConnected.length;
			boolean[] visited = new boolean[cities];
			int provinces = 0;
			for (int i = 0; i < cities; i++) {
				if (!visited[i]) {
					dfs(isConnected, i, cities, visited);
					provinces++;
				}
			}
			return provinces;
		}

		private static void dfs(int[][] isConnected, int i, int cities, boolean[] visited) {
			for (int j = 0; j < cities; j++) {
				if (isConnected[i][j] == 1 && !visited[j]) {
					visited[j] = true;
					dfs(isConnected, j, cities, visited);
				}
			}
		}

		public static void main(String[] args) {
			int circleNum = findCircleNum(new int[][]{
					{1, 0, 0},
					{0, 1, 0},
					{0, 0, 1}
			});
			System.out.println(circleNum);
		}
	}


}














