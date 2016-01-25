package com.znsx.util.licence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAParams;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.util.Base64;

import sun.security.provider.DSAPrivateKey;
import sun.security.provider.DSAPublicKeyImpl;

import com.znsx.cms.persistent.model.License;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * Licence工具类<br />
 * 序列化后的公匙用在中心进行验证，私匙制作licence生成工具
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-4-11 下午04:16:00
 */
public class LicenceUtil {

	public static final String DEFAULT_SEED = "332023abde334920ffaed322232000111";

	/**
	 * 生成公匙和私匙
	 * 
	 * @param seed
	 *            种子
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> generateKey(String seed) throws Exception {
		Map<String, String> map = new HashMap<String, String>(2);
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
		SecureRandom random = new SecureRandom();
		random.setSeed(seed.getBytes("utf8"));
		keygen.initialize(1024, random);

		KeyPair keyPair = keygen.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		Base64 base64 = new Base64();
		String publicKeyString = new String(base64.encode(publicKey
				.getEncoded()), "utf8");
		String privateKeyString = new String(base64.encode(privateKey
				.getEncoded()), "utf8");
		// BASE64Encoder encoder = new BASE64Encoder();
		// map.put("public", encoder.encode(publicKey.getEncoded()));
		// map.put("private", encoder.encode(privateKey.getEncoded()));
		map.put("public", publicKeyString);
		map.put("private", privateKeyString);

		System.out.println("publicKey: " + map.get("public"));
		System.out.println("privateKey: " + map.get("private"));
		return map;
	}

	/**
	 * 生成DSA运算需要的独立的p,q,g,j,x,y几个值
	 * 
	 * @param seed
	 *            生成因子
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-8 下午4:45:26
	 */
	@SuppressWarnings("restriction")
	public static void genKey(String seed) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
		SecureRandom random = new SecureRandom();
		random.setSeed(seed.getBytes("utf8"));
		keygen.initialize(1024, random);

		KeyPair keyPair = keygen.generateKeyPair();
		DSAPublicKeyImpl publicKey = (DSAPublicKeyImpl) keyPair.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
		DSAParams dsaParams = privateKey.getParams();
		Base64 base64 = new Base64();
		String p = new String(base64.encode(dsaParams.getP().toByteArray()),
				"utf8");
		String q = new String(base64.encode(dsaParams.getQ().toByteArray()),
				"utf8");
		String g = new String(base64.encode(dsaParams.getG().toByteArray()),
				"utf8");
		String x = new String(base64.encode(privateKey.getX().toByteArray()),
				"utf8");
		String y = new String(base64.encode(publicKey.getY().toByteArray()),
				"utf8");
		System.out.println("P: " + p);
		System.out.println("Q: " + q);
		System.out.println("G: " + g);
		System.out.println("X: " + x);
		System.out.println("Y: " + y);

		String publicKeyString = new String(base64.encode(publicKey
				.getEncoded()), "utf8");
		String privateKeyString = new String(base64.encode(privateKey
				.getEncoded()), "utf8");
		System.err.println("public: " + publicKeyString);
		System.err.println("private: " + privateKeyString);

