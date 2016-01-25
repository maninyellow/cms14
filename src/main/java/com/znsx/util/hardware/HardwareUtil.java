package com.znsx.util.hardware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 硬件信息获取工具类，只支持Windows和Linux两种操作系统
 * 
 * @author huangbuji
 *         <p />
 *         create at 2013-4-11 下午02:51:19
 */
public class HardwareUtil {

	private HardwareUtil() {
		// all the method is static, do not need instance
	}

	/**
	 * 获取操作系统名称
	 * 
	 * @return
	 */
	public static String getOsName() {
		String os = "";
		os = System.getProperty("os.name");
		return os;
	}

	/**
	 * 获取主板序列号
	 * 
	 * @return
	 */
	public static String getMotherboardSN() {
		String result = "";
		try {
			// windows
			if (getOsName().startsWith("Windows")) {
				File file = File.createTempFile("realhowto", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);

				String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
						+ "Set colItems = objWMIService.ExecQuery _ \n"
						+ "   (\"Select * from Win32_BaseBoard\") \n"
						+ "For Each objItem in colItems \n"
						+ "    Wscript.Echo objItem.SerialNumber \n"
						+ "    exit for  ' do the first cpu only! \n"
						+ "Next \n";

				fw.write(vbs);
				fw.close();
				Process p = Runtime.getRuntime().exec(
						"cscript //NoLogo " + file.getPath());
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line;
				}
				input.close();
			}
			// linux
			else if (getOsName().startsWith("Linux")) {
				String command = "dmidecode -t 2 | grep Serial";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("Serial Number") >= 0) {
						int index = line.indexOf(":");
						result = line.substring(index + 1);
						break;
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 获取CPU序列号
	 * 
	 * @return
	 */
	public static String getCPUID() {
		String result = "";
		try {
			// windows
			if (getOsName().startsWith("Windows")) {
				File file = File.createTempFile("tmp", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);
				String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
						+ "Set colItems = objWMIService.ExecQuery _ \n"
						+ "   (\"Select * from Win32_Processor\") \n"
						+ "For Each objItem in colItems \n"
						+ "    Wscript.Echo objItem.ProcessorId \n"
						+ "    exit for  ' do the first cpu only! \n"
						+ "Next \n";

				// + "    exit for  \r\n" + "Next";
				fw.write(vbs);
				fw.close();
				Process p = Runtime.getRuntime().exec(
						"cscript //NoLogo " + file.getPath());
				BufferedReader input = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line;
				}
				input.close();
				file.delete();
			}
			// linux
			else if (getOsName().startsWith("Linux")) {
				String command = "dmidecode -t 4 | grep ID";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("ID") >= 0) {
						int index = line.indexOf(":");
						result = line.substring(index + 1);
						break;
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	/**
	 * 获取网卡MAC地址
	 * 
	 * @return
	 */
	public static String getMACAddress() {
		String address = "";
		String os = getOsName();
		if (os.startsWith("Windows")) {
			try {
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("Physical Address") > 0) {
						int index = line.indexOf(":");
						index += 2;
						address = line.substring(index);
						break;
					}
				}
				br.close();
				return address.trim();
			} catch (IOException e) {
			}
		} else if (os.startsWith("Linux")) {
			String command = "/bin/sh -c ifconfig -a";
			Process p;
			try {
				p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("HWaddr") > 0) {
						int index = line.indexOf("HWaddr") + "HWaddr".length();
						address = line.substring(index);
						break;
					}
				}
				br.close();
			} catch (IOException e) {
			}
		}
		address = address.trim();
		return address;
	}

	public static void main(String[] args) {
		System.out.println("OS name = " + getOsName());
		System.out.println("MotherBoardSN = " + getMotherboardSN());
		System.out.println("MAC = " + getMACAddress());
		System.out.println("CPUID = " + getCPUID());
	}
}
