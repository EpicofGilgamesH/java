package test.graph;

import java.util.*;

/**
 * 图
 */
public class LeetCodeCase {

	static class Node {
		public int val;
		public List<Node> neighbors;

		public Node() {
			val = 0;
			neighbors = new ArrayList<>();
		}

		public Node(int _val) {
			val = _val;
			neighbors = new ArrayList<Node>();
		}

		public Node(int _val, ArrayList<Node> _neighbors) {
			val = _val;
			neighbors = _neighbors;
		}
	}

	/**
	 * 133. 克隆图
	 * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
	 * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
	 * class Node {
	 * public int val;
	 * public List<Node> neighbors;
	 * }
	 * 测试用例格式：
	 * 简单起见，每个节点的值都和它的索引相同。例如，第一个节点值为 1（val = 1），第二个节点值为 2（val = 2），以此类推。该图在测试用例中使用邻接列表表示。
	 * 邻接列表 是用于表示有限图的无序列表的集合。每个列表都描述了图中节点的邻居集。
	 * 给定节点将始终是图中的第一个节点（值为 1）。你必须将 给定节点的拷贝 作为对克隆图的引用返回。
	 * 示例 1：
	 * 输入：adjList = [[2,4],[1,3],[2,4],[1,3]]
	 * 输出：[[2,4],[1,3],[2,4],[1,3]]
	 * 解释：
	 * 图中有 4 个节点。
	 * 节点 1 的值是 1，它有两个邻居：节点 2 和 4 。
	 * 节点 2 的值是 2，它有两个邻居：节点 1 和 3 。
	 * 节点 3 的值是 3，它有两个邻居：节点 2 和 4 。
	 * 节点 4 的值是 4，它有两个邻居：节点 1 和 3 。
	 * 示例 2：
	 * 输入：adjList = [[]]
	 * 输出：[[]]
	 * 解释：输入包含一个空列表。该图仅仅只有一个值为 1 的节点，它没有任何邻居。
	 * 示例 3：
	 * 输入：adjList = []
	 * 输出：[]
	 * 解释：这个图是空的，它不含任何节点。
	 * 提示：
	 * 这张图中的节点数在 [0, 100] 之间。
	 * 1 <= Node.val <= 100
	 * 每个节点值 Node.val 都是唯一的，
	 * 图中没有重复的边，也没有自环。
	 * 图是连通图，你可以从给定节点访问到所有节点。
	 */
	public static class CloneGraph {

		private static Map<Node, Node> visited = new HashMap<>();

		/**
		 * dfs 深度优先遍历
		 * 1.无向连通图中,无向优先边的定义 例如A-B 则可表示为A->B B->A ;所以深度优先遍历时需要考虑是否遍历过节点的问题
		 * 2.关键点在于用一个HashMap来存储原节点和克隆节点的对应关系 {A:A1} {B:B1}
		 * A -- B
		 * |    |
		 * D -- C
		 * 在克隆时,A的临接边有 [B,D] B的临接边有[A,C] C的临接边有[B,D] D的临接边有[A,C]
		 * 遍历节点A时,会生成A1节点;同时遍历其临接边[B,D] 为A1节点创建临接边[B1,D1]
		 * 遍历节点B时,会生成B1节点,在遍历到其临接边A时,发现A节点已经遍历过,直接返回A1节点,作为B1节点的临接边
		 *
		 * @param node
		 * @return
		 */
		public static Node cloneGraph(Node node) {
			if (node == null) return null;
			// 如果当前节点已经遍历过,直接返回其克隆节点
			if (visited.containsKey(node)) return visited.get(node);
			// 创建当前node的克隆节点,其临接边需要置空
			Node clone = new Node(node.val, new ArrayList<>());
			// 标记node已经被遍历过
			visited.put(node, clone);
			// 拷贝节点的临接边
			for (Node neighbor : node.neighbors) {
				clone.neighbors.add(cloneGraph(neighbor));
			}
			return clone;
		}

		/**
		 * bfs 广度优先遍历
		 * A -- B
		 * |    |
		 * D -- C
		 * 同深度优先遍历,需要用HashMap存放已遍历过的节点,但遍历方式不同;
		 * 1.第一步遍历A节点,并构建其临接边B,D
		 * 2.第二步遍历上一节点的临接边B,D;同时构建其临接边
		 *
		 * @param node
		 * @return
		 */
		public static Node cloneGraphI(Node node) {
			if (node == null) return null;
			HashMap<Node, Node> visited = new HashMap<>();
			Deque<Node> deque = new LinkedList<>();
			deque.addFirst(node);
			int i = 0;
			Node first = null;
			while (!deque.isEmpty()) {
				Node cur = deque.pollLast();
				// 进行当前节点的克隆
				Node clone = bfsClone(cur, visited);
				if (i == 0) first = clone;
				// 进行当前节点临接边的克隆
				for (Node neighbor : cur.neighbors) {
					// 将临接边放入队列,已经放入过的不需要再放入
					if (!visited.containsKey(neighbor)) deque.addFirst(neighbor);
					// 临接边的克隆
					Node neighborClone = bfsClone(neighbor, visited);
					clone.neighbors.add(neighborClone);
				}
				i++;
			}
			// 返回第一个被克隆的节点
			return first;
		}

		public static Node bfsClone(Node node, HashMap<Node, Node> visited) {
			// 进行当前节点的克隆
			Node clone;
			if (visited.containsKey(node)) {
				clone = visited.get(node);
			} else {
				clone = new Node(node.val, new ArrayList<>());
				// 放入HashMap中
				visited.put(node, clone);
			}
			return clone;
		}

		/**
		 * bfs官方思路中,处理返回第一个克隆节点的方式和一些细节上的区别
		 *
		 * @param node
		 * @return
		 */
		public static Node cloneGraphII(Node node) {
			if (node == null) return null;
			HashMap<Node, Node> visited = new HashMap();
			Deque<Node> deque = new LinkedList<>();
			deque.addFirst(node);
			// 提前克隆第一个节点,并放入HashMap中,这样可以直接获取到这个克隆节点
			visited.put(node, new Node(node.val, new ArrayList<>()));
			while (!deque.isEmpty()) {
				Node cur = deque.pollLast();
				// 直接遍历邻居节点,为什么可以不克隆当前节点呢?因为在遍历邻居节点时,必然会创建;因为队列的元素就是在邻居节点中入队的
				for (Node neighbor : cur.neighbors) {
					if (!visited.containsKey(neighbor)) {
						visited.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
						deque.addFirst(neighbor);
					}
					// 更新当前节点的邻居列表
					visited.get(cur).neighbors.add(visited.get(neighbor));
				}
			}
			return visited.get(node);
		}

		public static void main(String[] args) {
			Node n1 = new Node(1, new ArrayList<>());
			Node n2 = new Node(2, new ArrayList<>());
			Node n3 = new Node(3, new ArrayList<>());
			Node n4 = new Node(4, new ArrayList<>());
			n1.neighbors.add(n2);
			n1.neighbors.add(n4);
			n2.neighbors.add(n1);
			n2.neighbors.add(n3);
			n3.neighbors.add(n2);
			n3.neighbors.add(n4);
			n4.neighbors.add(n1);
			n4.neighbors.add(n3);
			Node node = cloneGraphI(n1);
			System.out.println();
		}
	}
}
