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
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.ObjectLocation;
import smartfwk.testing.ui.core.objects.radio.RadioButton;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RadioButtonSI extends RadioButton {
	protected String radioButtonImg;
	protected ObjectLocation radioButtonImgLocation;

	public RadioButtonSI(String displayName, String radioButtonImg, ObjectLocation radioButtonImgLocation) {
		super(LocatorType.IMAGE, displayName);
		this.radioButtonImg = radioButtonImg;
		this.radioButtonImgLocation = radioButtonImgLocation;
	}

	public String getRadioButtonImage() {
		return radioButtonImg;
	}

	public ObjectLocation getRadioButtonImageLocation() {
		return radioButtonImgLocation;
	}

	@Override
	public RadioButtonValidatorSI getValidator(WebBrowser browser, Region region) {
		return new RadioButtonValidatorSI(browser, this, region);
	}

	@Override
	public RadioButtonSI clone() {
		return null;
	}

	@Override
	public RadioButtonSI updateLocatorParameterWithValue(String paramName, String value) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
