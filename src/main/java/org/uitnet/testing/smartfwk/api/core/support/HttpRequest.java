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

import java.util.LinkedHashMap;
import java.util.Map;

import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpRequest {
	private String payload;
	private Map<String, String> headers;

	public HttpRequest() {
		headers = new LinkedHashMap<>();
		headers.put("Content-Type", MediaType.APPLICATION_JSON);
		headers.put("Accept", MediaType.APPLICATION_JSON);
	}

	public HttpRequest(Map<String, String> headers, String payload, String payloadType, String responseContentType) {
		this.headers = new LinkedHashMap<>();
		this.headers.putAll(headers);
		this.headers.put("Content-Type", payloadType);
		this.headers.put("Accept", responseContentType);
		this.payload = payload;
	}

	public String getPayload() {
		return payload;
	}

	public HttpRequest setPayload(String payload) {
		this.payload = payload;
		return this;
	}

	public String getPayloadType() {
		return getHeader("Content-Type");
	}

	public HttpRequest setPayloadType(String payloadType) {
		if(StringUtil.isEmptyAfterTrim(payloadType)) {
			headers.remove("Content-Type");
		} else {
			headers.put("Content-Type", payloadType);
		}
		
		return this;
	}
	
	public String getHeader(String name) {
		for(Map.Entry<String, String> e : headers.entrySet()) {
			if(e.getKey().toUpperCase().equals(name.toUpperCase())) {
				return e.getValue();
			}
		};
		return null;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public HttpRequest setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public String getResponseContentType() {
		return headers.get("Accept");
	}

	public HttpRequest setResponseContentType(String responseContentType) {
		if(StringUtil.isEmptyAfterTrim(responseContentType)) {
			headers.remove("Accept");
		} else {
			headers.put("Accept", responseContentType);
		}
		
		return this;
	}
}
