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
package org.uitnet.testing.smartfwk;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

import io.cucumber.java.Scenario;

/**
 * Generic interface for cucumber scenario context.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberScenarioContext {
	protected Map<String, Object> params;
	protected Scenario scenario = null;

	protected String activeAppName = null;

	public SmartCucumberScenarioContext() {
		params = new TreeMap<>(Collections.reverseOrder());
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public String getActiveAppName() {
		return activeAppName;
	}

	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public AppConfig getActiveAppConfig() {
		return getTestConfigManager().getAppConfig(activeAppName);
	}

	public AppConfig getAppConfig(String appName) {
		return getTestConfigManager().getAppConfig(appName);
	}

	public void log(String message) {
		scenario.log(message);
	}

	public void close() {
		// must be overridden by child class for cleanup
	}

	public void addParamValue(String paramName, Object value) {
		params.put(paramName, value);
	}

	/**
	 * This method returns param value. If does not exist then returns as null.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValue(String paramName) {
		return params.get(paramName);
	}
	
	public String getParamValueAsString(String paramName) {
		return ObjectUtil.valueAsString(params.get(paramName));
	}
	
	public Integer getParamValueAsInteger(String paramName) {
		return ObjectUtil.valueAsInteger(params.get(paramName));
	}
	
	public Long getParamValueAsLong(String paramName) {
		return ObjectUtil.valueAsLong(params.get(paramName));
	}
	
	public Double getParamValueAsDouble(String paramName) {
		return ObjectUtil.valueAsDouble(params.get(paramName));
	}
	
	public Boolean getParamValueAsBoolean(String paramName) {
		return ObjectUtil.valueAsBoolean(params.get(paramName));
	}
	
	/**
	 * MultiValue params are like Array, List, Set
	 * @param paramName
	 * @param delimitter          - could be , or any string, if null then it will
	 *                            use default as ,
	 * @param valueEnclosingChars like ' or " or empty/null (denotes no enclosing)
	 * @return
	 */
	public String getMultiValueParamValueAsString(String paramName, String delimitter, String valueEnclosingChars) {
		return ObjectUtil.listSetArrayValueAsString(params.get(paramName), delimitter, valueEnclosingChars);
	}

	public Map<String, Object> getAllParams() {
		return params;
	}
	
	public Map<String, Object> getEntriesForParamsEndsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1 == null && o2 == null) { return 0; }
				if(o1 == null && o2 != null) { return o2.length(); }
				if(o1 != null && o2 == null) { return 0 - o1.length(); }
				return o2.length() - o1.length();
			}
		});
		
		for (Map.Entry<String, Object> k : params.entrySet()) {
			if (k.getKey().endsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}
	
	public Map<String, Object> getEntriesForParamsStartsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1 == null && o2 == null) { return 0; }
				if(o1 == null && o2 != null) { return o2.length(); }
				if(o1 != null && o2 == null) { return 0 - o1.length(); }
				return o2.length() - o1.length();
			}
		});
		
		for (Map.Entry<String, Object> k : params.entrySet()) {
			if (k.getKey().startsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}

	/**
	 * This method returns param value. If does not exist then returns paramName as
	 * value.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValueNullAsParamName(String paramName) {
		Object val = params.get(paramName);
		if (val == null) {
			return paramName;
		}
		return val;
	}

	public void removeParam(String paramName) {
		params.remove(paramName);
	}

	/**
	 * It will apply all params value to the text. It will convert param value to
	 * string then apply.
	 * 
	 * @param text
	 * @return the updated text
	 */
	public String applyParamsValueOnText(String text) {
		for (Map.Entry<String, Object> e : params.entrySet()) {
			text = text.replace(e.getKey(), "" + e.getValue());
		}

		return text;
	}
}
