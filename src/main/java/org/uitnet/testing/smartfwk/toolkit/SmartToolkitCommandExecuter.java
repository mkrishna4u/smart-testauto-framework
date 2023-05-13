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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.common.command.SmartCommandExecuter;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;

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
		boolean isProjectExist = new File(baseDir + "test-config").exists();
		
		if(!isProjectExist) {
			// 1. Create project directory structure
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config/app-drivers");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-config/apps-config");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases/e2e");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases/e2e/api");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "cucumber-testcases/e2e/ui");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "sikuli-resources");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "libs");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-data");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-data/api-request-templates");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-results");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "test-results/downloads");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/java");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/java/api");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/java/page_objects");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/main/resources");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java/stepdefs/api");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java/stepdefs/ui");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java/stepdefs/database");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/resources");
			
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts/windows");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "scripts/unix");
			LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "temp");
			
			// Copy global constants file
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-global/GlobalConstants.java", baseDir + "src/main/java/global", "GlobalConstants.java");
			
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-test-config" + File.separator + "TestConfig.yaml", baseDir + "test-config", "TestConfig.yaml");
			
			// Copy log4j file
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "cucumber/cucumber.properties", baseDir + "src/main/resources", "cucumber.properties");
			
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "log4j.xml", baseDir + "src/main/resources", "log4j.xml");
			
			// copy scripts
			copyScripts();
			
			// copy sikuli configs
			LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-test-config/sikuli-config", baseDir + "test-config/sikuli-config", true);
			
			// copy common stepdefs
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-stepdefs/api/SmartSharedApiStepDefs.java", baseDir + "src/test/java/stepdefs/api", "SmartSharedApiStepDefs.java");
			LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-stepdefs/ui/SmartSharedUiStepDefs.java", baseDir + "src/test/java/stepdefs/ui", "SmartSharedUiStepDefs.java");
			
		} else {
			System.out.println("Smart studio seems already configured.");
		}
		
		// Initialize application
		if(!StringUtil.isEmptyAfterTrim(appName)) {
			initApp(appName);
			System.out.println("Smart project environment configured successfully for '" + appName + "' application.");
		} else {
			System.out.println("Smart project environment configured successfully.");
		}
		
		// Copy sample feature files
		LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-feature-files/SampleUiFeature.feature", baseDir + "cucumber-testcases/e2e/ui", "SampleUiFeature.feature");
		LocalMachineFileSystem.copyFileNoOverwrite(baseTempDir + "sample-feature-files/SampleApiFeature.feature", baseDir + "cucumber-testcases/e2e/api", "SampleApiFeature.feature");
	}

	public void initApp(String appName) {
		System.out.println("Going to configure '" + appName + "' application...");
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		String appDir = baseDir + "test-config/apps-config/" + appName;
		if(new File(appDir).exists()) {
			Assert.fail("Application directory already exists. Directory '" + appDir + "' should not be present to configure new application.");
		} else {
			LocalMachineFileSystem.createDirectoriesIfNotExist(appDir);
		}
		
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java/stepdefs/api/" + appName);
		LocalMachineFileSystem.createDirectoriesIfNotExist(baseDir + "src/test/java/stepdefs/ui/" + appName);
		
		String sampleAppDir = baseTempDir + "sample-test-config/apps-config/sample-app";
		
		// Copy sample files to app directory
		LocalMachineFileSystem.copyDirectoryRecursively(sampleAppDir, appDir, true);
		
		// Update sample-app to appName		
		LocalMachineFileSystem.replaceTextInFile(appDir + File.separator + "AppConfig.yaml", "<app-name>", appName);
		addNewAppIntoTestConfig(appName);
		
		// Create appDir into page object
		LocalMachineFileSystem.createDirectoriesIfNotExist(Locations.getProjectRootDir() + File.separator + "src/main/java/page_objects/" + appName);
		
		// Create appDir into validators folder
		LocalMachineFileSystem.createDirectoriesIfNotExist(Locations.getProjectRootDir() + File.separator + "src/main/java/validators/" + appName);
		
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "test-runners", 
				baseDir + "src/test/java/stepdefs", true);
		
		// Copy app validators into validators folder.
		copyAppValidators(appName);
		
		
		// Copy sample page objects into page_objects.appName directory
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-po", 
				baseDir + "src/main/java/page_objects/" + appName, true);
		LocalMachineFileSystem.replaceTextInFile(baseDir + "src/main/java/page_objects/" + appName + "/LoginPO.java", "<app-name>", appName);
		LocalMachineFileSystem.replaceTextInFile(baseDir + "src/main/java/page_objects/" + appName + "/LoginSuccessPO.java", "<app-name>", appName);
		LocalMachineFileSystem.replaceTextInFile(baseDir + "src/main/java/page_objects/" + appName + "/SamplePO.java", "<app-name>", appName);
		
		// Copy sample stepdefs into stepdefs.ui and stepdefs.api directory
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-stepdefs/ui/app", 
				baseDir + "src/test/java/stepdefs/ui/" + appName, true);
		LocalMachineFileSystem.replaceTextInFile(baseDir + "src/test/java/stepdefs/ui/" + appName + "/SamplePageStepDefs.java", "<app-name>", appName);
		
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-stepdefs/api/app", 
				baseDir + "src/test/java/stepdefs/api/" + appName, true);
		LocalMachineFileSystem.replaceTextInFile(baseDir + "src/test/java/stepdefs/api/" + appName + "/SampleApiStepDefs.java", "<app-name>", appName);
		
		prepareDriverConfig(appName, null);
		
		System.out.println("'" + appName + "' application configured successfully.");
	}
	
	public void prepareDriverConfig(String appName, String envName) {
		System.out.println("Going to prepare driver configuration for '" + appName + "' application...");
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String appDir = baseDir + "test-config/apps-config/" + appName;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		YamlDocumentReader appConfig = new YamlDocumentReader(new File(appDir + File.separator + "AppConfig.yaml"), true);
		DocumentContext appConfigDoc = appConfig.getDocumentContext();
		
		PlatformType hostPlatformType = OSDetectorUtil.getHostPlatform();
		PlatformType targetPlatformType = PlatformType.valueOf2(appConfigDoc.read("$.testPlatformType", String.class));
		ApplicationType appType = ApplicationType.valueOf2(appConfigDoc.read("$.applicationType", String.class));
		WebBrowserType webBrowserType = WebBrowserType.valueOf2(appConfigDoc.read("$.appWebBrowser", String.class));
		String newDriverFileName = "AppDriver.yaml";
		if(!StringUtil.isEmptyAfterTrim(envName)) {
			YamlDocumentReader appEnvConfig = new YamlDocumentReader(new File(appDir + File.separator + "environments/AppConfig-" + envName + ".yaml"), true);
			DocumentContext appEnvConfigDoc = appEnvConfig.getDocumentContext();
			String value = JsonYamlUtil.readNoException("$.testPlatformType", String.class, appEnvConfigDoc);
			if(!StringUtil.isEmptyAfterTrim(value)) {
				targetPlatformType = PlatformType.valueOf2(value);
			}
			
			value = JsonYamlUtil.readNoException("$.applicationType", String.class, appEnvConfigDoc);			
			if(!StringUtil.isEmptyAfterTrim(value)) {
				appType = ApplicationType.valueOf2(value);
			}
			
			value = JsonYamlUtil.readNoException("$.appWebBrowser", String.class, appEnvConfigDoc);			
			if(!StringUtil.isEmptyAfterTrim(value)) {
				webBrowserType = WebBrowserType.valueOf2(value);
			}
			
			newDriverFileName = "AppDriver-" + envName + ".yaml";
		}
				
		String driverPlatform = null;
		if(targetPlatformType.getType().endsWith("-mobile")) {
			driverPlatform = targetPlatformType.getType();
			if(appType == ApplicationType.native_app) {
				LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-test-config/app-drivers/" + driverPlatform + "/" + appType.getType() +
						"/AppDriver.yaml", baseDir + "test-config/apps-config/" + appName + "/driver-configs" 
						, newDriverFileName, true);
			} else {
				LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-test-config/app-drivers/" + driverPlatform + "/" + appType.getType() +
						"/" + webBrowserType.getType() + "/AppDriver.yaml", baseDir + "test-config/apps-config/" + appName + "/driver-configs" 
						, newDriverFileName, true);
			}
			
		} else {
			driverPlatform = hostPlatformType.getType();
			
			if(appType == ApplicationType.native_app) {
				LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-test-config/app-drivers/" + driverPlatform + "/" + appType.getType() +
						"/AppDriver.yaml", baseDir + "test-config/apps-config/" + appName + "/driver-configs" 
						, newDriverFileName, true);
			} else {
				File f = new File(baseDir + "test-config/app-drivers/" + driverPlatform + "/" + appType.getType() + "/" + webBrowserType);
				if(!f.exists()) {
					f.mkdirs();
				}
				
				LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-test-config/app-drivers/" + driverPlatform + "/" + appType.getType() +
						"/" + webBrowserType.getType() + "/AppDriver.yaml", baseDir + "test-config/apps-config/" + appName + "/driver-configs" 
						, newDriverFileName, true);
				updateWebDriver(driverPlatform, ApplicationType.web_app, webBrowserType.getType(), "");
				System.out.println("IMPORTANT: Manually download the selenium web driver online for '" + webBrowserType.getType() 
				+ "' web browser and '" + hostPlatformType.getType() + "' platform and place it into '" + f.getAbsolutePath() + "' directory.");
			}
		}
		
		System.out.println("Driver configuration for '" + appName + "' application prepared successfully.");
		
	}
	
	public void updateWebDriver(String platformTypeStr, ApplicationType appType, String webBrowserTypeStr, String webDriverVersion) {
		PlatformType platformType = PlatformType.valueOf2(platformTypeStr);
		WebBrowserType webBrowserType = WebBrowserType.valueOf2(webBrowserTypeStr);
		new SmartWebDriverDownloader().download(platformType, appType, webBrowserType, webDriverVersion);
	}
	
	public void addAppsEnv(String envName) {
		System.out.println("Going to prepare '" + envName + "' environment for all applications...");
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		
		List<String> dirNames = LocalMachineFileSystem.listAllDirectoriesName(baseDir + "test-config/apps-config/");
		for(String appName: dirNames) {
			LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-env/SampleEnv.yaml", baseDir + "test-config/apps-config/" + appName + "/environments" 
					, envName + ".yaml", true);
			LocalMachineFileSystem.replaceTextInFile(baseDir + "test-config/apps-config/" + appName + "/environments/" + envName + ".yaml", "<Sample-Env>", envName);
			System.out.println("IMPORTANT: Update '" + baseDir + "test-config/apps-config/" + appName + "/environments/" + envName + ".yaml" + "' file to create new environment. Copy any property from '" 
			+ appName + "' application's AppConfig.yaml file with different value.");
		}
		
		System.out.println("NOTE: If you do not change any '" + baseDir + "test-config/apps-config/<App-Name>/environments/" + envName 
				+ ".yaml' file then during runtime, it will pick default information from AppConfig.yaml file for that application.");
	}
	
	public void upgradeScripts() {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String scriptsDir = baseDir + "scripts";
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		
		// copy script files to backup folder
		String backupDir = baseDir + File.separator + "upgrade-backup" + File.separator + getTimestampAsString();
		LocalMachineFileSystem.copyDirectoryRecursively(scriptsDir + "/unix", backupDir + "/scripts/unix", true);
		LocalMachineFileSystem.copyDirectoryRecursively(scriptsDir + "/windows", backupDir + "/scripts/windows", true);
		LocalMachineFileSystem.copyFileWithOverwrite(baseDir + "/smart-runner.cmd", backupDir + "/scripts", "smart-runner.cmd", false);
		LocalMachineFileSystem.copyFileWithOverwrite(baseDir + "/smart-runner.sh", backupDir + "/scripts", "smart-runner.sh", false);
		
		// upgrade scripts to latest scripts
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts/unix", scriptsDir + "/unix", false);
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts/windows", scriptsDir + "/windows", false);
		
		LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-scripts/main/smart-runner.sh", baseDir, "smart-runner.sh", false);
		LocalMachineFileSystem.copyFileWithOverwrite(baseTempDir + "sample-scripts/main/smart-runner.cmd", baseDir, "smart-runner.cmd", false);
		
		System.out.println("\nNOTE: set-env.sh and set-env.cmd files are not upgraded. Please change it manually if there is any change.");
	}
	
	private String getTimestampAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd-HHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	public void installAppiumServer() {
		System.out.println("Going to install Appium Server...");
		String appiumServerDir = Locations.getProjectRootDir() + File.separator + "appium-server";
		LocalMachineFileSystem.createDirectoriesIfNotExist(appiumServerDir);
		
		SmartCommandExecuter executer = new SmartCommandExecuter();
		executer.execute(appiumServerDir, "npm", "install", "appium");
		
		System.out.println("Appium Server installation is successful. It is installed in appium-server/ directory.");
	}
	
	public void startAppiumServer() {
		System.out.println("Going to start Appium Server...");
		SmartCommandExecuter executer = new SmartCommandExecuter();
		executer.execute(Locations.getProjectRootDir() + File.separator + "appium-server", "appium");
	}
	
	public void copyScripts() {
		String baseDir = Locations.getProjectRootDir() + File.separator;
		String scriptsDir = baseDir + "scripts";
		String baseTempDir = baseDir + "temp/org/uitnet/testing/smartfwk/resources/";
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts/unix", scriptsDir + "/unix", true);
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts/windows", scriptsDir + "/windows", true);
		
		LocalMachineFileSystem.copyDirectoryRecursively(baseTempDir + "sample-scripts/main", baseDir, true);
	}
	
	private void addNewAppIntoTestConfig(String appName) {
		String testConfigFilePath = Locations.getProjectRootDir() + File.separator + "test-config/TestConfig.yaml";
		YamlDocumentReader testConfig = new YamlDocumentReader(new File(testConfigFilePath), true);
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
		
		// Change the package name to point to correct app directory
		String targetValidatorFile = baseDir + "src/main/java/validators/" + appName + "/AppLoginPageValidator.java";
		LocalMachineFileSystem.replaceTextInFile(targetValidatorFile, "<app-name>", appName);
		
		targetValidatorFile = baseDir + "src/main/java/validators/" + appName + "/AppLoginSuccessPageValidator.java";
		LocalMachineFileSystem.replaceTextInFile(targetValidatorFile, "<app-name>", appName);		
	}
	
	public static void main(String[] args) {
		try {
			SmartToolkitCommandExecuter executer = new SmartToolkitCommandExecuter();
			
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
