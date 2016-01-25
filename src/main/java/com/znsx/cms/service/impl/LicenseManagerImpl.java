package com.znsx.cms.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.LicenseDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.UserDAO;
import com.znsx.cms.persistent.dao.UserSessionDAO;
import com.znsx.cms.persistent.dao.UserSessionHistoryDAO;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.LicenseManager;
import com.znsx.util.hardware.HardwareUtil;
import com.znsx.util.licence.LicenceUtil;

/**
 * 许可证业务接口实现类
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:56:29
 */
public class LicenseManagerImpl extends BaseManagerImpl implements
		LicenseManager {
	@Autowired
	private LicenseDAO licenseDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserSessionDAO userSessionDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private UserSessionHistoryDAO userSessionHistoryDAO;
	@Autowired
	private StandardNumberDAO snDAO;

	private String motherBoardSN = null;
	private String cpuid = null;
	private String mac = null;

	@Override
	public License getLicense() throws BusinessException {
		List<License> list = licenseDAO.findAll();
		if (list.size() > 0) {
			return list.get(0);
		}
		// 默认给与1个用户4路的许可
		else {
			License license = new License();
			license.setCameraAmount("4");
			license.setUserAmount("1");
			license.setDeviceAmount("1");
			license.setExpireTime("2100-01-01");
			license.setProjectName("");
			license.setLinkMan("");
			license.setContact("");
			return license;
		}
	}

	@Override
	public boolean checkLicense(License license) throws BusinessException {
		// 当没有license时，给与的默认license放过
		if ("1".equals(license.getDeviceAmount())
				&& "4".equals(license.getCameraAmount())) {
			return true;
		}
		// License内容校验
		contentCheck(license);
		// 硬件信息校验
		hardwareCheck(license);
		// 用户数量校验
		userAmountCheck(license);
		// 数据设备数量校验
		deviceAmountCheck(license);
		// 摄像头数量校验
		cameraAmountCheck(license);
		// 过期时间校验
		expireTimeCheck(license);

		return true;
	}

	@Override
	public String upload(License license) throws BusinessException {
		List<License> list = licenseDAO.findAll();
		// 如果已有license则更新
		if (list.size() > 0) {
			License record = list.get(0);
			record.setProjectName(license.getProjectName());
			record.setLinkMan(license.getLinkMan());
			record.setContact(license.getContact());
			record.setCameraAmount(license.getCameraAmount());
			record.setCpuidList(license.getCpuidList());
			record.setExpireTime(license.getExpireTime());
			record.setMacList(license.getMacList());
			record.setMotherBoardList(license.getMotherBoardList());
			record.setDeviceAmount(license.getDeviceAmount());
			record.setSignature(license.getSignature());
			record.setUserAmount(license.getUserAmount());
			return record.getId();
		}
		// 没有license则保存一个新的
		else {
			licenseDAO.save(license);
			return license.getId();
		}
	}

	/**
	 * 获取数字认证的公钥
	 * 
	 * @return 数字认证的公钥
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:32:39
	 */
	private String getPublicKey() throws BusinessException {
		try {
			String path = LicenceUtil.class.getResource("").getPath();
			File publicFile = new File(path + "/public.ky");
			FileReader fr = new FileReader(publicFile);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			fr.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR,
					"public.ky not found !");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR,
					"public.ky read IOException !");
		}
	}

	/**
	 * 获取数字认证的公钥,二进制方式
	 * 
	 * @return 数字认证的公钥
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-2-12 下午5:42:36
	 */
	private byte[] getPublicBinKey() throws BusinessException {
		try {
			String path = LicenceUtil.class.getResource("").getPath();
			File publicFile = new File(path + "/binPublic.ky");
			FileInputStream fi = new FileInputStream(publicFile);
			byte[] publicKey = new byte[fi.available()];
			fi.read(publicKey);
			fi.close();
			return publicKey;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR,
					"binPublic.ky not found !");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR,
					"binPublic.ky read IOException !");
		}
	}

	/**
	 * 校验硬件信息
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午6:50:13
	 */
	private void hardwareCheck(License license) throws BusinessException {
		// 校验MotherBoardSN
		if (null == motherBoardSN) {
			motherBoardSN = HardwareUtil.getMotherboardSN();
		}
		if (StringUtils.isNotBlank(motherBoardSN)
				&& StringUtils.isNotBlank(license.getMotherBoardList())) {
			String[] motherBoardSNs = license.getMotherBoardList().split(",");
			for (int i = 0; i < motherBoardSNs.length; i++) {
				if (motherBoardSNs[i].equals(motherBoardSN)) {
					return;
				}
			}
			throw new BusinessException(ErrorCode.MOTHER_BOARD_VERIFY_FAILED,
					"Server MotherBoardSN verify failed !");
		}
		// 校验CPUID
		if (null == cpuid) {
			cpuid = HardwareUtil.getCPUID();
		}
		if (StringUtils.isNotBlank(cpuid)
				&& StringUtils.isNotBlank(license.getCpuidList())) {
			String[] cpuids = license.getCpuidList().split(",");
			for (int i = 0; i < cpuids.length; i++) {
				if (cpuids[i].equals(cpuid)) {
					return;
				}
			}
			throw new BusinessException(ErrorCode.CPUID_VERIFY_FAILED,
					"Server CPUID verify failed !");
		}
		// 校验MAC地址
		if (null == mac) {
			mac = HardwareUtil.getMACAddress();
		}
		if (StringUtils.isNotBlank(mac)
				&& StringUtils.isNotBlank(license.getMacList())) {
			String[] macs = license.getMacList().split(",");
			for (int i = 0; i < macs.length; i++) {
				if (macs[i].equals(mac)) {
					return;
				}
			}
			throw new BusinessException(ErrorCode.MAC_VERIFY_FAILED,
					"Server MACAddress verify failed !");
		}
	}

	/**
	 * 用户数量校验
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午6:57:52
	 */
	private void userAmountCheck(License license) throws BusinessException {
		int userCount = userDAO.getTotalCount();
		String userAmount = license.getUserAmount();
		try {
			if (Integer.parseInt(userAmount) < userCount) {
				throw new BusinessException(ErrorCode.USER_AMOUNT_LIMIT,
						"User amount over license limit !");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.USER_AMOUNT_INVALID,
					"License value user_amount[" + license.getUserAmount()
							+ "] invaild !");
		}
	}

	/**
	 * 用户会话数量校验
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:11:28
	 */
	private void deviceAmountCheck(License license) throws BusinessException {
		// 获取数据设备数量
		int deviceCount = snDAO.countDeviceAmount();
		String deviceAmount = license.getDeviceAmount();
		try {
			if (Integer.parseInt(deviceAmount) < deviceCount) {
				throw new BusinessException(ErrorCode.DEVICE_AMOUNT_LIMIT,
						"Device amount over license limit !");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.DEVICE_AMOUNT_INVALID,
					"License value device_amount[" + license.getDeviceAmount()
							+ "] invaild !");
		}
	}

	/**
	 * 摄像头数量校验
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:14:21
	 */
	private void cameraAmountCheck(License license) throws BusinessException {
		int cameraCount = cameraDAO.getTotalCount();
		String cameraAmount = license.getCameraAmount();
		try {
			if (Integer.parseInt(cameraAmount) < cameraCount) {
				throw new BusinessException(ErrorCode.CAMERA_AMOUNT_LIMIT,
						"Camera amount over license limit !");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.CAMERA_AMOUNT_INVALID,
					"License value camera_amount[" + license.getCameraAmount()
							+ "] invaild !");
		}
	}

	/**
	 * 过期时间校验
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:26:05
	 */
	private void expireTimeCheck(License license) throws BusinessException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date expire = sdf.parse(license.getExpireTime());
			Date now = new Date();
			if (now.after(expire)) {
				throw new BusinessException(ErrorCode.LICENSE_EXPIRED,
						"Platform license is expired !");
			}
			// 验证是否修改了系统时间
			long time = userSessionDAO.getLatestSession();
			long hisTime = userSessionHistoryDAO.getLatestSession();
			if (hisTime > time) {
				time = hisTime;
			}
			if ((time - now.getTime()) > 7 * 24 * 3600000) {
				throw new BusinessException(ErrorCode.SYSTEM_BAD_CHANGED,
						"System time has been bad changed !");
			}

		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException(
					ErrorCode.LICENSE_EXPIRED_FORMAT_INVALID,
					"License value expire_time[" + license.getExpireTime()
							+ "] invaild !");
		}
	}

	/**
	 * License内容校验
	 * 
	 * @param license
	 *            平台License对象
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午7:34:21
	 */
	private void contentCheck(License license) throws BusinessException {
		if (StringUtils.isBlank(license.getSignature())) {
			throw new BusinessException(ErrorCode.LICENSE_HAS_BEEN_CHANGED,
					"License content has been changed !");
		}
		// String publicKeyString = getPublicKey();
		byte[] publicKey = getPublicBinKey();
		boolean contentVerify = false;
		try {
			// contentVerify = LicenceUtil.verify(license.toString(),
			// publicKeyString, license.getSignature());
			contentVerify = LicenceUtil.verifyBinKey(license.toString(),
					publicKey, license.getSignature());
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.LICENSE_FORMAT_INVALID,
					"Format of license file invalid !");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.ERROR, e.getMessage());
		}
		if (!contentVerify) {
			throw new BusinessException(ErrorCode.LICENSE_HAS_BEEN_CHANGED,
					"License content has been changed !");
		}
	}
}
