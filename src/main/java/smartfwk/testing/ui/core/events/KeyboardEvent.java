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
package smartfwk.testing.ui.core.events;

import org.openqa.selenium.Keys;

import smartfwk.testing.ui.core.objects.NewTextLocation;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class KeyboardEvent extends InputEvent<KeyboardEventName> {
	protected Keys key;
	protected String inputTextBeforeKeyAction;
	protected NewTextLocation textLocation;
	
	public KeyboardEvent(KeyboardEventName name, Keys key, String inputTextBeforeKeyAction, NewTextLocation textLocation) {
		this.type = InputEventType.keyBoard;
		this.name = name;
		this.key = key;
		this.inputTextBeforeKeyAction = inputTextBeforeKeyAction;
		this.textLocation = textLocation;
	}
	
	/**	
	 * @return the keyboard key.
	 */
	public Keys getKey() {
		return key;
	}
	
	/**
	 * @return the input text that is associated with the key.
	 */
	public String getInputText() {
		return inputTextBeforeKeyAction;
	}
	
	/**
	 * Location where to type the text.
	 * @return
	 */
	public NewTextLocation getTextLocation() {
		return textLocation;
	}
}
