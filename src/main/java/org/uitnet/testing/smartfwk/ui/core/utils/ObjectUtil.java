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

import java.lang.reflect.Method;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ObjectUtil {
	public ObjectUtil() {
		// do nothing
	}

	public static Method findClassMethod(Class<?> clazz, String methodName, Class<?>[] argTypes) {
		Method foundMethod = null;
		try {
			Method[] methods = clazz.getMethods();
			boolean found = false;
			for (Method m : methods) {
				foundMethod = null;
				if (m.getName().equals(methodName) && m.getParameterCount() == argTypes.length) {
					Class<?>[] paramTypes = m.getParameterTypes();
					Class<?> paramType = null;
					found = true;
					for (int i = 0; i < paramTypes.length; i++) {
						paramType = paramTypes[i];
						if (!paramType.getTypeName().equals(argTypes[i].getTypeName())) {
							found = false;
							break;
						}
					}

					if (found) {
						foundMethod = m;
						break;
					}
				}
			}

		} catch (Exception ex) {
			Assert.fail("Failed to find '" + methodName + "' method in class '" + clazz.getName() + "'.", ex);
		}

		if (foundMethod == null) {
			Assert.fail("Failed to find '" + methodName + "' method in class '" + clazz.getName() + "'.");
		}

		return foundMethod;
	}

	public static Object invokeMethod(Object clazzObj, Method m, Object[] argValues) {
		Object out = null;
		try {
			out = m.invoke(clazzObj, argValues);
		} catch (Exception ex) {
			Assert.fail("Failed to call '" + m.getName() + "' method from object " + clazzObj + ".", ex);
		}

		return out;
	}
}
