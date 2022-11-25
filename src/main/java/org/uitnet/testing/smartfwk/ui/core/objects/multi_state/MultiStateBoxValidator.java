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
package org.uitnet.testing.smartfwk.ui.core.objects.multi_state;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class MultiStateBoxValidator extends UIObjectValidator {
	private MultiStateBox multiStateBox;

	public MultiStateBoxValidator(SmartAppDriver appDriver, MultiStateBox uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.multiStateBox = uiObject;
	}

	@Override
	public MultiStateBox getUIObject() {
		return multiStateBox;
	}

	public abstract boolean isStateSelected(String state, int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator validateStateSelected(String state, int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator selectState(String state, int maxIterationsToLocateElements);

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract MultiStateBoxValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);
}
