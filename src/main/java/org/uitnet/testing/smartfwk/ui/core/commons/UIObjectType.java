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
package org.uitnet.testing.smartfwk.ui.core.commons;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum UIObjectType {
	accordion, button, checkBox, checkBoxGroup, comboBox, listBox, choices, dateItem, dateTimeItem, timeItem,
	horizontalScrollBar, hyperlink, image, label, table, locator, menuItem, radioButton, radioButtonGroup, section,
	tabSheet, tab, textArea, textBox, textBoxWithSearchIcon, toolStrip, verticalScrollBar, webPageTitle, webPage,
	webURL, leftSideImageOfRectangle, rightSideImageOfRectangle, headerColumn,

	projectSpecific // Used only for project specific component, no handling will be there in core
					// framework.
}
