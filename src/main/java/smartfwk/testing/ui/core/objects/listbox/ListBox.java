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
package smartfwk.testing.ui.core.objects.listbox;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.commons.UIObjectType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class ListBox extends UIObject {
	public ListBox(LocatorType locatorType, String displayName) {
		super(locatorType, UIObjectType.listBox, displayName);
	}

	public abstract ListBoxValidator getValidator(WebBrowser browser, Region region);
}