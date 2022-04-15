package gc;

import lombok.Data;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * @Description orika 引发metaspace oom 问题
 * @Date 2021-12-20 16:02
 * @Created by wangjie
 */
public class OrikaMetaspaceOOM {

	private final static MapperFactory ORIKA = new DefaultMapperFactory.Builder().build();

	public static void main(String[] args) {

		while (System.currentTimeMillis() > 1021000) {
			Source source = new Source();
			source.setName("aaa");
			source.setAge(18);
			ORIKA.classMap(Source.class, Des.class)
					.field("name", "name1")
					.field("age", "age1")
					.byDefault()
					.register();
			Des des = ORIKA.getMapperFacade().map(source, Des.class);
			System.out.println(des.toString());
		}
	}

	@Data
	static class Source {

		private String name;

		private int age;

		public String toString() {
			return "name:" + name + ",age:" + age;
		}
	}

	@Data
	static class Des {
		private String name1;

		private int age1;

		public String toString() {
			return "name1:" + name1 + ",age1:" + age1;
		}
	}
}
