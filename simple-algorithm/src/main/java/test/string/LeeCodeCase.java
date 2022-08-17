package test.string;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description LeeCode Case
 * @Date 2022-08-16 11:27
 * @Created by wangjie
 */
public class LeeCodeCase {

	//344. 反转字符串
	//编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
	//不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。

	/**
	 * @param s
	 */
	public static void reverseString(char[] s) {
		//直接双指针交换
		int n = s.length - 1;
		for (int i = 0, j = n; i < j; ++i, --j) {
			swap(s, i, j);
		}
	}

	/**
	 * 元素交换
	 *
	 * @param s
	 * @param a
	 * @param b
	 */
	private static void swap(char[] s, int a, int b) {
		char temp = s[a];
		s[a] = s[b];
		s[b] = temp;
	}

	//151. 颠倒字符串中的单词
	//给你一个字符串 s ，颠倒字符串中 单词 的顺序。
	//单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
	//返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
	//注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
	public String reverseWords(String s) {
		s = s.trim();
		List<String> list = Arrays.asList(s.split("\\s+"));
		Collections.reverse(list);
		return String.join("", list);
	}


	public static void main(String[] args) {
		char[] s = {'h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd'};
		reverseString(s);
		System.out.println("");
	}
}
