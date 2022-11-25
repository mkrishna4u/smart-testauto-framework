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

import java.util.LinkedList;
import java.util.List;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class MultiStateBox extends UIObject {
	protected List<String> validStates = new LinkedList<>();
	
	public MultiStateBox(LocatorType locatorType, String displayName, String[] validStates) {
		super(locatorType, UIObjectType.multiStateBox, displayName);
		if(validStates != null) {
			for(String s : validStates) {
				this.validStates.add(s);
			}
		}
	}
	
	public List<String> getValidStates() {
		return validStates;
	}

	public abstract MultiStateBoxValidator getValidator(SmartAppDriver appDriver, Region region);

	public abstract MultiStateBoxValidator getValidator(SmartCucumberScenarioContext scenarioContext, Region region);
}
