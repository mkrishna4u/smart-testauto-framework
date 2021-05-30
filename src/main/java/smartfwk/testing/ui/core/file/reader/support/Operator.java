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
package smartfwk.testing.ui.core.file.reader.support;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum Operator {
	/** Equal to operator */
	eq("="), 
	
	/** Not equal to operator */
	ne("!="), 
	
	/** Greater than operator */
	gt(">"), 
	
	/** Greater than equal to operator */
	gte(">="), 
	
	/** Less than operator */
	lt("<"), 
	
	/** Less than equal to operator */
	lte("<="),
	
	/** Starts with value operator */
	startsWith("startsWith"),
	
	/** Ends with value operator */
	endsWith("endsWith"),
	
	/** Contains value operator */
	contains("contains");
	
	private String op;
	private Operator(String op) {
		this.op = op;
	}
	
	public String getOp() {
		return this.op;
	}
}
