package twentythree.practice;

/**
 * @Description 自己手写二叉树
 * @Date 2020-12-22 15:12
 * @Created by wangjie
 */
public class MyBinaryTree {

	/**
	 * 根节点
	 */
	private Node root;

	/**
	 * 节点
	 */
	class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}
	}

	/**
	 * 添加数据,生成节点
	 * 新增节点时,需要进行树的查找,找到该节点应该生成的地方
	 * 从根节点处进行遍历查找,如果< rv 继续查找左边;> rv 继续查找右边
	 * 当发现当前节点为null时,说明该处可以插入节点数据,并退出
	 *
	 * @param current
	 * @param value
	 * @return
	 */
	public Node addNode(Node current, int value) {
		if (current == null) {
			return new Node(value);
		}
		int data = current.data;
		if (value < data) {
			current.left = addNode(current.left, value);
		} else if (value > data) {
			current.right = addNode(current.right, value);
		} else {
			return current;
		}
		return current;
	}

	public void addNode(int value) {
		root = addNode(root, value);
	}

	/**
	 * 前序遍历 先遍历根节点,再遍历左子节点,再遍历右子节点
	 * 每个节点的操作都是
	 */
	public void preTraverse(Node node) {
		if (node == null) {
			return;
		}
		System.out.println(node.data);
		preTraverse(node.left);
		preTraverse(node.right);
	}

	public void preTraverse() {
		preTraverse(root);
	}

	/**
	 * 中序遍历 先遍历左节点,再遍历根节点,再遍历右节点
	 *
	 * @param node
	 */
	public void midTraverse(Node node) {
		if (node == null) {
			return;
		}
		midTraverse(node.left);
		System.out.println(node.data);
		midTraverse(node.right);
	}

	public void midTraverse() {
		midTraverse(root);
	}

	/**
	 * 后续遍历 先遍历左节点,再遍历右节点,再遍历根节点
	 *
	 * @param node
	 */
	public void sufTraverse(Node node) {
		if (node == null)
			return;
		sufTraverse(node.left);
		sufTraverse(node.right);
		System.out.println(node.data);
	}

	public void sufTraverse() {
		sufTraverse(root);
	}


	public static void main(String[] args) {
		MyBinaryTree tree = new MyBinaryTree();
		tree.addNode(1);
		tree.addNode(2);
		tree.addNode(3);
		tree.addNode(4);
		tree.addNode(5);

		tree.preTraverse();
		System.out.println("end...");

		tree.midTraverse();
		System.out.println("end...");

		tree.sufTraverse();
		System.out.println("end...");
	}
}
