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
package smartfwk.testing.ui.core.objects;

import java.lang.reflect.Constructor;

import org.openqa.selenium.By;
import org.sikuli.script.Region;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.commons.UIObjectType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DOMObject extends UIObject {
	protected String xpath;

	public DOMObject(String displayName, String xpath) {
		super(LocatorType.DOM, UIObjectType.locator, displayName);
		this.xpath = xpath;
	}
	
	public DOMObject(UIObjectType elemType, String displayName, String xpath) {
		super(LocatorType.DOM, elemType, displayName);
		this.xpath = xpath;
	}

	public String getLocatorAsXPath() {
		return xpath;
	}

	public By getLocatorAsBy() {
		return By.xpath(xpath);
	}

	@Override
	public DOMObject updateLocatorParameterWithValue(String paramName, String value) {
		xpath = xpath.replaceAll(":" + paramName, value);
		return this;
	}

	@Override
	public DOMObjectValidator getValidator(WebBrowser browser, Region region) {
		return new DOMObjectValidator(browser, this, region);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public DOMObject clone() {
		try {
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;
			Constructor ctor = this.getClass().getConstructor(paramTypes);

			Object[] paramValues = new Object[2];
			paramValues[0] = displayName;
			paramValues[1] = xpath;

			return (DOMObject) ctor.newInstance(paramValues);
		} catch (Exception ex) {
			new Throwable(ex);
		}
		return null;
	}
}
