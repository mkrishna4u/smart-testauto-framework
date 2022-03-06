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
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class DateItemValidator extends UIObjectValidator {
	private DateItem dateItem;

	public DateItemValidator(SmartAppDriver appDriver, DateItem uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.dateItem = uiObject;
	}

	@Override
	public DateItem getUIObject() {
		return this.dateItem;
	}

	public abstract void validateDisabled(int maxIterationsToLocateElements);

	public abstract void validateEnabled(int maxIterationsToLocateElements);

	public abstract void validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void typeDate(String date, int maxIterationsToLocateElements);

	public abstract void selectDateUsingDatePicker(String date, int maxIterationsToLocateElements);

	public abstract void validateDate(String date, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);
}