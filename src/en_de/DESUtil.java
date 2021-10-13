package en_de;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * des加解密 对称加密
 */
public class DESUtil {

	private static final Logger log = LoggerFactory.getLogger(DESUtil.class);

	/**
	 * DES加密操作
	 *
	 * @param source 要加密的源
	 * @param key
	 * @return
	 */
	public static String encrypt(String source, String key) {
		//强加密随机数生成器
		SecureRandom random = new SecureRandom();
		try {
			SecretKey secretKey = generateSecret(key);
			//加密对象
			Cipher cipher = Cipher.getInstance("DES");
			//初始化加密对象需要的属性
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
			//开始加密
			byte[] result = cipher.doFinal(source.getBytes());
			//Base64加密
			return new BASE64Encoder().encode(result);
		} catch (Exception e) {
			log.warn("RES加密失败key：{}", source, e);
		}
		return null;
	}

	/**
	 * 解密
	 *
	 * @param cryptograph 密文
	 * @param key         约定的密钥
	 * @return
	 */
	public static String decrypt(String cryptograph, String key) {
		//强加密随机生成器
		SecureRandom random = new SecureRandom();
		try {
			//定义私钥规则
			SecretKey secretkey = generateSecret(key);
			//创建加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secretkey, random);
			//Base64对
			byte[] result = new BASE64Decoder().decodeBuffer(cryptograph);
			return new String(cipher.doFinal(result));
		} catch (Exception e) {
			log.warn("RES解密失败key：{}", cryptograph, e);
		}
		return null;
	}


	/**
	 * 密钥规则
	 */
	public static SecretKey generateSecret(String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		//创建密钥规则
		DESKeySpec keySpec = new DESKeySpec(key.getBytes());
		//创建密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		//按照密钥规则生成密钥
		return keyFactory.generateSecret(keySpec);
	}


	public static void main(String[] args) {
		/*String source = "42108720210301XXXX";
		String key = "JT_yl123456";
		String result = encrypt(source, key);
		//加密结果
		System.out.println(result);
		//解密
		System.out.println(decrypt(result, key));*/

		int a1 = 0;
		int a2 = 0;
		int b = --a1;
		int c = a2--;
		System.out.println("");
	}

}
