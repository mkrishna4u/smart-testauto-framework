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

import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartWebDriverDownloader {

	public SmartWebDriverDownloader() {

	}

	public void download(PlatformType platformType, ApplicationType appType, WebBrowserType webBrowserType, String webDriverVersion) {
		try {
			if(appType != ApplicationType.web_app) {
				System.out.println("Web driver can only be installed for web applications.");
				return;
			}
			
			String webDriverPath = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator + "app-drivers" + File.separator +
					platformType.getType() + File.separator + appType.getType() + File.separator + webBrowserType.getType();
			
			if(webBrowserType == WebBrowserType.chrome) {
				System.out.println("Manually download the suitable web driver from website: 'https://chromedriver.storage.googleapis.com/index.html' and put it into '" 
						+ webDriverPath + "' directory. Make sure zip file is extracted there and driver executable file should be at the same level as zip file.");
			} else if(webBrowserType == WebBrowserType.firefox) {
				System.out.println("Manually download the suitable web driver from website: 'https://github.com/mozilla/geckodriver/releases' and put it into '" 
						+ webDriverPath + "' directory. Make sure zip/tar.gz file is extracted there and driver executable file should be at the same level as zip/tar.gz file.");
			} else if(webBrowserType == WebBrowserType.edge) {
				System.out.println("Manually download the suitable web driver from website: 'https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/' and put it into '" 
						+ webDriverPath + "' directory. Make sure zip file is extracted there and driver executable file should be at the same level as zip file.");
			} else if(webBrowserType == WebBrowserType.opera) {
				System.out.println("Manually download the suitable web driver from website: 'https://github.com/operasoftware/operachromiumdriver/releases' and put it into '" 
						+ webDriverPath + "' directory. Make sure zip file is extracted there and driver executable file should be at the same level as zip file.");
			} else if(webBrowserType == WebBrowserType.safari) {
				System.out.println("Read the instructions on website: 'https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari' to"
						+ " configure Safari Web Browser for remote testing.");
			} else if(webBrowserType == WebBrowserType.internetExplorer) {
				System.out.println("Manually download the suitable web driver from website: 'https://www.selenium.dev/downloads/' and put it into '" 
						+ webDriverPath + "' directory. Make sure zip file is extracted there and driver executable file should be at the same level as zip file.");
			} else {
				System.err.println("ERROR: Web driver download is not supported. Go to the respective web driver website to download the compatible web driver.");
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
