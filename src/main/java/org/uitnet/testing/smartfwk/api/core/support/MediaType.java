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

/**
 * 
 * @author Madhav Krishna
 *
 */
public interface MediaType {
	String APPLICATION_JSON = "application/json";
	String APPLICATION_XML = "application/xml";
	String APPLICATION_OCTET_STREAM = "application/octet-stream";
	String APPLICATION_PDF = "application/pdf";
	String IMAGE_GIF = "image/gif";
	String IMAGE_PNG = "image/png";
	String IMAGE_JPEG = "image/jpeg";
	String TEXT_HTML = "text/html";
	String TEXT_XML = "text/xml";
	String TEXT_PLAIN = "text/plain";
	String MULTIPART_MIXED = "multipart/mixed";
	String MULTIPART_FORM_DATA = "multipart/form-data";
	String ALL = "*/*";	
	String ANY = "*/*";	
}
