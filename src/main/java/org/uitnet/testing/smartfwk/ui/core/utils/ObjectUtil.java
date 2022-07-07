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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	/**
	 * Convert object (List, Set and java types String, Integer, Double etc.) to
	 * string value. List and set are converted into comma separated value.
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String valueAsString(Object obj) {
		if (obj == null) {
			return null;
		}

		String strv = "";
		if (obj.getClass().isArray()) {
			Object[] objArr = (Object[]) obj;
			for (Object o : objArr) {
				if (strv.equals("")) {
					strv = "" + o;
				} else {
					strv = strv + ", " + o;
				}
			}
		} else if (obj instanceof List) {
			List list = (List) obj;
			for (Object o : list) {
				if (strv.equals("")) {
					strv = "" + o;
				} else {
					strv = strv + ", " + o;
				}
			}
		} else if (obj instanceof Set) {
			Set list = (Set) obj;
			for (Object o : list) {
				if (strv.equals("")) {
					strv = "" + o;
				} else {
					strv = strv + ", " + o;
				}
			}
		} else if (obj instanceof Map) {
			Assert.fail("Map to string conversion is not supported.");
		} else {
			strv = String.valueOf(obj);
		}

		return strv;
	}

	public static Integer valueAsInteger(Object obj) {
		if (obj == null) {
			return null;
		}

		return Integer.parseInt("" + obj);
	}

	public static Long valueAsLong(Object obj) {
		if (obj == null) {
			return null;
		}

		return Long.parseLong("" + obj);
	}

	public static Double valueAsDouble(Object obj) {
		if (obj == null) {
			return null;
		}

		return Double.parseDouble("" + obj);
	}

	public static Boolean valueAsBoolean(Object obj) {
		if (obj == null) {
			return null;
		}

		return Boolean.parseBoolean("" + obj);
	}

	/**
	 * This method converts the Array, List and Set type of objects into delimitter
	 * separated value. Each value will be enclosed using "valueEnclosingChars". It
	 * will not enclose null values.
	 * 
	 * @param obj                 - could be List, Set or Array type
	 * @param delimitter          - could be , or any string, if null then it will
	 *                            use default as ,
	 * @param valueEnclosingChars like ' or " or empty/null (denotes no enclosing)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String listSetArrayValueAsString(Object obj, String delimitter, String valueEnclosingChars) {
		if (obj == null) {
			return null;
		}

		valueEnclosingChars = (valueEnclosingChars == null) ? "" : valueEnclosingChars;
		delimitter = delimitter == null ? "," : delimitter;

		String strv = "";
		if (obj.getClass().isArray()) {
			Object[] objArr = (Object[]) obj;
			for (Object o : objArr) {
				if (strv.equals("")) {
					strv = ((o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars));
				} else {
					strv = strv + ", " + ((o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars));
				}
			}
		} else if (obj instanceof List) {
			List list = (List) obj;
			for (Object o : list) {
				if (strv.equals("")) {
					strv = (o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars);
				} else {
					strv = strv + ", " + ((o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars));
				}
			}
		} else if (obj instanceof Set) {
			Set list = (Set) obj;
			for (Object o : list) {
				if (strv.equals("")) {
					strv = (o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars);
				} else {
					strv = strv + ", " + ((o == null) ? "null" : (valueEnclosingChars + o + valueEnclosingChars));
				}
			}
		} else if (obj instanceof Map) {
			Assert.fail("Map to string conversion is not supported.");
		} else {
			strv = String.valueOf(obj);
		}

		return strv;
	}

	public static Constructor<?> findClassConstructor(Class<?> clazz, Class<?>[] argTypes) {
		Constructor<?> foundConstructor = null;
		try {
			Constructor<?>[] constructors = clazz.getConstructors();

			if (argTypes == null) {
				argTypes = new Class<?>[] {};
			}

			boolean found = false;
			for (Constructor<?> m : constructors) {
				foundConstructor = null;
				if (m.getParameterCount() == argTypes.length) {
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
						foundConstructor = m;
						break;
					}
				}
			}

		} catch (Exception ex) {
			Assert.fail("Failed to find constructor in class '" + clazz.getName() + "'.", ex);
		}

		if (foundConstructor == null) {
			Assert.fail("Failed to find constructor in class '" + clazz.getName() + "'.");
		}

		return foundConstructor;
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

	public static Object invokeMethod(Object clazzObj, Method m, Object[] argValues)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object out = null;
		out = m.invoke(clazzObj, argValues);
		return out;
	}

	public static void main(String[] args) {
		System.out.println(listSetArrayValueAsString(new Integer[] { null, 55, 78 }, null, "\""));
	}
}
