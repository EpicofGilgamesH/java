package test.graph;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;

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

	/**
	 * 399. 除法求值
	 */
	public static class CalcEquation {

		static class Pair {
			int index;
			double value;

			Pair(int index, double value) {
				this.index = index;
				this.value = value;
			}
		}

		/**
		 * 本题看了解题还是觉得很难写出来代码
		 * 思路是构建有权向量图
		 *
		 * @param equations
		 * @param values
		 * @param queries
		 * @return
		 */
		public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
			int nvars = 0;
			Map<String, Integer> variables = new HashMap<>();
			int n = equations.size();
			for (int i = 0; i < n; i++) {
				if (!variables.containsKey(equations.get(i).get(0))) {
					variables.put(equations.get(i).get(0), nvars++);
				}
				if (!variables.containsKey(equations.get(i).get(1))) {
					variables.put(equations.get(i).get(1), nvars++);
				}
			}
			// 对每个节点构建权重值
			List<Pair>[] edges = new List[nvars];
			for (int i = 0; i < nvars; i++) {
				edges[i] = new ArrayList<>();
			}
			// 对于每个节点构建有序权重向量图
			for (int i = 0; i < n; i++) {
				int va = variables.get(equations.get(i).get(0)), vb = variables.get(equations.get(i).get(1));
				edges[va].add(new Pair(vb, values[i]));
				edges[vb].add(new Pair(va, 1.0 / values[i]));
			}
			int queriesCount = queries.size();
			double[] ret = new double[queriesCount];
			for (int i = 0; i < queriesCount; i++) {
				List<String> query = queries.get(i);
				double result = -1.0;
				if (variables.containsKey(query.get(0)) && variables.containsKey(query.get(1))) {
					int ia = variables.get(query.get(0)), ib = variables.get(query.get(1));
					if (ia == ib) {  // 同一个节点
						result = 1.0;
					} else {
						Queue<Integer> points = new LinkedList<>();
						points.offer(ia);
						double[] ratios = new double[nvars];
						Arrays.fill(ratios, -1.0);
						ratios[ia] = 1.0;
						while (!points.isEmpty() && ratios[ib] < 0) {
							int x = points.poll();
							for (Pair pair : edges[x]) {
								int y = pair.index;
								double val = pair.value;
								if (ratios[y] < 0) {
									ratios[y] = ratios[x] * val;
									points.offer(y);
								}
							}
						}
						result = ratios[ib];
					}
				}
				ret[i] = result;
			}
			return ret;
		}
	}

	/**
	 * 210. 课程表 II
	 */
	public static class FindOrder {

		/**
		 * 广度优先遍历-构建拓扑图并记录入度值
		 * 1.如果A->B 学习A课程必须先学习B课程,那A有一条有向边指向B;且B的入度+1;这样做的原因是 有一个特性,当课程入度为0时,说明其没有
		 * 前置的学习条件,那么它就可以学习了
		 * 2.如何简单的构造拓扑图、记录入度数呢?   --这个是比较没有头绪的一个点
		 * 按照一般的思路,创建一个节点对象Node,其有课程名、有向边集合、入度值这三个成员;
		 * 但是本题课程为数字,且为包含0的正整数,可以使用索引下标来标记课程名;所以可以用List<List<>>结构
		 * 此时入度但是用int[]数组来存放,同样使用索引下标来标记课程名;
		 * 3.如何找出课程的安排序列呢?
		 * 特性:入度为0的课程即可以直接学习的课程.
		 * * 1)首先找出所有入度为0的课程,放入队列中;
		 * * 2)每次出队一个课程X,相当于安排该课程进行学习;找到X的有向边,将其入度-1(当X被安排后,X的有向边所指的课程入度都会-1)
		 * *   此时如有其有向边入度为0了,说明其下一步可以被安排,将其放入队列中;
		 * *   如果其有向边入度都不为0;X课程处理完
		 * * 3)当队列中的节点处理完,但已安排的课程数量不等于总的课程数量,说明拓扑图存在环,有课程永远也没法安排
		 *
		 * @param numCourses
		 * @param prerequisites
		 * @return
		 */
		public static int[] findOrder(int numCourses, int[][] prerequisites) {
			// 拓扑图
			List<List<Integer>> topology = new ArrayList<>();
			// 存储每个节点的入度
			int[] degrees = new int[numCourses];
			// 存储节点序列答案
			int[] result = new int[numCourses];
			// 答案数组的当前下标序列
			int idx = 0;
			for (int i = 0; i < numCourses; i++) {
				topology.add(new ArrayList<>());
			}
			for (int i = 0; i < prerequisites.length; i++) {
				topology.get(prerequisites[i][1]).add(prerequisites[i][0]);
				++degrees[prerequisites[i][0]];
			}
			Deque<Integer> deque = new LinkedList<>();
			// 将入度为0的节点放入队列中
			for (int i = 0; i < degrees.length; i++) {
				if (degrees[i] == 0) deque.offer(i);
			}
			while (!deque.isEmpty()) {
				Integer cur = deque.poll();
				// 出队的节点放入答案中
				result[idx++] = cur;
				// 将其有向边的入度-1
				List<Integer> sides = topology.get(cur);
				for (int side : sides) {
					if (--degrees[side] == 0) {
						deque.offer(side);
					}
				}
			}
			// 当处理完后,以安排的节点数量不等于总课程数量,说明拓扑图存在环,有课程没法安排
			if (idx != numCourses) {
				return new int[0];
			}
			return result;
		}

		// 有向图
		private static List<List<Integer>> edges;
		// 标记每个节点的状态:0=未搜索 1=搜索中 2=已完成
		private static int[] visited;
		// 数组模拟队列 idx=0 为栈顶 idx=n-1为栈底
		private static int[] result;
		// 有向图是否存在环
		private static boolean valid = true;
		// result模拟栈的下标
		private static int index;

		/**
		 * 深度优先遍历-拓扑图
		 * 相当于广度优先遍历来说,深度优先遍历更像一种逆向的解决思路;总是找到最深的一个节点,然后回溯回来
		 * 对于任意个节点,搜索过程中有三种状态
		 * 1.未搜索:还没有搜索到这个节点
		 * 2.搜索中:搜索过这个节点,但还没回溯到该节点,该节点还未入栈,存在相邻的节点没有所有完成
		 * 3.已完成:搜索过且已回溯过这个节点,该节点已入栈,并且所有该节点的相邻节点出现在站的更底部的位置,满足拓扑排序的要求.
		 * 搜索流程:
		 * 1.将当前搜索的节点u标记为[搜索中],遍历该节点的每一个相邻节点v;
		 * * 1)如果v为[未搜索] 开始搜索v,搜索完成回溯到u;
		 * * 2)如果v为[搜查中] 说明存在环,不存储拓扑排序
		 * * 3)如果v为[已完成] 说明搜索并回溯过这个节点;该节点相关的相邻节点都已处理;
		 * 2.当u的所有相邻节点都为[以完成],将u入栈,并标记为[已完成]
		 *
		 * @param numCourses
		 * @param prerequisites
		 * @return
		 */
		public static int[] findOrderI(int numCourses, int[][] prerequisites) {
			edges = new ArrayList<>();
			for (int i = 0; i < numCourses; ++i) {
				edges.add(new ArrayList<>());
			}
			visited = new int[numCourses];
			result = new int[numCourses];
			index = numCourses - 1;
			for (int[] info : prerequisites) {
				edges.get(info[1]).add(info[0]);
			}
			// 每次挑选一个「未搜索」的节点，开始进行深度优先搜索
			for (int i = 0; i < numCourses && valid; ++i) {
				if (visited[i] == 0) {
					dfs(i);
				}
			}
			if (!valid) {
				return new int[0];
			}
			// 如果没有环，那么就有拓扑排序
			return result;
		}

		public static void dfs(int u) {
			// 将节点标记为「搜索中」
			visited[u] = 1;
			// 搜索其相邻节点
			// 只要发现有环，立刻停止搜索
			for (int v : edges.get(u)) {
				// 如果「未搜索」那么搜索相邻节点
				if (visited[v] == 0) {
					dfs(v);
					if (!valid) {
						return;
					}
				}
				// 如果「搜索中」说明找到了环
				else if (visited[v] == 1) {
					valid = false;
					return;
				}
			}
			// 将节点标记为「已完成」
			visited[u] = 2;
			// 将节点入栈
			result[index--] = u;
		}

		public static void main(String[] args) {
			int[][] prerequisites = new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}};
			int[] order = findOrderI(4, prerequisites);
			System.out.println(Arrays.toString(order));
		}
	}
}
