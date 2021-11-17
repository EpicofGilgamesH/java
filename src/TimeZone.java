import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

/**
 * @Description 时区与夏时令
 * @Date 2021-11-03 9:30
 * @Created by wangjie
 */
public class TimeZone {

	public static void main(String[] args) {

		//墨西哥夏时令
		/*LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("Mexico/General"));
		System.out.println(localDateTime.toString());

		LocalDateTime now = LocalDateTime.now(ZoneId.of("Mexico/General"));
		System.out.println(now.toString());*/
		{
			//2021-10-03 北京时间 GTM+8
			LocalDateTime dateTime1 = LocalDateTime.of(2021, 10, 3, 10, 0, 0);
			//对应墨西哥时间
			ZonedDateTime sh = dateTime1.atZone(ZoneId.of("Asia/Shanghai"));
			ZonedDateTime zonedDateTime = sh.withZoneSameInstant(ZoneId.of("Mexico/General"));
			System.out.println("墨西哥:" + zonedDateTime);

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
	}
}
