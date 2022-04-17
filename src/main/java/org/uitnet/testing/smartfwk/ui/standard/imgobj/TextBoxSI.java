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
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.textbox.TextBox;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TextBoxSI extends TextBox {
	protected Map<String, String> leftSideImgs = new HashMap<>();
	protected Map<String, String> rightSideImgs = new HashMap<>();
	protected ObjectLocation location;
	protected boolean readOnly;
	protected boolean disabled;

	public TextBoxSI(String displayName, String leftSideImg, String rightSideImg, ObjectLocation location) {
		super(LocatorType.IMAGE, displayName);
		leftSideImgs.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, leftSideImg);
		rightSideImgs.put(SmartConstants.DEFAULT_IMAGE_LOCATOR, rightSideImg);
		this.location = location;
		this.readOnly = false;
		this.disabled = false;
	}

	public TextBoxSI addPlatformImageForNativeApp(PlatformType platform, String leftSideImg, String rightSideImg) {
		LocatorUtil.setPlatformImageForNativeApp(leftSideImgs, platform, leftSideImg);
		LocatorUtil.setPlatformImageForNativeApp(rightSideImgs, platform, rightSideImg);
		return this;
	}

	public TextBoxSI addPlatformImageForWebApp(PlatformType platform, WebBrowserType browserType, String leftSideImg,
			String rightSideImg) {
		LocatorUtil.setPlatformImageForWebApp(leftSideImgs, platform, browserType, leftSideImg);
		LocatorUtil.setPlatformImageForWebApp(rightSideImgs, platform, browserType, rightSideImg);
		return this;
	}

	public String getLeftSideImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(leftSideImgs, platform, appType, browserType);
	}

	public String getRightSideImage(PlatformType platform, ApplicationType appType, WebBrowserType browserType) {
		return LocatorUtil.findImage(rightSideImgs, platform, appType, browserType);
	}

	public ObjectLocation getLocation() {
		return location;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public TextBoxSI setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public TextBoxSI setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	@Override
	public TextBoxValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new TextBoxValidatorSI(appDriver, this, region);
	}

	@Override
	public TextBoxSI clone() {
		Assert.fail("clone() API is not implemented.");
		return this;
	}

	@Override
	public TextBoxSI updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
