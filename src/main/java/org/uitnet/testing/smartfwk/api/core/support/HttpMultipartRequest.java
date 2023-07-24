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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpMultipartRequest {
	private List<MultipartData> parts;
	private Map<String, String> headers;

	public HttpMultipartRequest() {
		parts = new LinkedList<>();
		headers = new LinkedHashMap<>();
		headers.put("Content-Type", MediaType.MULTIPART_FORM_DATA);
		headers.put("Accept", MediaType.APPLICATION_JSON);
	}

	public List<MultipartData> getParts() {
		return parts;
	}

	public HttpMultipartRequest addPart(MultipartData part) {
		this.parts.add(part);
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

	public HttpMultipartRequest setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}
	
	public String getResponseContentType() {
		return headers.get("Accept");
	}

	public HttpMultipartRequest setResponseContentType(String responseContentType) {
		headers.put("Accept", responseContentType);
		return this;
	}

	public String getContentType() {
		return headers.get("Content-Type");
	}

	public HttpMultipartRequest setContentType(String contentType) {
		headers.put("Content-Type", contentType);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nheaders: " + headers)
		.append("\nparts: " + parts);
		
		return builder.toString();
	}
}
