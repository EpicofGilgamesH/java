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

public class JoinSql {

	private static List<String> read() {
		List<String> list = new ArrayList<>();
		String insert = "INSERT INTO user_center.com_buyer_relation (buyer_id, salesman_id, salesman_user_id," +
				" salesman_employee_number, gmt_create, gmt_modified, `type`, is_deleted, company_id, " +
				"enterprise_id) VALUES(%s, %s, %s, '%s', now(), now(), 1, 0, %s, %s);";
		Path path = Paths.get("D:\\Users\\Desktop", "buyer.txt");
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(x -> {
				list.add(String.format(insert, x, 20841, 438677, "CmRe6321", 223, 82));
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list;
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
		List<String> list = read();
		write(list);
	}
}
