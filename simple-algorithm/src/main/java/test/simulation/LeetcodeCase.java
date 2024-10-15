package test.simulation;

import java.util.*;

/**
 * 模拟类
 */
public class LeetcodeCase {

	/**
	 * 415. 字符串相加
	 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
	 * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
	 * 示例 1：
	 * 输入：num1 = "11", num2 = "123"
	 * 输出："134"
	 * 示例 2：
	 * 输入：num1 = "456", num2 = "77"
	 * 输出："533"
	 * 示例 3：
	 * 输入：num1 = "0", num2 = "0"
	 * 输出："0"
	 * 提示：
	 * 1 <= num1.length, num2.length <= 104
	 * num1 和num2 都只包含数字 0-9
	 * num1 和num2 都不包含任何前导零
	 */
	public static class AddStrings {

		/**
		 * 字符串模拟加法,不限位数  用整数类型肯定是做不到的
		 * 数字0-9的ASCII码为[48-57]
		 * 从个位一直往左边开始计算,并记录高位是否需要进位
		 */
		public static String addString(String num1, String num2) {
			StringBuilder sb = new StringBuilder();
			boolean step = false;
			int n1 = num1.length() - 1, n2 = num2.length() - 1;
			while (n1 >= 0 || n2 >= 0) {
				int v1 = n1 < 0 ? 0 : num1.charAt(n1) - 48, v2 = n2 < 0 ? 0 : num2.charAt(n2) - 48;
				int v = v1 + v2 + (step ? 1 : 0);
				if (v >= 10) {
					step = true;
					v -= 10;
				} else {
					step = false;
				}
				sb.insert(0, (char) (v + 48));
				n1--;
				n2--;
			}
			return step ? sb.insert(0, "1").toString() : sb.toString();
		}

		public static void main(String[] args) {
			String s = addString("11", "123");
			System.out.println(s);
		}
	}

	/**
	 * 796. 旋转字符串
	 * 给定两个字符串, s 和 goal。如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
	 * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。
	 * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。
	 * 示例 1:
	 * 输入: s = "abcde", goal = "cdeab"
	 * 输出: true
	 * 示例 2:
	 * 输入: s = "abcde", goal = "abced"
	 * 输出: false
	 * 提示:
	 * 1 <= s.length, goal.length <= 100
	 * s 和 goal 由小写英文字母组成
	 */
	public static class RotateString {

		/**
		 * 个人思路:
		 * 首先题意中的选择字符串是什么含义呢?
		 * 实际是将首位字符移动到最后一位,举例: a,b,c,d,e
		 * 选择可以得到的场景如下:b,c,d,e,a | c,d,e,a,b | d,e,a,b,c | e,a,b,c,d | a,b,c,d,e
		 * 实际上是找到第一个出现的字符后,往后遍历,当遍历到字符串末尾时,继续回到字符串起始位置,当所有字符串遍历完,符合条件则返回true
		 *
		 * @param s
		 * @param goal
		 * @return
		 */
		public static boolean rotateString(String s, String goal) {
			if (s.length() != goal.length()) return false;
			int p = 0;  // p指向s的指针,q指向goal的指针
			for (int i = 0; i < s.length(); ++i) {
				int q = 0;
				if (s.charAt(i) == goal.charAt(q)) {
					p = i + 1 == s.length() ? 0 : i + 1;
					q = 1;
					while (q < goal.length() && s.charAt(p) == goal.charAt(q)) {
						p++;
						q++;
						if (p == s.length()) { // 超过s的最大序列号,返回到首位0
							p = 0;
						}
					}
					if (q == goal.length()) return true;
				}
			}
			return false;
		}

