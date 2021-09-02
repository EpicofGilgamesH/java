package base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.WeakHashMap;

/**
 * @Description 类型
 * Class是一个类。
 * Class对象代表着正在运行的Java程序中的类型和接口：enum、annotation、array以及基本类型(8种)都可以用Class对象表示。
 * Class没有public构造器。
 * 当类被加载或者在class loader中被方法调用时，JVM会自动构造Class对象
 * <p>
 * Type是一个接口。
 * Type是Java中所有类型的父接口。
 * Type包括：raw type(原始类型，对应Class), parameterized types(参数化类型),  array types(数组类型),
 * type variables(类型变量) and   primitive    types(基本类型，对应Class).
 * Type是JDK1.5引入的，主要是为了泛型
 * @Date 2021-08-10 9:47
 * @Created by wangjie
 */
public class BaseType {

	public static void main(String[] args) {

		Type type = String.class;

		Class clazz = String.class;
	}


	public static class B<T, K> {

		private T t;
	}

	public static class Book extends B<Integer, String> {

		private Integer integer;

		public static void main(String[] args) {
			Book book = new Book();
			Class<?> aClass = book.getClass();
			//获取book的superClass
			Class<?> superclass = aClass.getSuperclass();

			Type type = aClass.getGenericSuperclass();

			Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
			System.out.println(actualTypeArguments[0].getTypeName() + ";" + actualTypeArguments[1].getTypeName());

			System.out.println("");

			WeakHashMap whm = new WeakHashMap();

			LocalDateTime now = LocalDateTime.ofInstant(Instant.ofEpochMilli(1628825258178L), TimeZone.getTimeZone("GMT+7").toZoneId());

			System.out.println(now);
		}
	}
}
