package collection;

/**
 * @Description 迭代器接口, 实现迭代操作
 * @Date 2021-07-29 9:59
 * @Created by wangjie
 */
public interface Iterators<E> {

	/**
	 * 迭代器下一个元素
	 *
	 * @return
	 */
	E next();

	/**
	 * 迭代器删除元素,未实现时,调用默认的remove方法,抛出不支持删除异常
	 */
	default void remove() {
		throw new UnsupportedOperationException("remove");
	}
}
