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
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
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

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract boolean isReadonly(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateReadonly(int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateNotReadonly(int maxIterationsToLocateElements);

	public abstract TextBoxValidator typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements);
	
	public abstract TextBoxValidator typeText(String text, NewTextLocation location, int typeSpeedInMspc, boolean clickBeforeType, int maxIterationsToLocateElements);

	public abstract TextBoxValidator validateValue(String expectedValue, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract String getValue(int maxIterationsToLocateElements);
}