		public static boolean rotateStringI(String s, String goal) {
			if (s.length() != goal.length()) return false;
			int p = 0;  // p指向s的指针,q指向goal的指针
			for (int i = 0; i < s.length(); ++i) {
				int q = 0;
				if (s.charAt(i) == goal.charAt(q)) {
					p = i + 1 == s.length() ? 0 : i + 1;
					q = 1;
					for (; q < goal.length(); q++, p++) {
						p = p == s.length() ? 0 : p;
						if (s.charAt(p) != goal.charAt(q)) {
							break;
						}
					}
					if (q == goal.length()) return true;
				}
			}
			return false;
		}

		public static boolean rotateStringOfficial(String s, String goal) {
			return s.length() == goal.length() && (s + s).contains(goal);
		}

		public static void main(String[] args) {
			// boolean b = rotateString("abcde", "cdeab");
			// System.out.println(b);
			// boolean b1 = rotateString("abcde", "abced");
			// System.out.println(b1);
			System.out.println(rotateStringOfficial("gcmbf", "fgcmb"));
		}
	}

	/**
	 * 946. 验证栈序列
	 * 中等
	 * 相关标签
	 * 相关企业
	 * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
	 * 示例 1：
	 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
	 * 输出：true
	 * 解释：我们可以按以下顺序执行：
	 * push(1), push(2), push(3), push(4), pop() -> 4,
	 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
	 * 示例 2：
	 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
	 * 输出：false
	 * 解释：1 不能在 2 之前弹出。
	 * 提示：
	 * 1 <= pushed.length <= 1000
	 * 0 <= pushed[i] <= 1000
	 * pushed 的所有元素 互不相同
	 * popped.length == pushed.length
	 * popped 是 pushed 的一个排列
	 */
	public static class ValidateStackSequences {

		/**
		 * 个人思路:
		 * 根据题意,入栈必须按照入栈顺序,从而判断出栈是否可以按照给定的顺序
		 * <p>
		 * 首先第一个出栈的元素,肯定是当前入栈的最后一个元素,但是入栈一定要按照指定的顺序
		 * 比如pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
		 * 出栈第一个元素4,则说明栈中肯定有元素[1,2,3,4] ,
		 * 1.如果出栈第二个元素为3,则直接出栈即可,
		 * 2.如果出栈第二个元素为5,这需要先入栈5再出栈
		 * 3.如果出栈的第二个元素不为3或5,则错误
		 * 总结,下一个出栈的元素一定是当前出栈元素的在栈中的上一个元素或者在入栈列表中的下一个元素
		 * 用p,q分别表示出入栈的序列,然后维护一个栈deque,并进行操作
		 *
		 * @param pushed
		 * @param popped
		 * @return
		 */
		public static boolean validateStackSequences(int[] pushed, int[] popped) {
			// 先找到一个出栈的元素,在入栈列表中的位置
			int q = 0, p = 0;
			Deque<Integer> deque = new LinkedList<>();
			for (int i = 0; i < pushed.length; ++i) {
				deque.push(pushed[i]);
				if (pushed[i] == popped[0]) {
					q++;
					p = i + 1;
					break;
				}
			}
			deque.pop();
			while (q < popped.length) {
				if (!deque.isEmpty() && deque.getFirst() == popped[q]) {
					deque.pop();
					q++;
				} else {  // 不在栈中,需要在入栈列表中继续入栈
					boolean flag = false;
					for (; p < pushed.length; ++p) {
						deque.push(pushed[p]);
						if (pushed[p] == popped[q]) {
							flag = true;
							p++;
							break;
						}
					}
					if (!flag) return false;
				}
			}
			return deque.isEmpty();
		}

		public static boolean validateStackSequencesI(int[] pushed, int[] popped) {
			// 先找到一个出栈的元素,在入栈列表中的位置
			int q = 0, p = 0;
			Deque<Integer> deque = new LinkedList<>();
			while (q < popped.length) {
				if (!deque.isEmpty() && deque.getFirst() == popped[q]) {
					deque.pop();
					q++;
				} else {  // 不在栈中,需要在入栈列表中继续入栈
					boolean flag = false;
					for (; p < pushed.length; ++p) {
						deque.push(pushed[p]);
						if (pushed[p] == popped[q]) {
							flag = true;
							p++;
							break;
						}
					}
					if (!flag) return false;
				}
			}
			return deque.isEmpty();
		}

