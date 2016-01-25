package com.znsx.cms.service.iface;

import java.util.List;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.model.WfsRoadAdminVO;
import com.znsx.cms.service.model.WfsTollgateVO;

/**
 * WebGis接口调用Manager
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-14 下午2:04:05
 */
public interface WebGisManager extends BaseManager {
	/**
	 * 获取GIS服务中的路政单位信息
	 * 
	 * @return 路政单位信息
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-14 下午2:10:31
	 */
	public List<WfsRoadAdminVO> wfsListRoadAdmin(String wfsUrl);

	/**
	 * 获取GIS服务器中的医院信息
	 * 
	 * @return 医院单位信息
	 * @throws BusinessException
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:24:27
	 */
	public List<WfsRoadAdminVO> wfsListHospital(String wfsUrl);

	/**
	 * 获取GIS服务器中的消防队信息
	 * 
	 * @param wfsUrl
	 *            wfsUrl
	 * 
	 * @return 消防队信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:25:05
	 */
	public List<WfsRoadAdminVO> wfsListFire(String wfsUrl);

	/**
	 * 获取GIS服务器中的交警支队信息
	 * 
	 * @return 交警支队信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午3:26:18
	 */
	public List<WfsRoadAdminVO> wfsListPolice(String wfsUrl);

	/**
	 * 获取收费站信息
	 * 
	 * @return 收费站信息
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2014 下午2:36:15
	 */
	public List<WfsTollgateVO> wfsGetTollgate();

	/**
	 * 查询wfsUrl
	 * 
	 * @return wfsUrl
	 */
	public String getWfsUrl();
}
