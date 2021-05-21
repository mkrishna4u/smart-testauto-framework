/*
 * SmartTestAutoFwk
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
package smartfwk.testing.ui.core.objects.logon;

import java.awt.Rectangle;

import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.UIObjectType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class LoginSuccessPageValidator {
	protected WebBrowser browser;
	protected UIObject uiObject;
	protected Region region;
	
	public LoginSuccessPageValidator(UIObject locator, Region region) {
		this.uiObject = locator;
		if (browser != null) {
			this.region = (region == null) ? new Region(
					new Rectangle(0, 0, new Double(browser.getAppConfig().getBrowserWindowSize().getWidth()).intValue(),
							new Double(browser.getAppConfig().getBrowserWindowSize().getHeight()).intValue()))
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
		Assert.assertNotNull(browser, "WebBrowser instance is null. Please set before using this API.");
		validateInfo(activeUserProfileName);
	}

	public void logout(String activeUserProfileName) {
		Assert.assertNotNull(browser, "WebBrowser instance is null. Please set before using this API.");
		tryLogout(activeUserProfileName);		
	}

	public boolean isLoginSuccessPageVisible(String activeUserProfileName) {
		Assert.assertNotNull(browser, "WebBrowser instance is null. Please set before using this API.");
		return checkLoginSuccessPageVisible(activeUserProfileName);
	}

	public void setInitParams(WebBrowser browser) {
		this.browser = browser;
	}

	protected abstract void tryLogout(String activeUserProfileName);

	protected abstract void validateInfo(String activeUserProfileName);

	protected abstract boolean checkLoginSuccessPageVisible(String activeUserProfileName);

}
