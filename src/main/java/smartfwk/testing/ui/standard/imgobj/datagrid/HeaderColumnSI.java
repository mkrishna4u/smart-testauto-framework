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
package smartfwk.testing.ui.standard.imgobj.datagrid;

import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.ObjectLocation;
import smartfwk.testing.ui.core.objects.button.Button;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HeaderColumnSI extends Button {
	protected String columnImg;
	protected ObjectLocation columnImgLocation;

	public HeaderColumnSI(String displayName, String columnImg, ObjectLocation columnImgLocation) {
		super(LocatorType.IMAGE, displayName);
		this.columnImg = columnImg;
		this.columnImgLocation = columnImgLocation;
	}
	
	public String getColumnImage() {
		return columnImg;
	}

	public ObjectLocation getColumnImageLocation() {
		return columnImgLocation;
	}

	@Override
	public HeaderColumnValidatorSI getValidator(WebBrowser browser, Region region) {
		return new HeaderColumnValidatorSI(browser, this, region);
	}

	@Override
	public HeaderColumnSI clone() {
		return null;
	}

	@Override
	public HeaderColumnSI updateLocatorParameterWithValue(String paramName, String value) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented."); 
		return this;
	}

}
