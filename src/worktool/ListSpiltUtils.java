package worktool;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Description TODO
 * @Date 2022-03-29 9:50
 * @Created by wangjie
 */
public class ListSpiltUtils {

	/**
	 * 集合分割场景
	 * in 查询时最多支持1000条,此方法对该场景的查询进行分割
	 *
	 * @param list 输入list
	 * @param func 执行的查询
	 * @param <R>  返回类型
	 * @return
	 */
	public static <T, R> List<R> batchSelect(List<T> list, int count, Function<List<T>, List<R>> func) {
		List<R> result = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(list)) {
			boolean flag;
			int i = 0;
			int l = list.size();
			do {
				int j = i + count;
				flag = (j < l);
				List<T> ts = list.subList(i, flag ? j : l);
				List<R> apply = func.apply(ts);
				result.addAll(apply);
				i = j;
			}
			while (flag);
		}
		return result;
	}
}
