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
package org.uitnet.testing.smartfwk.validator;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.util.TreeMap;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.PayloadType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DownloadedFileValidator {
	private DownloadedFileValidator() {
		// do nothing
	}

	public static String getDownloadLocation() {
		return TestConfigManager.getInstance().getDownloadLocation();
	}

	public static void validateBrowserFileDownloaded(String expectedStartsWithText, String expectedFileExtension, boolean deleteLatestFilteredFileAfterValidation) {
		TreeMap<Long, String> filteredFiles = new TreeMap<>();
		try {
			Files.list(new File(getDownloadLocation()).toPath()).forEach((p) -> {
				String fName = p.toFile().getName();
				if(fName.startsWith(expectedStartsWithText) && fName.endsWith(expectedFileExtension)) {
					filteredFiles.put(p.toFile().lastModified(), fName);
				}
			});
			
			if(filteredFiles.size() == 0) {
				Assert.fail("No file found at downloads location '" + getDownloadLocation() + "' that starts with '" + expectedStartsWithText + "' and ends with '" + expectedFileExtension + "' text.");
			}
		} catch (Exception ex) {

		} catch (Error e) {
			throw e;
		} finally {
			if(deleteLatestFilteredFileAfterValidation && filteredFiles.size() > 0) {
				new File(filteredFiles.lastEntry().getValue()).delete();
			}
		}
	}

	public static void validateFileDownloaded(String expectedFileName, HttpResponse httpResponse,
			TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation) {
		assertNotNull(httpResponse, "HTTP response should not be null.");
		if(httpResponse.getPayLoadType() == null || httpResponse.getPayLoadType() != PayloadType.FILE) {
			fail("HTTP response payload type must be of FILE type.");
		}
		
		validateFileDownloaded(expectedFileName, httpResponse.getPayload(), httpResponse.getFilePath(),
				fileNameMatchMechanism, deleteAfterValidation);
	}

	public static void validateFileDownloaded(String expectedFileName, String actualFileName,
			String actualFileAbsolutePath, TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation) {
		TextMatchMechanism fnMatchMech = null;
		if (fileNameMatchMechanism == null) {
			fnMatchMech = TextMatchMechanism.exactMatchWithExpectedValue;
		}

		Assert.assertNotNull(actualFileName, "Filename cannot be null.");
		Assert.assertNotNull(actualFileName.trim().equals(""), "Filename cannot be empty.");
		Assert.assertNotNull(actualFileAbsolutePath,
				"Absolute file path of expected file '" + expectedFileName + "' cannot be null.");

		File file = new File(actualFileAbsolutePath);

		try {
			switch (fnMatchMech) {
			case exactMatchWithExpectedValue:
				if (!actualFileName.equals(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName + " does not match with expected filename '"
							+ expectedFileName + "'.");
				}
				break;
			case containsExpectedValue:
				if (!actualFileName.contains(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName + " does not contain expected text '"
							+ expectedFileName + "'.");
				}
				break;
			case endsWithExpectedValue:
				if (!actualFileName.endsWith(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName + " does not ends with expected text '"
							+ expectedFileName + "'.");
				}
				break;
			case startsWithExpectedValue:
				if (!actualFileName.startsWith(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName + " does not starts with expected text '"
							+ expectedFileName + "'.");
				}
				break;
			case exactMatchWithExpectedValueWithRemovedWhiteSpace:
				if (!actualFileName.trim().equals(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName + " does not match with expected filename '"
							+ expectedFileName + "'.");
				}
				break;
			case matchWithRegularExpression:
				if (!actualFileName.matches(expectedFileName)) {
					Assert.fail("Actual filename '" + actualFileName
							+ " does not match with expected regular expression '" + expectedFileName + "'.");
				}
				break;

			default:
				break;
			}

			if (!file.exists()) {
				Assert.fail("File not found at '" + actualFileAbsolutePath + "' location.");
			}

		} finally {
			if (deleteAfterValidation) {
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}
}
