package com.trax.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ReadConfig {

	Properties prop;

	public ReadConfig() {
		File src = new File("./Resources/config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}
	}

	public String getApplicationURL() {
		String url = prop.getProperty("reviewURL");
		return url;
	}

	public String getApplicationTrainingURL() {
		String trainingUrl = prop.getProperty("trainingURL");
		return trainingUrl;
	}

	public String getPartsDevURL() {
		String getPartsDevURL = prop.getProperty("partsURL");
		return getPartsDevURL;
	}

	public String getAdminUserName() {
		String username = prop.getProperty("AdminUserName");
		return username;
	}

	public String getTestURL() {
		String getTestURL = prop.getProperty("testURL");
		return getTestURL;
	}

	public String getUserId() {
		String username = prop.getProperty("userId");
		return username;
	}

	public String getAdminUserId() {
		String username = prop.getProperty("AdminUserId");
		return username;
	}

	public String getAccountingUserId() {
		String username = prop.getProperty("AccountingUserId");
		return username;
	}

	public String getPassword() {
		String password = prop.getProperty("password");
		return password;
	}

	public String getAdminPassword() {
		String password = prop.getProperty("AdminPassword");
		return password;
	}

	public String getWinChromePath() {
		String winChromepath = prop.getProperty("WindowsChromepath");
		return winChromepath;
	}

	public String getLinuxChromePath() {
		String linuxChromepath = prop.getProperty("LinuxChromepath");
		return linuxChromepath;
	}

	public String getEnvName() {
		String envpath = prop.getProperty("environment");
		return envpath;
	}

	public String getUserName() {
		String userName = prop.getProperty("userName");
		return userName;
	}

	public String getHost() throws UnknownHostException {
		String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;
	}

	public String[] getGmailCredentials() {
		String gmailCredentials[] = new String[3];

		gmailCredentials[0] = prop.getProperty("GmailHost");
		gmailCredentials[1] = prop.getProperty("GmailUsername");
		gmailCredentials[2] = prop.getProperty("GmailPassword");
		return gmailCredentials;
	}

	public String[] getOutlookCredentials() {
		String outlookCredentials[] = new String[3];

		outlookCredentials[0] = prop.getProperty("OutlookHost");
		outlookCredentials[1] = prop.getProperty("OutlookUsername");
		outlookCredentials[2] = prop.getProperty("OutlookPassword");
		return outlookCredentials;
	}

}
