package test.stack;

import java.util.*;

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
}
