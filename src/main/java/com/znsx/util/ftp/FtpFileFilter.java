package com.znsx.util.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;

/**
 * FTP文件过滤器
 * 
 * @author huangbuji
 *         <p />
 *         2012-8-27 上午10:15:24
 */
public class FtpFileFilter extends FTPFileFilters implements FTPFileFilter {

	private String suffix;

	/**
	 * 根据后缀名过滤
	 * 
	 * @param suffix
	 *            后缀名
	 */
	public FtpFileFilter(String suffix) {
		this.suffix = suffix;
	}

	public boolean accept(FTPFile file) {
		if (!file.isFile()) {
			return false;
		}
		if (file.getName().endsWith(suffix)) {
			return true;
		}
		return false;
	}

}
