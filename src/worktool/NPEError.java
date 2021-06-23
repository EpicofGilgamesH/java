package worktool;

import com.google.gson.Gson;

import java.util.*;

/**
 * @Description null pointer exception 空指针异常发生情况
 * @Date 2020/7/31 14:41
 * @Created by wangjie
 */
public class NPEError {

	private final static Gson GSON = new Gson();

	//1.返回类型为基本数据类型,return 包装数据类型的对象时,自动拆箱有可能产生NPE.
	public static int fun1() {
		/*Integer integer = new Integer(1);*/
		Integer integer = null;
		return integer;
	}

	//2.数据库的查询结果可能为null

	//3.集合里的元素即使是isNotEmpty,取出的数据元素也可能为null
	//  所以集合的判空一般使用 list!=null&&list.size()>0 或者使用 hutool封装的工具类
	public static boolean fun2() {
		//List<String> list = new ArrayList<>();
		List<String> list = null;
		//与和或的判断方式
		//return list != null && list.size() > 0;
		return list == null || list.size() == 0;
	}

	//4.远程调用返回的结果对象,全部都要进行判空操作

	//5.在session中获取的数据,需要进行判空操作

	//6.属性获取,equals()方法调用等方法调用,需要进行判空操作

	//使用Optional防止以上问题的出现
	public static void main(String[] args) {
		//fun1();

		//fun2();

		/*Optional<Student> emptyOptional = Optional.empty();
		emptyOptional.get();*/

		/*Student student=null;
		Optional<Student> optionalStudent=Optional.of(student);*/

		/*Student student = null;
		//创建一个Optional对象,如果其value为空,则创建empty泛型对象
		Optional<Student> optionalStudent = Optional.ofNullable(student);
		optionalStudent.get();*/

		//orElse  orElseGet当optional的value为null时,则执行其方法
		/*Student student = null;
		Optional<Student> optionalStudent = Optional.ofNullable(student);
		optionalStudent.orElse(new Student("a", "12", true));
		optionalStudent.orElseGet(() -> new Student("a", "12", true));*/

		//此处要注意一个问题,orElse是无论optional是否为null,都要执行;orElseGet是在option不为null时不执行
		/*Optional<Student> optionalStudent1 = Optional.ofNullable(new Student("b", "15", false));
		Student student = optionalStudent1.orElse(new Student("", "", false));
		Student student1 = optionalStudent1.orElseGet(() -> new Student("", "", false));
		System.out.println("student" + GSON.toJson(student));
		System.out.println("student1" + GSON.toJson(student1));*/

		//检查处理
		/*Student student = get();
		Optional<Student> optionalStudent = Optional.ofNullable(student);
		//Assert.isTrue(optionalStudent.isPresent());
		if (optionalStudent.isPresent()) {
			String name = optionalStudent.get().getName();
		}
		//ifPresent()方法没有返回值,只能做一些耗时操作
		optionalStudent.ifPresent(x -> x.setName("123"));
		Student student1 = optionalStudent.orElseGet(NPEError::get);
		System.out.println("student1:" + GSON.toJson(student1));*/

		/*Student student = get();
		Optional<Student> optionalStudent = Optional.ofNullable(student);
		optionalStudent.orElseThrow(() -> new NoSuchElementException());

		String[] str = new String[]{"a", "b", "c", "d", "1"};
		Optional<String[]> opt = Optional.ofNullable(str);*/
		//filter
		/*String[] res = opt.filter(x -> {
			for (String s : x) {
				if (s.contains("a")) {
					return false;
				}
			}
			return true;
		}).get();*/
		String[] str = new String[]{"a", "b", "c", "d", "1"};
		//System.out.println(Arrays.toString(str));
		Optional<String> s = Optional.ofNullable(str).map(x -> x.length + "1");
		System.out.println(s.get());
		/*List<String> list = null;
		list.stream().forEach(x -> x.toCharArray());*/
	}

	private static Student get() {
		return new Student("", "", false);
	}


	static class Student {

		public Student(String name, String age, boolean gender) {
			this.name = name;
			this.age = age;
			this.gender = gender;
			System.out.println("创建student完成");
		}

		private String name;

		private String age;

		private boolean gender;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public boolean isGender() {
			return gender;
		}

		public void setGender(boolean gender) {
			this.gender = gender;
		}
	}
}
