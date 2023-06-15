import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectDemo {

	/**
	 * db范围数据行
	 */
	@Data
	static class DBRangeData {

		public DBRangeData(int id, int start, int end) {
			this.id = id;
			this.start = start;
			this.end = end;
		}

		private int id;
		private int start;
		private int end;

		public String getKey() {
			return id + "_" + start + "_" + end;
		}
	}

	/**
	 * db数据行
	 */
	@Data
	static class DBData {
		private int id;

		private String name;
		private String des;
	}

	private final static RedisTemplate redisTemplate = new RedisTemplate();

	/**
	 * 获取数据行
	 *
	 * @return
	 */
	public static List<DBRangeData> getRange(int id) {

		String rangeKey = "range_" + id;
		List<DBRangeData> ranges;
		if ((ranges = redisTemplate.get(rangeKey)) == null) {
			// todo 查数据库,该id的所有版本范围数据,数据库查询的时候就可以排序
			ranges = Arrays.asList(new DBRangeData(1, 0, 100), new DBRangeData(1, 101, 200),
					new DBRangeData(1, 201, 300), new DBRangeData(1, 301, 500));
			redisTemplate.put(rangeKey, ranges, 10);
		}
		return ranges;
	}

	/**
	 * 通过版本号路由
	 *
	 * @return
	 */
	public static DBRangeData route(int id, int version) {
		List<DBRangeData> range = getRange(id);
		for (int i = 0; i < range.size(); i++) {
			int s = range.get(i).getStart();
			int e = range.get(i).getEnd();
			if (version >= s && version <= e) {
				return range.get(i);
			}
		}
		return null;
	}

	/**
	 * 查询实际数据,由于前一步路由,缓存数据量即是数据库数据量
	 *
	 * @param rd
	 * @return
	 */
	public static DBData getDBData(DBRangeData rd) {
		String key = rd.getKey();
		DBData dbData;
		if ((dbData = redisTemplate.get(key)) == null) {
			// todo 查询数据库,通过id,start,end三个字段精确查询
			dbData = new DBData();
			redisTemplate.put(key, dbData, 10);
		}
		return dbData;
	}

	public static void main(String[] args) {
		int id = 1, version = 210;

		DBRangeData route = route(id, version);
		if (route != null) {
			DBData dbData = getDBData(route);

			System.out.println(dbData.toString());
		}

	}

	/**
	 * 模拟缓存工具类
	 */
	static class RedisTemplate {

		public <T> void put(String key, T t, int ExpireTime) {
			return; // todo 模拟redisTemplate
		}

		public <T> T get(String key) {
			return null;
		}
	}
}
