import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @Date 2020-12-28 17:14
 * @Created by wangjie
 */
public class Requst20 {

	public static void main(String[] args) throws InterruptedException {
		File de = new File("C:\\Users\\Administrator\\Desktop\\19");
		ModuleNameEnums.execute(de);
	}
}
