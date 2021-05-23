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
package smartfwk.testing.ui.core.objects;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.commons.UIObjectType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class UIObject {
	protected LocatorType locatorType;
	protected UIObjectType uiObjectType;
	protected String displayName;
	
	public UIObject(LocatorType locatorType, UIObjectType type, String displayName) {
		this.locatorType = locatorType;
		this.uiObjectType = type;
		this.displayName = displayName;
	}
	
	public LocatorType getLocatorType() {
		return locatorType;
	}
	
	public UIObjectType getType() {
		return uiObjectType;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public abstract UIObject clone();
	public abstract UIObject updateLocatorParameterWithValue(String paramName, String value);
	public abstract UIObjectValidator getValidator(WebBrowser browser, Region region);
}