package backtracking;

/**
 * @Description 正则匹配
 * 在匹配普通字符串时,还需匹配 * 多个或零个字符  ? 一个或零个字符
 * @Date 2022-05-10 11:10
 * @Created by wangjie
 */
public class RegexPattern {

	private boolean matched = false; //是否匹配
	private char[] regex; //正则表达式
	private int rl; //正则表达式长度

	public RegexPattern(char[] regex) {
		this.regex = regex;
		this.rl = regex.length;
	}

	public boolean rMatch(char[] text) {
		match(0, 0, text, text.length);
		return matched;
	}

	/**
	 * 匹配方法
	 *
	 * @param ri 正则匹配的位置index
	 * @param ti 被匹配文本的位置index
	 * @param t  被匹配文本
	 * @param tl 被匹配文本长度
	 */
	private void match(int ri, int ti, char[] text, int tl) {
		if (matched) return; //匹配到了,停止匹配
		if (ri == rl) { //到了正则的结尾
			if (ti == tl) matched = true; //如果被匹配的文本也到了结果,则匹配成功
			return; //停止匹配
		}
		if (regex[ri] == '*') {  // *通配符 匹配任意个字符
			for (int i = 0; i <= rl - ri; i++) {
				match(ri + 1, ti + i, text, tl);  //就是直接用被匹配文本的下一个字符,匹配正则的下一个字符
			}
		} else if (regex[ri] == '?') { // ?通配符 匹配 0 or 1 个字符
			match(ri + 1, ti, text, tl);  //匹配0个
			match(ri + 1, ti + 1, text, tl);  //匹配1个
		} else if (ri < rl && regex[ri] == text[ti]) { //普通字符匹配
			match(ri + 1, ti + 1, text, tl);
		}
	}

	public static void main(String[] args) {
		RegexPattern pattern = new RegexPattern("abc*def?123".toCharArray());

		boolean b = pattern.rMatch("abcdefg1245678".toCharArray());
		System.out.println(b);
	}
}
