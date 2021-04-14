package heap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
		heapify(a, size, i);
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
			heapify(arr, n, i);
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
		Task task1 = new Task("A", LocalDateTime.of(2021, 4, 6, 13, 53, 00));
		Task task2 = new Task("A", LocalDateTime.of(2021, 4, 6, 15, 20, 00));
		Task task3 = new Task("A", LocalDateTime.of(2021, 4, 6, 15, 55, 00));
		Task task4 = new Task("A", LocalDateTime.of(2021, 4, 6, 17, 30, 00));
		Task[] tasks = new Task[]{task1, task2, task3, task4};

		//构建小顶堆
		build(tasks);



	}

	private static void build(Task[] tasks) {
		//从第一个非叶子节点进行堆化 (n-1)/2
		int n = tasks.length;
		if (tasks == null || n == 0) {
			return;
		}
		for (int i = n - 1; i >= 0; i--) {
			//堆化操作
			heapify(tasks, n, i);
		}
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
			if (i * 2 + 2 <= n && tasks[i * 2 + 2].time.isBefore(tasks[i].time))
				minIndex = i * 2 + 2;
			if (minIndex == i) //寻找的下一个堆化点是自己则退出循环
				break;
			//交换
			Task temp = new Task("", LocalDateTime.now());
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
	}


	//堆的应用二 : 求TOP K

	//堆的应用三 : 求中位数


	public static void main(String[] args) {

		//将运单号组装成in语句
		//获取数据并去重
		HashSet<String> set = new HashSet<>();
		Path filePath = Paths.get("C:\\Users\\Administrator\\Downloads\\", "fix.txt");
		//java 1.8 文件流处理
		StringBuilder stringBuilder = new StringBuilder();
		Long size = 0L;
		try (Stream lines = Files.lines(filePath)) {
			size = lines.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (Stream lines = Files.lines(filePath)) {
			String filedName = "waybill_no";
			stringBuilder.append(filedName).append(" in (");
			AtomicInteger j = new AtomicInteger();
			Long finalSize = size;
			lines.forEach(x -> {
				int index = j.get();
				if (index != 0) {
					if (index % 1000 == 999) {
						stringBuilder.append(") or " + filedName + " in (");
					} else {
						stringBuilder.append(",");
					}
				}
				stringBuilder.append("'" + x + "'");
				if (index == finalSize.intValue()) {
					stringBuilder.append(")");
				}
				j.incrementAndGet();
			});
			stringBuilder.append(")");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(stringBuilder.toString());
	}

}
