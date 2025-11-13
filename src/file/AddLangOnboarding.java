package file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AddLangOnboarding {

	/**
	 * 获取新语言文本,按照顺序
	 *
	 * @param filePath
	 * @return
	 */
	public static List<String> executeLang(Path filePath) {
		List<String> newLang = new ArrayList<>();
		// java 1.8 文件流处理
		try (Stream<String> lines = Files.lines(filePath)) {
			lines.forEach(newLang::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newLang;
	}

	/**
	 * 处理sql,生成Lang的sql,按照顺序
	 *
	 * @param filePath
	 */
	public static List<String> executeSql(Path filePath, List<String> langs) {
		List<String> list = new ArrayList<>();
		List<String> res = new ArrayList<>();
		try (Stream<String> lines = Files.lines(filePath)) {
			lines.forEach(list::add);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			int start = s.indexOf("zh_CN") + 8;
			int end = s.indexOf(",", start);
			String pre = s.substring(0, start);
			String next = s.substring(end);
			String replace = pre + "'" + langs.get(i) + "'" + next;
			replace = replace.replace("zh_CN", "pt-AO");
			res.add(replace);
		}
		return res;
	}

	private static void write(List<String> list) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("D:\\Users\\Desktop\\write.txt");
			FileChannel channel = fos.getChannel();
			for (String str : list) {
				ByteBuffer src = StandardCharsets.UTF_8.encode(str);
				// 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
				System.out.println("初始化容量和limit：" + src.capacity() + ","
						+ src.limit());
				int length;
				while ((length = channel.write(src)) != 0) {
					/*
					 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
					 */
					System.out.println("写入长度:" + length);
				}
				ByteBuffer newLine = StandardCharsets.UTF_8.encode("\r\n");
				channel.write(newLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		Path filePath = Paths.get("D:\\Users\\Desktop", "lang.txt");
		List<String> langs = executeLang(filePath);
		Path filePath1 = Paths.get("D:\\Users\\Desktop", "sql.txt");
		List<String> strings = executeSql(filePath1, langs);
		write(strings);
		System.out.println("complete.");
	}
}
