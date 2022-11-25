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

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class StringArgumentValidator implements CommandArgumentValidator {
	private int minLength;
	private int maxLength;
	private String allowedCharsRegEx;
	
	public StringArgumentValidator(int minLength, int maxLength, String allowedCharsRegEx) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.allowedCharsRegEx = allowedCharsRegEx;
	}
	
	@Override
	public void validate(String option, CommandArgument arg, String argValue) {
		if(minLength > 0) {
			if(StringUtil.isEmptyAfterTrim(argValue)) {
				Assert.fail("Argument '" + arg.getName() + "' is mandatory for option '" + option + "'. Please specify value.");
			}
		}
		
		if(maxLength > 0) {
			if(StringUtil.isEmptyAfterTrim(argValue) || argValue.length() > maxLength) {
				Assert.fail("Argument '" + arg.getName() + "' allow maximum of '" + maxLength + "' characters for option '" + option + "'.");
			}
		}
		
		if(allowedCharsRegEx != null && allowedCharsRegEx.length() > 0) {
			if(argValue != null) {
				if(!argValue.matches(allowedCharsRegEx)) {
					Assert.fail("Argument '" + arg.getName() + "' value '" + argValue + "' does not match with '" + allowedCharsRegEx + "' allowed characters for option '" + option + "'.");
				}
			}
		}
	}
}
