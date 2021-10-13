package en_de;

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
		String str = "abcdefg";
		String privateStr = sender.encrypt(str);
		String publicKey = sender.getKey().getPublicKey();

		//接收方
		String publicStr = receiver.decrypt(privateStr, publicKey);
		System.out.println("接收方解密结果:" + publicStr);

		//---------------------------------------------
		//接收方
		String str1 = "gfedcba";
		String privateStr1 = receiver.encrypt(str1, publicKey);

		//发送方
		String publicStr1 = sender.decrypt(privateStr1);
		System.out.println("发送方解密结果:" + publicStr1);

	}
}
