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
package org.uitnet.testing.smartfwk.core.validator;

import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ExpectedInfo {
	private Object ev;
	private ParamValueType valueType = ParamValueType.STRING;
	private TextMatchMechanism textMatchMechanism = TextMatchMechanism.exactMatchWithExpectedValue;
	private Integer n = 1;
	private InOrder inOrder = InOrder.NO;
	private IgnoreCase ignoreCase = IgnoreCase.NO;

	public ExpectedInfo() {

	}

	public Object getEv() {
		return ev;
	}

	public void setEv(Object ev) {
		this.ev = ev;
	}

	public String getValueTypeAsStr() {
		return valueType.getType();
	}

	public ParamValueType getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = StringUtil.isEmptyAfterTrim(valueType) ? ParamValueType.STRING
				: ParamValueType.valueOf2(valueType.trim());
	}

	public String getTextMatchMechanismAsStr() {
		return textMatchMechanism.getValue();
	}

	public TextMatchMechanism getTextMatchMechanism() {
		return textMatchMechanism;
	}

	public void setTextMatchMechanism(String textMatchMechanism) {
		this.textMatchMechanism = StringUtil.isEmptyAfterTrim(textMatchMechanism)
				? TextMatchMechanism.exactMatchWithExpectedValue
				: TextMatchMechanism.valueOf2(textMatchMechanism.trim());
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = (n == null ? 1 : n);
	}

	public String getInOrderAsStr() {
		return inOrder.getValue();
	}

	public InOrder getInOrder() {
		return inOrder;
	}

	public void setInOrder(String inOrder) {
		this.inOrder = StringUtil.isEmptyAfterTrim(inOrder) ? InOrder.NO : InOrder.valueOf2(inOrder.trim());
	}

	public IgnoreCase getIgnoreCase() {
		return ignoreCase;
	}

	public String getIgnoreCaseAsStr() {
		return ignoreCase.getValue();
	}

	public void setIgnoreCase(String ignoreCase) {
		this.ignoreCase = StringUtil.isEmptyAfterTrim(ignoreCase) ? IgnoreCase.NO
				: IgnoreCase.valueOf2(ignoreCase.trim());
	}

}
