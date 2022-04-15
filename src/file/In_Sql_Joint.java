package file;

import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @Description in sql 连接
 * @Date 2021-12-30 9:27
 * @Created by wangjie
 */
public class In_Sql_Joint {

	public static void main(String[] args) {

		Path path = Paths.get("C:\\Users\\Administrator\\Desktop", "waybill-20220116.txt");
		String waybill_no = getInSql(500, "WAYBILL_NO", path);
		System.out.println(waybill_no);
	}

	private static String getInSql(int limit, String filedName, Path path) {
		String str = "";
		StringBuilder stringBuilder = new StringBuilder();
		try (Stream<String> lines = Files.lines(path)) {
			AtomicInteger flag = new AtomicInteger(0);
			lines.forEach(x -> {
				if (StrUtil.isNotEmpty(x)) {
					if (flag.get() % limit == 0) {  //放在一个in中
						stringBuilder.append(filedName).append(" in (");
					}
					String str1 = (flag.get() % limit == 0) ? "'" + x + "'" : ",'" + x + "'";
					stringBuilder.append(str1).append("\r\n");
					if (flag.get() % limit == limit - 1) {
						stringBuilder.append(") or ");
					}
					flag.getAndIncrement();
				}
			});
			str = stringBuilder.toString();
			if (str.endsWith("or ")) {
				str = str.substring(0, str.length() - 5);
			}
			str = str.concat(")");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
