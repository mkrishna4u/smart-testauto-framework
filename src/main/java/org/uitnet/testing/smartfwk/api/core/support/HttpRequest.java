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

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpRequest {
	private String payload;
	private String payloadType;
	private Map<String, String> headers;
	private String responseContentType;

	public HttpRequest() {
		headers = new LinkedHashMap<>();
		headers.put("Content-Type", MediaType.APPLICATION_JSON);
		headers.put("Accept", MediaType.APPLICATION_JSON);
		payloadType = MediaType.APPLICATION_JSON;
	}

	public HttpRequest(Map<String, String> headers, String payload, String payloadType, String responseContentType) {
		headers = new LinkedHashMap<>();
		headers.putAll(headers);
		headers.put("Content-Type", MediaType.APPLICATION_JSON);
		headers.put("Accept", MediaType.APPLICATION_JSON);
		this.payload = payload;
		this.payloadType = payloadType;
		this.responseContentType = responseContentType;
	}

	public String getPayload() {
		return payload;
	}

	public HttpRequest setPayload(String payload) {
		this.payload = payload;
		return this;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public HttpRequest setPayloadType(String payloadType) {
		this.payloadType = payloadType;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public HttpRequest setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	public HttpRequest setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
		return this;
	}
}
