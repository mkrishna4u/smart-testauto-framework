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
package org.uitnet.testing.smartfwk.ui.core.commons;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ElementWithLocator {
	private String name;
	private Locator locator;
	
	public ElementWithLocator(String name, Locator locator) {
		this.name = name;
		this.locator = locator;				
	}
	
	public ElementWithLocator(String name) {
		this.name = name;	
	}

	public String getName() {
		return name;
	}

	public ElementWithLocator setName(String name) {
		this.name = name;
		return this;
	}

	public Locator getLocator() {
		return locator;
	}

	public ElementWithLocator setLocator(Locator locator) {
		this.locator = locator;
		return this;
	}
}
