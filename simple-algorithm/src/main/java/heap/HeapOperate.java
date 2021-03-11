package heap;

/**
 * @Description 数组存放堆结构
 * 堆的要求  1.完全二叉树 2.堆中每一个节点的值都必须大于等于（或小于等于）其子树中每个节点的值
 * @Date 2021-02-23 14:54
 * @Created by wangjie
 */
public class HeapOperate {

	private int[] a; //数组
	private int count; //堆可以存储的最大元素个数
	private int size; //堆中已存储的元素个数

	public HeapOperate(int capacity) {
		a = new int[capacity + 1]; //数组为index=0处不存放数据 满足 2^n+(1....2^n-1)
		count = capacity;
		size = 0;
	}

	//1.将新插入的数据放在数组中第一个未存放数据的位置
	//2.自下往上进行堆化
	public void insert(int data) {
		if (size == count) return;//堆满
		++size;
		a[size] = data;
		int i = size;
		//当前节点与其父节点进行比较,如果当前节点较大,则进行位置交换,直到达到顶节点
		while (i / 2 > 0 && a[i] > a[i / 2]) {
			//进行数据交换
			swap(a, i, i / 2);
			i = i / 2;
		}
	}

	//1.将根节点数据清空
	//2.将数组中最后一个存放数据的位置放到根节点中(交换)
	//3.自上往下进行堆化
	public void removeMax() {
		if (size == count) return;
		a[1] = a[size];
		--size;
		int i = 1;
		while (true) {
			int maxIndex = i;
			//判断是与左子节点交换 还是右子节点交换
			if (i * 2 <= count && a[i] < a[i * 2])
				maxIndex = i * 2;
			if (i * 2 + 1 < count && a[i] < a[i * 2 + 1])
				maxIndex = i * 2 + 1;
			if (maxIndex == i) break;
			swap(a, i, maxIndex);
			i = maxIndex;
		}
	}

	//建堆操作,设想有一个无序数组,将其初始化成堆
	//思路1:将建堆操作看成从一个空堆中插入数据的操作,直到数组的数据全部插入完成

	public void generate() {
		if (a == null && a.length == 0) return;
		for (int i = 0; i < a.length; ++i) {
			insert(a[i]);
		}
	}

	//思路2:上到下建堆操作->从最后一个非叶子节点依次从上往下进行堆化
	public void generateNew() {
		//最后一个非叶子节点
		//设 i为当前任意一个节点序列  n为该堆总数
		//当前节点的左子树为2*i
		// 2*i<=n  =>   i<=n/2 即1-n/2序列都为非子节点
		int n = a.length;
		if (a == null && n == 0) return;
		for (int i = n / 2; i > 0; --i) {
			//i节点从上至下进行堆化,判断左右子树大小
			while (true) {
				int maxIndex = i;
				if (i * 2 <= n && a[i] < a[i * 2])
					maxIndex = i * 2;
				if (i * 2 + 1 <= n && a[i] < a[i * 2 + 1])
					maxIndex = i * 2 + 1;
				if (maxIndex == i) break;
				swap(a, i, maxIndex);
				i = maxIndex;
			}
		}
	}

	//将a数组中,下标为i和j的数据进行交换操作
	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}


	public static void main(String[] args) {
		HeapOperate heapOperate = new HeapOperate(18);
		heapOperate.insert(23);
		heapOperate.insert(1);
		heapOperate.insert(7);
		heapOperate.insert(2);
		heapOperate.insert(4);
		heapOperate.insert(65);
		heapOperate.insert(15);
		heapOperate.insert(9);
		heapOperate.insert(28);
		heapOperate.insert(36);
		heapOperate.insert(12);
		heapOperate.insert(3);
		heapOperate.insert(5);
		heapOperate.insert(55);
		heapOperate.insert(20);

	}
}
