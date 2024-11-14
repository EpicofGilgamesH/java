package file;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 读取txt文档
 */
public class ReadTxtContent {

	public List<Data> read() {
		List<String> list = new ArrayList<>();
		Path path = Paths.get("D:\\Users\\Desktop", "mongo数据.txt");
		try (Stream<String> lines = Files.lines(path)) {
			list = lines.collect(Collectors.toList());
		} catch (
				IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}
		return JSONArray.parseArray(sb.toString(), Data.class);
	}

	private void getData(List<Data> list) {
		List<String> sIds = list.stream().map(x->x.getFromAccount().split("-")[2]).collect(Collectors.toList());
		List<String> lIds = list.stream().map(x->x.getToAccount().split("-")[2]).collect(Collectors.toList());
		System.out.println(String.join(",", sIds));
		System.out.println("----------------------------------");
		System.out.println(String.join(",", lIds));
	}

	public static void main(String[] args) {
		ReadTxtContent txtContent = new ReadTxtContent();
		txtContent.getData(txtContent.read());
		System.out.println();
	}

	@lombok.Data
	static class Data {
		private String fromAccount;
		private String toAccount;
		private String msgTime;
		private String[] msgBody;
		private String msg;
	}

	@lombok.Data
	static class msgBody {
		private String MsgContent;
	}

	@lombok.Data
	static class MsgContent {
		private String Text;
	}
}
