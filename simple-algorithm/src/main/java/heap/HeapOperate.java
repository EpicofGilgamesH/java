package heap;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 数组存放堆结构
 * 堆的要求  1.完全二叉树 2.堆中每一个节点的值都必须大于等于（或小于等于）其子树中每个节点的值
 * @Date 2021-02-23 14:54
 * @Created by wangjie
 */
public class HeapOperate {

	private static int[] a; //数组
	private static int count; //堆可以存储的最大元素个数
	private static int size; //堆中已存储的元素个数

	public HeapOperate(int capacity) {
		a = new int[capacity];
		count = capacity;
		size = 0;
	}

	//1.将新插入的数据放在数组中第一个未存放数据的位置
	//2.自下往上进行堆化
	public static void insert(int data) {
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
	public static void removeMax() {
		if (size == count) return;
		a[0] = a[size];
		--size;
		int i = 0;
		/*while (true) {
			int maxIndex = i;
			//判断是与左子节点交换 还是右子节点交换
			if (i * 2 <= count && a[i] < a[i * 2])
				maxIndex = i * 2;
			if (i * 2 + 1 < count && a[i] < a[i * 2 + 1])
				maxIndex = i * 2 + 1;
			if (maxIndex == i) break;
			swap(a, i, maxIndex);
			i = maxIndex;
		}*/
		heapify(a, size - 1, i);
	}

	//建堆操作,设想有一个无序数组,将其初始化成堆
	//思路1:将建堆操作看成从一个空堆中插入数据的操作,直到数组的数据全部插入完成

	public static void build(int[] arr) {
		if (arr == null && arr.length == 0) return;
		for (int i = 0; i < arr.length; ++i) {
			insert(arr[i]);
		}
	}

	//思路2:上到下建堆操作->从最后一个非叶子节点依次从上往下进行堆化
	public static void buildNew(int[] arr) {
		//最后一个非叶子节点
		//设 i为当前任意一个节点序列  n为该堆总数
		//当前节点的左子树为2*i
		// 2*i<=n  =>   i<=n/2 即1-n/2序列都为非子节点
		int n = arr.length;
		if (arr == null || n == 0) return;
		for (int i = (n - 1) / 2; i >= 0; i--) {
			//i节点从上至下进行堆化,判断左右子树大小
			heapify(arr, n - 1, i);
		}
	}

	/**
	 * 堆化排序思路
	 * 1.建堆
	 * 2.每次取出堆顶的元素,然后将下标为n的元素移动到堆顶
	 * 3.进行堆化操作
	 * 4.移动的元素从n开始,直到移动的元素下标为1,结束
	 *
	 * @param arr
	 */
	public static void heapSort(int[] arr) {
		if (arr.length <= 1)
			return;
		//建堆
		buildNew(arr);
		//约定数组length为堆size
		int k = arr.length - 1;
		while (k > 0) {
			//交换堆顶和堆尾
			swap(arr, 0, k);
			//从堆顶元素向下进行堆化
			heapify(arr, --k, 0);
		}
	}

	//将a数组中,下标为i和j的数据进行交换操作
	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	/**
	 * 将arr数组进行堆化,index从1-i
	 *
	 * @param arr 要堆化的数组
	 * @param n   最后堆化的元素下标
	 * @param k   要堆化的元素下标
	 */
	private static void heapify(int[] arr, int n, int i) {
		//i节点从上至下进行堆化,判断左右子树大小
		while (true) {
			// 最大值位置
			int maxPos = i;
			// 与左子节点（i * 2 + 1）比较，获取最大值位置
			if (i * 2 + 1 <= n && arr[i] < arr[i * 2 + 1]) {
				maxPos = i * 2 + 1;
			}
			// 最大值与右子节点（i * 2 + 2）比较，获取最大值位置
			if (i * 2 + 2 <= n && arr[maxPos] < arr[i * 2 + 2]) {
				maxPos = i * 2 + 2;
			}
			// 最大值是当前位置结束循环
			if (maxPos == i) {
				break;
			}
			// 与子节点交换位置
			swap(arr, i, maxPos);
			// 以交换后子节点位置接着往下查找
			i = maxPos;
		}
	}

	//---------------------------------------------------------------------------------------------------
	//堆的应用一 : 优先级队列
	//1.合并有序小文件
	//假设有10个有序数据,先需要将这10个有序数组合并成一个有序数据的方案.
	//1.分别取每个有序数据的最大值放入新的临时数组中,进行堆化操作
	//2.取出临时数据中的最大值(堆顶元素),然后放入结论数组中,再循环在10个有序数组中补充一个元素
	//3.循环以上操作,直到这10个有序数组的元素都清空
	public static int[] mergArray() {
		//目标数组
		int[] targeArr = new int[100];
		//目标数组插入下标记录
		int k = -1;
		//初始化数组
		List<Integer[]> list = initArray();
		//将每个数组中的最大元素,放入排序数组中
		int[] sortArr = new int[10];
		for (int i = 0; i < 10; i++) {
			sortArr[i] = list.get(i)[0];
		}
		//建堆
		buildNew(sortArr);
		int arrFlag;
		int arrIndex;
		int i = 0;
		while (true) {
			if (i == 90) {
				break;
			}
			if (i != 0) {
				heapify(sortArr, 9, 0);
			}
			//取堆顶放入目标数组中
			targeArr[++k] = sortArr[0];
			//依次补充一个元素到堆顶,再次堆化
			arrFlag = i % 10; //取余数:i(0-9)-> 0-9 | i(10-19)-> 0-9
			arrIndex = i / 10;//取值:i(0-9)->0 | (10-19)->1 | (20-29)->2
			sortArr[0] = list.get(arrFlag)[arrIndex + 1];
			i++;
		}
		//有序数组元素取完后,将排序数组中的10个元素依次取堆顶放入目标数组中
		int n = 9;
		while (true) {
			if (n < 0) {
				break;
			}
			heapify(sortArr, n, 0);
			targeArr[++k] = sortArr[0];
			sortArr[0] = sortArr[n];
			n--;
		}
		return targeArr;
	}

	private static List<Integer[]> initArray() {
		List<Integer[]> list = new ArrayList<>(10);
		Random random = new Random();
		for (int k = 0; k < 10; k++) {
			Integer[] arr = new Integer[10];
			for (int i = 0; i < 10; i++) {
				arr[i] = random.nextInt(1000);
			}
			//排序降序
			Arrays.sort(arr, Collections.reverseOrder());
			list.add(arr);
		}
		return list;
	}

	private static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}


