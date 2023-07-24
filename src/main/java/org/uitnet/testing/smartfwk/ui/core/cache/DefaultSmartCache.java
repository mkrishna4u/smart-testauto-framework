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
package org.uitnet.testing.smartfwk.ui.core.cache;

/**
 * Default implementation of SmartCache using Singleton class.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultSmartCache extends SmartCache {
	private static DefaultSmartCache instance;
	
	private DefaultSmartCache() {
		super();
	}
	
	public static SmartCache getInstance() {
		if(instance != null) {
			return instance;
		}
		
		synchronized(DefaultSmartCache.class) {
			if(instance == null) {
				instance = new DefaultSmartCache();
			}
		}
		
		return instance;
	}
}
