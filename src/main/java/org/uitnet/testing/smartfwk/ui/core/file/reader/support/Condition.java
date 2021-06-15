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
package org.uitnet.testing.smartfwk.ui.core.file.reader.support;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class Condition {
	private String columnName;
	private Operator op;
	private String value;
	
	public Condition(String columnName, Operator op, String value) {
		this.columnName = columnName;
		this.op = op;
		this.value = value;
	}

	public String getColumnName() {
		return columnName;
	}

	public Condition setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public Operator getOp() {
		return op;
	}

	public Condition setOp(Operator op) {
		this.op = op;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Condition setValue(String value) {
		this.value = value;
		return this;
	}
}