	//2.高性能定时器
	//现在有一些任务需要在指定的时间去执行,使用优先级队列,在堆顶设置最小值,每次只取堆顶,然后通过定时器去执行
	// A 2021-04-06 13:53  B 2021-04-06 15:20  C 2021-04-06 15:55 D 2021-04-06 17:30
	private static void highPerformanceTimer() {
		//初始化任务时间
		Task task1 = new Task("A", LocalDateTime.of(2021, 4, 21, 17, 53, 00));
		Task task2 = new Task("B", LocalDateTime.of(2021, 4, 21, 16, 20, 00));
		Task task3 = new Task("C", LocalDateTime.of(2021, 4, 21, 15, 55, 00));
		Task task4 = new Task("D", LocalDateTime.of(2021, 4, 21, 12, 30, 00));
		Task[] tasks = new Task[]{task1, task2, task3, task4};
		//进行堆化
		build(tasks);
		for (int i = 0; i < tasks.length; i++) {
			//取堆顶
			Task heapTop = getHeapTop(tasks, tasks.length - 1 - i);
			System.out.println(heapTop.toString());
		}
	}

	private static void build(Task[] tasks) {
		//从第一个非叶子节点进行堆化 (n-1)/2
		int n = tasks.length;
		if (tasks == null || n == 0) {
			return;
		}
		for (int i = (n - 1) / 2; i >= 0; i--) {
			//堆化操作
			heapify(tasks, n - 1, i);
		}
	}

	/**
	 * 取堆顶元素,然后保证剩下的元素进行堆化
	 *
	 * @param tasks 数组
	 * @param n     在堆中的元素的最大下标
	 * @return
	 */
	private static Task getHeapTop(Task[] tasks, int n) {
		Task task = tasks[0];
		//最后一个元素放置到堆顶,然后将堆顶元素进行堆化
		tasks[0] = tasks[n];
		heapify(tasks, n, 0);
		return task;
	}

