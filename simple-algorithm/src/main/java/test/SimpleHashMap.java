package test;

import java.util.Objects;

/**
 * @Description 实战测试题（一）
 * @Date 2022-07-27 11:57
 * @Created by wangjie
 */
public class SimpleHashMap<K, V> {

	// 假设猎聘网有 10 万名猎头顾问，每个猎头顾问都可以通过做任务（比如发布职位），
	// 来积累积分，然后通过积分来下载简历。假设你是猎聘网的一名工程师，
	// 如何在内存中存储这 10 万个猎头 ID 和积分信息，让它能够支持这样几个操作：
	//
	// 1.根据猎头的 ID 快速查找、删除、更新这个猎头的积分信息；
	// 2.查找积分在某个区间的猎头 ID 列表；
	// 3.查询积分从小到大排在第 x 位的猎头 ID信息；
	// 4.查找按照积分从小到大排名在第 x 位到第 y 位之间的猎头 ID 列表。

	//分析思路:
	// 1.首先通过ID就能快速找到简历信息,最好的方式是使用散列表 查找、删除、更新都是O(1)
	// 但是要考虑hash冲突的问题,可以参考HashMap的方式,将冲突的部分转换成链表,考虑整体数据量
	// 在10万,链表不需要转换成红黑树;本案例不考虑扩容***
	// 2.需要查找积分区间,参考数据库涉及,需要将积分做成索引结构,此处比较符合做成跳表结构,查询区间.
	// 时间复杂度为O(log n)
	// 3.需要查询排在第x 位的ID,类似求Top K的思路,使用堆来实现.但跳表也能实现该功能,在跳表的每个索引
	// 结点中加入一个span字段,记录这个索引结点到下一个索引节点包含链表结点的个数.这样不仅能找到top K,
	// 还能查询出排名在 x - y 之间的数据.

	static final int MAX = 1 << 30;

	/**
	 * 总容量大小
	 */
	private int size;

	/**
	 * 结点数组
	 */
	private Node<K, V>[] table;

	/**
	 * 数组大小 类似HashMap 2^n
	 */
	private int threshold;

	/**
	 * 散列表的结点类对象
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	static class Node<K, V> {
		/**
		 * 节点存放hash,用与查找时的比较
		 */
		final int hash;
		/**
		 * 节点存放key,get时,先通过hash找到位置,再对比key是否相同
		 */
		final K key;
		/**
		 * 存放在结点的数据
		 */
		V value;
		/**
		 * hash冲突时,存放在链表中 next指向下一个
		 */
		Node<K, V> next;

		Node(int hash, K key, V v, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = v;
			this.next = next;
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		/**
		 * Node获取hash
		 *
		 * @return
		 */
		public final int hash() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		/**
		 * 设置结点值,返回旧的Value值
		 *
		 * @param newV
		 * @return
		 */
		public final V setValue(V newV) {
			V oldV = value;
			value = newV;
			return oldV;
		}
	}

	public SimpleHashMap(int initCap) {
		if (initCap < 0) {
			throw new IllegalArgumentException("初始容量不能小于0.");
		}
		if (initCap > MAX)
			initCap = MAX;
		this.threshold = tableSizeFor(initCap);
	}