		/**
		 * 官方思路:
		 * 思路非常简洁,按照入栈列表顺序进行入栈,同时不停的进行出栈列表顺序进行出站,如果出栈元素不匹配则继续入栈
		 * 每次入栈后,都会循环去匹配出栈
		 *
		 * @param pushed
		 * @param popped
		 * @return
		 */
		public static boolean validateStackSequencesOfficial(int[] pushed, int[] popped) {
			Deque<Integer> deque = new LinkedList<>();
			for (int i = 0, j = 0; i < pushed.length; i++) {
				deque.push(pushed[i]);
				while (!deque.isEmpty() && deque.peek() == popped[j]) {
					deque.pop();
					j++;
				}
			}
			return deque.isEmpty();
		}

		public static void main(String[] args) {
			System.out.println(validateStackSequencesOfficial(new int[]{4, 0, 1, 2, 3}, new int[]{4, 2, 3, 0, 1}));
		}
	}

	/**
	 * 6. Z 字形变换
	 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
	 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
	 * P   A   H   N
	 * A P L S I I G
	 * Y   I   R
	 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
	 * 请你实现这个将字符串进行指定行数变换的函数：
	 * string convert(string s, int numRows);
	 * 示例 1：
	 * 输入：s = "PAYPALISHIRING", numRows = 3
	 * 输出："PAHNAPLSIIGYIR"
	 * 示例 2：
	 * 输入：s = "PAYPALISHIRING", numRows = 4
	 * 输出："PINALSIGYAHRPI"
	 * 解释：
	 * P     I    N
	 * A   L S  I G
	 * Y A   H R
	 * P     I
	 * 示例 3：
	 * 输入：s = "A", numRows = 1
	 * 输出："A"
	 * 提示：
	 * 1 <= s.length <= 1000
	 * s 由英文字母（小写和大写）、',' 和 '.' 组成
	 * 1 <= numRows <= 1000
	 */
	public static class Convert {

		/**
		 * 个人思路:
		 * 最简单的方式用n个数组,然后分别填充数据,最后连接成一个新的字符串
		 * P     I    N
		 * A   L S  I G
		 * Y A   H R
		 * P     I
		 *
		 * @param s
		 * @param numRows
		 * @return
		 */
		public static String convert(String s, int numRows) {
			if (numRows == 1) return s;
			List<List<Character>> list = new ArrayList<>();
			for (int i = 0; i < numRows; ++i) {
				list.add(new ArrayList<>(numRows));
			}
			int j = 0;
			boolean flag = true;
			for (int i = 0; i < s.length(); ++i) {
				list.get(j).add(s.charAt(i));
				if (j == 0) { // 指定到第一个数组上,则需要往下
					flag = true;
				}
				if (j == numRows - 1) { // 指定到最后一个数组上,则需要往上
					flag = false;
				}
				if (flag) {
					j++;
				} else {
					j--;
				}

			}
			StringBuilder sb = new StringBuilder();
			for (List<Character> row : list) {
				sb.append(join(row));
			}
			return sb.toString();
		}

		public static String join(List<Character> list) {
			StringBuilder sb = new StringBuilder();
			for (Character c : list) {
				sb.append(c);
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			System.out.println(convert("AB", 1));
		}
	}

	/**
	 * 54. 螺旋矩阵
	 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
	 * 示例 1：
	 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
	 * 输出：[1,2,3,6,9,8,7,4,5]
	 * 示例 2：
	 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
	 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
	 * 提示：
	 * m == matrix.length
	 * n == matrix[i].length
	 * 1 <= m, n <= 10
	 * -100 <= matrix[i][j] <= 100
	 */
	public static class SpiralOrder {

