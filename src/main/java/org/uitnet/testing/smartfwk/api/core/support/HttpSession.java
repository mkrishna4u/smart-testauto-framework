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
package org.uitnet.testing.smartfwk.api.core.support;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpSession {
	private Map<String, String> params;
	private Map<String, String> cookies;

	public HttpSession() {
		params = new LinkedHashMap<>();
		cookies = new LinkedHashMap<>();
	}

	public void addParam(String key, String value) {
		this.params.put(key, value);
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public void addCookie(String key, String value) {
		this.cookies.put(key, value);
	}

	public Map<String, String> getCookies() {
		return this.cookies;
	}

	@Override
	public String toString() {
		return "Params: " + params + "\nCookies: " + cookies;
	}
}
