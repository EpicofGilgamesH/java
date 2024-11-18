package test.stack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 栈的使用
 * 总结:队列的栈的特性,一个FIFO 另一个 FILO
 * 那么决定了他们的区别,栈按顺序出->入 到另一个栈 会逆序;队列出->入 到自己 会逆序(准确来说不是逆序,是将新加入的元素移动到队头,其他元素序列不变)
 */
public class LeetCodeCase {

	/**
	 * 232. 用栈实现队列
	 * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
	 * <p>
	 * 实现 MyQueue 类：
	 * <p>
	 * void push(int x) 将元素 x 推到队列的末尾
	 * int pop() 从队列的开头移除并返回元素
	 * int peek() 返回队列开头的元素
	 * boolean empty() 如果队列为空，返回 true ；否则，返回 false
	 * 说明：
	 * <p>
	 * 你 只能 使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
	 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：
	 * ["MyQueue", "push", "push", "peek", "pop", "empty"]
	 * [[], [1], [2], [], [], []]
	 * 输出：
	 * [null, null, null, 1, 1, false]
	 * <p>
	 * 解释：
	 * MyQueue myQueue = new MyQueue();
	 * myQueue.push(1); // queue is: [1]
	 * myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
	 * myQueue.peek(); // return 1
	 * myQueue.pop(); // return 1, queue is [2]
	 * myQueue.empty(); // return false
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= x <= 9
	 * 最多调用 100 次 push、pop、peek 和 empty
	 * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）
	 * <p>
	 * 进阶：
	 * <p>
	 * 你能否实现每个操作均摊时间复杂度为 O(1) 的队列？换句话说，执行 n 个操作的总时间复杂度为 O(n) ，即使其中一个操作可能花费较长时间。
	 */
	public static class MyQueue {
		/**
		 * 个人思路:
		 * 题意要求用栈实现队列 队列的特性是:队尾进,队头出 FIFO 栈的特性是:只有一个进出口,先进后出 FILO
		 * java的栈有哪些实现呢? 使用ArrayDeque作为栈来使用
		 * 栈只能提供 push to top, peek/pop from top, size, 和 is empty  这些api
		 * 也就是只能从top去处理元素
		 * 由于栈只能通top获取元素,而队列需要从尾部插入元素,从头部取出元素,所以单一个栈好像不能简单的实现,
		 * 此处设置两个栈来实现,栈A 是 FIFO顺序的;栈B 是 FILO顺序的;
		 * <p>
		 * 想不到该如何处理,遂参考了官方思路,实际上in栈是输入栈,存放输入的元素;out是输出栈,存放输出的元素,且顺序是输入的逆序;
		 * 当需要输出元素时,out栈为空,则把in栈的元素依次弹出放入out栈 就实现了输出的逆序.
		 */

		private final Deque<Integer> is; // 入队列栈-正常顺序栈
		private final Deque<Integer> os; // 出队列栈-倒序栈

		public MyQueue() {
			is = new ArrayDeque<>();
			os = new ArrayDeque<>();
		}

		public void push(int x) {
			is.push(x); // 入栈A
		}

		public int pop() {
			if (os.isEmpty()) {
				while (!is.isEmpty()) {
					os.push(is.pop());
				}
			}
			return os.pop();
		}

		public int peek() {
			if (os.isEmpty()) {
				while (!is.isEmpty()) {
					os.push(is.pop());
				}
			}
			return os.peek();
		}

		public boolean empty() {
			return is.isEmpty() && os.isEmpty();
		}

		public static void main(String[] args) {
			MyQueue queue = new MyQueue();
			queue.push(1);
			queue.push(2);
			queue.push(3);
			queue.push(4);
			queue.push(5);

			queue.peek();
			int pop = queue.pop();
			System.out.println(pop);
			int pop1 = queue.pop();
			System.out.println(pop1);
			int pop2 = queue.pop();
			System.out.println(pop2);
			int pop3 = queue.pop();
			System.out.println(pop3);
			int pop4 = queue.pop();
			System.out.println(pop4);
			int pop5 = queue.pop();
			System.out.println(pop5);
			int pop6 = queue.pop();
			System.out.println(pop6);
		}
	}

	/**
	 * 225. 用队列实现栈
	 * 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
	 * <p>
	 * 实现 MyStack 类：
	 * <p>
	 * void push(int x) 将元素 x 压入栈顶。
	 * int pop() 移除并返回栈顶元素。
	 * int top() 返回栈顶元素。
	 * boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
	 * <p>
	 * 注意：
	 * <p>
	 * 你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
	 * 你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
	 * <p>
	 * 示例：
	 * <p>
	 * 输入：
	 * ["MyStack", "push", "push", "top", "pop", "empty"]
	 * [[], [1], [2], [], [], []]
	 * 输出：
	 * [null, null, null, 2, 2, false]
	 * <p>
	 * 解释：
	 * MyStack myStack = new MyStack();
	 * myStack.push(1);
	 * myStack.push(2);
	 * myStack.top(); // 返回 2
	 * myStack.pop(); // 返回 2
	 * myStack.empty(); // 返回 False
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= x <= 9
	 * 最多调用100 次 push、pop、top 和 empty
	 * 每次调用 pop 和 top 都保证栈不为空
	 * <p>
	 * 进阶：你能否仅用一个队列来实现栈。
	 */
	public static class MyStack {

		private final Deque<Integer> is; // 入队列栈-正常顺序栈
		private final Deque<Integer> os; // 出队列栈-倒序栈

		/**
		 * 同理,栈和队列的存放顺序不一样,解决掉这个问题即可
		 * 队列只能先进先出,按插入的顺序排列的,那怎么才能让它反序的存放到队列中呢?
		 * 这里借助一个队列,每次插入元素,就把已经排序的队列出队然后入队到这个刚插入元素的队列,每次插入元素的队列在插入以前都是空的
		 * <p>
		 * 官方思路是交换队列,每次插入一个元素时,操作完后总会有一个队列是空的,那么把目标队列设置成非空的那个队列
		 */
		public MyStack() {
			is = new ArrayDeque<>();
			os = new ArrayDeque<>();
		}

		public void push(int x) {
			if (is.isEmpty()) {
				is.add(x);
				while (!os.isEmpty()) {
					is.add(os.pollFirst());
				}
			} else {
				os.add(x);
				while (!is.isEmpty()) {
					os.add(is.pollFirst());
				}
			}
		}

		public int pop() {
			if (empty()) {
				throw new RuntimeException("stack is empty.");
			}
			if (!is.isEmpty()) {
				return is.pollFirst();
			} else {
				return os.pollFirst();
			}
		}

		public int top() {
			if (empty()) {
				throw new RuntimeException("stack is empty.");
			}
			if (!is.isEmpty()) {
				return is.getFirst();
			} else {
				return os.getFirst();
			}
		}

		public boolean empty() {
			return is.isEmpty() && os.isEmpty();
		}

		public static void main(String[] args) {
			MyStack myStack = new MyStack();
			myStack.push(1);
			myStack.push(2);
			myStack.push(3);
			myStack.push(4);
			myStack.push(5);

			int pop = myStack.pop();
			System.out.println(pop);
			myStack.push(6);
			int pop1 = myStack.pop();
			System.out.println(pop1);

			int top = myStack.top();
			System.out.println("top:" + top);

			int pop2 = myStack.pop();
			System.out.println(pop2);
			int pop3 = myStack.pop();
			System.out.println(pop3);
			int pop4 = myStack.pop();
			System.out.println(pop4);
			int pop5 = myStack.pop();
			System.out.println(pop5);
			int pop6 = myStack.pop();
			System.out.println(pop6);

		}
	}

	/**
	 * 题目要求仅用一个队列来实现*********
	 * 队列的出入与栈相反,队列出入到另一个队列,其顺序不变,而栈则相反
	 * 而队列如果从头出,后从尾进,则可以把新加入的元素移动到队头,而其他元素顺序不变
	 * 所以栈-> 队列,用双栈很好解决; 而队列 -> 栈 ,用模拟环形队列,却很好解决
	 */
	public static class MyStackII {

