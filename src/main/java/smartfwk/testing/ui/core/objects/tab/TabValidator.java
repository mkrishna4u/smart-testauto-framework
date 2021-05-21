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
package smartfwk.testing.ui.core.objects.tab;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class TabValidator extends UIObjectValidator {
	private Tab tab;

	public TabValidator(WebBrowser browser, Tab uiObject, Region region) {
		super(browser, uiObject, region);
		this.tab = uiObject;
	}

	@Override
	public Tab getUIObject() {
		return tab;
	}

	public abstract void validateDisabled(int numRetries);

	public abstract void validateEnabled(int numRetries);

	public abstract void selectTab(int numRetries);

	public abstract void validateTabName(String tabName, int numRetries);

	public abstract void validateSelected(int numRetries);

	public abstract void validateNotSelected(int numRetries);

	public abstract void closeTab(int numRetries);
}
