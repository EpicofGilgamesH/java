package string;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GetJavaClassFieldsUtil {

	private static List<String> getClazzFields(Class<?> clazz) {
		List<String> list = new ArrayList<>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().contains("serialVersionUID")) continue;
			list.add(field.getName());
		}
		return list;
	}

	private static String generatorSelect(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		list.forEach(x -> {
			sb.append(StringUtil.camelToUnderscore(x)).append(",");
		});
		return sb.substring(0, sb.length() - 1);
	}

	public static void main(String[] args) {
		List<String> clazzFields = getClazzFields(GlobalSellerPageVO.class);
		String s = generatorSelect(clazzFields);
		System.out.println(s);
	}
}
