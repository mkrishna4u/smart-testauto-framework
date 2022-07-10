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
package org.uitnet.testing.smartfwk.api.core.support;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class FileSequenceNumberGenerator {
	private static FileSequenceNumberGenerator instance;
	private int currentNum;

	private FileSequenceNumberGenerator() {
		currentNum = 0;
	}

	public static FileSequenceNumberGenerator getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (FileSequenceNumberGenerator.class) {
			if (instance == null) {
				instance = new FileSequenceNumberGenerator();
			}
		}

		return instance;
	}

	public synchronized int next() {
		if (currentNum == 999999) {
			currentNum = 0;
		}

		return ++currentNum;
	}
}
