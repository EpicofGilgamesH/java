
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2020-11-12 14:02
 * @Created by wangjie
 */
public class ListTest {

	public static void main(String[] args) {

		List<String> a = new ArrayList<>();
		a.add("a");
		a.add("b");
		a.add("c");
		a.add("d");
		a.add("e");
		a.add("f");
		List<String> b = new ArrayList<>();
		b.add("1");

		a.set(3,b.get(0));

		a.add("g");

		Map<String,String> map=null;
		int size = map.size();

		System.out.println(a);
	}
}
