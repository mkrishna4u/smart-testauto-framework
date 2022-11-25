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

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;

/**
 * Used to perform touch actions and gestures using touch operations.
 * 
 * @author Madhav Krishna
 *
 */
public class TouchActionsUtil {
	private final static PointerInput FINGER_1 = new PointerInput(Kind.TOUCH, "finger1");
	private final static PointerInput FINGER_2 = new PointerInput(Kind.TOUCH, "finger2");
	
	public static void tap(WebDriver webDriver, Point point, int holdDurationInMs) {
		Sequence tap = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), point.getX(), point.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(new Pause(FINGER_1, Duration.ofMillis(holdDurationInMs)))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(tap));
	}
	
	public static void tapOnElement(WebDriver webDriver, WebElement elem, int holdDurationInMs) {
		Point point = elem.getLocation();
		tap(webDriver, point, holdDurationInMs);
	}
	
	public static void doubleTap(WebDriver webDriver, Point point, int pauseDurationInMs) {
		Sequence tap = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), point.getX(), point.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(new Pause(FINGER_1, Duration.ofMillis(100)))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()))
                .addAction(new Pause(FINGER_1, Duration.ofMillis(pauseDurationInMs)))
				.addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
		        .addAction(new Pause(FINGER_1, Duration.ofMillis(100)))
		        .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(tap));
	}
	
	public static void doubleTapOnElement(WebDriver webDriver, WebElement elem, int pauseDurationInMs) {
		Point point = elem.getLocation();
		doubleTap(webDriver, point, pauseDurationInMs);
	}
	
	/**
	 * Used to tap N times.
	 * @param webDriver
	 * @param point
	 * @param n
	 * @param pauseDurationInMs
	 */
	public static void nTaps(WebDriver webDriver, Point point, int n, int pauseDurationInMs) {
		Sequence tap = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), point.getX(), point.getY()));
                
		for(int i = 1; i < n; i++) {
			tap.addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
            .addAction(new Pause(FINGER_1, Duration.ofMillis(100)))
            .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()))
            .addAction(new Pause(FINGER_1, Duration.ofMillis(pauseDurationInMs)));
		}
        
		((Interactive) webDriver).perform(Arrays.asList(tap));
	}
	
	public static void nTapsOnElement(WebDriver webDriver, WebElement elem, int n, int pauseDurationInMs) {
		Point point = elem.getLocation();
		nTaps(webDriver, point, n, pauseDurationInMs);
	}
	
	public static void swipe(WebDriver webDriver, Point start, Point end, int swipeDurationInMs) {
		Sequence swipe = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(swipeDurationInMs), PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipe));
	}
	
	public static void swipeUpFromElement(WebDriver webDriver, WebElement elem, int offset, int swipeDurationInMs) {
		Point start = elem.getLocation();
		Point end = new Point(start.getX(), start.getY() - offset);
		swipe(webDriver, start, end, swipeDurationInMs);
	}
	
	public static void swipeDownFromElement(WebDriver webDriver, WebElement elem, int offset, int swipeDurationInMs) {
		Point start = elem.getLocation();
		Point end = new Point(start.getX(), start.getY() + offset);
		swipe(webDriver, start, end, swipeDurationInMs);
	}
	
	public static void swipeRightFromElement(WebDriver webDriver, WebElement elem, int offset, int swipeDurationInMs) {
		Point start = elem.getLocation();
		Point end = new Point(start.getX() + offset, start.getY());
		swipe(webDriver, start, end, swipeDurationInMs);
	}
	
	public static void swipeLeftFromElement(WebDriver webDriver, WebElement elem, int offset, int swipeDurationInMs) {
		Point start = elem.getLocation();
		Point end = new Point(start.getX() - offset, start.getY());
		swipe(webDriver, start, end, swipeDurationInMs);
	}
	
	public static void zoomIn(WebDriver webDriver, int numPixels, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point finger1p = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2) - 10, leftTopCornerPosition.getY() + dimension.getHeight() / 2);
		Point finger2p = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2) + 10, leftTopCornerPosition.getY() + dimension.getHeight() / 2);
		
		Sequence swipeFinger1 = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), finger1p.getX(), finger1p.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), finger1p.getX() - numPixels, finger1p.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		Sequence swipeFinger2 = new Sequence(FINGER_2, 1)
                .addAction(FINGER_2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), finger2p.getX(), finger2p.getY()))
                .addAction(FINGER_2.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_2.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), finger2p.getX() + numPixels, finger2p.getY()))
                .addAction(FINGER_2.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipeFinger1, swipeFinger2));
	}
	
	public static void zoomOut(WebDriver webDriver, int numPixels, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point finger1p = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2) - 10 - numPixels, leftTopCornerPosition.getY() + dimension.getHeight() / 2);
		Point finger2p = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2) + 10 + numPixels, leftTopCornerPosition.getY() + dimension.getHeight() / 2);
		
		Sequence swipeFinger1 = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), finger1p.getX(), finger1p.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), finger1p.getX() + numPixels, finger1p.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		Sequence swipeFinger2 = new Sequence(FINGER_2, 1)
                .addAction(FINGER_2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), finger2p.getX(), finger2p.getY()))
                .addAction(FINGER_2.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_2.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), finger2p.getX() - numPixels, finger2p.getY()))
                .addAction(FINGER_2.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipeFinger1, swipeFinger2));
	}
	
	public static void scrollUp(WebDriver webDriver, int percentScroll, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point start = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2), leftTopCornerPosition.getY() + dimension.getHeight());
		Point end = new Point(start.getX(), start.getY() - ((dimension.getHeight() * percentScroll) / 100));
		
		Sequence swipe = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipe));
	}
	
	public static void scrollDown(WebDriver webDriver, int percentScroll, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point start = new Point(leftTopCornerPosition.getX() + (dimension.getWidth() / 2), leftTopCornerPosition.getY());
		Point end = new Point(start.getX(), start.getY() + ((dimension.getHeight() * percentScroll) / 100));
		
		Sequence swipe = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipe));
	}
	
	public static void scrollRight(WebDriver webDriver, int percentScroll, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point start = new Point(leftTopCornerPosition.getX(), leftTopCornerPosition.getY() + (dimension.getHeight() / 2));
		Point end = new Point(start.getX() + ((dimension.getWidth() * percentScroll) / 100), start.getY());
		
		Sequence swipe = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipe));
	}
	
	public static void scrollLeft(WebDriver webDriver, int percentScroll, int durationInMs) {
		Dimension dimension = webDriver.manage().window().getSize();
		Point leftTopCornerPosition = webDriver.manage().window().getPosition();
		
		Point start = new Point(leftTopCornerPosition.getX() + dimension.getWidth(), leftTopCornerPosition.getY() + (dimension.getHeight() / 2));
		Point end = new Point(start.getX() - ((dimension.getWidth() * percentScroll) / 100), start.getY());
		
		Sequence swipe = new Sequence(FINGER_1, 1)
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(FINGER_1.createPointerDown(MouseButton.LEFT.asArg()))
                .addAction(FINGER_1.createPointerMove(Duration.ofMillis(durationInMs), PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(FINGER_1.createPointerUp(MouseButton.LEFT.asArg()));
		((Interactive) webDriver).perform(Arrays.asList(swipe));
	}
	
}
