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
package org.uitnet.testing.smartfwk.ui.core.objects;

import java.io.File;

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ImageObject extends UIObject {
	protected String image;

	public ImageObject(UIObjectType elemType, String displayName, String image) {
		super(LocatorType.IMAGE, elemType, displayName);
		this.image = TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + image;
	}

	public ImageObjectValidator getValidator(SmartAppDriver appDriver, Region region) {
		return new ImageObjectValidator(appDriver, this, region);
	}

	public String getImage() {
		return image;
	}

	@Override
	public UIObject clone() {
		return new ImageObject(uiObjectType, getDisplayName(), image);
	}

	@Override
	public ImageObject updateLocatorParameterWithValue(AppConfig appConfig, String paramName, String paramValue) {
		Assert.fail("updateLocatorParameterWithValue() API is not implemented.");
		return this;
	}

}
