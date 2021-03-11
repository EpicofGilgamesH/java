import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @Date 2020-12-28 21:39
 * @Created by wangjie
 */
public class Requst1 {


	public static void main(String[] args) throws InterruptedException {
		File de = new File("C:\\Users\\Administrator\\Desktop\\00");
		ModuleNameEnums.execute(de);
	}
}
