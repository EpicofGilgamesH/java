package topologicalSorting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Description 拓扑排序思路学习
 * @Date 2022-06-02 11:11
 * @Created by wangjie
 */
public class TopologicalSorting {
	//拓扑排序,解决问题场景
	//1.编译有依赖关系的文件,如果确定其编译顺序
	//2.穿衣顺序 鞋子->袜子 秋裤->内裤
	//拓扑思路一般是构建有向无环图,通过图的顶点及有向边关系,找出其依赖顺序

	/**
	 * 构建有向无环图,采用链表结构,记录每个顶点的出度,便于理解
	 */
	public static class Graph<T> {
		private T[] vs;//顶点数组
		private LinkedList<T> adj[]; //顶点出度链表集合
		private Map<T, Integer> map; //值和下标的存储关系

		public Graph(T[] vs) {
			this.vs = vs;
			//noinspection unchecked
			adj = new LinkedList[vs.length];
			map = new HashMap<>();
			for (int i = 0; i < vs.length; i++) {
				adj[i] = new LinkedList<>(); //每个顶点都对应一个链表
				map.put(vs[i], i);
			}
		}

		/**
		 * 添加顶点依赖关系,构建有向边 a->b a依赖b 则b需要优先,此处链表记录顶点出度
		 *
		 * @param a
		 * @param b
		 */
		public void add(int a, int b) {
			adj[a].add(vs[b]);
		}

		/**
		 * 通过Kahn思路实现拓扑排序
		 * 思路:遍历所有顶点的出度,如果出度为0则该顶点没有依赖其他任何顶点,则可以优先排序出来;
		 * 然后将依赖它的其他顶点的出度 - 1,再次遍历是否有出度为0的顶点,重复以上操作,直到所有的顶点遍历完成
		 */
		public void topoSortByKahn() {
			//因为指向性的问题,这里需要求其逆邻接表;如果反转指向性,这不需要;求入度数量时,会稍微麻烦点
			//noinspection unchecked
			LinkedList<T>[] inverseAdj = new LinkedList[vs.length];
			for (int i = 0; i < vs.length; i++) { //给链表数组申请空间
				inverseAdj[i] = new LinkedList<>();
			}
			for (int i = 0; i < vs.length; i++) {
				for (int j = 0; j < adj[i].size(); j++) {
					T t = adj[i].get(j); //获取a顶点的指向值  a->b
					inverseAdj[map.get(t)].add(vs[i]); //得到 b->a  vs数组通过值获取序列
				}
			}
			//统计每个顶点的出度数量
			int[] out = new int[vs.length];
			for (int i = 0; i < vs.length; i++) {
				out[i] = adj[i].size();
			}
			LinkedList<Integer> queue = new LinkedList<>();
			//找到出度为0的顶点,放入队列中
			for (int i = 0; i < out.length; i++) {
				if (out[i] == 0)
					queue.add(i);
			}
			//执行遍历操作
			while (!queue.isEmpty()) {
				//取出队列头部数据
				int i = queue.remove(); //该index为出度为0的顶点序列
				System.out.println("->" + vs[i]);
				//依赖i顶点的其他顶点出度-1
				for (int j = 0; j < inverseAdj[i].size(); j++) {
					T t = inverseAdj[i].get(j);
					int ti = map.get(t);
					out[ti]--;
					if (out[ti] == 0)
						queue.add(ti);
				}
			}
		}

		/**
		 * 深度优先遍历
		 * 思路:遍历所有节点,搜索其能到到达的顶点,然后优先打印,最后再打印自己
		 */
		public void topoSortByDFS() {
			boolean[] visited = new boolean[vs.length];
			for (int i = 0; i < vs.length; i++) { //对所有的顶点进行遍历
				if (!visited[i]) {  //可能这个顶点已经被遍历过了,还未遍历的才进行遍历
					visited[i] = true;
					dfs(i, visited);
				}
			}
		}

		/**
		 * dfs 递归方法,遍历其节点的最后顶节点进行打印,然后逐步打印次顶节点,最后打印自己
		 *
		 * @param vertices 需要遍历的节点index
		 * @param visited  对应索的引节点是否已被访问
		 */
		public void dfs(int vertices, boolean[] visited) {
			for (int i = 0; i < adj[vertices].size(); i++) {
				T t = adj[vertices].get(i);
				int ti = map.get(t);
				if (visited[ti]) //已经被遍历过了,则直接进行下一个边
					continue;
				visited[ti] = true;
				dfs(ti, visited);
			}
			System.out.println("->" + vs[vertices]); //优先打印最顶层顶点,逐步往下打印
		}

		//通过拓扑算法来确认依赖关系中是否有环
		//通过kahn完成后输入顶点的个数不等于总顶点个数,表明此时还存在顶点出度不为0的情况,即存在环.

	}

	public static void main(String[] args) {
		String[] vs = new String[]{"a", "b", "c", "d", "e"};
		Graph<String> graph = new Graph<>(vs);
		graph.add(0, 1); //a->b
		graph.add(4, 1); //e->b
		graph.add(1, 2); //b->c
		graph.add(3, 2); //d->c

		//graph.topoSortByKahn();

		graph.topoSortByDFS();
		System.out.println("end.");
	}
}
