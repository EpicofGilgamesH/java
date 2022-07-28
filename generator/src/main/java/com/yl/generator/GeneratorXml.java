package com.yl.generator;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description GeneratorXml
 * @Date 2022-07-15 17:04
 * @Created by wangjie
 */
public class GeneratorXml {

	private static Class<?> clazz = String.class;

	private static Info info;

	public static void main(String[] args) {
		init();
		pageList();
		getCount();
		save();
		batchSave();
		update();
		delete();
	}

	private static void pageList() {
		//Class<LogisticsProviders> logisticsProvidersClass = LogisticsProviders.class;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select t2.* from (\n" +
				"      select t1.*,rownum rn from (\n" +
				"      select * from");
		stringBuilder.append(" ").append(info.getTableName()).append(" t").append("\n")
				.append(" where ")
				.append("t.IS_ENABLE =1").append("\n")
				.append(" order by t.UPDATE_TIME)t1").append("\n")
				.append("where rownum <![CDATA[<=]]> #{dto.current}* #{dto.size})t2").append("\n")
				.append("where t2.rn > (#{dto.current}-1)* #{size}").append("\n");
		System.out.println(stringBuilder);
		System.out.println("------------------------------------------");
	}

	private static void getCount() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(1) from ")
				.append(info.getTableName()).append(" t").append("\n")
				.append("where t.IS_ENABLE =1").append("\n")
				.append(" ");
		System.out.println(stringBuilder);
		System.out.println("------------------------------------------");
	}

	private static void save() {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ")
				.append(info.getTableName()).append("\n")
				.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n")
				.append("CREATE_TIME,").append("\n");
		for (int i = 0; i < info.getColumnNames().size(); i++) {
			if (info.getColumnNames().get(i).equals("CREATE_TIME"))
				continue;
			sb.append("<if test=\"").append(info.getFieldNames().get(i)).append(" !=null and ")
					.append(info.getFieldNames().get(i)).append(" != ''\">").append("\n")
					.append(info.getColumnNames().get(i)).append(",").append("\n")
					.append(" </if>").append("\n");
		}
		sb.append("</trim>").append("\n")
				.append("values").append("\n")
				.append(" <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n")
				.append("sysdate,").append("\n");
		for (int i = 0; i < info.getColumnNames().size(); i++) {
			if (info.getColumnNames().get(i).equals("CREATE_TIME"))
				continue;
			sb.append("<if test=\"").append(info.getFieldNames().get(i)).append(" !=null and ")
					.append(info.getFieldNames().get(i)).append(" != ''\">").append("\n")
					.append("#{" + info.getFieldNames().get(i) + "}").append(",").append("\n")
					.append(" </if>").append("\n");
		}
		sb.append("</trim>").append("\n");
		System.out.println(sb);
		System.out.println("------------------------------------------");
	}

	private static void batchSave() {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into ").append(info.tableName).append("\n")
				.append("(");
		for (int i = 0; i < info.getColumnNames().size(); i++) {
			if (info.getColumnNames().get(i).equals("UPDATE_TIME"))
				continue;
			if (i == info.getColumnNames().size() - 1)
				sb.append(info.getColumnNames().get(i));
			else
				sb.append(info.getColumnNames().get(i)).append(",").append("\n");
		}
		sb.append(")").append("\n");
		sb.append("<foreach collection=\"entities\" item=\"item\" separator=\"union all\">").append("\n")
				.append("select").append("\n");
		for (int i = 0; i < info.getColumnNames().size(); i++) {
			if (info.getColumnNames().get(i).equals("UPDATE_TIME"))
				continue;
			if (i == info.getColumnNames().size() - 1)
				sb.append("#{").append("item.")
						.append(info.getFieldNames().get(i)).append(",").append("jdbcType=VARCHAR}")
						.append(" ").append(info.getColumnNames().get(i)).append("\n");
			else
				sb.append("#{").append("item.")
						.append(info.getFieldNames().get(i)).append(",").append("jdbcType=VARCHAR}")
						.append(" ").append(info.getColumnNames().get(i)).append(",").append("\n");

		}
		sb.append("from dual").append("\n");
		sb.append("</foreach>");
		System.out.println(sb);
		System.out.println("------------------------------------------");
	}

	private static void update() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(info.tableName).append("\n")
				.append("  <trim prefix=\"set\" suffixOverrides=\",\">").append("\n")
				.append("UPDATE_TIME = sysdate,").append("\n");
		for (int i = 0; i < info.getColumnNames().size(); i++) {
			if (info.getColumnNames().get(i).equals("UPDATE_TIME"))
				continue;
			sb.append("<if test=\"").append(info.getFieldNames().get(i)).append(" !=null and ")
					.append(info.getFieldNames().get(i)).append(" != ''\">").append("\n")
					.append(info.getColumnNames().get(i))
					.append(" = #{").append(info.getFieldNames().get(i)).append("},").append("\n")
					.append(" </if>").append("\n");
		}
		sb.append("  </trim>").append("\n");
		sb.append("   where ID = #{id}");
		System.out.println(sb);
		System.out.println("------------------------------------------");
	}

	private static void delete() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(info.tableName).append(" set ")
				.append("IS_ENABLE = #{isEnable}").append("\n")
				.append(" where ID = #{id}");
		System.out.println(sb);
		System.out.println("------------------------------------------");
	}

	private static void init() {
		Info info = new Info();
		Field[] declaredFields = clazz.getDeclaredFields();
		int l = declaredFields.length;
		List<String> fieldNames = new ArrayList<>();
		List<String> columnNames = new ArrayList<>();
		int i = 0;
		for (Field f : declaredFields) {
			if (f.getName().equals("serialVersionUID")) {
				continue;
			}

			TableField annotation = f.getAnnotation(TableField.class);
			if (annotation != null) {
				fieldNames.add(f.getName());
				columnNames.add(annotation.value());
				i++;
			}
		}
		TableName annotation = clazz.getAnnotation(TableName.class);
		String tableName = annotation.value();
		String pac = clazz.getName();
		info.setColumnNames(columnNames);
		info.setFieldNames(fieldNames);
		info.setTableName(tableName);
		info.setResultType(pac);
		GeneratorXml.info = info;
	}

	@Data
	static class Info {
		private List<String> fieldNames;
		private List<String> columnNames;
		private String tableName;
		private String resultType;
		private String current = "current";
		private String size = "size";
	}

	enum JdbcType {
		NVARCHAR,
		INTEGER,
		DATE
	}

}
