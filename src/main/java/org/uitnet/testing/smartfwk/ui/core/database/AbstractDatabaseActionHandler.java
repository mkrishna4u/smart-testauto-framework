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

import java.util.Calendar;
import java.util.List;

import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractDatabaseActionHandler implements DatabaseConnectionProvider {
	protected String appName;
	protected DatabaseProfile activeDatabaseProfile;
	protected int sessionExpiryDurationInSeconds;
	protected long lastRequestAccessTimeInMs;

	protected DatabaseConnection connection;

	public AbstractDatabaseActionHandler(String appName, int sessionExpiryDurationInSeconds, DatabaseProfile databaseProfile) {
		this.appName = appName;
		this.sessionExpiryDurationInSeconds = sessionExpiryDurationInSeconds;
		this.activeDatabaseProfile = databaseProfile;
	}

//	protected void connect(String profileName) {
//		DatabaseConnectionProvider connProvider = SmartDatabaseManager.getInstance().getDatabaseConnectionProvider(appName,
//				profileName);
//		connection = connProvider.connect(SmartDatabaseManager.getInstance().getDatabaseProfile(appName, profileName));
//	} 

	protected boolean isSessionExpired() {
		long currTimeInMs = Calendar.getInstance().getTimeInMillis();
		long durationInSeconds = (currTimeInMs - lastRequestAccessTimeInMs) / 1000;
		if (durationInSeconds >= sessionExpiryDurationInSeconds) {
			return true;
		}
		return false;
	}

	public String getDataAsJsonString(String entityName, String searchStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		return getDataAsJsonString(connection, entityName, searchStatement);
	}

	public DocumentContext getDataAsJsonDocument(String entityName, String searchStatement) {
		String jsonData = getDataAsJsonString(entityName, searchStatement);
		JsonDocumentReader reader = new JsonDocumentReader(jsonData);

		return reader.getDocumentContext();
	}

	public void updateData(String entityName, String updateStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		updateData(connection, entityName, updateStatement);
	}

	public void deleteData(String entityName, String deleteStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		deleteData(connection, entityName, deleteStatement);
	}

	public void insertData(String entityName, String insertStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		insertData(connection, entityName, insertStatement);
	}

	public void insertDataInBatch(String entityName, List<String> insertStatements) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		insertDataInBatch(connection, entityName, insertStatements);
	}

	public void create(String entityName, String createStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		create(connection, entityName, createStatement);
	}

	public void drop(String entityName, String dropStatement) {
		if (isSessionExpired()) {
			disconnect();
			connect(activeDatabaseProfile);
		} else {
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		drop(connection, entityName, dropStatement);
	}

	@Override
	public void disconnect() {
		disconnect(connection);
	}

	protected abstract void disconnect(DatabaseConnection connection);

	protected abstract String getDataAsJsonString(DatabaseConnection connection, String entityName,
			String searchStatement);

	protected abstract void updateData(DatabaseConnection connection, String entityName, String updateStatement);

	protected abstract void deleteData(DatabaseConnection connection, String entityName, String deleteStatement);

	protected abstract void insertData(DatabaseConnection connection, String entityName, String insertStatement);

	protected abstract void insertDataInBatch(DatabaseConnection connection, String entityName,
			List<String> insertStatements);

	protected abstract void create(DatabaseConnection connection, String entityName, String createStatement);

	protected abstract void drop(DatabaseConnection connection, String entityName, String dropStatement);
}
