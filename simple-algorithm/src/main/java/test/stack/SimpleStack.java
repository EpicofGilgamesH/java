package test.stack;

import java.util.Arrays;

/**
 * @Description 简单栈数据结构实现
 * @Date 2022-08-09 10:04
 * @Created by wangjie
 */
public class SimpleStack {

	//********************* 用数组实现顺序栈数组结构,支持扩容 ***********************
	static class ArrayStack<T> {

		private final static int DEFAULT_CAPACITY = 10;

		private Object[] items;
		private int size; //栈中元素的个数
		private int capacity; //栈的容量

		public ArrayStack() {
			this.capacity = DEFAULT_CAPACITY;
			items = new Object[this.capacity];
		}

		public ArrayStack(int capacity) {
			this.capacity = capacity;
			items = new Object[this.capacity];
		}

		/**
		 * 入栈操作
		 *
		 * @param v
		 * @return
		 */
		public boolean push(T v) {
			if (size == capacity)
				grow(capacity); //扩容
			items[size] = v;
			size++;
			return true;
		}

		/**
		 * 出栈
		 *
		 * @return
		 */
		public T pop() {
			if (size == 0)
				return null;
			Object v = items[size - 1];
			items[size - 1] = null; //help GC
			size--;
			return (T) v;
		}

		/**
		 * 扩容
		 */
		private void grow(int capacity) {
			//扩容时要考虑int溢出的情况
			int newCapacity = capacity + (capacity >> 1);
			if (newCapacity - capacity < 0) {
				//newCapacity如果溢出成为负数,负数-正数如果溢出则为正数,进不了这个if
				newCapacity = capacity;
			}
			if (newCapacity - (Integer.MAX_VALUE - 8) > 0) {
				//newCapacity如果溢出成为负数,负数-最大的正数肯定溢出,该if处理扩容溢出的情况
				if (capacity < 0) {
					throw new OutOfMemoryError();
				}
				newCapacity = (capacity > Integer.MAX_VALUE - 8)
						? Integer.MAX_VALUE : Integer.MAX_VALUE - 8;
			}
			//复制新的数组放到elementData中
			items = Arrays.copyOf(items, newCapacity);
			this.capacity = newCapacity;
		}

		public static void main(String[] args) {
			SimpleStack.ArrayStack<String> stack = new SimpleStack.ArrayStack<>();
			stack.push("a");
			stack.push("b");
			stack.push("c");
			stack.push("d");
			stack.push("e");
			stack.push("f");
			stack.push("g");
			stack.push("h");
			stack.push("i");
			stack.push("j");
			stack.push("k");
			stack.push("l");
			stack.push("m");
			stack.push("n");
			stack.push("o");
			stack.push("p");
			stack.push("q");
			stack.push("r");
			stack.push("s");
			stack.push("t");
			stack.push("u");
			stack.push("v");
			stack.push("w");
			stack.push("x");
			stack.push("y");
			stack.push("z");
			stack.push("1");
			stack.push("2");
			stack.push("3");

			System.out.println("");
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
			System.out.println(stack.pop());
		}

	}

	/**
	 * 基本四则运算,通过两个栈进行实现
	 * 简单的运算,数字和操作符肯定是交替出现的
	 *
	 * @param express
	 * @return
	 */
	public static int math_operate(String express) {
		ArrayStack<Integer> num = new ArrayStack<>(); //操作数栈
		ArrayStack<String> ops = new ArrayStack<>(); //运算符栈

		char[] chars = express.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String s = String.valueOf(chars[i]);
			int n;
			String o;
			if (i % 2 == 0) {
				n = Integer.valueOf(s);
				num.push(n);
			} else {
				o = s;
				String t;
				while ((t = ops.pop()) != null) {
					if (compare(o, t)) { //新操作符 >栈顶操作符,直接入栈
						ops.push(t);
						break;
					} else { //新操作符 >= 栈顶操作符,进行运算
						int n1 = num.pop();
						int n2 = num.pop();
						int r = getR(t, n1, n2);
						//将运算结果压入栈中
						num.push(r);
					}
				}
				ops.push(o);
			}
		}
		//操作字符串遍历完成后,计算身下的数据
		String t;
		while ((t = ops.pop()) != null) {
			int n1 = num.pop();
			int n2 = num.pop();
			int r = getR(t, n1, n2);
			num.push(r);//将运算结果压入栈中
		}
		return num.pop();//最后出栈数据
	}

	private static int getR(String t, int n1, int n2) {
		int r = 0;
		switch (t) {
			case "+":
				r = n1 + n2;
				break;
			case "-":
				r = n2 - n1;
				break;
			case "*":
				r = n1 * n2;
				break;
			case "/":
				r = n2 / n1;
				break;
		}
		return r;
	}

	/**
	 * 比较两个运算符优先级
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static boolean compare(String s1, String s2) {
		return getLevel(s1) > getLevel(s2);
	}

	private static int getLevel(String s) {
		int v = 0;
		switch (s) {
			case "+":
			case "-":
				break;
			case "*":
			case "/":
				v = 1;
				break;
		}
		return v;
	}

	public static void main(String[] args) {
		int i = math_operate("3+5*8-6*2+9");
		System.out.println(i);
	}
}
