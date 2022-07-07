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

/**
 * 
 * @author Madhav Krishna
 *
 * @param <T>
 */
public class MethodArg<T> {
	private String name;
	private MethodArgMode mode;
	private Class<T> dataType;
	private T value;

	public MethodArg() {

	}

	public MethodArg(String name, MethodArgMode mode, Class<T> dataType, T value) {
		this.name = name;
		this.mode = mode;
		this.dataType = dataType;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public MethodArg<T> setName(String name) {
		this.name = name;
		return this;
	}

	public MethodArgMode getMode() {
		return mode;
	}

	public MethodArg<T> setMode(MethodArgMode mode) {
		this.mode = mode;
		return this;
	}

	public Class<T> getDataType() {
		return dataType;
	}

	public MethodArg<T> setDataType(Class<T> dataType) {
		this.dataType = dataType;
		return this;
	}

	public T getValue() {
		return value;
	}

	public MethodArg<T> setValue(T value) {
		this.value = value;
		return this;
	}

}
