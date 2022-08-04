package test;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳表实现范围查找、top K 查找、top [K-L] 范围查找
 * 跳表在索引节点加range字段,记录原链表到该节点的个数,实现top K 和top K-L
 * 新增、删除时,如果操作*************待实现 todo...
 * @Date 2022-07-28 13:48
 * @Created by wangjie
 */
public class SimpleSkipList {

	/**
	 * 随机函数概率
	 */
	private static final float PROBABILITY = 0.5f;
	/**
	 * 跳表最大层级 2,4,8,16...n  16层 2^16  65536
	 */
	private static final int MAX_LEVEL = 16;
	/**
	 * 当前skipList层级数,初始为1
	 */
	private int levelCount = 1;

	private Node node = new Node();

	/**
	 * skipList 新增数据,新增原链表的同时,将通过随机函数,在索引层中新增对应结点
	 * 考虑修改的情况该怎么做???********
	 *
	 * @param value
	 * @param id
	 */
	public void insert(int value, String id) {
		//新增索引层级(随机)
		int l = randomLevel();
		Node newNode = new Node(value, id, l);
		//需要插入索引结点在每一层的前置结点 集合
		Node[] preNodes = new Node[l];
		for (int i = 0; i < l; i++) {
			preNodes[i] = node;
		}
		Node p = node;
		for (int i = l - 1; i >= 0; --i) {
			//在每一层索引中,从起始结点,往右进行遍历,
			//当data值 > 下一个结点值时,那上一个结点即为需要新增索引的前置结点
			//down指针是通过数组来实现,同一个结点不同的层级,如果有值,则说明存在down指针
			while (p.nextNodes[i] != null && p.nextNodes[i].value < value) {
				p = p.nextNodes[i];
			}
			//当发现p.nextNodes[i].value > value时,则说明p是新插入索引结点的前置结点
			preNodes[i] = p;
		}
		for (int i = 0; i < l; ++i) {
			//在每一层索引中,将新的结点插入,preNodes[i] -> newNode -> preNode[i].nextNodes[i]
			newNode.nextNodes[i] = preNodes[i].nextNodes[i];
			preNodes[i].nextNodes[i] = newNode;
		}
		//更新最大的索引层数
		if (levelCount < l) levelCount = l;
	}

	/**
	 * skipList查找结点,从最高层索引往下进行对比
	 * 在当前层找到 > 待查找结点value时 结点为node[i],则以node[i-1]该节点的下一层继续查找
	 * 从上往下时,每个结点的下一层必定有其down结点
	 *
	 * @param value
	 * @return
	 */
	public Node find(int value) {
		Node p = node;
		for (int i = levelCount - 1; i >= 0; --i) {
			//每一层找到最后一个小于value的结点,然后下沉一级,继续以上操作
			//当下沉到原始链表时,即同样找到最后一个小于value的结点
			while (p.nextNodes[i] != null && p.nextNodes[i].value < value) {
				p = p.nextNodes[i];
			}
		}
		//判断原始链表中找到的最后一个小于value的节点,是否为要找的结点
		if (p.nextNodes[0] != null && p.nextNodes[0].value == value)
			return p.nextNodes[0];
		else return null;
	}

	/**
	 * 查找范围 同find思路,找到起点和终点在原始链表中的位置,然后遍历中间的结点
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Node> findRange(int start, int end) {
		List<Node> list = new ArrayList<>();
		Node s = getOriginPreNode(start);
		Node e = getOriginPreNode(end);
		if (s != null && e != null) {
			while (s.nextNodes[0] != null && !s.equals(e)) {
				list.add(s.nextNodes[0]);
				s = s.nextNodes[0];
			}
		}
		return list;
	}

	/**
	 * 获取skipList原始链表中 最后小于value的结点
	 *
	 * @param value
	 * @return
	 */
	private Node getOriginPreNode(int value) {
		Node p = node;
		for (int i = levelCount - 1; i >= 0; --i) {
			//每一层找到最后一个小于value的结点,然后下沉一级,继续以上操作
			//当下沉到原始链表时,即同样找到最后一个小于value的结点
			while (p.nextNodes[i] != null && p.nextNodes[i].value < value) {
				p = p.nextNodes[i];
			}
		}
		return p;
	}

