import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;


/**
 * @Description GeneratorClazzFile
 * @Date 2022-09-09 14:52
 * @Created by wangjie
 */
@Data
public class GeneratorClazzFile {

	private List<Info> fields;
	private String clazzName;

	public static void main(String[] args) {
		/*GeneratorClazzFile generatorClazzFile = new GeneratorClazzFile();
		generatorClazzFile.readFile("C:\\Users\\Administrator\\Desktop\\pl.txt", "ProductListVo");
		String template = generatorClazzFile.getTemplate();
		System.out.println(template);*/
		StringBuilder s = new StringBuilder();
		try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\Administrator\\Desktop\\pl.txt"))) {
			//按数据行读取到流
			lines.forEach(x -> {
				String[] split = x.split(" ");
				s.append(split[0]).append(",").append("\r\n");

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s.toString());

	}

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("import lombok.Data;\n" +
				"\n" +
				"import java.io.Serializable;");
		sb.append("\r\n");

		sb.append("/**\n" +
				" * @Description ProductListVo\n" +
				" * @Date" + LocalDateTime.now().toString() + "\n" +
				" * @Created by wangjie\n" +
				" */");

		sb.append("@Data");
		sb.append("\r\n");
		sb.append("public class").append(clazzName).append(" ").append("implements Serializable {");
		sb.append("\r\n");
		sb.append("private static final long serialVersionUID = 1L;");
		sb.append("\r\n");
		//属性
		fields.forEach(x -> {
			sb.append("/**\n" +
					"* " + x.getDoc() + "\n" +
					"*/");
			sb.append("\r\n");
			sb.append("private").append(" ").append(x.getType()).append(" ").append(x.getFiled()).append(";");
			sb.append("\r\n");
		});
		sb.append("\r\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 读取类信息
	 *
	 * @param path
	 * @param name
	 */
	private void readFile(String url, String name) {
		clazzName = name;
		Path path = Paths.get(url);
		fields = new ArrayList<>();
		try (Stream<String> lines = Files.lines(path)) {
			//按数据行读取到流
			lines.forEach(x -> {
				String[] split = x.split("\t");
				List<String> list = Arrays.asList(split);
				Info info = new Info();
				info.setFiled(list.get(0));
				info.setType(getType(list.get(1)));
				if (list.size() > 3) {
					info.setDoc(list.get(3) + " " + list.get(2));
				} else
					info.setDoc(list.get(2));
				fields.add(info);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getType(String tp) {
		switch (tp) {
			case "string":
				return "String";
			case "date":
			case "datetime":
				return "LocalDateTime";
			case "float":
				return "Float";
			case "int":
			case "Int":
				return "Integer";
			case "array":
			case "Array":
				return "List";
			default:
				return tp;
		}
	}

	@Data
	static class Info {
		private String type;
		private String doc;
		private String filed;
	}


}
