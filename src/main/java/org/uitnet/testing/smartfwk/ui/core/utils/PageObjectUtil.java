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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObject;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.InputValue;
import org.uitnet.testing.smartfwk.core.validator.InputValueAction;
import org.uitnet.testing.smartfwk.core.validator.InputValueType;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.ui.core.commons.FieldValue;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.button.ButtonValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.checkbox.CheckBoxGroupValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.checkbox.CheckBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.combobox.ComboBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.file.InputFileValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.image.ImageValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.label.LabelValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.link.HyperlinkValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.listbox.ListBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.multi_state.MultiStateElementValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonGroupValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.textarea.TextAreaValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.textbox.TextBoxValidator;
import org.uitnet.testing.smartfwk.ui.standard.domobj.ButtonValidatorSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.HyperlinkValidatorSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.ImageValidatorSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.LabelValidatorSD;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.ButtonValidatorSI;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.HyperlinkValidatorSI;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.ImageValidatorSI;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.LabelValidatorSI;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageObjectUtil {

	private PageObjectUtil() {
		// do nothing
	}

	/**
	 * Page object should be specified using the format. When POs are in ./src/main/page_objects/ directory:
	 * "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 10, params: {p1: 'v1', p2: '${variable1}'} }" 
	 * 
	 * When POs are in sub directory of ./src/main/page_objects/ directory:
	 * "<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>
	 * 
	 * PO in JSON format:
	 * {name: "<PO-classname>.<field-name>", maxTimeToWaitInSeconds: 4, params: {p1: 'v1', p2: 'v2'} }
	 * 
	 * @param pageObject
	 * @return
	 */
	public static PageObjectInfo getPageObjectInfo(String pageObject) {
		pageObject = pageObject.trim();
		PageObjectInfo poInfo = null;
		if(pageObject.startsWith("{") && pageObject.endsWith("}")) {
			DocumentContext poAsJson = new JsonDocumentReader(pageObject, false).getDocumentContext();
			PageObject po = poAsJson.read("$", PageObject.class);
			poInfo = new PageObjectInfo(po);
		} else {
			PageObject po = new PageObject();
			po.setName(pageObject);
			po.setMaxTimeToWaitInSeconds(4);
			poInfo = new PageObjectInfo(po);
		}
		return poInfo;
	}
	
	public static PageObjectInfo getPageObjectInfo(String pageObject, SmartCucumberScenarioContext scenarioContext) {
		pageObject = pageObject.trim();
		PageObjectInfo poInfo = null;
		if(pageObject.startsWith("{") && pageObject.endsWith("}")) {
			DocumentContext poAsJson = new JsonDocumentReader(pageObject, false).getDocumentContext();
			PageObject po = poAsJson.read("$", PageObject.class);
			
			Map<String, String> params = po.getParams();
			if(po != null && params != null) {
				for(Map.Entry<String, String> e : params.entrySet()) {
					params.put(e.getKey(), scenarioContext.applyParamsValueOnText(e.getValue()));
				}
			}
			
			poInfo = new PageObjectInfo(po);
		} else {
			PageObject po = new PageObject();
			po.setName(pageObject);
			po.setMaxTimeToWaitInSeconds(4);
			poInfo = new PageObjectInfo(po);
		}
		return poInfo;
	}

	public static FieldValue getPageObject(PageObjectInfo poInfo) {
		Field f = null;
		Object value = null;
		try {
			Class<?> clazz = Class.forName(poInfo.getPoClassName());
			f = clazz.getField(poInfo.getPoClassFieldName());
			value = f.get(clazz);
		} catch (Exception ex) {
			Assert.fail("Failed to load the page object '" + poInfo.getPoClassName() + "." + poInfo.getPoClassFieldName()
					+ "'. Reason: " + ex.getLocalizedMessage(), ex);
		}
		return new FieldValue(f, value);
	}

	public static Object getPageObjectValidator(PageObjectInfo poInfo, SmartCucumberScenarioContext scenarioContext) {
		FieldValue fv = getPageObject(poInfo);
		Object validatorObj = null;
		try {
			applyParamsToLocator(fv, poInfo.getPageObject().getParams(), scenarioContext);
			Method method = fv.getField().getType().getMethod("getValidator", SmartCucumberScenarioContext.class,
					Region.class);
			validatorObj = method.invoke(fv.getValue(), scenarioContext, null);
		} catch (Exception ex) {
			Assert.fail("Failed to get validator for the page object '" + poInfo.getPoClassName() + "."
					+ poInfo.getPoClassFieldName() + "'. Reason: " + ex.getLocalizedMessage(), ex);
		}
		return validatorObj;
	}

	public static Object invokeValidatorMethod(String methodName, String[] methodArgTypes, Object[] methodArgValues,
			PageObjectInfo poInfo, SmartCucumberScenarioContext scenarioContext) {
		Object validatorObj = getPageObjectValidator(poInfo, scenarioContext);
		
		Class<?> clazz = validatorObj.getClass();
		Method m = null;
		if(methodArgTypes == null || methodArgTypes.length == 0) {
			m = ObjectUtil.findClassMethod(clazz, methodName, methodArgValues.length);
		} else {
			m = ObjectUtil.findClassMethod(clazz, methodName, methodArgTypes);
		}
		
		try {
			
			if(methodArgValues.length > 0) {
				return ObjectUtil.invokeMethod(validatorObj, m, methodArgValues);
			} else {
				return m.invoke(validatorObj);
			}
		} catch (Exception | Error ex) {
			Assert.fail("'" + methodName + "' operation is failed for page object '" + poInfo.getPoClassName() + "."
					+ poInfo.getPoClassFieldName() + "'. Reason: " + ex.getLocalizedMessage(), ex);
		}
		return null;
	}
	
	protected static void applyParamsToLocator(FieldValue fv, Map<String, String> locatorParams, SmartCucumberScenarioContext scenarioContext) {
		if(locatorParams != null && !locatorParams.isEmpty()) {
			try {
				for(Map.Entry<String, String> param: locatorParams.entrySet()) {
					Method method = fv.getField().getType().getMethod("updateLocatorParameterWithValue", String.class,
							String.class);
					Object newObj = method.invoke(fv.getValue(), param.getKey(), prepareParamValue(param.getValue(), scenarioContext));
					fv.setValue(newObj);
				}
			} catch(Exception | Error ex) {
				Assert.fail("Failed to update locator input parameters.", ex);
			}			
		}
		
	}
	
	/**
	 * paramValue may contain the variable like ${variableName} and this variable value should be extracted from scenarioContext.
	 * 
	 * @param paramValue
	 * @param scenarioContext
	 * @return
	 */
	public static String prepareParamValue(String paramValue, SmartCucumberScenarioContext scenarioContext) {
		if(paramValue != null) {
			paramValue = scenarioContext.applyParamsValueOnText(paramValue);
		}
		
		return paramValue;
	}
	
	@SuppressWarnings("unchecked")
	public static void validateFormFieldData(PageObjectInfo poInfo, String operator, ExpectedInfo expectedInfo, SmartCucumberScenarioContext scenarioContext) {
		Object poValidator = PageObjectUtil.getPageObjectValidator(poInfo, scenarioContext);
		
		if(poValidator instanceof TextBoxValidator) {
			TextBoxValidator validator = (TextBoxValidator) poValidator;
			String value = validator.getValue(poInfo.getMaxIterationsToLocateElements());
			ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
			ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, value, operator, expectedInfo);
		} else if(poValidator instanceof TextAreaValidator) {
			TextAreaValidator validator = (TextAreaValidator) poValidator;
			String value = validator.getTextValue(poInfo.getMaxIterationsToLocateElements());
			ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
			ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, value, operator, expectedInfo);
		} else if(poValidator instanceof CheckBoxValidator) {
			CheckBoxValidator validator = (CheckBoxValidator) poValidator;
			if(expectedInfo.getEv() != null && ("checked".equalsIgnoreCase("" + expectedInfo.getEv())
					|| "selected".equalsIgnoreCase("" + expectedInfo.getEv()))) {
				validator.validateCheckBoxChecked(poInfo.getMaxIterationsToLocateElements());
			} else {
				validator.validateCheckBoxUnchecked(poInfo.getMaxIterationsToLocateElements());
			}
		} else if(poValidator instanceof CheckBoxGroupValidator) {
			CheckBoxGroupValidator validator = (CheckBoxGroupValidator) poValidator;
			if(expectedInfo.getEv() != null) { // EV should be array/list
				ItemList<String> checkedItems = new ItemList<String>((List<String>)expectedInfo.getEv());					
				validator.validateCheckedCheckBoxes(checkedItems, poInfo.getMaxIterationsToLocateElements());
			} else {
				validator.validateNoCheckBoxesAreChecked(poInfo.getMaxIterationsToLocateElements());
			}
		} else if(poValidator instanceof RadioButtonValidator) {
			RadioButtonValidator validator = (RadioButtonValidator) poValidator;
			if(expectedInfo.getEv() != null && ("selected".equalsIgnoreCase("" + expectedInfo.getEv())
					|| "checked".equalsIgnoreCase("" + expectedInfo.getEv()))) {
				validator.validateSelected(poInfo.getMaxIterationsToLocateElements());
			} else {
				validator.validateNotSelected(poInfo.getMaxIterationsToLocateElements());
			}
		} else if(poValidator instanceof RadioButtonGroupValidator) {
			RadioButtonGroupValidator validator = (RadioButtonGroupValidator) poValidator;
			if(expectedInfo.getEv() != null) {
				validator.validateSelectedOption("" + expectedInfo.getEv(), "" +  expectedInfo.getEv(), poInfo.getMaxIterationsToLocateElements());
			} else {
				validator.validateNoOptionsAreSelected(poInfo.getMaxIterationsToLocateElements());
			}
		} else if(poValidator instanceof MultiStateElementValidator) {
			MultiStateElementValidator validator = (MultiStateElementValidator) poValidator;
			validator.validateStateSelected("" + expectedInfo.getEv(), poInfo.getMaxIterationsToLocateElements());
		} else if(poValidator instanceof ButtonValidator) {
			ButtonValidator validator = (ButtonValidator) poValidator;
			String avalue = "";
			if(validator instanceof ButtonValidatorSD) {
				ButtonValidatorSD v = (ButtonValidatorSD) validator;
				if(StringUtil.isEmptyAfterTrim(poInfo.getPageObject().getAttrName())) {
					avalue = WebElementUtil.getElementText(scenarioContext.getActiveAppDriver(), v.getDOMObjectValidator().getUIObject(), poInfo.getMaxIterationsToLocateElements());
				} else {
					avalue = v.getDOMObjectValidator().getAttributeValue(avalue, poInfo.getMaxIterationsToLocateElements());
				}
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, avalue, operator, expectedInfo);
			} else if(validator instanceof ButtonValidatorSI) {
				ButtonValidatorSI v = (ButtonValidatorSI) validator;
				v.validateVisible(poInfo.getMaxIterationsToLocateElements());
			} else {
				Assert.fail("The following validator is not supported: " + validator);
			}
		} else if(poValidator instanceof LabelValidator) {
			LabelValidator validator = (LabelValidator) poValidator;
			String avalue = "";
			if(validator instanceof LabelValidatorSD) {
				LabelValidatorSD v = (LabelValidatorSD) validator;
				if(StringUtil.isEmptyAfterTrim(poInfo.getPageObject().getAttrName())) {
					avalue = WebElementUtil.getElementText(scenarioContext.getActiveAppDriver(), v.getDOMObjectValidator().getUIObject(), poInfo.getMaxIterationsToLocateElements());
				} else {
					avalue = v.getDOMObjectValidator().getAttributeValue(avalue, poInfo.getMaxIterationsToLocateElements());
				}
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, avalue, operator, expectedInfo);
			} else if(validator instanceof LabelValidatorSI) {
				LabelValidatorSI v = (LabelValidatorSI) validator;
				v.validateVisible(poInfo.getMaxIterationsToLocateElements());
			} else {
				Assert.fail("The following validator is not supported: " + validator);
			}
		} else if(poValidator instanceof ComboBoxValidator) {
			ComboBoxValidator validator = (ComboBoxValidator) poValidator;
			if(expectedInfo.getEv() != null) {// EV should be List/Array
				List<String> selectedItems = validator.getSelectedItems(poInfo.getMaxIterationsToLocateElements());
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING_LIST.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, selectedItems, operator, expectedInfo);
			}
		} else if(poValidator instanceof HyperlinkValidator) {
			HyperlinkValidator validator = (HyperlinkValidator) poValidator;
			String avalue = "";
			if(validator instanceof HyperlinkValidatorSD) {
				HyperlinkValidatorSD v = (HyperlinkValidatorSD) validator;
				if(StringUtil.isEmptyAfterTrim(poInfo.getPageObject().getAttrName())) {
					avalue = WebElementUtil.getElementText(scenarioContext.getActiveAppDriver(), v.getDOMObjectValidator().getUIObject(), poInfo.getMaxIterationsToLocateElements());
				} else {
					avalue = v.getDOMObjectValidator().getAttributeValue(avalue, poInfo.getMaxIterationsToLocateElements());
				}
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, avalue, operator, expectedInfo);
			} else if(validator instanceof HyperlinkValidatorSI) {
				HyperlinkValidatorSI v = (HyperlinkValidatorSI) validator;
				v.validateVisible(poInfo.getMaxIterationsToLocateElements());
			} else {
				Assert.fail("The following validator is not supported: " + validator);
			}
		} else if(poValidator instanceof ImageValidator) {
			ImageValidator validator = (ImageValidator) poValidator;
			String avalue = "";
			if(validator instanceof ImageValidatorSD) {
				ImageValidatorSD v = (ImageValidatorSD) validator;
				if(StringUtil.isEmptyAfterTrim(poInfo.getPageObject().getAttrName())) {
					avalue = WebElementUtil.getElementText(scenarioContext.getActiveAppDriver(), v.getDOMObjectValidator().getUIObject(), poInfo.getMaxIterationsToLocateElements());
				} else {
					avalue = v.getDOMObjectValidator().getAttributeValue(avalue, poInfo.getMaxIterationsToLocateElements());
				}
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, avalue, operator, expectedInfo);
			} else if(validator instanceof ImageValidatorSI) {
				ImageValidatorSI v = (ImageValidatorSI) validator;
				v.validateVisible(poInfo.getMaxIterationsToLocateElements());
			} else {
				Assert.fail("The following validator is not supported: " + validator);
			}
		} else if(poValidator instanceof ListBoxValidator) {
			ListBoxValidator validator = (ListBoxValidator) poValidator;
			if(expectedInfo.getEv() != null) {// EV should be List/Array
				List<String> selectedItems = validator.getSelectedItems(poInfo.getMaxIterationsToLocateElements());
				ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING_LIST.getType());
				ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, selectedItems, operator, expectedInfo);
			}
		} else if(poValidator instanceof InputFileValidator) {
			InputFileValidator validator = (InputFileValidator) poValidator;
			String value = validator.getValue(poInfo.getMaxIterationsToLocateElements());
			ParamPath fieldInfo = new ParamPath(validator.getUIObject().getDisplayName(), ParamValueType.STRING.getType());
			ParameterValidator.validateParamValueAsExpectedInfo(true, fieldInfo, value, operator, expectedInfo);
		}
	}
	
	/**
	 * Used to fill the form field data.
	 * 
	 * @param poInfo
	 * @param inputValue
	 * @param scenarioContext
	 */
	@SuppressWarnings("unchecked")
	public static void fillUiInputFieldInfo(PageObjectInfo poInfo, InputValue inputValue, SmartCucumberScenarioContext scenarioContext) {
		Object poValidator = PageObjectUtil.getPageObjectValidator(poInfo, scenarioContext);
		
		if(poValidator instanceof TextBoxValidator) {
			TextBoxValidator validator = (TextBoxValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.TYPE) {
				if(inputValue.getValueType() == InputValueType.AUTO_GENERATED) {
					String value = inputValue.getAutoValueInputs().build();
					validator.typeText(value, inputValue.getLocation(), inputValue.getTypeSpeedMspc(), inputValue.getTypeAfterClick(), 
							poInfo.getMaxIterationsToLocateElements());
				} else {
					validator.typeText((inputValue.getValue() == null ? "" : "" + inputValue.getValue()), 
							inputValue.getLocation(), inputValue.getTypeSpeedMspc(), inputValue.getTypeAfterClick(), poInfo.getMaxIterationsToLocateElements());
				}
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on TextBox component.");
			}
		} else if(poValidator instanceof TextAreaValidator) {
			TextAreaValidator validator = (TextAreaValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.TYPE) {
				if(inputValue.getValueType() == InputValueType.AUTO_GENERATED) {
					String value = inputValue.getAutoValueInputs().build();
					validator.typeText(value, inputValue.getLocation(), inputValue.getTypeSpeedMspc(), inputValue.getTypeAfterClick(), poInfo.getMaxIterationsToLocateElements());
				} else {
					validator.typeText((inputValue.getValue() == null ? "" : "" + inputValue.getValue()), 
							inputValue.getLocation(), inputValue.getTypeSpeedMspc(), inputValue.getTypeAfterClick(), poInfo.getMaxIterationsToLocateElements());
				}
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on TextArea component.");
			}
			
		} else if(poValidator instanceof CheckBoxValidator) {
			CheckBoxValidator validator = (CheckBoxValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				validator.checkAndValidateChecked(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK || inputValue.getAction() == InputValueAction.DESELECT) {
				validator.uncheckAndValidateUnchecked(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on CheckBox component.");
			}
		} else if(poValidator instanceof CheckBoxGroupValidator) {
			CheckBoxGroupValidator validator = (CheckBoxGroupValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.checkCheckBoxes(cbNames, poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK || inputValue.getAction() == InputValueAction.DESELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.uncheckCheckBoxes(cbNames, poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.CHECK_ALL || inputValue.getAction() == InputValueAction.SELECT_ALL) {
				validator.checkAllCheckBoxes(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK_ALL || inputValue.getAction() == InputValueAction.DESELECT_ALL) {
				validator.uncheckAllCheckBoxes(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on CheckBoxGroup component.");
			}
		} else if(poValidator instanceof RadioButtonValidator) {
			RadioButtonValidator validator = (RadioButtonValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				validator.select(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on RadioButton component.");
			}
		} else if(poValidator instanceof RadioButtonGroupValidator) {
			RadioButtonGroupValidator validator = (RadioButtonGroupValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				validator.selectOption("" + inputValue.getValue(), "" + inputValue.getValue(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on RadioButtonGroup component.");
			}
		} else if(poValidator instanceof MultiStateElementValidator) {
			MultiStateElementValidator validator = (MultiStateElementValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT || inputValue.getAction() == InputValueAction.TYPE) {
				validator.selectState("" + inputValue.getValue(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on RadioButtonGroup component.");
			}
		} else if(poValidator instanceof ButtonValidator) {
			ButtonValidator validator = (ButtonValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on Button component.");
			}
		} else if(poValidator instanceof LabelValidator) {
			LabelValidator validator = (LabelValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on Label component.");
			}
		} else if(poValidator instanceof ComboBoxValidator) {
			ComboBoxValidator validator = (ComboBoxValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.selectItems(cbNames, inputValue.getSelectingOptionMatchMechanism(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK || inputValue.getAction() == InputValueAction.DESELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.deselectItems(cbNames, inputValue.getSelectingOptionMatchMechanism(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.CHECK_ALL || inputValue.getAction() == InputValueAction.SELECT_ALL) {
				validator.selectAllItems(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK_ALL || inputValue.getAction() == InputValueAction.DESELECT_ALL) {
				validator.deselectAllItems(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on ComboBox component.");
			}
		} else if(poValidator instanceof HyperlinkValidator) {
			HyperlinkValidator validator = (HyperlinkValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on Hyperlink component.");
			}
		} else if(poValidator instanceof ListBoxValidator) {
			ListBoxValidator validator = (ListBoxValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.CHECK || inputValue.getAction() == InputValueAction.SELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.selectItems(cbNames, inputValue.getSelectingOptionMatchMechanism(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK || inputValue.getAction() == InputValueAction.DESELECT) {
				List<String> items = (List<String>)inputValue.getValue();
				ItemList<String> cbNames = new ItemList<String>(items);
				validator.deselectItems(cbNames, inputValue.getSelectingOptionMatchMechanism(), poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.CHECK_ALL || inputValue.getAction() == InputValueAction.SELECT_ALL) {
				validator.selectAllItems(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.UNCHECK_ALL || inputValue.getAction() == InputValueAction.DESELECT_ALL) {
				validator.deselectAllItems(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on ListBox component.");
			}
		} else if(poValidator instanceof InputFileValidator) {
			InputFileValidator validator = (InputFileValidator) poValidator;
			if(inputValue.getAction() == InputValueAction.SELECT) {
				if(inputValue.getValueType() == InputValueType.STRING_LIST) {
					validator.selectFiles(new ItemList<>((List<String>)inputValue.getValue()), poInfo.getMaxIterationsToLocateElements());
				} else if(inputValue.getValueType() == InputValueType.STRING) {
					ItemList<String> list = new ItemList<>();
					list.add("" + inputValue.getValue());
					validator.selectFiles(list, poInfo.getMaxIterationsToLocateElements());
				} else {
					Assert.fail("Value type '" + inputValue.getValueType().getType() + "' is not supported.");
				}
			} else if(inputValue.getAction() == InputValueAction.COMMAND_KEYS) {
				List<String> cmdKeys = (List<String>)inputValue.getValue();
				if(cmdKeys != null && !cmdKeys.isEmpty()) {
					String[] keysArr = prepareKeysChord(cmdKeys);
					validator.sendCommandKeys(poInfo.getMaxIterationsToLocateElements(), keysArr);
				} else {
					Assert.fail("No command key found to perform operation.");
				}
			} else if(inputValue.getAction() == InputValueAction.MOUSE_CLICK) {
				validator.click(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DOUBLECLICK) {
				validator.doubleClick(poInfo.getMaxIterationsToLocateElements());
			} else if(inputValue.getAction() == InputValueAction.MOUSE_DRAG_DROP) {
				Assert.assertNotNull(inputValue.getToPo(), "For Drag-And-Drop operation 'toPo' property must be specified.");
				PageObjectInfo toPoInfo = getPageObjectInfo(inputValue.getToPo());
				UIObjectValidator toValidator = (UIObjectValidator) getPageObjectValidator(toPoInfo, scenarioContext);
				WebElement fromElem = (WebElement) validator.findElement(poInfo.getMaxIterationsToLocateElements());
				WebElement toElem = (WebElement) toValidator.findElement(poInfo.getMaxIterationsToLocateElements());
				DragAndDropUtil.dragAndDropElement(fromElem, toElem, scenarioContext.getActiveAppDriver());
			} else {
				Assert.fail("'" + inputValue.getAction() + "' is not supported on InputFile component.");
			}
		} else {
			Assert.fail("Support for '" + poInfo.getPoClassFieldName() + "' type of component is not present.");
		}
		
		if(inputValue.getWaitTimeInMsAfterOp() > 0) {
			scenarioContext.waitForSeconds(inputValue.getWaitTimeInMsAfterOp());
		}
	}
	
	public static String[] prepareKeysChord(List<String> keyList) {
		String[] keysChord = new String[keyList.size()];
		Keys selKey;
		for(int i = 0; i < keyList.size(); i++) {
			selKey = findSeleniumKeysByName(keyList.get(i));
			if(selKey == null) {
				keysChord[i] = keyList.get(i);
			} else {
				keysChord[i] = selKey.toString();
			}
		}
		
		return keysChord;
	}
	
	public static Keys findSeleniumKeysByName(String keyName) {
		try {
			return Keys.valueOf(keyName);
		} catch(Throwable th) {
			
		}
		return null;
	}
}
