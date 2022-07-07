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
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.objects.ObjectLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.listbox.ListBox;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ListBoxSI extends ListBox {
	protected int width;
	protected int height;
	protected ObjectLocation location;
	protected boolean readOnly;
	protected boolean disabled;

	public ListBoxSI(String displayName, int width, int height, ObjectLocation location) {
		super(LocatorType.IMAGE, displayName);
		this.width = width;
		this.height = height;
		this.location = location;
		this.readOnly = true;
		this.disabled = false;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ObjectLocation getLocation() {
		return location;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public ListBoxSI setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public ListBoxSI setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	@Override
	public ListBoxValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new ListBoxValidatorSI(appDriver, this, region);
	}

	@Override
	public ListBoxValidatorSI getValidator(SmartCucumberUiScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

	@Override
	public ListBoxSI clone() {
		return new ListBoxSI(displayName, width, height, location);
	}

	@Override
	public ListBoxSI updateLocatorParameterWithValue(String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
