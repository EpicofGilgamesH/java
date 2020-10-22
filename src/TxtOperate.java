import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 将txt以空格分隔符进行对齐操作,并对首行进行合并单元格
 * @Date 2020-09-27 16:21
 * @Created by wangjie
 */
public class TxtOperate {

	public static void main(String[] args) throws IOException {

		//读
		List<String> list = new ArrayList();
		File file = new File("C:\\Users\\Administrator\\Desktop\\networkmanagement-url.txt");
		BufferedReader bufferedReader = null;
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			bufferedReader = new BufferedReader(reader);
			//按行读取字符串
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				list.add(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			bufferedReader.close();
			reader.close();
		}

		//获取每行分隔后最大的字数
		int max1 = 0;
		int max2 = 0;
		int max3 = 0;
		List<Index> indexList = new ArrayList<>();
		for (String str : list) {
			String[] split = str.split("\\s");
			int length1 = split[0].length();
			int length2 = split[1].length();
			int length3 = split[2].length();

			if (length1 > max1)
				max1 = length1;
			if (length2 > max2)
				max2 = length2;
			if (length3 > max3)
				max3 = length3;

			Index index = new Index();
			index.setS1(split[0]);
			index.setS2(split[1]);
			index.setS3(split[2]);
			if (split.length >= 4)
				index.setS4(split[3]);
			else
				index.setS4("");
			indexList.add(index);
		}

		//通过最大字数,计算每一行的分隔空格的数量
		int max = 5;
		for (Index index : indexList) {
			index.setC1(max + max1 - index.getS1().length());
			index.setC2(max + max2 - index.getS2().length());
			index.setC3(max + max3 - index.getS3().length());
		}

		//写
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\networkmanagement-url-result.txt"));
		String s1 = "";
		for (Index index : indexList) {
			String s;
			if (index.getS1() != "" && index.getS1().equals(s1)) {
				s = getSpan(index.getC1() + index.getS1().length()) + index.getS2() + getSpan(index.getC2()) + index.getS3() + getSpan(index.getC3()) + index.getS4();
			} else {
				s = index.getS1() + getSpan(index.getC1()) + index.getS2() + getSpan(index.getC2()) + index.getS3() + getSpan(index.getC3()) + index.getS4();
			}
			s1 = index.getS1();
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
		System.out.println("成功");
	}

	private static String getSpan(int count) {
		String result = "";
		for (int i = 0; i < count; i++) {
			result += " ";
		}
		return result;
	}

	static class Index {
		private String s1;
		private String s2;
		private String s3;
		private String s4;
		private int c1;
		private int c2;
		private int c3;

		public String getS1() {
			return s1;
		}

		public void setS1(String s1) {
			this.s1 = s1;
		}

		public String getS2() {
			return s2;
		}

		public void setS2(String s2) {
			this.s2 = s2;
		}

		public String getS3() {
			return s3;
		}

		public void setS3(String s3) {
			this.s3 = s3;
		}

		public String getS4() {
			return s4;
		}

		public void setS4(String s4) {
			this.s4 = s4;
		}

		public int getC1() {
			return c1;
		}

		public void setC1(int c1) {
			this.c1 = c1;
		}

		public int getC2() {
			return c2;
		}

		public void setC2(int c2) {
			this.c2 = c2;
		}

		public int getC3() {
			return c3;
		}

		public void setC3(int c3) {
			this.c3 = c3;
		}
	}
}
