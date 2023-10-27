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
package org.uitnet.testing.smartfwk;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.support.FileSequenceNumberGenerator;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberScenarioHooksExecuter {
	private static SmartCucumberScenarioHooksExecuter instance;

	private SmartCucumberScenarioHooksExecuter() {
		
	}

	public static SmartCucumberScenarioHooksExecuter getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (FileSequenceNumberGenerator.class) {
			if (instance == null) {
				instance = new SmartCucumberScenarioHooksExecuter();
			}
		}

		return instance;
	}
	
	public void executeHook(SmartCucumberScenarioContext scenarioContext, String hookName, String data) {
		String[] qClassAndMethodArr;
		qClassAndMethodArr = getQClassAndMethod(scenarioContext, hookName);
		String qClassName = qClassAndMethodArr[0];
		String methodName = qClassAndMethodArr[1];
				
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(qClassName);
			Method m = ObjectUtil.findClassMethod(clazz, methodName, 2);
			Object[] args = {scenarioContext, data};
			ObjectUtil.invokeMethod(clazz, m, args);
		} catch(Exception ex) {
			Assert.fail("Failed to execute '" + methodName + "' hook/method defined in '" + qClassName + 
					"' class. If this class or method does not exist, please create using the following method signature:\n"
					+ "\n\npublic static void " + methodName + "(SmartCucumberScenarioContext scenarioContext, String data) {\n//add code here\n}."
					+ "\n\nAlso try to add the implementation of this method as per your requirements.", ex);
		}
	}
		
	public void executeAfterScenarioHooks(SmartCucumberScenarioContext scenarioContext) {
		Map<String, String> hooks = scenarioContext.getRegisteredAfterScenarioHooks();
		if(hooks == null || hooks.isEmpty()) {
			return;
		}
		
		String data;
		String[] qClassAndMethodArr;
		List<String> exceptions = new LinkedList<>();
		int hookCounter = 0;
		for(Map.Entry<String, String> hook: hooks.entrySet()) {
			try {
				hookCounter++;
				qClassAndMethodArr = getQClassAndMethod(scenarioContext, hook.getKey());
				String qClassName = qClassAndMethodArr[0];
				String methodName = qClassAndMethodArr[1];
				
				data = hook.getValue();
				
				try {
					@SuppressWarnings("rawtypes")
					Class clazz = Class.forName(qClassName);
					Method m = ObjectUtil.findClassMethod(clazz, methodName, 2);
					Object[] args = {scenarioContext, data};
					ObjectUtil.invokeMethod(clazz, m, args);
				} catch(Exception ex) {
					String msg = "Hook#-" + hookCounter + ": Failed to execute '" + methodName + "' hook/method defined in '" + qClassName + 
							"' class. If this class or method does not exist, please create using the following method signature:\n"
							+ "\n\npublic static void " + methodName + "(SmartCucumberScenarioContext scenarioContext, String data) {\n//add code here\n}."
							+ "\n\nAlso try to add the implementation of this method as per your requirements.\n" + ObjectUtil.convertExceptionToString(ex);
					
					exceptions.add(msg);
				}
			} catch(Exception ex) {
				String msg = "Hook#-" + hookCounter + ": Failed to execute '" + hook.getKey() + "' hook/method If this class or method does not exist, "
						+ "please create the appropriate hook in src/main/java/scenario_hooks directory and register in the correct step.\n" 
						+ ObjectUtil.convertExceptionToString(ex);
				
				exceptions.add(msg);
			}
				
		}
		
		if(!exceptions.isEmpty()) {
			Assert.fail("Failed " + exceptions.size() + " / " + hooks.size() + " hooks.\n" + ObjectUtil.convertListToString(exceptions, "\n\n"));
		}
	}
	
	public String[] getQClassAndMethod(SmartCucumberScenarioContext scenarioContext, String qualifiedMethodName) {
		String qMethodName = qualifiedMethodName.split("-")[0].trim();
		
		int lastIndex = qMethodName.lastIndexOf(".");
		String qClassName = "scenario_hooks." + qMethodName.substring(0, lastIndex);
		String methodName = qMethodName.substring(lastIndex+1);
		
		String[] qClassAndMethodArr = {qClassName, methodName};
		
		return qClassAndMethodArr;
	}
	
	public void checkHookExists(SmartCucumberScenarioContext scenarioContext, String qualifiedMethodName) {
		String[] qClassAndMethodArr = getQClassAndMethod(scenarioContext, qualifiedMethodName);
		String qClassName = qClassAndMethodArr[0];
		String methodName = qClassAndMethodArr[1];
		
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(qClassName);
			ObjectUtil.findClassMethod(clazz, methodName, 2);
		} catch(Exception ex) {
			Assert.fail("Failed to find '" + methodName + "' hook/method in '" + qClassName + 
					"' class. If this class or method does not exist, please create using the following method signature:\n"
					+ "public static void " + methodName + "(SmartCucumberScenarioContext scenarioContext, String data) {\n//add code here\n}."
					+ "\nAlso try to add the implementation of this method as per your requirements.", ex);
		}
	}
}
