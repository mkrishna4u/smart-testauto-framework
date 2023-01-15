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
public abstract class MultiStateElementValidator extends UIObjectValidator {
	private MultiStateElement multiStateBox;

	public MultiStateElementValidator(SmartAppDriver appDriver, MultiStateElement uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.multiStateBox = uiObject;
	}

	@Override
	public MultiStateElement getUIObject() {
		return multiStateBox;
	}

	public abstract boolean isStateSelected(String state, int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator validateStateSelected(String state, int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator selectState(String state, int maxIterationsToLocateElements);

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract MultiStateElementValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);
}