		/**
		 * 螺旋矩阵
		 * 用left,right,up,down 分别来记录左,右,上,下 四个边已经遍历过的行数
		 * 1.从左到右遍历,则up向下移动一位,范围[left,right]
		 * 2.从上到下遍历,则right向左移动一位,范围[up,down]
		 * 3.从右到左遍历,则down向上移动一位,范围[right,left]
		 * 4.从下到上遍历,则left向右移动一位,范围[down,up]
		 * 如果某次遍历时范围为0,则遍历结束
		 *
		 * @param matrix
		 * @return
		 */
		public static List<Integer> spiralOrder(int[][] matrix) {
			int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;
			List<Integer> list = new ArrayList<>();
			while (true) {
				if (left <= right) {
					for (int i = left; i <= right; ++i) {
						list.add(matrix[up][i]);
					}
					up++;
				} else break;
				if (up <= down) {
					for (int i = up; i <= down; ++i) {
						list.add(matrix[i][right]);
					}
					right--;
				} else break;
				if (right >= left) {
					for (int i = right; i >= left; --i) {
						list.add(matrix[down][i]);
					}
					down--;
				} else break;
				if (down >= up) {
					for (int i = down; i >= up; --i) {
						list.add(matrix[i][left]);
					}
					left++;
				} else break;
			}
			return list;
		}

		public static List<Integer> spiralOrderI(int[][] matrix) {
			int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1;
			List<Integer> list = new ArrayList<>();
			while (left <= right && up <= down) {
				for (int i = left; i <= right; ++i) {
					list.add(matrix[up][i]);
				}
				up++;
				for (int i = up; i <= down; ++i) {
					list.add(matrix[i][right]);
				}
				right--;
				if (up <= down) {
					for (int i = right; i >= left; --i) {
						list.add(matrix[down][i]);
					}
					down--;
				}
				if (left <= right) {
					for (int i = down; i >= up; --i) {
						list.add(matrix[i][left]);
					}
					left++;
				}
			}
			return list;
		}

