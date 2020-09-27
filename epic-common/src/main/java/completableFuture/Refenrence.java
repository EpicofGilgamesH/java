package completableFuture;

import javax.print.PrintService;

/**
 * @Description TODO
 * @Date 2020-09-03 13:48
 * @Created by wangjie
 */
public class Refenrence {


	public static void main(String[] args) {

		Student student1 = new Student();
		student1.setName("jack");
		student1.setAge(18);
		print(student1);

		change(student1);

		print(student1);

	}

	public static void change(Student student) {
		student.setName("jim");
		student = new Student("jackson", 20);
		print(student);
	}

	public static void print(Student student) {
		System.out.println("结果：" + student.name + "," + student.age);
	}

	static class Student {
		private String name;
		private int age;

		public Student() {
		}

		public Student(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}
