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

import java.util.Locale;

import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;

/**
 * Used to find the operating system type of the host platform.
 * 
 * @author Madhav Krishna
 *
 */
public class OSDetectorUtil {

	private OSDetectorUtil() {
		// do nothing
	}

	public static PlatformType getHostPlatform() {
		PlatformType ptype = PlatformType.unknown;
		String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

		if (osName == null) {
			return ptype;
		}

		if (osName.contains("windows")) {
			ptype = PlatformType.windows;
		} else if (osName.contains("mac") || osName.contains("darwin")) {
			ptype = PlatformType.mac;
		} else if (osName.contains("android")) {
			ptype = PlatformType.android_mobile;
		} else if (osName.contains("ios")) {
			ptype = PlatformType.ios_mobile;
		} else {
			ptype = PlatformType.linux;
		}

		return ptype;
	}
}
