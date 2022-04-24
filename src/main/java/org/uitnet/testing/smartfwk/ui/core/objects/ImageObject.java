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

import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
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
public class ImageObject extends UIObject {
	protected Map<String, String> platformImages = new HashMap<>();

	public ImageObject(UIObjectType elemType, String displayName, String image) {
		super(LocatorType.IMAGE, elemType, displayName);
		platformImages.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, image);
	}

	public ImageObject(UIObjectType elemType, String displayName, Map<String, String> platformImages) {
		super(LocatorType.IMAGE, elemType, displayName);
		this.platformImages = platformImages;
	}

	public ImageObject addPlatformImageForNativeApp(PlatformType platform, String image) {
		LocatorUtil.setPlatformImageForNativeApp(platformImages, platform, image);
		return this;
	}

	public ImageObject addPlatformImageForWebApp(PlatformType platform, WebBrowserType browserType, String image) {
		LocatorUtil.setPlatformImageForWebApp(platformImages, platform, browserType, image);
		return this;
	}

	public ImageObjectValidator getValidator(SmartAppDriver appDriver, Region region) {
		return new ImageObjectValidator(appDriver, this, region);
	}

	public ImageObjectValidator getValidator(SmartCucumberUiScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	public String getImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(platformImages, platform, appType, browserType);
	}

	@Override
	public UIObject clone() {
		return new ImageObject(uiObjectType, getDisplayName(), platformImages);
	}

	@Override
	public ImageObject updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
