package test.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 链表类型 实现单链表、循环链表、双向链表，支持增删操作
 * @Date 2022-08-03 10:10
 * @Created by wangjie
 */
public class LinkedType {

	/**
	 * 单向链表
	 *
	 * @param <E>
	 */
	static class SinglyLinkedList<E> {

		/**
		 * 头结点
		 */
		public Node<E> first;
		/**
		 * 尾结点
		 */
		public Node<E> last;
		/**
		 * 结点数
		 */
		public int size;

		/**
		 * add,将该结点添加到链表的尾部
		 *
		 * @param e
		 */
		public void add(E e) {
			final Node<E> l = last;
			final Node<E> newNode = new Node<>(e, null);
			last = newNode;
			if (l == null) {
				//尾结点为空,说明链表中没有结点
				first = newNode;
			} else {
				l.next = newNode;
			}
			size++;
		}

		/**
		 * 删除节点
		 *
		 * @param e
		 */
		public void remove(E e) {
			//链表无结点
			if (first == null || last == null) {
				return;
			}
			//删除item为null的节点
			if (e == null) {
				//从头结点往后遍历
				for (Node<E> x = first, prev = null; x != null; prev = x, x = x.next) {
					if (x.item == null) {
						//删掉该结点
						unlink(x, prev);
					}
				}
			} else {
				for (Node<E> x = first, prev = null; x != null; prev = x, x = x.next) {
					if (e.equals(x.item)) {
						//删掉该结点
						unlink(x, prev);
					}
				}
			}
		}

		/**
		 * 重新连接链表
		 *
		 * @param x
		 * @param prev
		 * @return
		 */
		private Node<E> unlink(Node<E> x, Node<E> prev) {
			Node<E> next;
			if ((next = x.next) == null) { //当前x结点的下结点为null时,即x为尾结点
				if (prev == null) { //第一个节点即命中条件
					first = null;
					last = null;
				} else {
					last = prev;
					prev.next = null;
				}
			} else { //当前x结点的下结点的下一结点不为尾结点
				if (prev == null) {
					first = next;
				} else {
					prev.next = next;
				}
			}
			x.item = null; //help GC
			size--;
			return x;
		}


		static class Node<E> {
			E item;
			Node<E> next;

			Node(E item, Node<E> next) {
				this.item = item;
				this.next = next;
			}
		}
	}

	/**
	 * 双向链表
	 *
	 * @param <E>
	 */
	static class DoubleLinkedList<E> {
		private Node<E> first;
		private Node<E> last;
		private int size;

		static class Node<E> {
			E item;
			Node<E> prev;
			Node<E> next;

			Node(E item, Node<E> prev, Node<E> next) {
				this.item = item;
				this.prev = prev;
				this.next = next;
			}
		}

		/**
		 * 新增
		 */
		public void add(E e) {
			final Node<E> l = last;
			final Node<E> newNode = new Node<>(e, l, null);
			last = newNode;
			if (l == null) { //链表中没有结点
				first = newNode;
			} else {
				l.next = newNode;
			}
			size++;
		}

		/**
		 * 删除
		 *
		 * @param e
		 */
		public void remove(E e) {
			if (first == null || last == null) { //链表无结点
				return;
			}
			if (e == null) { //删除为null的结点
				for (Node<E> x = first; x != null; x = x.next) {
					if (x.item == null) { //命中条件
						unlink(x);
					}
				}
			} else {
				for (Node<E> x = first; x != null; x = x.next) {
					if (e.equals(x.item)) {
						unlink(x);
					}
				}
			}
		}

		/**
		 * 重连链表
		 *
		 * @param x
		 */
		private void unlink(Node<E> x) {
			final Node<E> prev = x.prev;
			final Node<E> next = x.next;
			//思路:
			// 当被删除结点是first时,只用删除其next指针
			// 当被删除结点是last时,只用删除其prev指针
			// 当被删除结点是在中间时,同时删除其next,pev指针
			if (prev == null) { //x即first结点
				first = next;
			} else {
				prev.next = next;
				x.prev = null; //GC 被删除结点x的前向指针
			}
			if (next == null) { //x即尾结点
				last = prev;
			} else {
				next.prev = prev;
				x.next = null; //GC被删除结点x的后向指针
			}
			x.item = null; //help GC
			size--;
		}
	}

