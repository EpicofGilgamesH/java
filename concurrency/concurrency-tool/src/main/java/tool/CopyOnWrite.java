package tool;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWrite 写时复制
 * 比如String字符串是final类型,其属性value[]也是final类型,来保证对象不可变
 * 因为String对象同样满足读多写少的场景,在需要修改字符串对象时,比如replace方法,会调通new String()构建一个新的对象
 * 同时Long,Integer等都是基于Copy-on-Write方案来实现的
 */
public class CopyOnWrite {

	/**
	 * CopyOnWriteArrayList在添加一个元素时,会将整个集合复制一个新的,在数据量大的情况下是不建议使用的.
	 * 也是用在并发场景下,对于共享变量的处理
	 */
	private CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
	private CopyOnWriteArraySet<String> stringCopyOnWriteArraySet = new CopyOnWriteArraySet<>();
}
