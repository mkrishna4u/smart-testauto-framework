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
package org.uitnet.testing.smartfwk.ui.core.objects.file;

import java.util.List;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class InputFileValidator extends UIObjectValidator {
	private InputFile inputFile;

	public InputFileValidator(SmartAppDriver appDriver, InputFile uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.inputFile = uiObject;
	}

	@Override
	public InputFile getUIObject() {
		return inputFile;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract boolean isEnabled(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateEnabled(int maxIterationsToLocateElements);
	
	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateEnabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract boolean isReadonly(int maxIterationsToLocateElements);
	
	public abstract String getValue(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateReadonly(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateNotReadonly(int maxIterationsToLocateElements);

	public abstract InputFileValidator validateValue(String expectedValue, TextMatchMechanism validationMechanism, int maxIterationsToLocateElements);

	public abstract void selectFiles(ItemList<String> relativeFilePaths, int maxIterationsToLocateElements);
	public abstract void selectFiles(ItemList<String> relativeFilePaths, int waitTimeInSecondsAfterSelect, int maxIterationsToLocateElements);
}
