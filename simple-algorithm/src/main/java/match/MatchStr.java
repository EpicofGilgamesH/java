package match;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 字符串匹配简单算法
 * @Date 2021-06-08 17:53
 * @Created by wangjie
 */
public class MatchStr {

	public final static int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	public final static char[] ALPHABETS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	public final static Map<Character, Integer> MAP = new HashMap<>(26);

	/**
	 * bf Brute Force暴力匹配算法
	 * 从主串(a 长度m)中依次查找是否存在匹配的模式串(b 长度n) 主串从i=0开始 匹配 [i,i+n] i+n<=m
	 * 时间复杂度 O(m*n)
	 *
	 * @param a 主串
	 * @param b 模式串
	 * @return
	 */
	public static int bf(String a, String b) {
		char[] ac = a.toCharArray();
		char[] bc = b.toCharArray();
		int al = ac.length;
		int bl = bc.length;
		for (int i = 0; i < al - bl + 1; i++) {
			boolean flag = true;
			for (int j = 0; j < bl; j++) {
				//比较ac中[i,i+n] 和 bc中[i,i+n]是否都匹配
				if (bc[j] != ac[i + j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 匹配的思路就是将主串分解成m-n+1个子串,然后与模式串进行一一匹配
	 * RK匹配采用的思路是Hash,将所有子串和模式串进行Hash转换成一个整形数,然后比较数的大小;这样时间复杂度为O(m-n+1)即O(n)
	 * 当Hash冲突时,需直接进行比较该子串和模式串的值;当冲突较高时,时间复杂度会退化,直到O(m*n)
	 * 此处采用简单的Hash算法
	 * [a-z] 由质数组成 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97 101
	 * 子串[a,b,c,d] Hash公式 hash=2+3+5+7  交换字符顺序hash值一样
	 * 此处使用HashMap来存放hash值对应关系,会导致效率变慢?
	 *
	 * @param a 主串(长度m)
	 * @param b 模式串(长度n)
	 * @return
	 */
	public static int rk(String a, String b) {
		int m = a.length();
		int n = b.length();
		int l = m - n + 1;
		//模式串hash
		int bHash = getHash(b);
		for (int i = 0; i < l; i++) {
			String substring = a.substring(i, i + n);
			if (bHash == getHash(substring) && b.equals(substring)) {
				return i;
			}
		}

		return -1;
	}

	public static int getHash(String str) {
		if (MAP.size() <= 0) {
			initMap();
		}
		char[] chars = str.toCharArray();
		int code = 0;
		for (int i = 0; i < chars.length; i++) {
			code += MAP.get(chars[i]);
		}
		return code;
	}

	public static void initMap() {
		for (int i = 0; i < 26; i++) {
			MAP.put(ALPHABETS[i], PRIMES[i]);
		}
	}


	/**
	 * 主串                模式串
	 * / d  a  b  c /    / c  a /
	 * / e  f  a  d /    / e  f /
	 * / c  c  a  f /
	 * / d  e  f  c /
	 * <p>
	 * a[m][n]           b[i][j]
	 * 主串可以分解成多少个相对于的子串?
	 * 从左到右分解 ->x [0,m-i+1]
	 * 从上到下分解 ->y [0,n-j+1]
	 * 子串的取值 [x][y] -->> [x+1...x+i][y+1...y+j]
	 * 子串HASH方式: x轴向后移动一位 直接相加 ;y轴向后移动一位 *x轴的个数然后相加
	 * 此处不考虑Long类型的长度问题
	 * eg:
	 * / c  a /
	 * / e  f /
	 * hash= 5+2+11*2+13*2=55
	 *
	 * @param a 主串二维数组(矩阵)
	 * @param b 模式串二维数组
	 * @return
	 */
	public static int[] arrayRk(char[][] a, char[][] b) {
		int m = a.length;
		int n = a[0].length;
		int i = b.length;
		int j = b[0].length;

		//获取模式串hashcode
		long code = multidimensionalArrayHashcode(b);

		int[] re = new int[]{-1, -1};
		//获取所有子串
		for (int x = 0; x < m - i + 1; x++) {
			for (int y = 0; y < n - j + 1; y++) {
				//先从第一行的0位置开始,沿x轴往后推,取子集的hashcode
				char[][] c = new char[i][j];
				for (int k = 0; k < i; k++) {
					for (int l = 0; l < j; l++) {
						c[k][l] = a[x + k][y + l];
					}
				}
				long ma = multidimensionalArrayHashcode(c);
				if (ma == code) {
					//二次比较
					if (equlas(b, c)) {
						re[0] = x;
						re[1] = y;
						return re;
					}
				}
			}
		}
		return re;
	}

	/**
	 * 获取二维数组hashcode
	 *
	 * @param arr
	 * @return
	 */
	public static long multidimensionalArrayHashcode(char[][] arr) {
		if (MAP.size() <= 0) {
			initMap();
		}
		long code = 0;
		for (int y = 0; y < arr.length; y++) {
			for (int x = 0; x < arr[0].length; x++) {
				code += MAP.get(arr[x][y]) * (y + 1);
			}
		}
		return code;
	}

	/**
	 * 比较两个元素数量一致的多维数组是否完全相等
	 *
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean equlas(char[][] c1, char[][] c2) {
		int x = c1.length;
		int y = c1[0].length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (c1[i][j] != c2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}


	public static void main(String[] args) {

		/*String a = "aabbccddeeff";
		String b = "abcd";
		long start = System.currentTimeMillis();
		int bf = -1;
		for (int i = 0; i < 10000; i++) {
			bf = bf(a, b);
		}
		System.out.println("值为:" + bf + "耗时:" + (System.currentTimeMillis() - start));

		long start1 = System.currentTimeMillis();
		int rk = -1;
		for (int i = 0; i < 10000; i++) {
			rk = rk(a, b);
		}
		System.out.println("值为1:" + rk + "耗时:" + (System.currentTimeMillis() - start1));*/

		/*int[][] intArray = new int[][]{{1, 2}, {2, 3, 4}, {3, 4}, {5, 6}};
		int i1 = intArray[1][0];
		int i = intArray.length;
		System.out.println(i1);*/


		char[][] a = {{'d', 'e', 'c', 'd'}, {'a', 'f', 'c', 'e'}, {'b', 'a', 'a', 'f'}, {'c', 'd', 'f', 'c'}};
		char[][] b = {{'c', 'e'}, {'a', 'f'}};

		int[] ints = arrayRk(a, b);
		System.out.println(ints);
	}
}
