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
package org.uitnet.testing.smartfwk.ui.core.objects.choices;

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
public abstract class ChoicesValidator extends UIObjectValidator {
	private Choices choices;

	public ChoicesValidator(SmartAppDriver appDriver, Choices uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.choices = uiObject;
	}

	@Override
	public Choices getUIObject() {
		return this.choices;
	}

	public abstract void validateItemDisabled(String itemName, int maxIterationsToLocateElements);

	public abstract void validateItemEnabled(String itemName, int maxIterationsToLocateElements);

	public abstract void validateItemSelected(String item, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract void validateItemsSelected(ItemList<String> items, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements);

	public abstract String getSelectedItem(int maxIterationsToLocateElements);

	public abstract List<String> getSelectedItems(int maxIterationsToLocateElements);

	public abstract List<String> getAllItems(int maxIterationsToLocateElements);

	public abstract void selectFirstItem(int maxIterationsToLocateElements);

	public abstract void selectLastItem(int maxIterationsToLocateElements);

	public abstract void selectItem(String itemName, int maxIterationsToLocateElements);

	public abstract void selectItems(ItemList<String> itemsToBeSelected, int maxIterationsToLocateElements);

	public abstract void deselectItem(String itemName, int maxIterationsToLocateElements);

	public abstract void deselectItems(ItemList<String> itemsToBeDeselected, int maxIterationsToLocateElements);

	public abstract void validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements);

	public abstract void validateItemsNotPresent(ItemList<String> items, int maxIterationsToLocateElements);
}
