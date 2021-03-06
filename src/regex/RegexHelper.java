package regex;

import java.util.regex.Pattern;

/**
 * @Description 正则表达式入门
 * @Date 2021-06-23 14:32
 * @Created by wangjie
 */
public class RegexHelper {

	public static void main(String[] args) {
		{
			String pattern = "^[A-Za-z0-9_-]{3,15}$";

			String str = "runNNboo123-2_";
			boolean matches = str.matches(pattern);
			//System.out.println("是否匹配:" + matches);
		}
		//在 Java 中，\\ 表示：我要插入一个正则表达式的反斜线，所以其后的字符具有特殊的意义
		//表示一位数字的正则表达式是\\d 表示一个普通反斜杠是\\
		// \ 将下一字符标记为特殊字符、文本、反向引用或八进制转移符 eg: n匹配字符n ; \n匹配换行符; \\\\ 匹配\\; \\ (匹配 （
		// ^ 匹配输入字符串开始的位置.如果设置了 RegExp 对象的 Multiline 属性，^ 还会与"\n"或"\r"之后的位置匹配
		// $ 匹配输入字符结尾的位置.如果设置了 RegExp 对象的 Multiline 属性，$ 还会与"\n"或"\r"之前的位置匹配
		// * 零次或多次匹配前面的字符串或子表达式. eg: zo* 匹配 z 和zoo ; * 等效{0,}
		// + 一次或多次匹配前面的字符串或子表达式. eg: zo+ 匹配 zo 和 zoo ; 单不匹配z ; + 等效{1,}
		// ? 零次或一次匹配前面的字符串或子表达式. eg: do(es)? 匹配do 或者 does; ? 等效 {0,1}
		// {n} n是正整数,匹配n次. eg:o{2} 可以匹配oo
		// {n,} n是正整数,至少匹配n次.
		// {n,m} n,m是正整数,其中n<=m 至少匹配n次,至多匹配m次.
		// ? 当此字符紧随任何其他限定符(*、+、?、{n}、{n,}、{n,m})之后时，匹配模式是"非贪心的"."非贪心的"模式匹配搜索到的、尽可能短的字符串，而默认的"贪心的"模式匹配搜索到的、尽可能长的字符串。例如，在字符串"oooo"中，"o+?"只匹配单个"o"，而"o+"匹配所有"o"。
		// . 匹配除\r\n之外的任意字符.
		// x|y 匹配 x 或 y eg: z|food 匹配z 或 food ; (z|f)ood 匹配 zood或 food.
		// [xyz] 字符集,匹配包含的任一字符,eg: [abc]匹配 apple中的 a .
		// [^xyz] 反向字符集,匹配未包含的任何字符, eg: [abc]匹配 apple中的 p、p、l、e .
		// [a-z] 字符范围.
		// [^a-z]反向范围字符.
		// \b 匹配一个字边界，即字与空格间的位置; eg: "er\b"匹配"never"中的"er",但不匹配"verb"中的"er".
		// \B 非字边界匹配.
		// \d 数字字符匹配.等效于 [0-9]
		// \D 非数字字符匹配.等效于 [^0-9]

		Pattern pattern1 = Pattern.compile("(\\D*)(\\d+)(.*)");

		{
			String pattern = "zo*";
			String str = "z";
			boolean matches = str.matches(pattern);
			//System.out.println(matches);
		}
		{
			String pattern = "zo+";
			String str = "zooooo";
			boolean matches = str.matches(pattern);
			//System.out.println(matches);
		}
		{
			String pattern = "do(es)?";
			String str = "do";
			boolean matches = str.matches(pattern);
			//System.out.println(matches);
		}
		{
			String pattern = "o{2}";
			String str = "oo";
			boolean matches = str.matches(pattern);
			//System.out.println(matches);
		}
		{
			String str = "There once was a man from NewYork";
			/*Pattern pattern = Pattern.compile("once");
			Matcher matcher = pattern.matcher(str);
			String group = matcher.group();*/
			//System.out.println(group);
		}
		{
			String regex = "^[0-9a-zA-Z]{12,13}+(-100|-[0][0-9][0-9])?$";

			String str1 = "12345678912141";
			String str2 = "123456789121";
			String str3 = "abc123bdd456a77";
			String str4 = "123456789121-002";
			String str5 = "1ab4567891213-098";
			boolean matches = str1.matches(regex);
			boolean matches1 = str2.matches(regex);
			boolean matches2 = str3.matches(regex);
			boolean matches3 = str4.matches(regex);
			boolean matches4 = str5.matches(regex);
			System.out.println("");
		}

	}
}
