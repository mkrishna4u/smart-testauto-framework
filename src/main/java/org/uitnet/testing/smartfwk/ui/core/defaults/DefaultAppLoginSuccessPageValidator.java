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
package org.uitnet.testing.smartfwk.ui.core.defaults;

import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginSuccessPageValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultAppLoginSuccessPageValidator extends LoginSuccessPageValidator {

	public DefaultAppLoginSuccessPageValidator() {
		super(null, null);
	}

	@Override
	public void setInitParams(SmartAppDriver appDriver) {
		this.appDriver = appDriver;

	}

	@Override
	protected void tryLogout(String activeUserProfileName) {
		// Write code to logout from application
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		// Write code to validate whether login success page is visible.
	}

	@Override
	protected boolean checkLoginSuccessPageVisible(String activeUserProfileName) {
		// Write code to check whether the login success page is visible.
		return false;
	}
}
