package map;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

/**
 * @Description 临接链表-图结构
 * @Date 2021-05-11 17:09
 * @Created by wangjie
 */
public class LinkedListMap {
	private int v;//顶点个数
	private LinkedList<Integer>[] ll; //临接表集合

	public LinkedListMap(int v) {
		this.v = v;
		//每个定点构成数组,顶点后面连接的是链表
		ll = new LinkedList[v];
		for (int i = 0; i < v; i++) {
			ll[i] = new LinkedList<>();
		}
	}

	/**
	 * 无向图中添加一条边 s(下标)和t(下标)进行连接
	 *
	 * @param s
	 * @param t
	 */
	public void add(int s, int t) {
		ll[s].add(t);
		ll[t].add(s);
	}

	/**
	 * 广度优先搜索s->t的最短距离 (层级遍历)
	 * 在最坏的情况下(即遍历完所有的链表集合)时间复杂度为定点数v+边数s 即O(s)
	 * 空间复杂度 使用队列和两个数组大小均不超过V 即O(V)
	 * <p>
	 * 需要boolean[] visited 用来记录被访问过的顶点
	 * 需要LinkedList<Integer> queue队列,存储已经被访问、但相连的顶点还没有被访问的顶点
	 * 需要int[] prev 用来记录遍历的顶点->其上一个顶点
	 *
	 * @param s
	 * @param t
	 */
	public void bfs(int s, int t) {
		if (s == t) return;
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[v];
		int[] prev = new int[v];
		//初始化prev
		for (int i = 0; i < prev.length; i++) {
			prev[i] = -1;
		}
		//需要搜索的顶点队列
		queue.add(s);
		while (queue.size() != 0) {
			Integer w = queue.poll();
			for (int j = 0; j < ll[w].size(); j++) {
				Integer a = ll[w].get(j);
				if (!visited[a]) {
					prev[a] = w;
					if (a == t) {
						print(prev, s, t);
						return;
					}
					visited[a] = true;
					queue.add(a);
				}
			}
		}
	}

	/**
	 * 使用广度优先搜索s节点的l度临接点集合
	 *
	 * @param s
	 * @param l
	 */
	public List<Integer> nearLimit(int s, int l) {

		return null;
	}

	/**
	 * 记录是否已经找到
	 */
	private boolean isFind = false;

	/**
	 * 深度优先搜索 s->t的最短距离,从其实节点开始,往后进行匹配;
	 * 当最后的节点没有临近节点切还未找到最终指定节点时,进行回溯,然后重新开始深度优先搜索(递归)
	 * 找到指定t时,结束递归操作
	 *
	 * @param s
	 * @param t
	 */
	public void dfs(int s, int t) {
		if (s == t) return;
		boolean[] visited = new boolean[v];
		int[] prev = new int[v];
		//初始化prev
		for (int i = 0; i < v; i++) {
			prev[i] = -1;
		}
		//深度优先查找
		recurDfs(s, t, visited, prev);
		print(prev, s, t);
	}

	/**
	 * 递归查找
	 *
	 * @param w      当前正在查找的节点
	 * @param t      目标节点
	 * @param isFind 是否已经找到
	 * @param prev   上一个节点值集合
	 */
	public void recurDfs(int w, int t, boolean[] visited, int[] prev) {
		//递归出口
		if (isFind) return;
		visited[w] = true;//标记已经查找
		if (w == t) {
			isFind = true;
			return;
		}
		for (int i = 0; i < ll[w].size(); i++) {
			Integer q = ll[w].get(i);
			if (!visited[q]) {
				prev[q] = w;
				recurDfs(q, t, visited, prev);
			}
		}
	}

	/**
	 * 递归打印搜索路径
	 *
	 * @param prev
	 * @param s
	 * @param t
	 */
	private void print(int[] prev, int s, int t) {
		if (prev[t] != -1 && t != s) {
			print(prev, s, prev[t]);
		}
		System.out.println(t + " ");
	}

	public static void main(String[] args) {
		LinkedListMap map = new LinkedListMap(8);
		map.add(0, 1);
		map.add(0, 3);

		map.add(1, 2);
		map.add(1, 4);

		map.add(2, 5);

		map.add(3, 4);

		map.add(4, 5);
		map.add(4, 6);

		map.add(5, 4);
		map.add(5, 7);

		map.add(6, 7);

		map.add(7, 6);

		//map.bfs(0, 6);
		map.dfs(0, 6);
	}
}
