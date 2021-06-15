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
package org.uitnet.testing.smartfwk.ui.core.objects.webpage;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class WebPage extends UIObject {
	private UIObject title;
	private WebPagePath launchPath;
	private WebPageRecognitionItems pageRecognitionItems;
	private WebPageStatus status;

	/**
	 * 
	 * @param webPageTitle
	 *            Page title
	 * @param launchPath
	 *            The launchPath is a path to locate the web page by performing
	 *            certain operations.
	 * @param pageRecognitionItems
	 *            Parameters used to recognize the web page once after the page
	 *            is being opened by performing certain operations (that are
	 *            associated with launchPath).
	 */
	public WebPage(UIObject webPageTitle, WebPagePath launchPath,
			WebPageRecognitionItems pageRecognitionItems) {
		super(LocatorType.WEB_PAGE, UIObjectType.webPage, null);
		this.title = webPageTitle;
		this.launchPath = launchPath;
		this.pageRecognitionItems = pageRecognitionItems;
		status = WebPageStatus.Closed;
	}

	public UIObject getTitle() {
		return title;
	}

	public WebPagePath getLaunchPath() {
		return launchPath;
	}

	public WebPageRecognitionItems getPageRecognitionItems() {
		return pageRecognitionItems;
	}

	@Override
	public WebPageValidator getValidator(WebBrowser browser, Region region) {
		return new WebPageValidator(browser, this);
	}

	public WebPageStatus getStatus() {
		return status;
	}

	public void setStatus(WebPageStatus status) {
		this.status = status;
	}

	public String getReadablePath() {
		String readablePath = "";
		if (launchPath == null || launchPath.getPath() == null) {
			return readablePath;
		}

		for (PathItem pathItem : launchPath.getPath()) {
			if ("".equals(readablePath)) {
				readablePath = pathItem.getItem().getDisplayName() + "("
						+ pathItem.getEvent().getName() + ")";
			} else {
				readablePath = readablePath + " -> "
						+ pathItem.getItem().getDisplayName() + "("
						+ pathItem.getEvent().getName() + ")";
			}
		}
		return readablePath;
	}

	@Override
	public WebPage clone() {		
		return null;
	}

	@Override
	public UIObject updateLocatorParameterWithValue(String paramName, String value) {
		
		return this;
	}
}