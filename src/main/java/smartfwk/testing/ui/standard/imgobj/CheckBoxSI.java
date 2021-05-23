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
package smartfwk.testing.ui.standard.imgobj;

import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.ObjectLocation;
import smartfwk.testing.ui.core.objects.checkbox.CheckBox;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class CheckBoxSI extends CheckBox {
	protected String checkBoxImg;
	protected ObjectLocation checkBoxImgLocation;

	public CheckBoxSI(String displayName, String checkBoxImg, ObjectLocation checkBoxImgLocation) {
		super(LocatorType.IMAGE, displayName);
		this.checkBoxImg = checkBoxImg;
		this.checkBoxImgLocation = checkBoxImgLocation;
	}

	public String getCheckBoxImage() {
		return checkBoxImg;
	}

	public ObjectLocation getCheckBoxImageLocation() {
		return checkBoxImgLocation;
	}

	@Override
	public CheckBoxValidatorSI getValidator(WebBrowser browser, Region region) {
		return new CheckBoxValidatorSI(browser, this, region);
	}

	@Override
	public CheckBoxSI clone() {
		return null;
	}

	@Override
	public CheckBoxSI updateLocatorParameterWithValue(String paramName, String value) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
