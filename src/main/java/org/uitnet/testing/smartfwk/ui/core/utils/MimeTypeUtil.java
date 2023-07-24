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

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MimeTypeUtil {
	private static MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
	private static List<String> binaryFileExtensions = new LinkedList<>();

	static {
		binaryFileExtensions.add(".bin");
		binaryFileExtensions.add(".pdf");
		binaryFileExtensions.add(".exe");
		binaryFileExtensions.add(".doc");
		binaryFileExtensions.add(".docx");
		binaryFileExtensions.add(".xls");
		binaryFileExtensions.add(".xlsx");
		binaryFileExtensions.add(".ppt");
		binaryFileExtensions.add(".pptx");
		binaryFileExtensions.add(".zip");
		binaryFileExtensions.add(".gzip");
		binaryFileExtensions.add(".rar");
		binaryFileExtensions.add(".gz");
		binaryFileExtensions.add(".tgz");
		binaryFileExtensions.add(".tar");
		binaryFileExtensions.add(".odt");
		binaryFileExtensions.add(".ods");
		binaryFileExtensions.add(".dll");
		binaryFileExtensions.add(".jar");
		binaryFileExtensions.add(".war");
	}

	private MimeTypeUtil() {

	}

	/**
	 * Returns the file extension based on the media type.
	 * 
	 * @param mediaType
	 * @return
	 */
	public static String getFileExtensionFromMediaType(String mediaType) {
		try {
			MimeType mimeType = mimeTypes.getRegisteredMimeType(mediaType);
			if (mimeType == null) {
				return null;
			}

			return mimeType.getExtension();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns the extension of the file if that media type belongs to binary file.
	 * 
	 * @param mediaType
	 * @return
	 */
	public static String getBinaryFileExtension(String mediaType) {
		if (mediaType == null) {
			return null;
		}
		if (mediaType.toLowerCase().startsWith("image/") || mediaType.toLowerCase().startsWith("video/")
				|| mediaType.toLowerCase().startsWith("audio/")) {
			return getFileExtensionFromMediaType(mediaType);
		}

		String extn = getFileExtensionFromMediaType(mediaType);

		if (extn != null && binaryFileExtensions.contains(extn)) {
			return extn;
		}

		return null;
	}

	/**
	 * Detects the media type for file.
	 * 
	 * @param absluteFilePath
	 * @return
	 */
	public static String findMediaTypeForFile(String absluteFilePath) {
		try {
			String mType = new Tika().detect(new File(absluteFilePath));
			if (mType != null) {
				return mType;
			}
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}
}
