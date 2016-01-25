package com.znsx.cms.persistent.dao;

import java.util.List;

import com.znsx.cms.persistent.model.PlaylistFolder;

/**
 * 存放情报板播放方案的文件夹对象数据库接口
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-7-31 下午4:06:15
 */
public interface PlaylistFolderDAO extends BaseDAO<PlaylistFolder, String> {
	/**
	 * 查询情报板常用条目文件夹
	 * 
	 * @param subType
	 *            情报板子类型
	 * @return
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-8-7 上午10:15:06
	 */
	public List<PlaylistFolder> listFolder(Integer subType);
}
