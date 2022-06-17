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
package org.uitnet.testing.smartfwk.ui.core.database;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDatabaseManager implements DatabaseManager {
	private static SmartDatabaseManager instance;

	// Key: appName:targetServerName, Value: AbstractDatabaseActionHandler
	private Map<String, AbstractDatabaseActionHandler<?>> dbActionHandlers;

	// Key: appName:targetServerName:dbProfileName, Value: DatabaseConnectionProvider
	private Map<String, DatabaseConnectionProvider<?>> dbConnectionProviders;

	private SmartDatabaseManager() {
		dbActionHandlers = new HashMap<>();
		dbConnectionProviders = new HashMap<>();
	}

	public static SmartDatabaseManager getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (SmartDatabaseManager.class) {
			if (instance == null) {
				instance = new SmartDatabaseManager();
			}
		}

		return instance;
	}

	@Override
	public void registerDatabaseActionHandler(String appName, String targetServerName,
			AbstractDatabaseActionHandler<?> actionHandler) {
		dbActionHandlers.put(prepareKey(appName, targetServerName), actionHandler);
	}

	@Override
	@SuppressWarnings("unchecked")
	public AbstractDatabaseActionHandler<?> getRegisteredDatabaseActionHandler(String appName, String targetServerName) {
		AbstractDatabaseActionHandler<?> actionHandler = dbActionHandlers.get(prepareKey(appName, targetServerName));
		Assert.assertNotNull(actionHandler,
				"No database action handler registered with SmartDatabaseManager class for appName = " + appName
						+ " and targetServerName = " + targetServerName
						+ ". This must be registered in cucumber step definition method that is annotated with @BeforeAll.");

		AbstractDatabaseActionHandler<?> newTestHelper = actionHandler.clone();
		newTestHelper.setDatabaseManager(this);
		newTestHelper.setTargetServerName(targetServerName);

		return newTestHelper;
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public DatabaseConnectionProvider<?> getDatabaseConnectionProvider(String appName, String targetServerName,
			String databaseProfileName) {
		String mapKey = prepareConnectionProviderMapKey(appName, targetServerName, databaseProfileName);
		DatabaseConnectionProvider<?> connectionProvider = dbConnectionProviders.get(mapKey);

		if (connectionProvider != null) {
			return connectionProvider;
		}

		synchronized (SmartDatabaseManager.class) {
			connectionProvider = dbConnectionProviders.get(mapKey);
			if (connectionProvider != null) {
				return connectionProvider;
			}

			connectionProvider = getRegisteredDatabaseActionHandler(appName, targetServerName);
			dbConnectionProviders.put(mapKey, connectionProvider);

			return connectionProvider;
		}
	}

	@Override
	public void deregisterAll() {
		dbActionHandlers.clear();

		for (DatabaseConnectionProvider<?> aap : dbConnectionProviders.values()) {
			aap.disconnect();
		}

		dbConnectionProviders.clear();
	}
	
	@Override
	public DatabaseProfile getDatabaseProfile(String appName, String profileName) {
		return TestConfigManager.getInstance().getDatabaseProfile(appName, profileName);
	}

	private String prepareConnectionProviderMapKey(String appName, String targetServerName, String databseProfileName) {
		return appName + ":" + targetServerName + ":" + databseProfileName;
	}

	private String prepareKey(String appName, String targetServerName) {
		return appName + ":" + targetServerName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AbstractDatabaseActionHandler<?> getDatabaseActionHandlerForProfile(String appName,
			String targetServerName, String profileName) {
		AbstractDatabaseActionHandler<?> actionHandler = getRegisteredDatabaseActionHandler(appName, targetServerName);
		actionHandler.setActiveDatabaseProfileName(profileName);
		return actionHandler;
	}
}
