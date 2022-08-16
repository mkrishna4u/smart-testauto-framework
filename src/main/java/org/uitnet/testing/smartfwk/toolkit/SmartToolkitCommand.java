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
package org.uitnet.testing.smartfwk.toolkit;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartToolkitCommand {
	private String option;
	private CommandArgument[] args;
	private String description;
	
	public SmartToolkitCommand(String option, CommandArgument[] args, String description) {
		this.option = option;
		this.args = args;
		this.description = description;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public CommandArgument[] getArgs() {
		return args;
	}

	public void setArgs(CommandArgument[] args) {
		this.args = args;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		String str = option;
		if(args != null) {
			for(CommandArgument arg : args) {
				str = str + " " + arg.getName();
			}
		}
		
		str = str + "\n    Description: " + description;
		
		return str;
	}
}
