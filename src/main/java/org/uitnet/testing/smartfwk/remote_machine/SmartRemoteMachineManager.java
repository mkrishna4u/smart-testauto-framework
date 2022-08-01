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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * This class is used to register all remote machine action handlers.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartRemoteMachineManager implements RemoteMachineManager {
	private static SmartRemoteMachineManager instance;

	// Key: appName, Value: AbstractRemoteMachineActionHandler
	private Map<String, AbstractRemoteMachineActionHandler> actionHandlers;

	// Key: appName:remoteMachineName:userProfileName, Value:
	// ApiAuthenticationProvider
	private Map<String, RemoteMachineConnectionProvider> connectionProviders;

	private SmartRemoteMachineManager() {
		actionHandlers = new HashMap<>();
		connectionProviders = new HashMap<>();
		initActionHandlers();
	}

	public static RemoteMachineManager getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (SmartRemoteMachineManager.class) {
			if (instance == null) {
				instance = new SmartRemoteMachineManager();
			}
		}

		return instance;
	}

	private void initActionHandlers() {
		Collection<AppConfig> appConfigs = TestConfigManager.getInstance().getAppConfigs();
		if (appConfigs != null) {
			Collection<RemoteMachineConfig> remoteMachines = null;
			for (AppConfig appConfig : appConfigs) {
				RemoteMachinesConfig remoteMachinesConfig = appConfig.getRemoteMachinesConfig();
				if (remoteMachinesConfig != null) {
					remoteMachines = remoteMachinesConfig.getRemoteMachines();
					if (remoteMachines != null) {
						for (RemoteMachineConfig remoteMachine : remoteMachines) {
							try {
								if (!StringUtil.isEmptyAfterTrim(remoteMachine.getActionHandlerClass())
										&& !StringUtil.isEmptyAfterTrim(remoteMachine.getName())) {
									Class<?> clazz = Class.forName(remoteMachine.getActionHandlerClass());
									AbstractRemoteMachineActionHandler obj = (AbstractRemoteMachineActionHandler) ObjectUtil
											.findClassConstructor(clazz,
													new Class[] { String.class, String.class, int.class,
															RemoteMachineConfig.class })
											.newInstance(remoteMachine.getName(), remoteMachinesConfig.getAppName(),
													remoteMachine.getSessionExpiryDurationInSeconds(), remoteMachine);
									registerActionHandler(appConfig.getAppName(), remoteMachine.getName(), obj);
								}
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Used to register new remote machine action handler.
	 * 
	 * @param appName
	 * @param remoteMachineName
	 * @param actionHandler
	 */
	private void registerActionHandler(String appName, String remoteMachineName,
			AbstractRemoteMachineActionHandler actionHandler) {
		actionHandlers.put(prepareKey(appName, remoteMachineName), actionHandler);
	}

	@Override
	public AbstractRemoteMachineActionHandler getActionHandler(String appName, String remoteMachineName) {
		AbstractRemoteMachineActionHandler handler = getRegisteredActionHandler(appName, remoteMachineName);
		return handler;
	}

	private AbstractRemoteMachineActionHandler getRegisteredActionHandler(String appName, String remoteMachineName) {
		AbstractRemoteMachineActionHandler actionHandler = actionHandlers.get(prepareKey(appName, remoteMachineName));
		Assert.assertNotNull(actionHandler,
				"No remote machine registered with SmartRemoteMachineManager class for appName = " + appName
						+ " and remoteMachineName = " + remoteMachineName
						+ ". This must be registered in cucumber step definition method that is annotated with @BeforeAll.");

		AbstractRemoteMachineActionHandler newActionHandler = actionHandler.clone();
		newActionHandler.setRemoteMachineManager(this);

		return newActionHandler;
	}

	@Override
	public RemoteMachineConnectionProvider getConnectionProvider(String appName, String remoteMachineName) {
		String mapKey = prepareConnectionProviderMapKey(appName, remoteMachineName);
		RemoteMachineConnectionProvider connectionProvider = connectionProviders.get(mapKey);

		if (connectionProvider != null) {
			return connectionProvider;
		}

		synchronized (SmartRemoteMachineManager.class) {
			connectionProvider = connectionProviders.get(mapKey);
			if (connectionProvider != null) {
				return connectionProvider;
			}

			connectionProvider = getRegisteredActionHandler(appName, remoteMachineName);
			connectionProviders.put(mapKey, connectionProvider);

			return connectionProvider;
		}
	}

	private String prepareConnectionProviderMapKey(String appName, String remoteMachineName) {
		return appName + ":" + remoteMachineName;
	}

	private String prepareKey(String appName, String remoteMachineName) {
		return appName + ":" + remoteMachineName;
	}

	@Override
	public synchronized void deregisterAll() {
		actionHandlers.clear();

		for (RemoteMachineConnectionProvider aap : connectionProviders.values()) {
			aap.disconnect();
		}

		connectionProviders.clear();
	}
}
