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

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MathUtil {
	private MathUtil() {
		// do nothing
	}

	public static Double power(Object number, Object power) {
		if(number == null) {
			Assert.fail("Number should not be null.");
		}
		
		if(power == null) {
			Assert.fail("Power value should not be null.");
		}
		
		String num = "" + number;
		String pow = "" + power;
		
		Double result = Math.pow(Double.valueOf(num), Double.valueOf(pow));
		return result;
	}
	
	public static Object abs(Object number) {
		if(number == null) {
			Assert.fail("Number should not be null.");
		}
		
		String num = "" + number;
		
		if(num.contains(".")) {
			Double result = Math.abs(Double.valueOf(num));
			return result;
		} else {
			Long result = Math.abs(Long.valueOf(num));
			return result;
		}
	}
	
	public static Object negate(Object number) {
		if(number == null) {
			Assert.fail("Number should not be null.");
		}
		
		String num = "" + number;
		
		if(num.contains(".")) {
			Double result = 0.0D - Double.valueOf(num);
			return result;
		} else {
			Long result = 0L - Long.valueOf(num);
			return result;
		}
	}
	
	public static Double floor(Object number) {
		if(number == null) {
			Assert.fail("Number should not be null.");
		}
		
		String num = "" + number;
		
		Double result = Math.floor(Double.valueOf(num));
		return result;
	}
	
	public static Double ceil(Object number) {
		if(number == null) {
			Assert.fail("Number should not be null.");
		}
		
		String num = "" + number;
		
		Double result = Math.ceil(Double.valueOf(num));
		return result;
	}
	
	public static Object max(Object number1, Object number2) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(number2 == null) {
			Assert.fail("Number2 should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + number2;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Math.max(Double.valueOf(num1), Double.valueOf(num2));
			return result;
		} else {
			Long result = Math.max(Long.valueOf(num1), Long.valueOf(num2));
			return result;
		}
	}
	
	public static Object min(Object number1, Object number2) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(number2 == null) {
			Assert.fail("Number2 should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + number2;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Math.min(Double.valueOf(num1), Double.valueOf(num2));
			return result;
		} else {
			Long result = Math.min(Long.valueOf(num1), Long.valueOf(num2));
			return result;
		}
	}
	
	public static Object add(Object number1, Object number2) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(number2 == null) {
			Assert.fail("Number2 should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + number2;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Double.valueOf(num1) + Double.valueOf(num2);
			return result;
		} else {
			Long result = Long.valueOf(num1) + Long.valueOf(num2);
			return result;
		}
	}
	
	public static Object substract(Object number1, Object subtrahend) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(subtrahend == null) {
			Assert.fail("Subtrahend should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + subtrahend;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Double.valueOf(num1) - Double.valueOf(num2);
			return result;
		} else {
			Long result = Long.valueOf(num1) - Long.valueOf(num2);
			return result;
		}
	}
	
	public static Object multiply(Object number1, Object number2) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(number2 == null) {
			Assert.fail("Number2 should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + number2;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Double.valueOf(num1) * Double.valueOf(num2);
			return result;
		} else {
			Long result = Long.valueOf(num1) * Long.valueOf(num2);
			return result;
		}
	}
	
	public static Object divide(Object number1, Object divisor) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(divisor == null) {
			Assert.fail("Divisor should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + divisor;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Double.valueOf(num1) / Double.valueOf(num2);
			return result;
		} else {
			Long result = Long.valueOf(num1) / Long.valueOf(num2);
			return result;
		}
	}
	
	public static Object modulus(Object number1, Object number2) {
		if(number1 == null) {
			Assert.fail("Number1 should not be null.");
		}
		
		if(number2 == null) {
			Assert.fail("Number2 should not be null.");
		}
		
		String num1 = "" + number1;
		String num2 = "" + number2;
		
		if(num1.contains(".") || num2.contains(".")) {
			Double result = Double.valueOf(num1) % Double.valueOf(num2);
			return result;
		} else {
			Long result = Long.valueOf(num1) % Long.valueOf(num2);
			return result;
		}
	}

}
