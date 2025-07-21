package math;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class USBUtil {


	static class UDisk {
		private final int id;        // U盘ID
		private final String brand;  // 品牌
		private final double price;  // 价格（元）
		private final int capacity;  // 容量（GB）

		public UDisk(int id, String brand, double price, int capacity) {
			this.id = id;
			this.brand = brand;
			this.price = price;
			this.capacity = capacity;
		}

		@Override
		public String toString() {
			return String.format("ID:%-2d | 品牌:%-6s | 价格:%-6.1f元 | 容量:%-3dGB",
					id, brand, price, capacity);
		}
	}

	public static void main(String[] args) {

		// 创建10个U盘测试数据
		List<UDisk> usbList = Arrays.asList(
				new UDisk(1, "SanDisk", 89.5, 64),
				new UDisk(2, "Kingston", 129.0, 128),
				new UDisk(3, "Samsung", 199.0, 256),
				new UDisk(4, "HP", 75.0, 32),
				new UDisk(5, "Toshiba", 159.0, 128),
				new UDisk(6, "Lexar", 229.0, 512),
				new UDisk(7, "PNY", 65.0, 32),
				new UDisk(8, "Verbatim", 179.0, 256),
				new UDisk(9, "Transcend", 115.0, 64),
				new UDisk(10, "Patriot", 299.0, 1024)
		);

		// 1. 按价格排序（使用Comparator定义排序规则）
		TreeMap<UDisk, Integer> priceMap = new TreeMap<>(Comparator.comparingDouble(d -> d.price));
		// 2. 按容量排序（使用Comparator定义排序规则）
		TreeMap<UDisk, Integer> capacityMap = new TreeMap<>(Comparator.comparingDouble(d -> d.capacity));
		// 添加数据到TreeMap
		for (UDisk disk : usbList) {
			priceMap.put(disk,0);
			capacityMap.put(disk,0);
		}
		// 输出按价格排序结果
		System.out.println("==================== 按价格排序 ====================");
		priceMap.keySet().forEach(System.out::println);
		// 输出按容量排序结果
		System.out.println("\n==================== 按容量排序 ====================");
		capacityMap.keySet().forEach(System.out::println);
	}
}
