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
package org.uitnet.testing.smartfwk.ui.standard.imgobj;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class WebPageTitleSI extends ImageObject {

	public WebPageTitleSI(String displayName, String image) {
		super(UIObjectType.webPageTitle, displayName, image);
	}

	@Override
	public WebPageTitleValidatorSI getValidator(WebBrowser browser, Region region) {
		return new WebPageTitleValidatorSI(browser, this, region);
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

}