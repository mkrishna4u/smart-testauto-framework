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
package smartfwk.testing.ui.standard.imgobj;

import org.sikuli.script.Region;

import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.ImageObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class WebPageTitleValidatorSI extends ImageObjectValidator {

	public WebPageTitleValidatorSI(WebBrowser browser, WebPageTitleSI locator, Region region) {
		super(browser, locator, region);
	}

	@Override
	public WebPageTitleSI getUIObject() {
		return (WebPageTitleSI) imgLocator;
	}
}
