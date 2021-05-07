import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2021-04-16 16:40
 * @Created by wangjie
 */
public class ReplaceStr {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("UT0000036941634");
		list.add("UT0000015361498");
		System.out.println(JSON.toJSONString(list));
	}
}
