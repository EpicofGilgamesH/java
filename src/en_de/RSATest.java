package en_de;

import cn.hutool.crypto.digest.MD5;
import worktool.SqlOperate;

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

		/*MD5 md5 = MD5.create();
		String s = md5.digestHex("Aa123456");
		System.out.println("md5:" + s);
		String encrypt = receiver.encrypt("df10ef8509dc176d733d59549e7dbfaf", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbC3q5fAKQxJF+iTQrrStEzcw2V6WYO08jMDcgcBklQY7OCLvWJ7xAMiP2tqnx0/kH5d2ppfxx1nP2R2wxxGIXI4fn4uw2ZifIlmvZe5BzvA8xiRMqS4SZVjzWeiPsD1uPriDxZTWHi24RiUBFx/Bs9tQv2PnD51G4sKLmKDCfGQIDAQAB");
		System.out.println(encrypt);
		String decrypt = sender.decrypt("d11oA60QwZLlsJjRtfUJn4bHLSQNVzFv+3Sx9R07t+sq0TnfJDi5eXHxYu7KxEC0djVHrw9HJpZO/W3rguENR13POJ/MXB4kkGXilHtIF3U4qLcabZYr1ZytwJscfYH3blVa4sJoqMa5HE00XiEW51QBdr+ULEkJopF7S6mmVZc=");
		if (StrUtil.isEmpty(decrypt)) { //解密错误
			throw new RuntimeException("error");
		}
		System.out.println("解密后:" + decrypt);*/

		myDecrypt(sender, "kLiwJ8dDQBYedf1neECflRzfArUBspnR6u1R1jYQRz+LG1vphkE6zrSLQPLYUDsyi2Oxlgdth+y45JwspuN6ikJA9GBduKMr+FC4SEA93lV4fbywUgXIb1WuOMwXqw6e1G05YtqxhO5SI5SgCaRfISs7i08sRabK6BIUjezY4+w=");
		myDecrypt(sender, "YZ05LafB41oLIpabOsNbt53DPnpxhQc951Q2X/C4Q3qIN1n2eN63texF/Y6NNH+WFMHoRBCjd7M7+eFQSewsWBVAPwYvMQ1J9+ay+kVxeX43cQLG+qwNfc7BnKAOuE4RXdAasZL/ARRDpJSOaFCNOLEnV8XRpHJDP7goGWZ1EE8=");

		MD5 md5 = MD5.create();
		String aa1234567 = md5.digestHex("ops-bc=cb-spo=1646018786=40076325-2ad3-4a4a-a30d-0b56c8d76f28=age=200name=riversex=200");
		System.out.println("新md5:" + aa1234567);

		String aa123456 = md5.digestHex("Aa123456");
		System.out.println("旧md5:" + aa123456);
		System.out.println("md5:"+md5.digestHex("JT074601"));

		long l = System.currentTimeMillis() / 1000;
		//1646016654
		//1646016565
		System.out.println(l);
	}


	private static void myDecrypt(RSAUtil.Sender sender, String str) {
		String s = sender.decryptStaic(str, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJMAYb1/GTIJ0O42ZT3NuHuY22yjoZD2AmHLX380OQhJ53IvYBV1iagB0Mf79h//ZAcoru3kFlTJUER2PBZH54cTja/Mas3dMnFd5/hx6cVCRdbqmLEjCUAWA9Vsj0GWLXbaWtaNxSN4IFZcISlptyVp8PFjPNgTxymbNx2+cJXpAgMBAAECgYBn/KUnf3k7RGZfnGsRxSzzgbX2q+nmuaAFve/X9AFEM8NdqtW1WGgASQh/72S0Td1ckxLrhaWmZKI7S8hFmqfUdm6bMyvM6nSqebmKcJOFDRCXYosHXSF9aIJh2QAeuQtly/ZCiUYiTX1O4CSeOEaXZgyHblBfHVK7AHelbPeoAQJBAOmu7XzEvvk3AemU0A6xru6AK713z7GbqPxNxTcmGkDPjkyaAxF8Yz+0pPWuZ2Fn2S5sXPu6eYlPbIyYRERYROECQQChCkNthGVDqJ9OT7Bk0hkQG4WynXJLm9QYskBMMdjINncznJwuACZhJzIv9b+1UhUp3rZyxnVv0cR2BZ1T8WoJAkAZwiZ7clR6tA2J6dVlSUC3GXm40NQcB8SAyzHJ+nE6Y6aT2wYrj0KBazAjxK2wlvnhnCBDvzJWaGLWvJIJ1pEBAkEAialzYHBX/FFt9QysFlN/d0R/suNiq/2GfqIJ0tidnvDoTydmXvBj3pMaFON3wPFtBADNaCn8g90Gm3lLN1HcuQJAUShB3VD/O2FtYHoBf+3aZ8FbQjZO7Ooojmi5GLBfAMCkv5pRgprSK4w7xuuv/57TtnmWDeHRs0QDUOWOc4TvEw==");
		System.out.println("解密后:" + s);


	}
}
