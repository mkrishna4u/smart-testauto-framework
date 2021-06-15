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

import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RowFilter {
	List<Object> statements;
	
	public RowFilter() {
		statements = new LinkedList<>();
	}
	
	public RowFilter condition(Condition condition) {
		Assert.assertNotNull(condition, "Row Filter Condition can't be empty.");
		statements.add(condition);
		return this;
	}
	
	public RowFilter and() {
		statements.add(Join.and);
		return this;
	}
	
	public RowFilter or() {
		statements.add(Join.or);
		return this;
	}
	
	public List<Object> getFilter() {
		return  statements;
	}
}
