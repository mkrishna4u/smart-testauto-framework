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
package org.uitnet.testing.smartfwk.ui.core.objects.scrollbar;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class ScrollbarValidator extends UIObjectValidator {
	private Scrollbar scrollableElem;

	public ScrollbarValidator(SmartAppDriver appDriver, Scrollbar uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.scrollableElem = uiObject;
	}

	@Override
	public Scrollbar getUIObject() {
		return scrollableElem;
	}

	public abstract boolean isDisabled(int maxIterationsToLocateElements);

	public abstract ScrollbarValidator validateDisabled(int maxIterationsToLocateElements);

	public abstract ScrollbarValidator validateEnabled(int maxIterationsToLocateElements);

	public abstract boolean isDisabledButNotReadonly(int maxIterationsToLocateElements);

	public abstract ScrollbarValidator setHorizontalThumbGripLocation(int numPixelsFromLeft, int maxIterationsToLocateElements);
	public abstract ScrollbarValidator setHorizontalThumbGripLocation(double pctValueFromLeft, int maxIterationsToLocateElements);

	public abstract ScrollbarValidator setVerticalThumbGripLocation(int numPixelsFromTop, int maxIterationsToLocateElements);
	public abstract ScrollbarValidator setVerticalThumbGripLocation(double pctValueFromTop, int maxIterationsToLocateElements);

}
