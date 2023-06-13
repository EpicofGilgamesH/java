package regex;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
		// \\w匹配字母、数字、下划线。等价于 [A-Za-z0-9_]
		Pattern pattern1 = Pattern.compile("(\\D*)(\\d+)(.*)");

		{
			String pattern = "zo*";
			String str = "z";
			boolean matches = str.matches(pattern);
			System.out.println(matches);
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
			String regex = "^[0-9a-zA-Z]{12,13}+(-100|-[0][0-9][0-" +
					"9])?$";

			String regex1 = "^\\d{4}-(0[1-9]|1[0-2])-([0-3][1-9]|3[0-1])(\\s|T)([0-1][1-9]|2[0-4]):[0-5]\\d:[0-5]\\d$";


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

		{
			//子单规则:-[002-100] 00-99 []
			String regex = "^(UTE|JTE)[0-9]{12}(-100|-[0][0][2-9]|-[0][1-9][0-9])?$";
			String regex2 = "^(UTE|JTE)[0-9]{12}(-[2-100]{3})?$";
			String str = "UT0000015348631";
			String str1 = "JTE123456789012-111";

			String str2 = "UTE12345678901";
			String str3 = "UTE12345678901-001";
			boolean matches = str.matches(regex);
			boolean matches1 = str1.matches(regex);

			boolean matches2 = str2.matches(regex);
			boolean matches3 = str3.matches(regex);
			System.out.println("");
		}

		{
			String regex = "^([12]\\d{11})|([2]\\d{11}([0][0][2-9]|[0][1-9][0-9]|[1-9][0-9][0-9]))|([R]\\d{8})$";
			String regex1 = "^([12]\\d{11}|[2]\\d{11}([0][0][2-9]|[0][1-9][0-9]|[1-9][0-9][0-9])|[R]\\d{8})$";
			String regex2 = "^[12]\\d{11}|[2]\\d{11}([0][0][2-9]|[0][1-9][0-9]|[1-9][0-9][0-9])|[R]\\d{8}$";
			String str = "abcR12345678";
			String str1 = "R12345678";
			boolean matches = str.matches(regex);
			boolean matches1 = str1.matches(regex);

			boolean matches2 = str.matches(regex1);
			boolean matches3 = str1.matches(regex1);

			boolean matches4 = str.matches(regex2);
			boolean matches5 = str1.matches(regex2);
			System.out.println("");
		}
		{
			List<String> waybillNos = new ArrayList<>();
			waybillNos.add("200000046665");
			System.out.println(JSON.toJSONString(waybillNos));
		}
		{
			String regex = "^(\\d{1,12})|(\\d{1,12}.\\d{1,2})$";
			String num = "0.00";
			String num1 = "0.001";
			String num2 = "2.00";
			String num3 = "2.010";
			String num4 = "2000000000000.010";

			String num5 = "123456789";
			String num6 = "132456.10";
			String num7 = "0.03";

			boolean matches = num.matches(regex);
			boolean matches1 = num1.matches(regex);
			boolean matches2 = num2.matches(regex);
			boolean matches3 = num3.matches(regex);
			boolean matches4 = num4.matches(regex);
			boolean matches5 = num5.matches(regex);
			boolean matches6 = num6.matches(regex);
			boolean matches7 = num7.matches(regex);

			BigDecimal bigDecimal = new BigDecimal(0);
			System.out.println("saaa" + bigDecimal);

			System.out.println("");

			{
				int i = 0;
				int j = 0;
				System.out.println(i++);
				System.out.println(++j);
			}
			{
				String x = " 内部系统错误";
				if (StrUtil.isEmpty(x)) {
					System.out.println("");
				}
				x = x.trim();
				if ((x = x.trim()).isEmpty()) {
					System.out.println("");
				}
				System.out.println("");
			}

			{
				String regex1 = "^[A-Za-z0-9]{10}$";
				String regex2 = "^[^\\u4e00-\\u9fa5]{0,30}$";
				String str = "qw243312^%&',;=><M|}{去";
				boolean matches8 = str.matches(regex2);
				/*String str1 = null;
				boolean matches9 = str1.matches(regex2);*/
				System.out.println("");
			}

			{
				//限制最大9个字符；只可输入数字和-；输入第五位时自动加杠；邮编示例：40301–110
				String regex11 = "^[0-9]{5}-[0-9]{1,3}$";
				String aa = "40301-110";
				boolean matches8 = aa.matches(regex11);
				System.out.println("");
			}

			{
				//UAT环境：UTE + 12 位数字，共计15位（UTE00至UTE19开头）
				//PRO环境：JTE + 12 位数字，共计15位（JE00至JTE19开头）

				String regex11 = "^UTE[0-1]{1}[0-9]{1}[0-9]{10}$";
				String regex12 = "^JTE[0-1]{1}[0-9]{1}[0-9]{10}$";

				String a = "UTE121234567890";
				String b = "UTE221234567890";
				boolean matches8 = a.matches(regex11);
				boolean matches9 = b.matches(regex11);
				System.out.println("");
			}

			{
				System.out.println(System.currentTimeMillis());
			}

			{
				String regex1 = "^[A-Za-z]{3}\\w*$";
				boolean matches8 = "abc12dfdafa42fdsafdsafafbgcvcxFFDSFD34".matches(regex1);
				System.out.println("");
			}

			{
				String regex3 = "^(([a-zA-Z\\s]*[\\s]*)|[\\s]*)$";
				boolean matches8 = "".matches(regex3);
				boolean matches9 = "   ".matches(regex3);
				boolean matches10 = "1234ffff".matches(regex3);
				boolean matches11 = "23343".matches(regex3);
				boolean matches12 = "fadflafa ".matches(regex3);
				boolean matches13 = "fdafafd".matches(regex3);
				boolean matches14 = "fafaf  fadfaf".matches(regex3);
				System.out.println("");
			}

			{
				String regex4 = "^\\d*$";
				boolean matches8 = "1016276".matches(regex4);
				System.out.println("");
			}

			{
				String regex5 = "\\$\\{(.*?)\\}";
				boolean matches9 = "lkfjafj${abc}eedd${dbf}111".matches(regex5);
			}
		}
	}

	public static void get(final List<String> list) {
		/*list = new ArrayList<>();
		list.add("123");*/
		//JT074601
	}
}
