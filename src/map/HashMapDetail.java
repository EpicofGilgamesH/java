package map;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description HashMap 源码详解
 * @Date 2022-10-21 11:15
 * @Created by wangjie
 */
public class HashMapDetail {

	public static void main(String[] args) {

		System.out.println("a".hashCode());
		System.out.println("b".hashCode());
		System.out.println("q".hashCode());
		System.out.println("12".hashCode());
		System.out.println("78".hashCode());

		//找到 (i=n-1)&hashcode 相同值的字符串元素 i即槽位
		int i = 15 & "a".hashCode();
		int j = 15 & "q".hashCode();
		int k = 15 & "12".hashCode();

		//制造冲突,让Entry放在链表上
		Map<String, Integer> map = new HashMap<>();
		map.put("a", 1);
		map.put("q", 2);
		map.put("12", 3);
		map.put("78", 4);

		//制造扩容
		map.put("1", 5);
		map.put("2", 6);
		map.put("3", 7);
		map.put("4", 8);
		map.put("5", 9);
		map.put("6", 10);
		map.put("7", 11);
		map.put("8", 12);
		map.put("9", 13);

		System.out.println("");
	}
}
