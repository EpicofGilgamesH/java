package test.heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * heap堆/优先级队列的应用
 */
public class LeetcodeCase {

	/**
	 * 295. 数据流的中位数
	 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
	 * 例如 arr = [2,3,4] 的中位数是 3 。
	 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
	 * 实现 MedianFinder 类:
	 * MedianFinder() 初始化 MedianFinder 对象。
	 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
	 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
	 * 示例 1：
	 * 输入
	 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
	 * [[], [1], [2], [], [3], []]
	 * 输出
	 * [null, null, null, 1.5, null, 2.0]
	 * 解释
	 * MedianFinder medianFinder = new MedianFinder();
	 * medianFinder.addNum(1);    // arr = [1]
	 * medianFinder.addNum(2);    // arr = [1, 2]
	 * medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
	 * medianFinder.addNum(3);    // arr[1, 2, 3]
	 * medianFinder.findMedian(); // return 2.0
	 * 提示:
	 * -105 <= num <= 105
	 * 在调用 findMedian 之前，数据结构中至少有一个元素
	 * 最多 5 * 104 次调用 addNum 和 findMedian
	 */
	public static class MedianFinder {

		/**
		 * 通过数组实时去计算也可以实现,但时间复杂度为O(n)
		 * 此题肯定是需要设计一个O(1)时间复杂度的算法来实现《数据结构与算法之美》中有讲解过如果求大量数据的中位数
		 * <p>
		 * 大/小顶堆的实现方式;
		 * 如果数据量是奇数,则小顶堆的节点数量为:n/2+1 大顶堆的节点数量为:n/2
		 * 如果数据量是偶数,则大/小顶堆的节点数量均为:n/2
		 * 那如果控制两个堆的数量呢?
		 * 思路是首先确定大/小堆的堆顶,然后依次加入元素.小顶堆的所有元素都比大顶堆元素大;
		 * 放入所有元素后,要平衡两个堆的元素数量.
		 * 先使用jdk的优先级队列,后面手动实现堆数据结构
		 */
		private PriorityQueue<Integer> left;
		private PriorityQueue<Integer> right;

		public MedianFinder() {
			left = new PriorityQueue<>(10); // 小顶堆
			right = new PriorityQueue<>(10, (o1, o2) -> o2 - o1); // 大顶堆
		}

		public void addNum(int num) {
			if (left.isEmpty()) {
				left.add(num);
				return;
			}
			if (num > left.peek()) {
				left.add(num);
			} else {
				right.add(num);
			}
			// 平衡数量
			int size = left.size() + right.size();
			if (size % 2 == 0) {  // 偶数,都是n/2
				if (left.size() > size / 2) {  // 小顶堆数量多,移至大顶堆
					for (int i = 0; i < left.size() - size / 2; ++i) {
						right.add(left.poll());
					}
				} else {
					for (int i = 0; i < size / 2 - left.size(); ++i) {
						left.add(right.poll());
					}
				}
			} else {  // 奇数 小顶堆n/2+1 大顶堆 n/2
				if (left.size() > size / 2 + 1) {
					for (int i = 0; i < left.size() - (size / 2 + 1); ++i) {
						right.add(left.poll());
					}
				} else {
					for (int i = 0; i < (size / 2 + 1) - left.size(); ++i) {
						left.add(right.poll());
					}
				}
			}
		}

		public double findMedian() {
			int size = left.size() + right.size();
			if (size == 1) {
				return left.peek();
			}
			if (size % 2 == 0) {
				return (left.peek() + right.peek()) / (double) 2;
			} else {
				return (double) left.peek();
			}
		}
	}

	/**
	 * 手写优先级队列,基于数组
	 * 基于数组实现优先级队列(堆),必须先思考的几个问题如下:
	 * 1.堆化方式 2.大/小顶堆 3.堆顶元素弹出与重新堆化
	 * 堆化方式:
	 * 1.按照一个个元素添加进去的方式,那就是从下往上堆化,每个节点依次比较交换,直到根节点
	 * 2.从上至下进行堆化,从最后一个非叶子节点依次往下堆化，直到根节点
	 * 那为什么要选择从上至下的堆化方式呢?因为后续要处理移除堆顶元素的堆化问题,此时只需要最后一个元素放在堆顶,
	 * 然后堆化堆顶即可.
	 * 通过数组建堆: 堆的定义 1)堆是一颗完全二叉树,左子树一定是满的 2)堆中每个节点的元素都比其子节点大或者小
	 * *        8            0
	 * *      /  \         /  \
	 * *     6    7   ->  1    2
	 * *    / \  / \     / \  /
	 * *   3  2 5       3  4 5
	 * *
	 * 某个节点对应数组的下标为i,那么其左节点下标为:2*i+1 右节点下标为:2*i+2
	 * 大小顶堆:
	 * 根据节点大小的比较方式来确定
	 * 堆顶元素弹出与重新堆化:
	 * 堆顶元素弹出后,将最后一个叶子节点放在堆顶,堆顶节点从上至下进行堆化即可
	 * *********************************
	 * 不考虑数组扩容问题
	 * 拓展:后续考虑扩容、泛型数据类型、比较器等方面
	 */
	public static class BigTopHeap {

