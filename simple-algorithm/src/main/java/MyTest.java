import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
		//Integer e = new Integer(10);

		int f = 10;
		int i = 10;

		Integer h = 10;

		Integer j = new Integer(10);

		Integer c = 222;
		Integer d = 222;
		System.out.println(a == b);
		System.out.println(c == d);

		//System.out.println(a == e);

		System.out.println(f == i);

		System.out.println(i == h);

		System.out.println(10 == j);

		//System.out.println(e == 10);

		String egatee2022 = MD5.create().digestHex("egatee2022", StandardCharsets.UTF_8);
		System.out.println(egatee2022);

		String json = "{\n" +
				"    \"@type\": \"java.util.HashMap\",\n" +
				"    \"toAccount\": \"IM-USER-406094\",\n" +
				"    \"fromAccount\": \"IM-USER-437177\"\n" +
				"}";

		/*HashMap<String, String> accountMap = JSONUtil.toBean(JSONUtil.toJsonStr(null), HashMap.class);
		String fromAccount = accountMap.get("fromAccount").split("-")[2];
		System.out.println(fromAccount);*/

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("complete.");
	}
}