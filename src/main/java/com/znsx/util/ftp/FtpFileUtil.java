package com.znsx.util.ftp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * FTP文件和目录工具
 * 
 * @author huangbuji
 *         <p />
 *         2012-8-27 上午10:42:34
 */
public class FtpFileUtil {

	/**
	 * 递归创建目录
	 * 
	 * @param ftpClient
	 *            ftp客户端工具
	 * @param dir
	 *            要创建的多级目录
	 * @param isRoot
	 *            是否根目录，递归是用到，外部调用始终为true
	 * @return true成功，false失败
	 * @throws Exception
	 */
	public static boolean createDirectory(FTPClient ftpClient, String dir,
			boolean isRoot) throws Exception {
		if (StringUtils.isBlank(dir) || ftpClient.changeWorkingDirectory(dir)) {
			return true;
		} else {
			int first = dir.indexOf("/") + 1;
			int second = dir.indexOf("/", first);
			if (first == 0) {
				return true;
			}
			if (second == -1) {
				second = dir.length();
			}
			String parentDir = dir.substring(first, second);
			if (isRoot) {
				ftpClient.changeWorkingDirectory(dir.substring(0, first));
			}
			ftpClient.makeDirectory(parentDir);
			ftpClient.changeWorkingDirectory(parentDir);

			return createDirectory(ftpClient, dir.substring(second), false);
		}
	}
}
