package test.array;

/**
 * 实现一个大小固定的有序数组, 支持动态增删改操作
 * 允许重复元素
 * 有序数组的查找,首先想到的应该是二分查找的思路
 *
 * @Date 2022-07-29 18:10
 * @Created by wangjie
 */
public class SortArray {

	private int[] data;
	private int size; //数组总容量
	private int mod; //元素数量

	public SortArray(int size) {
		this.size = size;
		this.data = new int[size];
	}

	/**
	 * 有序数组 二分查找来实现
	 */
	public void insert(int v) {
		int low = 0, high = mod - 1;
		int pos = getPos(v, low, high);
		//pos后面的所有元素向后移动一位,t放到pos+1的位置
		for (int i = mod - 1; i > pos; --i) {
			data[i + 1] = data[i];
		}
		data[pos + 1] = v;
		mod++;
	}

	/**
	 * 找到当前值需要插入的位置
	 *
	 * @param v    当前元素值
	 * @param low  查找起始位置
	 * @param high 查找结束位置
	 * @return
	 */
	private int getPos(int v, int low, int high) {
		int pos = -1;
		while (low <= high) {
			int mid = high - ((high - low) >> 2);
			if (data[mid] == v) {
				pos = mid;
				break;
			} else if (data[mid] > v) {
				//在[low,mid-1]中查找
				high = mid - 1;
				pos = high;
			} else {
				//在[mid+1,high]中查找
				low = mid + 1;
				pos = high;
			}
		}
		return pos;
	}

	/**
	 * 修改元素的值
	 */
	public void update(int index, int v) {
		int o = data[index];
		if (o == v)
			return;
		int pos = getPos(v, 0, mod - 1);
		if (o < v) {
			//在右边数据中新增一个v数据,向左移动 [index,pos-1],最左边先移动
			for (int i = index; i < pos; ++i) {
				data[i] = data[i + 1];
			}
		} else {
			//在左边数据中新增一个v数据,向右移动 [index-1,pos],最右边先移动
			for (int i = index - 1; i >= pos; --i) {
				data[i + 1] = data[i];
			}
		}
		//修改pos+1位置的值
		data[pos + 1] = v;
	}

	/**
	 * 删除元素v
	 *
	 * @param v
	 */
	public void delete(int v) {
		int pos = getPos(v, 0, mod - 1);
		if (data[pos] != v) {
			return;
		}
		//pos被删除,则[pos+1,mod-1]数据左移一位
		for (int i = pos + 1; i <= mod - 1; ++i) {
			data[i] = data[i + 1];
		}
		mod--;
	}

	public static void main(String[] args) {
		SortArray sortArray = new SortArray(16);
		sortArray.insert(6);
		sortArray.insert(3);
		sortArray.insert(1);
		sortArray.insert(12);
		sortArray.insert(17);

		sortArray.insert(8);
		sortArray.insert(8);

		sortArray.update(4, 2);
		sortArray.update(5, 4);

		sortArray.delete(4);

		System.out.println("");
	}
}
