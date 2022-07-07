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
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.label.Label;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class LabelSI extends Label {
	protected Map<String, String> platformImages = new HashMap<>();
	protected ObjectLocation labelImgLocation;

	public LabelSI(String displayName, String labelImg, ObjectLocation labelImgLocation) {
		super(LocatorType.IMAGE, displayName);
		platformImages.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, labelImg);
		this.labelImgLocation = labelImgLocation;
	}

	public LabelSI addPlatformImageForNativeApp(PlatformType platform, String labelImg) {
		LocatorUtil.setPlatformImageForNativeApp(platformImages, platform, labelImg);
		return this;
	}

	public LabelSI addPlatformImageForWebApp(PlatformType platform, WebBrowserType browserType, String labelImg) {
		LocatorUtil.setPlatformImageForWebApp(platformImages, platform, browserType, labelImg);
		return this;
	}

	public String getLabelImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(platformImages, platform, appType, browserType);
	}

	public ObjectLocation getLabelImageLocation() {
		return labelImgLocation;
	}

	@Override
	public LabelValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new LabelValidatorSI(appDriver, this, region);
	}

	@Override
	public LabelValidatorSI getValidator(SmartCucumberUiScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	@Override
	public LabelSI clone() {
		return null;
	}

	@Override
	public LabelSI updateLocatorParameterWithValue(String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
