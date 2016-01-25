package com.znsx.util.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;

/**
 * NetworkUtil
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-8 上午11:05:06
 */
public class NetworkUtil {

	/**
	 * 获取本机所有的IPV4地址，按照CIDR的表示方法输出
	 * 
	 * @return CIDR的表示方法的IP地址集合，如：new String[]{"127.0.0.1/8",
	 *         "192.168.1.104/24"}
	 * @throws BusinessException
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-8 上午11:17:11
	 */
	public static String[] getAllCidrIP() throws BusinessException {
		// System.setProperty("java.net.preferIPv4Stack", "true");
		List<String> cidrIPs = new LinkedList<String>();

		try {
			Enumeration<NetworkInterface> nis = NetworkInterface
					.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();

				List<InterfaceAddress> list = ni.getInterfaceAddresses();
				for (InterfaceAddress ia : list) {
					if (null != ia) {
						InetAddress inetAddress = ia.getAddress();
						if (inetAddress instanceof Inet4Address) {
							short maskLength = ia.getNetworkPrefixLength();
							// 注意：由于jdk1.6BUG导致IPV4和IPV6的获取掩码混乱，强制指定大于32位掩码的为24位掩码，
							// 因此程序只能支持192.168.1.*,192.168.2.*这样的网段划分，如果在1网段内还划分了子网段会导致BUG产生
							if (maskLength > 32) {
								maskLength = 24;
							}
							String cidrNotation = inetAddress.getHostAddress()
									+ "/" + maskLength;
							cidrIPs.add(cidrNotation);
							System.out.println("find cms ip : " + cidrNotation);
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
		return cidrIPs.toArray(new String[0]);
	}

	/**
	 * 查找通过子网掩码匹配指定IP使用的网卡IP
	 * 
	 * @param ip
	 *            需要匹配的IP地址,如"192.168.1.10"
	 * @param cidrIPs
	 *            CIDR表示法表示的一组IP地址
	 * @return 匹配的CIDR表示法表示的IP地址,如"192.168.1.1/24",或者没有匹配上返回Null
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-8 上午11:50:58
	 */
	public static String findMappingCidrIP(String ip, String[] cidrIPs) {
		for (String s : cidrIPs) {
			SubnetUtils subnet = new SubnetUtils(s);
			SubnetInfo info = subnet.getInfo();
			if (info.isInRange(ip)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * 从一组IP地址中，找到存在于指定网段中的第一个IP地址
	 * 
	 * @param cidrIP
	 *            CIDR表示法表示的网段地址，如："192.168.1.7/24"
	 * @param ips
	 *            IP数组，如：new String[]{"192.168.1.111","192.168.2.107"}
	 * @return IP地址，如："192.168.1.111"
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-1-8 下午2:46:16
	 */
	public static String filterRangeIP(String cidrIP, String[] ips) {
		if (null == cidrIP) {
			return null;
		}
		SubnetUtils subnet = new SubnetUtils(cidrIP);
		SubnetInfo info = subnet.getInfo();
		for (String ip : ips) {
			if (info.isInRange(ip)) {
				return ip;
			}
		}
		return null;
	}

	public static String[] getAllLocalHostIP() throws BusinessException {
		List<String> res = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				Enumeration nii = ni.getInetAddresses();
				while (nii.hasMoreElements()) {
					ip = (InetAddress) nii.nextElement();
					if (ip.getHostAddress().indexOf(":") == -1) {
						res.add(ip.getHostAddress());
						System.out.println("cms ip=" + ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					e.getMessage());
		}
		return (String[]) res.toArray(new String[0]);
	}
}
