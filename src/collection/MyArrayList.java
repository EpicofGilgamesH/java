package collection;

import java.util.*;

/**
 * 该类主要为了体现迭代器设计思想,不对ArrayList做较全面的展示
 * 迭代器维护三个成员字段 cursor:游标  last:当前元素下标  expectModCount:期望数据长度
 * 在获取next元素时,获取游标所在位置元素,然后游标向后移动一位
 * 在删除next元素时,删除last元素,游标回到last地址;last为-1标识当前元素被删除;确保一个迭代元素只能被删除一次
 * 指定期望数据长度=修改长度
 *
 * @Description ArrayList
 * @Date 2021-07-29 10:20
 * @Created by wangjie
 */
public class MyArrayList<E> extends AbstractList<E>
		implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 1720456651945419394L;

	private static final int DEFAULT_CAPACITY = 10; //初始size

	private static final Object[] DEFAULT_EMPTY_ELEMENTDATA = {}; //空集合

	private Object[] elementData; //集合数组

	private int size; //集合大小

	public MyArrayList() {
		this.elementData = DEFAULT_EMPTY_ELEMENTDATA;
	}

	/**
	 * 通过Object[]获取指定序列的元素,通过类型转换成指定类型,拆箱操作
	 * #@SuppressWarnings("unchecked") 不做Unchecked cast 提示
	 *
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private E getElement(int index) {
		return (E) elementData[index];
	}

	@Override
	public E get(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		return getElement(index);
	}

	/**
	 * 添加元素
	 * 1.modCount+1  modCount:记录修改次数,为后续迭代和序列化是否线程安全提供依据
	 * 2.查看size+1 是否大于 elementData 的长度,是否需要扩容
	 * 3.add元素到 elementData
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean add(E element) {
		modCount++;
		int newCapacity = size + 1;
		if (newCapacity > elementData.length) {
			//需要扩容
			//初始化时elementData元素为空,初始化数组长度为10
			if (elementData == DEFAULT_EMPTY_ELEMENTDATA) {
				newCapacity = Math.max(newCapacity, DEFAULT_CAPACITY);
			}
			grow(newCapacity);
		}
		elementData[size++] = element;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E remove(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		modCount++;
		E oldElement = getElement(index);
		int numMoved = size - index - 1; //删除元素时,将删除元素index后的元素复制到数组中
		if (numMoved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		}
		elementData[--size] = null; //call GC clear the last one
		return null;
	}

	/**
	 * arrayList 扩容操作
	 *
	 * @param minCapacity
	 */
	private void grow(int minCapacity) {
		//扩容时要考虑int溢出的情况
		int newCapacity = minCapacity + (minCapacity >> 1);
		if (newCapacity - minCapacity < 0) {
			//newCapacity如果溢出成为负数,负数-正数如果溢出则为正数,进不了这个if
			newCapacity = minCapacity;
		}
		if (newCapacity - (Integer.MAX_VALUE - 8) > 0) {
			//newCapacity如果溢出成为负数,负数-最大的正数肯定溢出,该if处理扩容溢出的情况
			if (minCapacity < 0) {
				throw new OutOfMemoryError();
			}
			newCapacity = (minCapacity > Integer.MAX_VALUE - 8)
					? Integer.MAX_VALUE : Integer.MAX_VALUE - 8;
		}
		//复制新的数组放到elementData中
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	/**
	 * 删除指定的序列
	 *
	 * @param index
	 */
	public void removeIndexes(int[] index) {
		//从小到大进行交换需要删除的元素
		int l = index.length;
		int des = size - 1;
		for (int i = 0; i < l; i++) {
			int del = index[i];
			while (contain(index, des)) {
				des--;
				l--;
			}
			//swap
			if (des > del)
				elementData[del] = elementData[des];
		}
		//removeRange(elementData.length - index.length, elementData.length - 1);
		elementData = Arrays.copyOf(elementData, size - index.length);
	}

	private boolean contain(int[] arr, int ind) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == ind) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 迭代器类,实现集合的迭代遍历功能
	 * 1.迭代器实现顺序迭代的功能,所有它具有一个记录当前位置的游标,还有当前游标位置下一个
	 * 元素
	 * 2.迭代器实现迭代过程中的删除元素操作,单不允许迭代过程中的新增和修改操作,以免发生不
	 * 可预期的问题
	 */
	public class itrs<E> implements Iterators<E> {

		private int cursor; //游标 迭代的位置
		private int last = -1; //当前元素下标 默认为-1,如果当前元素被删除,标记为-1
		private int expectModCount = modCount; //期望的集合元素数量,确保在迭代的过程中,元素数量不被修改(只允许删除操作)

		/**
		 * 是否还有元素
		 *
		 * @return
		 */
		public boolean hasNext() {
			return cursor != size();
		}

		/**
		 * 获取下一个元素,初始游标从0开始
		 * 1.随时检查期望集合元素数量是否正常
		 * 2.返回当前游标指向的元素
		 * 3.游标+1
		 *
		 * @return
		 */
		@Override
		@SuppressWarnings("unchecked")
		public E next() {
			checkModCount(); //操作之前先检查
			int i = cursor;
			if (i > size) { //如果游标超过size,hasNext返回false,不应进到此方法
				throw new NoSuchElementException();
			}
			Object[] elementData = MyArrayList.this.elementData;
			if (i > elementData.length) {
				//游标超出
				throw new IndexOutOfBoundsException();
			}
			cursor = i + 1;//游标向后移动一位
			return (E) elementData[last = i];
		}

		@Override
		public void remove() {
			if (last < 0) {
				//当前迭代位置<0 ,则异常
				throw new IllegalStateException();
			}
			checkModCount();
			try {
				//删除当前位置元素
				MyArrayList.this.remove(last);
				//游标上移
				cursor = last;
				last = -1;
				expectModCount = modCount; //允许的操作
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		final private void checkModCount() {
			if (modCount != expectModCount)
				throw new ConcurrentModificationException("itrs expectModCount error!");
		}


	}

	public static void main(String[] args) {
		{
			int a = Integer.MAX_VALUE - 10000;
			int c = a + 1;
			int b = a + (a >> 1);
			int d = (Integer.MAX_VALUE - 8);
			System.out.println("b:" + b);
			System.out.println("d:" + d);
			if (b - c < 0) {
				System.out.println("1");
			}

			if (b - d > 0) {
				System.out.println("2");
			}

			int i = a * 2;
			System.out.println(i);

		}

		{
			List<String> list = new MyArrayList<>();
			list.add("a");
			list.add("b");
			list.add("c");
			list.add("d");
			list.add("e");
			list.add("f");

			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				System.out.println(next);
				if ("d".equals(next)) {
					iterator.remove();
				}
			}

			list.forEach(System.out::println);
		}
	}
}
