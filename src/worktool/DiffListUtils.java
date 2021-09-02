package worktool;

import collection.MyArrayList;
import collection.MyList;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @Description List集合取差集工具
 * @Date 2021-06-04 14:26
 * @Created by wangjie
 */
public class DiffListUtils {

	/**
	 * 求list1集合中 不属于list中元素的集合(list1->list2 差集,返回list1)
	 *
	 * @param list1
	 * @param list2
	 * @param <E>
	 * @return
	 */
	public static <E, F> List<E> subtract(final List<? extends E> list1, final List<? extends F> list2,
	                                      Function<E, String> funcE, Function<F, String> funcF) {
		final int i = 1;
		final List<E> result = new ArrayList<>();
		final HashMap<String, Integer> map = new HashMap<>(list2.size());
		//将list2数据通过匹配条件作为key存放到map中
		for (final F f : list2) {
			String mapKey = funcF.apply(f);
			map.put(mapKey, i);
		}
		for (final E e : list1) {
			String mapKey = funcE.apply(e);
			//在map中不存在说明不是差集
			if (map.containsKey(mapKey)) {
				result.add(e);
			}
		}
		// call to GC
		list1.clear();
		list2.clear();
		map.clear();
		return result;
	}

	/**
	 * 求list1集合中 不属于list中元素的集合(list1->list2 差集,返回list1)
	 *
	 * @param list1
	 * @param list2
	 * @param pre
	 * @param <E>
	 * @param <F>
	 * @return
	 */
	public static <E, F> List<E> subtract1(final List<E> list1, final List<F> list2, BiPredicate<E, F> pre) {
		if ((list1 == null || list1.size() <= 0) || (list2 == null || list2.size() <= 0)) {
			return list1;
		}
		//获取要删除的下标
		List<Integer> deletes = new ArrayList<>();
		for (int i = 0; i < list1.size(); i++) {
			E e = list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				F f = list2.get(j);
				if (pre.test(e, f)) {
					deletes.add(i);
					break;
				}
			}
		}
		//进行删除操作
		for (int i = deletes.size() - 1; i >= 0; i--) {
			list1.remove(deletes.get(i).intValue());
		}
		// call to GC
		list2.clear();
		deletes.clear();
		return list1;
	}

	/**
	 * 求list1集合中 不属于list中元素的集合(list1->list2 差集,返回list1)
	 * 对remove环节进行优化
	 *
	 * @param list1
	 * @param list2
	 * @param pre
	 * @param <E>
	 * @param <F>
	 * @return
	 */
	public static <E, F> List<E> subtract2(final MyArrayList<E> list1, final MyArrayList<F> list2, BiPredicate<E, F> pre) {
		if ((list1 == null || list1.size() <= 0) || (list2 == null || list2.size() <= 0)) {
			return list1;
		}
		//获取要删除的下标
		List<Integer> deletes = new ArrayList<>();
		for (int i = 0; i < list1.size(); i++) {
			E e = list1.get(i);
			for (int j = 0; j < list2.size(); j++) {
				F f = list2.get(j);
				if (pre.test(e, f)) {
					deletes.add(i);
					break;
				}
			}
		}
		//进行删除操作
		list1.removeIndexes(deletes.stream().mapToInt(Integer::valueOf).toArray());
		// call to GC
		list2.clear();
		deletes.clear();
		return list1;
	}
}
