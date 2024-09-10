package request;

import cn.hutool.http.HttpRequest;

public class SmsReport {

	public static void sendReport() {
		String json = "{\n" +
				"\t\"smsCount\": 1,\n" +
				"\t\"mobile\": \"+2349139355965\",\n" +
				"\t\"messageId\": \"100001200201240905061312278831\",\n" +
				"\t\"receiveTime\": \"2024-09-05 06:28:13\",\n" +
				"\t\"status\": \"UNDELIV\"\n" +
				"}";
		try {
			String response = HttpRequest.post("https://api.egatee.com/api/notify/common/sms/notifyReportMessage")
					.body(json)
					.execute()
					.body();
			System.out.println(response);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public static void main(String[] args) {
		sendReport();
		System.out.println();
	}
}
