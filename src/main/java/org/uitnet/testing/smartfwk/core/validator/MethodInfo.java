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
package org.uitnet.testing.smartfwk.core.validator;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the calling method information.
 * 
 * @author Madhav Krishna
 *
 */
public class MethodInfo {
	private String className;
	private String methodName;
	private List<Class<?>> argsType;
	private List<Object> argsValue;
	private Boolean isStatic = false;
	
	public MethodInfo() {
		argsType = new LinkedList<>();
		argsValue = new LinkedList<>();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Class<?>> getArgsType() {
		return argsType;
	}

	public void setArgsType(List<Class<?>> argsType) {
		this.argsType = argsType == null ? new LinkedList<>() : argsType;
	}

	public List<Object> getArgsValue() {
		return argsValue;
	}

	public void setArgsValue(List<Object> argsValue) {
		this.argsValue = argsValue == null ? new LinkedList<>() : argsValue;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic == null ? false : isStatic;
	}
}