	/**
	 * 环形链表 在单链表的基础上尾结点指向头结点
	 *
	 * @param <E>
	 */
	static class RingLinkedList<E> {
		/**
		 * 头结点
		 */
		private Node<E> head;

		/**
		 * 尾结点
		 */
		private Node<E> tail;

		private int size;

		static class Node<E> {
			E item;
			Node<E> next;

			Node(E item, Node<E> next) {
				this.item = item;
				this.next = next;
			}
		}

		/**
		 * 新增
		 *
		 * @param e
		 */
		public void add(E e) {
			final Node<E> t = tail;
			final Node<E> newNode = new Node<>(e, null); //新增结点为尾结点,指向头结点
			if (t == null) { //链表无结点
				head = newNode;
			} else {
				t.next = newNode;
				newNode.next = head;
			}
			tail = newNode;
			size++;
		}

		public void remove(E e) {
			if (head == null || tail == null) {
				return;
			}
			Node<E> prev = null;
			if (e == null) { //删除 item=null 的结点
				for (Node<E> x = head; x != null && !head.equals(x); prev = x, x = x.next) { //环形遍历一周
					if (x.item == null) { //命中条件
						unlink(x, prev);
					}
				}
			} else {
				for (Node<E> x = head; x != null && !head.equals(x); prev = x, x = x.next) { //环形遍历一周
					if (e.equals(x.item)) { //命中条件
						unlink(x, prev);
					}
				}
			}
		}

		private void unlink(Node<E> x, Node<E> prev) {
			final Node<E> next = x.next;
			if (next == null || prev == null) { //环形链表只有一个结点
				head = null;
				tail = null;
			}
			if (next == null) { //删除结点为tail
				prev.next = head;
				tail = prev;
			}
			if (prev == null) { //删除结点为head
				tail.next = next;
				head = next;
			}
			//删除中间结点
			prev.next = next;
			x.item = null;
			size--;
		}
	}

	public static void main(String[] args) {
		LinkedType.SinglyLinkedList<String> singlyLinkedList = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList.remove("1"); //空链表删除

		singlyLinkedList.add("1");
		singlyLinkedList.remove("1"); //单结点删除

		singlyLinkedList.add("2");
		singlyLinkedList.add("3");
		singlyLinkedList.add(null);
		singlyLinkedList.add("4");
		singlyLinkedList.add("5");
		singlyLinkedList.add("6");

		singlyLinkedList.remove(null); //删除null结点
		singlyLinkedList.remove("2"); //删除头结点
		singlyLinkedList.remove("6"); //删除尾结点


		LinkedType.DoubleLinkedList<String> doubleLinkedList = new LinkedType.DoubleLinkedList<>();
		doubleLinkedList.add("1");
		doubleLinkedList.add("2");
		doubleLinkedList.add("3");
		doubleLinkedList.add(null);
		doubleLinkedList.add("4");
		doubleLinkedList.add("5");
		doubleLinkedList.add("6");
		doubleLinkedList.add("7");

		doubleLinkedList.remove(null); //删除null结点
		doubleLinkedList.remove("1"); //删除头结点
		doubleLinkedList.remove("7"); //删除尾结点
		System.out.println("");

		RingLinkedList<String> ringLinkedList = new RingLinkedList<>();
		ringLinkedList.add("1");
		ringLinkedList.add("2");
		ringLinkedList.add("3");
		ringLinkedList.add(null);
		ringLinkedList.add("4");
		ringLinkedList.add("5");
		ringLinkedList.add("6");
		ringLinkedList.add("7");
		ringLinkedList.add("8");

		ringLinkedList.remove("1");
		ringLinkedList.remove("8");
	}
}
