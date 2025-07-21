package math;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分数计算
 */
public class ScoreCalculator {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入成绩字符串（例：数学87分，物理76分，英语96分）:");
		String input = scanner.nextLine(); // 获取整行文本
		scanner.close();

		Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)分"); // 匹配数字+“分”字
		Matcher matcher = pattern.matcher(input);
		double total = 0;
		int count = 0;

		while (matcher.find()) {
			double score = Double.parseDouble(matcher.group(1)); // 提取数字部分
			total += score;
			count++;
			System.out.println(score); // 输出单科成绩：87.0, 76.0, 96.0
		}
		scanner.close();

		System.out.printf("总分: %.1f分\n", total);      // 总分: 259.0分
		System.out.printf("平均分: %.2f分\n", total / count); // 平均分: 86.33分
	}
}
