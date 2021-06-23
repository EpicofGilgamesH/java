package worktool;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2020-11-12 14:02
 * @Created by wangjie
 */
public class ListTest {

	public static void main(String[] args) throws InterruptedException {

		LocalDateTime now = LocalDateTime.now();

		long start2 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			CollectionUtils.subtract(getList1(now), getList2(now));
		}
		System.out.println("耗时1:" + (System.currentTimeMillis() - start2) + "ms");

		long start3 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			DiffListUtils.subtract(getList1(now), getList2(now),
					x -> x.getA() + "-" + x.getB() + "-" + x.getC().toString(),
					x -> x.getA() + "-" + x.getB() + "-" + x.getC().toString()
			);
		}
		System.out.println("耗时2:" + (System.currentTimeMillis() - start3) + "ms");

		long start4 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			DiffListUtils.subtract1(getList1(now), getList2(now),
					(x, y) -> x.getC().equals(y.getC())
							&& x.getB().equals(y.getB())
							&& x.getC().isEqual(y.getC()));
		}
		System.out.println("耗时3:" + (System.currentTimeMillis() - start4) + "ms");

		TimeUnit.SECONDS.sleep(10);
	}

	static class Node {
		private String a;
		private String b;
		private LocalDateTime c;

		public Node(String a, String b, LocalDateTime c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof Node)) {
				return false;
			}
			Node node = (Node) obj;
			return (StrUtil.equals(node.getA(), a)
					&& StrUtil.equals(node.getB(), b)
					&& node.getC().isEqual(c));
		}

		@Override
		public int hashCode() {
			int result = 17;
			result += 31 * a.hashCode();
			result += 31 * b.hashCode();
			result += 31 * c.hashCode();
			return result;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		public LocalDateTime getC() {
			return c;
		}

		public void setC(LocalDateTime c) {
			this.c = c;
		}

	}


	public static List<Node> getList1(LocalDateTime now) {
		List<Node> list1 = new ArrayList<>();
		list1.add(new Node("1", "2", now));
		list1.add(new Node("2", "3", now));
		list1.add(new Node("3", "4", now));
		list1.add(new Node("4", "5", now));
		list1.add(new Node("5", "6", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("7", "7", now));
		list1.add(new Node("8", "7", now));
		list1.add(new Node("9", "7", now));
		list1.add(new Node("10", "7", now));
		list1.add(new Node("11", "7", now));
		list1.add(new Node("12", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("12", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("12", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("6", "7", now));
		list1.add(new Node("7", "8", now));
		list1.add(new Node("8", "9", now));
		return list1;
	}

	public static List<Node> getList2(LocalDateTime now) {
		List<Node> list = new ArrayList<>();
		list.add(new Node("7", "8", now));
		list.add(new Node("8", "9", now));
		return list;
	}

	public static void tes2() {
		LocalDateTime now = LocalDateTime.now();
		DiffListUtils.subtract(getList1(now), getList2(now),
				x -> x.getA() + "-" + x.getB() + "-" + x.getC().toString(),
				x -> x.getA() + "-" + x.getB() + "-" + x.getC().toString()
		);
	}

	public static void test3() {
		LocalDateTime now = LocalDateTime.now();
		DiffListUtils.subtract1(getList1(now), getList2(now),
				(x, y) -> x.getC().equals(y.getC())
						&& x.getB().equals(y.getB())
						&& x.getC().isEqual(y.getC()));
	}

	public static void test1() {
		LocalDateTime now = LocalDateTime.now();
		CollectionUtils.subtract(getList1(now), getList2(now));
	}
}
