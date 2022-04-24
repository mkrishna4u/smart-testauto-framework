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
package org.uitnet.testing.smartfwk.ui.standard.domobj;

import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocateBy;
import org.uitnet.testing.smartfwk.ui.core.commons.Locator;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;
import org.uitnet.testing.smartfwk.ui.core.objects.textbox.TextBox;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TextBoxSD extends TextBox {
	protected Map<String, Locator> platFormLocators = new HashMap<>();

	public TextBoxSD(String displayName) {
		super(LocatorType.DOM, displayName);
	}

	public TextBoxSD(String displayName, Map<String, Locator> platFormLocators) {
		super(LocatorType.DOM, displayName);
		this.platFormLocators = platFormLocators;
	}

	public TextBoxSD(String displayName, String xpath) {
		super(LocatorType.DOM, displayName);
		platFormLocators.put(SmartConstants.DEFAULT_XPATH_LOCATOR, new Locator(LocateBy.Xpath, xpath));
	}

	public TextBoxSD addPlatformLocatorForNativeApp(PlatformType platform, LocateBy locateBy, String locatorValue) {
		LocatorUtil.setPlatformLocatorForNativeApp(platFormLocators, platform, locateBy, locatorValue);
		return this;
	}

	public TextBoxSD addPlatformLocatorForWebApp(PlatformType platform, WebBrowserType browserType, LocateBy locateBy,
			String locatorValue) {
		LocatorUtil.setPlatformLocatorForWebApp(platFormLocators, platform, browserType, locateBy, locatorValue);
		return this;
	}

	@Override
	public TextBoxValidatorSD getValidator(SmartAppDriver appDriver, Region region) {
		return new TextBoxValidatorSD(appDriver, this, region);
	}

	@Override
	public TextBoxValidatorSD getValidator(SmartCucumberUiScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	public Locator getLocator(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findLocator(platFormLocators, platform, appType, browserType);
	}

	public Map<String, Locator> getPlatformLocators() {
		return platFormLocators;
	}

	@Override
	public UIObject clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextBoxSD updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		String newDisplayName = displayName.replaceAll(":" + paramName, paramValue);

		Map<String, Locator> newPlatFormLocators = new HashMap<>();
		for (Map.Entry<String, Locator> locator : platFormLocators.entrySet()) {
			newPlatFormLocators.put(locator.getKey(), new Locator(locator.getValue().getLocateBy(),
					locator.getValue().getValue().replaceAll(":" + paramName, paramValue)));
		}
		return new TextBoxSD(newDisplayName, newPlatFormLocators);
	}
}
