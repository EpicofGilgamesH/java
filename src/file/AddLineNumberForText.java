package file;

import java.io.*;

public class AddLineNumberForText {

	public static void main(String[] args) {
		// 1. 定义文件路径
		String inputFile = "D:\\Users\\Desktop\\文件\\large.txt";   // 源文件（需提前创建）
		String outputFile = "D:\\Users\\Desktop\\文件\\copy.txt";  // 目标输出文件

		try (
				// 2. 初始化输入输出流（自动关闭资源）
				BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
		) {
			String line;
			int lineNumber = 1;  // 行号计数器

			// 3. 逐行读取处理
			while ((line = reader.readLine()) != null) {
				// 4. 添加行号并写入新文件
				String numberedLine = String.format("%d. %s", lineNumber, line);
				writer.write(numberedLine);
				writer.newLine();  // 写入换行符
				lineNumber++;
			}
			System.out.println("行号添加完成！输出至: " + outputFile);

		} catch (FileNotFoundException e) {
			System.err.println("文件不存在: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("IO错误: " + e.getMessage());
		}
	}
}
