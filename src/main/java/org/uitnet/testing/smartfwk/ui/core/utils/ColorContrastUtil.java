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

import java.awt.Color;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ColorContrastUtil {
	private static final double RED_FACTOR = 0.2126;
    private static final double GREEN_FACTOR = 0.7152;
    private static final double BLUE_FACTOR = 0.0722;
    private static final double CONTRAST_FACTOR = 0.05;
    private static final double RGB_MAX_VALUE = 255;
    private static final double RSGB_FACTOR = 0.03928;
    private static final double LUMINANCE_INF = 12.92;
    private static final double LUMINANCE_SUP_CONST = 0.055;
    private static final double LUMINANCE_SUP_CONST2 = 1.055;
    private static final double LUMINANCE_EXP = 2.4;

	private ColorContrastUtil() {
		// do nothing
	}
			
	public static boolean isContrastValid(String foregroundColor, String backgroundColor, double coefficientLevel) {
		Color fgColor = parseColor(foregroundColor);
		Color bgColor = parseColor(backgroundColor);
        Double actualContrastLevel = calcContrastRatio(fgColor, bgColor); 
        return (actualContrastLevel >= coefficientLevel);
    }
	
	public static double calcContrastRatio(String foregroundColor, String backgroundColor) {
		Color fgColor = parseColor(foregroundColor);
		Color bgColor = parseColor(backgroundColor);
		System.out.println("fgColor: " + fgColor + "; bgColor: " + bgColor);
		return calcContrastRatio(fgColor, bgColor);
	}
	
    public static double calcContrastRatio(Color foregroundColor, Color backgroundColor) {
        double fgLuminosity = calcLuminosity(foregroundColor);
        double bgLuminosity = calcLuminosity(backgroundColor);
        if (fgLuminosity > bgLuminosity) {
            return calcContrast(fgLuminosity, bgLuminosity);
        } else {
            return calcContrast(bgLuminosity, fgLuminosity);
        }
    }
	
	public static Color parseColor(String colorAsStr) {
		if(StringUtil.isEmptyAfterTrim(colorAsStr)) {
			return null;
		}
		
		colorAsStr = colorAsStr.trim().toLowerCase();
		Color color = null;
		int red = -1;
		int green = -1;
		int blue = -1;
		//float alpha = -1;
		float degree = -1;
		float saturation = -1;
		float lightness = -1;
		if(colorAsStr.startsWith("#") && colorAsStr.length() > 6) {
			red = Integer.parseInt(colorAsStr.substring(1, 3), 16);
			green = Integer.parseInt(colorAsStr.substring(3, 5), 16);
			blue = Integer.parseInt(colorAsStr.substring(5, 7), 16);
			color = new Color(red, green, blue);
		} else if(colorAsStr.startsWith("rgba")) {
			String[] colors = colorAsStr.replaceAll("[a-zA-Z() ]", "").split(",");
			red = Integer.parseInt(colors[0]);
			green = Integer.parseInt(colors[1]);
			blue = Integer.parseInt(colors[2]);
//			alpha = colors[3].endsWith("%") ? Float.parseFloat(colors[3].substring(0, colors[3].length()-1)) / 100 : 
//				Float.parseFloat(colors[3].substring(0, colors[3].length()));
			color = new Color(red, green, blue);
		} else if(colorAsStr.startsWith("rgb")) {
			String[] colors = colorAsStr.replaceAll("[a-zA-Z() ]", "").split(",");
			red = Integer.parseInt(colors[0]);
			green = Integer.parseInt(colors[1]);
			blue = Integer.parseInt(colors[2]);
			color = new Color(red, green, blue);
		} else if(colorAsStr.startsWith("hsla")) {
			String[] colors = colorAsStr.replaceAll("[a-zA-Z() ]", "").split(",");
			degree = Float.parseFloat(colors[0]);
			saturation = colors[1].endsWith("%") ? Float.parseFloat(colors[1].substring(0, colors[1].length()-1)) / 100 : 
				Float.parseFloat(colors[1].substring(0, colors[1].length()));
			lightness = colors[2].endsWith("%") ? Float.parseFloat(colors[2].substring(0, colors[2].length()-1)) / 100 : 
				Float.parseFloat(colors[2].substring(0, colors[2].length()));
//			alpha = colors[3].endsWith("%") ? Float.parseFloat(colors[3].substring(0, colors[3].length()-1)) / 100 : 
//				Float.parseFloat(colors[3].substring(0, colors[3].length()));
			int[] rgb = hslToRgb(degree/360, saturation, lightness);
			color = new Color(rgb[0], rgb[1], rgb[2]);
			//color = new Color(Float.valueOf(color.getRed())/255, Float.valueOf(color.getGreen())/255, Float.valueOf(color.getBlue())/255, alpha);
		} else if(colorAsStr.startsWith("hsl")) {
			String[] colors = colorAsStr.replaceAll("[a-zA-Z() ]", "").split(",");
			degree = Float.parseFloat(colors[0]);
			saturation = colors[1].endsWith("%") ? Float.parseFloat(colors[1].substring(0, colors[1].length()-1)) / 100 : 
				Float.parseFloat(colors[1].substring(0, colors[1].length()));
			lightness = colors[2].endsWith("%") ? Float.parseFloat(colors[2].substring(0, colors[2].length()-1)) / 100 : 
				Float.parseFloat(colors[2].substring(0, colors[2].length()));
			int[] rgb = hslToRgb(degree/360, saturation, lightness);
			color = new Color(rgb[0], rgb[1], rgb[2]);
		}
		
		return color;
	}
	
	public static double calcDistanceColor(final Color foregroundColor, final Color backgroundColor) {
        int redFg = foregroundColor.getRed();
        int redBg = backgroundColor.getRed();
        int greenBg = backgroundColor.getGreen();
        int greenFg = foregroundColor.getGreen();
        int blueFg = foregroundColor.getBlue();
        int blueBg = backgroundColor.getBlue();
        return (Math.sqrt(Math.pow(redFg - redBg, 2) + Math.pow(greenFg - greenBg, 2) + Math.pow(blueFg - blueBg, 2)));
    }

    private static double calcContrast(double lighter, double darker) {
        return (Double.valueOf(((lighter + CONTRAST_FACTOR) / (darker + CONTRAST_FACTOR))));
    }

    public static double calcLuminosity(Color color) {
        double luminosity =
                calclComposantValue(color.getRed()) * RED_FACTOR
                + calclComposantValue(color.getGreen()) * GREEN_FACTOR
                + calclComposantValue(color.getBlue()) * BLUE_FACTOR;

        return luminosity;
    }

    private static double calclComposantValue(double composant) {
        double rsgb = composant / RGB_MAX_VALUE;
        if (rsgb <= RSGB_FACTOR) {
            return rsgb / LUMINANCE_INF;
        } else {
            return Math.pow(((rsgb + LUMINANCE_SUP_CONST) / LUMINANCE_SUP_CONST2), LUMINANCE_EXP);
        }
    }
    
    private static int[] hslToRgb(float h, float s, float l){
        float r, g, b;

        if (s == 0f) {
            r = g = b = l; // achromatic
        } else {
            float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
            float p = 2 * l - q;
            r = hueToRgb(p, q, h + 1f/3f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1f/3f);
        }
        int[] rgb = {(int) (r * 255), (int) (g * 255), (int) (b * 255)};
        return rgb;
    }

    private static float hueToRgb(float p, float q, float t) {
        if (t < 0f)
            t += 1f;
        if (t > 1f)
            t -= 1f;
        if (t < 1f/6f)
            return p + (q - p) * 6f * t;
        if (t < 1f/2f)
            return q;
        if (t < 2f/3f)
            return p + (q - p) * (2f/3f - t) * 6f;
        return p;
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
