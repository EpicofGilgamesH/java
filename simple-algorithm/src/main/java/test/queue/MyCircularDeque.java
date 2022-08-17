package test.queue;

/**
 * LeeCode: 641.设计循环双端队列
 *  循环双端队列
 * @Date 2022-08-10 15:39
 * @Created by wangjie
 */
public class MyCircularDeque {

	private int[] data;
	private int size; //队列长度
	private int head; //队头序列
	private int tail; //队尾序列

	public MyCircularDeque(int k) {
		this.data = new int[k + 1]; //tail 预留一个空间
		this.size = k;
	}

	public boolean insertFront(int value) {
		if ((tail + 1) % (size + 1) == head)
			return false;
		head = head == 0 ? size : head - 1;
		data[head] = value;
		return true;
	}

	public boolean insertLast(int value) {
		if ((tail + 1) % (size + 1) == head)
			return false;
		data[tail] = value;
		tail = tail == size ? 0 : tail + 1;
		return true;
	}

	public boolean deleteFront() {
		if (tail == head) return false;
		head = head == size ? 0 : head + 1;
		return true;
	}

	public boolean deleteLast() {
		if (tail == head) return false;
		tail = tail == 0 ? size : tail - 1;
		return true;
	}

	public int getFront() {
		if (tail == head) return -1;
		return data[head];
	}

	public int getRear() {
		if (tail == head) return -1;
		int t = tail == 0 ? size : tail - 1;
		return data[t];
	}

	public boolean isEmpty() {
		return tail == head;
	}

	public boolean isFull() {
		return (tail + 1) % (size + 1) == head;
	}

	/**
	 * Your MyCircularDeque object will be instantiated and called as such:
	 * MyCircularDeque obj = new MyCircularDeque(k);
	 * boolean param_1 = obj.insertFront(value);
	 * boolean param_2 = obj.insertLast(value);
	 * boolean param_3 = obj.deleteFront();
	 * boolean param_4 = obj.deleteLast();
	 * int param_5 = obj.getFront();
	 * int param_6 = obj.getRear();
	 * boolean param_7 = obj.isEmpty();
	 * boolean param_8 = obj.isFull();
	 */

	public static void main(String[] args) {
		MyCircularDeque deque = new MyCircularDeque(3);
		System.out.println(deque.insertLast(1));
		System.out.println(deque.insertLast(2));
		System.out.println(deque.insertFront(3));
		System.out.println(deque.insertFront(4));
		System.out.println(deque.getRear());
		System.out.println(deque.isFull());
		System.out.println(deque.deleteLast());
		System.out.println(deque.insertFront(4));
		System.out.println(deque.getFront());
	}
}
