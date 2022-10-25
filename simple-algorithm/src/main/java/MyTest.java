/**
 * @Description TODO
 * @Date 2021-09-23 16:54
 * @Created by wangjie
 */
public class MyTest {

/*
	public static String a = "";

	@sun.misc.Contended
	public static String b = "";

	@sun.misc.Contended
	public volatile String c = "";
*/


	public static void main(String[] args) {
		Integer a = 10;  //Integer a =
		Integer b = 10;
		Integer e = new Integer(10);

		int f = 10;
		int i = 10;

		Integer h = 10;

		Integer j = new Integer(10);

		Integer c = 222;
		Integer d = 222;
		System.out.println(a == b);
		System.out.println(c == d);

		System.out.println(a == e);

		System.out.println(f == i);

		System.out.println(i == h);

		System.out.println(10 == j);

		System.out.println(e==10);
	}
}