		private final Deque<Integer> is; // 入队列栈-正常顺序栈

		public MyStackII() {
			is = new ArrayDeque<>();
		}

		public void push(int x) {
			if (empty()) {
				is.add(x);
			} else {  // 队列出后又入队列,过程中size不变,所以需要判断需要出入的次数
				int s = is.size();
				is.add(x);
				for (int i = 0; i < s; ++i) {
					is.add(is.pollFirst());
				}
				/*while (!empty()) { // 忽略了可以输入相同的元素,通过先等判断无法确定位置
					is.pollFirst();
					is.add(v);
				}*/
			}
		}

		public int pop() {
			if (empty()) {
				throw new RuntimeException("stack is empty.");
			}
			return is.pollFirst();
		}

		public int top() {
			if (empty()) {
				throw new RuntimeException("stack is empty.");
			}
			return is.getFirst();
		}

		public boolean empty() {
			return is.isEmpty();
		}

		public static void main(String[] args) {
			MyStackII myStack = new MyStackII();
			myStack.push(1);
			myStack.push(2);
			myStack.push(3);
			myStack.push(4);
			myStack.push(5);

			int pop = myStack.pop();
			System.out.println(pop);
			myStack.push(6);
			int pop1 = myStack.pop();
			System.out.println(pop1);

			int top = myStack.top();
			System.out.println("top:" + top);

			int pop2 = myStack.pop();
			System.out.println(pop2);
			int pop3 = myStack.pop();
			System.out.println(pop3);
			int pop4 = myStack.pop();
			System.out.println(pop4);
			int pop5 = myStack.pop();
			System.out.println(pop5);
			int pop6 = myStack.pop();
			System.out.println(pop6);
		}
	}

	/**
	 * 20. 有效的括号
	 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
	 * <p>
	 * 有效字符串需满足：
	 * <p>
	 * 左括号必须用相同类型的右括号闭合。
	 * 左括号必须以正确的顺序闭合。
	 * 每个右括号都有一个对应的相同类型的左括号。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：s = "()"
	 * 输出：true
	 * 示例 2：
	 * <p>
	 * 输入：s = "()[]{}"
	 * 输出：true
	 * 示例 3：
	 * <p>
	 * 输入：s = "(]"
	 * 输出：false
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 104
	 * s 仅由括号 '()[]{}' 组成
	 */
	public static class ValidBrackets {

		/**
		 * 个人思路:
		 * 使用栈来入栈左部分,然后遍历到右部分时,进行出栈操作
		 *
		 * @param s
		 * @return
		 */
		public static boolean isValid(String s) {
			Deque<Character> stack = new ArrayDeque<>();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '(' || c == '[' || c == '{') {
					stack.add(c);
				} else {
					if (!stack.isEmpty()) {
						Character c1 = stack.pollLast();
						if ((c != ')' || c1 != '(') && (c != ']' || c1 != '[') && (c != '}' || c1 != '{')) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return stack.isEmpty();
		}

		public static void main(String[] args) {
			boolean valid = isValid("()[]{}())");
			System.out.println(valid);
		}
	}

	/**
	 * 1047. 删除字符串中的所有相邻重复项
	 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
	 * <p>
	 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
	 * <p>
	 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
	 * <p>
	 * 示例：
	 * <p>
	 * 输入："abbaca"
	 * 输出："ca"
	 * 解释：
	 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，
	 * 其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= S.length <= 20000
	 * S 仅由小写英文字母组成。
	 */
	public static class RemoveDuplicates {

		/**
		 * 个人思路:
		 * 首先判断相邻两个元素是否相同是比较容易的,遍历即可,但是相邻元素相同要消掉,然后再进行比较,就比较难处理相邻的元素了
		 * 此时使用栈,然消掉的元素出栈,很方便就实现了下一次相邻元素的位置定位
		 * <p>
		 * 实际上使用双端队列,比栈更方便,相当于包含了队列和栈的功能
		 *
		 * @param s
		 * @return
		 */
		public static String removeDuplicates(String s) {
			Deque<Character> stack = new ArrayDeque<>();
			for (int i = 0; i < s.length(); ++i) {
				if (stack.isEmpty()) {
					stack.add(s.charAt(i));
				} else {
					if (stack.getLast() == s.charAt(i)) { // 相邻相同
						stack.pollLast();
					} else {
						stack.add(s.charAt(i));
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			while (!stack.isEmpty()) {
				sb.insert(0, stack.pollLast());
			}
			return sb.toString();
		}

		/**
		 * 个人思路:
		 * 使用双指针
		 * 正常情况下 p,q指针相邻,消掉的情况下p前移,q后移;当p<0或者不能消掉时,p=q,q=q+1;当q > length时完成
		 * 用这种方式没法记住消息了哪些元素?????,通过一个List去存放要删除的元素,然后最后进行元素的删除
		 * <p>
		 * 这样的思路太过复杂,并且还有很多场景无法考虑到
		 *
		 * @param s
		 * @return
		 */
		public static String removeDuplicatesII(String s) {
			List<Integer> list = new ArrayList<>();
			int p = 0, q = 1;
			while (q < s.length()) {
				if (p < 0 || list.contains(p)) {
					p = q;
					q++;
					if (q >= s.length()) {
						break;
					}
				}
				if (s.charAt(p) != s.charAt(q)) {
					if (p + 1 != q) {
						p = q;
					} else {
						p++;
					}
				} else {
					list.add(p);
					list.add(q);
					p--;
				}
				q++;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); ++i) {
				if (!list.contains(i)) {
					sb.append(s.charAt(i));
				}
			}
			return sb.toString();
		}

		/**
		 * 首先字符串本来就可以模拟栈,有出入栈的api
		 * 其次,LeetCode 评论区有人用了字符串原地模拟栈的方式来解决本题
		 * 使用一个值 p 记录当前操作入栈的下标;然后遍历字符串,如果不相同则把当前字符放入到p的位置,p++;如果相同则入栈的位置p--
		 * 最后得到的字符串,前p位反转过来就是需要的结果
		 *
		 * @param s
		 * @return
		 */
		public static String removeDuplicatesIII(String s) {
			int p = 0;
			char[] bytes = s.toCharArray();
			for (int i = 0; i < bytes.length; ++i) {
				if (p == 0 || bytes[i] != bytes[p - 1]) {
					bytes[p++] = bytes[i];
				} else {
					p--;
				}
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < p; i++) {
				sb.append(bytes[i]);
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			String abbaca = removeDuplicates("abbaca");
			System.out.println(abbaca);

			String abbaca1 = removeDuplicatesII("aaaaaaaaa");
			System.out.println(abbaca1);

			String abbaca2 = removeDuplicatesIII("abbaca");
			System.out.println(abbaca2);
		}
	}

	/**
	 * 150. 逆波兰表达式求值
	 * <p>
	 * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
	 * <p>
	 * 请你计算该表达式。返回一个表示表达式值的整数。
	 * <p>
	 * 注意：
	 * <p>
	 * 有效的算符为 '+'、'-'、'*' 和 '/' 。
	 * 每个操作数（运算对象）都可以是一个整数或者另一个表达式。
	 * 两个整数之间的除法总是 向零截断 。
	 * 表达式中不含除零运算。
	 * 输入是一个根据逆波兰表示法表示的算术表达式。
	 * 答案及所有中间计算结果可以用 32 位 整数表示。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：tokens = ["2","1","+","3","*"]
	 * 输出：9
	 * 解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
	 * 示例 2：
	 * <p>
	 * 输入：tokens = ["4","13","5","/","+"]
	 * 输出：6
	 * 解释：该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
	 * 示例 3：
	 * <p>
	 * 输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
	 * 输出：22
	 * 解释：该算式转化为常见的中缀算术表达式为：
	 * ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
	 * = ((10 * (6 / (12 * -11))) + 17) + 5
	 * = ((10 * (6 / -132)) + 17) + 5
	 * = ((10 * 0) + 17) + 5
	 * = (0 + 17) + 5
	 * = 17 + 5
	 * = 22
	 * 提示：
	 * <p>
	 * 1 <= tokens.length <= 104
	 * tokens[i] 是一个算符（"+"、"-"、"*" 或 "/"），或是在范围 [-200, 200] 内的一个整数
	 * <p>
	 * <p>
	 * 逆波兰表达式：
	 * <p>
	 * 逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。
	 * <p>
	 * 平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
	 * 该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
	 * 逆波兰表达式主要有以下两个优点：
	 * <p>
	 * 去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
	 * 适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中
	 */
	public static class EvalRPN {

		/**
		 * 个人思路,使用栈
		 *
		 * @param tokens
		 * @return
		 */
		public static int evalRPN(String[] tokens) {
			Deque<Integer> stack = new ArrayDeque<>();
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")) {
					int v1 = stack.pollLast();
					int v2 = stack.pollLast();
					switch (tokens[i]) {
						case "+":
							stack.add(v1 + v2);
							break;
						case "-":
							stack.add(v1 - v2);
							break;
						case "*":
							stack.add(v1 * v2);
							break;
						case "/":
							stack.add(v2 / v1);
							break;
					}
				} else {
					stack.add(Integer.valueOf(tokens[i]));
				}
			}
			return stack.pollLast();
		}

		public static void main(String[] args) {
			int i = evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"});
			System.out.println(i);
		}
	}

