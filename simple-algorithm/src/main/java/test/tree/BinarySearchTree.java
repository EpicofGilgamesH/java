package test.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 二叉查找树
 * @Date 2022-08-18 10:07
 * @Created by wangjie
 */
public class BinarySearchTree {

	public TreeNode root;

	/**
	 * 树节点
	 */
	static class TreeNode {
		private int data;
		private TreeNode parent;
		private TreeNode left;
		private TreeNode right;

		TreeNode(int date) {
			this.data = date;
		}
	}

	/**
	 * 添加节点,不考虑重复新增
	 */
	public boolean insert(int v) {
		if (root == null) {
			root = new TreeNode(v);
			return true;
		}
		TreeNode n = root;
		while (true) {
			if (v > n.data) {
				if (n.right == null) {
					TreeNode newNode = new TreeNode(v);
					newNode.parent = n;
					n.right = newNode;
					return true;
				}
				n = n.right;
			} else if (v < n.data) {
				if (n.left == null) {
					TreeNode newNode = new TreeNode(v);
					newNode.parent = n;
					n.left = newNode;
					return true;
				}
				n = n.left;
			} else {
				return false;
			}
		}
	}

	/**
	 * 删除元素
	 *
	 * @param v
	 * @return
	 */
	public boolean delete(int v) {

		return false;
	}

	/**
	 * 打印二叉搜索树 中-左-右
	 */
	public void print() {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			//左右子结点入队
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
			System.out.println(node.data);
		}
	}

	/**
	 * 中序遍历树 左-中-右
	 */
	public void midTraverse() {
		mid(this.root);
	}

	private void mid(TreeNode treeNode) {
		if (treeNode == null) return; //退出条件
		mid(treeNode.left); //先遍历左子树
		System.out.println(treeNode.data); //再遍历自己
		mid(treeNode.right); //最后遍历右子树
	}

	/**
	 * 前序遍历 中-左-右
	 */
	public void preTraverse() {
		pre(this.root);
	}

	private void pre(TreeNode node) {
		if (node == null) return;
		System.out.println(node.data);
		pre(node.left);
		pre(node.right);
	}

	public static void main(String[] args) {
		BinarySearchTree tree = new BinarySearchTree();
		tree.insert(8);
		tree.insert(11);
		tree.insert(14);
		tree.insert(7);
		tree.insert(2);
		tree.insert(20);
		tree.insert(6);
		tree.insert(17);
		tree.insert(32);

		System.out.println("");

		tree.print();
		System.out.println("----------------------------");
		tree.preTraverse();

	}
}
