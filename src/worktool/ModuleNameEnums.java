package worktool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Class Description
 *
 * @author
 * @version 1.0.0
 * @date 2020-05-11 10:04
 */
public enum ModuleNameEnums {

	arrival("arrival"),
	/*deliveryout("deliveryout"),
	sign("sign"),
	collection_proxy("collection_proxy"),
	cabinet_entering("cabinet_entering"),
	cabinet_deliveryout("cabinet_deliveryout"),
	cabinet_sign("cabinet_sign"),*/;

	ModuleNameEnums(String name) {
		this.name = name;
	}

	public final static String URL = "";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ModuleNameEnums of(String moduleName) {
		return Stream.of(ModuleNameEnums.values()).filter(e -> StrUtil.equalsIgnoreCase(e.getName(), moduleName)).findFirst().orElse(null);
	}

	public static void execute(File de) throws InterruptedException {
		if (de.exists() && de.isDirectory()) {
			File[] files = de.listFiles();
			for (File file : files) {
				for (ModuleNameEnums value : ModuleNameEnums.values()) {
					long start = System.currentTimeMillis();
					System.out.println("开始推送,推送时间:" + LocalDateTime.now() + ",推送文件名:" + file.getName() + ",推送类型:" + value);
					String result = HttpRequest.post(ModuleNameEnums.URL).form("file", file).form("module", value).contentType("multipart/form-data").execute().body();
					System.out.println("推送结束,推送文件名:" + file.getName() + ",推送类型:" + value + "推送结果:" + result + ",推送耗时:" + (System.currentTimeMillis() - start));
					System.out.println("------------------------------------------------------------------------");
					//TimeUnit.SECONDS.sleep(2);
				}
			}
		}
	}
}
