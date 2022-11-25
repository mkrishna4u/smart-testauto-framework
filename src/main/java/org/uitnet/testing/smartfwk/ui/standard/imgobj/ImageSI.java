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
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.image.Image;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ImageSI extends Image {
	protected Map<String, String> platformImages = new HashMap<>();
	protected ObjectLocation imageLocation;

	public ImageSI(String displayName, String image, ObjectLocation imageLocation) {
		super(LocatorType.IMAGE, displayName);
		platformImages.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, image);
		this.imageLocation = imageLocation;
	}

	public ImageSI addPlatformImageForNativeApp(PlatformType platform, String image) {
		LocatorUtil.setPlatformImageForNativeApp(platformImages, platform, image);
		return this;
	}

	public ImageSI addPlatformImageForWebApp(PlatformType platform, WebBrowserType browserType, String image) {
		LocatorUtil.setPlatformImageForWebApp(platformImages, platform, browserType, image);
		return this;
	}

	public String getImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(platformImages, platform, appType, browserType);
	}

	public ObjectLocation getImageLocation() {
		return imageLocation;
	}

	@Override
	public ImageValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new ImageValidatorSI(appDriver, this, region);
	}

	@Override
	public ImageValidatorSI getValidator(SmartCucumberScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	@Override
	public ImageSI clone() {
		return null;
	}

	@Override
	public ImageSI updateLocatorParameterWithValue(String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
