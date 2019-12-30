package cn.forest.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * Description: RSA加密
 * 
 * @version 2019年7月31日
 * @since JDK1.8
 */
public class RSAEncrypt {

	/**
	 * 
	 * Description: 获取私钥
	 * 
	 * @data 2019年7月31日
	 * @param
	 * @return
	 */
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥数据为空");
		}
	}
	
	/**
	 * 
	 * Description: 私钥解密
	 * 
	 * @data 2019年7月31日
	 * @param
	 * @return
	 */
	public static String decrypt(String privateKeyStr, String pwdText)
			throws Exception {
		if(privateKeyStr == null){
			throw new Exception("解密私钥为空, 请设置");
		}
		byte[] cipherData = Base64.decode(pwdText);
		RSAPrivateKey privateKey = loadPrivateKeyByStr(privateKeyStr);
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return new String(output);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("输入错误");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("密文数据已损坏");
		}
	}
	
	
	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey 公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();

			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("明文数据已损坏");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		String pwd = "123456";
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALTKaRAZXbtvBuz2XAzEBZrYskHTe+kItG1iE4B7B7k+Dk0fzM/i05qML81lrDvwMYYCu2fgfP6sDN+JLT8oIqsCAwEAAQ==";
		String enPwd = Base64.encode(encrypt(loadPublicKeyByStr(publicKey), pwd.getBytes()));
		System.out.println(enPwd);
		String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtMppEBldu28G7PZcDMQFmtiyQdN76Qi0bWITgHsHuT4OTR/Mz+LTmowvzWWsO/AxhgK7Z+B8/qwM34ktPygiqwIDAQABAkADnSAogDlNa9S5m9sPLkPmanp1smKBr/jbTuCQ6uSmoTxNXozzYWeyMBQHoX7QgkbDOdjiuqN+yec7SRAP6wwhAiEA5dk6X9gbhhVZlV/yvo/SxyIfsmKOQTiz1Bzm5XDFwEMCIQDJXEd2wJnBSdWKdb/WOJqgbF28scZE3+HwnQ5HnVABeQIgIHDynkiASgKFbGHofyEJnPWiB6mFbos0RoH1zPiH0MUCIBiZI0nIgX/xSxrdA5Bh3dHXKyRP+06SowFHzmTFPO7RAiEAm+HiRxmEKI6frQjKOmcymO99EUsAXwhBqEmXVJJs+cg=";
		System.out.println(decrypt(privateKey, enPwd));
	}
}