	/**
	 * 删除
	 * 和添加同理,需要找到每一层索引需要删除结点的前置结点
	 * 确保最下层原始链表有这个值,然后统一进行删除
	 *
	 * @param value
	 */
	public void delete(int value) {
		Node[] preNodes = new Node[levelCount];
		Node p = new Node();
		for (int i = levelCount - 1; i >= 0; --i) {
			while (p.nextNodes[i] != null && p.nextNodes[i].value < value) {
				p = p.nextNodes[i];
			}
			//获取每一层级 最后一个小于value的结点,作为前置结点
			preNodes[i] = p;
		}
		//首先确保原始链表中存在需要删除的结点
		if (preNodes[0] != null && preNodes[0].value == value) {
			//从最上层开始删除 需要删除的结点
			for (int i = levelCount - 1; i >= 0; --i) {
				Node next;
				if ((next = preNodes[i].nextNodes[i]) != null
						&& next.value == value) {
					next = next.nextNodes[i];
				}
			}
		}
		//删除个层级索引的结点,层级可能变小
		while (levelCount > 1 && node.nextNodes[levelCount] == null)
			levelCount--;
	}

	/**
	 * skipList获取随机函数
	 * 在不停新增数据的时候,我们为了避免某2个索引节点之间数据非常多,极端情况下退化成链表.
	 * 需要同时新增索引,我们在哪些层级中新增索引,通过随机函数来决定.
	 * 如果随机函数得到K=2,则在第[1-2]级中新增该索引,索引级数从下往上递增,原链表上第一层索引为第一级
	 * 理论上来说,我们要实现一个排列:
	 * 一级索引结点个数应战原链表结点个数的 50%
	 * 二级索引结点个数应战原链表结点个数的 25%
	 * 三级索引结点个数应战原链表结点个数的 12.5%
	 * ...
	 * 该随机函数需要达到目的:
	 * 50%的概率返回 1
	 * 25%的概率返回 2
	 * 12.5%的概率返回 3
	 * ...
	 *
	 * @return
	 */
	private int randomLevel() {
		int level = 1;

		//Math.random()随机生成0-1之间的double
		//Math.random() < PROBABILITY 概率为50%
		while (Math.random() < PROBABILITY && level < MAX_LEVEL) {
			level++;
		}
		return level;
	}

	/**
	 * 跳表结点
	 */
	static class Node {
		/**
		 * 结点数据 -> 评分
		 */
		private int value = -1;
		/**
		 * id
		 */
		private String id;
		/**
		 * 当前结点在每个层级中指向的下一个结点 [集合]
		 */
		private Node[] nextNodes = new Node[MAX_LEVEL];
		/**
		 * 当前结点最大层级
		 */
		private int maxLevel = 0;

		Node(int value, String id, int maxLevel) {
			this.value = value;
			this.id = id;
			this.maxLevel = maxLevel;
		}

		Node() {
		}
	}

	public static void main(String[] args) {
		SimpleSkipList skipList = new SimpleSkipList();
		skipList.insert(90, "2022072801");
		skipList.insert(20, "2022072802");
		skipList.insert(16, "2022072803");
		skipList.insert(3, "2022072804");
		skipList.insert(7, "2022072805");
		skipList.insert(19, "2022072806");
		skipList.insert(18, "2022072807");
		skipList.insert(26, "2022072808");
		skipList.insert(63, "2022072809");
		skipList.insert(72, "2022072810");

		Node node = skipList.find(26);

		List<Node> range = skipList.findRange(16, 63);
		System.out.println("");
	}
}
