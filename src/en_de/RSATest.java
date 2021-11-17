package en_de;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;

/**
 * @Description TODO
 * @Date 2021-09-29 14:43
 * @Created by wangjie
 */
public class RSATest {

	public static void main(String[] args) {
		RSAUtil.Sender sender = RSAUtil.Sender.getInstance();
		RSAUtil.Receiver receiver = new RSAUtil.Receiver();

		//发送方
		/*String str = "abcdefg";
		String privateStr = sender.encrypt(str);
		String publicKey = sender.getKey().getPublicKey();

		//接收方
		String publicStr = receiver.decrypt(privateStr, publicKey);
		System.out.println("接收方解密结果:" + publicStr);

		//---------------------------------------------
		//接收方
		String str1 = "df10ef8509dc176d733d59549e7dbfaf";
		String privateStr1 = receiver.encrypt(str1, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEpLOUX/HPWldff+wDNKCXN7O0KUJ/jgKLp+/RwjnA+d6qCDKYWx8mNhCAXDT9e36Q1S8zIusXpym/wDpGhvHslMenjTCjtUaNrTF36DKHaXRpS1Ba6XPW+p4nTYbaouXo5dbo/sqFM8jv0LFA1634fbaOjBR5MJMeUOQdrv8MvQIDAQAB");

		//发送方
		String publicStr1 = sender.decrypt(privateStr1);
		System.out.println("发送方解密结果:" + publicStr1);


		Integer a = null;
		int b = a;
		System.out.println(b);
		String pwd="Aa123456";*/

		MD5 md5 = MD5.create();
		String s = md5.digestHex("123456");
		System.out.println("md5:" + s);
		String encrypt = receiver.encrypt("df10ef8509dc176d733d59549e7dbfaf", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbC3q5fAKQxJF+iTQrrStEzcw2V6WYO08jMDcgcBklQY7OCLvWJ7xAMiP2tqnx0/kH5d2ppfxx1nP2R2wxxGIXI4fn4uw2ZifIlmvZe5BzvA8xiRMqS4SZVjzWeiPsD1uPriDxZTWHi24RiUBFx/Bs9tQv2PnD51G4sKLmKDCfGQIDAQAB");
		System.out.println(encrypt);
		String decrypt = sender.decrypt("Don7jexU1Gt7qwYT8951Ta5hc35nAIgOg9Oo5Faa1IcBIwVS1YJQG5i533GFVkDOhs6M6vklNxYWj/5Yg/WfQhpYsd8yuPSe14v60jdPTJTdrViDdWk+9JXfEdoaNudI0vEWb1VDCeOypKDGG4mpospICygVC9uFN4ktH+izicc=");
		if (StrUtil.isEmpty(decrypt)) { //解密错误
			throw new RuntimeException("error");
		}
		System.out.println("解密后:" + decrypt);
	}
}
