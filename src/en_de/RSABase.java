/*
package en_de;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

*/
/**
 * @Description RAS加解密抽象父类
 * @Date 2021-09-29 14:07
 * @Created by wangjie
 *//*

public abstract class RSABase {
	private final static Logger log = LoggerFactory.getLogger(RSABase.class);

	protected RSABase(Mode mode) {
		init(mode);
	}

	//密钥对
	private Key key;

	protected Key getKey() {
		return key;
	}

	//初始化方法
	private void init(Mode mode) {
		if (mode == Mode.SENDER) {
			//生成RSA实例
			KeyPairGenerator keyPairGenerator;
			try {
				keyPairGenerator = KeyPairGenerator.getInstance("RSA");
				//初始化秘钥对生成器
				keyPairGenerator.initialize(1024, new SecureRandom());
				KeyPair keyPair = keyPairGenerator.generateKeyPair();
				RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
				RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
				key = new Key(Base64.encodeBase64String(rsaPublicKey.getEncoded()),
						Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
				//key = new Key(Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()),
				//		Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));
			} catch (NoSuchAlgorithmException e) {
				log.error("RSA初始化失败:{}", e.getMessage());
			}
		}
	}

	*/
/**
	 * 发送方用私钥进行加密
	 *
	 * @param str
	 * @return
	 *//*

	protected String encrypt(String str) {
		//Base64编码公钥
		String result = "";
		try {
			byte[] decode = Base64.decodeBase64(key.getPrivateKey());
			//byte[] decode = Base64.getDecoder().decode(key.getPrivateKey().getBytes());
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, priKey);
			result = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
			//result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception ex) {
			log.error("私钥加密错误:{}", ex.getMessage());
		}
		return result;
	}

	*/
/**
	 * 发送方用私钥进行解密
	 *
	 * @param str
	 * @param
	 * @return
	 *//*

	protected String decrypt(String str) {
		String result = "";
		try {
			//对需要解密的密文进行Base64解码
			byte[] bytes = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
			//byte[] bytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
			//Base64私钥
			byte[] decode = Base64.decodeBase64(key.getPrivateKey());
			//byte[] decode = Base64.getDecoder().decode(key.getPrivateKey());
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			result = new String(cipher.doFinal(bytes));
		} catch (Exception ex) {
			log.error("私钥解密错误:{}", ex.getMessage());
		}
		return result;
	}

	protected String decryptStaic(String str,String pKey) {
		String result = "";
		try {
			//对需要解密的密文进行Base64解码
			byte[] bytes = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
			//byte[] bytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
			//Base64私钥
			byte[] decode = Base64.decodeBase64(pKey);
			//byte[] decode = Base64.getDecoder().decode(key.getPrivateKey());
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			result = new String(cipher.doFinal(bytes));
		} catch (Exception ex) {
			log.error("私钥解密错误:{}", ex.getMessage());
		}
		return result;
	}

	*/
/**
	 * 接收方用公钥进行加密
	 *
	 * @param str
	 * @param publicKey
	 * @return
	 *//*

	protected String encrypt(String str, String publicKey) {
		//Base64编码公钥
		String result = "";
		try {
			byte[] decode = Base64.decodeBase64(publicKey);
			//byte[] decode = Base64.getDecoder().decode(publicKey);
			RSAPublicKey priKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, priKey);
			result = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
			//result = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception ex) {
			log.error("公钥加密错误:{}", ex.getMessage());
			System.out.println("");
		}
		return result;
	}

	*/
/**
	 * 接收方用公钥进行解密
	 *
	 * @param str
	 * @return
	 *//*

	protected String decrypt(String str, String publicKey) {
		String result = "";
		try {
			//对需要解密的密文进行Base64解码
			byte[] bytes = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
			//byte[] bytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
			//Base64私钥
			byte[] decode = Base64.decodeBase64(publicKey);
			//byte[] decode = Base64.getDecoder().decode(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decode));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pubKey);
			result = new String(cipher.doFinal(bytes));
		} catch (Exception ex) {
			log.error("公钥解密错误:{}", ex.getMessage());
		}
		return result;
	}

	@Data
	public static class Key {
		public Key(String k1, String k2) {
			this.publicKey = k1;
			this.privateKey = k2;
		}

		//公钥
		private String publicKey;
		//私钥
		private String privateKey;
	}

	public enum Mode {
		SENDER,
		RECEIVER
	}
}
*/
