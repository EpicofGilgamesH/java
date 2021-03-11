package rabbit;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;

import java.util.List;

/**
 * @Description 文件切割, 切割文件/切割文件夹,将文件切割成指定行数/指定大小
 * 针对运单号的切割
 * @Date 2020-12-28 14:40
 * @Created by wangjie
 */
public class FileCut {

	private static final String targetPath = "C:\\Users\\Administrator\\Desktop\\file3\\";

	public static void main(String[] args) throws Exception {

		File targetFile = new File(targetPath);
		if (!targetFile.exists() && !targetFile.isDirectory()) {
			targetFile.mkdir();
		}
		String path = "C:\\Users\\Administrator\\Desktop\\data\\data02\\";
		File file = new File(path);
		List<File> list = new ArrayList<>();
		//获取源文件夹下所有的文件
		getFiles(list, file);

		System.out.println("111");
		int index = 0;
		for (File fi : list) {
			//index = splitFile(fi, 50000, index, x -> x.toUpperCase().startsWith("JT") && x.length() == 15);
			index = splitFile1(fi, 0.9f, index, x -> x.toUpperCase().startsWith("JT") && x.length() == 15);
		}
		System.out.println("result:"/* + waybillNo.length()*/);
	}

	//将文件切割成指定行数
	private static int splitFile(File file, int size, int index, Predicate<String> predicate) throws Exception {

		if (!file.exists()) {
			throw new Exception("目标路径错误:" + file.getName());
		}
		int i = 0;
		String str = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getTargetPath("result", index, ".txt")), "UTF-8"));

		while ((str = reader.readLine()) != null) {
			if (!StrUtil.isEmpty(str)) {
				if (predicate.test(str)) {
					writer.write(str);
					writer.newLine();
					if (i % size == (size - 1)) {
						writer.flush();
						writer.close();
						index++;
						writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getTargetPath("result", index, ".txt")), "UTF-8"));
					}
				}
			}
			i++;
		}
		writer.flush();
		writer.close();
		return ++index;
	}

	//将文件切割成指定大小,单位M
	private static int splitFile1(File file, float memory, int index, Predicate<String> predicate) throws Exception {
		if (!file.exists()) {
			throw new Exception("目标路径错误:" + file.getName());
		}
		int i = 0;
		String str = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getTargetPath("result", index, ".txt")), "UTF-8"));
		int count = 0;
		while ((str = reader.readLine()) != null) {
			if (!StrUtil.isEmpty(str)) {
				if (predicate.test(str)) {
					byte[] content = str.getBytes();
					count += content.length;
					writer.write(str);
					writer.newLine();
					if (count >= memory * 1024 * 1024) {
						writer.flush();
						writer.close();
						index++;
						count = 0;
						writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getTargetPath("result", index, ".txt")), "UTF-8"));
					}
				}
			}
			i++;
		}
		writer.flush();
		writer.close();
		return ++index;
	}

	//生成文件名
	private static String getTargetPath(String prefix, int index, String extension) {
		String indexStr = "";
		if (index < 10) {
			indexStr = "0" + index;
		} else {
			indexStr = String.valueOf(index);
		}
		return targetPath + prefix + "-" + indexStr + ".txt";
	}

	//获取所有目标文件
	private static void getFiles(List<File> list, File sourceFile) {
		if (sourceFile.isFile()) {
			list.add(sourceFile);
		} else {
			//文件夹递归获取文件
			File[] files = sourceFile.listFiles();
			for (File fi : files) {
				getFiles(list, fi);
			}
		}
	}

}
