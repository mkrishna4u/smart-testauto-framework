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
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;

/**
 * This is an element that have multiple states i.e. Expandable Collapsable
 * section (Accordion), Minimized / Maximized element, triple state checkbox
 * etc.
 * 
 * @author Madhav Krishna
 *
 */
public abstract class MultiStateElement extends UIObject {

	public MultiStateElement(LocatorType locatorType, String displayName) {
		super(locatorType, UIObjectType.multiStateBox, displayName);
	}

	public abstract MultiStateElementValidator getValidator(SmartAppDriver appDriver, Region region);

	public abstract MultiStateElementValidator getValidator(SmartCucumberScenarioContext scenarioContext,
			Region region);
}