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
package org.uitnet.testing.smartfwk.ui.core.objects;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocateBy;
import org.uitnet.testing.smartfwk.ui.core.commons.Locator;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DOMObject extends UIObject {
	protected Map<String, Locator> platFormLocators = new HashMap<>();

	public DOMObject(String displayName, String xpath) {
		super(LocatorType.DOM, UIObjectType.locator, displayName);
		platFormLocators.put(SmartConstants.DEFAULT_XPATH_LOCATOR, new Locator(LocateBy.Xpath, xpath));
	}

	public DOMObject(UIObjectType elemType, String displayName, String xpath) {
		super(LocatorType.DOM, elemType, displayName);
		platFormLocators.put(SmartConstants.DEFAULT_XPATH_LOCATOR, new Locator(LocateBy.Xpath, xpath));
	}

	public DOMObject(UIObjectType elemType, String displayName, Map<String, Locator> platFormLocators) {
		super(LocatorType.DOM, elemType, displayName);
		this.platFormLocators = platFormLocators;
	}

	public Locator getLocator(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findLocator(platFormLocators, platform, appType, browserType);
	}

	public Map<String, Locator> getPlatformLocators() {
		return platFormLocators;
	}

	@Override
	public DOMObject updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		String newDisplayName = displayName.replaceAll(":" + paramName, paramValue);

		Map<String, Locator> newPlatFormLocators = new HashMap<>();
		for (Map.Entry<String, Locator> locator : platFormLocators.entrySet()) {
			newPlatFormLocators.put(locator.getKey(), new Locator(locator.getValue().getLocateBy(),
					locator.getValue().getValue().replaceAll(":" + paramName, paramValue)));
		}
		return new DOMObject(uiObjectType, newDisplayName, newPlatFormLocators);
	}

	@Override
	public DOMObjectValidator getValidator(SmartAppDriver appDriver, Region region) {
		return new DOMObjectValidator(appDriver, this, region);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DOMObject clone() {
		try {
			Class[] paramTypes = new Class[3];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;
			paramTypes[2] = Map.class;
			Constructor ctor = this.getClass().getConstructor(paramTypes);

			Map<String, Locator> newPlatFormLocators = new HashMap<>();

			for (Map.Entry<String, Locator> entry : platFormLocators.entrySet()) {
				newPlatFormLocators.put(entry.getKey(),
						new Locator(entry.getValue().getLocateBy(), entry.getValue().getValue()));
			}

			Object[] paramValues = new Object[3];
			paramValues[0] = uiObjectType;
			paramValues[1] = displayName;
			paramValues[2] = newPlatFormLocators;

			return (DOMObject) ctor.newInstance(paramValues);
		} catch (Exception ex) {
			new Throwable(ex);
		}
		return null;
	}
}
