package test.linkedList;

/**
 * @Description LeeCode 案例
 * @Date 2022-08-03 17:14
 * @Created by wangjie
 */
public class LeeCodeCase {

	//给你一个链表的头节点 head ，判断链表中是否有环。*************************

	/**
	 * 判断链表中是否有环,快慢指针 至于为什么快慢指针如果有环,一定会相遇;
	 * 通过反举法,如果在一个环形中,相交的场景中,快指针刚好跳过慢指针,跑到慢指针的前面;
	 * 那么快慢指针没有落在同一个点,那他们的上一步是在同一个点出发的,违背假设.
	 *
	 * @param head
	 * @return
	 */
	public static <E> boolean hasCycle(LinkedType.SinglyLinkedList.Node<E> head) {
		LinkedType.SinglyLinkedList.Node<E> f = head, s = head;
		if (head == null || head.next == null) { //没有元素或者只有一个元素
			return false;
		}
		while (f != null && f.next != null) {
			s = s.next;
			f = f.next.next;
			if (s == f) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		LinkedType.SinglyLinkedList<Integer> singlyLinkedList = new LinkedType.SinglyLinkedList<>();
		singlyLinkedList.add(1);
		singlyLinkedList.add(2);
		singlyLinkedList.add(3);
		singlyLinkedList.add(4);
		singlyLinkedList.add(5);
		singlyLinkedList.add(6);
		singlyLinkedList.add(7);
		singlyLinkedList.add(8);

		boolean b = hasCycle(singlyLinkedList.first);
		System.out.println(b);

		singlyLinkedList.last.next = singlyLinkedList.first;

		LinkedType.SinglyLinkedList.Node<Integer> newNode = new LinkedType.SinglyLinkedList.Node<>(0, singlyLinkedList.first);

		boolean b1 = hasCycle(newNode);
		System.out.println(b1);
	}

}