	/**
	 * 239. 滑动窗口最大值
	 * <p>
	 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。
	 * 滑动窗口每次只向右移动一位。
	 * <p>
	 * 返回 滑动窗口中的最大值 。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
	 * 输出：[3,3,5,5,6,7]
	 * 解释：
	 * 滑动窗口的位置                最大值
	 * ---------------               -----
	 * [1  3  -1] -3  5  3  6  7      3
	 * 1 [3  -1  -3] 5  3  6  7       3
	 * 1  3 [-1  -3  5] 3  6  7       5
	 * 1  3  -1 [-3  5  3] 6  7       5
	 * 1  3  -1  -3 [5  3  6] 7       6
	 * 1  3  -1  -3  5 [3  6  7]      7
	 * 示例 2：
	 * <p>
	 * 输入：nums = [1], k = 1
	 * 输出：[1]
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 105
	 * -104 <= nums[i] <= 104
	 * 1 <= k <= nums.length
	 */
	public static class MaxSlidingWindow {

		/**
		 * 个人思路:
		 * 只需要获取最大值,跟排序还是很大区别的;
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int[] maxSlidingWindow(int[] nums, int k) {
			if (nums.length == 1) {
				return nums;
			}
			if (k == 1) {
				return nums;
			}
			int[] arr = new int[nums.length - k + 1];
			Deque<Integer> queue = new LinkedList<>();
			if (nums[0] > nums[1]) {
				queue.add(nums[1]);
				queue.add(nums[0]);
			} else {
				queue.add(nums[0]);
				queue.add(nums[1]);
			}
			if (nums.length == 2) {
				return new int[]{queue.getLast()};
			}
			for (int i = 2; i < nums.length; i++) {
				if (nums[i] > queue.getLast()) {
					queue.add(nums[i]);
					queue.pollFirst();
				}
				if (i - k >= 0 && nums[i - k] > queue.getFirst()) {
					queue.pollFirst();
					queue.addFirst(nums[i - k]);
				}
				if (i - k + 1 >= 0)
					arr[i - k + 1] = queue.getLast();
			}
			return arr;
		}

		/**
		 * 优先级队列应该比较容易实现,但是双端队列的思路,只能通过官方的解释正向理解,自己思考完全没有那个理解 xd
		 * <p>
		 * 思路详解:
		 * 1.用队列存放滑动窗口中元素的下标,但是这些下标的元素在队列中应递减排列,但下标值应递增;这样就可以从队尾找到最大值,则滑动窗口时,
		 * 移除掉左边界的元素,并加入右边界的元素. 那么如果做到这样的效果呢?
		 * 2.理论依据,窗口中的[i],[j]两个元素,当i < j 即[i]在[j]的左边;此时如果[i] < [j],那么只要[j]元素存在队列中,[i]就是可以丢弃的.
		 * 所以,一旦入队的元素比队尾大,队尾元素就可以丢弃了
		 * <p>
		 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
		 * 输出: [3,3,5,5,6,7]
		 * 初始状态：L=R=0,队列:{}
		 * i=0,nums[0]=1。队列为空,直接加入。队列：{1}
		 * i=1,nums[1]=3。队尾值为1，3>1，弹出队尾值，加入3。队列：{3}
		 * i=2,nums[2]=-1。队尾值为3，-1<3，直接加入。队列：{3,-1}。此时窗口已经形成，L=0,R=2，result=[3]
		 * i=3,nums[3]=-3。队尾值为-1，-3<-1，直接加入。队列：{3,-1,-3}。队首3对应的下标为1，L=1,R=3，有效。result=[3,3]
		 * i=4,nums[4]=5。队尾值为-3，5>-3，依次弹出后加入。队列：{5}。此时L=2,R=4，有效。result=[3,3,5]
		 * i=5,nums[5]=3。队尾值为5，3<5，直接加入。队列：{5,3}。此时L=3,R=5，有效。result=[3,3,5,5]
		 * i=6,nums[6]=6。队尾值为3，6>3，依次弹出后加入。队列：{6}。此时L=4,R=6，有效。result=[3,3,5,5,6]
		 * i=7,nums[7]=7。队尾值为6，7>6，弹出队尾值后加入。队列：{7}。此时L=5,R=7，有效。result=[3,3,5,5,6,7]
		 * <p>
		 * R=i L=k-i ,队列中的值递减排列,窗口滑动时,判断左边界元素是否还在,即队首的i值是否 < L,进行弹出
		 * 队列中存储元素下标值,方便窗口滑动时的元素移除
		 * 最终队列中元素的效果,元素值从大到小排列,下标值按原序排列
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int[] maxSlidingWindowOfficial(int[] nums, int k) {
			if (nums.length == 1) {
				return nums;
			}
			int[] arr = new int[nums.length - k + 1];
			Deque<Integer> deque = new LinkedList<>();
			for (int i = 0; i < nums.length; ++i) {
				while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
					deque.pollLast();
				}
				deque.addLast(i);
				// 判断滑动窗口是否形成,是否需要移除左边界元素
				if (k - i - 1 <= 0) {
					if (deque.peekFirst() <= i - k) {
						deque.pollFirst();
					}
					arr[i - k + 1] = nums[deque.peekFirst()];
				}
			}
			return arr;
		}

		public static void main(String[] args) {
			int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
			int[] ints = maxSlidingWindowOfficial(nums, 3);
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * 347. 前 K 个高频元素
	 * <p>
	 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
	 * <p>
	 * 示例 1:
	 * <p>
	 * 输入: nums = [1,1,1,2,2,3], k = 2
	 * 输出: [1,2]
	 * 示例 2:
	 * <p>
	 * 输入: nums = [1], k = 1
	 * 输出: [1]
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 105
	 * k 的取值范围是 [1, 数组中不相同的元素的个数]
	 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
	 * <p>
	 * 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
	 */
	public static class TopKFrequent {

		/**
		 * 个人思路:
		 * 典型的TopK问题的变换,最优解有快排求TopK 和 大小顶堆求TopK 时间复杂度都是O(n*Log n)
		 * 当然还有借助HashMap的方式,遍历完后,比较出TopK元素
		 */
		public static int[] topKFrequentByHash(int[] nums, int k) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
			}
			List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
			list.sort((o1, o2) -> o2.getValue() - o1.getValue());
			int[] arr = new int[k];
			for (int i = 0; i < k; i++) {
				arr[i] = list.get(i).getKey();
			}
			return arr;
		}

