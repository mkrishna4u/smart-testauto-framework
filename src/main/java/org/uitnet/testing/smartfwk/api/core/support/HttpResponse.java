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

import org.uitnet.testing.smartfwk.validator.HttpResponseValidator;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpResponse {
	private int code;
	private String message;
	private Map<String, String> headers;
	private PayloadType payloadType;
	private String payload;
	private String filePath;
	private HttpResponseValidator validator;

	public HttpResponse() {
		payloadType = PayloadType.TEXT;
		headers = new LinkedHashMap<>();
		validator = new HttpResponseValidator(this);
	}
	
	public HttpResponseValidator getValidator() {
		return validator;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public String getHeader(String name) {
		if(headers == null) { return null; }
		for(Map.Entry<String, String> e : headers.entrySet()) {
			if(e.getKey().toUpperCase().equals(name.toUpperCase())) {
				return e.getValue();
			}
		};
		return null;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public PayloadType getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(PayloadType payloadType) {
		this.payloadType = payloadType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\ncode: " + code)
		.append("\nmessage: " + message)
		.append("\nheaders: " + headers)
		.append("\nfilePath: " + filePath)
		.append("\npayload: " + payload);
		
		return builder.toString();
	}
}
