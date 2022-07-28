

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description 获取字段的注释
 * @Date 2022-07-21 15:28
 * @Created by wangjie
 */
public class DocUtil {

	/**
	 * 会自动注入
	 */
	private static RootDoc rootDoc;

	/**
	 * 会自动调用这个方法
	 *
	 * @param root root
	 * @return true
	 */
	public static boolean start(RootDoc root) {
		rootDoc = root;
		return true;
	}

	/**
	 * 生成文档
	 *
	 * @param beanFilePath 注意这里是.java文件绝对路径
	 * @return 文档注释
	 */
	public static DocVo execute(String beanFilePath) {
		Main.execute(new String[]{"-doclet", DocUtil.class.getName(), "-docletpath",
				DocUtil.class.getResource("/").getPath(), "-encoding", "utf-8", beanFilePath});

		ClassDoc[] classes = rootDoc.classes();

		if (classes == null || classes.length == 0) {
			return null;
		}
		ClassDoc classDoc = classes[0];
		// 获取属性名称和注释
		FieldDoc[] fields = classDoc.fields(false);

		List<DocVo.FieldVO> fieldVOList = new ArrayList<>(fields.length);

		for (FieldDoc field : fields) {
			fieldVOList.add(new DocVo.FieldVO(field.name(), field.type().typeName(), field.commentText()));
		}
		return new DocVo(fieldVOList);
	}

	public static void main(String[] args) {
		execute("")
	}
}
