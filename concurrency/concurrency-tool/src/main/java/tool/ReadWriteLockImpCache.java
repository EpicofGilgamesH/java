package tool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于读写锁实现缓存
 */
public class ReadWriteLockImpCache<K, V> {

	private final Map<K, V> map = new HashMap<>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	/**
	 * 读缓存
	 *
	 * @param k
	 * @return
	 */
	public V get(K k) {
		V v = null;
		r.lock();
		try {
			v = map.get(k);
		} finally {
			r.unlock();
		}
		return v;
	}

	public void put(K k, V v) {
		w.lock();
		try {
			map.put(k, v);
		} finally {
			w.unlock();
		}
	}
}
