/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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

import java.io.File;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.ProxyConfigurationType;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;

/**
 * If you are using privateKey and public key for authentication then key should
 * be generated correctly like the following command:
 * 
 * ssh-keygen -m PEM -t rsa -b 4096 -C "your_email@example.com" -f <private-key-filename>
 * 
 * @author Madhav Krishna
 *
 */
public class SmartRemoteMachineActionHandler extends AbstractRemoteMachineActionHandler {

	public SmartRemoteMachineActionHandler(String remoteMachineName, String appName, int sessionExpiryDurationInSeconds,
			RemoteMachineConfig remoteMachineConfig) {
		super(remoteMachineName, appName, sessionExpiryDurationInSeconds, remoteMachineConfig);
	}

	@Override
	public RemoteMachineConnection connect(RemoteMachineConfig remoteMachineConfig) {
		Session session = null;
		RemoteMachineConnection connection = null;
		try {
			JSch jsch = new JSch();

			if (!StringUtil.isEmptyAfterTrim(remoteMachineConfig.getPrivateKeyPath())) {
				jsch.addIdentity(
						Locations.getProjectRootDir() + File.separator + remoteMachineConfig.getPrivateKeyPath(),
						StringUtil.isEmptyAfterTrim(remoteMachineConfig.getPublicKeyPath()) ? null
								: Locations.getProjectRootDir() + File.separator
										+ remoteMachineConfig.getPublicKeyPath(),
						StringUtil.isEmptyAfterTrim(remoteMachineConfig.getPrivateKeyPassphrase()) ? null
								: remoteMachineConfig.getPrivateKeyPassphrase().getBytes());
			}

			session = jsch.getSession(remoteMachineConfig.getUserName(), remoteMachineConfig.getHostNameOrIpAddress(),
					remoteMachineConfig.getPort());

			if (!StringUtil.isEmptyAfterTrim(remoteMachineConfig.getPassword())) {
				session.setPassword(remoteMachineConfig.getPassword());
			}

			if (remoteMachineConfig.getSshConfigs() != null) {
				for (Map.Entry<String, String> config : remoteMachineConfig.getSshConfigs().entrySet()) {
					session.setConfig(config.getKey(), config.getValue());
				}
			}

			if (remoteMachineConfig.getProxyConfig() != null
					&& remoteMachineConfig.getProxyConfig().getProxyConfigType() != null) {
				if (remoteMachineConfig.getProxyConfig().getProxyConfigType() == ProxyConfigurationType.MANUAL_PROXY) {
					ProxyHTTP proxy = new ProxyHTTP(remoteMachineConfig.getProxyConfig().getHttpProxyHostname(),
							Integer.parseInt(remoteMachineConfig.getProxyConfig().getHttpProxyPort()));
					proxy.setUserPasswd(remoteMachineConfig.getProxyConfig().getSocksUsername(),
							remoteMachineConfig.getProxyConfig().getSocksPassword());
					session.setProxy(proxy);
				}
			}

			session.connect();

			connection = new RemoteMachineConnection(session);
		} catch (Exception e) {
			if (session != null) {
				try {
					session.disconnect();
				} catch (Exception e2) {
				}
			}
			Assert.fail("Failed to connect to the remote server (AppName: " + appName + ", RemoteMachineName: "
					+ remoteMachineName + ", HostNameOrIpAddress: " + remoteMachineConfig.getHostNameOrIpAddress()
					+ "). Reason: " + e.getMessage(), e);
		}
		return connection;
	}

	@Override
	protected void disconnect(RemoteMachineConnection connection) {
		try {
			if (connection != null) {
				((Session) connection.getConnection()).disconnect();
			}
		} catch (Exception e) {

		}
	}

}
