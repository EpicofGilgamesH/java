/**
 * @Description TODO
 * @Date 2021-09-23 16:54
 * @Created by wangjie
 */
public class MyTest {

	public static String a = "";

	@sun.misc.Contended
	public static String b = "";

	@sun.misc.Contended
	public volatile String c = "";
}
