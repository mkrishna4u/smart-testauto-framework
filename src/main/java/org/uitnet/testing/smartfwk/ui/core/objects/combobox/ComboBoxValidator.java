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
package org.uitnet.testing.smartfwk.ui.core.objects.combobox;

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
public abstract class ComboBoxValidator extends UIObjectValidator {
	private ComboBox comboBox;

	public ComboBoxValidator(SmartAppDriver appDriver, ComboBox uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.comboBox = uiObject;
	}

	@Override
	public ComboBox getUIObject() {
		return this.comboBox;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateEnabled(int maxIterationsToLocateElements);

	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateSelectedItem(String expectedSelectedValue,
			TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract String getSelectedItem(int maxIterationsToLocateElements);

	public abstract List<String> getSelectedItems(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator selectFirstItem(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator selectLastItem(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator selectItem(String itemName, int maxIterationsToLocateElements);
	
	public abstract ComboBoxValidator selectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements);

	public abstract ComboBoxValidator selectItems(ItemList<String> itemsToBeSelected,
			int maxIterationsToLocateElements);
	
	public abstract ComboBoxValidator selectItems(ItemList<String> itemsToBeSelected,
			TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements);
	
	public abstract ComboBoxValidator selectAllItems(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator deselectItem(String itemName, int maxIterationsToLocateElements);
	
	public abstract ComboBoxValidator deselectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements);
	
	public abstract ComboBoxValidator deselectItems(ItemList<String> itemsToBeDeselected, int maxIterationsToLocateElements);

	public abstract ComboBoxValidator deselectAllItems(int maxIterationsToLocateElements);

	public abstract ComboBoxValidator deselectItems(ItemList<String> itemsToBeDeselected,
			TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements);

	public abstract ComboBoxValidator validateItemsNotPresent(ItemList<String> items,
			int maxIterationsToLocateElements);
}
