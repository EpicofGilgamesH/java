package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @Description or in 切割数据
 * @Date 2021-01-04 9:45
 * @Created by wangjie
 */
public class OrInOperate {

	public static void main(String[] args) {
		Path filePath = Paths.get("C:\\Users\\Administrator\\Downloads\\", "fix.txt");
		String waybillNos = orInJoin(filePath, "WAYBILL_NO");
		System.out.println(waybillNos);
	}

	public static String orInJoin(Path filePath, String filed) {
		//java 1.8 文件流处理
		StringBuilder stringBuilder = new StringBuilder();
		Long size = 0L;
		try (Stream lines = Files.lines(filePath)) {
			size = lines.count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (Stream lines = Files.lines(filePath)) {
			stringBuilder.append(filed).append(" in (");
			AtomicInteger j = new AtomicInteger();
			Long finalSize = size;
			lines.forEach(x -> {
				int index = j.get();
				if (index != 0) {
					if (index % 1000 == 999) {
						stringBuilder.append(") or " + filed + " in (");
					} else {
						stringBuilder.append(",");
					}
				}
				stringBuilder.append("'" + x + "'");
				if (index == finalSize.intValue()) {
					stringBuilder.append(")");
				}
				j.incrementAndGet();
			});
			stringBuilder.append(")");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

}
