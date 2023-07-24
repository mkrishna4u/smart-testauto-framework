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

import org.testng.Assert;

/**
 * This operator is used to match with expected value. Expected value format could be like given below:
 * Format-1: As a JSON. based on the operator type different parameter's value can be specified.
 * {ev: ["ev1", "ev2"], valueType="", textMatchMechanism: "", n: 5, inOrder: "yes/no", regEx: ""}
 * 
 * Format-2: not as a JSON. In this case you can not specify multiple values and the other parameters for data matching purpose. 
 * It will always work based on the exact text match mechanism. i.e.
 * 
 *   ev
 * 
 * @author Madhav Krishna
 *
 */
public enum ValueMatchOperator {
	EQUAL_TO("="),
	NOT_EQUAL_TO("!="),
	GREATER_THAN(">"),
	GREATER_THAN_EQUAL_TO(">="),
	LESS_THAN("<"),
	LESS_THAN_EQUAL_TO("<="),
	IN("in"),
	NOT_IN("!in"),
	CONTAINS("contains"),
	NOT_CONTAINS("!contains"),//N
	CONTAINS_ATLEAST_N("contains-atleast-n"), 
	CONTAINS_ATMOST_N("contains-atmost-n"), 
	STARTS_WITH("starts-with"),
	NOT_STARTS_WITH("!starts-with"), // N
	ENDS_WITH("ends-with"),
	NOT_ENDS_WITH("!ends-with"), //N
	PRESENT("present"),
	NOT_PRESENT("!present")
	;

	private String op;

	private ValueMatchOperator(String op) {
		this.op = op;
	}

	public String getOperator() {
		return op;
	}

	public static ValueMatchOperator valueOf2(String type) {
		for (ValueMatchOperator t : values()) {
			if (t.getOperator().equalsIgnoreCase(type) || t.name().equalsIgnoreCase(type)) {
				return t;
			}
		}
		Assert.fail("Value match operator '" + type + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return op;
	}
}

