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

/**
 * 
 * @author Madhav Krishna
 *
 */
public class FontUtil {

	private FontUtil() {
		// do nothing
	}
	
	/**
	 * Used to compute font size in REM unit.
	 * @param fontSize - font size either in px or rem. Examples: 10px, 0.5rem 
	 * @return the calculated REM size.
	 */
	public static double computeFontSizeInREM(String fontSize) {
		if(StringUtil.isEmptyAfterTrim(fontSize)) {
			return 0;
		}
		
		fontSize = fontSize.trim().toLowerCase();
		double remValue = 0;
		if(fontSize.endsWith("px")) {
			// Note: 1px = 0.0625 REM
			String str = fontSize.replaceAll("[a-zA-Z ]", "");
			if(!StringUtil.isEmptyAfterTrim(str)) {
				double px = Double.parseDouble(str);
				remValue = px * 0.0625;
			}
		} else if(fontSize.endsWith("rem")) {
			String str = fontSize.replaceAll("[a-zA-Z ]", "");
			if(!StringUtil.isEmptyAfterTrim(str)) {
				remValue = Double.parseDouble(str);
			}
		}
		
		return remValue;
	}
	
//	public static void main(String[] args) {
//		try {			
//			double ratio = ColorContrastUtil.calcContrastRatio("#468847", "#DFF0D8");
//			System.out.println("ratio: " + ratio);
//			ratio = ColorContrastUtil.calcContrastRatio("hsla(120, 32%, 40%)", "hsl(102, 44%, 89%)");
//			System.out.println("ratio: " + ratio);
//			ratio = ColorContrastUtil.calcContrastRatio("rgb(70, 136, 71)", "rgb(223, 240, 216)");
//			System.out.println("ratio: " + ratio);
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
