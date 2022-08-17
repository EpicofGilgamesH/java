package test.linkedList;

/**
 * @Description LeeCodeCase
 * @Date 2022-08-04 9:59
 * @Created by wangjie
 */
public class LeeCodeCase1 {

	//实现单链表反转 *****************************************************************************************

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

	//将两个升序链表合并为一个新的 升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。*******************************

	/**
	 * 思路类似合并两个有序数组  这个折腾了一上午,链表指来指去,无非就是指针向后枚举和提前保存指针操作,需要思维清晰
	 *
	 * @param head1
	 * @param head2
	 * @return
	 */
	public static LinkedType.SinglyLinkedList.Node<Integer> mergeEscLinked(LinkedType.SinglyLinkedList.Node<Integer> head1,
	                                                                       LinkedType.SinglyLinkedList.Node<Integer> head2) {
		if (head1 == null) {
			return head2;
		}
		if (head2 == null) {
			return head1;
		}
		//ac:a链表的当前结点;an:a链表的next结点 e:当前重新连接的链表的尾结点 s:重新连接的链表的头结点 sentry:哨兵
		LinkedType.SinglyLinkedList.Node<Integer> sentry = new LinkedType.SinglyLinkedList.Node<>(Integer.MIN_VALUE, null),
				ac = head1, bc = head2, e = sentry;
		while (ac != null && bc != null) {
			if (ac.item <= bc.item) {
				e.next = ac;
				ac = ac.next;
			} else {
				e.next = bc;
				bc = bc.next;
			}
			e = e.next; //新链表向后移动一位
		}
		//查看链表剩余
		if (ac != null) {
			e.next = ac;
		}
		if (bc != null) {
			e.next = bc;
		}
		return sentry.next;
	}


	/**
	 * 递归思路求解
	 * 思路:
	 * list1[0] -> merge(list1[1:],list2)
	 * list2[0] -> merge(list1,list2[1:])
	 * <p>
	 * 当list1[0]<=list2[0]时,用list1[0] 指向 合并好的(list1[1...n],list2[0...n])
	 * 当list1[0]>list2[0]时,用list2[0] 指向 合并好的(list1[0...n],list2[1...n])
	 *
	 * @param head1
	 * @param head2
	 * @return
	 */
	public static LinkedType.SinglyLinkedList.Node<Integer> mergeEscLinked1(LinkedType.SinglyLinkedList.Node<Integer> l1,
	                                                                        LinkedType.SinglyLinkedList.Node<Integer> l2) {
		if (l1 == null) {
			return l2;
		} else if (l2 == null) {
			return l1;
		} else if (l1.item < l2.item) {
			l1.next = mergeEscLinked1(l1.next, l2);
			return l1;
		} else {
			l2.next = mergeEscLinked1(l1, l2.next);
			return l2;
		}
	}


	//给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。************************

	/**
	 * 类似两个有序链表合并思路
	 *
	 * @param lists
	 * @return
	 */
	public static LinkedType.SinglyLinkedList.Node<Integer> mergeKLists(LinkedType.SinglyLinkedList.Node<Integer>[] lists) {
		if (lists == null || lists.length == 0) {
			return null;
		}
		//设置哨兵结点,每个链表的指针放在数组集合中,用index标识
		LinkedType.SinglyLinkedList.Node<Integer> sentry = new LinkedType.SinglyLinkedList.Node<>(Integer.MIN_VALUE, null);
		LinkedType.SinglyLinkedList.Node<Integer>[] pointers = new LinkedType.SinglyLinkedList.Node[lists.length];
		for (int i = 0; i < pointers.length; ++i) {
			pointers[i] = lists[i];
		}
		LinkedType.SinglyLinkedList.Node<Integer> e = sentry, min; //e为结果链表的尾结点

		while ((min = isAllNull(pointers)).item != Integer.MAX_VALUE) { //当只剩下一个链表还有结点,则退出;但是判断所有链表都没有结点,思路会更清晰
			//求所有指针中最小的,结果链表的尾结点指向最小结点
			e.next = min;
			//结果链表的尾结点向后移动一位
			e = e.next;
		}
		return sentry.next;
	}

	/**
	 * 是否所有的指针都已经指向null,如果不是则返回最小的指针
	 *
	 * @param pointers
	 * @return
	 */
	private static LinkedType.SinglyLinkedList.Node<Integer> isAllNull(LinkedType.SinglyLinkedList.Node<Integer>[] pointers) {
		LinkedType.SinglyLinkedList.Node<Integer> min = new LinkedType.SinglyLinkedList.Node<>(Integer.MAX_VALUE, null);
		int j = 0;//记录最小结点指针的index
		for (int i = 0; i < pointers.length; i++) {
			if (pointers[i] != null && pointers[i].item <= min.item) {
				min = pointers[i];
				j = i;
			}

		}
		if (pointers[j] != null)  //for循环没有进入,即pointers数组中没有的指针全为null时
			pointers[j] = pointers[j].next;//最小指针后移一位
		return min;
	}


	//给定一个头结点为 head 的非空单链表，返回链表的中间结点。如果有两个中间结点，则返回第二个中间结点*******************************

	/**
	 * 快慢指针 s指针每次前进1步,f指针每次前进2步,当f指针走到null时,s指针即为中点
	 *
	 * @param head
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedType.SinglyLinkedList.Node<E> middleNode(LinkedType.SinglyLinkedList.Node<E> head) {

		if (head == null || head.next == null) { //没有结点或只有一个结点,直接返回
			return head;
		}
		LinkedType.SinglyLinkedList.Node<E> s = head, f = head.next;//slow,fast指针分别从0位置开始,0是哨兵位置
		while (f != null) {
			s = s.next;
			f = f.next != null ? f.next.next : null;
		}
		return s;
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


		LinkedType.SinglyLinkedList<Integer> singlyLinkedList1 = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList1.add(1);
		singlyLinkedList1.add(2);
		singlyLinkedList1.add(3);
		singlyLinkedList1.add(4);
		singlyLinkedList1.add(5);
		singlyLinkedList1.add(6);
		singlyLinkedList1.add(7);
		singlyLinkedList1.add(8);

		LinkedType.SinglyLinkedList<Integer> singlyLinkedList2 = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList2.add(1);
		singlyLinkedList2.add(3);
		singlyLinkedList2.add(5);
		singlyLinkedList2.add(6);
		singlyLinkedList2.add(9);

		//LinkedType.SinglyLinkedList.Node<Integer> integerNode = mergeEscLinked(singlyLinkedList1.first, singlyLinkedList2.first);
		System.out.println("");

		//LinkedType.SinglyLinkedList.Node<Integer> integerNode1 = mergeEscLinked1(singlyLinkedList1.first, singlyLinkedList2.first);
		System.out.println("");

		//LinkedType.SinglyLinkedList.Node<Integer> integerNode = middleNode(singlyLinkedList1.first);
		System.out.println("");

		LinkedType.SinglyLinkedList<Integer> singlyLinkedList3 = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList3.add(2);
		singlyLinkedList3.add(4);
		singlyLinkedList3.add(6);
		singlyLinkedList3.add(9);
		singlyLinkedList3.add(10);

		LinkedType.SinglyLinkedList.Node<Integer>[] arr = new LinkedType.SinglyLinkedList.Node[3];
		arr[0] = singlyLinkedList1.first;
		arr[1] = singlyLinkedList2.first;
		arr[2] = singlyLinkedList3.first;

		LinkedType.SinglyLinkedList.Node<Integer> integerNode = mergeKLists(arr);
		System.out.println("");
	}
}
