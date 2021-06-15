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
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.textarea.TextArea;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TextAreaSI extends TextArea {
	protected String leftSideImg;
	protected String rightSideImg;
	protected ObjectLocation location;
	protected boolean readOnly;
	protected boolean disabled;

	public TextAreaSI(String displayName, String leftSideImg, String rightSideImg, ObjectLocation location) {
		super(LocatorType.IMAGE, displayName);
		this.leftSideImg = leftSideImg;
		this.rightSideImg = rightSideImg;
		this.location = location;
		this.readOnly = false;
		this.disabled = false;
	}

	public String getLeftSideImage() {
		return leftSideImg;
	}

	public String getRightSideImage() {
		return rightSideImg;
	}

	public ObjectLocation getLocation() {
		return location;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public TextAreaSI setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public TextAreaSI setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	@Override
	public TextAreaValidatorSI getValidator(WebBrowser browser, Region region) {
		return new TextAreaValidatorSI(browser, this, region);
	}

	@Override
	public TextAreaSI clone() {
		return new TextAreaSI(displayName, leftSideImg, rightSideImg, location);
	}

	@Override
	@Deprecated
	public TextAreaSI updateLocatorParameterWithValue(String paramName, String value) {
		return this;
	}

}
