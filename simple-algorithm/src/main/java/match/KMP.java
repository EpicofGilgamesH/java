package match;

/**
 * @Description KMP匹配算法 需要多实践和思考
 * @Date 2021-07-26 10:17
 * @Created by wangjie
 */
public class KMP {

	public static void kmp(char[] s, char[] p) {
		int[] next = getNext(p);
		int tar = 0, pos = 0; //tar主串匹配索引 pos模式串匹配位置
		while (tar < s.length) {
			if (s[tar] == p[pos]) {
				tar++;
				pos++;
			} else if (pos != 0) {
				//有一些字符可以匹配,且出现坏字符时
				pos = next[pos - 1];
			} else { //模式串第一个字符不匹配,直接往后匹配下一位
				tar++;
			}
			if (pos == p.length) {
				System.out.println(tar - pos + 1);
				pos = next[pos - 1];
			}
		}
	}

	/**
	 * 获取模式串next数组
	 *
	 * @return
	 */
	public static int[] getNext(char[] p) {
		//初始化next数组
		int[] next = new int[p.length];
		//next 下标x 从1开始
		int x = 1;
		//模式串匹配元素下标 now
		int now = 0;
		while (x < p.length) {
			//如果p[now]==p[x] 则该前缀-后缀匹配可以往后拓展一位
			if (p[now] == p[x]) {
				now++;
				next[x] = now; //next的值就是匹配的最长长度,即now的值
				x++;
			} else if (now != 0) {
				//p[now]和p[x]不匹配,且now不为0 即不是第一个开始匹配的位置
				//缩小now的位置
				now = next[now - 1];
			} else {
				x++;
				next[x] = now; //now==0
			}
		}
		return next;
	}

	public static void main(String[] args) {
		char[] s = {'a', 'b', 'a', 'b', 'c', 'e', 'd', 'a', 'b', 'a', 'b'};
		char[] p = {'b', 'c', 'e', 'b', 'c'};

		kmp(s, p);
		System.out.println("");
	}
}
