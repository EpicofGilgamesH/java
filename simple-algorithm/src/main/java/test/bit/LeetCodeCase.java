package test.bit;

import javax.print.attribute.standard.PrinterURI;

/**
 * 位运算
 */
public class LeetCodeCase {

	/**
	 * 191. 位1的个数
	 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中
	 * 设置位
	 * 的个数（也被称为汉明重量）。
	 * 示例 1：
	 * 输入：n = 11
	 * 输出：3
	 * 解释：输入的二进制串 1011 中，共有 3 个设置位。
	 * 示例 2：
	 * 输入：n = 128
	 * 输出：1
	 * 解释：输入的二进制串 10000000 中，共有 1 个设置位。
	 * 示例 3：
	 * 输入：n = 2147483645
	 * 输出：30
	 * 解释：输入的二进制串 11111111111111111111111111111101 中，共有 30 个设置位。
	 * 提示：
	 * 1 <= n <= 231 - 1
	 * 进阶：
	 * 如果多次调用这个函数，你将如何优化你的算法？
	 */
	public static class HammingWeight {

		/**
		 * 个人思路:
		 * 实际上是将int值转换成二进制位,然后统计处二进制位为1的个数(常规思路)
		 * 那如何通过位运算来计算呢?
		 * 位运算时间复杂度:循环次数就是n的二进制位数,每次循环需要进行一次与运算
		 *
		 * @param n
		 * @return
		 */
		public static int hammingWeight(int n) {
			int sum = 0, x = 1;
			for (int i = 0; i < 32; i++) {
				if ((n & x) > 0) {
					sum++;
				}
				x = x << 1;
				if (x > n) break;
			}
			return sum;
		}

		/**
		 * 位运算可以简化,上面位运算中,n每次和2的次方进行与运算;实际是和 1,10,100,1000,10000,100000 ...
		 * 来进行与运算,获的对应位上的值是否为1
		 * 此处可以将n右移后,每次与1进行与运算,这样运算会更快
		 *
		 * @param n
		 * @return
		 */
		public static int hammingWeightI(int n) {
			int sum = 0;
			while (n > 0) {
				if ((n & 1) == 1) {
					sum++;
				}
				n = n >> 1;
			}
			return sum;
		}

		/**
		 * 官方思路:
		 * n&n-1  n-1 二进制位代表了什么,代表了1->0 ; 10->01; 100->011 即最后一位的1肯定取反为0,因为减了1
		 * n=101011; n-1= 101010; n&(n-1)=101010
		 * n=101010; n-1=101001; n&(n-1)=101000;
		 *
		 * @param n
		 * @return
		 */
		public static int hammingWeightOfficial(int n) {
			int sum = 0;
			while (n > 0) {
				n = n & (n - 1);
				sum++;
			}
			return sum;
		}

		public static void main(String[] args) {
			System.out.println(hammingWeightI(2147483645));
		}
	}

	/**
	 * 231. 2 的幂
	 * 给你一个整数 n，请你判断该整数是否是 2 的幂次方。如果是，返回 true ；否则，返回 false 。
	 * 如果存在一个整数 x 使得 n == 2x ，则认为 n 是 2 的幂次方。
	 * 示例 1：
	 * 输入：n = 1
	 * 输出：true
	 * 解释：20 = 1
	 * 示例 2：
	 * 输入：n = 16
	 * 输出：true
	 * 解释：24 = 16
	 * 示例 3：
	 * 输入：n = 3
	 * 输出：false
	 * 提示：
	 * -231 <= n <= 231 - 1
	 * 进阶：你能够不使用循环/递归解决此问题吗？
	 */
	public static class IsPowerOfTwo {

		/**
		 * 个人思路:
		 * 1.求一个整数是否为2的整数幂次方,常规思路列举2的整数次幂就能匹配出来;首先尾数只能是2,4,6,8
		 * 2.使用二进制位呢?必须首位为1,其他位都为0;
		 * eg: 16 -> 10000 怎么证明首位为1其后都是0呢? 16-1 -> 1111 所有位都是1;怎么证明每一位都是1呢?????
		 * 还是需要循环判断每一位
		 */
		public static boolean isPowerOfTwo(int n) {
			if (n <= 0) return false;
			if (n == 1) return true;
			n = n - 1;
			while (n > 0) {
				if ((n & 1) == 0) {
					return false;
				}
				n = n >> 1;
			}
			return true;
		}

