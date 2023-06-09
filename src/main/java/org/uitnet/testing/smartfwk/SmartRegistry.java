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
package org.uitnet.testing.smartfwk;

import java.util.Map;

import org.uitnet.testing.smartfwk.api.core.defaults.ApiTestManager;
import org.uitnet.testing.smartfwk.api.core.defaults.SmartApiTestManager;
import org.uitnet.testing.smartfwk.database.DatabaseManager;
import org.uitnet.testing.smartfwk.database.SmartDatabaseManager;
import org.uitnet.testing.smartfwk.messaging.MessageHandlerManager;
import org.uitnet.testing.smartfwk.messaging.SmartMessageHandlerManager;
import org.uitnet.testing.smartfwk.remote_machine.RemoteMachineManager;
import org.uitnet.testing.smartfwk.remote_machine.SmartRemoteMachineManager;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.SingletonAppConnectorMap;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartRegistry {

	private SmartRegistry() {
		// do nothing
	}

	public static DatabaseManager getDatabaseManager() {
		return SmartDatabaseManager.getInstance();
	}

	public static ApiTestManager getApiTestManager() {
		return SmartApiTestManager.getInstance();
	}
	
	public static RemoteMachineManager getRemoteMachineManager() {
		return SmartRemoteMachineManager.getInstance();
	}
	
	public static MessageHandlerManager getMessageHandlerManager() {
		return SmartMessageHandlerManager.getInstance();
	}

	public static TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public static void deregisterAll() {
		getDatabaseManager().deregisterAll();
		getApiTestManager().deregisterAll();
		getRemoteMachineManager().deregisterAll();
		getMessageHandlerManager().deregisterAll();
		// deregister UI connectors
		synchronized (SmartRegistry.class) {
			try {
				// if (!TestConfigManager.getInstance().isParallelMode()) {
				Map<String, AbstractAppConnector> appConnectors = SingletonAppConnectorMap.getInstance().getMap();
				if (appConnectors != null && !appConnectors.isEmpty()) {
					for (AbstractAppConnector appConnector : appConnectors.values()) {
						appConnector.logoutAndQuit();
					}
				}
				appConnectors.clear();
				// }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
