package test.linkedList;

import java.util.HashSet;
import java.util.Set;

public class LeetcodeCaseII {

	/**
	 * 单向链表定义
	 */
	static class ListNode {
		public ListNode() {
		}

		public ListNode(int val) {
			this.val = val;
		}

		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}

		private int val;
		private ListNode next;

	}

	/**
	 * 203. 移除链表元素
	 * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
	 * <p>
	 * <p>
	 * 示例 1：
	 * * *  1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6
	 * * *               ↓
	 * * *  1 ->2 -> 3 -> 4 -> 5
	 * <p>
	 * <p>
	 * 输入：head = [1,2,6,3,4,5,6], val = 6
	 * 输出：[1,2,3,4,5]
	 * 示例 2：
	 * <p>
	 * 输入：head = [], val = 1
	 * 输出：[]
	 * 示例 3：
	 * <p>
	 * 输入：head = [7,7,7,7], val = 7
	 * 输出：[]
	 */
	static class RemoveElements {


		/**
		 * 个人思路:
		 * 最简单的,遍历整个链表,遇到满足条件的节点,进行删除节点操作 时间复杂度O(n)
		 * 代码细节,记录head节点
		 *
		 * @param head
		 * @param val
		 * @return
		 */
		public static ListNode removeElements(ListNode head, int val) {

			ListNode c = head;
			ListNode p = null;
			while (c != null) {
				boolean f = false;
				boolean g = false;
				if (c.val == val) {
					if (p == null) { // 当前处理的节点是头节点,头节点后移
						head = c.next;
						f = true;
					} else {  // 中间节点
						p.next = c.next;
						g = true;
					}
				}
				if (!f) {
					if (!g) {
						p = c;
					}
				}
				c = c.next;
			}

			return head;
		}

		/**
		 * 查看官网文档或者解题评论
		 * 为什么想到不利用哨兵的方式 为其创建一个虚拟的头呢??? 这样繁琐的头节点问题,就可以很方便的解决了
		 * 所以,遇到边界问题,首先要思考,是否能使用哨兵的思维来解决.
		 *
		 * @param head
		 * @param val
		 * @return
		 */
		public static ListNode removeElementsOfficial(ListNode head, int val) {

			ListNode first = new ListNode();
			first.next = head;
			ListNode pre = first;
			while (pre.next != null) {
				if (pre.next.val == val) {
					pre.next = pre.next.next;
				} else {
					pre = pre.next;  // 这里不定义当前处理的节点是因为,在获取当前节点的上一节点时,会出现多个定义的问题
				}
			}
			return first.next;
		}

		/**
		 * 官方思路2 递归
		 *
		 * @param head
		 * @param val
		 * @return
		 */
		public static ListNode removeElementsRecursion(ListNode head, int val) {

			// 无法理解思路 ---
			return null;
		}

		public static void main(String[] args) {

			/*ListNode head = new ListNode(1);
			ListNode node1 = new ListNode(2);
			ListNode node2 = new ListNode(6);
			ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);
			ListNode node5 = new ListNode(5);
			ListNode node6 = new ListNode(6);

			head.next = node1;
			node1.next = node2;
			node2.next = node3;
			node3.next = node4;
			node4.next = node5;
			node5.next = node6;*/

			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(2);
			ListNode node4 = new ListNode(1);
			node1.next = node2;
			node2.next = node3;
			node3.next = node4;


			ListNode listNode = removeElementsOfficial(node1, 2);
			System.out.println();
		}
	}

	/**
	 * 206. 反转链表
	 * 单向链表反转
	 * 输入：head = [1,2,3,4,5]
	 * 输出：[5,4,3,2,1]
	 */
	static class ReverseList {

		/**
		 * 思路:做好反转时候的前后节点控制,重点在于提前存储原链表的下一个节点 和 新链表的构造
		 *
		 * @param head
		 * @return
		 */
		public static ListNode reverseList(ListNode head) {

			ListNode c = head, p = null;
			while (c != null) {
				ListNode n = c.next;
				c.next = p;
				p = c;
				c = n;
			}
			return p;
		}

		public static void main(String[] args) {
			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);

			node1.next = node2;
			node2.next = node3;
			node3.next = node4;

			reverseList(node1);
		}
	}

	/**
	 * 24. 两两交换链表中的节点
	 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
	 * <p> eg：
	 * * *  1 -> 2 -> 3 -> 4
	 * * *  ↓
	 * * *  2 -> 1 -> 4 -> 3
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：head = [1,2,3,4]
	 * 输出：[2,1,4,3]
	 * 示例 2：
	 * <p>
	 * 输入：head = []
	 * 输出：[]
	 * 示例 3：
	 * <p>
	 * 输入：head = [1]
	 * 输出：[1]
	 * <p>
	 */
	static class SwapPairs {

		/**
		 * 个人思路：
		 * 常规思路来看,是每两个元素进行一次操作
		 *
		 * @param head
		 * @return
		 */
		public static ListNode swapPairs(ListNode head) {

			if (head == null)
				return null;
			if (head.next == null)
				return head;

			// 第一步先处理掉
			ListNode f = head, s = f.next, n = s.next, p = null, h = s;
			s.next = f;
			p = f;
			while (n != null) {
				f = n;
				s = n.next;
				if (s != null) {
					n = s.next;
					p.next = s;
					s.next = f;
					p = f;
				} else {
					p.next = f;
					break;
				}
			}
			f.next = null;
			return h;
		}

		public static ListNode swapPairsRecursion(ListNode head) {

			if (head == null || head.next == null) return head;

			ListNode first = head, second = head.next, third = second.next;
			second.next = first;
			first.next = swapPairsRecursion(third);
			return second;
		}

		public static void main(String[] args) {

			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);

			node1.next = node2;
			node2.next = node3;
			node3.next = node4;

			// ListNode node = swapPairs(node1);
			ListNode node = swapPairsRecursion(node1);
			System.out.println();
		}
	}

	/**
	 * 19. 删除链表的倒数第 N 个结点
	 * <p>
	 * 给你一个链表，删除链表的倒数第n个结点，并且返回链表的头结点。
	 * <p>
	 * * * 1 -> 2 -> 3 -> 4 ->5
	 * * * ↓
	 * * * 1-> 2 -> 3 -> 5
	 * 示例 1：
	 * <p>
	 * <p>
	 * 输入：head = [1,2,3,4,5], n = 2
	 * 输出：[1,2,3,5]
	 * 示例 2：
	 * <p>
	 * 输入：head = [1], n = 1
	 * 输出：[]
	 * 示例 3：
	 * <p>
	 * 输入：head = [1,2], n = 1
	 * 输出：[1]
	 * <p>
	 * 提示：
	 * <p>
	 * 链表中结点的数目为 sz
	 * 1 <= sz <= 30
	 * 0 <= Node.val <= 100
	 * 1 <= n <= sz
	 */
	static class RemoveNthFromEnd {

		/**
		 * 个人思路:
		 * 在未遍历完链表之前,并不清楚链表有多少个节点,那也不能判断倒数第n个节点的位置
		 * 常规方法应该是需要遍历两次链表,第一次先计算链表的长度l,第二次找到倒数第n的位置:l-n+1
		 *
		 * @param head
		 * @param n
		 * @return
		 */
		public static ListNode removeNthFromEnd(ListNode head, int n) {

			int l = 0;
			ListNode f = head;
			while (f != null) {
				l++;
				f = f.next;
			}

			ListNode s = head, pre = null;
			int i = 1;
			while (i < l - n + 1) {
				pre = s;
				s = s.next;
				i++;
			}
			if (pre == null) { // 删的是第一个元素
				return head.next;
			}
			pre.next = s.next;
			return head;
		}


		/**
		 * 如上思路有边界问题需要处理,一定要对边界问题产生敏感,考虑周到
		 * 当有类似问题时,要考虑到哨兵模式,补偿一个虚拟节点
		 *
		 * @param head
		 * @param n
		 * @return
		 */
		public static ListNode removeNthFromEndOfficial(ListNode head, int n) {

			int l = 0;
			ListNode f = head;
			while (f != null) {
				l++;
				f = f.next;
			}

			ListNode virtual = new ListNode(-1);
			virtual.next = head;
			ListNode s = head, pre = virtual;
			int i = 1;
			while (i < l - n + 1) {
				pre = s;
				s = s.next;
				i++;
			}
			pre.next = s.next;
			return virtual.next;
		}

		public static void main(String[] args) {

			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			/*ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);
			ListNode node5 = new ListNode(5);*/

			node1.next = node2;
			/*node2.next = node3;
			node3.next = node4;
			node4.next = node5;*/

			// ListNode node = removeNthFromEnd(node1, 2);
			removeNthFromEndOfficial(node1, 2);
			System.out.println();
		}
	}

	/**
	 * 160. 相交链表
	 * 给你两个单链表的头节点headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
	 * <p>
	 * 图示两个链表在节点 c1 开始相交：
	 * <p>
	 * <p>
	 * <p>
	 * 题目数据 保证 整个链式结构中不存在环。
	 * <p>
	 * 注意，函数返回结果后，链表必须 保持其原始结构 。
	 * <p>
	 * 自定义评测：
	 * <p>
	 * 评测系统 的输入如下（你设计的程序 不适用 此输入）：
	 * <p>
	 * intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
	 * listA - 第一个链表
	 * listB - 第二个链表
	 * skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
	 * skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数
	 * 评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * <p>
	 * <p>
	 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
	 * 输出：Intersected at '8'
	 * 解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
	 * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
	 * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
	 * — 请注意相交节点的值不为 1，因为在链表 A 和链表 B 之中值为 1 的节点 (A 中第二个节点和 B 中第三个节点) 是不同的节点。换句话说，它们在内存中指向两个不同的位置，而链表 A 和链表 B 中值为 8 的节点 (A 中第三个节点，B 中第四个节点) 在内存中指向相同的位置。
	 * <p>
	 * <p>
	 * 示例2：
	 * <p>
	 * <p>
	 * <p>
	 * 输入：intersectVal= 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
	 * 输出：Intersected at '2'
	 * 解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
	 * 从各自的表头开始算起，链表 A 为 [1,9,1,2,4]，链表 B 为 [3,2,4]。
	 * 在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
	 * 示例3：
	 * <p>
	 * <p>
	 * <p>
	 * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
	 * 输出：null
	 * 解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
	 * 由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
	 * 这两个链表不相交，因此返回 null 。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * listA 中节点数目为 m
	 * listB 中节点数目为 n
	 * 1 <= m, n <= 3 * 104
	 * 1 <= Node.val <= 105
	 * 0 <= skipA <= m
	 * 0 <= skipB <= n
	 * 如果 listA 和 listB 没有交点，intersectVal 为 0
	 * 如果 listA 和 listB 有交点，intersectVal == listA[skipA] == listB[skipB]
	 * <p>
	 * <p>
	 * 进阶：你能否设计一个时间复杂度 O(m + n) 、仅用 O(1) 内存的解决方案？
	 */
	static class GetIntersectionNode {

		/**
		 * 个人思路:
		 * 常规来说,寻找两个链表的相交点,需要两次循环遍历;时间复杂6、‘度O(n^2)
		 * 可以借助Hash,减少遍历次数
		 *
		 * @param headA
		 * @param headB
		 * @return
		 */
		public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {

			ListNode fa = headA;
			Set<ListNode> set = new HashSet<>();
			while (fa != null) {
				set.add(fa);
				fa = fa.next;
			}
			if (set.isEmpty())
				return null;
			ListNode fb = headB;
			while (fb != null) {
				if (set.contains(fb)) {
					return fb;
				}
				fb = fb.next;
			}
			return null;
		}


		/**
		 * 双指针,交替遍历法
		 * A指针遍历链表A,遍历完之后到B链表头节点
		 * B指针遍历链表B,遍历完之后到A链表头节点
		 * 如果有一个链表为空,则不可能有相交节点
		 * <p>
		 * 设A链表不相交节点数a,B链表不相交节点数b,相交节点数c
		 * 如果有相交节点,则指针A遍历完A链表次数=a+c,然后遍历到B链表并到达相交点次数=a+c+b;
		 * 指针B同理,到达A链表相交点的次数=b+c+a 则说明,两个指针会在第二次遍历时在交点相遇.
		 * <p>
		 * 如果没有相交节点,如果A,B链表的节点数一样,则同时到达链尾,都为null;
		 * 如果A,B链表的节点数不一样,则在第二次遍历时,同时到达链尾,都为null a+c+b+c=b+c+a+c;
		 * <p>
		 * 以上,只会在对方的链表上跑一次
		 *
		 * @param headA
		 * @param headB
		 * @return
		 */
		public static ListNode getIntersectionNodeOfficial(ListNode headA, ListNode headB) {

			if (headA == null || headB == null)
				return null;
			ListNode pA = headA, pB = headB;
			while (pA != pB) {
				pA = pA == null ? headB : pA.next;
				pB = pB == null ? headA : pB.next;
			}
			return pA;
		}

		public static void main(String[] args) {

			ListNode headA = new ListNode(4);
			ListNode headA1 = new ListNode(1);
			ListNode node1 = new ListNode(8);
			ListNode node2 = new ListNode(4);
			ListNode node3 = new ListNode(5);

			ListNode headB = new ListNode(5);
			ListNode headB1 = new ListNode(6);
			ListNode headB2 = new ListNode(1);

			headA.next = headA1;
			headA1.next = node1;
			node1.next = node2;
			node2.next = node3;
			headB.next = headB1;
			headB1.next = headB2;
			headB2.next = node1;

			ListNode intersectionNode = getIntersectionNodeOfficial(headA, headB);
			System.out.println();
		}
	}

	/**
	 * 142. 环形链表 II
	 * <p>
	 * 给定一个链表的头节点 head，返回链表开始入环的第一个节点。如果链表无环，则返回null。
	 * <p>
	 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
	 * <p>
	 * 不允许修改 链表。
	 * 提示：
	 * <p>
	 * 链表中节点的数目范围在范围 [0, 104] 内
	 * -105 <= Node.val <= 105
	 * pos 的值为 -1 或者链表中的一个有效索引
	 */
	static class DetectCycle {

		/**
		 * 个人思路:
		 * 当然使用hash是很简便的方法,但是空间复杂度将不是O(1)
		 * 但是使用双指针,通过数学推理,又没找到合适的办法...
		 * 无赖,查看官方思路...发现,推理正确,但是最终没有找到 找出入环处的节点.
		 *
		 * @param head
		 * @return
		 */
		public static ListNode detectCycle(ListNode head) {

			Set<ListNode> set = new HashSet<>();
			ListNode current = head;
			while (current != null) {
				if (!set.contains(current)) {
					set.add(current);
					current = current.next;
				} else {
					return current;
				}
			}
			return null;
		}

		/**
		 * 官方思路,双指针在环中相交,fast指针最多只需要一圈,就能与slow指针在环中相遇 论证一
		 *
		 * @param head
		 * @return
		 */
		public static ListNode detectCycleOfficial(ListNode head) {

			ListNode fast = head, slow = head;
			// 首先考虑是否环形链表
			// 1.如果不是环形链表,fast指针肯定先到null
			while (fast != null) {
				slow = slow.next;
				if (fast.next != null) {
					fast = fast.next.next;
				} else {
					return null;
				}
				// 2.如果是环形链表,fast在环内第一圈肯定和slow相遇
				if (fast == slow) {
					ListNode p = head;
					while (p != slow) {
						p = p.next;
						slow = slow.next;
					}
					return p;
				}

			}
			return null;
		}

		public static void main(String[] args) {

			ListNode head1 = new ListNode(3);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(0);
			ListNode node4 = new ListNode(-4);

			head1.next = node2;
			node2.next = node3;
			node3.next = node4;
			node4.next = node2;

			// ListNode node = detectCycle(head1);
			ListNode node = detectCycleOfficial(head1);
			System.out.println();
		}
	}
}
