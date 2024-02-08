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

import static org.testng.Assert.fail;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.core.validator.ParamValue;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;

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
			strv = String.valueOf((valueEnclosingChars + obj + valueEnclosingChars));
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

	/* public static Method findClassMethod(Class<?> clazz, String methodName, Class<?>[] argTypes) {
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
	} */
	
	public static Method findClassMethod(Class<?> clazz, String methodName, String[] argTypesName) {
		Method foundMethod = null;
		try {
			Method[] methods = clazz.getMethods();
			boolean found = false;
			for (Method m : methods) {
				foundMethod = null;
				if (m.getName().equals(methodName) && m.getParameterCount() == argTypesName.length) {
					Class<?>[] paramTypes = m.getParameterTypes();
					Class<?> paramType = null;
					found = true;
					for (int i = 0; i < paramTypes.length; i++) {
						paramType = paramTypes[i];
						if (!paramType.getTypeName().equals(argTypesName[i])) {
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
	
	public static Method findClassMethod(Class<?> clazz, String methodName, int numArgs) {
		Method foundMethod = null;
		try {
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
				if (m.getName().equals(methodName) && m.getParameterCount() == numArgs) {					
					foundMethod = m;
					break;
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
		ObjectUtil.fixArgsValues(m.getParameterTypes(), argValues);
		out = m.invoke(clazzObj, argValues);
		return out;
	}

	@SuppressWarnings("unchecked")
	public static ParamValue convertObjectToParamValue(Object obj) {
		ParamValue pvalue = new ParamValue();
		pvalue.setV(obj);

		ParamValueType valueType = null;
		if (obj != null && obj instanceof List) {
			List<Object> list = (List<Object>) obj;
			for (Object elem : list) {
				if (elem != null) {
					if (elem instanceof Long || elem instanceof Integer) {
						valueType = ParamValueType.INTEGER_LIST;
						break;
					} else if (elem instanceof Boolean) {
						valueType = ParamValueType.BOOLEAN_LIST;
						break;
					} else if (elem instanceof Double || elem instanceof Float) {
						valueType = ParamValueType.DECIMAL_LIST;
						break;
					}
				}
			}
			if (valueType == null) {
				valueType = ParamValueType.STRING_LIST;
			}
		} else if (obj != null && obj instanceof Set) {
			Set<Object> list = (Set<Object>) obj;
			List<Object> list2 = new ArrayList<>();
			for (Object elem : list) {
				list2.add(elem);
				if (elem != null) {
					if (elem instanceof Long || elem instanceof Integer) {
						valueType = ParamValueType.INTEGER_LIST;
					} else if (elem instanceof Boolean) {
						valueType = ParamValueType.BOOLEAN_LIST;
					} else if (elem instanceof Double || elem instanceof Float) {
						valueType = ParamValueType.DECIMAL_LIST;
					}
				}
			}
			if (valueType == null) {
				valueType = ParamValueType.STRING_LIST;
			}
			pvalue.setV(list2);
		} else if (obj != null && obj instanceof Map) {
			Assert.fail("Map data is not supported.");
		} else if (obj != null && obj.getClass().isArray()) {
			Object[] list = (Object[]) obj;
			List<Object> list2 = new ArrayList<>();
			for (Object elem : list) {
				list2.add(elem);
				if (elem != null) {
					if (elem instanceof Long || elem instanceof Integer) {
						valueType = ParamValueType.INTEGER_LIST;
					} else if (elem instanceof Boolean) {
						valueType = ParamValueType.BOOLEAN_LIST;
					} else if (elem instanceof Double || elem instanceof Float) {
						valueType = ParamValueType.DECIMAL_LIST;
					}
				}
			}
			if (valueType == null) {
				valueType = ParamValueType.STRING_LIST;
			}
			pvalue.setV(list2);
		} else {
			if (obj instanceof Long || obj instanceof Integer) {
				valueType = ParamValueType.INTEGER;
			} else if (obj instanceof Boolean) {
				valueType = ParamValueType.BOOLEAN;
			} else if (obj instanceof Double || obj instanceof Float) {
				valueType = ParamValueType.DECIMAL;
			}

			if (valueType == null) {
				if(obj == null || !(obj instanceof List || obj.getClass().isArray())) {
					valueType = ParamValueType.STRING;
				} else {
					valueType = ParamValueType.STRING_LIST;
				}
			}
		}

		pvalue.setValueType(valueType.getType());

		return pvalue;
	}

	@SuppressWarnings("unchecked")
	public static void fixValueTypesInParamValueObjects(ParamValue pv1, ValueMatchOperator operator, ParamValue pv2) {
		if (pv1.getValueType() != pv2.getValueType() && pv1 != null && pv2 != null) {
			if (pv1.getValueType().getType().endsWith("-list")) {
				List<Object> list = (List<Object>) pv1.getV();
				if (list == null || list.isEmpty()) {
					if (operator == ValueMatchOperator.EQUAL_TO || operator == ValueMatchOperator.GREATER_THAN
							|| operator == ValueMatchOperator.GREATER_THAN_EQUAL_TO
							|| operator == ValueMatchOperator.LESS_THAN
							|| operator == ValueMatchOperator.LESS_THAN_EQUAL_TO) {
						pv1.setValueType(pv2.getValueType().getType());
					}
				}
			} else if (pv2.getValueType().getType().endsWith("-list")) {
				List<Object> list = (List<Object>) pv2.getV();
				if (list == null || list.isEmpty()) {
					if (operator == ValueMatchOperator.EQUAL_TO || operator == ValueMatchOperator.GREATER_THAN
							|| operator == ValueMatchOperator.GREATER_THAN_EQUAL_TO
							|| operator == ValueMatchOperator.LESS_THAN
							|| operator == ValueMatchOperator.LESS_THAN_EQUAL_TO) {
						pv2.setValueType(pv1.getValueType().getType());
					}
				}
			} else {
				if (pv1.getV() == null) {
					if (operator == ValueMatchOperator.EQUAL_TO || operator == ValueMatchOperator.GREATER_THAN
							|| operator == ValueMatchOperator.GREATER_THAN_EQUAL_TO
							|| operator == ValueMatchOperator.LESS_THAN
							|| operator == ValueMatchOperator.LESS_THAN_EQUAL_TO) {
						pv1.setValueType(pv2.getValueType().getType());
					}
				} else if (pv2.getV() == null) {
					if (operator == ValueMatchOperator.EQUAL_TO || operator == ValueMatchOperator.GREATER_THAN
							|| operator == ValueMatchOperator.GREATER_THAN_EQUAL_TO
							|| operator == ValueMatchOperator.LESS_THAN
							|| operator == ValueMatchOperator.LESS_THAN_EQUAL_TO) {
						pv2.setValueType(pv1.getValueType().getType());
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object fixObjectValueAsPerItsType(Object value, ParamValueType valueType) {
		if (valueType == null) {
			if(value == null || !(value instanceof List || value.getClass().isArray())) {
				valueType = ParamValueType.STRING;
			} else {
				valueType = ParamValueType.STRING_LIST;
			}
		}
		
		if(value == null) { return null; }
		
		switch (valueType) {
		case STRING: {
			return ("" + value);
		}
		case STRING_LIST: {
			List<Object> elems = (List<Object>) value;
			List<String> elems2 = new ArrayList<>();
			for(Object elem : elems) {
				elems2.add(elem == null ? null : "" + elem);
			}
			
			return elems2;
		}
		case INTEGER: {
			return Long.parseLong("" + value);
		}
		case INTEGER_LIST: {
			List<Object> elems = (List<Object>) value;
			List<Long> elems2 = new ArrayList<>();
			for(Object elem : elems) {
				elems2.add(elem == null ? null : Long.parseLong("" + elem));
			}
			
			return elems2;
		}
		case DECIMAL: {
			return Double.parseDouble("" + value);
		}
		case DECIMAL_LIST: {
			List<Object> elems = (List<Object>) value;
			List<Double> elems2 = new ArrayList<>();
			for(Object elem : elems) {
				elems2.add(elem == null ? null : Double.parseDouble("" + elem));
			}
			
			return elems2;
		}
		case BOOLEAN: {
			return Boolean.parseBoolean("" + value);
		}
		case BOOLEAN_LIST: {
			List<Object> elems = (List<Object>) value;
			List<Boolean> elems2 = new ArrayList<>();
			for(Object elem : elems) {
				elems2.add(elem == null ? null : Boolean.parseBoolean("" + elem));
			}
			
			return elems2;
		}
		default:
			fail("'" + valueType + "' value type is not supported.");
		}

		return null;
	}
	
	/**
	 * Used to fix the method arguments value based on the method arguments type.
	 * 
	 * @param argsType
	 * @param argsValue
	 */
	@SuppressWarnings("unchecked")
	public static void fixArgsValues(Class<?>[] argsType, Object[] argsValue) {
		if(argsType == null || argsType.length==0|| argsValue == null || argsValue.length==0) {
			return;
		}
		
		if(argsValue.length != argsType.length) {
			Assert.fail("Number of argument value should be same as number of argument type. NumArgTypes: " + argsType.length + ", NumArgValue: " + argsValue.length);
		}
		
		Collection<Object> objs = null;
		for(int i = 0; i < argsType.length; i++) {
			if(argsType[i].isArray()) {
				//objs = (Object[]) argsValue[i]);
				//Object valueArr1 = argsValue[i]; //(Object[]) Array.newInstance(argsType[i].getComponentType(), objs.size());
				Object[] valueArr = (Object[])argsValue[i];
				
				if(objs != null) {
					int counter;
					for(int j = 0; j < objs.size(); j++) {
						counter = 0;
						for(Object obj : objs) {
							if(counter == j) {
								valueArr[j] = obj; 
								break;
							}
							counter++;
						}
					}
					argsValue[i] = valueArr;
				}
			} else if("java.util.Set".equals(argsType[i].getTypeName())) {
				objs = ((Collection<Object>) argsValue[i]);
				Set<Object> valueArr = new TreeSet<>();
				
				if(objs != null) {
					int counter;
					for(int j = 0; j < objs.size(); j++) {
						counter = 0;
						for(Object obj : objs) {
							if(counter == j) {
								valueArr.add(obj); 
								break;
							}
							counter++;
						}
					}
					argsValue[i] = valueArr;
				}
			}
		}
	}
	
	/**
	 * Used to convert string to java class type name.
	 * @param typeAsStr - like String, String[], List<String>, Set<String>,
	 * 		java.util.List, java.util.Set etc.
	 * @return converted value as java class type name.
	 */
	public static String convertStringToJavaClassType(String typeAsStr) {
		String clazzTypeName = null;
		try {
			boolean isArray = false;
			typeAsStr = typeAsStr.trim();
			if (typeAsStr.endsWith("[]")) {
				isArray = true;
				typeAsStr = typeAsStr.split("\\[\\]")[0];
			}

			if (typeAsStr.equals("int")) {
				clazzTypeName = (isArray ? int[].class.getTypeName() : Integer.TYPE.getTypeName());
			} else if (typeAsStr.equals("long")) {
				clazzTypeName = (isArray ? long[].class.getTypeName() : Long.TYPE.getTypeName());
			} else if (typeAsStr.equals("double")) {
				clazzTypeName = (isArray ? double[].class.getTypeName() : Double.TYPE.getTypeName());
			} else if (typeAsStr.equals("float")) {
				clazzTypeName = (isArray ? float[].class.getTypeName() : Float.TYPE.getTypeName());
			} else if (typeAsStr.equals("short")) {
				clazzTypeName = (isArray ? short[].class.getTypeName() : Short.TYPE.getTypeName());
			} else if (typeAsStr.equals("byte")) {
				clazzTypeName = (isArray ? byte[].class.getTypeName() : Byte.TYPE.getTypeName());
			} else if (typeAsStr.equals("boolean")) {
				clazzTypeName = (isArray ? boolean[].class.getTypeName() : Boolean.TYPE.getTypeName());
			} else if (typeAsStr.equals("char")) {
				clazzTypeName = (isArray ? char[].class.getTypeName() : Character.TYPE.getTypeName());
			} else if (typeAsStr.equals("Integer")) {
				clazzTypeName = (isArray ? Integer[].class.getTypeName() : Integer.class.getTypeName());
			} else if (typeAsStr.equals("Long")) {
				clazzTypeName = (isArray ? Long[].class.getTypeName() : Long.class.getTypeName());
			} else if (typeAsStr.equals("Double")) {
				clazzTypeName = (isArray ? Double[].class.getTypeName() : Double.class.getTypeName());
			} else if (typeAsStr.equals("Float")) {
				clazzTypeName = (isArray ? Float[].class.getTypeName() : Float.class.getTypeName());
			} else if (typeAsStr.equals("Short")) {
				clazzTypeName = (isArray ? Short[].class.getTypeName() : Short.class.getTypeName());
			} else if (typeAsStr.equals("Byte")) {
				clazzTypeName = (isArray ? Byte[].class.getTypeName() : Byte.class.getTypeName());
			} else if (typeAsStr.equals("Boolean")) {
				clazzTypeName = (isArray ? Boolean[].class.getTypeName() : Boolean.class.getTypeName());
			} else if (typeAsStr.equals("Character")) {
				clazzTypeName = (isArray ? Character[].class.getTypeName() : Character.class.getTypeName());
			} else if (typeAsStr.equals("String")) {
				clazzTypeName = (isArray ? String[].class.getTypeName() : String.class.getTypeName());
			} else if (typeAsStr.startsWith("List")) {
				clazzTypeName = (isArray ? List[].class.getTypeName() : List.class.getTypeName());
			} else if (typeAsStr.startsWith("Set")) {
				clazzTypeName = (isArray ? Set[].class.getTypeName() : Set.class.getTypeName());
			} else if (typeAsStr.startsWith("Map")) {
				clazzTypeName = (isArray ? Map[].class.getTypeName() : Map.class.getTypeName());
			} else {
				Class<?> c = Class.forName(typeAsStr);
				clazzTypeName = (isArray ? Array.newInstance(c).getClass().getTypeName() : c.getTypeName());
			}

		} catch (Exception ex) {
			Assert.fail("Failed to convert argument type to a class. Please specify fully "
					+ "qualified class name as argument type. Note: the following primitive "
					+ "types are supported: int, long, double, float, byte, boolean, char.", ex);
		}
		return clazzTypeName;
	}
	
	public static String convertExceptionToString(Throwable th) {
		String[] traces = ExceptionUtils.getRootCauseStackTrace(th);
		String output = "";
		if(traces != null && traces.length > 0) {
			String trace = null;
			for(int i = 0; i < traces.length;i++) {
				trace = traces[i];
				if(output.equals("")) {
					output = trace.toString();
				} else {
					output = output + "\n\n" + trace.toString();
				}
			}
		}
		
		return output;
	}
	
	public static String convertListToString(List<?> list, String separator) {
		String output = "";
		
		if(list == null || list.isEmpty()) { return output; }
		
		for(Object obj : list) {
			if(output.equals("")) {
				output = "" + obj;
			} else {
				output = output + separator + obj;
			}
		}
		
		return output;
	}
	
}
