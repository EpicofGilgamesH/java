package en_de;

/**
 * @Description TODO
 * @Date 2021-09-30 10:58
 * @Created by wangjie
 */


public class Parent {
	public static String i = "a";
	public String j = "b";

	public Parent() {
		System.out.println("c");
	}

	static {
		System.out.println("d");
	}

	{
		System.out.println("e");
	}

	public static void main(String[] args) {
		Parent p = new Parent();
		System.out.println("");
	}
}


