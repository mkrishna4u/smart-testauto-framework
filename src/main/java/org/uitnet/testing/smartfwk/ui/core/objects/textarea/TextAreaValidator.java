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
package org.uitnet.testing.smartfwk.ui.core.objects.textarea;

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
public abstract class TextAreaValidator extends UIObjectValidator {
	private TextArea textArea;

	public TextAreaValidator(SmartAppDriver appDriver, TextArea uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.textArea = uiObject;
	}

	@Override
	public TextArea getUIObject() {
		return textArea;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract boolean isReadonly(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateReadonly(int maxIterationsToLocateElements);

	public abstract TextAreaValidator validateNotReadonly(int maxIterationsToLocateElements);

	public abstract TextAreaValidator typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements);

	public abstract TextAreaValidator typeText(String textToType, NewTextLocation location, int typeSpeedInMspc, boolean clickBeforeType, int maxIterationsToLocateElements);
	
	public abstract TextAreaValidator validateTextValue(String expectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements);

	public abstract String getTextValue(int maxIterationsToLocateElements);
}
