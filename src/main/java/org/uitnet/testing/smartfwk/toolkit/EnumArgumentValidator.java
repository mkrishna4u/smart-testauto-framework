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

import java.lang.reflect.Method;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class EnumArgumentValidator<ENUM> implements CommandArgumentValidator {
	private boolean mandatory;
	private Class<ENUM> enumClass;
	private String enumMethodToValidateValidEnum = "valueOf2";
	
	
	public EnumArgumentValidator(boolean mandatory, Class<ENUM> enumClass) {
		this.mandatory = mandatory;
		this.enumClass = enumClass;
	}
	
	@Override
	public void validate(String option, CommandArgument arg, String argValue) {
		if(mandatory == true) {
			if(StringUtil.isEmptyAfterTrim(argValue)) {
				Assert.fail("Argument '" + arg.getName() + "' is mandatory for option '" + option + "'. Please specify value.");
			}
		}
		
		if(enumClass == null) {
			Assert.fail("Must specify the enum class.");
		}
		
		try {
			Method m = ObjectUtil.findClassMethod(enumClass, enumMethodToValidateValidEnum, new String[] {String.class.getTypeName()});
			ObjectUtil.invokeMethod(enumClass, m, new Object[] {argValue});
		} catch(Exception | Error ex) {
			try {
				Method m = ObjectUtil.findClassMethod(enumClass, "values", null);
				Object ret = ObjectUtil.invokeMethod(enumClass, m, null);
				Assert.fail("Invalid argument value. Expected one of from: " + ret, ex);
			} catch(Exception | Error e) {
				// do nothing
			}
		}
	}
}
