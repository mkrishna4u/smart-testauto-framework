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
package org.uitnet.testing.smartfwk.remote_machine;

import java.util.HashMap;
import java.util.Map;

import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.ProxyConfiguration;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RemoteMachineConfig {
	private String name;	
	private String hostNameOrIpAddress;
	private int port;
	/**
	 * For valid value, please refer 
	 */
	private String platformType = PlatformType.linux.getType();
	
	private String actionHandlerClass;
	private Integer sessionExpiryDurationInSeconds;
	private String userName;
	private String password;
	
	private String privateKeyPath;
	private String privateKeyPassphrase;
	private String publicKeyPath;	
	private Map<String, String> sshConfigs;
	private ProxyConfiguration proxyConfig;
		
	private Map<String, Object> additionalProps;
	
	public RemoteMachineConfig() {
		sshConfigs = new HashMap<>();
		//sshConfigs.put("StrictHostKeyChecking", "no");
		//sshConfigs.put("PreferredAuthentications", "publickey,password");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostNameOrIpAddress() {
		return hostNameOrIpAddress;
	}

	public void setHostNameOrIpAddress(String hostNameOrIpAddress) {
		this.hostNameOrIpAddress = hostNameOrIpAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = PlatformType.valueOf2(platformType).getType();
	}

	public String getActionHandlerClass() {
		return actionHandlerClass;
	}

	public void setActionHandlerClass(String actionHandlerClass) {
		this.actionHandlerClass = actionHandlerClass;
	}

	public Integer getSessionExpiryDurationInSeconds() {
		return sessionExpiryDurationInSeconds;
	}

	public void setSessionExpiryDurationInSeconds(Integer sessionExpiryDurationInSeconds) {
		this.sessionExpiryDurationInSeconds = sessionExpiryDurationInSeconds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}

	public String getPrivateKeyPassphrase() {
		return privateKeyPassphrase;
	}

	public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
		this.privateKeyPassphrase = privateKeyPassphrase;
	}

	public String getPublicKeyPath() {
		return publicKeyPath;
	}

	public void setPublicKeyPath(String publicKeyPath) {
		this.publicKeyPath = publicKeyPath;
	}

	public Map<String, String> getSshConfigs() {
		return sshConfigs;
	}

	public void setSshConfigs(Map<String, String> sshConfigs) {
		this.sshConfigs = sshConfigs;
	}

	public ProxyConfiguration getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(ProxyConfiguration proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}
}
