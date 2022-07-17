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
package org.uitnet.testing.smartfwk.ui.core.objects.button;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class ButtonValidator extends UIObjectValidator {
	private Button button;

	public ButtonValidator(SmartAppDriver appDriver, Button uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.button = uiObject;
	}

	@Override
	public Button getUIObject() {
		return this.button;
	}

	public abstract ButtonValidator validateName(String expectedName, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract ButtonValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract ButtonValidator validateEnabled(int maxIterationsToLocateElements);
}
