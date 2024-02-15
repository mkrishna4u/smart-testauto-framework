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
public class MultipartFormRecord {
	public String partName;
	public String partType;
	public String contentType;
	public byte[] contents; // Or filePath

	public MultipartFormRecord() {
		
	}
	
	public MultipartFormRecord(String partName, String partType, String contentType, byte[] contents) {
		this.partName = partName;
		this.partType = partType;
		this.contentType = contentType;
		this.contents = contents;
	}
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\npartName: " + partName)
		.append("\npartType: " + partType)
		.append("\ncontentType: " + contentType)
		.append("\ncontents: " + String.valueOf(contents));
		
		return builder.toString();
	}
}
