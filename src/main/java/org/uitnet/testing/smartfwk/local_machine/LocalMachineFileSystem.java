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
package org.uitnet.testing.smartfwk.local_machine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class LocalMachineFileSystem {
	private LocalMachineFileSystem() {
		// do nothing
	}

	public static List<String> listFiles(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String expectedValue) {
		List<String> fileNames = new LinkedList<>();
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			fs.forEach((path) -> {
				if (StringUtil.isTextMatchedWithExpectedValue(path.toFile().getName(), expectedValue,
						fileNameMatchMechanism)) {
					fileNames.add(path.toFile().getAbsolutePath());
				}
			});
		} catch (Exception e) {
			Assert.fail(
					"Failed to list files from local '" + absoluteLocalPath + "' directory. Reason: " + e.getMessage(),
					e);
		}
		return fileNames;
	}

	public static List<String> deleteFiles(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String expectedValue) {
		List<String> fileNames = new LinkedList<>();
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			fs.forEach((path) -> {
				if (StringUtil.isTextMatchedWithExpectedValue(path.toFile().getName(), expectedValue,
						fileNameMatchMechanism)) {
					fileNames.add(path.toFile().getAbsolutePath());
					try {
						Files.delete(path);
					} catch (Exception e1) {
						throw new RuntimeException(e1.getMessage());
					}
				}
			});
		} catch (Exception e) {
			Assert.fail("Failed to delete files from local '" + absoluteLocalPath + "' directory. Reason: "
					+ e.getMessage(), e);
		}
		return fileNames;
	}

	public static String copyFileAsNewName(String absoluteLocalFilePath, String newFileName) {
		try {
			String fileParentDir = extractFileParentDirectory(absoluteLocalFilePath);
			String newFilePath = fileParentDir + File.separator + newFileName;
			Files.copy(Path.of(absoluteLocalFilePath), Path.of(newFilePath), StandardCopyOption.REPLACE_EXISTING);
			return newFilePath;
		} catch (Exception e) {
			Assert.fail("Fail to copy '" + absoluteLocalFilePath + "' file to new name as '" + newFileName + "'", e);
		}
		return null;
	}

	public static void validateFilePresent(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String expectedValue) {
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			boolean found = false;

			Iterator<Path> itr = fs.iterator();
			while (itr.hasNext()) {
				Path path = itr.next();
				if (StringUtil.isTextMatchedWithExpectedValue(path.toFile().getName(), expectedValue,
						fileNameMatchMechanism)) {
					found = true;
					break;
				}
			}

			if (!found) {
				Assert.fail("No file match found at '" + absoluteLocalPath + "' location using FileNameMatchMechanism: "
						+ fileNameMatchMechanism + " and expectedFileName: " + expectedValue);
			}
		} catch (Exception e) {
			Assert.fail(
					"Failed to check file presence at '" + absoluteLocalPath + "' location. Reason: " + e.getMessage(),
					e);
		}
	}

	public static String extractFileName(String filePath) {
		if (filePath == null) {
			return "";
		}
		filePath = filePath.replace("\\", "/");
		int lastIndex = filePath.lastIndexOf("/");
		if (lastIndex >= 0 && filePath.length() > (lastIndex + 1)) {
			return filePath.substring(lastIndex + 1);
		}
		return null;
	}

	public static String extractFileParentDirectory(String filePath) {
		if (filePath == null) {
			return "";
		}
		filePath = filePath.replace("\\", "/");
		int lastIndex = filePath.lastIndexOf("/");
		if (lastIndex >= 0 && filePath.length() > (lastIndex + 1)) {
			return filePath.substring(0, lastIndex);
		}
		return null;
	}
}