		private final int[] arr;
		private int size;

		public BigTopHeap() {
			arr = new int[100];
			size = 0;
		}

		public BigTopHeap(int[] array) {
			arr = Arrays.copyOf(array, 100);
			size = array.length;
			build();
		}

		/**
		 * add a element
		 * 加入到堆的最后,然后从下往上堆化
		 *
		 * @param num
		 */
		public void add(int num) {
			arr[size++] = num;
			int i = size - 1;
			while (true) {
				int j = i;
				if ((i - 1) / 2 >= 0 && num > arr[(i - 1) / 2]) {
					swap(arr, i, (i - 1) / 2);
					j = (i - 1) / 2;
				}
				if (j == i) break;
				i = j;
			}
		}

		/**
		 * get the first element
		 *
		 * @return
		 */
		public int peek() {
			return arr[0];
		}

		/**
		 * get and remove the first element
		 *
		 * @return
		 */
		public int poll() {
			int v = arr[0];
			arr[0] = arr[size - 1];
			arr[size - 1] = 0;
			size--;
			heapify(0);
			return v;
		}

		/**
		 * 堆化操作
		 */
		private void build() {
			// 通过节点个数,判断最后一个非叶子节点;总节点个数为i,最后一个非叶子节点为(i-1)/2
			// 此处堆顶下标从0开始
			int i = (size - 1) / 2;
			for (; i >= 0; --i) {
				heapify(i);
			}
		}

