package worktool;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 字符串去重
 * @Date 2021-05-17 18:08
 * @Created by wangjie
 */
public class StringDiscont {

	public static void main(String[] args) {

		Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\翻译", "all.txt");
		List<String> list = new ArrayList<>();

		try (Stream lines = Files.lines(path)) {
			lines.forEach(x -> list.add(x.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		list.stream().sorted().collect(Collectors.toList());
		List<String> list1 = list.stream().distinct().collect(Collectors.toList());
		list1.forEach(System.out::println);

		List<String> keywordList=new ArrayList<>();
		keywordList.add("JT12345678901");
		String s = JSON.toJSONString(keywordList);
		System.out.println(s);
	}
}
