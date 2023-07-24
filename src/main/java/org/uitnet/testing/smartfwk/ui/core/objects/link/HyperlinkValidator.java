/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.core.objects.link;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class HyperlinkValidator extends UIObjectValidator {
	private Hyperlink hyperlink;

	public HyperlinkValidator(SmartAppDriver appDriver, Hyperlink uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.hyperlink = uiObject;
	}

	@Override
	public Hyperlink getUIObject() {
		return hyperlink;
	}

	public abstract HyperlinkValidator validateName(String expectedValue, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract String getName(int maxIterationsToLocateElements);
}