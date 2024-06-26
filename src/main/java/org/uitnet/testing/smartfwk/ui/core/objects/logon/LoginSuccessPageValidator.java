/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.core.objects.logon;

import java.awt.Rectangle;

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;
import org.uitnet.testing.smartfwk.ui.core.utils.ScreenUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class LoginSuccessPageValidator {
	protected SmartAppDriver appDriver;
	protected UIObject uiObject;
	protected Region region;

	public LoginSuccessPageValidator(UIObject locator, Region region) {
		this.uiObject = locator;
		if (appDriver != null) {
			this.region = (region == null)
					? ScreenUtil.createRegionWithNoError(new Rectangle(0, 0,
							Double.valueOf(appDriver.getAppConfig().getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appDriver.getAppConfig().getBrowserWindowSize().getHeight()).intValue()))
					: region;
		}
	}

	public UIObject getUIObject() {
		return uiObject;
	}

	public UIObjectType geUIObjectType() {
		return uiObject.getType();
	}

	public Region getRegion() {
		return region;
	}

	public void validate(String activeUserProfileName) {
		if(appDriver != null && !appDriver.shouldOpenURL()) { return; }
		if(!DefaultInfo.DEFAULT_USER_PROFILE_NAME.equals(activeUserProfileName)) {
			Assert.assertNotNull(appDriver, "WebappDriver instance is null. Please set before using this API.");
			validateInfo(activeUserProfileName);
		}
	}

	public void logout(String activeUserProfileName) {
		if(appDriver != null && !appDriver.shouldOpenURL()) { return; }
		if(!DefaultInfo.DEFAULT_USER_PROFILE_NAME.equals(activeUserProfileName)) {
			Assert.assertNotNull(appDriver, "WebappDriver instance is null. Please set before using this API.");
			tryLogout(activeUserProfileName);
		}
	}

	public boolean isLoginSuccessPageVisible(String activeUserProfileName) {
		if(appDriver != null && !appDriver.shouldOpenURL()) { return false; }
		if(!DefaultInfo.DEFAULT_USER_PROFILE_NAME.equals(activeUserProfileName)) {
			Assert.assertNotNull(appDriver, "WebappDriver instance is null. Please set before using this API.");
			return checkLoginSuccessPageVisible(activeUserProfileName);
		}
		return false;
	}

	public void setInitParams(SmartAppDriver appDriver) {
		this.appDriver = appDriver;
	}

	protected abstract void tryLogout(String activeUserProfileName);

	protected abstract void validateInfo(String activeUserProfileName);

	protected abstract boolean checkLoginSuccessPageVisible(String activeUserProfileName);

}
