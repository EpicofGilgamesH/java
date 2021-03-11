package twentythree.practice;

/**
 * @Description 二叉查找树
 * @Date 2020-12-23 17:42
 * @Created by wangjie
 */
public class BinarySearchTree {

	/**
	 * 根节点
	 */
	private Node tree;

	public static class Node {
		private int data;
		private Node left;
		private Node right;

		public Node(int data) {
			this.data = data;
		}
	}

	/**
	 * 查找数据
	 * 从根目录开始查找,> data则查找其左子节点 < data 则查找其右子节点 = data 则直接返回
	 * 退出条件为当前比较的节点为null,则无
	 *
	 * @param data
	 * @return
	 */
	public Node find(int data) {
		Node p = tree;
		while (p != null) {
			if (p.data < data)
				p = p.left;
			else if (p.data > data)
				p = p.right;
			else
				return p;
		}
		return null;
	}

	/**
	 * 插入数据(不考虑重复新增)
	 * 从根目录开始查找,> data则查找其左子节点,如果左节点为null,则创建左节点
	 * < data则查找右子节点,如果右节点为null,则创建右节点
	 *
	 * @param data
	 * @return
	 */
	public boolean insert(int data) {
		if (tree == null) {
			tree = new Node(data);
			return true;
		}
		Node p = tree;
		while (p != null) {
			if (p.data > data) {
				if (p.left == null) {
					p.left = new Node(data);
					return true;
				}
				p = p.left;
			} else if (p.data < data) {
				if (p.right == null) {
					p.right = new Node(data);
					return true;
				}
				p = p.right;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 删除节点
	 * 从root目录开始往下进行查找
	 * 1.如果要删除的节点没有子节点,直接将该需要删除节点的父节点指向该节点的指针置为null
	 * 2.如果要删除的节点有一个子节点,则将指向该需要删除节点的父节点指针直接指向该子节点
	 * 3.如果要删除的节点有两个子节点,则找到该需要删除节点的子节点中最小节点,替换掉该节点
	 *
	 * @param data
	 * @return
	 */
	public boolean delete(int data) {
		Node p = tree;
		Node pp = null;//父节点
		while (p != null && p.data != data) {
			pp = p;
			if (data > p.data)
				p = p.right;
			else
				p = p.left;
		}
		if (p == null)
			return false;
		if (p.left != null && p.right != null) {
			//找最小的节点
			Node minP = p.right;
			Node minPp = p; //父节点
			while (minP.left != null) {
				minPp = minP;
				minP = minP.left;
			}
			p.data = minP.data;//将最小节点的data替换到p节点中
			p = minP;
			pp = minPp;
		}

		Node child;
		if (p.left != null)
			child = p.left;
		else if (p.right != null)
			child = p.right;
		else child = null;

		if (pp == null)
			tree = child; //根节点
		else if (pp.left == p)
			pp.left = child;
		else if (pp.right == p)
			pp.right = child;
		return true;
	}

	/**
	 * 给定二叉树的确切高度
	 * 递归思路: max(左子树高度,右子树高度)+1
	 *
	 * @return
	 */
	public int hight(Node node) {
		//递归出口
		if (node == null) {
			return 0;
		}
		if (node.left != null || node.right != null) {
			return Math.max(hight(node.left), hight(node.right)) + 1;
		}
		return 0;
	}


	public static void main(String[] args) {
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(33);
		tree.insert(16);
		tree.insert(50);
		tree.insert(13);
		tree.insert(18);
		tree.insert(34);
		tree.insert(58);
		tree.insert(15);
		tree.insert(17);
		tree.insert(25);
		tree.insert(51);
		tree.insert(66);
		tree.insert(19);
		tree.insert(27);
		tree.insert(55);

		tree.delete(18);

		int hight = tree.hight(tree.tree);

		System.out.println(hight);

	}


}
