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
package org.uitnet.testing.smartfwk.ui.core.objects.accordion;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.Sections;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AccordionValidator extends UIObjectValidator {
	private Accordion accordion;

	public AccordionValidator(SmartAppDriver appDriver, Accordion uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.accordion = uiObject;
	}

	@Override
	public Accordion getUIObject() {
		return accordion;
	}

	public abstract void validateDisabledSections(Sections sections, int maxIterationsToLocateElements);

	public abstract void validateOpenedSections(Sections sections, int maxIterationsToLocateElements);

	public abstract void validateClosedSections(Sections sections, int maxIterationsToLocateElements);

	public abstract void vaidateSectionsPresence(Sections sections, int maxIterationsToLocateElements);

	public abstract void openSection(String section, int maxIterationsToLocateElements);

	public abstract void closeSection(String section, int maxIterationsToLocateElements);

}