	/**
	 * 从上到下进行堆化操作
	 *
	 * @param tasks
	 * @param n
	 * @param i
	 */
	private static void heapify(Task[] tasks, int n, int i) {
		int minIndex = i;
		while (true) {
			if (i * 2 + 1 <= n && tasks[i * 2 + 1].time.isBefore(tasks[i].time))
				minIndex = i * 2 + 1;
			if (i * 2 + 2 <= n && tasks[i * 2 + 2].time.isBefore(tasks[minIndex].time))
				minIndex = i * 2 + 2;
			if (minIndex == i) //寻找的下一个堆化点是自己则退出循环
				break;
			//交换
			Task temp;
			temp = tasks[i];
			tasks[i] = tasks[minIndex];
			tasks[minIndex] = temp;
			//赋值
			i = minIndex;
		}
	}

	static class Task {

		public Task(String name, LocalDateTime time) {
			this.name = name;
			this.time = time;
		}

		private String name;
		private LocalDateTime time;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public LocalDateTime getTime() {
			return time;
		}

		public void setTime(LocalDateTime time) {
			this.time = time;
		}

		@Override
		public String toString() {
			return "当前任务名:" + getName() + ",经" + Duration.between(LocalDateTime.now(), getTime()).toMinutes() + "min后执行";
		}
	}

	//堆的应用二 : 求TOP K
	//1.静态数组中求TOP K
	//在n个元素中查找前K大的元素,需要维护一个大小为K的小顶堆;遍历元素,如果大于堆顶
	//则删除堆顶,并将该元素插入堆中(替换堆顶元素,重新建堆)
	//遍历数组时间复杂度 O(n) 堆化操作时间复杂度是 O(logK) 最坏的情况是 (n+1)*logK 即nlogK
	public static int[] getTopK(int[] arr, int k) {
		int l = arr.length;
		if (l < k) return null;
		//创建一个长度为k的数组,放入k个元素,构建小顶堆
		int[] target = new int[k];
		for (int i = 0; i < k; i++) {
			target[i] = arr[i];
		}
		buildSmallTopHeap(target);
		for (int j = k + 1; j < l; j++) {
			if (arr[j] > target[0]) {
				//该元素大于堆顶元素,则删除原堆顶,将该元素入堆并重新堆化
				target[0] = arr[j];
				buildSmallTopHeap(target);
			}
		}
		//将结果集堆进行排序操作
		sortHeap(target);
		return target;
	}

	//2.动态数组中求TOP K
	//一直维护一个大小为K的小顶堆,当有元素插入时,将其与小顶堆堆顶(小顶堆最小的)进行比较,如果大于堆顶元素,
	//则删除堆顶并将该元素插入堆中,当要查询TOP K时直接将该堆返回即可

	static class TopK {

		private static List<Integer> list = new ArrayList<>();//原集合

		private static int[] target; //topK 堆数组

		private static int k = 0;

		private static boolean flag = false;

		public static void buildTopK(int n) {
			target = new int[n];
			k = n;
		}

		/**
		 * 当集合插入数据时,动态维护
		 *
		 * @param insertElement
		 * @return
		 */
		public static int[] getTopKForDynamicArray(int insertElement) {
			list.add(insertElement);
			if (list.size() < k) {
				return null;
			}
			//将数据放入小顶堆中
			if (!flag) {
				for (int i = 0; i < k; i++) {
					target[i] = list.get(i);
				}
				buildSmallTopHeap(target);
				flag = true;
				return target;
			}
			//新元素与堆顶进行比较
			if (target[0] < insertElement) {
				target[0] = insertElement;
				//堆化index=0的元素
				heapifyForSmallTopHeap(target, k - 1, 0);
			}
			heapSort(target);
			return target;
		}

	}


	/**
	 * 构建小顶堆
	 *
	 * @param arr
	 */
	public static void buildSmallTopHeap(int[] arr) {
		int n = arr.length;
		if (arr == null || n == 0) return;
		//从第一个非叶子节点进行堆化操作
		//设 i为当前任意一个节点序列  n为该堆总数
		//当前节点的左子树为2*i+1 右子树为2*1+2
		// 2*i+2<=n  =>   i<=(n-2)/2
		for (int i = (n - 2) / 2; i >= 0; i--) {
			heapifyForSmallTopHeap(arr, n - 1, i);
		}
	}