		File publicFile = new File("D:/binPublic.ky");
		File privateFile = new File("D:/binPrivate.ky");
		FileOutputStream out = new FileOutputStream(publicFile);
		out.write(publicKey.getEncoded());
		out.flush();
		out.close();
		out = new FileOutputStream(privateFile);
		out.write(privateKey.getEncoded());
		out.flush();
		out.close();
	}

	/**
	 * 根据数据，生成签名
	 * 
	 * @param data
	 *            数据信息
	 * @param privateKey
	 *            私匙序列化后的base64编码
	 * @return base64编码的签名信息
	 * @throws Exception
	 */
	public static String sign(String data, String privateKeyString)
			throws Exception {
		Base64 base64 = new Base64();
		// 私匙反序列化
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] bytes = decoder.decodeBuffer(privateKeyString);
		byte[] bytes = base64.decode(privateKeyString.getBytes("utf8"));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		PrivateKey privateKey = KeyFactory.getInstance("DSA").generatePrivate(
				keySpec);
		// 生成签名
		Signature signature = Signature.getInstance("DSA");
		signature.initSign(privateKey);
		signature.update(data.getBytes("utf8"));
		// return new BASE64Encoder().encode(signature.sign());
		return new String(base64.encode(signature.sign()), "utf8");
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 *            数据信息
	 * @param publicKeyString
	 *            公匙序列化后的base64编码
	 * @param signature
	 *            base64编码的签名信息
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String data, String publicKeyString,
			String signature) throws Exception {
		// 公匙反序列化
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] bytes = decoder.decodeBuffer(publicKeyString);
		Base64 base64 = new Base64();
		byte[] bytes = base64.decode(publicKeyString.getBytes("utf8"));
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		PublicKey publicKey = KeyFactory.getInstance("DSA").generatePublic(
				keySpec);
		// 验证
		Signature sign = Signature.getInstance("DSA");
		sign.initVerify(publicKey);
		sign.update(data.getBytes("utf8"));
		// return sign.verify(decoder.decodeBuffer(signature));
		return sign.verify(base64.decode(signature.getBytes("utf8")));
	}

	/**
	 * 采用二进制公匙进行签名验证
	 * 
	 * @param data
	 *            数据信息
	 * @param publicKey
	 *            2进制公匙
	 * @param signature
	 *            base64编码的签名信息
	 * @return
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-12 下午5:37:18
	 */
	public static boolean verifyBinKey(String data, byte[] publicKey,
			String signature) throws Exception {
		Base64 base64 = new Base64();
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		PublicKey pub = KeyFactory.getInstance("DSA").generatePublic(keySpec);
		// 验证
		Signature sign = Signature.getInstance("DSA");
		sign.initVerify(pub);
		sign.update(data.getBytes("utf8"));
		// return sign.verify(decoder.decodeBuffer(signature));
		return sign.verify(base64.decode(signature.getBytes("utf8")));
	}

	public static License parseLicense(InputStream in) {
		try {
			InputStreamReader reader = new InputStreamReader(in, "utf8");
			BufferedReader br = new BufferedReader(reader);
			String projectName = br.readLine();
			projectName = projectName.substring(projectName.indexOf(":") + 2);
			String linkMan = br.readLine();
			linkMan = linkMan.substring(linkMan.indexOf(":") + 2);
			String contact = br.readLine();
			contact = contact.substring(contact.indexOf(":") + 2);
			String userAmount = br.readLine();
			userAmount = userAmount.substring(userAmount.indexOf("=") + 2,
					userAmount.length());
			String cameraAmount = br.readLine();
			cameraAmount = cameraAmount.substring(
					cameraAmount.indexOf("=") + 2, cameraAmount.length());
			String deviceAmount = br.readLine();
			deviceAmount = deviceAmount.substring(
					deviceAmount.indexOf("=") + 2, deviceAmount.length());
			String expireTime = br.readLine();
			expireTime = expireTime.substring(expireTime.indexOf("=") + 2,
					expireTime.length());
			String motherBoardList = br.readLine();
			motherBoardList = motherBoardList.substring(
					motherBoardList.indexOf("=") + 2, motherBoardList.length());
			String cpuidList = br.readLine();
			cpuidList = cpuidList.substring(cpuidList.indexOf("=") + 2,
					cpuidList.length());
			String macList = br.readLine();
			macList = macList.substring(macList.indexOf("=") + 2,
					macList.length());
			String signature = br.readLine();
			signature = signature.substring(signature.indexOf(":") + 2,
					signature.length());

			License license = new License();
			license.setProjectName(projectName);
			license.setLinkMan(linkMan);
			license.setContact(contact);
			license.setCameraAmount(cameraAmount);
			license.setCpuidList(cpuidList);
			license.setExpireTime(expireTime);
			license.setMacList(macList);
			license.setMotherBoardList(motherBoardList);
			license.setDeviceAmount(deviceAmount);
			license.setSignature(signature);
			license.setUserAmount(userAmount);
			return license;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ENCODING_ERROR,
					"License file encode is not UTF-8 !");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"Network IO error !");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.LICENSE_FORMAT_INVALID,
					"Format of license file invalid !");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 补充C#签名信息缺失的前面46位二进制
	 * 
	 * @param sign
	 *            base64编码的签名信息
	 * @return 完整的签名二进制信息
	 * @throws Exception
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-13 上午10:38:46
	 */
	public static byte[] changeDSANet2java(String sign) throws Exception {
		Base64 base64 = new Base64();
		byte tx[] = base64.decode(sign.getBytes("utf8"));

		byte[] tx_new = new byte[46];
		tx_new[0] = 48;
		tx_new[1] = 44;
		tx_new[2] = 2;
		tx_new[3] = 20;

		for (int x = 0; x < 20; x++) {
			tx_new[x + 4] = tx[x];
		}
		tx_new[24] = 2;
		tx_new[25] = 20;

		for (int x = 20; x < 40; x++) {
			tx_new[x + 6] = tx[x];
		}
		return tx_new;
	}

	public static void main(String[] args) throws Exception {
		genKey(DEFAULT_SEED);
	}
}
