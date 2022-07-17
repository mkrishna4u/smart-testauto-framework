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
package org.uitnet.testing.smartfwk.ui.core.objects.checkbox;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class CheckBoxValidator extends UIObjectValidator {
	private CheckBox checkBox;

	public CheckBoxValidator(SmartAppDriver appDriver, CheckBox uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.checkBox = uiObject;
	}

	@Override
	public CheckBox getUIObject() {
		return checkBox;
	}

	public abstract boolean isCheckBoxChecked(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateCheckBoxChecked(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateCheckBoxUnchecked(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator checkAndValidateChecked(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator uncheckAndValidateUnchecked(int maxIterationsToLocateElements);

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract CheckBoxValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);
}