	/**
	 * 堆进行排序
	 * 将堆顶的元素与堆中最后元素进行交换,然后排出最后元素进行堆化,找出第二个最小的元素,重复该操作
	 * 堆的升序和降序 处理方式 大顶堆适合做升序,小顶堆适合做降序
	 *
	 * @param arr
	 */
	public static void sortHeap(int[] arr) {
		if (arr == null || arr.length <= 0) return;
		int k = arr.length - 1;
		while (k > 0) {
			//将堆顶元素放在最下的元素下标处
			swap(arr, 0, k);
			heapifyForSmallTopHeap(arr, --k, 0);
		}
	}


	/**
	 * 小顶堆 从上自下堆化操作
	 *
	 * @param arr
	 * @param n   最后堆化的元素下标
	 * @param i   需要堆化的元素下标
	 */
	public static void heapifyForSmallTopHeap(int[] arr, int n, int i) {
		//i节点从上至下进行堆化,判断左右子树大小
		while (true) {
			// 最大值位置
			int minPos = i;
			// 与左子节点（i * 2 + 1）比较，获取最大值位置
			if (i * 2 + 1 <= n && arr[i] > arr[i * 2 + 1]) {
				minPos = i * 2 + 1;
			}
			// 最大值与右子节点（i * 2 + 2）比较，获取最大值位置
			if (i * 2 + 2 <= n && arr[minPos] > arr[i * 2 + 2]) {
				minPos = i * 2 + 2;
			}
			// 最大值是当前位置结束循环
			if (minPos == i) {
				break;
			}
			// 与子节点交换位置
			swap(arr, i, minPos);
			// 以交换后子节点位置接着往下查找
			i = minPos;
		}
	}

	/**
	 * 小顶堆 从上自下堆化操作
	 *
	 * @param arr
	 * @param n   最后堆化的元素下标
	 * @param i   需要堆化的元素下标
	 */
	public static void heapifyListForSmallTopHeap(List<Integer> arr, int n, int i) {
		//i节点从上至下进行堆化,判断左右子树大小
		while (true) {
			// 最大值位置
			int minPos = i;
			// 与左子节点（i * 2 + 1）比较，获取最大值位置
			if (i * 2 + 1 <= n && arr.get(i) > arr.get(i * 2 + 1)) {
				minPos = i * 2 + 1;
			}
			// 最大值与右子节点（i * 2 + 2）比较，获取最大值位置
			if (i * 2 + 2 <= n && arr.get(minPos) > arr.get(i * 2 + 2)) {
				minPos = i * 2 + 2;
			}
			// 最大值是当前位置结束循环
			if (minPos == i) {
				break;
			}
			//交换位置
			swapList(arr, i, minPos);
			// 以交换后子节点位置接着往下查找
			i = minPos;
		}
	}

	//大顶堆,从上自下堆化操作
	public static void heapifyListForLargeTopHeap(List<Integer> arr, int n, int i) {
		int maxPos = i;
		while (true) {
			if (i * 2 + 1 <= n && arr.get(i * 2 + 1) > arr.get(i))
				maxPos = i * 2 + 1;
			if (i * 2 + 2 <= n && arr.get(i * 2 + 2) > arr.get(maxPos))
				maxPos = i * 2 + 2;
			if (maxPos == i) //寻找的下一个堆化点是自己则退出循环
				break;
			//交换位置
			swapList(arr, i, maxPos);
			//赋值
			i = maxPos;
		}
	}

