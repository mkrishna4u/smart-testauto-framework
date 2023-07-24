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
package org.uitnet.testing.smartfwk.toolkit;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class CommandArgument {
	private String name;
	private CommandArgumentValidator validator;
	
	public CommandArgument(String name, CommandArgumentValidator validator) {
		this.name = name;
		this.validator = validator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommandArgumentValidator getValidator() {
		return validator;
	}

	public void setValidator(CommandArgumentValidator validator) {
		this.validator = validator;
	}
}
