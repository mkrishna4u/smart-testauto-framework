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

import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;

/**
 * 
 * @author Madhav Krishna
 *
 */
public interface DatabaseManager {
	void registerDatabaseActionHandler(String appName, String targetServerName,
			AbstractDatabaseActionHandler actionHandler);

	AbstractDatabaseActionHandler getRegisteredDatabaseActionHandler(String appName, String targetServerName);

	DatabaseConnectionProvider getDatabaseConnectionProvider(String appName, String targetServerName,
			String databseProfileName);

	DatabaseProfile getDatabaseProfile(String appName, String profileName);

	AbstractDatabaseActionHandler getDatabaseActionHandlerForProfile(String appName, String targetServerName,
			String profileName);

	void deregisterAll();
}
