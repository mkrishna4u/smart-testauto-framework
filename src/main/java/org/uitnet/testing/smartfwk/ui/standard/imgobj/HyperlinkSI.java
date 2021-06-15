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
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.link.Hyperlink;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HyperlinkSI extends Hyperlink {
	protected String hyperlinkImg;
	protected ObjectLocation hyperlinkImgLocation;

	public HyperlinkSI(String displayName, String hyperlinkImg, ObjectLocation hyperlinkImgLocation) {
		super(LocatorType.IMAGE, displayName);
		this.hyperlinkImg = hyperlinkImg;
		this.hyperlinkImgLocation = hyperlinkImgLocation;
	}

	public String getHyperlinkImage() {
		return hyperlinkImg;
	}

	public ObjectLocation getHyperlinkImageLocation() {
		return hyperlinkImgLocation;
	}

	@Override
	public HyperlinkValidatorSI getValidator(WebBrowser browser, Region region) {
		return new HyperlinkValidatorSI(browser, this, region);
	}

	@Override
	public HyperlinkSI clone() {
		return null;
	}

	@Override
	public HyperlinkSI updateLocatorParameterWithValue(String paramName, String value) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
