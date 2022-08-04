package test.LinkedList;

/**
 * @Description LeeCodeCase
 * @Date 2022-08-04 9:59
 * @Created by wangjie
 */
public class LeeCodeCase1 {

	//实现单链表反转 ***************************

	/**
	 * 思路: 从头结点开始往后遍历,将指针反转过来
	 */
	public static <E> void singlyLinkedReverse(LinkedType.SinglyLinkedList<E> head) {
		//从头结点开始枚举链表
		if (head == null || head.first == null) { //链表无结点
			return;
		}
		if (head.first.next == null) { //链表只有一个结点
			return;
		}
		//prev枚举的当前结点的prev节点,x枚举的当前结点
		LinkedType.SinglyLinkedList.Node<E> x = head.first, prev = null, next = x;
		head.last = x;//头结点尾结点互换
		for (; next != null; prev = x) {
			x = next;
			next = x.next;
			x.next = prev;//当前结点指向前一个结点
		}
		head.first = prev;
	}

	/**
	 * 通过递归的方式,比较难以理解
	 *
	 * @param head
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedType.SinglyLinkedList.Node<E> singlyLinkedReverse1(LinkedType.SinglyLinkedList.Node<E> head) {
		if (head == null || head.next == null) {
			return head;
		}
		LinkedType.SinglyLinkedList.Node<E> newNode = singlyLinkedReverse1(head.next);//先反转k的上一个,再反转自己
		head.next.next = head;
		head.next = null;
		return newNode;
	}

	public static void main(String[] args) {
		LinkedType.SinglyLinkedList<String> singlyLinkedList = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList.add("1");
		singlyLinkedList.add("2");
		singlyLinkedList.add("3");
		singlyLinkedList.add(null);
		singlyLinkedList.add("4");
		singlyLinkedList.add("5");
		singlyLinkedList.add("6");

		singlyLinkedReverse(singlyLinkedList);
		System.out.println("");

		LinkedType.SinglyLinkedList.Node<String> stringNode = singlyLinkedReverse1(singlyLinkedList.first);
		System.out.println("");
	}
}
