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
package org.uitnet.testing.smartfwk.ui.core.config.database.orm;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.testng.Assert;
import org.testng.Reporter;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class OrmDatabaseQueryHandler {
	private SessionFactory hibernateSessionFactory;
		
	public OrmDatabaseQueryHandler(String appName, String profileName) {
		Configuration hibernateCfg = new Configuration()
				.configure(new File(Locations.getConfigDirPath() + File.separator + "apps-config" + File.separator +
						appName + File.separator + "database-profiles" + File.separator + profileName  + ".xml"));

		hibernateSessionFactory = hibernateCfg.buildSessionFactory();
	}

	public boolean removeTableRecord(String tableName, String whereCondition) {
		boolean removed = false;

		String query = "delete from " + tableName + " where " + whereCondition;
		removed = executeUpdateQuery(query);

		if (!removed) {
			Assert.fail("Failed to remove record using query: " + query);
		}

		return removed;
	}

	public boolean removeTableRecordNoAssert(String tableName, String whereCondition) {
		boolean removed = false;

		String query = "delete from " + tableName + " where " + whereCondition;
		removed = executeUpdateQuery(query);

		return removed;
	}

	@SuppressWarnings("rawtypes")
	public boolean checkRecordExistInTable(String tableName, String whereCondition) {
		boolean exists = false;
		String query = "select * from " + tableName + " where " + whereCondition;

		List records = executeSearchQuery(query);
		if (records == null || records.size() > 0) {
			exists = true;
		}

		return exists;
	}

	@SuppressWarnings("rawtypes")
	public void validateRecordExistInTable(String recordName, String tableName, String whereCondition) {
		boolean exists = false;
		String query = "select * from " + tableName + " where " + whereCondition;

		List records = executeSearchQuery(query);
		if (records == null || records.size() > 0) {
			exists = true;
		}

		Assert.assertTrue(exists, "'" + recordName + "' record does not exist in the database. Query: " + query);
	}

	public boolean executeUpdateQuery(String query) {
		int updatedRecords = 0;
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = hibernateSessionFactory.openSession();
			txn = hibSession.beginTransaction();

			NativeQuery<?> sqlQuery = hibSession.createSQLQuery(query);
			updatedRecords = sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log("Error in executing update query '" + query + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing update query '" + query + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}

		return (updatedRecords > 0);
	}

	@SuppressWarnings("rawtypes")
	public List executeSearchQuery(String query) {
		List foundRecords = null;
		Session hibSession = null;
		try {
			Thread.sleep(5000);

			hibSession = hibernateSessionFactory.openSession();

			NativeQuery sqlQuery = hibSession.createSQLQuery(query);
			foundRecords = sqlQuery.list();

		} catch (Exception ex) {
			Reporter.log("Error in executing query '" + query + "'.");
			Assert.fail("Error in executing query '" + query + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}

		return foundRecords;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeSearchQuery(String query, Class<T> entityClass) {
		List<T> foundRecords = null;
		Session hibSession = null;
		try {
			Thread.sleep(5000);

			hibSession = hibernateSessionFactory.openSession();

			NativeQuery<T> sqlQuery = hibSession.createSQLQuery(query);
			sqlQuery.addEntity(entityClass);
			foundRecords = sqlQuery.list();

		} catch (Exception ex) {
			Reporter.log("Error in executing query '" + query + "'.");
			Assert.fail("Error in executing query '" + query + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}

		return foundRecords;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getTableRowColumnValue(String tableName, String columnName, String whereCondition) {
		String columnValue = null;

		List foundRecords = null;
		Session hibSession = null;
		String query = "select " + columnName + " from " + tableName + " where " + whereCondition;
		try {
			Thread.sleep(5000);

			hibSession = hibernateSessionFactory.openSession();

			NativeQuery<Object[]> sqlQuery = hibSession.createSQLQuery(query);
			foundRecords = sqlQuery.list();
			if (foundRecords != null && foundRecords.size() > 0) {
				columnValue = String.valueOf(foundRecords.get(0));
			}

		} catch (Exception ex) {
			Reporter.log("Error in executing search query '" + query + "'.");
			Assert.fail("Error in executing search query '" + query + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}

		return columnValue;
	}

}
