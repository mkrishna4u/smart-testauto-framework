/*
 * SmartTestAutoFwk
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
package smartfwk.testing.ui.core.objects.combobox;

import java.util.List;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.commons.ItemList;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObjectValidator;
import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class ComboBoxValidator extends UIObjectValidator {
	private ComboBox comboBox;

	public ComboBoxValidator(WebBrowser browser, ComboBox uiObject, Region region) {
		super(browser, uiObject, region);
		this.comboBox = uiObject;
	}

	@Override
	public ComboBox getUIObject() {
		return this.comboBox;
	}

	public abstract void validateDisabled(int numRetries);

	public abstract void validateEnabled(int numRetries);

	public abstract void validateSelectedItem(String expectedSelectedValue, TextValidationMechanism validationMechanism,
			int numRetries);

	public abstract String getSelectedItem(int numRetries);
	
	public abstract List<String> getSelectedItems(int numRetries);

	public abstract void selectFirstItem(int numRetries);

	public abstract void selectLastItem(int numRetries);

	public abstract void selectItem(String itemName, int numRetries);

	public abstract void selectItems(ItemList<String> itemsToBeSelected, int numRetries);

	public abstract void validateItemsPresent(ItemList<String> items, int numRetries);

	public abstract void validateItemsNotPresent(ItemList<String> items, int numRetries);
}
