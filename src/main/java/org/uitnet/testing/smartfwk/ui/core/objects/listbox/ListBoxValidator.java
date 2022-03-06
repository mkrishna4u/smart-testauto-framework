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
package org.uitnet.testing.smartfwk.ui.core.objects.listbox;

import java.util.List;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class ListBoxValidator extends UIObjectValidator {
	private ListBox comboBox;

	public ListBoxValidator(SmartAppDriver appDriver, ListBox uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.comboBox = uiObject;
	}

	@Override
	public ListBox getUIObject() {
		return this.comboBox;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract void validateDisabled(int maxIterationsToLocateElements);

	public abstract void validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract void validateSelectedItem(String expectedSelectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements);

	public abstract String getSelectedItem(int maxIterationsToLocateElements);

	public abstract List<String> getSelectedItems(int maxIterationsToLocateElements);

	public abstract void selectFirstItem(int maxIterationsToLocateElements);

	public abstract void selectLastItem(int maxIterationsToLocateElements);

	public abstract void selectItem(String itemName, int maxIterationsToLocateElements);

	public abstract void selectItems(ItemList<String> itemsToBeSelected, int maxIterationsToLocateElements);

	public abstract void validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements);

	public abstract void validateItemsNotPresent(ItemList<String> items, int maxIterationsToLocateElements);
}