		public static void main(String[] args) {
			int[][] matrix = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}, {26, 27, 28, 29, 30}};
			List<Integer> integers = spiralOrderI(matrix);
			System.out.println(integers);
		}
	}

	/**
	 * 59. 螺旋矩阵 II
	 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
	 * 示例 1：
	 * 输入：n = 3
	 * 输出：[[1,2,3],[8,9,4],[7,6,5]]
	 * 示例 2：
	 * 输入：n = 1
	 * 输出：[[1]]
	 * 提示：
	 * 1 <= n <= 20
	 */
	public static class GenerateMatrix {

		/**
		 * nxn的矩阵,刚好填充满这个二维数组
		 *
		 * @param n
		 * @return
		 */
		public static int[][] generateMatrix(int n) {
			int[][] arr = new int[n][n];
			int m = 1;
			int left = 0, right = n - 1, up = 0, down = n - 1;
			while (m <= n * n) {
				for (int i = left; i <= right; ++i) {
					arr[up][i] = m++;
				}
				up++;
				for (int i = up; i <= down; ++i) {
					arr[i][right] = m++;
				}
				right--;
				for (int i = right; i >= left; --i) {
					arr[down][i] = m++;
				}
				down--;
				for (int i = down; i >= up; --i) {
					arr[i][left] = m++;
				}
				left++;
			}
			return arr;
		}

		public static void main(String[] args) {
			int[][] ints = generateMatrix(1);
			System.out.println(ints);
		}
	}

	/**
	 * 48. 旋转图像
	 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
	 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
	 * 示例 1：
	 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
	 * 输出：[[7,4,1],[8,5,2],[9,6,3]]
	 * 示例 2：
	 * 输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
	 * 输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
	 * 提示：
	 * n == matrix.length == matrix[i].length
	 * 1 <= n <= 20
	 * -1000 <= matrix[i][j] <= 1000
	 */
	public static class Rotate {

		/**
		 * 个人思路:
		 * 实际上与54、59题类似的思路,将每个边的元素进行转移
		 * <p>
		 * 官方思路使用水平翻转+对角线翻转 来实现逆时针旋转90度
		 *
		 * @param matrix
		 */
		public static void rotate(int[][] matrix) {
			int left = 0, right = matrix[0].length - 1, up = 0, down = matrix.length - 1, length = matrix[0].length - 1;
			while (left <= right && up <= down) {
				for (int i = left; i < right; ++i) {  // 四个最外层边进行转移,转移完成后left++,right-- ,在旋转的过程中,right为最后一位时不用再转,因为第一位已经转过了
					// a,b,c,d 顺时针逆转90度进行交换元素 a->temp d->a c->d b->c temp->b  ->表示放入
					int temp = matrix[up][i];
					matrix[up][i] = matrix[length - i][left];
					matrix[length - i][left] = matrix[down][length - i];
					matrix[down][length - i] = matrix[i][right];
					matrix[i][right] = temp;
				}
				left++;
				right--;
				up++;
				down--;
			}
		}

		/**
		 * 由于该二维数组是nxn的，所有left=up;right=down 即可以省去up和down变量
		 *
		 * @param matrix
		 */
		public static void rotateI(int[][] matrix) {
			int left = 0, right = matrix[0].length - 1, length = matrix[0].length - 1;
			while (left <= right) {
				for (int i = left; i < right; ++i) {  // 四个最外层边进行转移,转移完成后left++,right-- ,在旋转的过程中,right为最后一位时不用再转,因为第一位已经转过了
					// a,b,c,d 顺时针逆转90度进行交换元素 a->temp d->a c->d b->c temp->b  ->表示放入
					int temp = matrix[left][i];
					matrix[left][i] = matrix[length - i][left];
					matrix[length - i][left] = matrix[right][length - i];
					matrix[right][length - i] = matrix[i][right];
					matrix[i][right] = temp;
				}
				left++;
				right--;
			}
		}

		public static void main(String[] args) {
			int[][] arr = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
			// int[][] arr = new int[][]{{1}};
			rotateI(arr);
			System.out.println();
		}
	}

	/**
	 * 8. 字符串转换整数 (atoi)
	 * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数。
	 * 函数 myAtoi(string s) 的算法如下：
	 * 空格：读入字符串并丢弃无用的前导空格（" "）
	 * 符号：检查下一个字符（假设还未到字符末尾）为 '-' 还是 '+'。如果两者都不存在，则假定结果为正。
	 * 转换：通过跳过前置零来读取该整数，直到遇到非数字字符或到达字符串的结尾。如果没有读取数字，则结果为0。
	 * 舍入：如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被舍入为 −231 ，大于 231 − 1 的整数应该被舍入为 231 − 1 。
	 * 返回整数作为最终结果。
	 * 示例 1：
	 * 输入：s = "42"
	 * 输出：42
	 * 解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
	 * 带下划线线的字符是所读的内容，插入符号是当前读入位置。
	 * 第 1 步："42"（当前没有读入字符，因为没有前导空格）
	 * ^
	 * 第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
	 * ^
	 * 第 3 步："42"（读入 "42"）
	 * ^
	 * 示例 2：
	 * 输入：s = " -042"
	 * 输出：-42
	 * 解释：
	 * 第 1 步："   -042"（读入前导空格，但忽视掉）
	 * ^
	 * 第 2 步："   -042"（读入 '-' 字符，所以结果应该是负数）
	 * ^
	 * 第 3 步："   -042"（读入 "042"，在结果中忽略前导零）
	 * ^
	 * 示例 3：
	 * <p>
	 * 输入：s = "1337c0d3"
	 * <p>
	 * 输出：1337
	 * <p>
	 * 解释：
	 * <p>
	 * 第 1 步："1337c0d3"（当前没有读入字符，因为没有前导空格）
	 * ^
	 * 第 2 步："1337c0d3"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
	 * ^
	 * 第 3 步："1337c0d3"（读入 "1337"；由于下一个字符不是一个数字，所以读入停止）
	 * ^
	 * 示例 4：
	 * <p>
	 * 输入：s = "0-1"
	 * <p>
	 * 输出：0
	 * <p>
	 * 解释：
	 * <p>
	 * 第 1 步："0-1" (当前没有读入字符，因为没有前导空格)
	 * ^
	 * 第 2 步："0-1" (当前没有读入字符，因为这里不存在 '-' 或者 '+')
	 * ^
	 * 第 3 步："0-1" (读入 "0"；由于下一个字符不是一个数字，所以读入停止)
	 * ^
	 * 示例 5：
	 * <p>
	 * 输入：s = "words and 987"
	 * <p>
	 * 输出：0
	 * <p>
	 * 解释：
	 * <p>
	 * 读取在第一个非数字字符“w”处停止。
	 * <p>
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 0 <= s.length <= 200
	 * s 由英文字母（大写和小写）、数字（0-9）、' '、'+'、'-' 和 '.' 组成
	 */
	public static class MyAtoi {

		/**
		 * 字符串的超多规范性处理,如果使用if-else 很容易忽略一些场景.
		 * 官方使用有限状态机的思路
		 * <p>
		 * 先列出状态表
		 * *******************************************
		 * *     |  ' '  |  +/-   | number   | other |
		 * *******************************************
		 * start |start  |signed  | number   |end    |
		 * *******************************************
		 * signed| end   | end    | number   |end    |
		 * *******************************************
		 * number| end   | end    | number   |end    |
		 * *******************************************
		 * end   | end   | end    | end      |end    |
		 * *******************************************
		 * <p>
		 * 字符串在遍历时,总共有4种状态start:开始  signed:符号位  number:数字  end:结束
		 * 字符的类型 1.空格 2.+/- 3.数字 4.其他
		 *
		 * @param s
		 * @return
		 */
		public static int myAtoi(String s) {
			Automaton automaton = new Automaton();
			for (int i = 0; i < s.length(); ++i) {
				if (automaton.get(s.charAt(i)) == -1) {
					break;
				}
			}
			return (int) automaton.v * automaton.sign;
		}

		private static class Automaton {
			private int sign = 1;  // 符号位,默认为 +
			private long v = 0;       // 解析的值,默认为0
			private String state = "start"; // 状态,默认为开始
			// 状态表,根据顺序构建
			private Map<String, String[]> table = new HashMap<String, String[]>() {{
				put("start", new String[]{"start", "signed", "number", "end"});
				put("signed", new String[]{"end", "end", "number", "end"});
				put("number", new String[]{"end", "end", "number", "end"});
				put("end", new String[]{"end", "end", "end", "end"});
			}};

			// 每个字符获取时,进行状态判断
			public int get(char c) {
				state = table.get(state)[getIndex(c)]; // 下一步状态获取并更新
				if (Objects.equals(state, "number")) {
					v = v * 10 + c - '0';  // 10进制表示,后面每多一位,则前面的值*10+该位的值
					v = sign == 1 ? Math.min(v, Integer.MAX_VALUE) : Math.min(v, (long) Integer.MAX_VALUE + 1);
				} else if (Objects.equals(state, "signed")) {
					sign = c == '+' ? 1 : -1;
				}
				return Objects.equals(state, "end") ? -1 : 1;
			}

			// 获取字符的序列index
			private int getIndex(char c) {
				if (c == ' ') {
					return 0;
				} else if (c == '+' || c == '-') {
					return 1;
				} else if (Character.isDigit(c)) {
					return 2;
				} else return 3;
			}
		}

		public static void main(String[] args) {
			int maxValue = Integer.MAX_VALUE;
			int minValue = Integer.MIN_VALUE;
			long l = -(long) Integer.MIN_VALUE;
			// 负数的最大比正数多1
			System.out.println();
			System.out.println(myAtoi("1337c0d3"));

		}
	}

	/**
	 * 31. 下一个排列
	 * 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
	 * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
	 * 整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
	 * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
	 * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
	 * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
	 * 给你一个整数数组 nums ，找出 nums 的下一个排列。
	 * 必须 原地 修改，只允许使用额外常数空间。
	 * 示例 1：
	 * 输入：nums = [1,2,3]
	 * 输出：[1,3,2]
	 * 示例 2：
	 * 输入：nums = [3,2,1]
	 * 输出：[1,2,3]
	 * 示例 3：
	 * 输入：nums = [1,1,5]
	 * 输出：[1,5,1]
	 * 提示：
	 * 1 <= nums.length <= 100
	 * 0 <= nums[i] <= 100
	 */
	public static class NextPermutation {

		/**
		 * 4,5,2,6,3,1
		 * 1.从后往前,找到第一个不遵守升序的数 a,如果整个数组都升序,说明其是最大
		 * 2.然后在已遍历的数中,找到第一个符合的是数b 满足b>a
		 * 3.将a和b进行交换,这样可以使得其不按升序位的数字完成最小幅度的增大,然后把a右边的数字变成升序
		 * 如何变成升序呢?交换之前b>a,交换之后a放到了b的位置:4,5,3,6,2,1 由于b>a 那么a右边的数还是降序,
		 * 我们只需要反转它成为升序即可
		 *
		 * @param nums
		 */
		public static void nextPermutation(int[] nums) {
			int i = nums.length - 2;
			while (i >= 0 && nums[i] >= nums[i + 1]) {
				i--;
			}
			if (i >= 0) {
				int j = nums.length - 1;
				// 在i+1...n中找到比i位置数字大的最少的数,因为降序,那么第一个最小的即是要的数字
				while (j >= 0 && nums[i] >= nums[j]) {
					j--;
				}
				swap(nums, i, j);
			}
			reverse(nums, i + 1, nums.length - 1);
		}

		private static void swap(int[] nums, int i, int j) {
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
		}

		private static void reverse(int[] nums, int start, int end) {
			while (start < end) {
				swap(nums, start, end);
				start++;
				end--;
			}
		}

		public static void main(String[] args) {
			int[] ints = {4, 5, 2, 6, 3, 1};
			nextPermutation(ints);
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * 287. 寻找重复数
	 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
	 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
	 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
	 * 示例 1：
	 * 输入：nums = [1,3,4,2,2]
	 * 输出：2
	 * 示例 2：
	 * 输入：nums = [3,1,3,4,2]
	 * 输出：3
	 * 示例 3 :
	 * 输入：nums = [3,3,3,3,3]
	 * 输出：3
	 * 提示
	 * 1 <= n <= 105
	 * nums.length == n + 1
	 * 1 <= nums[i] <= n
	 * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
	 * 进阶：
	 * 如何证明 nums 中至少存在一个重复的数字?
	 * 你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？
	 */
	public static class FindDuplicate {

		/**
		 * 形成索引指向数字值的链表,那么存在重复数字时,链表就会存在环
		 *
		 * @param nums
		 * @return
		 */
		public static int findDuplicate(int[] nums) {
			int fast = 0, slow = 0;
			do {
				slow = nums[slow];
				fast = nums[nums[fast]];
			} while (fast != slow);
			slow = 0;
			while (slow != fast) {
				slow = nums[slow];
				fast = nums[fast];
			}
			return slow;
		}

		public static void main(String[] args) {
			int[] nums = new int[]{1, 3, 4, 2, 2};
			int duplicate = findDuplicate(nums);
			System.out.println(duplicate);
		}
	}
}
