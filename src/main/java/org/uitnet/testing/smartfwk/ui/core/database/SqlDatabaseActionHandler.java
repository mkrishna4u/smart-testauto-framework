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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.testng.Assert;
import org.testng.Reporter;
import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

/**
 * Relational database action handler also called the SQLDatabaseActionHandler.
 * Used to perform operations on relational databases like oracle, mariadb,
 * postgres, mysql etc.
 * 
 * @author Madhav Krishna
 *
 */
public class SqlDatabaseActionHandler extends AbstractDatabaseActionHandler {

	public SqlDatabaseActionHandler(String appName, int sessionExpiryDurationInSeconds, DatabaseProfile databaseProfile) {
		super(appName, sessionExpiryDurationInSeconds, databaseProfile);
	}

	@Override
	public DatabaseConnection connect(DatabaseProfile dbProfile) {
		Map<String, Object> dbAdditionalProps = dbProfile.getAdditionalProps();
		Properties dbProps = new Properties();
		dbProps.putAll(dbAdditionalProps);
		Configuration hibernateCfg = new Configuration().addProperties(dbProps);

		SessionFactory hibernateSessionFactory = hibernateCfg.buildSessionFactory();

		DatabaseConnection connection = new DatabaseConnection(hibernateSessionFactory);
		return connection;
	}

	@Override
	public void disconnect(DatabaseConnection connection) {
		if (connection != null && connection.getConnection() != null) {
			try {
				((SessionFactory)connection.getConnection()).close();
			} catch (Exception ex) {
			}
		}
	}

	@Override
	protected String getDataAsJsonString(DatabaseConnection connection, String entityName,
			String searchStatement) {
		Session hibSession = null;
		try {
			Thread.sleep(5000);
			hibSession = ((SessionFactory)connection.getConnection()).openSession();

			NativeQuery<?> sqlQuery = hibSession.createSQLQuery(searchStatement);
			List<?> foundRecords = sqlQuery.list();
			return JsonYamlUtil.convertObjectToJsonString(foundRecords);
		} catch (Exception ex) {
			Reporter.log("Error in executing query '" + searchStatement + "'.");
			Assert.fail("Error in executing query '" + searchStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}

		return null;
	}

	@Override
	protected void updateData(DatabaseConnection connection, String entityName,
			String updateStatement) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();

			NativeQuery<?> sqlQuery = hibSession.createSQLQuery(updateStatement);
			sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log(
					"Error in executing update query '" + updateStatement + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing update query '" + updateStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}

	@Override
	protected void deleteData(DatabaseConnection connection, String entityName,
			String deleteStatement) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();

			NativeQuery<?> sqlQuery = hibSession.createSQLQuery(deleteStatement);
			sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log(
					"Error in executing delete query '" + deleteStatement + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing delete query '" + deleteStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}

	@Override
	protected void insertData(DatabaseConnection connection, String entityName,
			String insertStatement) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();

			NativeQuery<?> sqlQuery = hibSession.createSQLQuery(insertStatement);
			sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log(
					"Error in executing insert query '" + insertStatement + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing insert query '" + insertStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}

	@Override
	protected void insertDataInBatch(DatabaseConnection connection, String entityName,
			List<String> insertStatements) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();
			hibSession.setJdbcBatchSize(100);

			NativeQuery<?> sqlQuery = null;
			for (String stmt : insertStatements) {
				sqlQuery = hibSession.createSQLQuery(stmt);
				sqlQuery.executeUpdate();
			}

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log("Error in executing batch insert query. Error message: " + ex.getMessage());
			Assert.fail("Error in executing batch insert query.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}

	@Override
	protected void create(DatabaseConnection connection, String entityName, String createStatement) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();
			hibSession.setJdbcBatchSize(100);

			NativeQuery<?> sqlQuery = null;
			sqlQuery = hibSession.createSQLQuery(createStatement);
			sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log(
					"Error in executing create query '" + createStatement + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing create query '" + createStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}

	@Override
	protected void drop(DatabaseConnection connection, String entityName, String dropStatement) {
		Session hibSession = null;
		Transaction txn = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			txn = hibSession.beginTransaction();
			hibSession.setJdbcBatchSize(100);

			NativeQuery<?> sqlQuery = null;
			sqlQuery = hibSession.createSQLQuery(dropStatement);
			sqlQuery.executeUpdate();

			txn.commit();
			txn = null;
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
			}
			Reporter.log("Error in executing drop query '" + dropStatement + "'. Error message: " + ex.getMessage());
			Assert.fail("Error in executing drop query '" + dropStatement + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
	}
	
}
