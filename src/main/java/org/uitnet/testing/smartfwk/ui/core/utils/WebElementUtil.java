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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.util.Locale;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class WebElementUtil {

	private WebElementUtil() {
		// Do nothing
	}

	public static boolean isElementDisabled(SmartAppDriver appDriver, DOMObject domObject) {
		boolean disabled = false;
		boolean isReadonlyCalled = false;
		 try {
			 disabled = isElementDisabledButNotReadonly(appDriver, domObject);
			 if(!disabled) {
				 isReadonlyCalled = true;
				 disabled = isElementReadonly(appDriver, domObject);
			 }
		 } catch(Exception | Error e) {
			 if(!isReadonlyCalled) {
				 disabled = isElementReadonly(appDriver, domObject);
			 } else {
				 throw e;
			 }
		 }
		 return disabled;
	}
	
	public static boolean isElementDisabledButNotReadonly(SmartAppDriver appDriver, DOMObject domObject) {
		boolean disabled = false;

		WebElement webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
				domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));

		if (appDriver.getAppType() == ApplicationType.native_app) {
			try {
				if (webElem != null && ((webElem.getAttribute("disabled") != null) || !webElem.isEnabled())) {
					disabled = true;
				}
			} catch (Exception | Error ex) {
				String checkElemEnabledAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
						.get(SmartConstants.WEBATTRMAPKEY_CHECK_ELEMENT_ENABLED_ATTR);
				String attrValue = null;
				if (checkElemEnabledAttr != null && !"".equals(checkElemEnabledAttr)) {
					attrValue = webElem.getAttribute(checkElemEnabledAttr);
					if (attrValue != null && !"true".equals(attrValue.toLowerCase(Locale.ENGLISH))) {
						disabled = true;
					}
				} else {
					String checkElemDisabledAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
							.get(SmartConstants.WEBATTRMAPKEY_CHECK_ELEMENT_DISABLED_ATTR);
					if (checkElemDisabledAttr != null && "".equals(checkElemDisabledAttr)) {
						attrValue = webElem.getAttribute(checkElemDisabledAttr);
						if (attrValue != null && "true".equals(attrValue.toLowerCase(Locale.ENGLISH))) {
							disabled = true;
						}
					} else {
						Assert.fail("Please specify the value for property '"
								+ SmartConstants.WEBATTRMAPKEY_CHECK_ELEMENT_DISABLED_ATTR + "' or property '"
								+ SmartConstants.WEBATTRMAPKEY_CHECK_ELEMENT_ENABLED_ATTR
								+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
					}
				}
			}
		} else {
			if (webElem != null && ((webElem.getAttribute("disabled") != null) || !webElem.isEnabled())) {
				disabled = true;
			}
		}

		return disabled;
	}

	public static boolean isElementReadonly(SmartAppDriver appDriver, DOMObject domObject) {
		boolean readonly = false;

		WebElement webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
				domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));

		if (appDriver.getAppType() == ApplicationType.native_app) {
			String checkElemReadonlyAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
					.get(SmartConstants.WEBATTRMAPKEY_CHECK_TEXTBOX_STATE_AS_READONLY_ATTR);
			String attrValue = null;
			if (checkElemReadonlyAttr != null && !"".equals(checkElemReadonlyAttr)) {
				attrValue = webElem.getAttribute(checkElemReadonlyAttr);
				if (attrValue != null && !"true".equals(attrValue.toLowerCase(Locale.ENGLISH))) {
					readonly = true;
				}
			} else {
				Assert.fail("Please specify the value for property '"
						+ SmartConstants.WEBATTRMAPKEY_CHECK_TEXTBOX_STATE_AS_READONLY_ATTR
						+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
			}
		} else {

			if (webElem != null && webElem.getAttribute("readonly") != null) {
				readonly = true;
			}
		}

		return readonly;
	}

	public static boolean isElementVisible(SmartAppDriver appDriver, DOMObject domObject) {
		boolean visible = false;

		WebElement webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
				domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));

		visible = isElementVisible(appDriver, webElem);

		return visible;
	}
	
	public static boolean isElementVisible(SmartAppDriver appDriver, WebElement webElem) {
		boolean visible = false;

		if (appDriver.getAppType() == ApplicationType.native_app) {
			if (webElem != null) {
				visible = true;
			}
		} else {

			if (webElem != null && !("hidden".equals(LocatorUtil.getCssValue(webElem, "visibility")) 
					|| "none".equals(LocatorUtil.getCssValue(webElem, "display")))
					|| webElem.getAttribute("hidden") != null) {
				visible = true;
			}
		}

		return visible;
	}

	public static boolean isElementSelected(SmartAppDriver appDriver, DOMObject domObject) {
		boolean selected = false;

		WebElement webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
				domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));

		if (appDriver.getAppType() == ApplicationType.native_app) {
			try {
				if (webElem != null && webElem.isSelected()) {
					selected = true;
				}
			} catch (Exception | Error ex) {
				String checkElemOpSelectedAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
						.get(SmartConstants.WEBATTRMAPKEY_CHECK_OPTION_SELECTED_ATTR);
				String attrValue = null;
				if (checkElemOpSelectedAttr != null && !"".equals(checkElemOpSelectedAttr)) {
					attrValue = webElem.getAttribute(checkElemOpSelectedAttr);
					if (attrValue != null && !"true".equals(attrValue.toLowerCase(Locale.ENGLISH))) {
						selected = true;
					}
				} else {
					Assert.fail("Please specify the value for property '"
							+ SmartConstants.WEBATTRMAPKEY_CHECK_OPTION_SELECTED_ATTR
							+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
				}
			}
		} else {
			if (webElem != null && webElem.isSelected()) {
				selected = true;
			}
		}

		return selected;
	}
	
	public static String getElementText(SmartAppDriver appDriver, DOMObject domObject, int maxIterationsToLocateElements) {
		String text = "";
		WebElement webElem = null;
		
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				
				if (appDriver.getAppType() == ApplicationType.native_app) {
					String getElemTextAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
							.get(SmartConstants.WEBATTRMAPKEY_GET_ELEMENT_TEXT_ATTR);
					if (getElemTextAttr != null && !"".equals(getElemTextAttr)) {
						text = webElem.getAttribute(getElemTextAttr);
					} else {
						Assert.fail("Please specify the value for property '"
								+ SmartConstants.WEBATTRMAPKEY_GET_ELEMENT_TEXT_ATTR
								+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
					}
				} else {
					if (webElem != null) {
						text = webElem.getText();
					}
				}
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					throw th;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return text;
	}
	
	public static String getElementText(SmartAppDriver appDriver, WebElement webElem) {
		String text = "";
		
		if(webElem == null) { return text; }
		
		if (appDriver.getAppType() == ApplicationType.native_app) {
			String getElemTextAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
					.get(SmartConstants.WEBATTRMAPKEY_GET_ELEMENT_TEXT_ATTR);
			if (getElemTextAttr != null && !"".equals(getElemTextAttr)) {
				text = webElem.getAttribute(getElemTextAttr);
			} else {
				Assert.fail("Please specify the value for property '"
						+ SmartConstants.WEBATTRMAPKEY_GET_ELEMENT_TEXT_ATTR
						+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
			}
		} else {
			if (webElem != null) {
				text = webElem.getText();
			}
		}
		
		return text;
	}
	
	public static String getInputTextValue(SmartAppDriver appDriver, DOMObject domObject, int maxIterationsToLocateElements) {
		String text = "";
		WebElement webElem = null;
		
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				
				text = getInputTextValue(appDriver, webElem);
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					throw th;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return text;
	}
	
	public static String getInputTextValue(SmartAppDriver appDriver, WebElement webElem) {
		String text = "";
		
		if (appDriver.getAppType() == ApplicationType.native_app) {
			String getElemTextAttr = appDriver.getAppConfig().getAppDriverConfig().getWebAttrMap()
					.get(SmartConstants.WEBATTRMAPKEY_GET_INPUT_VALUE_ATTR);
			if (getElemTextAttr != null && !"".equals(getElemTextAttr)) {
				text = webElem.getAttribute(getElemTextAttr);
			} else {
				Assert.fail("Please specify the value for property '"
						+ SmartConstants.WEBATTRMAPKEY_GET_INPUT_VALUE_ATTR
						+ "' in AppDriver.yaml file for Application '" + appDriver.getAppName() + "'.");
			}
		} else {
			if (webElem != null) {
				text = webElem.getAttribute("value");
			}
		}
		
		return text;
	}
	
	/* TODO: 
	public static Map<String, String> findColorContrastRatioForElemAndItsChildren(SmartAppDriver appDriver, WebElement webElem) {
		String tagName = webElem.getTagName();
		String attrId = webElem.getAttribute("id");
		String attrClass = webElem.getAttribute("class");
		String attrStyle = webElem.getAttribute("style");
		String textVal = getElementText(appDriver, webElem);
		String fgColor = webElem.getCssValue("color");
		String bgColor = webElem.getCssValue("background-color");
		String fontWeight = webElem.getCssValue("font-weight");
		double fontSizeInREM = FontUtil.computeFontSizeInREM(webElem.getCssValue("font-size"));
		boolean visible = isElementVisible(appDriver, webElem);
		
		List<WebElement> childs = webElem.findElements(By.xpath("./child::*"));
		WebElement child;
		
		List<WebElement> elems = new ArrayList<WebElement>();
		if(childs != null) { elems.addAll(0, childs); }
		while(elems != null && elems.size() > 0) {
			child = elems.get(0);
			
			
			// add children
			childs = child.findElements(By.xpath("./child::*"));
			if(childs != null) { elems.addAll(0, childs); }
		}
	} */
}
