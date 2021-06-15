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

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class HttpMultipartRequest {
	private List<MultipartData> parts;
	private String responseContentType;
	private String contentType;

	public HttpMultipartRequest() {
		contentType = "multipart/form-data";
		parts = new LinkedList<>();
	}

	public List<MultipartData> getParts() {
		return parts;
	}

	public HttpMultipartRequest addPart(MultipartData part) {
		this.parts.add(part);
		return this;
	}

	public String getResponseContentType() {
		return responseContentType;
	}

	public HttpMultipartRequest setResponseContentType(String responseContentType) {
		this.responseContentType = responseContentType;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
