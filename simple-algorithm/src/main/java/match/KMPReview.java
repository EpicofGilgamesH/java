package match;

/**
 * @Description kmp 复习
 * @Date 2022-04-21 9:29
 * @Created by wangjie
 */
public class KMPReview {

	//主串   s
	//模式串 p

	/**
	 * 获取模式串p的next数组 朴素方法
	 * LOCKER
	 * @param p
	 * @return
	 */
	private static int[] getNext(char[] p) {
		int l = p.length;
		int[] n = new int[l];
		for (int i = 0; i < l; i++) {
			int k = 0;
			for (int s = 0, e = i; s < e; s++, e--) {
				if (p[s] == p[e])
					k++;
				else break;
			}
			n[i] = k;
		}
		return n;
	}

	private static int[] getNext1(char[] p) {
		int l = p.length;
		int[] n = new int[l];
		n[0] = 0;
		int x = 1, i = 1;
		boolean flag = false;
		while (x < l) {
			if (flag) i = x;
			int m = n[i - 1];
			if (p[m] == p[x]) {
				n[x] = m + 1;
				x++;
				flag = true;
			} else {
				i = m;
				if (i == 0) {
					n[x] = 0;
					x++;
					flag = true;
				} else
					flag = false;
			}
		}
		return n;
	}

	private static int[] getNext2(char[] p) {
		int[] n = new int[p.length];
		n[0] = 0;
		//i:next数组游标  j:p模式串游标
		for (int i = 1, j = 0; i < p.length; i++) {
			while (j > 0 && p[i] != p[j]) {
				j = n[j - 1];
			}
			if (p[i] == p[j]) {
				j++;
			}
			n[i] = j;
		}
		return n;
	}

	/**
	 * kmp 匹配
	 *
	 * @param s
	 * @param p
	 * @return
	 */
	public static void kmp(char[] s, char[] p) {

		int[] next = getNext(p);
		int si = 0, pi = 0; //主串和模式串的匹配位置
		while (si < s.length) {
			if (s[si] == p[pi]) {
				si++;
				pi++;
			} else if (pi != 0) {
				pi = next[pi - 1];  //模式串匹配失败的前一个位置的next值 pi= p[next[pi-1]]
			} else {
				//pi==0 模式串第一个字符就不匹配,向后移动一位
				si++;
			}
			if (pi == p.length) {
				System.out.println(si - pi + 1);
				pi = next[pi - 1];
			}
		}
	}

	public static void main(String[] args) {
		char[] p = "abaabac".toCharArray();
		//int[] next = getNext(p);

		//kmp("ababaabaabac".toCharArray(), p);
		System.out.println("");


		char[] p1 = "abcabdddabcabc".toCharArray();
		int[] next1 = getNext2(p1);
		System.out.println("");
	}
}
