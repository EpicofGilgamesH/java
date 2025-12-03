package file;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


	private static List<Vo> readTxt() {
		List<Vo> list = new ArrayList<>();
		Path path = Paths.get("D:\\Users\\Desktop", "id.txt");
		try (Stream<String> lines = Files.lines(path)) {
			list = lines.filter(StrUtil::isNotBlank)
					.map(x -> {
						Vo vo = new Vo();
						String[] split = x.split("\t");
						if (split.length > 0) {
							vo = new Vo(split[2], split[3], split[4], split[5]);
						}
						return vo;
					}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}


	private static List<String> getSql(List<Vo> list) {
		List<String> res = new ArrayList<>();
		String sql = "INSERT INTO `quickbiz_base`.`gd_stock_alert_info_1` (`enterprise_id`, `company_id`, `type`, `goods_id`, `sku_id`, `warehouse_id`, `min_inventory_qty`, `gmt_create`, `gmt_modified`, `is_deleted`) VALUES (6046, 2719, 100023, 1, %s, %s, %s, %s, '2025-12-03 18:03:37', '2025-12-03 18:06:09', 0);";
		for (Vo l : list) {
			res.add(String.format(sql, l.getSpuId(), l.getSkuId(), l.getWareId(), l.getMiq()));
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

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class Vo {
		private String spuId;
		private String skuId;
		private String wareId;
		private String miq;
	}


	public static void main(String[] args) {
		List<Vo> ids = readTxt();
		List<String> sqls = getSql(ids);
		write(sqls);
	}
}