		/**
		 * 1. n&(n-1) 是将n的二进制数最后一位1变成0;
		 * 2. n如果是2的整数次幂,那么n的二进制必须是首位为1,后面位全部为0
		 * 3. 那么n&(n-1) 就肯定等于0
		 * 4.2的整数次幂肯定是正整数
		 *
		 * @param n
		 * @return
		 */
		public static boolean isPowerOfTwoI(int n) {
			if (n <= 0) return false;
			return (n & (n - 1)) == 0;
		}

		/**
		 * 2的整数次幂
		 * 1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384
		 * 例如 1024之前的整数次幂n,都是n*2^x得到的说明:n*2*x=1024  => 1024/n =2^x;
		 * 2^x = 1,2,4,8,16... 由于1024=2^10 那么他的约数也只能是1,2,4,8,16
		 * 所以可以证明 一个数n是最大的2的整数次幂的约数,那么他就是2^x
		 * 32位整数除去符号位最大为: 2^30 (0...30)
		 *
		 * @param n
		 * @return
		 */
		public static boolean isPowerOfTwoII(int n) {
			if (n <= 0) return false;
			int max = 1 << 30;
			return max % n == 0;
		}

		public static void main(String[] args) {
			System.out.println(isPowerOfTwoII(3));
			System.out.println(isPowerOfTwoII(16));
			System.out.println(isPowerOfTwoII(0));
			System.out.println(isPowerOfTwoII(1));
		}
	}

	/**
	 * 371. 两整数之和
	 * 给你两个整数 a 和 b ，不使用 运算符 + 和 - ，计算并返回两整数之和。
	 * 示例 1：
	 * 输入：a = 1, b = 2
	 * 输出：3
	 * 示例 2：
	 * 输入：a = 2, b = 3
	 * 输出：5
	 * 提示：
	 * -1000 <= a, b <= 1000
	 */
	public static class GetSum {

		/**
		 * 只能借助官方思路,毫无头绪
		 * 首先了解位运算的特性:
		 * 1. '|' 或运算,都是0得0,否则得1;
		 * 2. '&' 与运算,都是1得1,否则得0; 都是1的时候返回1,可用来计算进位,都是1才进位,进位左移1
		 * 3. '^' 异或运算,相同得0,不同得1;需要进位都是1的情况得0,那剩下[1,0] [0,1] [00] 三中情况;根据异或运算刚好等得到该位相加的情况
		 * 所以a+b 可以分成两个部分:1.无进位部分的计算  2.有进位部分的计算
		 * 无进位部分计算:a^b=c1;
		 * 有进位部分计算:(a&b) << 1 =c2;
		 * 那么结果就是 c1+c2;因为不能使用+,-号运算,c1+c2 当做a和b继续上面的操作;
		 * 直到有进位的计算结果c2=0,那么结果就等于c1;
		 * 有符号整数用补码来表示和存储:
		 * 1.正整数的补码与源码相同;
		 * 2.负整数的补码为其源码除符号外的所有位取反后加1;
		 * 所以负数可以用加法直接计算
		 *
		 * @param a
		 * @param b
		 * @return
		 */
		public static int getSum(int a, int b) {
			while (b != 0) {
				int c1 = a ^ b; // 无进位的值
				int c2 = (a & b) << 1; // 有进位的值
				a = c1;
				b = c2;
			}
			return a;
		}

		public static void main(String[] args) {
			System.out.println(getSum(1, 2));
			System.out.println(getSum(2, 3));
			System.out.println(getSum(23, 45));
		}
	}

	/**
	 * 136. 只出现一次的数字
	 * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
	 * 你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
	 * 示例 1 ：
	 * 输入：nums = [2,2,1]
	 * 输出：1
	 * 示例 2 ：
	 * 输入：nums = [4,1,2,1,2]
	 * 输出：4
	 * 示例 3 ：
	 * 输入：nums = [1]
	 * 输出：1
	 * 提示：
	 * 1 <= nums.length <= 3 * 104
	 * -3 * 104 <= nums[i] <= 3 * 104
	 * 除了某个元素只出现一次以外，其余每个元素均出现两次。
	 */
	public static class SingleNumber {

