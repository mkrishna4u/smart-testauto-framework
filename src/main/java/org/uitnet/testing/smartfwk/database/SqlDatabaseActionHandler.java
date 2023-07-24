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
package org.uitnet.testing.smartfwk.database;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.testng.Assert;
import org.testng.Reporter;
import org.uitnet.testing.smartfwk.common.MethodArg;
import org.uitnet.testing.smartfwk.common.MethodArgMode;
import org.uitnet.testing.smartfwk.common.ReturnType;
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
		
		try {
			TimeUnit.SECONDS.wait(5);
		} catch(Exception ex) {
			
		}

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
			hibSession = ((SessionFactory)connection.getConnection()).openSession();

			NativeQuery<?> sqlQuery = hibSession.createNativeQuery(searchStatement);
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

			NativeQuery<?> sqlQuery = hibSession.createNativeQuery(updateStatement);
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

			NativeQuery<?> sqlQuery = hibSession.createNativeQuery(deleteStatement);
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

			NativeQuery<?> sqlQuery = hibSession.createNativeQuery(insertStatement);
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
				sqlQuery = hibSession.createNativeQuery(stmt);
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
			sqlQuery = hibSession.createNativeQuery(createStatement);
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
			sqlQuery = hibSession.createNativeQuery(dropStatement);
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

	@Override
	protected String executeFunctionReturnAsJson(String functionName, ReturnType returnType, Object... args) {
		Session hibSession = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			String driverClazz = activeDatabaseProfile.getAdditionalPropertyValue("hibernate.connection.driver_class", String.class);
			NativeQuery<?> query = null;
			if(driverClazz.contains("Oracle")) {
				query = hibSession.createNativeQuery("select " + functionName + "(" + prepareArgsForFunction(args) + ") from dual");
			} else {
				query = hibSession.createNativeQuery("select " + functionName + "(" + prepareArgsForFunction(args) + ")");
			}
			
			for(int i = 1; i < args.length; i++) {
				query.setParameter(i, args[i-1]);
			}
			
			if(returnType == null || returnType == ReturnType.SingleRecord) {
				Object foundRecords = query.getSingleResult();
				return JsonYamlUtil.convertObjectToJsonString(foundRecords);
			} else {
				List<?> foundRecords = query.getResultList();
				return JsonYamlUtil.convertObjectToJsonString(foundRecords);
			}
		} catch (Exception ex) {
			Reporter.log("Error in executing function '" + functionName + "'.");
			Assert.fail("Error in executing function '" + functionName + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
		return null;
	}

	@Override
	protected String executeProcedureReturnAsJson(String procedureName, MethodArg<?>... args) {
		Session hibSession = null;
		try {
			hibSession = ((SessionFactory)connection.getConnection()).openSession();
			StoredProcedureQuery query = null;
			query = hibSession.createStoredProcedureQuery(procedureName);
			
			List<String> outParamNames = new LinkedList<>();
			List<String> refCursorParamNames = new LinkedList<>();
			
			for(int i = 1; i < args.length; i++) {
				if(args[i].getMode() == MethodArgMode.IN) {
					query.registerStoredProcedureParameter(args[i].getName(), args[i].getDataType(), ParameterMode.valueOf(args[i].getMode().name()));
					query.setParameter(args[i].getName(), args[i].getValue());
				} else if(args[i].getMode() == MethodArgMode.INOUT) {
					query.registerStoredProcedureParameter(args[i].getName(), args[i].getDataType(), ParameterMode.valueOf(args[i].getMode().name()));
					query.setParameter(args[i].getName(), args[i].getValue());
					outParamNames.add(args[i].getName());
				} else if(args[i].getMode() == MethodArgMode.OUT) {
					query.registerStoredProcedureParameter(args[i].getName(), args[i].getDataType(), ParameterMode.valueOf(args[i].getMode().name()));
					outParamNames.add(args[i].getName());
				} else {
					query.registerStoredProcedureParameter(args[i].getName(), void.class, ParameterMode.valueOf(args[i].getMode().name()));
					refCursorParamNames.add(args[i].getName());
				}
			}
			
			if(refCursorParamNames.size() > 1) {
				Assert.fail("Procedure can have only one parameter as REF_CURSOR.");
				return null;
			}
			
			Map<String, Object> outputMap = new LinkedHashMap<>();
			
			if(refCursorParamNames.size() == 1) {
				Object result = query.getSingleResult();
				outputMap.put(refCursorParamNames.get(0), result);
			} else {
				query.execute();
			}
			
			for(String outParamName : outParamNames) {
				Object result = query.getOutputParameterValue(outParamName);
				if(result instanceof Clob) {
					result = readClobData((Clob)result);
				}
				outputMap.put(outParamName, result);
			}
			
			if(outputMap.size() > 0) {
				return JsonYamlUtil.convertObjectToJsonString(outputMap);
			}
		} catch (Exception ex) {
			Reporter.log("Error in executing procedure '" + procedureName + "'.");
			Assert.fail("Error in executing procedure '" + procedureName + "'.", ex);
		} finally {
			if (hibSession != null) {
				hibSession.close();
			}
		}
		return null;
	}
	
	private String prepareArgsForFunction(Object[] args) {
		StringBuilder sb = new StringBuilder("");
		
		if(args == null) {
			return "";
		}
		
		for(int i = 0; i < args.length; i++) {
			if(i == 0) {
				sb.append("?");
			} else {
				sb.append(", ?");
			}
		}
		
		return sb.toString();
	}
	
	private String readClobData(Clob clob) throws SQLException, IOException {
		Reader reader = null;
		char charBuffer[];
		String outBuffer;
		
		try {
			charBuffer = new char[(int) clob.length()];
			reader = clob.getCharacterStream();
			reader.read(charBuffer);
			outBuffer = new String(charBuffer);
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		
		return outBuffer;
	}
	
}
