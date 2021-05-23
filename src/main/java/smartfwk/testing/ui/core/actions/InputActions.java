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
package smartfwk.testing.ui.core.actions;

import java.util.ArrayList;
import java.util.List;

import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObject;
import smartfwk.testing.ui.core.objects.webpage.WebPage;
import smartfwk.testing.ui.standard.imgobj.WebPageTitleSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class InputActions {
	private String name;
	private List<InputAction> actions;
	private WebPage webPage;
	private List<UIObject> pageRecognitionItems;

	public InputActions(String name, WebPage webPage) {
		this.name = name;
		actions = new ArrayList<InputAction>(10);
		this.webPage = webPage;
		pageRecognitionItems = new ArrayList<UIObject>(2);
	}

	public String getName() {
		return name;
	}

	public InputActions append(InputAction action) {
		actions.add(action);
		return this;
	}

	public List<InputAction> getActions() {
		return actions;
	}

	public InputActionHandler getActionHandler(WebBrowser browser) {
		return new InputActionHandler(browser, this);
	}

	public WebPage getWebPage() {
		return webPage;
	}

	public void setWebPage(WebPage webPage) {
		this.webPage = webPage;
	}

	public InputActions appendPageRecognitionItem(UIObject item) {
		pageRecognitionItems.add(item);

		return this;
	}

	public List<UIObject> getPageRecognitionItems() {
		return pageRecognitionItems;
	}

	public String getReadablePath() {
		String readablePath = "";
		for (InputAction action : actions) {
			if ("".equals(readablePath)) {
				readablePath = action.getItem().getDisplayName() + "("
						+ action.getEvent().getName() + ")";
			} else {
				readablePath = readablePath + " -> "
						+ action.getItem().getDisplayName() + "("
						+ action.getEvent().getName() + ")";
			}
		}
		return readablePath;
	}

	/**
	 * This method returns the expected page title only. Other information will
	 * not be present. Title name will contain the whole path of the expected webpage.
	 * 
	 * @return
	 */
	public WebPage getExpectedWebPage() {
		return new WebPage(new WebPageTitleSI(this.webPage.getTitle()
				.getDisplayName() + ":> " + getReadablePath(), null), null,
				null);
	}
}