		/**
		 * 个人思路:
		 * 使用位运算思路,一个整数二进制位 异或 自己等于0
		 * 因为二进制位每一位都相同
		 * 异或可满足交换律??怎么证明呢??
		 * a^b^c=a^c^b; => 0^1^1 = 1^0^1 = 1^1^0;满足交换律;
		 * <p>
		 * leetcode 官方思路也没有证明为什么异常满足交换律和结合律
		 * 异或运算处理的使用场景:
		 * 1.任何数和0做异或运算,结果任然是原来的数,即a^0=a;
		 * 2.任何数和其自身做异或运算,结果是0,即a^a=0;
		 * 3.异或运算满足交换律和结合律,即 a^b^a=b^a^a=b^(a^a)=b^0=b;
		 *
		 * @param nums
		 * @return
		 */
		public static int singleNumber(int[] nums) {
			int v = nums[0];
			for (int i = 1; i < nums.length; i++) {
				v ^= nums[i];
			}
			return v;
		}

		public static void main(String[] args) {
			System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));
		}
	}

	/**
	 * 137. 只出现一次的数字 II
	 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
	 * 你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
	 * 示例 1：
	 * 输入：nums = [2,2,3,2]
	 * 输出：3
	 * 示例 2：
	 * 输入：nums = [0,1,0,1,0,1,99]
	 * 输出：99
	 * 提示：
	 * 1 <= nums.length <= 3 * 104
	 * -231 <= nums[i] <= 231 - 1
	 * nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
	 */
	public static class SingleNumberII {

		/**
		 * 个人思路:
		 * 按位运算想不到思路
		 * 二进制位,对每一位进行单独的运算;已知每一位都为0或者1,那么每一位上出现3次,那么肯定是0和3
		 * 可得,唯一一个只出现一次的数,它在这一位上,就是除以3的余数
		 *
		 * @param nums
		 * @return
		 */
		public static int singleNumber(int[] nums) {
			int ans = 0;
			for (int i = 0; i < 32; ++i) {
				int total = 0;
				for (int num: nums) {
					total += ((num >> i) & 1);
				}
				if (total % 3 != 0) {
					ans |= (1 << i);
				}
			}
			return ans;
		}

		public static void main(String[] args) {
			System.out.println(singleNumber(new int[]{-2,-2,1,1,4,1,4,4,-4,-2}));
		}
	}

	/**
	 * 67. 二进制求和
	 * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
	 * 示例 1：
	 * 输入:a = "11", b = "1"
	 * 输出："100"
	 * 示例 2：
	 * 输入：a = "1010", b = "1011"
	 * 输出："10101"
	 * 提示：
	 * 1 <= a.length, b.length <= 104
	 * a 和 b 仅由字符 '0' 或 '1' 组成
	 * 字符串如果不是 "0" ，就不含前导零
	 */
	public static class AddBinary {

		/**
		 * 简单来看可以用位上的数字相加
		 *
		 * @param a
		 * @param b
		 * @return
		 */
		public static String addBinary(String a, String b) {
			int l1 = a.length(), l2 = b.length();
			int i = 0, carry = 0;
			StringBuilder sb = new StringBuilder();
			while (i < l1 || i < l2) {
				int va = 0, vb = 0;
				if (i < l1) va = a.charAt(l1 - i - 1) - '0';
				if (i < l2) vb = b.charAt(l2 - i - 1) - '0';
				int v = va + vb + carry;
				sb.append(v % 2);
				carry = v / 2;
				i++;
			}
			if (carry == 1) sb.append(carry);
			return sb.reverse().toString();
		}

		public static void main(String[] args) {
			String s = addBinary("111", "11");
			System.out.println(s);
		}
	}

	/**
	 * 190. 颠倒二进制位
	 * 颠倒给定的 32 位无符号整数的二进制位。
	 * 提示：
	 * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
	 * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在 示例 2 中，输入表示有符号整数 -3，输出表示有符号整数 -1073741825。
	 * 示例 1：
	 * 输入：n = 00000010100101000001111010011100
	 * 输出：964176192 (00111001011110000010100101000000)
	 * 解释：输入的二进制串 00000010100101000001111010011100 表示无符号整数 43261596，
	 * 因此返回 964176192，其二进制表示形式为 00111001011110000010100101000000。
	 * 示例 2：
	 * 输入：n = 11111111111111111111111111111101
	 * 输出：3221225471 (10111111111111111111111111111111)
	 * 解释：输入的二进制串 11111111111111111111111111111101 表示无符号整数 4294967293，
	 * 因此返回 3221225471 其二进制表示形式为 10111111111111111111111111111111 。
	 * 提示：
	 * 输入是一个长度为 32 的二进制字符串
	 * 进阶: 如果多次调用这个函数，你将如何优化你的算法？
	 */
	public static class ReverseBits {

		/**
		 * java 32位整数,都是有符号的
		 * 32位二进制,索引从0-31 其中第0位颠倒之后就是在31的位置,1颠倒在30的位置, i -> 31-i
		 *
		 * @param n
		 * @return
		 */
		public static int reverseBits(int n) {
			int res = 0;
			for (int i = 0; i < 32; i++) {
				if (((n >> i) & 1) == 1) {
					res += 1 << (31 - i);
				}
			}
			return res;
		}

		/**
		 * 分治思想
		 * 00000010100101000001111010011100
		 * 将32位分成2份,每份16位;然后处理这16位的颠倒,同理这16位可以分成8位;依次最后可以分成2位,2位进行颠倒操作;
		 * 所以按分治的思想,先分成最小份,2位进行颠倒,然后上一步4位进行颠倒...
		 *
		 * @param n
		 * @return
		 */
		private static final int M1 = 0x55555555; // 01010101010101010101010101010101
		private static final int M2 = 0x33333333; // 00110011001100110011001100110011
		private static final int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
		private static final int M8 = 0x00ff00ff; // 00000000111111110000000011111111

		public static int reverseBitsI(int n) {
			n = n >>> 1 & M1 | (n & M1) << 1;  // 奇数和偶数位颠倒
			n = n >>> 2 & M2 | (n & M2) << 2;  // 2位和2位之间进行颠倒
			n = n >>> 4 & M4 | (n & M4) << 4;  // 4位和4位之间进行颠倒
			n = n >>> 8 & M8 | (n & M8) << 8;  // 4位和4位之间进行颠倒
			return n >>> 16 | n << 16;  // 两个16位进行颠倒
		}

		public static void main(String[] args) {
			int i = reverseBitsI(43261596);
			System.out.println(i);
		}
	}

	/**
	 * 201. 数字范围按位与
	 * 给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
	 * 示例 1：
	 * 输入：left = 5, right = 7
	 * 输出：4
	 * 示例 2：
	 * 输入：left = 0, right = 0
	 * 输出：0
	 * 示例 3：
	 * 输入：left = 1, right = 2147483647
	 * 输出：0
	 * 提示：
	 * 0 <= left <= right <= 231 - 1
	 */
	public static class RangeBitwiseAnd {

		/**
		 * left= 5 right=7
		 * [0101,0110,0111]
		 * 在某一位上,如果范围的开始和结束不同,则在这个范围上,肯定会出现这一位为0和1的场景;
		 * 那么与时,这一位肯定为0;所以本题可以简化成范围的开始和结束数字中,相同的前缀位
		 * <p>
		 * 这里有个细节,如何找到公共前缀,在将两个二进制位进行 右移的同时,当这两个数相等,则说明只剩下公共前缀了****
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static int rangeBitwiseAnd(int left, int right) {
			int num = 0;
			while (left < right) {
				left >>= 1;
				right >>= 1;
				num++;
			}
			return left << num;
		}

		/**
		 * 消除掉最右边的1
		 * 对于范围[m,n] 清除n最右边的1直到n<=m这个时候,剩下的就是公共前缀,其余位都为0
		 *
		 * @param left
		 * @param right
		 * @return
		 */
		public static int rangeBitwiseAndI(int left, int right) {
			while (left < right) {
				right &= right - 1;
			}
			return right;
		}

		public static void main(String[] args) {
			System.out.println(rangeBitwiseAndI(5, 7));
		}
	}
}


