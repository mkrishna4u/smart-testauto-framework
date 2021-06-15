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
package org.uitnet.testing.smartfwk.ui.standard.domobj;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.label.Label;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class WebPageTitleSD extends Label {
	protected String xpath;

	public WebPageTitleSD(String displayName, String xpath) {
		super(LocatorType.DOM, displayName);
		this.uiObjectType = UIObjectType.webPageTitle;
		this.xpath = xpath;
	}

	@Override
	public WebPageTitleValidatorSD getValidator(WebBrowser browser, Region region) {
		return new WebPageTitleValidatorSD(browser, this, region);
	}

	public String getLocatorXPath() {
		return xpath;
	}

	@Override
	public WebPageTitleSD clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebPageTitleSD updateLocatorParameterWithValue(String paramName, String value) {
		String newXPath = xpath.replaceAll(":" + paramName, value);
		return new WebPageTitleSD(displayName, newXPath);
	}

}
