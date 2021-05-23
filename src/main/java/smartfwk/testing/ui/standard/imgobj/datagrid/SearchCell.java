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
package smartfwk.testing.ui.standard.imgobj.datagrid;

import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SearchCell {
	protected HeaderColumnSI column;
	protected Object value;
	protected ValueType valueType;
	protected TextValidationMechanism textValueValidationMechanism;

	public SearchCell(HeaderColumnSI column, Object value,
			ValueType valueType,
			TextValidationMechanism textValueValidationMechanism) {
		this.column = column;
		this.value = value;
		this.valueType = valueType;
		this.textValueValidationMechanism = textValueValidationMechanism;
	}

	public HeaderColumnSI getColumn() {
		return column;
	}

	public void setColumn(HeaderColumnSI column) {
		this.column = column;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public TextValidationMechanism getTextValueValidationMechanism() {
		return textValueValidationMechanism;
	}

	public void setTextValueValidationMechanism(
			TextValidationMechanism textValueValidationMechanism) {
		this.textValueValidationMechanism = textValueValidationMechanism;
	}

	@Override
	public String toString() {
		return "{Column=" + column.getDisplayName() + ", Value=" + value
				+ ", ValueType=" + valueType.name()
				+ ", TextValueValidationMechanism="
				+ textValueValidationMechanism + "}";
	}
}
