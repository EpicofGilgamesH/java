package test.heap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

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

	/**
	 * 373. 查找和最小的 K 对数字
	 * 给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
	 * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
	 * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
	 * 示例 1:
	 * 输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
	 * 输出: [1,2],[1,4],[1,6]
	 * 解释: 返回序列中的前 3 对数：
	 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
	 * 示例 2:
	 * 输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
	 * 输出: [1,1],[1,1]
	 * 解释: 返回序列中的前 2 对数：
	 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
	 * 提示:
	 * 1 <= nums1.length, nums2.length <= 105
	 * -109 <= nums1[i], nums2[i] <= 109
	 * nums1 和 nums2 均为 升序排列
	 * 1 <= k <= 104
	 * k <= nums1.length * nums2.length
	 */
	public static class KSmallestPairs {

		/**
		 * 首先想到先对两个数组进行排序, a[]长度为m; b[]长度为n;
		 * 需要对所有的元素进行排序
		 * TreeSet不用于该场景,他是基于Comparator的实现条件进行去重处理的,所以还是有优先级队列
		 * <p>
		 * 但是出现一个问题,所有的元素都会放入优先级队列,超出内存限制
		 *
		 * @param nums1
		 * @param nums2
		 * @param k
		 * @return
		 */
		public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
			List<List<Integer>> list = new ArrayList<>();
			int m = nums1.length, n = nums2.length;
			PriorityQueue<List<Integer>> priorityQueue = new PriorityQueue<>(k, (Comparator.comparingInt(o -> o.get(0) + o.get(1))));
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					priorityQueue.offer(Arrays.asList(nums1[i], nums2[j]));
				}
			}
			for (int i = 0; i < k; i++) {
				list.add(priorityQueue.poll());
			}
			return list;
		}

		/**
		 * 显然本题要充分利用两个数组本来就有序递增的特性
		 * 那么(a[0],b[0]) 肯定是最小的
		 * 要寻找第二小的,只会出现在(a[1],b[0])和(a[0],b[1])中,如果(a[0],b[1])更小;
		 * 那么第三小的,会出现在a[1],b[0])、a[1],b[1])、a[0],b[2])中
		 * 可以借助优先级队列来实现,小顶堆每次将推顶元素出队,然后放入下一次要找的可选元素
		 * 当出队元素达到k时,即可停止;但放入的可选元素可能会重复,所以需要记录是否重复
		 *
		 * @param nums1
		 * @param nums2
		 * @param k
		 * @return
		 */
		public static List<List<Integer>> kSmallestPairsI(int[] nums1, int[] nums2, int k) {
			List<List<Integer>> list = new ArrayList<>();
			Set<String> set = new HashSet<>();
			// 优先级队列中存放的是两个数组的坐标 int[]数组中,存放nums1的idx和nums2的idx
			PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(k, (o1, o2) -> nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]]);
			priorityQueue.add(new int[]{0, 0});
			while (list.size() < k) {
				int[] p = priorityQueue.poll();
				int i = p[0], j = p[1];  // i,j为数组的idx
				list.add(Arrays.asList(nums1[i], nums2[j]));
				if (i + 1 < nums1.length) {
					String k2 = (i + 1) + "-" + j;
					if (!set.contains(k2)) {
						priorityQueue.add(new int[]{i + 1, j});
						set.add(k2);
					}
				}
				if (j + 1 < nums2.length) {
					String k1 = i + "-" + (j + 1);
					if (!set.contains(k1)) {
						priorityQueue.add(new int[]{i, j + 1});
						set.add(k1);
					}
				}
			}
			return list;
		}

		/**
		 * 如何避免重复加入
		 * [0,0]-> [0,1]-> [0,2]-> [0,3]-> [0,4]
		 * * ↓       ↓       ↓       ↓       ↓
		 * [1,0]-> [1,1]-> [1,2]-> [1,3]-> [1,4]
		 * * ↓       ↓       ↓       ↓       ↓
		 * [2,0]-> [2,1]-> [2,2]-> [2,3]-> [2,4]
		 * * ↓       ↓       ↓       ↓       ↓
		 * [3,0]-> [3,1]-> [3,2]-> [3,3]-> [3,4]
		 * <p>
		 * 首次将[0,0]放入优先级队列中,它一定是最小的;[0,0]之后最小的只能从[0,1]和[1,0]中得到
		 * 即[a,b]元素后一个元素查找时,需要把[a+1,b]和[a,b+1]放入堆中
		 * 但是在放入堆中,会出现重复放的情况,如何解决这个问题呢?
		 * 其实不一定每次都要将[a+1,b]和[a,b+1]都放入堆中,每行的第一个元素是该行最小的;如果每行的元素没有往后移动一位,
		 * 那么说明该元素还较大,下一次放入元素到堆中时,不需要放在它后面的元素,因为他在这一行中才是最小的
		 * <p>
		 * 所以可以现将每行的首个元素先放入堆中,然后每次只向右移动来将需要的元素放入堆中;
		 * [0,0]-> [0,1]-> [0,2]-> [0,3]-> [0,4]
		 * * ↓
		 * [1,0]-> [1,1]-> [1,2]-> [1,3]-> [1,4]
		 * * ↓
		 * [2,0]-> [2,1]-> [2,2]-> [2,3]-> [2,4]
		 * * ↓
		 * [3,0]-> [3,1]-> [3,2]-> [3,3]-> [3,4]
		 *
		 * @param nums1
		 * @param nums2
		 * @param k
		 * @return
		 */
		public static List<List<Integer>> kSmallestPairsII(int[] nums1, int[] nums2, int k) {
			List<List<Integer>> list = new ArrayList<>();
			// 优先级队列中存放的是两个数组的坐标 int[]数组中,存放nums1的idx和nums2的idx
			PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(k, (o1, o2) -> nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]]);
			for (int i = 0; i < Math.min(nums1.length, k); i++) {  // 最坏的情况是会取到第k行,即每行第一个元素都是最小的
				priorityQueue.add(new int[]{i, 0});
			}
			while (list.size() < k) {
				int[] poll = priorityQueue.poll();
				list.add(Arrays.asList(nums1[poll[0]], nums2[poll[1]]));
				if (poll[1] + 1 < nums2.length) {
					priorityQueue.add(new int[]{poll[0], poll[1] + 1});
				}
			}
			return list;
		}

		public static void main(String[] args) {
			int[] nums1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, nums2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			List<List<Integer>> lists = kSmallestPairsI(nums1, nums2, 30);
			System.out.println(lists);
		}
	}

	/**
	 * 502. IPO
	 * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
	 * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
	 * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
	 * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
	 * 答案保证在 32 位有符号整数范围内。
	 * 示例 1：
	 * 输入：k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
	 * 输出：4
	 * 解释：
	 * 由于你的初始资本为 0，你仅可以从 0 号项目开始。
	 * 在完成后，你将获得 1 的利润，你的总资本将变为 1。
	 * 此时你可以选择开始 1 号或 2 号项目。
	 * 由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
	 * 因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。
	 * 示例 2：
	 * 输入：k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
	 * 输出：6
	 * 提示：
	 * 1 <= k <= 105
	 * 0 <= w <= 109
	 * n == profits.length
	 * n == capital.length
	 * 1 <= n <= 105
	 * 0 <= profits[i] <= 104
	 * 0 <= capital[i] <= 109
	 */
	public static class FindMaximizedCapital {

		/**
		 * 因为投资的次数有限制,所以需要贪心思路,每次投资都需要在可投范围内成本最大化
		 * 将成本capital进行升序排序,由于已知资本是不断递增的,所以每次投资时,找到可投范围;这样可投范围内的投资不会重复
		 * 这样每次投资将可投范围内的利润放入优先级队列,找到最大化投资利润,此时将利润加入资本中;资本会上升,可投范围继续加大;
		 * 这是一个非常闭环的思路,每次都会在投资范围内找最大利润项目,当投资k次后,停止投资即可.
		 *
		 * @param k
		 * @param w
		 * @param profits
		 * @param capital
		 * @return
		 */
		public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
			int[][] arr = new int[profits.length][2];
			for (int i = 0; i < arr.length; i++) {
				arr[i][0] = profits[i];
				arr[i][1] = capital[i];
			}
			Arrays.sort(arr, Comparator.comparingInt(o -> o[1]));
			PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(((o1, o2) -> arr[o2][0] - arr[o1][0]));
			int i = 0;
			while (k > 0) {
				while (i < arr.length && w >= arr[i][1]) {
					priorityQueue.offer(i++);
				}
				if (priorityQueue.isEmpty()) {
					return w;
				}
				Integer poll = priorityQueue.poll();
				w += arr[poll][0];
				k--;
			}
			return w;
		}

		public static int findMaximizedCapitalI(int k, int w, int[] profits, int[] capital) {
			int n = profits.length;
			int cur = 0;
			int[][] arr = new int[n][2];
			for (int i = 0; i < n; ++i) {
				arr[i][0] = capital[i];
				arr[i][1] = profits[i];
			}
			Arrays.sort(arr, (a, b) -> a[0] - b[0]);
			PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> b - a); // 优先级队列中直接存放的利润值
			for (int i = 0; i < k; ++i) {
				while (cur < n && arr[cur][0] <= w) {
					priorityQueue.add(arr[cur][1]);
					cur++;
				}
				if (!priorityQueue.isEmpty()) {
					w += priorityQueue.poll();
				} else {
					break;
				}
			}
			return w;
		}

		static class V {
			private int pro;
			private int cap;

			public V(int pro, int cap) {
				this.pro = pro;
				this.cap = cap;
			}

			public int getPro() {
				return pro;
			}

			public void setPro(int pro) {
				this.pro = pro;
			}

			public int getCap() {
				return cap;
			}

			public void setCap(int cap) {
				this.cap = cap;
			}
		}

		/**
		 * 由成本值组成小顶堆,由利润值组成大堆顶
		 * 现将所有项目都加入小顶堆,然后根据当前资本可投项目,放入大顶堆中,每次出队大顶堆的堆顶,即当前可投的最大利润
		 *
		 * @param k
		 * @param w
		 * @param profits
		 * @param capital
		 * @retu
		 */
		public static int findMaximizedCapitalII(int k, int w, int[] profits, int[] capital) {
			Queue<V> minHeap = new PriorityQueue<>((a, b) -> a.cap - b.cap);
			Queue<V> maxHeap = new PriorityQueue<>((a, b) -> b.pro - a.pro);
			for (int i = 0; i < profits.length; ++i) {
				minHeap.offer(new V(profits[i], capital[i]));
			}
			while (k-- > 0) {
				while (!minHeap.isEmpty() && minHeap.peek().cap <= w) {
					maxHeap.offer(minHeap.poll());
				}
				if (maxHeap.isEmpty()) {
					break;
				}
				w += maxHeap.poll().pro;
			}
			return w;
		}

		public static void main(String[] args) {
			int maximizedCapital = findMaximizedCapitalII(1, 0, new int[]{1, 2, 3}, new int[]{0, 1, 2});
			System.out.println(maximizedCapital);
		}
	}
}
