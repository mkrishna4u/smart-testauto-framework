/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class CheckBoxGroupValidator extends UIObjectValidator {
	private CheckBoxGroup checkBoxGroup;

	public CheckBoxGroupValidator(SmartAppDriver appDriver, CheckBoxGroup uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.checkBoxGroup = uiObject;
	}

	@Override
	public CheckBoxGroup getUIObject() {
		return checkBoxGroup;
	}

	public abstract void checkAllCheckBoxes(int maxIterationsToLocateElements);
	
	public abstract void uncheckAllCheckBoxes(int maxIterationsToLocateElements);
	
	public abstract void checkCheckBoxes(ItemList<String> cbNames, int maxIterationsToLocateElements);
	
	public abstract void uncheckCheckBoxes(ItemList<String> cbNames, int maxIterationsToLocateElements);
	
	public abstract void validateCheckedCheckBoxes(ItemList<String> cbNames, int maxIterationsToLocateElements);
	
	public abstract void validateUncheckedCheckBoxes(ItemList<String> cbNames, int maxIterationsToLocateElements);

	public abstract void validateCheckBoxesPresent(ItemList<String> cbNames, int maxIterationsToLocateElements);
	
	public abstract void validateCheckBoxesNotPresent(ItemList<String> cbNames, int maxIterationsToLocateElements);
	
	public abstract void validateNoCheckBoxesAreChecked(int maxIterationsToLocateElements);
	
	public abstract void validateAllCheckBoxesAreChecked(int maxIterationsToLocateElements);
}
