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
package org.uitnet.testing.smartfwk.ui.core.objects.radio;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class RadioButtonValidator extends UIObjectValidator {
	private RadioButton option;

	public RadioButtonValidator(SmartAppDriver appDriver, RadioButton uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.option = uiObject;
	}

	@Override
	public RadioButton getUIObject() {
		return option;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract void validateDisabled(int maxIterationsToLocateElements);

	public abstract void validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void select(int maxIterationsToLocateElements);

	public abstract void validateSelected(int maxIterationsToLocateElements);

	public abstract void validateNotSelected(int maxIterationsToLocateElements);
}
