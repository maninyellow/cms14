package com.znsx.cms.service.iface;

import com.znsx.cms.persistent.model.License;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 许可证业务接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013 下午2:55:48
 */
public interface LicenseManager extends BaseManager {
	/**
	 * 上传License文件
	 * 
	 * @param license
	 *            上传的license文件对象
	 * @return 上传成功的LicenseID
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:59:50
	 */
	public String upload(License license) throws BusinessException;

	/**
	 * 获取平台License
	 * 
	 * @return 平台License
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:02:02
	 */
	public License getLicense() throws BusinessException;

	/**
	 * 检查License的合法性
	 * 
	 * @param license
	 *            待检查的license对象
	 * 
	 * @return 合法-true, 非法-false
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午3:11:41
	 */
	public boolean checkLicense(License license) throws BusinessException;
}
