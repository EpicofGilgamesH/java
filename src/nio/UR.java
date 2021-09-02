package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description TODO
 * @Date 2021-08-16 14:42
 * @Created by wangjie
 */
public class UR {

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://www.baidu.com");
		InputStream is = url.openStream();
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
		bufferedReader.close();
	}
}
