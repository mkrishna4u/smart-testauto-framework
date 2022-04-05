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

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageObjectInfo {
	private String poClassName;
	private String poObjectName;
	private DocumentContext jsonParams;
	
	public PageObjectInfo(String poClassName, String poObjectName, DocumentContext jsonParams) {
		this.poClassName = poClassName;
		this.poObjectName = poObjectName;
		this.jsonParams = jsonParams;
	}

	public String getPoClassName() {
		return poClassName;
	}

	public void setPoClassName(String poClassName) {
		this.poClassName = poClassName;
	}

	public String getPoObjectName() {
		return poObjectName;
	}

	public void setPoObjectName(String poObjectName) {
		this.poObjectName = poObjectName;
	}

	public DocumentContext getJsonParams() {
		return jsonParams;
	}

	public void setJsonParams(DocumentContext jsonParams) {
		this.jsonParams = jsonParams;
	}
	
	public int getMaxIterationsToLocateElements() {
		Integer maxTimeToWaitInSeconds = 4;
		if(jsonParams != null) {
			try {
				maxTimeToWaitInSeconds = jsonParams.read("$.maxTimeToWaitInSeconds");
			}catch(Exception ex) {}
			
			if(maxTimeToWaitInSeconds == null) {
				maxTimeToWaitInSeconds = 4;
			}
		}
		
		if(maxTimeToWaitInSeconds < 0) {
			maxTimeToWaitInSeconds = 0;
		}
		
		return Double.valueOf(Math.floor(maxTimeToWaitInSeconds / 2)).intValue();
	}
}
