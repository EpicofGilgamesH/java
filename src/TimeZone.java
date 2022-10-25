import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 时区与夏时令
 * @Date 2021-11-03 9:30
 * @Created by wangjie
 */
public class TimeZone {

	public static void main(String[] args) throws InterruptedException {

		//墨西哥夏时令
		/*LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("Mexico/General"));
		System.out.println(localDateTime.toString());

		LocalDateTime now = LocalDateTime.now(ZoneId.of("Mexico/General"));
		System.out.println(now.toString());*/
		{
			//2021-10-03 北京时间 GTM+8 2021-11-28 00:00:00
			LocalDateTime dateTime1 = LocalDateTime.of(2021, 12, 16, 15, 38, 53);
			//对应墨西哥时间
			ZonedDateTime sh = dateTime1.atZone(ZoneId.of("Asia/Shanghai"));
			ZonedDateTime zonedDateTime = sh.withZoneSameInstant(ZoneId.of("Brazil/East"));
			System.out.println("巴西:" + zonedDateTime);

			LocalDateTime dateTime2 = LocalDateTime.of(2021, 11, 3, 10, 0, 0);
			ZonedDateTime sh1 = dateTime2.atZone(ZoneId.of("Asia/Shanghai"));
			ZonedDateTime zonedDateTime1 = sh1.withZoneSameInstant(ZoneId.of("Mexico/General"));
			System.out.println("墨西哥:" + zonedDateTime1);
		}

		{
			//2021-10-03 北京时间 GTM+8
			LocalDateTime dateTime1 = LocalDateTime.of(2021, 10, 3, 10, 0, 0);
			//对应墨西哥时间
			ZonedDateTime sh = dateTime1.atZone(ZoneId.of("Asia/Shanghai"));
			ZonedDateTime zonedDateTime = sh.withZoneSameInstant(ZoneId.of("Europe/Paris"));
			System.out.println("埃及:" + zonedDateTime);

			LocalDateTime dateTime2 = LocalDateTime.of(2021, 11, 3, 10, 0, 0);
			ZonedDateTime sh1 = dateTime2.atZone(ZoneId.of("Asia/Shanghai"));
			ZonedDateTime zonedDateTime1 = sh1.withZoneSameInstant(ZoneId.of("Europe/Paris"));
			System.out.println("埃及:" + zonedDateTime1);

			String aaa="\"BC:LOGIN_FAILED_TIMES:xym002\"";
		}

		{
			LocalDateTime time = null;
			for (int i = 0; i < 10; i++) {
				TimeResult tr = getTime(time);
				time = tr.endTime;
				System.out.println(JSON.toJSONString(tr));
				//TimeUnit.SECONDS.sleep(2);
			}

		}

		{
			List<String> list = new ArrayList<>();

			List<String> list1 = null;
			list.addAll(list1);
			System.out.println("");
		}
	}

	private static TimeResult getTime(LocalDateTime lastEndTime) {
		LocalDateTime startTime;
		LocalDateTime endTime;

		LocalDateTime dbTime = LocalDateTime.now();
		dbTime = dbTime.minusMinutes(3);

		//获取上一次的结束时间  default
		lastEndTime = (lastEndTime == null)
				? LocalDateTime.of(2021, 12, 24, 15, 35, 0)
				: lastEndTime;

		startTime = lastEndTime;
		endTime = startTime.plusMinutes(30);
		endTime = endTime.isAfter(dbTime) ? dbTime : endTime;

		if (startTime.isEqual(endTime)) {
			return new TimeResult(null, null);
		}
		return new TimeResult(startTime, endTime);
	}

	static class TimeResult {

		public TimeResult(LocalDateTime startTime, LocalDateTime endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		}

		private LocalDateTime startTime;
		private LocalDateTime endTime;

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public LocalDateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}
	}
}
