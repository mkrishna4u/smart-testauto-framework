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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDatabaseManager implements DatabaseManager {
	private static SmartDatabaseManager instance;

	// Key: appName:profileName, Value: AbstractDatabaseActionHandler
	private Map<String, AbstractDatabaseActionHandler> dbActionHandlers;

	private SmartDatabaseManager() {
		dbActionHandlers = new HashMap<>();
		initDatabaseProfiles();
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

	private void initDatabaseProfiles() {
		Collection<AppConfig> appConfigs = TestConfigManager.getInstance().getAppConfigs();
		if (appConfigs != null) {
			Collection<DatabaseProfile> dbProfiles = null;
			for (AppConfig appConfig : appConfigs) {
				dbProfiles = appConfig.getDatabaseProfiles();
				if (dbProfiles != null) {
					for (DatabaseProfile profile : dbProfiles) {
						try {
							Class<?> clazz = Class.forName(profile.getDatabaseHandlerClass());
							AbstractDatabaseActionHandler obj = (AbstractDatabaseActionHandler) ObjectUtil
									.findClassConstructor(clazz, new Class[] {String.class, int.class, DatabaseProfile.class})
									.newInstance(profile.getAppName(), profile.getSessionExpiryDurationInSeconds(), profile);
							registerDatabaseActionHandler(appConfig.getAppName(), profile.getProfileName(), obj);
						} catch (Exception ex) {
							throw new RuntimeException(ex);
						}
					}
				}
			}
		}
	}

	@Override
	public void registerDatabaseActionHandler(String appName, String profileName,
			AbstractDatabaseActionHandler actionHandler) {
		dbActionHandlers.put(prepareKey(appName, profileName), actionHandler);
	}

	@Override
	public void deregisterAll() {
		for (DatabaseConnectionProvider aap : dbActionHandlers.values()) {
			aap.disconnect();
		}

		dbActionHandlers.clear();
	}

	@Override
	public DatabaseProfile getDatabaseProfile(String appName, String profileName) {
		return TestConfigManager.getInstance().getDatabaseProfile(appName, profileName);
	}

	private String prepareKey(String appName, String profileName) {
		return appName + ":" + profileName;
	}

	@Override
	public AbstractDatabaseActionHandler getDatabaseActionHandler(String appName, String profileName) {
		AbstractDatabaseActionHandler actionHandler = dbActionHandlers.get(prepareKey(appName, profileName));
		return actionHandler;
	}
}
