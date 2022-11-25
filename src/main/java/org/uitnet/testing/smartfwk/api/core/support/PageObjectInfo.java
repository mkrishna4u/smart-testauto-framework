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

import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageObjectInfo {
	private PageObject pageObject;
	
	public PageObjectInfo(PageObject pageObject) {
		this.pageObject = pageObject;
	}

	public String getPoClassName() {
		int dotIdx = pageObject.getName().lastIndexOf(".");
		return DefaultInfo.DEFAULT_PAGE_OBJECTS_PACKAGE + "." + pageObject.getName().substring(0, dotIdx);
	}
	
	public String getPoClassFieldName() {
		int dotIdx = pageObject.getName().lastIndexOf(".");
		return pageObject.getName().substring(dotIdx+1, pageObject.getName().length());
	}

	public PageObject getPageObject() {
		return pageObject;
	}

	public int getMaxIterationsToLocateElements() {
		Integer maxTimeToWaitInSeconds = 4;
		try {
			maxTimeToWaitInSeconds = pageObject.getMaxTimeToWaitInSeconds();
		}catch(Exception ex) {}
		
		if(maxTimeToWaitInSeconds == null) {
			maxTimeToWaitInSeconds = 4;
		}
		
		if(maxTimeToWaitInSeconds < 0) {
			maxTimeToWaitInSeconds = 0;
		}
		
		return Double.valueOf(Math.floor(maxTimeToWaitInSeconds / 2)).intValue();
	}
}
