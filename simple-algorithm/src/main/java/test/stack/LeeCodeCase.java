package test.stack;

import java.util.*;

/**
 * @Description LeeCode 案例
 * @Date 2022-08-09 16:57
 * @Created by wangjie
 */
public class LeeCodeCase {

	// 20.有效的括号
	// 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
	// 有效字符串需满足：
	// 左括号必须用相同类型的右括号闭合。
	// 左括号必须以正确的顺序闭合。
	public static boolean valid(String s) {
		//'关闭'符号只用来弹出'打开'符号;打开符号一定是要压入到栈的,所以这样的思路就清晰明了
		Map<Character, Character> map = new HashMap<>();
		map.put(')', '(');
		map.put('}', '{');
		map.put(']', '[');

		Deque<Character> stack = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if (map.containsKey(c)) {
				//与栈顶数据对比,相同就出栈,不同就入栈
				if (stack.isEmpty() || map.get(c) != stack.peek()) {
					return false;
				}
				stack.pop();
			} else
				stack.push(c);
		}
		return stack.isEmpty();
	}

	//32.最长有效括号
	//给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。

	/**
	 * 审题要清晰,最长有效括号,是指连续匹配 '()' 成功的长度,那么问题的关键就是 当出现第一个匹配不到的 ')'时,
	 * 将其存入栈底,然后计算其后续能匹配的最大长度
	 * 1.如果是 '(' 则入栈
	 * 2.如果是 ')' 则先出栈,然后看栈是否为空,如果为空则说明此时的 ')'匹配不到,则压入栈底
	 * 3.栈底需要定义一个哨兵 -1,表示初始化时,第一个匹配不到的 ')'位置序列为 -1
	 * 4.计算最长有效括号,每次匹配到有效 ')'时,l=i-栈顶元素值:通俗理解是 一个 ')'消掉一个 '('
	 * 最后消掉的总长度即为结果
	 *
	 * @param s
	 * @return
	 */
	public static int longestValidParentheses(String s) {
		Character open = '(', close = ')';
		Deque<Integer> stack = new LinkedList<>();
		//初始化栈,添加一个哨兵
		stack.push(-1);
		int max = 0;
		for (int i = 0; i < s.length(); ++i) {
			Character c;
			if ((c = s.charAt(i)) == open)
				stack.push(i);
			else if (c == close) {
				stack.pop();
				if (stack.isEmpty()) {
					stack.push(i);
				} else {
					max = Math.max(max, i - stack.peek());
				}
			}
		}
		return max;
	}

	/**
	 * 动态规划思路解题
	 * dp状态数组,其中 '('肯定都为0
	 * 其中'('则计算其最长有效
	 * 1.当s[i]=')' 且s[i-1] = '(' 也就是刚好与前一个进行匹配,则dp[i]= dp[i-2]+2
	 * 即与其匹配到的 '('的前一个的dp值+2
	 * 2.当s[i]=')' 且s[i-1] = ')' 也就是 '))'的情况,的找到与i-1匹配的 '(' index= i-dp[i-1]-1 ;
	 * 其中i-dp[i-1] 就是与i位置')'匹配的'('的位置 那么匹配的前一个就是 i-dp[i-1]-1;
	 * 2-1.如果s[i-dp[i-1]-1]= '(' 则说明找到了与i位置匹配的'(',那么dp[i]=dp[i-1]+dp[i-dp[i-1]-2]+2;
	 * 即要加上 i-i处的dp值+2,还要加上前面已匹配的dp值
	 * 2-2.如果s[i-dp[i-1]-1]= ')' 则说明i位置没有匹配的')',那么dp[i]=0
	 * 找出dp数组中最大的值,即为最长有效括回匹配
	 */
	public static int longestValidParentheses_dp(String s) {
		char open = '(', close = ')';
		int max = 0;
		int[] dp = new int[s.length()];
		for (int i = 1; i < s.length(); ++i) {
			char p;
			if (s.charAt(i) == close) {
				if ((p = s.charAt(i - 1)) == open) {
					dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2; //预防超出界限
				} else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == open) {  // '))'
					dp[i] = dp[i - 1] + ((i - dp[i - 1] - 2) >= 0 ? dp[i - dp[i - 1] - 2] : 0) + 2; //预防超出界限
				}
				max = Math.max(max, dp[i]);
			}
		}
		return max;
	}

	//150. 逆波兰表达式求值
	//根据 逆波兰表示法，求表达式的值。
	//有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
	//注意 两个整数之间的除法只保留整数部分。
	//可以保证给定的逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。

	/**
	 * 逆波兰表达式：
	 * <p>
	 * 逆波兰表达式是一种后缀表达式，所谓后缀就是指算符写在后面。
	 * 平常使用的算式则是一种中缀表达式，如 ( 1 + 2 ) * ( 3 + 4 ) 。
	 * 该算式的逆波兰表达式写法为 ( ( 1 2 + ) ( 3 4 + ) * ) 。
	 * 逆波兰表达式主要有以下两个优点：
	 * 去掉括号后表达式无歧义，上式即便写成 1 2 + 3 4 + * 也可以依据次序计算出正确结果。
	 * 适合用栈操作运算：遇到数字则入栈；遇到算符则取出栈顶两个数字进行计算，并将结果压入栈中
	 *
	 * @param tokens
	 * @return
	 */
	public static int evalRPN(String[] tokens) {
		List<String> operates = Arrays.asList("+", "-", "*", "/");
		Deque<Integer> stack = new LinkedList<>();
		for (int i = 0; i < tokens.length; ++i) {
			String s = tokens[i];
			if (!operates.contains(s)) { //不是运算符,则入栈
				stack.push(Integer.valueOf(s));
			} else { //运算符,弹出两个元素,进行运算
				Integer pop1 = stack.pop();
				Integer pop2 = stack.pop();
				switch (s) {
					case "+":
						stack.push(pop2 + pop1);
						break;
					case "-":
						stack.push(pop2 - pop1);
						break;
					case "*":
						stack.push(pop2 * pop1);
						break;
					case "/":
						stack.push(pop2 / pop1);
						break;
				}
			}
		}
		return stack.pop();
	}


	public static void main(String[] args) {
		String s = "{[]}";
		boolean valid = valid(s);
		System.out.println("");

		String s1 = ")(()";
		//int i = longestValidParentheses(s1);
		int i = longestValidParentheses_dp(s1);
		System.out.println(i);
		String[] strings = new String[]{"4", "13", "5", "/", "+"};
		String[] strings1 = new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
		int e1 = evalRPN(strings1);
		System.out.println(e1);
	}
}
