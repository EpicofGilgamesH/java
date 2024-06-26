package file;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.ListUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadTxt {

	private static List<Long> readTxt() {
		List<Long> list = new ArrayList<>();
		Path path = Paths.get("D:\\Users\\Desktop", "userid.txt");
		try (Stream<String> lines = Files.lines(path)) {
			list = lines.filter(StrUtil::isNotBlank)
					.map(x -> Long.valueOf(x.trim()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static void writeTxt(List<List<Long>> list) {
		List<String> strs = list.stream().map(JSON::toJSONString).collect(Collectors.toList());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("D:\\Users\\Desktop\\write.txt");
			FileChannel channel = fos.getChannel();
			for (String str : strs) {
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
		List<Long> longs = readTxt();
		List<List<Long>> partition = ListUtils.partition(longs, 60);
		writeTxt(partition);
	}
}
