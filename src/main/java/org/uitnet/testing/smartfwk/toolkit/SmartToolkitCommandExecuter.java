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
package org.uitnet.testing.smartfwk.toolkit;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.local_machine.ResourceInfo;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartToolkitCommandExecuter {

	public SmartToolkitCommandExecuter() {

	}

	public void initProject(String appName) {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		// 1. Create project directory structure
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config/app-drivers");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config/apps-config");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config/sikuli-config");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases/e2e");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "sikuli-resources");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "libs");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-data");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-results");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-results/downloads");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/java");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/resources");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/resources");
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts/windows");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts/unix");
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "temp");
		
		// Copy global constants file
		LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-global/GlobalConstants.java", baseDir + "src/main/java/global", "GlobalConstants.java");
		
		LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-test-config" + File.separator + "TestConfig.yaml", baseDir + "test-config", "TestConfig.yaml");
		
		// Copy log4j file
		LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "log4j.xml", baseDir + "src/main/resources", "log4j.xml");
		
		
		// copy scripts
		copyScripts();
		
		
		// Initialize application
		if(!StringUtil.isEmptyAfterTrim(appName)) {
			initApp(appName);
		}
	}

	public void initApp(String appName) {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		String appDir = baseDir + "test-config/apps-config/" + appName;
		if(new File(appDir).exists()) {
			Assert.fail("Application directory already exists. Directory '" + appDir + "' should not be present to configure new application.");
		} else {
			LocalMachineFileSystem.createDirectoriesIfNotExist(appDir);
		}
		
		String sampleAppDir = baseTempDir + "sample-test-config/apps-config/sample-app";
		
		// Copy sample files to app directory
		LocalMachineFileSystem.copyDirectoryRecursively(sampleAppDir, appDir, true);
		
		// Update sample-app to appName		
		LocalMachineFileSystem.replaceTextInFile(appDir + File.separator + "AppConfig.yaml", "sample-app", appName);
		addNewAppIntoTestConfig(appName);
		
		// Create appDir into page object
		LocalMachineFileSystem.createDirectoriesIfNotExist(Locations.getProjectRootDir() + File.separator + "src/main/java/page_objects/" + appName);
		
		// Create appDir into validators folder
		LocalMachineFileSystem.createDirectoriesIfNotExist(Locations.getProjectRootDir() + File.separator + "src/main/java/validators/" + appName);
		
		// Create appDir into stepdefs folder
		LocalMachineFileSystem.createDirectoriesIfNotExist(Locations.getProjectRootDir() + File.separator + "src/test/java/stepdefs/" + appName);
		
		// Copy app validators into validators.
		copyAppValidators(appName);
	}
	
	public void copyScripts() {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String scriptsDir = baseDir + "scripts";
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts", scriptsDir, true);
	}
	
	private void addNewAppIntoTestConfig(String appName) {
		String testConfigFilePath = Locations.getProjectRootDir() + File.separator + "test-config/TestConfig.yaml";
		YamlDocumentReader testConfig = new YamlDocumentReader(new File(testConfigFilePath));
		String appNames = testConfig.getDocumentContext().read("$.appNames", String.class);
		if("sample-app".equals(appNames)) {
			LocalMachineFileSystem.replaceTextInFile(testConfigFilePath, "sample-app", appName);
		} else {
			LocalMachineFileSystem.replaceTextInFile(testConfigFilePath, appNames, appNames + "," + appName);
		}
		
	}
	
	private void copyAppValidators(String appName) {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-validators", 
				baseDir + "src/main/java/validators/" + appName, true);
		
		// Change the class name
		new File(baseDir + "src/main/java/validators/" + appName + "/SampleAppLoginPageValidator.java")
			.renameTo(new File(baseDir + "src/main/java/validators/" + appName + "/SampleAppLoginPageValidator.java"));
	}
	
	public static void main(String[] args) {
		try {
			SmartToolkitCommandExecuter executer = new SmartToolkitCommandExecuter();
			//LocalMachineFileSystem.copyClassResoucesNoOverwrite("log4j.xml", Locations.getProjectRootDir() + File.separator + "test-data/temp", "log4j2.xml");
			//List<ResourceInfo> fileNames = LocalMachineFileSystem.listClassResources("sample-scripts/");
			//System.out.println(fileNames);
			
			String[] files2 = new File("C:\\projects").list();
			
			Set<String> filesSet = new TreeSet<>();
			if(files2 != null) {
				for(String f : files2) {
					filesSet.add(f);
				}
			}
			System.out.println(filesSet);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
