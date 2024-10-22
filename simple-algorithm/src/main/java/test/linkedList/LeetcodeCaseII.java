package test.linkedList;

import java.util.*;

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

	/**
	 * 86. 分隔链表
	 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
	 * 你应当 保留 两个分区中每个节点的初始相对位置。
	 * 示例 1：
	 * 输入：head = [1,4,3,2,5,2], x = 3
	 * 输出：[1,2,2,4,3,5]
	 * 示例 2：
	 * 输入：head = [2,1], x = 2
	 * 输出：[1,2]
	 * 提示：
	 * 链表中节点的数目在范围 [0, 200] 内
	 * -100 <= Node.val <= 100
	 * -200 <= x <= 200
	 */
	public static class Partition {

		/**
		 * 个人思路:
		 * 首先思考在原链表上进行操作,常规做法
		 * 1.找到第一个 >=x的节点,并记录p节点前置节点pre_p,如果第一个节点就>=x 所以需要为链表添加一个前置节点head
		 * 2.遍历链表从p.next节点开始,记录为q,其前置节点记录为pre_q,如果 q.val < x 则pre_q.next=q.next; pre_p.next=q; q.next=p
		 *
		 * @param head
		 * @param x
		 * @return
		 */
		public static ListNode partition(ListNode head, int x) {
			if (head == null) return head;
			ListNode node = new ListNode(Integer.MIN_VALUE);
			ListNode result = node;
			node.next = head;
			ListNode p = null, pre_p = null, q, pre_q;
			while (node.next != null) {
				// 找到第一个 > x的节点
				pre_q = node;
				q = node.next;
				if (pre_p == null && q.val >= x) {
					pre_p = pre_q;
					p = q;
				}
				boolean flag = false;
				if (pre_p != null && q.val < x) {
					pre_q.next = q.next;
					pre_p.next = q;
					q.next = p;

					pre_p = pre_p.next;
					p = pre_p.next;
					flag = true;
				}
				if (!flag)
					node = pre_q.next;
			}
			return result.next;
		}

		/**
		 * 构造左右两个链表,分别存放小于和大于等于x的节点数据
		 *
		 * @param head
		 * @param x
		 * @return
		 */
		public static ListNode partitionI(ListNode head, int x) {
			ListNode min = new ListNode(Integer.MIN_VALUE);
			ListNode max = new ListNode(Integer.MIN_VALUE);
			ListNode left = min, right = max;
			while (head != null) {
				ListNode next = head.next;
				if (head.val < x) {
					left.next = head;
					left.next.next = null;
					left = left.next;
				} else {
					right.next = head;
					right.next.next = null;
					right = right.next;
				}
				head = next;
			}
			left.next = max.next;
			return min.next;
		}

		public static void main(String[] args) {
			ListNode head = new ListNode(1);
			ListNode node1 = new ListNode(4);
			ListNode node2 = new ListNode(3);
			ListNode node3 = new ListNode(0);
			ListNode node4 = new ListNode(2);
			ListNode node5 = new ListNode(5);
			ListNode node6 = new ListNode(2);
			head.next = node1;
			node1.next = node2;
			node2.next = node3;
			node3.next = node4;
			node4.next = node5;
			node5.next = node6;
			ListNode partition = partitionI(head, 3);
			System.out.println();

			ListNode aa = node1;
			node1 = null;
			System.out.println();

			ABC abc = new ABC("a", "b");
			ABC ab = new ABC("123", "456");
			ABC abc1 = abc;
			abc = ab;
			System.out.println();
		}

	}

	public static class ABC {
		private String aa;
		private String bb;

		public ABC(String aa, String bb) {
			this.aa = aa;
			this.bb = bb;
		}

		public String getBb() {
			return bb;
		}

		public void setBb(String bb) {
			this.bb = bb;
		}

		public String getAa() {
			return aa;
		}

		public void setAa(String aa) {
			this.aa = aa;
		}
	}

	/**
	 * 237. 删除链表中的节点
	 * 有一个单链表的 head，我们想删除它其中的一个节点 node。
	 * 给你一个需要删除的节点 node 。你将 无法访问 第一个节点  head。
	 * 链表的所有值都是 唯一的，并且保证给定的节点 node 不是链表中的最后一个节点。
	 * 删除给定的节点。注意，删除节点并不是指从内存中删除它。这里的意思是：
	 * 给定节点的值不应该存在于链表中。
	 * 链表中的节点数应该减少 1。
	 * node 前面的所有值顺序相同。
	 * node 后面的所有值顺序相同。
	 * 自定义测试：
	 * 对于输入，你应该提供整个链表 head 和要给出的节点 node。node 不应该是链表的最后一个节点，而应该是链表中的一个实际节点。
	 * 我们将构建链表，并将节点传递给你的函数。
	 * 输出将是调用你函数后的整个链表。
	 * 示例 1：
	 * 输入：head = [4,5,1,9], node = 5
	 * 输出：[4,1,9]
	 * 解释：指定链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9
	 * 示例 2：
	 * 输入：head = [4,5,1,9], node = 1
	 * 输出：[4,5,9]
	 * 解释：指定链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9
	 * 提示：
	 * 链表中节点的数目范围是 [2, 1000]
	 * -1000 <= Node.val <= 1000
	 * 链表中每个节点的值都是 唯一 的
	 * 需要删除的节点 node 是 链表中的节点 ，且 不是末尾节点
	 */
	public static class DeleteNode {

		/**
		 * 个人思路:
		 * 题意不是很清晰,是需要删除链表中的一个非链尾的节点么?
		 * eg:
		 * 4 -> 1 -> 5 -> 3  链表需要删除值为5的节点
		 * 按常规方法,找到值为5的节点时,记录其前置节点1,同时记录其后继节点3,把前置节点的next指向后置节点
		 * 但是次数题意中有个条件,每个元素的值不相同,所以节点的值可以修改;将1的next节点的值改为1 : 4 -> 1 -> 1 -> 3
		 * 然后删除1节点,这样操作起来会方便很多
		 *
		 * @param node
		 */
		public static void deleteNode(ListNode node) {
			node.val = node.next.val;
			node.next = node.next.next;
		}
	}

	public static class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}

	/**
	 * 138. 随机链表的复制
	 * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
	 * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
	 * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
	 * 返回复制链表的头节点。
	 * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
	 * val：一个表示 Node.val 的整数。
	 * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
	 * 你的代码 只 接受原链表的头节点 head 作为传入参数。
	 * 示例 1：
	 * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
	 * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
	 * 示例 2：
	 * 输入：head = [[1,1],[2,1]]
	 * 输出：[[1,1],[2,1]]
	 * 示例 3：
	 * 输入：head = [[3,null],[3,0],[3,null]]
	 * 输出：[[3,null],[3,0],[3,null]]
	 * 提示：
	 * 0 <= n <= 1000
	 * -104 <= Node.val <= 104
	 * Node.random 为 null 或指向链表中的节点。
	 */
	public static class CopyRandomList {

		/**
		 * 个人思路:
		 * 深拷贝随机链表,next指针很好复制,但是随机指针需要节点存在才可以复制
		 * 那么需要两次构造,第一次遍历构造next指针,第二次遍历构造random指针
		 *
		 * @param head
		 * @return
		 */
		public static Node copyRandomList(Node head) {
			Map<Node, Node> map = new HashMap<>();
			Node cp = new Node(Integer.MIN_VALUE);
			Node cpHead = cp;
			Node node = head;
			while (node != null) {
				Node cpNode = new Node(node.val);
				cpHead.next = cpNode;
				map.put(node, cpNode);
				node = node.next;
				cpHead = cpHead.next;
			}
			Node randomNode = cp.next;
			node = head;
			while (node != null) {
				if (node.random != null) {
					randomNode.random = map.get(node.random);
				}
				node = node.next;
				randomNode = randomNode.next;
			}
			return cp.next;
		}

		public static void main(String[] args) {
			Node node7 = new Node(7);
			Node head13 = new Node(13);
			Node head11 = new Node(11);
			Node head10 = new Node(10);
			Node head1 = new Node(1);
			node7.next = head13;
			head13.next = head11;
			head11.next = head10;
			head10.next = head1;
			node7.random = null;
			head13.random = node7;
			head11.random = head1;
			head10.random = head11;
			head1.random = node7;
			Node node = copyRandomList(node7);
			System.out.println();
		}
	}

	/**
	 * 2. 两数相加
	 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
	 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
	 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
	 * 示例 1：
	 * 输入：l1 = [2,4,3], l2 = [5,6,4]
	 * 输出：[7,0,8]
	 * 解释：342 + 465 = 807.
	 * 示例 2：
	 * 输入：l1 = [0], l2 = [0]
	 * 输出：[0]
	 * 示例 3：
	 * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
	 * 输出：[8,9,9,9,0,0,0,1]
	 * 提示：
	 * 每个链表中的节点数在范围 [1, 100] 内
	 * 0 <= Node.val <= 9
	 * 题目数据保证列表表示的数字不含前导零
	 */
	public static class AddTwoNumbers {

		/**
		 * 1.链表对应都有的节点处理
		 * 2.只有一条链表有的节点
		 *
		 * @param l1
		 * @param l2
		 * @return
		 */
		public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
			ListNode res = new ListNode(), next = res, pre = res;
			boolean isCarry = false;
			while (l1 != null || l2 != null) {
				int sum = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + (isCarry ? 1 : 0);
				int v = sum % 10; // 相加后节点的值
				isCarry = sum / 10 == 1; // 相加后,后一位是否进位
				next.val = v;
				if (l1 != null) l1 = l1.next;
				if (l2 != null) l2 = l2.next;
				pre = next;
				next.next = new ListNode();
				next = next.next;
			}
			// 两个链表都结束遍历后,可能还存在进位
			if (isCarry) {
				next.val = 1;
			} else {
				pre.next = null;
			}
			return res;
		}

		public static void main(String[] args) {
			ListNode l1 = new ListNode(9);
			ListNode l2 = new ListNode(9);
			ListNode l3 = new ListNode(9);
			ListNode l4 = new ListNode(9);
			ListNode l5 = new ListNode(9);
			ListNode l6 = new ListNode(9);
			ListNode l7 = new ListNode(9);
			l1.next = l2;
			l2.next = l3;
			l3.next = l4;
			l4.next = l5;
			l5.next = l6;
			l6.next = l7;

			ListNode l2_1 = new ListNode(9);
			ListNode l2_2 = new ListNode(9);
			ListNode l2_3 = new ListNode(9);
			ListNode l2_4 = new ListNode(9);
			l2_1.next = l2_2;
			l2_2.next = l2_3;
			l2_3.next = l2_4;

			/*ListNode l1 = new ListNode(2);
			ListNode l2 = new ListNode(4);
			ListNode l3 = new ListNode(3);
			l1.next = l2;
			l2.next = l3;

			ListNode l4 = new ListNode(5);
			ListNode l5 = new ListNode(6);
			ListNode l6 = new ListNode(4);
			l4.next = l5;
			l5.next = l6;*/

			ListNode node = addTwoNumbers(l1, l2_1);
			System.out.println(node);
		}
	}

	/**
	 * 92. 反转链表 II
	 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
	 * 示例 1：
	 * 输入：head = [1,2,3,4,5], left = 2, right = 4
	 * 输出：[1,4,3,2,5]
	 * 示例 2：
	 * 输入：head = [5], left = 1, right = 1
	 * 输出：[5]
	 * 提示：
	 * 链表中节点数目为 n
	 * 1 <= n <= 500
	 * -500 <= Node.val <= 500
	 * 1 <= left <= right <= n
	 * 进阶： 你可以使用一趟扫描完成反转吗？
	 * <p>
	 * todo...该题是超多头部公司的面试题,虽然琢磨了1-2个小时是做出来了,但是中途对于链表的反转思路还是不够清晰,后续需要多回顾思考.
	 */
	public static class ReverseBetween {

		/**
		 * 个人思路:
		 * 自定的范围内反转链表,需要记录反转的开始节点和结束节点
		 *
		 * @param head
		 * @param left
		 * @param right
		 * @return
		 */
		public static ListNode reverseBetween(ListNode head, int left, int right) {
			if (left == right) return head;
			ListNode lNode = new ListNode(Integer.MIN_VALUE, head);
			head = lNode;
			int i = 0;
			ListNode pre = lNode; // 反转区域的前一个节点
			while (i < left) {
				pre = lNode;
				lNode = lNode.next;
				i++;
			}
			// 进入反转区域 lNode为反转区域的第一个节点
			ListNode firstIn = lNode;
			ListNode preIn = null;
			ListNode cur = lNode;
			while (i <= right) {
				ListNode n = cur.next;  // 存储当前节点的下一个节点
				cur.next = preIn;       // 当前节点指向上一个节点
				preIn = cur;            // 存储当前节点作为上一个节点
				cur = n;                // 当前节点向后一个节点移动
				i++;
			}
			// 反转区域完成后,需要处理反转区域左右两边的链表连接
			pre.next = preIn;
			firstIn.next = cur;
			return head.next;
		}

		public static void main(String[] args) {
			ListNode node1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);
			ListNode node5 = new ListNode(5);
			node1.next = node2;
			node2.next = node3;
			node3.next = node4;
			node4.next = node5;
			ListNode node = reverseBetween(node1, 1, 4);
			System.out.println(node);
		}
	}

	/**
	 * 25. K 个一组翻转链表
	 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
	 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
	 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
	 * 示例 1：
	 * 输入：head = [1,2,3,4,5], k = 2
	 * 输出：[2,1,4,3,5]
	 * 示例 2：
	 * 输入：head = [1,2,3,4,5], k = 3
	 * 输出：[3,2,1,4,5]
	 * 提示：
	 * 链表中的节点数目为 n
	 * 1 <= k <= n <= 5000
	 * 0 <= Node.val <= 1000
	 * 进阶：你可以设计一个只用 O(1) 额外内存空间的算法解决此问题吗？
	 */
	public static class ReverseKGroup {

		/**
		 * 类似上一题的思路,前k个节点进行翻转,后k个节点不翻转,依次进行
		 * 首先要知道链表的长度,确保k不是链表长度倍数时,最后剩下的节点不需要翻转
		 *
		 * @param head
		 * @param k
		 * @return
		 */
		public ListNode reverseKGroup(ListNode head, int k) {
			ListNode lNode = new ListNode(Integer.MIN_VALUE, head);
			head = lNode;
			int i = 0, j = 1;
			ListNode pre = null, in = lNode, cur = lNode;
			while (i < k) {
				ListNode next = cur.next;

			}

			return null;
		}
	}

	/**
	 * 82. 删除排序链表中的重复元素 II
	 * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
	 * 示例 1：
	 * 输入：head = [1,2,3,3,4,4,5]
	 * 输出：[1,2,5]
	 * 示例 2：
	 * 输入：head = [1,1,1,2,3]
	 * 输出：[2,3]
	 * 提示：
	 * 链表中节点数目在范围 [0, 300] 内
	 * -100 <= Node.val <= 100
	 * 题目数据保证链表已经按升序 排列
	 */
	public static class DeleteDuplicates {

		/**
		 * 个人思路
		 * 遍历链表的同时,记录每个节点的值,当节点的值相同时,进行记录;直到遍历到不同值的节点时,由第一个相同的记录节点的前一个节点
		 * 指向当前节点
		 * <p>
		 * 本题 记录一个前节点当前值,然后遍历节点时依次对比,不太方便
		 * 官方解题思路是,当前节点的next节点和next->next节点比较值是否相等
		 *
		 * @param head
		 * @return
		 */
		public static ListNode deleteDuplicates(ListNode head) {
			ListNode hair = new ListNode(Integer.MIN_VALUE, head);
			ListNode cur = hair, pre = hair, ppre = hair;
			int v = 0;
			boolean flag = false, end = false;
			while (cur != null) {
				if (v == cur.val) {
					flag = true;
				} else {  // 不等
					if (flag) {  // 且前面有相等变为不等时,需要重新指向
						ppre.next = cur;
					} else {
						ppre = pre;
						pre = cur;
					}
					v = cur.val;
					flag = false;
				}
				cur = cur.next;
				if (cur == null && !end) {
					cur = new ListNode(Integer.MIN_VALUE);
					end = true;
				}
			}
			ppre.next = null;
			return hair.next;
		}

		/*
		 * 官方思路：
		 * 通过查找链表中节点不同 来删除相同的节点不太方便操作
		 * 官方思路中,是遍历到当前节点时,去比较其下个节点和下下个节点的值是否相同
		 * 如果相同,就删掉当前节点的下一个节点,同时要记录被删除节点的值x,后续对比,直到next节点不等于x
		 * 官方思路的关键点在于,需要通过当前几点去比较下个节点和下下个节点,这样删除操作将变得非常简单
		 */
		public static ListNode deleteDuplicatesOfficial(ListNode head) {
			if (head == null) return null;
			ListNode hair = new ListNode(Integer.MIN_VALUE, head);
			ListNode cur = hair;
			while (cur.next != null && cur.next.next != null) {
				if (cur.next.val == cur.next.next.val) {  // 当前节点的next节点和next.next节点值相等 记录值,同时删掉next节点
					int v = cur.next.val;
					cur.next = cur.next.next;
					while (cur.next != null && cur.next.val == v) {
						cur.next = cur.next.next;
					}
				} else {
					cur = cur.next;  // 当前节点往后移动一位
				}
			}
			return hair.next;
		}

		public static void main(String[] args) {
			/*ListNode l1 = new ListNode(1);
			ListNode l2 = new ListNode(2);
			ListNode l3_1 = new ListNode(3);
			ListNode l3_2 = new ListNode(3);
			ListNode l3_3 = new ListNode(3);
			ListNode l4_1 = new ListNode(4);
			ListNode l4_2 = new ListNode(4);
			ListNode l5 = new ListNode(5);
			ListNode l5_1 = new ListNode(5);
			l1.next = l2;
			l2.next = l3_1;
			l3_1.next = l3_2;
			l3_2.next = l3_3;
			l3_3.next = l4_1;
			l4_1.next = l4_2;
			l4_2.next = l5;
			l5.next = l5_1;*/

			ListNode l1 = new ListNode(1);
			ListNode l1_1 = new ListNode(1);
			ListNode l1_2 = new ListNode(1);
			ListNode l2 = new ListNode(2);
			ListNode l3 = new ListNode(3);
			ListNode l3_1 = new ListNode(3);
			l1.next = l1_1;
			l1_1.next = l1_2;
			l1_2.next = l2;
			l2.next = l3;
			l3.next = l3_1;
			ListNode node = deleteDuplicatesOfficial(l1);

			System.out.println(node);
		}
	}

	/**
	 * 61. 旋转链表
	 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
	 * 示例 1：
	 * 输入：head = [1,2,3,4,5], k = 2
	 * 输出：[4,5,1,2,3]
	 * 示例 2：
	 * 输入：head = [0,1,2], k = 4
	 * 输出：[2,0,1]
	 * 提示：
	 * 链表中节点的数目在范围 [0, 500] 内
	 * -100 <= Node.val <= 100
	 * 0 <= k <= 2 * 109
	 */
	public static class RotateRight {

		/**
		 * 个人思路:
		 * 先要遍历出链表的长度,然后根据k的值,判断需要将最后的k个节点移动到链表的最前面
		 * 这样需要遍历两次链表
		 *
		 * @param head
		 * @param k
		 * @return
		 */
		public static ListNode rotateRight(ListNode head, int k) {
			if (head == null || k == 0 || head.next == null) return head;
			ListNode cur = head;
			int l = 0;
			while (cur.next != null) {
				l++;
				cur = cur.next;
			}
			ListNode tailNode = cur;
			k = k % (l + 1);
			if (k == 0) return head;
			ListNode cur1 = head;
			int i = l - k;
			while (i > 0) {
				cur1 = cur1.next;
				i--;
			}
			// 找到新的头节点
			ListNode newHead = cur1.next;
			cur1.next = null;
			// 重新连接链表
			tailNode.next = head;
			return newHead;
		}

		public static void main(String[] args) {
			ListNode l1 = new ListNode(1);
			ListNode l2 = new ListNode(2);
			ListNode l3 = new ListNode(3);
			ListNode l4 = new ListNode(4);
			ListNode l5 = new ListNode(5);
			l1.next = l2;
		/*	l2.next = l3;
			l3.next = l4;
			l4.next = l5;*/
			ListNode node = rotateRight(l1, 2);
			System.out.println();
		}

	}

	/**
	 * 146. LRU 缓存
	 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
	 * 实现 LRUCache 类：
	 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
	 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
	 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
	 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
	 * 示例：
	 * 输入
	 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
	 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
	 * 输出
	 * [null, null, null, 1, null, -1, null, -1, 3, 4]
	 * 解释
	 * LRUCache lRUCache = new LRUCache(2);
	 * lRUCache.put(1, 1); // 缓存是 {1=1}
	 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
	 * lRUCache.get(1);    // 返回 1
	 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
	 * lRUCache.get(2);    // 返回 -1 (未找到)
	 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
	 * lRUCache.get(1);    // 返回 -1 (未找到)
	 * lRUCache.get(3);    // 返回 3
	 * lRUCache.get(4);    // 返回 4
	 * 提示：
	 * 1 <= capacity <= 3000
	 * 0 <= key <= 10000
	 * 0 <= value <= 105
	 * 最多调用 2 * 105 次 get 和 put
	 */
	public static class LRUCache {

		/**
		 * 本题需要一个HashMap 和双向链表来实现 ;HashMap实现get和put的O(1)
		 * 双向链表实现缓存淘汰机制
		 */
		static class LinkedNode {
			int key;
			int val;
			LinkedNode next;
			LinkedNode prev;

			public LinkedNode() {
			}

			public LinkedNode(int key, int val) {
				this.key = key;
				this.val = val;
			}
		}

		private final Map<Integer, LinkedNode> map = new HashMap<>();
		private int size;
		private final int capacity;
		private final LinkedNode head, tail;  // 头、尾指针


		public LRUCache(int capacity) {
			this.capacity = capacity;
			this.size = 0;
			// 初始化头尾指针,伪指针标记界限
			this.head = new LinkedNode();
			this.tail = new LinkedNode();
			this.head.next = this.tail;
			this.tail.prev = this.head;
		}

		public int get(int key) {
			LinkedNode node = map.get(key);
			if (node == null) return -1;
			// get操作也会使指定缓存值移动到最前面
			node.prev.next = node.next;
			node.next.prev=node.prev;
			node.next = head.next;
			head.next.prev=node;
			head.next = node;
			node.prev=head;
			return node.val;
		}

		public void put(int key, int value) {
			LinkedNode node;
			if (map.containsKey(key)) {  // map中存在该key,则节点需要移动到最前面
				node = map.get(key);
				node.val = value;
				node.prev.next = node.next;
				node.next.prev = node.prev;
				node.next = head.next;
				head.next.prev = node;
				head.next = node;
				node.prev = head;
			} else { // map中不存在该key,直接插到头部
				node = new LinkedNode(key, value);
				node.next = head.next;
				head.next.prev = node;
				head.next = node;
				node.prev = head;
				size++;
				// 超出链表的容量时,同时删除链表尾部节点
				if (size > capacity) {
					map.remove(tail.prev.key);
					tail.prev.prev.next = tail;
					tail.prev = tail.prev.prev;
					size--;
				}
			}
			this.map.put(key, node);
		}

		public static void main(String[] args) {
			LRUCache cache = new LRUCache(2);
			cache.put(1, 1);
			cache.put(2, 2);
			System.out.println(cache.get(1));
			cache.put(3, 3);
			System.out.println(cache.get(2));
			cache.put(4, 4);
			System.out.println(cache.get(1));
			System.out.println(cache.get(3));
			System.out.println(cache.get(4));
			System.out.println();
		}
	}

	/**
	 * 234. 回文链表
	 * 给你一个单链表的头节点 head ，请你判断该链表是否为
	 * 回文链表
	 * 。如果是，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：head = [1,2,2,1]
	 * 输出：true
	 * 示例 2：
	 * 输入：head = [1,2]
	 * 输出：false
	 * 提示：
	 * 链表中节点数目在范围[1, 105] 内
	 * 0 <= Node.val <= 9
	 * 进阶：你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
	 */
	public static class IsPalindrome {

		/**
		 * 转换成数组,然后判断数组是否为回文数组
		 *
		 * @param head
		 * @return
		 */
		public static boolean isPalindrome(ListNode head) {
			if (head == null) return true;
			List<Integer> list = new ArrayList<>();
			while (head != null) {
				list.add(head.val);
				head = head.next;
			}
			for (int i = 0; i < list.size() / 2; i++) {
				if (!Objects.equals(list.get(i), list.get(list.size() - i - 1))) {
					return false;
				}
			}
			return true;
		}

		/**
		 * 可以找到找到链表的中心点;反转前部分链表,然后分别遍历前后两部分链表
		 *
		 * @param head
		 * @return
		 */
		public static boolean isPalindromeI(ListNode head) {
			if (head == null) return true;
			int count = 0;
			ListNode node = head;
			while (node != null) {
				count++;
				node = node.next;
			}
			if (count == 1) return true;
			if (count == 2) return head.val == head.next.val;
			if (count == 3) return head.val == head.next.next.val;
			int mid = count / 2;
			boolean odd = count % 2 != 0;
			ListNode pre = head, cur = head.next, next = null;
			pre.next = null; // 头指针指向null
			while (mid - 1 > 0) {  // 反正中间位置的链表
				next = cur.next;
				cur.next = pre;
				pre = cur;
				cur = next;
				mid--;
			}
			ListNode left = pre, right = odd ? next.next : next;
			while (left != null) {
				if (left.val != right.val) {
					return false;
				}
				left = left.next;
				right = right.next;
			}
			return true;
		}

		public static void main(String[] args) {
			ListNode head = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(3);
/*			ListNode node4 = new ListNode(4);
			ListNode node3_1 = new ListNode(3);
			ListNode node2_1 = new ListNode(2);
			ListNode node1_1 = new ListNode(1);*/
			head.next = node2;
			node2.next = node3;
		/*	node3.next = node4;
			node4.next = node3_1;
			node3_1.next = node2_1;
			node2_1.next = node1_1;*/
			System.out.println(isPalindromeI(head));
		}
	}

	/**
	 * 147. 对链表进行插入排序
	 * 给定单个链表的头 head ，使用 插入排序 对链表进行排序，并返回 排序后链表的头 。
	 * 插入排序 算法的步骤:
	 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
	 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
	 * 重复直到所有输入数据插入完为止。
	 * 下面是插入排序算法的一个图形示例。部分排序的列表(黑色)最初只包含列表中的第一个元素。每次迭代时，从输入数据中删除一个元素(红色)，并就地插入已排序的列表中。
	 * 对链表进行插入排序。
	 * 示例 1：
	 * 输入: head = [4,2,1,3]
	 * 输出: [1,2,3,4]
	 * 示例 2：
	 * 输入: head = [-1,5,3,4,0]
	 * 输出: [-1,0,3,4,5]
	 * 提示：
	 * 列表中的节点数在 [1, 5000]范围内
	 * -5000 <= Node.val <= 5000
	 */
	public static class InsertionSortList {

		/**
		 * 插入排序的思路,分为已排区和未排区;每次从未排区拿出一个元素,与已排区元素逐个比较,找到放入的位置进行插入操作
		 *
		 * @param head
		 * @return
		 */
		public static ListNode insertionSortList(ListNode head) {
			if (head == null || head.next == null) return head;
			ListNode sortHead = head, unSortHead = head.next;
			sortHead.next = null;
			while (unSortHead != null) {  // 未排区头节点不为null
				ListNode sortNode = sortHead, sortPre = null, unSortNex = unSortHead.next;
				unSortHead.next = null; // 未排区头节点从未排区删除
				boolean flag = false;
				while (sortNode != null) {
					if (unSortHead.val < sortNode.val) {  // 未排区头节点插入到sortNode的前面
						flag = true;
						if (sortPre == null) {
							unSortHead.next = sortHead;
							sortHead = unSortHead;
						} else {
							sortPre.next = unSortHead;  // 插入中间
							unSortHead.next = sortNode;
						}
						break;
					}
					sortPre = sortNode;
					sortNode = sortNode.next;
				}
				if (!flag) sortPre.next = unSortHead;
				unSortHead = unSortNex;

			}
			return sortHead;
		}

		/**
		 * 记录已排序区的最后一个节点,如果当前插入元素大于等于已排区最后一个元素时,直接将其放在最后
		 *
		 * @param head
		 * @return
		 */
		public static ListNode insertionSortListI(ListNode head) {
			if (head == null) return head;
			ListNode sortHead = head, sortTail = head, unSortHead = head.next;
			sortHead.next = null;
			while (unSortHead != null) {  // 未排区头节点不为null
				ListNode sortNode = sortHead, sortPre = null, unSortNex = unSortHead.next;
				unSortHead.next = null; // 未排区头节点从未排区删除
				if (sortTail.val <= unSortHead.val) { // 未排区头节点值大于等于已排区最后一个节点值时,应该放在末尾
					sortTail.next = unSortHead;
					sortTail = unSortHead;
				} else {
					while (sortNode != null) {
						if (unSortHead.val < sortNode.val) {  // 未排区头节点插入到sortNode的前面
							if (sortPre == null) {
								unSortHead.next = sortHead;
								sortHead = unSortHead;
							} else {
								sortPre.next = unSortHead;  // 插入中间
								unSortHead.next = sortNode;
							}
							break;
						}
						sortPre = sortNode;
						sortNode = sortNode.next;
					}
				}
				unSortHead = unSortNex;
			}
			return sortHead;
		}

		/**
		 * 官方思路中,已排区最后一个元素lastSorted,还有一个作用,即其next为为排区的第一个元素
		 * 这样代码将会非常简洁
		 *
		 * @param head
		 * @return
		 */
		public static ListNode insertionSortListII(ListNode head) {
			if (head == null) return null;
			ListNode dummyHead = new ListNode(0);
			dummyHead.next = head;
			ListNode lastSorted = head, curr = head.next;
			while (curr != null) {
				if (lastSorted.val <= curr.val) {
					lastSorted = lastSorted.next;
				} else {
					ListNode prev = dummyHead;
					while (prev.next.val <= curr.val) {
						prev = prev.next;
					}
					lastSorted.next = curr.next;
					curr.next = prev.next;
					prev.next = curr;
				}
				curr = lastSorted.next;
			}
			return dummyHead.next;
		}

		public static void main(String[] args) {
			ListNode head = new ListNode(6);
			ListNode n5 = new ListNode(5);
			ListNode n3 = new ListNode(3);
			ListNode n1 = new ListNode(1);
			ListNode n8 = new ListNode(8);
			ListNode n7 = new ListNode(7);
			ListNode n2 = new ListNode(2);
			ListNode n4 = new ListNode(4);
			head.next = n5;
			n5.next = n3;
			n3.next = n1;
			n1.next = n8;
			n8.next = n7;
			n7.next = n2;
			n2.next = n4;
			ListNode node = insertionSortListI(head);
			System.out.println(node);
		}
	}

	/**
	 * 148. 排序链表
	 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
	 * 示例 1：
	 * 输入：head = [4,2,1,3]
	 * 输出：[1,2,3,4]
	 * 示例 2：
	 * 输入：head = [-1,5,3,4,0]
	 * 输出：[-1,0,3,4,5]
	 * 示例 3：
	 * 输入：head = []
	 * 输出：[]
	 * 提示：
	 * 链表中节点的数目在范围 [0, 5 * 104] 内
	 * -105 <= Node.val <= 105
	 * 进阶：你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
	 */
	public static class SortList {

		/**
		 * 归并排序,首先要特别熟悉数组的归并排序思路
		 * 1.先找到链表的中点,通过快慢指针来找
		 * 2.先拆分,后合并
		 *
		 * @param head
		 * @return
		 */
		public static ListNode sortList(ListNode head) {
			return sortList(head, null);
		}

		private static ListNode sortList(ListNode head, ListNode tail) {
			if (head == null) return head;
			if (head.next == tail) {
				head.next = null; // 如果头节点指向尾节点,则所有此时拆分后的链表只有两个节点,拆分后需要断开链表,此时不需要再拆分,递归结束
				return head;
			}
			ListNode fast = head, slow = head;
			while (fast != tail) {
				slow = slow.next;
				fast = fast.next;
				if (fast != tail) {
					fast = fast.next;
				}
			}
			ListNode mid = slow;
			ListNode node1 = sortList(head, mid);
			ListNode node2 = sortList(mid, tail);
			return merge(node1, node2);
		}

		private static ListNode merge(ListNode head1, ListNode head2) {
			ListNode dummyHead = new ListNode(0);
			ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
			while (temp1 != null && temp2 != null) {
				if (temp1.val <= temp2.val) {
					temp.next = temp1;
					temp1 = temp1.next;
				} else {
					temp.next = temp2;
					temp2 = temp2.next;
				}
				temp = temp.next;
			}
			if (temp1 != null) {
				temp.next = temp1;
			} else if (temp2 != null) {
				temp.next = temp2;
			}
			return dummyHead.next;
		}

		/**
		 * 回顾下归并排序
		 * 先拆分,后合并
		 *
		 * @param arr
		 */
		private static void mergeSort(int[] arr) {
			mergeSort(arr, 0, arr.length - 1);
		}

		private static void mergeSort(int[] arr, int p, int r) {
			int q = (r + p) >> 1;
			if (q >= r) return;
			mergeSort(arr, p, q);
			mergeSort(arr, q + 1, r);
			mergeArr(arr, p, q, r);
		}

		/**
		 * 将已经有序的 arr[p,q]和arr[q+1,r] 合并成有序的arr[p,r]
		 * 需要申请临时数据来存放,然后通过比较把元素有序的放入临时数据
		 *
		 * @param arr
		 * @param p
		 * @param q
		 * @param r
		 */
		private static void mergeArr(int[] arr, int p, int q, int r) {
			int i = p, j = q + 1, k = 0;
			int[] temp = new int[r - p + 1];
			while (i <= q && j <= r) {
				if (arr[i] <= arr[j]) {
					temp[k++] = arr[i++];
				} else {
					temp[k++] = arr[j++];
				}
			}
			while (i <= q) {
				temp[k++] = arr[i++];
			}
			while (j <= r) {
				temp[k++] = arr[j++];
			}
			for (int value : temp) {
				arr[p++] = value;
			}
		}

		public static void main(String[] args) {
			/*int[] arr = new int[]{2, 3, 6, 1, 7, 9, 5, 4};
			mergeSort(arr);
			System.out.println(Arrays.toString(arr));*/
			ListNode node2 = new ListNode(2);
			ListNode node3 = new ListNode(3);
			ListNode node6 = new ListNode(6);
			ListNode node1 = new ListNode(1);
			ListNode node7 = new ListNode(7);
			ListNode node9 = new ListNode(9);
			ListNode node5 = new ListNode(5);
			ListNode node4 = new ListNode(4);
			node2.next = node3;
			node3.next = node6;
			node6.next = node1;
			node1.next = node7;
			node7.next = node9;
			node9.next = node5;
			node5.next = node4;
			ListNode node = sortList(node2);
			System.out.println();
		}
	}

	/**
	 * 2095. 删除链表的中间节点
	 * 给你一个链表的头节点 head 。删除 链表的 中间节点 ，并返回修改后的链表的头节点 head 。
	 * 长度为 n 链表的中间节点是从头数起第 ⌊n / 2⌋ 个节点（下标从 0 开始），其中 ⌊x⌋ 表示小于或等于 x 的最大整数。
	 * 对于 n = 1、2、3、4 和 5 的情况，中间节点的下标分别是 0、1、1、2 和 2 。
	 * 示例 1：
	 * 输入：head = [1,3,4,7,1,2,6]
	 * 输出：[1,3,4,1,2,6]
	 * 解释：
	 * 上图表示给出的链表。节点的下标分别标注在每个节点的下方。
	 * 由于 n = 7 ，值为 7 的节点 3 是中间节点，用红色标注。
	 * 返回结果为移除节点后的新链表。
	 * 示例 2：
	 * 输入：head = [1,2,3,4]
	 * 输出：[1,2,4]
	 * 解释：
	 * 上图表示给出的链表。
	 * 对于 n = 4 ，值为 3 的节点 2 是中间节点，用红色标注。
	 * 示例 3：
	 * 输入：head = [2,1]
	 * 输出：[2]
	 * 解释：
	 * 上图表示给出的链表。
	 * 对于 n = 2 ，值为 1 的节点 1 是中间节点，用红色标注。
	 * 值为 2 的节点 0 是移除节点 1 后剩下的唯一一个节点。
	 * 提示：
	 * 链表中节点的数目在范围 [1, 105] 内
	 * 1 <= Node.val <= 105
	 */
	static class DeleteMiddle {

		/**
		 * 思路:
		 * 通过快慢指针找到链表的中点,移除该节点
		 *
		 * @param head
		 * @return
		 */
		public static ListNode deleteMiddle(ListNode head) {
			if (head == null || head.next == null) return null;
			ListNode slow = head, fast = head, slowPrev = null;
			while (fast != null && fast.next != null) {
				slowPrev = slow;
				slow = slow.next;
				fast = fast.next.next;
			}
			slowPrev.next = slow.next;
			return head;
		}

		public static void main(String[] args) {
			ListNode node1 = new ListNode(1);
			ListNode node3 = new ListNode(3);
			ListNode node4 = new ListNode(4);
			ListNode node7 = new ListNode(7);
			ListNode node1_1 = new ListNode(1);
			ListNode node2 = new ListNode(2);
			ListNode node6 = new ListNode(6);
			node1.next = node3;
			node3.next = node4;
			node4.next = node7;
			node7.next = node1_1;
			node1_1.next = node2;
			node2.next = node6;
			ListNode node = deleteMiddle(node1);
			System.out.println();
		}
	}

	/**
	 * 328. 奇偶链表
	 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
	 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
	 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
	 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
	 * 示例 1:
	 * 输入: head = [1,2,3,4,5]
	 * 输出: [1,3,5,2,4]
	 * 示例 2:
	 * 输入: head = [2,1,3,5,6,4,7]
	 * 输出: [2,3,6,7,1,5,4]
	 * 提示:
	 * n ==  链表中的节点数
	 * 0 <= n <= 104
	 * -106 <= Node.val <= 106
	 */
	static class OddEvenList {

		/**
		 * 思路：
		 * 奇偶链表的起始节点分别为:odd even
		 * odd的下一个节点是 even.next;even的下一个节点是新的add节点的next
		 * 所以在拼接奇偶两条链表时,需要一直更新add和even节点
		 *
		 * @param head
		 * @return
		 */
		public static ListNode oddEvenList(ListNode head) {
			if (head == null || head.next == null) return head;
			ListNode odd = head, even = head.next, evenHead = even;
			while (true) {
				if (even != null) {
					odd.next = even.next;
					if (even.next != null) {
						odd = odd.next;
					} else {
						break;
					}
				} else {
					break;
				}
				even.next = odd.next;
				even = even.next;
			}
			odd.next = evenHead;
			return head;
		}

		/**
		 * 官方的while条件是如何做的,因为先拼接奇数链后拼接偶数链
		 * 那么可以对偶数链判断其是否为null和他的下一个是否null,它本身为null,则自身下一步没法拼接;它的next为null,则奇数链下一步没法拼接
		 * 非常的清晰明了
		 *
		 * @param head
		 * @return
		 */
		public static ListNode oddEvenListOfficial(ListNode head) {
			if (head == null || head.next == null) return head;
			ListNode odd = head, even = head.next, evenHead = even;
			while (even != null && even.next != null) {
				odd.next = even.next;
				odd = odd.next;
				even.next = odd.next;
				even = even.next;
			}
			odd.next = evenHead;
			return head;
		}

		public static void main(String[] args) {
			ListNode node2 = new ListNode(2);
			ListNode node1 = new ListNode(1);
			ListNode node3 = new ListNode(3);
			ListNode node5 = new ListNode(5);
			ListNode node6 = new ListNode(6);
			ListNode node4 = new ListNode(4);
			ListNode node7 = new ListNode(7);
			node2.next = node1;
			node1.next = node3;
			node3.next = node5;
			node5.next = node6;
			node6.next = node4;
			node4.next = node7;
			ListNode node = oddEvenList(node2);
			System.out.println();
		}
	}

	/**
	 * 2130. 链表最大孪生和
	 * 在一个大小为 n 且 n 为 偶数 的链表中，对于 0 <= i <= (n / 2) - 1 的 i ，第 i 个节点（下标从 0 开始）的孪生节点为第 (n-1-i) 个节点 。
	 * 比方说，n = 4 那么节点 0 是节点 3 的孪生节点，节点 1 是节点 2 的孪生节点。这是长度为 n = 4 的链表中所有的孪生节点。
	 * 孪生和 定义为一个节点和它孪生节点两者值之和。
	 * 给你一个长度为偶数的链表的头节点 head ，请你返回链表的 最大孪生和 。
	 * 示例 1：
	 * 输入：head = [5,4,2,1]
	 * 输出：6
	 * 解释：
	 * 节点 0 和节点 1 分别是节点 3 和 2 的孪生节点。孪生和都为 6 。
	 * 链表中没有其他孪生节点。
	 * 所以，链表的最大孪生和是 6 。
	 * 示例 2：
	 * 输入：head = [4,2,2,3]
	 * 输出：7
	 * 解释：
	 * 链表中的孪生节点为：
	 * - 节点 0 是节点 3 的孪生节点，孪生和为 4 + 3 = 7 。
	 * - 节点 1 是节点 2 的孪生节点，孪生和为 2 + 2 = 4 。
	 * 所以，最大孪生和为 max(7, 4) = 7 。
	 * 示例 3：
	 * 输入：head = [1,100000]
	 * 输出：100001
	 * 解释：
	 * 链表中只有一对孪生节点，孪生和为 1 + 100000 = 100001 。
	 * 提示：
	 * 链表的节点数目是 [2, 105] 中的 偶数 。
	 * 1 <= Node.val <= 105
	 */
	static class PairSum {

		/**
		 * 思路:
		 * 1.先找到链表的中点,然后将前部分链表反转
		 * 2.从中间开始往后逐步遍历节点,找出最大的孪生和
		 *
		 * @param head
		 * @return
		 */
		public static int pairSum(ListNode head) {
			ListNode slow = head, fast = head, prev = null, next = null;
			while (fast != null && fast.next != null) {
				fast = fast.next.next; // 先记录下一个fast指针,很关键 因为slow在翻转时会断开链表
				next = slow.next; // 记录next
				slow.next = prev;
				prev = slow;
				slow = next;
			}
			ListNode first = prev, second = slow;
			int max = 0;
			while (first != null) {
				max = Math.max(first.val + second.val, max);
				first = first.next;
				second = second.next;
			}
			return max;
		}

		public static void main(String[] args) {
			ListNode node2 = new ListNode(2);
			ListNode node1 = new ListNode(1);
			ListNode node3 = new ListNode(3);
			ListNode node5 = new ListNode(5);
			ListNode node6 = new ListNode(6);
			ListNode node4 = new ListNode(4);
			ListNode node7 = new ListNode(7);
			node2.next = node1;
			node1.next = node3;
			node3.next = node5;
			node5.next = node6;
			node6.next = node4;
			// node4.next = node7;
			System.out.println(pairSum(node2));
		}
	}


}