		/**
		 * 这里有个注意点,当前节点的左右子节点,谁更大它就与当前节点交换;
		 * 想清楚一些细节,不要想当然以为是这样*****
		 *
		 * @param i
		 */
		private void heapify(int i) {
			int j = i;
			while (true) {
				int p = j;
				if (2 * j + 1 < size && arr[2 * j + 1] > arr[j]) {
					p = 2 * j + 1;
				}
				if (2 * j + 2 < size && arr[2 * j + 2] > arr[p]) {
					p = 2 * j + 2;
				}
				if (j == p) break; // 左右子节点都没有或者未进行交换,即终止
				swap(arr, p, j);
				j = p;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

	}

	public static class SmallTopHeap {

		private final int[] arr;
		private int size;

		public SmallTopHeap() {
			arr = new int[100];
			size = 0;
		}

		public SmallTopHeap(int[] array) {
			arr = Arrays.copyOf(array, 100);
			size = array.length;
			build();
		}

		/**
		 * add a element
		 * 加入到堆的最后,然后从下往上堆化
		 *
		 * @param num
		 */
		public void add(int num) {
			arr[size++] = num;
			int i = size - 1;
			while (true) {
				int j = i;
				if ((i - 1) / 2 >= 0 && num < arr[(i - 1) / 2]) {
					swap(arr, i, (i - 1) / 2);
					j = (i - 1) / 2;
				}
				if (j == i) break;
				i = j;
			}
		}

		/**
		 * get the first element
		 *
		 * @return
		 */
		public int peek() {
			return arr[0];
		}

		/**
		 * get and remove the first element
		 *
		 * @return
		 */
		public int poll() {
			int v = arr[0];
			arr[0] = arr[size - 1];
			size--;
			heapify(0);
			return v;
		}

		/**
		 * 堆化操作
		 */
		private void build() {
			// 通过节点个数,判断最后一个非叶子节点;总节点个数为i,最后一个非叶子节点为(i-1)/2
			// 此处堆顶下标从0开始
			int i = (size - 1) / 2;
			for (; i >= 0; --i) {
				heapify(i);
			}
		}

		/**
		 * 这里有个注意点,当前节点的左右子节点,谁更大它就与当前节点交换;
		 * 想清楚一些细节,不要想当然以为是这样*****
		 *
		 * @param i
		 */
		private void heapify(int i) {
			int j = i;
			while (true) {
				int p = j;
				if (2 * j + 1 < size && arr[2 * j + 1] < arr[j]) {
					p = 2 * j + 1;
				}
				if (2 * j + 2 < size && arr[2 * j + 2] < arr[p]) {
					p = 2 * j + 2;
				}
				if (j == p) break; // 左右子节点都没有或者未进行交换,即终止
				swap(arr, p, j);
				j = p;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

	}

	public static class MedianFinderI {

		/**
		 * 通过数组实时去计算也可以实现,但时间复杂度为O(n)
		 * 此题肯定是需要设计一个O(1)时间复杂度的算法来实现《数据结构与算法之美》中有讲解过如果求大量数据的中位数
		 * <p>
		 * 大/小顶堆的实现方式;
		 * 如果数据量是奇数,则小顶堆的节点数量为:n/2+1 大顶堆的节点数量为:n/2
		 * 如果数据量是偶数,则大/小顶堆的节点数量均为:n/2
		 * 那如果控制两个堆的数量呢?
		 * 思路是首先确定大/小堆的堆顶,然后依次加入元素.小顶堆的所有元素都比大顶堆元素大;
		 * 放入所有元素后,要平衡两个堆的元素数量.
		 * 先使用jdk的优先级队列,后面手动实现堆数据结构
		 */
		private SmallTopHeap left;
		private BigTopHeap right;

		public MedianFinderI() {
			left = new SmallTopHeap(); // 小顶堆
			right = new BigTopHeap(); // 大顶堆
		}

		public void addNum(int num) {
			if (left.size == 0) {
				left.add(num);
				return;
			}
			if (num > left.peek()) {
				left.add(num);
			} else {
				right.add(num);
			}
			// 平衡数量
			int size = left.size + right.size;
			if (size % 2 == 0) {  // 偶数,都是n/2
				if (left.size > size / 2) {  // 小顶堆数量多,移至大顶堆
					for (int i = 0; i < left.size - size / 2; ++i) {
						right.add(left.poll());
					}
				} else {
					for (int i = 0; i < size / 2 - left.size; ++i) {
						left.add(right.poll());
					}
				}
			} else {  // 奇数 小顶堆n/2+1 大顶堆 n/2
				if (left.size > size / 2 + 1) {
					for (int i = 0; i < left.size - (size / 2 + 1); ++i) {
						right.add(left.poll());
					}
				} else {
					for (int i = 0; i < (size / 2 + 1) - left.size; ++i) {
						left.add(right.poll());
					}
				}
			}
		}

		public double findMedian() {
			int size = left.size + right.size;
			if (size == 1) {
				return left.peek();
			}
			if (size % 2 == 0) {
				return (left.peek() + right.peek()) / (double) 2;
			} else {
				return (double) left.peek();
			}
		}

		public static void main(String[] args) {
			MedianFinderI mf = new MedianFinderI();
			mf.addNum(8);
			mf.addNum(7);
			mf.addNum(6);
			mf.addNum(5);
			mf.addNum(4);
			System.out.println(mf.findMedian());
		}
	}

	public static void main(String[] args) {
		MedianFinder medianFinder = new MedianFinder();
		medianFinder.addNum(8);
		medianFinder.addNum(7);
		medianFinder.addNum(6);
		medianFinder.addNum(5);
		medianFinder.addNum(4);
		System.out.println(medianFinder.findMedian());

		int[] arr = new int[]{3, 6, 2, 7, 5, 10};
		BigTopHeap bigTopHeap = new BigTopHeap(arr);
		System.out.println(Arrays.toString(bigTopHeap.arr));
		bigTopHeap.add(8);
		bigTopHeap.add(12);
		bigTopHeap.add(1);
		System.out.println(Arrays.toString(bigTopHeap.arr));
		System.out.println(bigTopHeap.peek());
		bigTopHeap.poll();
		System.out.println();

		SmallTopHeap smallTopHeap = new SmallTopHeap(arr);
		System.out.println(Arrays.toString(smallTopHeap.arr));
		smallTopHeap.add(8);
		smallTopHeap.add(12);
		smallTopHeap.add(1);
		System.out.println(Arrays.toString(smallTopHeap.arr));
		System.out.println(smallTopHeap.peek());
		smallTopHeap.poll();
		System.out.println();
	}
}
