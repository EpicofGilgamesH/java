package string;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * ?????????
 */
public class BinaryToString {

	public static void main(String[] args) {
		byte[] b;


		Map<String, String> map = new HashMap<>();
		map.put("a", "123");
		map.put("b", "456");
		map.put("c", "789");
		String s = JSON.toJSONString(map);
		byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
		System.out.println(Arrays.toString(bytes));

		String a = "\\x1f\\x8b\\x08\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\xed\\x9c\\xd30\\x10\\x86\\xff\\xce";
		String[] split = a.split("\\\\");
		byte[] bytes1 = new byte[split.length-1];
		for (int i = 0; i < bytes1.length; i++) {
			bytes1[i]= (byte)Integer.parseInt(split[i+1].substring(1),16);
		}
		String s1 = Base64.getEncoder().encodeToString(bytes1);
		System.out.println(s1);
	}


}
