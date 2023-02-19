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

import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.data.builder.TestDataBuilder;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * Input data for the components used on UI forms to fill the form element information.
 * 
 * @author Madhav Krishna
 *
 */
public class InputValue {
	/**
	 * Used to perform operation.
	 */
	private InputValueAction action = InputValueAction.TYPE;
	
	/**
	 * Valid values as per enum. For more details @see InputValueType
	 */
	private InputValueType valueType = InputValueType.STRING;
	
	/**
	 * We can specify single / multiple values.
	 */
	private Object value;
	
	/**
	 * Used to input value at the particular location mainly in input text fields on UI.
	 * For more details @see NewTextLocation.
	 */
	private NewTextLocation location = NewTextLocation.replace;
	
	/**
	 * applicable when valueType = "auto-gen"
	 */
	private TestDataBuilder autoValueInputs = new TestDataBuilder();
	
	/**
	 * Valid values as per enum. For more details @see TextMatchMechanism.
	 * Mostly applicable for Combobox / Select / ListBox UI components.
	 */
	private TextMatchMechanism selectingOptionMatchMechanism = TextMatchMechanism.exactMatchWithExpectedValue;
	
	/**
	 * Used mostly in mouse drag element to drop here.
	 */
	private String toPo;
	
	/**
	 * Typing speed in milliseconds per character.
	 */
	private int typeSpeedMspc = 0;
	
	/**
	 * Flag to indicate typing should start after clicking on the element.
	 */
	private boolean typeAfterClick = true;
	
	/**
	 * Indicate how long system should wait after operation is initiated.
	 */
	private int waitTimeInMsAfterOp = 0;
	
	public InputValue() {
		// do nothing
	}

	public InputValueAction getAction() {
		Assert.assertNotNull(action, "Input value action must be specified.");
		return action;
	}

	public void setAction(String action) {
		Assert.assertNotNull(action, "Input value action must be specified.");
		if(StringUtil.isEmptyAfterTrim(action)) {
			this.action = InputValueAction.TYPE;
		} else {
			this.action = InputValueAction.valueOf2(action.trim());
		}
	}

	public String getValueTypeAsStr() {
		return valueType.getType();
	}
	
	public InputValueType getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		if(StringUtil.isEmptyAfterTrim(valueType)) {
			if(value == null || !(value instanceof List || value.getClass().isArray())) {
				this.valueType = InputValueType.STRING;
			} else {
				this.valueType = InputValueType.STRING_LIST;
			}
		} else {
			this.valueType = InputValueType.valueOf2(valueType.trim());
		}
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		setValueType(null);
	}

	public String getLocationAsStr() {
		return location.name();
	}
	
	public NewTextLocation getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if(StringUtil.isEmptyAfterTrim(location)) {
			this.location = NewTextLocation.replace;
		} else {
			this.location = NewTextLocation.valueOf2(location.trim());
		}
		
	}

	public TestDataBuilder getAutoValueInputs() {
		return autoValueInputs;
	}

	public void setAutoValueInputs(TestDataBuilder autoValueInputs) {
		if(autoValueInputs != null) {
			this.autoValueInputs = autoValueInputs;
		}
	}
	
	public String getSelectingOptionMatchMechanismAsStr() {
		return selectingOptionMatchMechanism.name();
	}

	public TextMatchMechanism getSelectingOptionMatchMechanism() {
		return selectingOptionMatchMechanism;
	}

	public void setSelectingOptionMatchMechanism(String selectingOptionMatchMechanism) {
		if(StringUtil.isEmptyAfterTrim(selectingOptionMatchMechanism)) {
			this.selectingOptionMatchMechanism = TextMatchMechanism.exactMatchWithExpectedValue;
		} else {
			this.selectingOptionMatchMechanism = TextMatchMechanism.valueOf2(selectingOptionMatchMechanism);
		}
	}

	public String getToPo() {
		return toPo;
	}

	public void setToPo(String toPo) {
		this.toPo = toPo;
	}

	public int getTypeSpeedMspc() {
		return typeSpeedMspc;
	}

	public void setTypeSpeedMspc(Integer typeSpeedMspc) {
		this.typeSpeedMspc = (typeSpeedMspc == null || typeSpeedMspc.intValue() < 1) ? 0 : typeSpeedMspc.intValue();
	}

	public boolean getTypeAfterClick() {
		return typeAfterClick;
	}

	public void setTypeAfterClick(Boolean typeAfterClick) {
		this.typeAfterClick = (typeAfterClick == null) ? true : typeAfterClick.booleanValue();
	}

	public int getWaitTimeInMsAfterOp() {
		return waitTimeInMsAfterOp;
	}

	public void setWaitTimeInMsAfterOp(Integer waitTimeInMsAfterOp) {
		this.waitTimeInMsAfterOp = (waitTimeInMsAfterOp == null || waitTimeInMsAfterOp.intValue() < 1) ? 0 : waitTimeInMsAfterOp.intValue();
	}
}
