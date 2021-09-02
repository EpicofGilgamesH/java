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
 * @Description 提取翻译日志
 * @Date 2021-08-31 16:12
 * @Created by wangjie
 */
public class ExtractStr {


	public static void main(String[] args) {
		Path path = Paths.get("C:\\Users\\Administrator\\Downloads", "导出3.csv");

		TreeSet<String> strSet = getStrSet(path);

		strSet.forEach(System.out::println);

	}

	private static TreeSet<String> getStrSet(Path filePath) {
		TreeSet<String> set = new TreeSet<>();
		try (Stream<String> lines = Files.lines(filePath)) {
			lines.forEach(x -> {
				String str;
				if (StrUtil.isNotEmpty(str = x.substring(64, x.length() - 1))
						&& isContainChineseCharacter(str)) {
					set.add(str);
				}
			});
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
