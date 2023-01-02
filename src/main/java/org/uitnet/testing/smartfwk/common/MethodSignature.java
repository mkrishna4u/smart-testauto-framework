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
package org.uitnet.testing.smartfwk.common;

import java.util.LinkedList;
import java.util.List;

import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MethodSignature {
	private String name;
	private List<String> argsType;
	private List<Object> argsValue;
	
	public MethodSignature() {
		argsType = new LinkedList<>();
		argsValue = new LinkedList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getArgsType() {
		return argsType.toArray(new String[argsType.size()]);
	}

	public void setArgsType(String[] argsType) {
		if (argsType != null) {
			String clazz = null;
			for (String arg : argsType) {
				clazz = ObjectUtil.convertStringToJavaClassType(arg);
				this.argsType.add(clazz);
			}
		}
	}

	public Object[] getArgsValue() {
		return argsValue.toArray(new Object[argsValue.size()]);
	}

	public void setArgsValue(List<Object> argsValue) {
		this.argsValue = argsValue;
	}
}
