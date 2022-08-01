/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.ui.core.config;

import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ProxyConfiguration {
	private ProxyConfigurationType proxyConfigType;

	private String httpProxyHostname;
	private String httpProxyPort;

	private String sslProxyHostname;
	private String sslProxyPort;

	private String ftpProxyHostname;
	private String ftpProxyPort;

	private String socksHostname;
	private String socksPort;
	private String socksVersion;

	private String socksUsername;
	private String socksPassword;

	private String noProxyFor;

	public ProxyConfiguration() {
		proxyConfigType = ProxyConfigurationType.NO_PROXY;
	}
	
	public ProxyConfiguration(String appName, Map<String, String> properties) {
		proxyConfigType = ProxyConfigurationType.NO_PROXY;
		if(!DefaultInfo.DEFAULT_APP_NAME.equals(appName)) {
			initProxyConfig(appName, properties);
		}
	}

	private void initProxyConfig(String appName, Map<String, String> properties) {
		String propValue = properties.get("proxyConfigType");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify 'proxyConfigType' in AppConfig.yaml. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			propValue = propValue.trim();
			proxyConfigType = ProxyConfigurationType.valueOf(propValue);
		}

		httpProxyHostname = properties.get("httpProxyHostname");
		if (httpProxyHostname == null || "".equals(httpProxyHostname.trim())) {
		} else {
			httpProxyHostname = httpProxyHostname.trim();
		}

		httpProxyPort = properties.get("httpProxyPort");
		if (httpProxyPort == null || "".equals(httpProxyPort.trim())) {
		} else {
			httpProxyPort = httpProxyPort.trim();
		}

		sslProxyHostname = properties.get("sslProxyHostname");
		if (sslProxyHostname == null || "".equals(sslProxyHostname.trim())) {
		} else {
			sslProxyHostname = sslProxyHostname.trim();
		}

		sslProxyPort = properties.get("sslProxyPort");
		if (sslProxyPort == null || "".equals(sslProxyPort.trim())) {
		} else {
			sslProxyPort = sslProxyPort.trim();
		}

		ftpProxyHostname = properties.get("ftpProxyHostname");
		if (ftpProxyHostname == null || "".equals(ftpProxyHostname.trim())) {
		} else {
			ftpProxyHostname = ftpProxyHostname.trim();
		}

		ftpProxyPort = properties.get("ftpProxyPort");
		if (ftpProxyPort == null || "".equals(ftpProxyPort.trim())) {
		} else {
			ftpProxyPort = ftpProxyPort.trim();
		}

		socksHostname = properties.get("socksHostname");
		if (socksHostname == null || "".equals(socksHostname.trim())) {
		} else {
			socksHostname = socksHostname.trim();
		}

		socksPort = properties.get("socksPort");
		if (socksPort == null || "".equals(socksPort.trim())) {
		} else {
			socksPort = socksPort.trim();
		}

		socksVersion = properties.get("socksVersion");
		if (socksVersion == null || "".equals(socksVersion.trim())) {
		} else {
			socksVersion = socksVersion.trim();
		}

		socksUsername = properties.get("socksUsername");
		if (socksUsername == null || "".equals(socksUsername.trim())) {
		} else {
			socksUsername = socksUsername.trim();
		}

		socksPassword = properties.get("socksPassword");
		if (socksPassword == null || "".equals(socksPassword.trim())) {
		} else {
			socksPassword = socksPassword.trim();
		}

		noProxyFor = properties.get("noProxyFor");
		if (noProxyFor == null || "".equals(noProxyFor.trim())) {
		} else {
			noProxyFor = noProxyFor.trim();
		}
	}

	public ProxyConfigurationType getProxyConfigType() {
		return proxyConfigType;
	}

	public String getHttpProxyHostname() {
		return httpProxyHostname;
	}

	public String getHttpProxyPort() {
		return httpProxyPort;
	}

	public String getSslProxyHostname() {
		return sslProxyHostname;
	}

	public String getSslProxyPort() {
		return sslProxyPort;
	}

	public String getFtpProxyHostname() {
		return ftpProxyHostname;
	}

	public String getFtpProxyPort() {
		return ftpProxyPort;
	}

	public String getSocksHostname() {
		return socksHostname;
	}

	public String getSocksPort() {
		return socksPort;
	}

	public String getSocksVersion() {
		return socksVersion;
	}

	public String getSocksUsername() {
		return socksUsername;
	}

	public String getSocksPassword() {
		return socksPassword;
	}

	public String getNoProxyFor() {
		return noProxyFor;
	}

	public void setProxyConfigType(String proxyConfigType) {
		this.proxyConfigType = ProxyConfigurationType.valueOf(proxyConfigType);
	}

	public void setHttpProxyHostname(String httpProxyHostname) {
		this.httpProxyHostname = httpProxyHostname;
	}

	public void setHttpProxyPort(String httpProxyPort) {
		this.httpProxyPort = httpProxyPort;
	}

	public void setSslProxyHostname(String sslProxyHostname) {
		this.sslProxyHostname = sslProxyHostname;
	}

	public void setSslProxyPort(String sslProxyPort) {
		this.sslProxyPort = sslProxyPort;
	}

	public void setFtpProxyHostname(String ftpProxyHostname) {
		this.ftpProxyHostname = ftpProxyHostname;
	}

	public void setFtpProxyPort(String ftpProxyPort) {
		this.ftpProxyPort = ftpProxyPort;
	}

	public void setSocksHostname(String socksHostname) {
		this.socksHostname = socksHostname;
	}

	public void setSocksPort(String socksPort) {
		this.socksPort = socksPort;
	}

	public void setSocksVersion(String socksVersion) {
		this.socksVersion = socksVersion;
	}

	public void setSocksUsername(String socksUsername) {
		this.socksUsername = socksUsername;
	}

	public void setSocksPassword(String socksPassword) {
		this.socksPassword = socksPassword;
	}

	public void setNoProxyFor(String noProxyFor) {
		this.noProxyFor = noProxyFor;
	}

}
