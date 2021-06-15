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
package org.uitnet.testing.smartfwk.ui.core.objects.datetime;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class DateItemValidator extends UIObjectValidator {
	private DateItem dateItem;

	public DateItemValidator(WebBrowser browser, DateItem uiObject, Region region) {
		super(browser, uiObject, region);
		this.dateItem = uiObject;
	}

	@Override
	public DateItem getUIObject() {
		return this.dateItem;
	}

	public abstract void validateDisabled(int numRetries);

	public abstract void validateEnabled(int numRetries);

	public abstract void typeDate(String date, int numRetries);

	public abstract void selectDateUsingDatePicker(String date, int numRetries);

	public abstract void validateDate(String date, TextValidationMechanism validationMechanism, int numRetries);
}