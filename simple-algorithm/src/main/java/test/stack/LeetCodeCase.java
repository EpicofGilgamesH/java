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
}
