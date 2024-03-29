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
package org.uitnet.testing.smartfwk.ui.core.commons;

import java.io.File;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class Locations {
	public static String getProjectRootDir() {
		String rootDir = System.getProperty("user.dir");
		if (rootDir == null || "".equals(rootDir.trim())) {
			Assert.fail("Please set environment variable 'project.root.dir' . Going to exit...");
			System.exit(1);
		}
		return rootDir;
	}

	public static String getConfigDirPath() {
		String configPath = getProjectRootDir() + File.separator + "test-config";

		return configPath;
	}

	public static String getProjectTargetDirPath() {
		// System.out.println(System.getProperties());
		String configPath = System.getProperty("user.dir") + File.separator + "target" + File.separator;

		return configPath;
	}
	
	/**
	 * This reads the active environment from system property '-Dapps.active.environment'.
	 * Multiple environments are configured comma separated. like:
	 * 
	 * -Dapps.active.environment=app1:environment1,app2:environment1,app3:environment2
	 * 
	 * @param appName
	 * @return
	 */
	public static String getAppActiveEnvironmentName(String appName) {
		String appsEnv = System.getProperty("apps.active.environment");
		if (!(appsEnv == null || "".equals(appsEnv.trim()))) {
			String[] appsEnvArr = appsEnv.split(",");
			String[] appEnvArr = null;
			String appName1 = null;
			for(String appEnv : appsEnvArr) {
				appEnvArr = appEnv.split(":");
				if(appEnvArr.length != 2) { continue; }
				appName1 = appEnvArr[0].trim();
				
				if(appName1.equals(appName)) {
					return appEnvArr[1].trim();
				}				
			}
		}
		return null;
	}
}
