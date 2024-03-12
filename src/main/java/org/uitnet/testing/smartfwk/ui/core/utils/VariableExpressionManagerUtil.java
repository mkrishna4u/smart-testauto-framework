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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.w3c.dom.Document;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * This utility is used to extract & apply the parameters within the text.
 * 
 * @author Madhav Krishna
 *
 */
public class VariableExpressionManagerUtil {

	private VariableExpressionManagerUtil() {
	}

	public static String applyParamValueOnText(String text, String paramName, Object valueObj) {
		if(text == null) {
			return null;
		}
		
		Map<String, TextParamInfo> textParams = extractParamDetailsFromText(text, paramName);
		String textParam;
		TextParamInfo pInfo;
		
		for(Map.Entry<String, TextParamInfo> entry : textParams.entrySet()) {
			textParam = entry.getKey();
			pInfo = entry.getValue();
			List<?> values = null;
			Object value = null;
			if(valueObj != null && pInfo.getPathType() == PathType.JSON_PATH) {
				DocumentContext docCtx = (DocumentContext)valueObj;
				if(pInfo.getValueType() != null && 
						(pInfo.getValueType() == ParamValueType.INTEGER_LIST 
						|| pInfo.getValueType() == ParamValueType.DECIMAL_LIST
						|| pInfo.getValueType() == ParamValueType.STRING_LIST 
						|| pInfo.getValueType() == ParamValueType.BOOLEAN_LIST)) {
					TypeRef<List<String>> typeRef = new TypeRef<List<String>>() {
					};
					values = pInfo.getPath() != null ? docCtx.read(pInfo.getPath(), typeRef) : null;
					String listAsStr = prepareListValue(values, pInfo.getValueType(), pInfo.getAction());
					text = text.replace(textParam, "" + listAsStr);
				} else {
					value = pInfo.getPath() != null ? docCtx.read(pInfo.getPath()) : null;
					String value2 = prepareValue(value, pInfo.getValueType(), pInfo.getAction());
					text = text.replace(textParam, "" + value2);
				}
			} else if(valueObj != null && pInfo.getPathType() == PathType.XPATH) {
				Document docCtx = (Document)valueObj;
				if(pInfo.getValueType() != null && 
						(pInfo.getValueType() == ParamValueType.INTEGER_LIST 
						|| pInfo.getValueType() == ParamValueType.DECIMAL_LIST
						|| pInfo.getValueType() == ParamValueType.STRING_LIST 
						|| pInfo.getValueType() == ParamValueType.BOOLEAN_LIST)) {
					XmlDocumentReader xmlReader = new XmlDocumentReader(docCtx);
					if(pInfo.getPath() != null) {
						values = (List<?>) xmlReader.findAttributeOrTextValues("XPath", new ParamPath(pInfo.getPath(), pInfo.getValueType().getType()));
						String listAsStr = prepareListValue(values, pInfo.getValueType(), pInfo.getAction());
						text = text.replace(textParam, "" + listAsStr);
					}
				} else {
					XmlDocumentReader xmlReader = new XmlDocumentReader(docCtx);
					if(pInfo.getPath() != null) {
						value = xmlReader.findAttributeOrTextValues("XPath", new ParamPath(pInfo.getPath(), 
								pInfo.getValueType() == null ? ParamValueType.STRING.getType() : pInfo.getValueType().getType()));
						String value2 = prepareValue(value, pInfo.getValueType(), pInfo.getAction());
						text = text.replace(textParam, "" + value2);
					}
				}
			} else {
				if(pInfo.getValueType() != null && 
						(pInfo.getValueType() == ParamValueType.INTEGER_LIST 
						|| pInfo.getValueType() == ParamValueType.DECIMAL_LIST
						|| pInfo.getValueType() == ParamValueType.STRING_LIST 
						|| pInfo.getValueType() == ParamValueType.BOOLEAN_LIST)) {
					values = (List<?>) valueObj;
					String listAsStr = prepareListValue(values, pInfo.getValueType(), pInfo.getAction());
					text = text.replace(textParam, "" + listAsStr);
				} else {
					value = valueObj;
					String value2 = prepareValue(value, pInfo.getValueType(), pInfo.getAction());
					text = text.replace(textParam, "" + value2);
				}
				
			}
		}
		
		return text;
	}
	
	private static String prepareListValue(List<?> values, ParamValueType valueType, ParamValueAction action) {
		String listValues = "";
		if(values == null || values.size() == 0) {
			return listValues;
		}
		
		for(int i = 0; i < values.size(); i++) {
			if(valueType == ParamValueType.STRING_LIST) {
				listValues = "".equals(listValues) ? "\"" + applyActionOnText(values.get(i), action) + "\"" : 
					listValues + ", \"" + applyActionOnText(values.get(i), action) + "\"";
			} else {
				listValues = "".equals(listValues) ? "" + applyActionOnText(values.get(i), action) : 
					listValues + ", " + applyActionOnText(values.get(i), action) + "";
			}
		}
		
		return listValues;
	}
	
