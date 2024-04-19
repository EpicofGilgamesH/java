package file;

import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @Description 提取日志文件中的指定值
 * @Date 2021-08-31 16:12
 * @Created by wangjie
 */
public class ExtractStr {


	public static void main(String[] args) {
		Path path = Paths.get("D:\\Users\\Desktop", "abc.txt");

		TreeSet<String> strSet = getStrSet(path);

		System.out.println();
	}

	private static TreeSet<String> getStrSet(Path filePath) {
		TreeSet<String> set = new TreeSet<>();
		try (Stream<String> lines = Files.lines(filePath)) {
			StringBuilder sb = new StringBuilder();
			lines.forEach(x -> {
				Pattern phone = Pattern.compile("phoneNo:(\\d+),");
				Pattern userId = Pattern.compile("userId:(\\d+),");
				Pattern msg = Pattern.compile("respCode(.+?),"); // 最小匹配,非贪婪匹配********注意正则的规则所在
				Pattern time = Pattern.compile("\"time\":\"(.+)\"");
				Matcher matcher = phone.matcher(x);
				if (matcher.find()) {
					sb.append(matcher.group(1)).append("\t");
				}
				Matcher matcher1 = userId.matcher(x);
				if (matcher1.find()) {
					sb.append(matcher1.group(1)).append("\t");
				}
				Matcher matcher2 = msg.matcher(x);
				if (matcher2.find()) {
					String group = matcher2.group(1);
					if (StrUtil.isNotBlank(group)) {
						group = group.substring(5, group.length() - 2);
					}
					sb.append(group).append("\t");
				}
				Matcher matcher3 = time.matcher(x);
				if (matcher3.find()) {
					sb.append(matcher3.group(1)).append("\t");
				}
				sb.append("\r\n");
			});
			System.out.println(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}

	/**
	 * 检查字符串中是否含有中文
	 *
	 * @param str
	 * @return
	 */
	private static boolean isContainChineseCharacter(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
