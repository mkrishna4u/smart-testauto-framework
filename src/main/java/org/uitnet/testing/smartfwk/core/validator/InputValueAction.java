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
package org.uitnet.testing.smartfwk.core.validator;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum InputValueAction {
	TYPE("type"),
	COMMAND_KEYS("cmd-keys"),
	MOUSE_CLICK("mouse-click"),
	MOUSE_DOUBLECLICK("mouse-doubleclick"),
	MOUSE_DRAG_DROP("mouse-drag-drop"),
	CHECK("check"),
	CHECK_ALL("check-all"),
	UNCHECK("uncheck"),
	UNCHECK_ALL("uncheck-all"),
	SELECT("select"),
	SELECT_ALL("select-all"),
	DESELECT("deselect"),
	DESELECT_ALL("deselect-all");
	

	private String action;

	private InputValueAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public static InputValueAction valueOf2(String type) {
		for (InputValueAction t : values()) {
			if (t.getAction().equals(type)) {
				return t;
			}
		}
		Assert.fail("Input value action '" + type + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return action;
	}
}
