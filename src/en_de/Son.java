package en_de;

/**
 * @Description TODO
 * @Date 2021-09-30 11:00
 * @Created by wangjie
 */
public class Son extends Parent {
	public static String i = "a";
	public String j = "b";

	public Son() {
		System.out.println("c");
	}

	static {
		System.out.println("d");
	}

	{
		System.out.println("e");
	}

	public static void main(String[] args) {

		String a = Son.i;
	}
}

