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
package org.uitnet.testing.smartfwk.ui.standard.imgobj;

import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.button.Button;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ButtonSI extends Button {
	protected Map<String, String> platformImages = new HashMap<>();
	protected ObjectLocation buttonImgLocation;

	public ButtonSI(String displayName, String buttonImg, ObjectLocation buttonImgLocation) {
		super(LocatorType.IMAGE, displayName);
		platformImages.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, buttonImg);
		this.buttonImgLocation = buttonImgLocation;
	}

	public ButtonSI addPlatformImageForNativeApp(PlatformType platform, String buttonImg) {
		LocatorUtil.setPlatformImageForNativeApp(platformImages, platform, buttonImg);
		return this;
	}

	public ButtonSI addPlatformImageForWebApp(PlatformType platform, WebBrowserType browserType, String buttonImg) {
		LocatorUtil.setPlatformImageForWebApp(platformImages, platform, browserType, buttonImg);
		return this;
	}

	public String getButtonImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(platformImages, platform, appType, browserType);
	}

	public ObjectLocation getButtonImageLocation() {
		return buttonImgLocation;
	}

	@Override
	public ButtonValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new ButtonValidatorSI(appDriver, this, region);
	}
	
	@Override
	public ButtonValidatorSI getValidator(SmartCucumberUiScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	@Override
	public ButtonSI clone() {
		return null;
	}

	@Override
	public ButtonSI updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