	/**
	 * 通过key计算hash
	 *
	 * @param key
	 * @return
	 */
	static final int hash(Object key) {
		int h;
		//key的hash异或其高16位,尽量散列
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	/**
	 * 返回整个map中key-value的个数
	 *
	 * @return
	 */
	public int size() {
		return this.size;
	}

	/**
	 * map数据插入
	 *
	 * @param key
	 * @param value
	 * @return 若为修改时返回旧值
	 */
	public V put(K key, V value) {
		SimpleHashMap.Node<K, V>[] tab;
		SimpleHashMap.Node<K, V> p;
		int n, i;
		int hash = hash(key);
		if ((tab = table) == null || (n = tab.length) == 0) {
			//map还未初始化
			n = ((tab = init()).length);
		}
		if ((p = tab[i = (n - 1) & hash]) == null) {
			//n为table长度 n-1为最左为1的位变为0,其他为全为1
			//n = 1000 0000 n-1 = 0111 1111
			//(n-1) & hash 即用取hash的n的长度的位数,进行与操作,得到的值,一定在n的范围内
			//为null则该槽位还没有存储结点对象
			tab[i] = new Node<>(hash, key, value, null);
			size++;
		} else {
			//槽位有值,则要判断是新增还是修改
			K k;
			V oldValue;
			if (p.hash == hash &&
					((k = p.key) == key || (key != null && key.equals(k)))) {
				//数组上的修改
				oldValue = p.value;
				p.value = value;
				return oldValue;
			} else {
				//链表,不考虑转换成红黑树
				Node<K, V> tail = p.next;
				Node<K, V> pre = p;
				while (tail != null) {
					if (tail.hash == hash &&
							((k = tail.key) == key || (key != null && key.equals(k)))) {
						//链表上已存在,修改
						oldValue = tail.value;
						tail.value = value;
						return oldValue;
					}
					pre = tail;
					tail = tail.next;
				}
				//链表上不存在,新增
				pre.next = new Node<>(hash, key, value, null);
				size++;
			}
		}
		return null;
	}

	/**
	 * map 查找key
	 *
	 * @param key
	 * @return
	 */
	public V get(K key) {
		int hash = hash(key);
		Node<K, V>[] tab;
		Node<K, V> first, e;
		int n;
		K k;
		if ((tab = table) != null && (n = table.length) > 0
				&& (first = tab[(n - 1) & hash]) != null) {
			//数组上找到值,判断在数组上,还是在链表上
			if (first.hash == hash &&
					((k = first.key) == key || (k != null && k.equals(key)))) {
				return first.value;
			}
			if ((e = first.next) != null) { //数组槽位下链表tail有值
				do {
					if (first.hash == hash &&
							((k = e.key) == key || (k != null && k.equals(key)))) {
						return e.value;
					}
				} while ((e = e.next) != null);
			}
		}
		return null;
	}


	/**
	 * 通过初始化Map给定的容量,计算Map内Node数组的初始化Size
	 * 找到 > cap 的最近的2^n
	 * 分析:
	 * int n = 00000000 00000001 10110010 10011101    十进制:111261
	 * 要得到比n大的最近的2^n 即 m = 00000000 00000010 00000000 00000000
	 * 此处通过位运算,先将n的最左为1的位及其后面的位 全部设置为1,然后+1 得到m
	 * <p>
	 * n |= n >>> 1; 将n无符号右移1位,高位补0
	 * 00000000 00000001 10110010 10011101
	 * 00000000 00000000 11011001 01001110
	 * ----------------------------------------  '|'运算
	 * 00000000 00000001 11111011 11011111 保证最左为1的位,紧接后面的位一定为1
	 * <p>
	 * n |= n >>> 2; 将n无符号右移2位
	 * 00000000 00000001 11111011 11011111
	 * 00000000 00000000 01111110 11110111
	 * ----------------------------------------  '|'运算
	 * 00000000 00000001 11111111 11111111 保证最左为1的位,紧接后面的4位一定为1,至此,已经达成目的
	 * <p>
	 * n |= n >>> 4; 将n无符号右移4位
	 * 00000000 00000001 11111111 11111111
	 * 00000000 00000000 00011111 11111111
	 * ----------------------------------------  '|'运算
	 * 00000000 00000001 11111111 11111111 保证最左为1的位,紧接8位一定为1
	 * <p>
	 * 同理 n |= n >>> 8; 将n无符号右移8位 保证最左为1的位,紧接16位为1;
	 * 同理 n |= n >>> 16; 将n无符号右移16位 保证最左为1的位,紧接32位为1;
	 * 覆盖32位int值的范围
	 *
	 * @param cap 初始map设置容量
	 * @return
	 */
	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAX) ? MAX : n + 1;
	}

	/**
	 * 初始化
	 */
	final SimpleHashMap.Node<K, V>[] init() {
		if (this.threshold == 0) {
			this.threshold = 16;
		}
		this.table = (SimpleHashMap.Node<K, V>[]) new Node[this.threshold];
		return this.table;
	}

	public static void main(String[] args) {

		SimpleHashMap<String, Integer> simpleHashMap = new SimpleHashMap<>(20);
		simpleHashMap.put("2022072801", 68);
		simpleHashMap.put("2022072802", 80);
		simpleHashMap.put("2022072803", 52);
		simpleHashMap.put("2022072804", 77);
		simpleHashMap.put("2022072805", 69);
		simpleHashMap.put("2022072806", 70);
		simpleHashMap.put("2022072807", 71);
		simpleHashMap.put("2022072808", 81);
		simpleHashMap.put("2022072809", 85);
		simpleHashMap.put("2022072810", 58);
		simpleHashMap.put("2022072811", 76);
		simpleHashMap.put("2022072812", 78);

		//制造hash碰撞
		simpleHashMap.put("20220728100", 90);
		simpleHashMap.put("202207281000", 90);

		//simpleHashMap.get(10);

		Integer integer = simpleHashMap.get("202207281000");
		System.out.println("");
	}
}
