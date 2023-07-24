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
package org.uitnet.testing.smartfwk.ui.core.appdriver;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class AppIdGenerator {
	private static AppIdGenerator instance;
	private int value = 0;

	private AppIdGenerator() {
		// do nothing
	}

	public static AppIdGenerator getInstance() {
		if (instance == null) {
			synchronized (AppIdGenerator.class) {
				if (instance == null) {
					instance = new AppIdGenerator();
				}
			}
		}
		return instance;
	}

	public synchronized int nextValue() {
		value = value + 1;
		if (value == 99999999) {
			value = 1;
		}
		return value;
	}

	public int currentValue() {
		return value;
	}
}
