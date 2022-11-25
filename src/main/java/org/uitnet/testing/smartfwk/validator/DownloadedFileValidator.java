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
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.PayloadType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

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

	public static void validateBrowserFileDownloaded(String expectedStartsWithText, String expectedFileExtension, 
			boolean deleteLatestFilteredFileAfterValidation, int numIterationsToLocateFile) {
		for (int i = 0; i <= numIterationsToLocateFile; i++) {
			try {
				
				validateBrowserFileDownloaded(expectedStartsWithText, expectedFileExtension, deleteLatestFilteredFileAfterValidation);
				break;
			} catch(Exception | Error ex) {
				if (i == numIterationsToLocateFile) {
					throw ex;
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(Exception ex1) {}
		}
	}
	
	public static void validateBrowserFileDownloaded(String expectedStartsWithText, String expectedFileExtension, boolean deleteLatestFilteredFileAfterValidation) {
		TreeMap<Long, String> filteredFiles = new TreeMap<>();
		try {
			Files.list(new File(getDownloadLocation()).toPath()).forEach((p) -> {
				String fName = p.toFile().getName();
				if(fName.startsWith(expectedStartsWithText) && 
						(!StringUtil.isEmptyAfterTrim(expectedFileExtension) && fName.endsWith(expectedFileExtension))) {
					filteredFiles.put(p.toFile().lastModified(), fName);
				}
			});
			
			if(filteredFiles.size() == 0) {
				Assert.fail("No file found at downloads location '" + getDownloadLocation() + "' that starts with '" + expectedStartsWithText + "' and ends with '" + expectedFileExtension + "' text.");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} catch (Error e) {
			throw e;
		} finally {
			if(deleteLatestFilteredFileAfterValidation && filteredFiles.size() > 0) {
				new File(filteredFiles.lastEntry().getValue()).delete();
			}
		}
	}

	public static void validateFileDownloaded(String expectedFileName, HttpResponse httpResponse,
			TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation, int numIterationsToLocateFile) {
		for (int i = 0; i <= numIterationsToLocateFile; i++) {
			try {
				
				validateFileDownloaded(expectedFileName, httpResponse, fileNameMatchMechanism, deleteAfterValidation);	
				break;
			} catch(Exception | Error ex) {
				if (i == numIterationsToLocateFile) {
					throw ex;
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(Exception ex1) {}
		}
	}
	
	public static void validateFileDownloaded(String expectedFileName, HttpResponse httpResponse,
			TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation) {
		assertNotNull(httpResponse, "HTTP response should not be null.");
		if(httpResponse.getPayloadType() == null || httpResponse.getPayloadType() != PayloadType.FILE) {
			fail("HTTP response payload type must be of FILE type.");
		}
		
		validateFileDownloaded(expectedFileName, httpResponse.getPayload(), httpResponse.getFilePath(),
				fileNameMatchMechanism, deleteAfterValidation);
	}

	public static void validateFileDownloaded(String expectedFileName, String actualFileName,
			String actualFileAbsolutePath, TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation, int numIterationsToLocateFile) {
		for (int i = 0; i <= numIterationsToLocateFile; i++) {
			try {
				
				validateFileDownloaded(expectedFileName, actualFileName, actualFileAbsolutePath, fileNameMatchMechanism, deleteAfterValidation);	
				break;
			} catch(Exception | Error ex) {
				if (i == numIterationsToLocateFile) {
					throw ex;
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(2);
			}catch(Exception ex1) {}
		}
	}
	
	public static void validateFileDownloaded(String expectedFileName, String actualFileName,
			String actualFileAbsolutePath, TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation) {
		TextMatchMechanism fnMatchMech = fileNameMatchMechanism;
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
			case startsWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail("Actual filename '" + actualFileName + "' does not starts with expected text '" + expectedFileName
							+ "'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case containsExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not contain expected text '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case endsWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail("Actual filename '" + actualFileName + "' does not ends with expected text '" + expectedFileName
							+ "'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case exactMatchWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not equal to expected filename '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case matchWithRegularExpression:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail("Actual filename '" + actualFileName + "' does not equal to expected regular expression value '"
							+ expectedFileName + "'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case exactMatchWithExpectedValueAfterRemovingSpaces:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not equal to expected filename '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case icStartsWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail("Actual filename '" + actualFileName + "' does not starts with expected text '" + expectedFileName
							+ "'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case icContainsExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not contain expected text '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case icEndsWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail("Actual filename '" + actualFileName + "' does not ends with expected text '" + expectedFileName
							+ "'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;
			case icExactMatchWithExpectedValue:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not equal to expected filename '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
				}
				break;		
			case icExactMatchWithExpectedValueAfterRemovingSpaces:
				if (!DataMatchUtil.matchTextValue(actualFileName, expectedFileName, fnMatchMech)) {
					Assert.fail(
							"Actual filename '" + actualFileName + "' does not equal to expected filename '" + expectedFileName + 
							"'. TextMatchMechanism = " + fnMatchMech.name() + ".");
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