		/**
		 * 使用大顶堆求TopK 发现之前学的数据结构知识已经忘干净了
		 * 1.堆的定义
		 * 2.堆化的过程
		 * 3.堆排序的过程
		 * <p>
		 * 跌跌撞撞,终于把堆排序的代码手写出来了,为什么之前学得关于堆排序解决TopK、中位数、百分比等问题都忘得非常干净呢?
		 * 最终的原因是,当时解决完全是顺着别人的思路来思考,而并不是自己主动思考到的答案,所以下一次自己在思考时,根本没有思路.
		 */
		public static int[] topKFrequentByPriorityQueue(int[] nums, int k) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
			}
			// 构建小顶堆
			Map.Entry<Integer, Integer>[] arr = new Map.Entry[k + 1];
			int i = 1;
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				if (i <= k) {  // 堆中元素少于k时,继续入堆,从下往上堆化
					arr[i] = entry;
					int j = i;
					while (j / 2 >= 1) {
						if (arr[j].getValue() < arr[j / 2].getValue()) {
							swap(arr, j, j / 2);
						}
						j = j / 2;
					}
				} else { // 堆中已有k个元素,不能继续增加;如果该元素比对顶元素小则丢弃;比对顶元素大,则淘汰对顶元素,从上之下进行一次堆化
					if (entry.getValue() > arr[1].getValue()) {
						arr[1] = entry;
						int j = 1;
						while (true) {
							int minPos = j;
							if (j * 2 <= k && arr[j].getValue() > arr[j * 2].getValue()) {
								minPos = j * 2;
							}
							if (j * 2 + 1 <= k && arr[minPos].getValue() > arr[j * 2 + 1].getValue()) {
								minPos = j * 2 + 1;
							}
							if (minPos == j) break;  // 从上往下堆化和从下往上堆化思路不一样,前者需要比较两个叶子节点,如果都不满足条件则停止
							swap(arr, j, minPos);
							j = minPos;
						}
					}
				}
				i++;
			}
			int[] r = new int[k];
			for (int j = 0; j < k; j++) {
				r[j] = arr[j + 1].getKey();
			}
			return r;
		}

		private static void swap(Map.Entry<Integer, Integer>[] arr, int i, int j) {
			Map.Entry<Integer, Integer> temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		/**
		 * 当然还有快排求TopK的思路
		 * 快排的思路,数组arr[0,r] 设r为pivot点,将大的数放在左边,小的数放在右边
		 * 然后查看放在左边的元素个数n,如果n>=k-1,那么输出左边的k个元素即可
		 * 如果n<k-1,则继续在[p,r]中寻找,重复之前的操作
		 * 先通过新建数组的方式来实现分隔
		 * <p>
		 * 普通的数组移动的方法,都调试了老半天才通过;可见快排的编码细节还是要多注意,并且最后还超出执行时间限制了。。。
		 * 还是借鉴官方的思路吧。。。。
		 */
		public static int[] topKFrequentByFastSort(int[] nums, int k) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
			}
			Map.Entry<Integer, Integer>[] arr = new Map.Entry[map.size()];
			int m = 0;
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				arr[m++] = entry;
			}
			if (arr.length == k) {
				int[] re = new int[k];
				for (int o = 0; o < k; o++) {
					re[o] = arr[o].getKey();
				}
				return re;
			}
			return pivot(arr, k, 0, arr.length - 1);
		}

		public static int[] pivot(Map.Entry<Integer, Integer>[] arr, int k, int p, int r) {
			int pivot = arr[r].getValue();
			Map.Entry<Integer, Integer>[] left = new Map.Entry[r - p + 1];
			Map.Entry<Integer, Integer>[] right = new Map.Entry[r - p + 1];
			int a = 0, b = 0;
			for (int i = p; i < r; i++) {
				if (arr[i].getValue() >= pivot) {
					left[a++] = arr[i];
				} else {
					right[b++] = arr[i];
				}
			}
			// 填充回原arr数组
			int s = p;
			for (int i = 0; i < a; i++) {
				arr[s++] = left[i];
			}
			arr[s++] = arr[r];
			for (int j = 0; j < b; j++) {
				arr[s++] = right[j];
			}
			if (p + a > k - 1 && p <= a - 1) {  // 如果比较出来的左边数组的元素个数 >= k-1 则前k个元素都在pivot的左边
				return pivot(arr, k, p, a - 1);
			} else if (p + a < k - 1 && r >= a + 1) {
				return pivot(arr, k, a + 1, r); // 继续在后面的元素中寻找
			} else {
				int[] re = new int[k];
				for (int o = 0; o < k; o++) {
					re[o] = arr[o].getKey();
				}
				return re;
			}
		}

		/**
		 * 回顾下快排中原地区别分3部分的思路
		 * 想要原地将数组进行分区,则肯定设计到元素的交换;
		 * 1.用i作为已排区位置的索引,j作为遍历元素的索引;
		 * 2.当遍历元素[j] > pivot 时,i,j元素进行交换,那么较大的元素[j]被交换到了已排区;而未排区的元素[i]有两种场景
		 * -- 1) i < j 则i已经遍历过了,但还处理未排区,肯定是较小的元素
		 * -- 2) i=j 正在遍历的元素[j]刚好是未排区的开始元素,直接称为已排区的结束元素
		 * 3.当遍历元素[j] < pivot 时,已排区元素不增加,继续往后遍历
		 * 4.当遍历元素[j]到了队列尾部时,交换i,j;i是未排区的开始,即元素[i]肯定 < pivot,与pivot进行交换,最终得到3部分按序排列-->
		 * 已排区 > pivot > 未排区
		 */
		public static int pivot(int[] arr, int p, int r) {
			int pivot = arr[r];
			int i = p, j = p;
			for (; j < r; ++j) {
				if (arr[j] > pivot) {
					swap(arr, i, j);
					i++;
				}
			}
			swap(arr, i, j);
			return i;
		}

		public static void fastSort(int[] arr) {
			fastSort(arr, 0, arr.length - 1);
		}

		/**
		 * 借此场景,回顾一下快排
		 */
		public static void fastSort(int[] arr, int p, int r) {
			if (p >= r) return;  // 递归的终止条件非常重要,直到p=r时,有序三部分无法拆分变成1,则终止;而由于递归中的区间取值,会出现p>r的情况,同样需要终止
			int pivot = pivot(arr, p, r); // 分成有序三部分
			fastSort(arr, p, pivot - 1);  // 将左部分再次分成有序三部分
			fastSort(arr, pivot + 1, r); // 将右部分再次分成有序三部分
		}

		private static void swap(int[] arr, int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		public static void main(String[] args) {
			// int[] ints = topKFrequentByPriorityQueue(new int[]{-1,1,4,-4,3,5,4,-2,3,-1}, 3);
			// int[] ints = topKFrequentByHash(new int[]{-1, 1, 4, -4, 3, 5, 4, -2, 3, -1}, 3);
			/*int[] ints = topKFrequentByFastSort(new int[]{-1, 1, 4, -4, 3, 5, 4, -2, 3, -1}, 3);
			System.out.println(Arrays.toString(ints));*/
			int[] arr = new int[]{5, 3, 2, 1, 6, 4};
			pivot(arr, 0, arr.length - 1);
			System.out.println(Arrays.toString(arr));

			int[] arr1 = new int[]{5, 3, 2, 1, 6, 4, 0, 9, 7, -1};
			fastSort(arr1);
			System.out.println(Arrays.toString(arr1));
		}
	}

	/**
	 * 由上一题衍生出来的求topK元素
	 * 215. 数组中的第K个最大元素
	 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
	 * <p>
	 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
	 * <p>
	 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
	 * <p>
	 * 示例 1:
	 * <p>
	 * 输入: [3,2,1,5,6,4], k = 2
	 * 输出: 5
	 * 示例 2:
	 * <p>
	 * 输入: [3,2,3,1,2,4,5,5,6], k = 4
	 * 输出: 4
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= k <= nums.length <= 105
	 * -104 <= nums[i] <= 104
	 */
	public static class FindKthLargest {

		/**
		 * top k ,快排的分成3部分的有序区域,当pivot=k-1时,说明pivot就是第k大的元素
		 */
		public static int findKthLargest(int[] nums, int k) {
			return fastSort(nums, k, 0, nums.length - 1);
		}

		public static int fastSort(int[] nums, int k, int p, int r) {
			int pivot = nums[r];
			int i = p, j = p;
			for (; j < r; ++j) {
				if (nums[j] > pivot) {
					swap(nums, i, j);
					i++;
				}
			}
			swap(nums, i, r);
			if (i > k - 1) { // 说明第k大的元素在左边已排区中,需要在已排区中继续分区,找到pivot=k-1
				return fastSort(nums, k, p, i - 1);
			} else if (i < k - 1) { // 说明第k大的元素在右边较小区,需要在右边继续分区,找到pivot=k-1
				return fastSort(nums, k, i + 1, r);
			} else {
				return nums[i];  // 那么,如何确定一定会存在pivot=k-1的场景呢? 当元素总数<k时,根本不存在第k大的元素,则需要递归终止出口
			}
		}


		public static int findKthLargestII(int[] nums, int k) {
			return fastSortII(nums, k, 0, nums.length - 1);
		}

		/**
		 * 防止最后一个元素每次都最小,时间复杂度退化到O(n^2) 加入随机区分点
		 */
		public static int fastSortII(int[] nums, int k, int p, int r) {
			int v = (int) (Math.random() * (r - p + 1)) + p;
			int pivot = nums[v];
			swap(nums, v, r);
			int i = p, j = p;
			for (; j < r; ++j) {
				if (nums[j] > pivot) {
					swap(nums, i, j);
					i++;
				}
			}
			swap(nums, i, r);
			if (i > k - 1) { // 说明第k大的元素在左边已排区中,需要在已排区中继续分区,找到pivot=k-1
				return fastSort(nums, k, p, i - 1);
			} else if (i < k - 1) { // 说明第k大的元素在右边较小区,需要在右边继续分区,找到pivot=k-1
				return fastSort(nums, k, i + 1, r);
			} else {
				return nums[i];  // 那么,如何确定一定会存在pivot=k-1的场景呢? 当元素总数<k时,根本不存在第k大的元素,则需要递归终止出口
			}
		}

		private static void swap(int[] arr, int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		/**
		 * 使用小顶堆来实现,堆的元素个数为k,比堆顶小的元素直接过滤,比堆顶大的元素入堆;适合总数据量大,但是k比较小的场景
		 * 时间复杂度分析:堆化是(k-2)/2 * Log k ;入堆是 (n-k) Log k ;总复杂度应该是n log k ;此时如果k远小于n,复杂度将会接近 n
		 */
		public static int findKthLargestByPriorityQueue(int[] nums, int k) {
			// 拿前k个元素进行堆化,当然也可以使用插入的方式进行堆化;下标[0,k-1]
			// 插入的方式进行堆化 从下至上,子节点与父节点比较;直接堆化的方式,从上至下,所有的非叶子节点倒序堆化,父节点与两个字节点进行比较
			for (int i = (k - 2) / 2; i >= 0; --i) {
				heapify(nums, i, k - 1);
			}

			for (int i = k; i < nums.length; ++i) {
				if (nums[i] > nums[0]) { // 比堆顶元素小,则忽略;比堆顶元素大,替换堆顶,重新堆化;保持小顶堆始终存放得前k大的元素
					nums[0] = nums[i];
					heapify(nums, 0, k - 1);
				}
			}
			// 小顶堆排序,堆顶就是当前元素中最小的,而对的元素个数为k,则堆顶为第k大的元素
			return nums[0];
		}

		/**
		 * 堆化数组
		 *
		 * @param nums 数组
		 * @param s    需要堆化的开始下标
		 * @param r    需要堆化的结束下标
		 */
		public static void heapifyNums(int[] nums, int s, int r) {
			// 从上到下进行堆化,最后一个非叶子节点下标 (r-s)/2
			int k = (r - s) / 2;
			for (int i = k; i >= 0; --i) {
				while (true) {
					int minPos = i;
					if (2 * i + 1 <= r && nums[2 * i + 1] < nums[i]) {
						minPos = 2 * i + 1;
					}
					if (2 * i + 2 <= r && nums[2 * i + 2] < nums[i]) {
						minPos = 2 * i + 2;
					}
					if (minPos == i) break; // k节点比其两个字节点,都小;则该节点已堆化完成,这就是非叶子节点倒序堆化的原因
					swap(nums, i, minPos);
					i = minPos;
				}
			}
		}

		/**
		 * 从上到下堆化操作
		 *
		 * @param nums 数组
		 * @param s    要堆化的元素下标
		 * @param n    堆的最后一个元素下标
		 */
		public static void heapify(int[] nums, int s, int n) {
			while (true) {
				int minPos = s;
				if (2 * s + 1 <= n && nums[2 * s + 1] < nums[minPos]) {
					minPos = 2 * s + 1;
				}
				if (2 * s + 2 <= n && nums[2 * s + 2] < nums[minPos]) {
					minPos = 2 * s + 2;
				}
				if (minPos == s) break; // k节点比其两个字节点,都小;则该节点已堆化完成,这就是非叶子节点倒序堆化的原因
				swap(nums, s, minPos);
				s = minPos;
			}
		}

		public static void main(String[] args) {
			int[] arr = new int[]{3, 2, 1, 5, 6, 4, 7, 8, 23};
			// int kthLargest = findKthLargestII(arr, 3);
			// System.out.println(kthLargest);
			int kthLargestByPriorityQueue = findKthLargestByPriorityQueue(arr, 5);
			System.out.println(kthLargestByPriorityQueue);
		}
	}

	/**
	 * 155.最小栈
	 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
	 * 实现 MinStack 类:
	 * MinStack() 初始化堆栈对象。
	 * void push(int val) 将元素val推入堆栈。
	 * void pop() 删除堆栈顶部的元素。
	 * int top() 获取堆栈顶部的元素。
	 * int getMin() 获取堆栈中的最小元素。
	 * 示例 1:
	 * 输入：
	 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
	 * [[],[-2],[0],[-3],[],[],[],[]]
	 * 输出：
	 * [null,null,null,null,-3,null,0,-2]
	 * 解释：
	 * MinStack minStack = new MinStack();
	 * minStack.push(-2);
	 * minStack.push(0);
	 * minStack.push(-3);
	 * minStack.getMin();   --> 返回 -3.
	 * minStack.pop();
	 * minStack.top();      --> 返回 0.
	 * minStack.getMin();   --> 返回 -2.
	 * 提示：
	 * -231 <= val <= 231 - 1
	 * pop、top 和 getMin 操作总是在 非空栈 上调用
	 * push, pop, top, and getMin最多被调用 3 * 104 次
	 */
	public static class MinStack {

		private List<Integer> arr;

		private LinkedList<Integer> stack;

		/**
		 * 个人思路:
		 * 使用额外的数组来进行排序,然后依次压入栈中.这种做法其实是借助数组排序,未使用到栈本身的特性进行排序
		 * <p>
		 * **************** 题意理解错误了,并不是要把原本的栈变成一个单调栈;原本的栈保持不变,得提供一个api能在O(1)的
		 * **************** 时间复杂度查找到最小值
		 */
		public MinStack() {
			stack = new LinkedList<>();
			arr = new ArrayList<>();
		}

		public void push(int val) {
			// 维护栈内元素从上到下递减
			// 1.如果val>=栈顶元素,直接入栈
			// 2.如果 val<栈顶元素,则依次出栈;当val>=当前栈顶元素时入栈;已出栈的元素按顺序保存在数组中,最后按顺序入栈
			if (stack.isEmpty()) {
				stack.push(val);
				return;
			}
			boolean flag = false;
			while (!stack.isEmpty()) {
				Integer crr = stack.pop();
				arr.add(crr);
				if (val >= crr) {
					arr.set(arr.size() - 1, val);
					arr.add(crr);
					flag = true;
					break;
				}
			}
			if (!flag) arr.add(val);
			for (int i = arr.size() - 1; i >= 0; --i) {
				stack.push(arr.get(i));
			}
			arr.clear();
		}

		public void pop() {
			stack.pollLast();
		}

		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return stack.getLast();
		}
	}

	/**
	 * 栈的规则为FILO 先进后出,元素的弹出有顺序性
	 * 比如压入 2,-1,3,-2时,弹出的顺序为 -2,3,-1,2 所以当弹出-2后,元素肯定还有3,-1,2 这个是可知的
	 * 1.那么弹出-2后栈中最小值也可知为-1
	 * 2.弹出3后后栈中最小值也可知为-1
	 * 3.弹出-1后栈中最小值可知为2
	 * 所以可以维护一个最小值栈,记录原数据栈中弹出一个元素后对应最小值
	 */
	public static class MinStackI {

		private Stack<Integer> stack;
		private Stack<Integer> minStack;

		public MinStackI() {
			stack = new Stack<>();
			minStack = new Stack<>();
			minStack.push(Integer.MAX_VALUE);
		}

		public void push(int val) {
			stack.push(val);
			/*int top = val;
			if (!minStack.isEmpty() && val > minStack.peek()) {
				top = minStack.peek();
			}
			minStack.push(top);*/
			minStack.push(Math.min(val, minStack.peek()));  // 类似于哨兵的作用,让代码简化
		}

		public void pop() {
			stack.pop();
			minStack.pop();
		}

		public int top() {
			return stack.peek();
		}

		public int getMin() {
			return minStack.peek();
		}
	}

	public static void main(String[] args) {
		MinStack minStack = new MinStack();
		minStack.push(-2);
		minStack.push(0);
		minStack.push(-1);
		System.out.println(minStack.getMin());
		minStack.pop();
		System.out.println(minStack.top());
		System.out.println(minStack.getMin());

		MinStackI minStackI = new MinStackI();
		minStackI.push(-2);
		minStackI.push(0);
		minStackI.push(-3);
		System.out.println(minStackI.getMin());
		minStackI.pop();
		System.out.println(minStackI.top());
		System.out.println(minStackI.getMin());
	}

	/**
	 * 394. 字符串解码
	 * 给定一个经过编码的字符串，返回它解码后的字符串。
	 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
	 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
	 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
	 * 示例 1：
	 * 输入：s = "3[a]2[bc]"
	 * 输出："aaabcbc"
	 * 示例 2：
	 * 输入：s = "3[a2[c]]"
	 * 输出："accaccacc"
	 * 示例 3：
	 * 输入：s = "2[abc]3[cd]ef"
	 * 输出："abcabccdcdcdef"
	 * 示例 4：
	 * 输入：s = "abc3[cd]xyz"
	 * 输出："abccdcdcdxyz"
	 * 提示：
	 * 1 <= s.length <= 30
	 * s 由小写英文字母、数字和方括号 '[]' 组成
	 * s 保证是一个 有效 的输入。
	 * s 中所有整数的取值范围为 [1, 300]
	 */
	public static class DecodeString {

		/**
		 * 个人思路:
		 * 示例1、3、4都是顺序操作即可,但是示例2给出了一中情况,[]的输入是嵌套的,类似于符号运算,需要处理最内层的[]
		 * 3[a2[c]] 依次入栈
		 * <p>
		 * c
		 * [
		 * 2
		 * a
		 * [
		 * 3
		 * 当遇到字符']'时,弹出直到字符'[' 构建字符串,并弹出下一个次数字符,连接字符串:cc 然后将得到的字符串cc压入栈中
		 * 遇到第二个字符']'时,同上连接字符串并压入栈中.最后将整个栈正序遍历出来
		 * <p>
		 * 3[a]2[bc]
		 * a
		 * [
		 * 3
		 * 下一个字符为']' : aaa
		 * <p>
		 * c
		 * b
		 * [
		 * 2
		 * 下一个字符为']' : cbcb
		 * <p>
		 * 当然本题也可以用递归的方式求解,涉及到《编译原理》相关内容
		 *
		 * @param s
		 * @return
		 */
		public static String decodeString(String s) {
			Deque<String> stack = new LinkedList<>();
			for (int i = 0; i < s.length(); ++i) {
				if (s.charAt(i) != ']') {
					stack.push(String.valueOf(s.charAt(i)));
				} else {  // 遍历到']',则弹出元素直到遇到'['
					StringBuilder part = new StringBuilder();
					while (!stack.isEmpty() && !stack.peek().equals("[")) {
						part.insert(0, stack.pop());
					}
					stack.pop();
					// 处理数字
					StringBuilder timeStr = new StringBuilder();
					while (!stack.isEmpty() && Character.isDigit(stack.peek().charAt(0))) {
						timeStr.insert(0, stack.pop());
					}
					int times = Integer.parseInt(timeStr.toString());
					String partStr = part.toString();
					for (int j = 0; j < times; ++j) {
						stack.push(partStr);
					}
				}
			}
			StringBuilder result = new StringBuilder();
			while (!stack.isEmpty()) {
				result.insert(0, stack.pop());
			}
			return result.toString();
		}

		public static void main(String[] args) {
			System.out.println(decodeString("3[a]2[bc]"));
		}
	}

	/**
	 * 71. 简化路径
	 * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
	 * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；
	 * 两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
	 * 请注意，返回的 规范路径 必须遵循下述格式：
	 * 始终以斜杠 '/' 开头。
	 * 两个目录名之间必须只有一个斜杠 '/' 。
	 * 最后一个目录名（如果存在）不能 以 '/' 结尾。
	 * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
	 * 返回简化后得到的 规范路径 。
	 * 示例 1：
	 * 输入：path = "/home/"
	 * 输出："/home"
	 * 解释：注意，最后一个目录名后面没有斜杠。
	 * 示例 2：
	 * 输入：path = "/../"
	 * 输出："/"
	 * 解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
	 * 示例 3：
	 * 输入：path = "/home//foo/"
	 * 输出："/home/foo"
	 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
	 * 示例 4：
	 * 输入：path = "/a/./b/../../c/"
	 * 输出："/c"
	 * 提示：
	 * 1 <= path.length <= 3000
	 * path 由英文字母，数字，'.'，'/' 或 '_' 组成。
	 * path 是一个有效的 Unix 风格绝对路径。
	 */
	public static class SimplifyPath {

		/**
		 * 个人思路:
		 * 本题要通过栈来模拟文件夹的层次关系
		 * 1.首先用'/'来进行分割,多余的'/'能直接忽略掉
		 * 2.使用一个栈来模拟文件层次,栈底代表根目录,根目录再往上级目录都是根目录;
		 * 3.遇到非'.' '..'时,都被视为文件名,'.'表示当前目录 '..'表示上级目录
		 *
		 * @param path
		 * @return
		 */
		public static String simplifyPath(String path) {
			Deque<String> stack = new ArrayDeque<>();
			String[] arr = path.split("/");
			for (String s : arr) {
				if (s.equals("..")) {
					if (!stack.isEmpty()) stack.pop();
				} else {
					if (!s.equals(".")) { // 非 '.'和 '..' 都是文件名
						if (!s.isEmpty()) stack.push(s);
					}
				}
			}
			if (stack.isEmpty()) return "/";
			StringBuilder sb = new StringBuilder();
			while (!stack.isEmpty()) {
				sb.append("/").append(stack.pollLast());
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			System.out.println(simplifyPath("/home//foo/"));
		}
	}

	/**
	 * 224. 基本计算器
	 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
	 * 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
	 * 示例 1：
	 * 输入：s = "1 + 1"
	 * 输出：2
	 * 示例 2：
	 * 输入：s = " 2-1 + 2 "
	 * 输出：3
	 * 示例 3：
	 * 输入：s = "(1+(4+5+2)-3)+(6+8)"
	 * 输出：23
	 * 提示：
	 * 1 <= s.length <= 3 * 105
	 * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
	 * s 表示一个有效的表达式
	 * '+' 不能用作一元运算(例如， "+1" 和 "+(2 + 3)" 无效)
	 * '-' 可以用作一元运算(即 "-1" 和 "-(2 + 3)" 是有效的)
	 * 输入中不存在两个连续的操作符
	 * 每个数字和运行的计算将适合于一个有符号的 32位 整数
	 */
	public static class Calculate {

		/**
		 * 个人思路:
		 * 基本计算器,运用栈来匹配括号和括弧之间的运算字符串,实现先计算括号中的运算的方式
		 *
		 * @param s
		 * @return
		 */
		public static int calculate(String s) {
			Deque<String> stack = new ArrayDeque<>();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				// 字符出现的场景如下
				// 1.符号 + -
				// 2.符号( )
				// 3.数字 数字可以有多位,需要合并数字;数字前的运算符号如果是- ,则入栈负数
				if (c != ' ') {
					if (c == '+' || c == '-' || c == '(') {
						stack.push(String.valueOf(c));
					} else if (c == ')') {  // 匹配与之对应的'('
						long num = 0, pre = 0;
						boolean flag = false;
						while (!stack.isEmpty()) {
							String v = stack.pop();
							if (v.equals("+")) {
								num += pre;
								flag = false;
							} else if (v.equals("-")) {
								num -= pre;
								flag = false;
							} else if (v.equals("(")) {
								if (flag) num += pre;
								break;
							} else {
								pre = Long.parseLong(v);
								flag = true;
							}
						}
						stack.push(String.valueOf(num));
					} else {  // 数字,需要判断是否有多位
						long num = 0;
						while (i < s.length() && Character.isDigit(s.charAt(i))) {
							num = num * 10 + s.charAt(i) - '0';
							i++;
						}
						stack.push(String.valueOf(num));
						i--;
					}
				}
			}
			// stack中没有括号,直接处理
			long total = 0, pre = 0;
			boolean flag = false;
			while (!stack.isEmpty()) {
				String v = stack.pop();
				if (v.equals("+")) {
					total += pre;
					flag = false;
				} else if (v.equals("-")) {
					total -= pre;
					flag = false;
				} else {
					pre = Long.parseLong(v);
					flag = true;
				}
			}
			return flag ? (int) (pre + total) : (int) total;
		}

		/**
		 * 官方思路中没有消除括号的过程,因为该元素仅涉及到 + - 的运算,括号中的计算过程 只需要将其符号调整正确即可
		 * 当遇到'('时,其前面是'+' 则所有符号不需要变化;其前面是'-'则所有符号均需要反转
		 * 用一个栈ops专门存放操作符号,每当遇到 '('时,将当前的sign(符号值 取1和-1)压入栈中;每当遇到')'时都从栈中弹出一个符号位;
		 * 所以这个思路是去掉括号,相当于把括号全部散开
		 *
		 * @param s
		 * @return
		 */
		public static int calculateOfficial(String s) {
			Deque<Integer> ops = new LinkedList<Integer>();
			ops.push(1);
			int sign = 1, i = 0, res = 0;
			while (i < s.length()) {
				char c = s.charAt(i);
				if (c == '+') {
					sign = ops.peek();
					i++;
				} else if (c == '-') {
					sign = -ops.peek();
					i++;
				} else if (c == '(') {  // 遇到'('说明()中的所有符号都应该被确定
					ops.push(sign);
					i++;
				} else if (c == ')') {
					ops.pop();
					i++;
				} else if (c == ' ') {
					i++;
				} else {  // 数字
					long num = 0;
					while (i < s.length() && Character.isDigit(s.charAt(i))) {
						num = num * 10 + s.charAt(i) - '0';
						i++;
					}
					res += (int) (sign * num);
				}
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(calculate("(1+(4+5+2)-3)+(6+8)"));
			System.out.println(calculateOfficial("(1+(4+5+2)-3)+(6+8)"));
		}
	}

	/**
	 * 739. 每日温度
	 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
	 * 示例 1:
	 * 输入: temperatures = [73,74,75,71,69,72,76,73]
	 * 输出: [1,1,4,2,1,1,0,0]
	 * 示例 2:
	 * 输入: temperatures = [30,40,50,60]
	 * 输出: [1,1,1,0]
	 * 示例 3:
	 * 输入: temperatures = [30,60,90]
	 * 输出: [1,1,0]
	 * 提示：
	 * 1 <= temperatures.length <= 105
	 * 30 <= temperatures[i] <= 100
	 */
	public static class DailyTemperatures {

		/**
		 * 暴力计算
		 *
		 * @param temperatures
		 * @return
		 */
		public static int[] dailyTemperatures(int[] temperatures) {
			int n = temperatures.length;
			int[] res = new int[n];
			for (int i = 0; i < n; i++) {
				int cur = temperatures[i];
				for (int j = i + 1; j < n; j++) {
					if (temperatures[j] > cur) {
						res[i] = j - i;
						break;
					}
				}
			}
			return res;
		}

		public static int[] dailyTemperaturesI(int[] temperatures) {
			int n = temperatures.length;
			int[] res = new int[n];
			Deque<Integer> deque = new LinkedList<>();
			for (int i = 0; i < n; ++i) {
				while (!deque.isEmpty() && temperatures[i] > temperatures[deque.peek()]) {
					Integer t = deque.peek();
					deque.pop();
					res[t] = i - t;
				}
				deque.push(i);
			}
			return res;
		}

		public static void main(String[] args) {
			int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
			System.out.println(Arrays.toString(dailyTemperaturesI(temperatures)));
		}
	}

	/**
	 * 2390. 从字符串中移除星号
	 * 给你一个包含若干星号 * 的字符串 s 。
	 * 在一步操作中，你可以：
	 * 选中 s 中的一个星号。
	 * 移除星号 左侧 最近的那个 非星号 字符，并移除该星号自身。
	 * 返回移除 所有 星号之后的字符串。
	 * 注意：
	 * 生成的输入保证总是可以执行题面中描述的操作。
	 * 可以证明结果字符串是唯一的。
	 * 示例 1：
	 * 输入：s = "leet**cod*e"
	 * 输出："lecoe"
	 * 解释：从左到右执行移除操作：
	 * - 距离第 1 个星号最近的字符是 "leet**cod*e" 中的 't' ，s 变为 "lee*cod*e" 。
	 * - 距离第 2 个星号最近的字符是 "lee*cod*e" 中的 'e' ，s 变为 "lecod*e" 。
	 * - 距离第 3 个星号最近的字符是 "lecod*e" 中的 'd' ，s 变为 "lecoe" 。
	 * 不存在其他星号，返回 "lecoe" 。
	 * 示例 2：
	 * 输入：s = "erase*****"
	 * 输出：""
	 * 解释：整个字符串都会被移除，所以返回空字符串。
	 * 提示：
	 * 1 <= s.length <= 105
	 * s 由小写英文字母和星号 * 组成
	 * s 可以执行上述操作
	 */
	static class RemoveStars {

		/**
		 * 个人思路:
		 * 如果是字符就是入栈,如果是*就弹出最顶部的字符,直到字符串遍历完成
		 *
		 * @param s
		 * @return
		 */
		public static String removeStars(String s) {
			char[] chars = s.toCharArray();
			Deque<Character> stack = new LinkedList<>();
			for (int i = 0; i < chars.length; ++i) {
				if (chars[i] == '*' && !stack.isEmpty()) {
					stack.pop();
				} else {
					stack.push(chars[i]);
				}
			}
			StringBuilder sb = new StringBuilder();
			while (!stack.isEmpty()) {
				sb.insert(0, stack.pop());
			}
			return sb.toString();
		}

		/**
		 * 不用栈也可以,拼接字符串,遇到'*'就删除最后一个字符,StringBuilder就可以实现
		 *
		 * @param s
		 * @return
		 */
		public static String removeStarsII(String s) {
			StringBuilder sb = new StringBuilder();
			for (char c : s.toCharArray()) {
				if (c == '*' && sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1); // 删除最后一个字符
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}

		/**
		 * 不拼接字符串,通过char数组转成字符串
		 *
		 * @param s
		 * @return
		 */
		public static String removeStarsIII(String s) {
			char[] chars = new char[s.length()];
			int idx = -1;
			for (char c : s.toCharArray()) {
				if (c != '*') {
					chars[++idx] = c;
				} else {
					idx--;               // 当遇到'*'时,覆盖前一个索引
				}
			}
			return new String(chars, 0, idx + 1);
		}

		public static void main(String[] args) {
			System.out.println(removeStars("erase*****"));
		}
	}

	/**
	 * 735. 小行星碰撞
	 * 给定一个整数数组 asteroids，表示在同一行的小行星。
	 * 对于数组中的每一个元素，其绝对值表示小行星的大小，正负表示小行星的移动方向（正表示向右移动，负表示向左移动）。每一颗小行星以相同的速度移动。
	 * 找出碰撞后剩下的所有小行星。碰撞规则：两个小行星相互碰撞，较小的小行星会爆炸。如果两颗小行星大小相同，则两颗小行星都会爆炸。两颗移动方向相同的小行星，永远不会发生碰撞。
	 * 示例 1：
	 * 输入：asteroids = [5,10,-5]
	 * 输出：[5,10]
	 * 解释：10 和 -5 碰撞后只剩下 10 。 5 和 10 永远不会发生碰撞。
	 * 示例 2：
	 * 输入：asteroids = [8,-8]
	 * 输出：[]
	 * 解释：8 和 -8 碰撞后，两者都发生爆炸。
	 * 示例 3：
	 * 输入：asteroids = [10,2,-5]
	 * 输出：[10]
	 * 解释：2 和 -5 发生碰撞后剩下 -5 。10 和 -5 发生碰撞后剩下 10 。
	 * 提示：
	 * 2 <= asteroids.length <= 104
	 * -1000 <= asteroids[i] <= 1000
	 * asteroids[i] != 0
	 */
	static class AsteroidCollision {


		/**
		 *
		 * 个人思路: &nbsp;
		 *
		 * 1.如果栈为空时,入栈下一个元素
		 * 2.如果栈不为空时,判断当前元素与栈顶元素符号是否一致,如果一致直接入栈;
		 * * 如果不一致,比较绝对值->栈顶元素绝对值小,则出栈,后重复2的操作;此处存在循环;
		 * *                    ->当前元素绝对值小,则当前元素删除,继续下一个元素
		 * * 如果一致,入栈
		 * <p>
		 * *******************
		 * 没有考虑负数向左,正数向右的问题,负数在左边,正数在右边,就不可能遇到
		 *
		 * @param asteroids
		 * @return
		 */
		public static int[] asteroidCollision(int[] asteroids) {
			Deque<Integer> stack = new LinkedList<>();
			for (int i = 0; i < asteroids.length; ++i) {
				int curr = asteroids[i];
				if (stack.isEmpty()) {
					stack.push(curr);
				} else { // 栈顶存在元素
					while (true) {
						int peek = stack.peek();
						if (curr < 0 && peek > 0) { // 判断符号是否一致,正向右 负向左
							if (-curr < peek) {  // 栈顶元素大
								break;
							} else if (-curr > peek) { // 当前元素大,栈顶元素出栈
								stack.pop();
								if (stack.isEmpty()) {
									stack.push(curr);
									break;
								}
							} else {  // 相等,栈顶出栈并进行下一步
								stack.pop();
								break;
							}
						} else {
							stack.push(curr);
							break;
						}
					}
				}
			}
			int[] res = new int[stack.size()];
			int idx = res.length - 1;
			while (!stack.isEmpty()) {
				res[idx] = stack.pop();
				idx--;
			}
			return res;
		}

		/**
		 * 官方思路：
		 * 遍历行星数组,当前行星为aster,使用变量alive记录行星aster是否会存在(即不会爆炸)
		 * 当行星aster存在且aster<0(向左),栈顶元素top非空且>0(向右),说明两个行星对向移动:
		 * 1.如果top > -aster 则行星爆炸,alive=false;
		 * 2.如果top < -aster 则栈顶行星爆炸,top出栈,重复以上操作,直到不满足条件
		 * 如果alive=true,说明行星需要入栈
		 * *******************************************************
		 * 官方思路非常清晰简洁,如何才能想到如此清晰简洁的思路呢??
		 *
		 * @param asteroids
		 * @return
		 */
		public static int[] asteroidCollisionOfficial(int[] asteroids) {
			Deque<Integer> stack = new LinkedList<>();
			for (int i = 0; i < asteroids.length; ++i) {
				boolean alive = true;
				int aster = asteroids[i];
				while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0) {  // 行星没有爆炸则需要一直和栈顶元素进行比较
					int top;
					alive = (top = stack.peek()) < -aster;
					if (top <= -aster) {  // top小于和等于-aster时都会爆炸,删除栈顶元素
						stack.pop();
					}
				}
				if (alive) {
					stack.push(aster);
				}
			}
			int[] res = new int[stack.size()];
			int idx = res.length - 1;
			while (!stack.isEmpty()) {
				res[idx] = stack.pop();
				idx--;
			}
			return res;
		}

		public static void main(String[] args) {
			int[] ints = asteroidCollisionOfficial(new int[]{10, 2, -5});
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * 901. 股票价格跨度
	 * 设计一个算法收集某些股票的每日报价，并返回该股票当日价格的 跨度 。
	 * 当日股票价格的 跨度 被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
	 * 例如，如果未来 7 天股票的价格是 [100,80,60,70,60,75,85]，那么股票跨度将是 [1,1,1,2,1,4,6] 。
	 * 实现 StockSpanner 类：
	 * StockSpanner() 初始化类对象。
	 * int next(int price) 给出今天的股价 price ，返回该股票当日价格的 跨度 。
	 * 示例：
	 * 输入：
	 * ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
	 * [[], [100], [80], [60], [70], [60], [75], [85]]
	 * 输出：
	 * [null, 1, 1, 1, 2, 1, 4, 6]
	 * 解释：
	 * StockSpanner stockSpanner = new StockSpanner();
	 * stockSpanner.next(100); // 返回 1
	 * stockSpanner.next(80);  // 返回 1
	 * stockSpanner.next(60);  // 返回 1
	 * stockSpanner.next(70);  // 返回 2
	 * stockSpanner.next(60);  // 返回 1
	 * stockSpanner.next(75);  // 返回 4 ，因为截至今天的最后 4 个股价 (包括今天的股价 75) 都小于或等于今天的股价。
	 * stockSpanner.next(85);  // 返回 6
	 * 提示：
	 * 1 <= price <= 105
	 * 最多调用 next 方法 104 次
	 */
	static class StockSpanner {

		/**
		 * 首先要使用单调栈,单调栈的作用是保证栈中的元素按一定的顺序,这样给出一个数v,找出比他小的所有元素,就很容易
		 * 但是栈中的元素没有按顺序的保存所有元素了,那如何能求出一个数其相邻的前面的数比其小的个数呢?
		 * 此时需要在栈元素中保存每个数的序列,这样就能通过序列号计算出结果.
		 * ---------------------------------------------------------------------------------------------
		 * 单调栈操作步骤
		 * |60 |   70
		 * |80 |
		 * |100|
		 * --------------------
		 * 此时需要计算元素70的前面相邻连续比它小的数的个数,由于栈顶元素60<70,60出栈,用元素70的idx减去此时栈顶元素80的idx即为所求
		 * 然后元素70入栈,以上一直保证栈中元素单调递减.
		 * 那么如何证明元素60出栈,不会引起其后面元素的结果计算呢?
		 * 此时单调栈情况:
		 * |70 |   x
		 * |80 |
		 * |100|
		 * 如果现在有元素x需要计算结果,如果x < 70,那么所求结果等于1,栈无操作;
		 * 如果x >= 70 ,那么x > 60必然成立,所有结果用不到元素60的idx,此时元素70出栈,会用比元素70更大的元素的idx进行结果求解
		 */
		public StockSpanner() {
			stack = new ArrayDeque<>();
			stack.push(new int[]{-1, Integer.MAX_VALUE});
			idx = -1;
		}

		private Deque<int[]> stack;
		private int idx;

		public int next(int price) {
			idx++;
			while (price >= stack.peek()[1]) {
				stack.pop();
			}
			int res = idx - stack.peek()[0];
			stack.push(new int[]{idx, price});
			return res;
		}

		public static void main(String[] args) {
			StockSpanner stockSpanner = new StockSpanner();
			System.out.println(stockSpanner.next(100));
			System.out.println(stockSpanner.next(80));
			System.out.println(stockSpanner.next(60));
			System.out.println(stockSpanner.next(70));
			System.out.println(stockSpanner.next(60));
			System.out.println(stockSpanner.next(75));
			System.out.println(stockSpanner.next(85));

		}
	}
}