	private static Object applyActionOnText(Object text, ParamValueAction action) {
		if(action == null) { return text; }
		String newText = "" + text;
		switch(action) {
			case replaceNullByEmpty:
				if("null".equalsIgnoreCase(newText)) {
					newText = "";
				}
				break;
			case trim:
				newText = newText.trim();
				break;
			case trimLeft:
				newText = newText.stripLeading();
				break;
			case trimRight:
				newText = newText.stripTrailing();
				break;
		}
		
		return newText;
	}
	
	private static String prepareValue(Object value, ParamValueType valueType, ParamValueAction action) {
		if(value == null) {
			Object v = applyActionOnText(value, action);
			if(v == null) { return null; }
			else { return "\"" + v + "\""; }
		}
		
		if(valueType == null) {
			return "" + value;
		} else if(valueType == ParamValueType.STRING) {
			if(value instanceof List) {
				List<?> elems = (List<?>) value;
				String r = "";
				for(Object elem : elems) {
					r = "".equals(r) ? "\"" + applyActionOnText(elem, action)  + "\""
							: r + ", \"" + applyActionOnText(elem, action) + "\"";
				}
				return r;
			} else {
				return "\"" + applyActionOnText(value, action) + "\"";
			}			
		} else {
			return "" + applyActionOnText(value, action);
		}		
	}
	
	public static Map<String, TextParamInfo> extractParamDetailsFromText(String text, String paramName) {
		if(StringUtil.isEmptyAfterTrim(text) || StringUtil.isEmptyAfterTrim(paramName)) {
			return null;
		}
		
		Map<String, TextParamInfo> params = new LinkedHashMap<String, TextParamInfo>();
		
		TextParamInfo pInfo = new TextParamInfo();
		
		String pStart = "${"+paramName+":";		
		int s = -1;
		
		do {
			String fParam = pStart;
			s = text.indexOf(pStart, 0);
			
			if(s >= 0) {
				int lbraces = 1;
				for(int i = (s + pStart.length()); i < text.length(); i++) {
					fParam = fParam + text.charAt(i);
					
					if(text.charAt(i) == '{') {
						lbraces++;
					} else if(text.charAt(i) == '}') {
						lbraces--;
					}
					
					if(lbraces == 0) {
						break;
					}
				}
				
				pInfo = prepareTextParamInfo(fParam, paramName);
				params.put(fParam, pInfo);
				text = text.replace(fParam, "");
			} else if(text.indexOf(paramName, 0) >= 0) {
				pInfo = prepareTextParamInfo(paramName, paramName);
				params.put(paramName, pInfo);
				text = text.replace(paramName, "");
			}
		} while(s >= 0);
		
		return params;		
	}
	
	private static TextParamInfo prepareTextParamInfo(String fParam, String paramName) {
		TextParamInfo pInfo = new TextParamInfo();
		
		if(fParam.equals(paramName)) {
			return pInfo;
		}
		
		String type = "", path = "", action="";
		String pStart = "${"+paramName+":";
		char ch;
		int counter = 0;
		for(int i = pStart.length(); i < (fParam.length() - 1); i++) {
			ch = fParam.charAt(i);
			if(ch == ':') {
				counter++;
				continue;
			}
			
			if(counter == 0) {
				type = type + fParam.charAt(i);
			} else if(counter == 1) {
				action = action + fParam.charAt(i);
			} else if(counter == 2) {
				path = path + fParam.charAt(i);
			} 
		}
		
		if(!StringUtil.isEmptyAfterTrim(type)) {
			ParamValueType type2 = ParamValueType.valueOf2(type);
			pInfo.setValueType(type2);
		}
		
		if(!StringUtil.isEmptyAfterTrim(action)) {
			ParamValueAction action2 = ParamValueAction.valueOf2(action);
			pInfo.setAction(action2);
		}
		
		if(!StringUtil.isEmptyAfterTrim(path)) {
			path = path.trim();
			pInfo.setPath(path);
			if(path.startsWith("$")) {
				pInfo.setPathType(PathType.JSON_PATH);
			} else {
				pInfo.setPathType(PathType.XPATH);
			}			
		}
		
		return pInfo;
	}

	public static class TextParamInfo {
		private ParamValueType valueType;
		private ParamValueAction action;
		private PathType pathType; // jsonPath, xpath
		private String path;
		
		public TextParamInfo() {
			
		}
		
		public ParamValueType getValueType() {
			return valueType;
		}

		public void setValueType(ParamValueType valueType) {
			this.valueType = valueType;
		}

		public ParamValueAction getAction() {
			return action;
		}

		public void setAction(ParamValueAction action) {
			this.action = action;
		}

		public PathType getPathType() {
			return pathType;
		}

		public void setPathType(PathType pathType) {
			this.pathType = pathType;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
		
		@Override
		public String toString() {			
			return "valueType: " + valueType + ", action: " + action + ", pathType: " + pathType + ", path: " + path;
		}
	}

	public static enum PathType {
		JSON_PATH, XPATH
	}
	
	public enum ParamValueAction {
		replaceNullByEmpty, trim, trimLeft, trimRight;

		public static ParamValueAction valueOf2(String strType) {
			if(strType == null || "".equals(strType.trim())) { return null; }
			for (ParamValueAction type1 : values()) {
				if (type1.name().equalsIgnoreCase(strType.trim())) {
					return type1;
				}
			}
			Assert.fail("Param value action '" + strType + "' is not supported.");
			return null;
		}		
	}
}
