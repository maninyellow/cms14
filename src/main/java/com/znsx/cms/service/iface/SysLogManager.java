package com.znsx.cms.service.iface;

import com.znsx.cms.persistent.model.SysLog;
import com.znsx.cms.service.exception.BusinessException;

/**
 * 系统日志业务接口
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-3-28 上午10:17:03
 */
public interface SysLogManager extends BaseManager {

	/**
	 * 日志记录，仅供测试使用，实际应用请调用batchLog()方法
	 * 
	 * @param record
	 * @throws BusinessException
	 */
	public void log(SysLog record) throws BusinessException;

	/**
	 * 批量日志记录
	 * 
	 * @param record
	 * @throws BusinessException
	 */
	public void batchLog(SysLog record) throws BusinessException;

	/**
	 * 根据对象ID和类型，获取对象名称
	 * 
	 * @param id
	 *            对象ID
	 * @param type
	 *            对象类型， hibernate配置中的class name
	 * @return 对象名称
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2013 下午2:17:58
	 */
	public String getNameByIdAndType(Object id, String type)
			throws BusinessException;

	/**
	 * 根据对象ID和类型，获取对象
	 * 
	 * @param id
	 *            对象ID
	 * @param type
	 *            对象类型， hibernate配置中的class name
	 * @return 对象实体
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2015-4-3 下午2:54:43
	 */
	public Object getEntityByIdAndType(Object id, String type)
			throws BusinessException;
}
