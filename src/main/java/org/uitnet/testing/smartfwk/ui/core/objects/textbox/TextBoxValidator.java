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
package org.uitnet.testing.smartfwk.ui.core.objects.textbox;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class TextBoxValidator extends UIObjectValidator {
	private TextBox textBox;

	public TextBoxValidator(SmartAppDriver appDriver, TextBox uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.textBox = uiObject;
	}

	@Override
	public TextBox getUIObject() {
		return textBox;
	}

	public abstract boolean isDisabled(int numRetries);

	public abstract void validateDisabled(int numRetries);

	public abstract void validateEnabled(int numRetries);
	
	public abstract boolean isDisabledButNotReadonly(int numRetries);

	public abstract void validateDisabledButNotReadonly(int numRetries);

	public abstract void validateEnabledButNotReadonly(int numRetries);

	public abstract boolean isReadonly(int numRetries);

	public abstract void validateReadonly(int numRetries);

	public abstract void validateNotReadonly(int numRetries);

	public abstract void typeText(String textToType, NewTextLocation location, int numRetries);

	public abstract void validateValue(String expectedValue, TextMatchMechanism validationMechanism, int numRetries);

	public abstract String getValue(int numRetries);
}
