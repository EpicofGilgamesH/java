package file;

import cn.hutool.core.util.StrUtil;

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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratorInsertSql {


	private static List<Long> readTxt() {
		List<Long> list = new ArrayList<>();
		Path path = Paths.get("D:\\Users\\Desktop", "id.txt");
		try (Stream<String> lines = Files.lines(path)) {
			list = lines.filter(StrUtil::isNotBlank)
					.map(x -> Long.valueOf(x.trim()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}


	private static List<String> getSql(List<Long> list) {
		List<String> res = new ArrayList<>();
		String sql = "INSERT INTO user_center.uc_country_setting (country_id, item_key, label, remark, item_value, value_type, value_rule, is_hidden, dependency, sort, is_deleted, gmt_create, gmt_modified) VALUES(%s, 'sms_signature_salt', '短信接口签名盐', '指定index', '%s', 2, NULL, 0, NULL, 0, 0, now(),now());";
		for (Long l : list) {
			res.add(String.format(sql, l, generateSalt(13)));
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


	private static String generateSalt(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			Random r = new Random();
			sb.append(r.nextInt(10) + "");
		}
		return sb.toString();
	}


	public static void main(String[] args) {
		List<Long> ids = readTxt();
		List<String> sqls = getSql(ids);
		write(sqls);
	}
}
