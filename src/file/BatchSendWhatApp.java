package file;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BatchSendWhatApp {

	private static List<String> readTelphones() {
		List<String> list = new ArrayList<>();
		Path path = Paths.get("D:\\Users\\Desktop", "phones.txt");
		try (Stream<String> lines = Files.lines(path)) {
			list = lines.filter(StrUtil::isNotBlank)
					.map(String::trim)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static void request(String phone) {
		JSONObject json = new JSONObject();
		json.put("phone_number", phone);
		json.put("device_id", "16f19175-cc28-4698-87e1-bfedf8e9c4c8");
		json.put("template_id", "65a57555-b418-4117-8170-b01926d04586");
		json.put("api_key", "TLWSk4VFDKiYA2Pw3Gxie3eP1Ibe6oDSlKwNuPILdmSfaxyT771NJ9cpEvuHB1");
		json.put("data", null);
		try {
			String response = HttpRequest.post("https://api.ng.termii.com/api/send/template")
					.body(json.toString())
					.execute()
					.body();
			System.out.println(response);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public static void main(String[] args) {
		List<String> strings = readTelphones();
		strings.forEach(BatchSendWhatApp::request);
	}
}
