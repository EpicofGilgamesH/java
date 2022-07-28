package shortestPath;

import lombok.Data;

import java.util.LinkedList;

/**
 * @Description 最短距离算法
 * 思路 :1.将所有的交叉路口设为顶点、道路设为边,构建有向有权图 - 建模
 * 2.使用优先级队列和动态规划,找到最优路线
 * @Date 2022-06-07 10:45
 * @Created by wangjie
 */
public class ShortestPath {

	/**
	 * 有向有权图的邻接表数据结构
	 */
	@Data
	public static class Graph {
		private int v; //顶点个数
		private LinkedList<Edge> adj[]; //邻接表

		public Graph(int v) {
			//noinspection unchecked
			this.adj = new LinkedList[v];
			for (int i = 0; i < v; i++) { //开辟空间
				adj[i] = new LinkedList<>();
			}
		}
	}

	/**
	 * 边属性-数据结构
	 */
	@Data
	public static class Edge {
		private int sid; //边起点顶点编号
		private int tid; //边终点顶点编号
		private int w; //边权重

		public Edge(int sid, int tid, int w) {
			this.sid = sid;
			this.tid = tid;
			this.w = w;
		}
	}

	/**
	 * 顶点属性,从起始点到该顶点的距离
	 */
	@Data
	public static class Vertex {
		private int id; //顶点的编号,即存放的位置
		private int dist; //从起点到顶点的距离

		private int index; //优先级队列下标

		public Vertex(int id, int dist) {
			this.id = id;
			this.dist = dist;
		}
	}

	//单源最短路径 一个顶点到另一个顶点的最短路径

	/**
	 * 优先级队列,因为jdk提供的优先级队列,没有暴露更新数据的接口,所以需要手动实现
	 * 此处优先级队列为小顶堆,从堆顶获取最小数据
	 */
	public static class PriorityQueue {
		private Vertex[] nodes; //节点值
		private int count; //队列总长度
		private int size = 0;  //已放入长度

		public PriorityQueue(int v) {
			this.count = v;
			this.nodes = new Vertex[v + 1];
		}

		/**
		 * 获取元素,拿到堆顶,并删除堆顶元素,剩下元素重新进行堆化操作
		 * 1.将堆中最后一个元素移到堆顶
		 * 2.自上而下进行堆化操作
		 *
		 * @return
		 */
		public Vertex poll() {
			if (size == 0) return null;
			Vertex root = nodes[1];
			Vertex node = new Vertex(root.getId(), root.getDist()); //堆顶元素
			nodes[1] = nodes[size]; //堆尾元素放在堆顶的位置
			size--;
			int i = 1;
			while (true) {
				int pos = i;
				if (2 * i <= size && nodes[2 * i].dist < nodes[i].dist) { //父节点与左子节点比较
					pos = 2 * i;
				}
				if (2 * i + 1 < size && nodes[2 * i + 1].dist < nodes[i].dist) { //父节点与右子节点比较
					pos = 2 * i + 1;
				}
				if (pos == i) {
					return node;
				}
				//交换
				swap(nodes[i], nodes[pos]);
				i = pos; //从该子节点位置继续往下堆化
			}
		}

		/**
		 * 添加元素,构建小顶堆
		 *
		 * @param v
		 */
		public void add(Vertex v) {
			//插入一个元素,从下至上进行堆化操作,堆从index=1开始
			//新插入的元素,放到第一个未存放数据的位置,即数组中第一个为null值的位置
			if (size >= count) {
				//队列已满,优化功能应考虑扩容,使用List较为合适,此处为方便,使用数组作为数据结构
				return;
			}
			nodes[++size] = v; //赋值
			v.setIndex(size);
			int i = size;
			while (i / 2 > 0 && v.dist < nodes[i / 2].dist) { //父节点存在的情况下,与其父节点进行比较
				//与父节点进行交换,引用类型交换其值
				swap(nodes[i], nodes[i / 2]);
				i = i / 2;
			}
		}


		/**
		 * 更新指定节点的值,需要重新堆化
		 * 修改一个元素的值,先找到该元素的位置
		 *
		 * @param v
		 */
		public void update(Vertex v) {
			int i = v.getIndex(); //找到元素的位置
			while (i / 2 > 0 && v.dist < nodes[i / 2].dist) { //向上比较,堆化
				swap(nodes[i], nodes[i / 2]);
				i = i / 2;
			}
			while (true) {  //向下堆化
				int pos = i;
				if (2 * i <= size && nodes[2 * i].dist < nodes[i].dist) { //父节点与左子节点比较
					pos = 2 * i;
				}
				if (2 * i + 1 < size && nodes[2 * i + 1].dist < nodes[i].dist) { //父节点与右子节点比较
					pos = 2 * i + 1;
				}
				if (pos == i) {
					break;
				}
				//交换
				swap(nodes[i], nodes[pos]);
				i = pos; //从该子节点位置继续往下堆化
			}
		}

		/**
		 * 优先级队列是否为空
		 *
		 * @return
		 */
		public boolean isEmpty() {
			return size <= 1;
		}

		/**
		 * 交换v1,v2的值
		 *
		 * @param v1
		 * @param v2
		 */
		private void swap(Vertex v1, Vertex v2) {
			int temp1 = v1.getId();
			int temp2 = v1.getDist();
			int temp3 = v1.getIndex();
			v1.setId(v2.getId());
			v1.setDist(v2.getDist());
			v1.setIndex(v2.getIndex());
			v2.setId(temp1);
			v2.setDist(temp2);
			v2.setIndex(temp3);
		}

		//建堆操作有两种思路
		// 1.新增一个元素,从下到上进行堆化;知道所有的元素新增完成
		// 2.从所有的非叶子节点进行从上至下的堆化操作

		/**
		 * 堆的实际结构是数组,index=0处没有元素,从1开始,左节点index=2*1 右节点index=2*i+1;
		 * 设堆的总元素为n个,堆的非叶子节点最大为 2i <= n 得 i <= n/2;即非叶子节点范围[1...n/2]
		 */
		private void build(Vertex[] array) {
			//本场景不用建堆操作
		}
	}

	public static void main(String[] args) {
		short a = 8;
		int i = a << 1;
		System.out.println(i);

		long l = 5000 - System.currentTimeMillis() % 1000;
		System.out.println(l);


		boolean m = false, n = true, o = false;
		if ((!m || !n) && !o) {
			System.out.println("");
		}

		if ((!m || !n) && !o) {
			System.out.println("");
		}

		// !((m && n) or o) => (!m || !n) or !o
		//       !((trigger_code in (0, 200) and handle_code = 0) OR(handle_code = 200))
		//等价于  (trigger_code not in (0, 200) or handle_code !=0) and handle_code!=200
	}

}
