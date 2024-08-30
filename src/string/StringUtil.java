package string;

import cn.hutool.core.util.StrUtil;

public class StringUtil {

	/**
	 * 驼峰类型转下划线,首字母忽略
	 *
	 * @param in
	 * @return
	 */
	public static String camelToUnderscore(String in) {
		if (StrUtil.isBlank(in)) return in;
		StringBuilder sb = new StringBuilder();
		sb.append(in.charAt(0));
		for (int i = 1; i < in.length(); ++i) {
			char c = in.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_").append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underscoreToCamel(String in) {
		if (StrUtil.isBlank(in)) return in;
		StringBuilder sb = new StringBuilder();
		String[] split = in.split("_");
		sb.append(split[0]);
		for (int i = 1; i < split.length; ++i) {
			sb.append(Character.toUpperCase(split[i].charAt(0))).append(split[i].substring(1));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String a = "sellerNameAndId";
		String b;
		System.out.println(b = camelToUnderscore(a));
		System.out.println(underscoreToCamel(b));
	}
}
