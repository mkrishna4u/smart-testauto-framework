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
package smartfwk.testing.ui.core.objects.radio;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.commons.ItemMap;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class RadioButtonGroupValidator extends UIObjectValidator {
	private RadioButtonGroup optionGroup;

	public RadioButtonGroupValidator(WebBrowser browser, RadioButtonGroup uiObject, Region region) {
		super(browser, uiObject, region);
		this.optionGroup = uiObject;
	}

	@Override
	public RadioButtonGroup getUIObject() {
		return optionGroup;
	}

	public abstract void validateDisabled(int numRetries);

	public abstract void validateEnabled(int numRetries);

	public abstract void selectOption(String value, String displayValue, int numRetries);

	public abstract void validateSelectedOption(String value, String displayValue, int numRetries);

	/**
	 * 
	 * @param options: ItemMap contains key: optionValue, value: optionDisplayValue
	 * @param numRetries
	 */
	public abstract void validateNotSelectedOptions(ItemMap<String, String> options, int numRetries);
}