	//大顶堆,从下自上进行堆化操作
	public static void heapifyListForLargeTopHeapDesc(List<Integer> arr, int i) {
		while (i / 2 >= 0 && arr.get(i) > arr.get((i - 1) / 2)) {
			swapList(arr, i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	//小顶堆,从下自上进行堆化操作
	public static void heapifyListForSmallTopHeapDesc(List<Integer> arr, int i) {
		while ((i - 1) / 2 >= 0 && arr.get(i) < arr.get((i - 1) / 2)) {
			swapList(arr, i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	//list元素进行交换
	public static void swapList(List<Integer> list, int i, int j) {
		int temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}


	//堆的应用三 : 求中位数
	//1.静态数组中求中位数,思路是直接进行排序,然后偶数取 2/n-1 ,奇数取 2/n
	//2.动态数据求中位数:创建一个大顶堆(左)、小顶堆(右) 最后取大顶堆的堆顶为中位数
	//当数组长度为偶数时,左堆:n/2 右堆:n/2 保证左堆堆顶元素在左边最大,但是比右堆堆顶元素小,则刚好为n/2大小的元素
	//当数组长度为奇数时,左堆:n/2+1 右堆:n/2
	//每新增一个元素时,如果该元素<=左堆堆顶,则放入左堆中;>左堆堆顶,则放入右堆中
	//当左右堆的数量不满足上面的要求时,将堆顶元素进行转移,以确保满足
	static class Median {
		//数组
		private static List<Integer> array;
		//数组长度
		private static int length = 0;
		//左大顶推
		private static List<Integer> leftHeapMax = new ArrayList<>(0);
		//右小顶推
		private static List<Integer> rightHeapMin = new ArrayList<>(0);

		//插入元素
		public static void insert(int element) {
			++length;
			//当左大顶堆没有数据时,说明整个数组为空,直接放入左大顶堆中
			if (leftHeapMax.size() == 0) {
				leftHeapMax.add(element);
			} else {
				int leftHeapTop = leftHeapMax.get(0);
				if (leftHeapTop >= element) {
					//堆顶放在n-1的位置,新元素放在堆顶,0位置从上自下进行堆化操作
					addAndHeapify(element, leftHeapMax, true);
				} else {
					addAndHeapify(element, rightHeapMin, false);
				}
			}
			//判断左右堆的大小是否符合规则
			while (true) {
				int lSize = leftHeapMax.size();
				//首先判断奇偶
				if (length % 2 == 0) {
					//偶数,左右各n/2
					if (lSize < length / 2) {
						//左堆 < n/2 则把右堆顶元素移动到左堆中
						int el = rightHeapMin.get(0);
						addAndHeapify(el, leftHeapMax, true);
						//右堆删除堆顶,并堆化
						removeTopAndHeapify(rightHeapMin, false);
					} else if (lSize > length / 2) {
						//左堆 > n/2 则把左堆堆顶元素移动到右堆中
						int el = leftHeapMax.get(0);
						addAndHeapify(el, rightHeapMin, false);
						//左堆删除堆顶,并堆化
						removeTopAndHeapify(leftHeapMax, true);
					} else {
						//符合要求,退出
						return;
					}
				} else {
					//奇数,左堆:n/2+1 右堆:n/2
					if (lSize < length / 2 + 1) {
						//左堆 < n/2 则把右堆顶元素移动到左堆中
						int el = rightHeapMin.get(0);
						addAndHeapify(el, leftHeapMax, true);
						//右堆删除堆顶,并堆化
						removeTopAndHeapify(rightHeapMin, false);
					} else if (lSize > length / 2 + 1) {
						//左堆 > n/2 则把左堆堆顶元素移动到右堆中
						int el = leftHeapMax.get(0);
						addAndHeapify(el, rightHeapMin, false);
						//左堆删除堆顶,并堆化
						removeTopAndHeapify(leftHeapMax, true);
					} else {
						//符合要求,退出
						return;
					}
				}
			}
		}

		//求中位数
		public static int percentage() {
			return leftHeapMax.get(0);
		}

		//添加元素并堆化
		private static void addAndHeapify(int element, List<Integer> list, boolean isMaxHeapify) {
			list.add(element);
			//swapList(list, 0, list.size() - 1);
			//堆化
			if (isMaxHeapify)
				heapifyListForLargeTopHeapDesc(list, list.size() - 1);
			else
				heapifyListForSmallTopHeapDesc(list, list.size() - 1);
		}

		//删除堆顶元素并堆化
		private static void removeTopAndHeapify(List<Integer> list, boolean isMaxHeapify) {
			int size = list.size();
			int max = list.get(size - 1);
			list.set(0, max);
			list.remove(size - 1);
			if (isMaxHeapify) {
				heapifyListForLargeTopHeap(list, list.size() - 1, 0);
			} else {
				heapifyListForSmallTopHeap(list, list.size() - 1, 0);
			}
		}
	}

	//如何快速求接口的 99% 响应时间
	static class percentage {
		//数组长度
		private static int length = 0;
		//左大顶推
		private static List<Integer> leftHeapMax = new ArrayList<>(0);
		//右小顶推
		private static List<Integer> rightHeapMin = new ArrayList<>(0);

		private static double percentageValue = 0.99D;

		//插入元素
		public static void insert(int element) {
			++length;
			//当左大顶堆没有数据时,说明整个数组为空,直接放入左大顶堆中
			if (leftHeapMax.size() == 0) {
				leftHeapMax.add(element);
			} else {
				int leftHeapTop = leftHeapMax.get(0);
				if (leftHeapTop >= element) {
					//堆顶放在n-1的位置,新元素放在堆顶,0位置从上自下进行堆化操作
					addAndHeapify(element, leftHeapMax, true);
				} else {
					addAndHeapify(element, rightHeapMin, false);
				}
			}
			//判断左右堆的大小是否符合规则
			while (true) {
				int lSize = leftHeapMax.size();
				//偶数,左右各n/2
				if (lSize < (int) (length * percentageValue)) {
					//左堆 < n/2 则把右堆顶元素移动到左堆中
					int el = rightHeapMin.get(0);
					addAndHeapify(el, leftHeapMax, true);
					//右堆删除堆顶,并堆化
					removeTopAndHeapify(rightHeapMin, false);
				} else if (lSize > (int) (length * percentageValue)) {
					//左堆 > n/2 则把左堆堆顶元素移动到右堆中
					int el = leftHeapMax.get(0);
					addAndHeapify(el, rightHeapMin, false);
					//左堆删除堆顶,并堆化
					removeTopAndHeapify(leftHeapMax, true);
				} else {
					//符合要求,退出
					return;
				}
			}
		}

		//求百分位数
		public static int percentage() {
			if (leftHeapMax.isEmpty()) {
				return -1;
			}
			return leftHeapMax.get(0);
		}

		//添加元素并堆化
		private static void addAndHeapify(int element, List<Integer> list, boolean isMaxHeapify) {
			list.add(element);
			//swapList(list, 0, list.size() - 1);
			//堆化
			if (isMaxHeapify)
				heapifyListForLargeTopHeapDesc(list, list.size() - 1);
			else
				heapifyListForSmallTopHeapDesc(list, list.size() - 1);
		}

		//删除堆顶元素并堆化
		private static void removeTopAndHeapify(List<Integer> list, boolean isMaxHeapify) {
			int size = list.size();
			int max = list.get(size - 1);
			list.set(0, max);
			list.remove(size - 1);
			if (isMaxHeapify) {
				heapifyListForLargeTopHeap(list, list.size() - 1, 0);
			} else {
				heapifyListForSmallTopHeap(list, list.size() - 1, 0);
			}
		}
	}

	public static void main(String[] args) {
		//highPerformanceTimer();
		/*int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 152, 13, 14, 15, 16};
		int[] topK = getTopK(arr, 4);
		System.out.println(JSON.toJSONString(topK));*/

		/*TopK.buildTopK(4);
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(1)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(2)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(3)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(4)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(5)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(6)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(7)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(8)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(9)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(10)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(11)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(12)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(152)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(13)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(14)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(15)));
		System.out.println(JSON.toJSONString(TopK.getTopKForDynamicArray(16)));*/

		percentage.insert(1);
		System.out.println(percentage.percentage());
		percentage.insert(2);
		System.out.println(percentage.percentage());
		percentage.insert(3);
		System.out.println(percentage.percentage());
		percentage.insert(4);
		System.out.println(percentage.percentage());
		percentage.insert(5);
		System.out.println(percentage.percentage());
		percentage.insert(6);
		System.out.println(percentage.percentage());
		percentage.insert(7);
		System.out.println(percentage.percentage());
		percentage.insert(8);
		System.out.println(percentage.percentage());
		percentage.insert(9);
		System.out.println(percentage.percentage());
		percentage.insert(10);
		System.out.println(percentage.percentage());
		percentage.insert(11);
		System.out.println(percentage.percentage());
		percentage.insert(12);
		System.out.println(percentage.percentage());
		percentage.insert(13);
		System.out.println(percentage.percentage());
		percentage.insert(14);
		System.out.println(percentage.percentage());
		percentage.insert(15);
		System.out.println(percentage.percentage());
		percentage.insert(16);
		System.out.println(percentage.percentage());
		percentage.insert(17);
		System.out.println(percentage.percentage());
		percentage.insert(18);
		System.out.println(percentage.percentage());
		percentage.insert(19);
		System.out.println(percentage.percentage());
		percentage.insert(20);
		System.out.println(percentage.percentage());
		percentage.insert(21);
		System.out.println(percentage.percentage());
		percentage.insert(22);
		int i = percentage.percentage();
		System.out.println(i);

	}
}
