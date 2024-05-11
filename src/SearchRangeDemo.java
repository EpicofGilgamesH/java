import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRangeDemo {

	@Data
	static class Range {

		public Range(int s, int e) {
			this.start = s;
			this.end = e;
		}

		private int start;
		private int end;
	}

	/**
	 * 获取数据源
	 *
	 * @return
	 */
	private static List<Range> getDatasource() {

		return Arrays.asList(new Range(40, 50), new Range(3, 20), new Range(25, 33),
				new Range(70, 100));
	}


	/**
	 * 数据源排序
	 *
	 * @param list
	 */
	private static List<Range> sort(List<Range> list) {
		return list.stream().sorted(Comparator.comparingInt(o -> o.start)).collect(Collectors.toList());
	}

	/**
	 * 查询
	 */
	private static boolean search(Range r) {
		if (r.getEnd() < r.getStart()) {
			System.out.println("要校验的数据错误,请检查!");
			return false;
		}

		List<Range> datasource = getDatasource();
		datasource = sort(datasource);

		List<Range> availables = new ArrayList<>();
		// 规定范围必须为正整数
		int s = 1;
		Range first = datasource.get(0);
		if (first.getStart() > 1) {
			availables.add(new Range(1, first.getStart() - 1));
		}
		for (int i = 1; i < datasource.size(); i++) {
			Range pre = datasource.get(i - 1);
			Range cur = datasource.get(i);
			if (pre.getEnd() >= cur.getStart()) {
				System.out.println("源数据范围出现重叠,请检查!");
				return false;
			} else if (pre.getEnd() + 1 < cur.getStart()) {
				availables.add(new Range(pre.getEnd() + 1, cur.getStart() - 1));
			}
		}
		Range last = datasource.get(datasource.size() - 1);
		if (last.getEnd() != Integer.MAX_VALUE) {
			availables.add(new Range(last.getEnd() + 1, Integer.MAX_VALUE));
		}

		if (availables.size() == 0)
			return false;

		// search O(n) case 1
		for (int i = 0; i < availables.size(); i++) {
			Range cur = availables.get(i);
			if (cur.getStart() <= r.getStart() && cur.getEnd() >= r.getEnd()) {
				return true;
			}
		}
		return false;

	}

	public static void main(String[] args) {
		/*boolean search = search(new Range(22, 26));
		boolean search1 = search(new Range(1, 3));
		boolean search2 = search(new Range(34, 38));
		boolean search3 = search(new Range(43, 50));
		boolean search4 = search(new Range(60, 88));
		boolean search5 = search(new Range(212, 888));
		System.out.println(search);
		System.out.println(search1);
		System.out.println(search2);
		System.out.println(search3);
		System.out.println(search4);
		System.out.println(search5);*/
		Integer i1 = 4;
		Integer i2 = null;
		if (i1 > i2) {
			System.out.println("true");
		}
	}
}
