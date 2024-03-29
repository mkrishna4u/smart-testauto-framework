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
package org.uitnet.testing.smartfwk.ui.core.defaults.testng;

import org.testng.IExecutionListener;
import org.testng.Reporter;
import org.uitnet.testing.smartfwk.SmartRegistry;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartUiTestNGExecutionListener implements IExecutionListener {
	@Override
	public void onExecutionStart() {

	}

	@Override
	public void onExecutionFinish() {
		Reporter.log("Going to close all opened applications.", true);
		SmartRegistry.deregisterAll();
//		try {
//			if (!TestConfigManager.getInstance().isParallelMode()) {
//				Map<String, AbstractAppConnector> appConnectors = SingletonAppConnectorMap.getInstance().getMap();
//				if (appConnectors != null && !appConnectors.isEmpty()) {
//					for (AbstractAppConnector appConnector : appConnectors.values()) {
//						appConnector.logoutAndQuit();
//					}
//				}
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		SmartDatabaseManager.getInstance().deregisterAll();
	}

}
