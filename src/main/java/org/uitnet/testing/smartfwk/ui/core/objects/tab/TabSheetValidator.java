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
package org.uitnet.testing.smartfwk.ui.core.objects.tab;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemSet;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class TabSheetValidator extends UIObjectValidator {
	private TabSheet tabSheet;

	public TabSheetValidator(SmartAppDriver appDriver, TabSheet locator, Region region) {
		super(appDriver, locator, region);
		this.tabSheet = locator;
	}

	@Override
	public TabSheet getUIObject() {
		return this.tabSheet;
	}

	public void setComponent(TabSheet component) {
		this.tabSheet = component;
	}

	public abstract void selectTab(String tabName, int maxIterationsToLocateElements);

	public abstract String findSelectedTab(int maxIterationsToLocateElements);

	public abstract void validateSelectedTab(String expectedSelectedTabName, int maxIterationsToLocateElements);

	public abstract void validateTabsPresent(ItemSet<String> allTabNames, int maxIterationsToLocateElements);

	public abstract void validateDisabledTabs(ItemSet<String> disabledTabNames, int maxIterationsToLocateElements);

	public abstract void validateEnabledTabs(ItemSet<String> enabledTabNames, int maxIterationsToLocateElements);
}
