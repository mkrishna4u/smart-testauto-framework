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
package org.uitnet.testing.smartfwk.local_machine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
	
	public static List<String> listFiles(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String fileNamePrefix, String fileExtension) {
		List<String> fileNames = new LinkedList<>();
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			fs.forEach((path) -> {
				String fileName = path.toFile().getName();
				if (StringUtil.isTextMatchedWithExpectedValue(fileName, fileNamePrefix,
						fileNameMatchMechanism) && (StringUtil.isEmptyAfterTrim(fileExtension) ? true : fileName.endsWith(fileExtension))) {
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
	
	public static List<String> listAllDirectoriesName(String absoluteLocalPath) {
		List<String> fileNames = new LinkedList<>();
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			fs.forEach((path) -> {
				File f = path.toFile();
				if(f.isDirectory()) {
					fileNames.add(f.getName());
				}
			});
		} catch (Exception e) {
			Assert.fail(
					"Failed to list directories from local '" + absoluteLocalPath + "' directory. Reason: " + e.getMessage(),
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
	
	public static List<String> deleteFiles(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String fileNamePrefix, String fileExtension) {
		List<String> fileNames = new LinkedList<>();
		try (Stream<Path> fs = Files.list(Path.of(absoluteLocalPath))) {
			fs.forEach((path) -> {
				String fileName = path.toFile().getName();
				if (StringUtil.isTextMatchedWithExpectedValue(fileName, fileNamePrefix,
						fileNameMatchMechanism) && (StringUtil.isEmptyAfterTrim(fileExtension) ? true : fileName.endsWith(fileExtension))) {
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

	public static void createDirectoriesIfNotExist(String absoluteDirectoryPath) {
		try {
			if (absoluteDirectoryPath != null) {
				if (!new File(absoluteDirectoryPath).exists()) {
					Files.createDirectories(Path.of(absoluteDirectoryPath));
				}
			}
		} catch (Exception e) {
			Assert.fail("Failed to create '" + absoluteDirectoryPath + "' directory. Reason: " + e.getMessage(), e);
		}
	}
	
	public static void copyFileNoOverwrite(String filePath, String absoluteLocalPath,
			String newfileName) {

		try (InputStream resIS = new FileInputStream(new File(filePath))) {
			createDirectoriesIfNotExist(absoluteLocalPath);
			String file = absoluteLocalPath + File.separator + newfileName;
			if (!new File(file).exists()) {
				Files.copy(resIS, Path.of(file), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			Assert.fail("Failed to copy '" + filePath + "' file on '" + absoluteLocalPath
					+ "' location. Reason: " + e.getMessage(), e);
		}
	}
	
	public static void copyFileWithOverwrite(String filePath, String absoluteLocalPath,
			String newfileName, boolean shouldBackupOldFile) {

		if(shouldBackupOldFile) {
			backupFileIfExists(absoluteLocalPath, newfileName);
		}
		
		try (InputStream resIS = new FileInputStream(new File(filePath))) {
			createDirectoriesIfNotExist(absoluteLocalPath);
			String file = absoluteLocalPath + File.separator + newfileName;
			Files.copy(resIS, Path.of(file), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			Assert.fail("Failed to copy '" + filePath + "' file on '" + absoluteLocalPath
					+ "' location. Reason: " + e.getMessage(), e);
		}
	}
	
	public static void copyDirectoryRecursively(String directory, String targetDir, boolean shouldBackupOldFiles) {
		try {
			if(directory == null) { return; }
			String[] files2 = new File(directory).list();
			
			Set<String> filesSet = new TreeSet<>();
			if(files2 != null) {
				for(String f : files2) {
					filesSet.add(directory + File.separator + f);
				}
			}
			
//			/System.out.println(filesSet);
			
			if(filesSet.size() > 0) {
				createDirectoriesIfNotExist(targetDir);
			}
			
			File file1;
			for(String filePath : filesSet) {
				file1 = new File(filePath);
				if(file1.isDirectory()) {
					copyDirectoryRecursively(directory + "/" + file1.getName(), targetDir+ "/" + file1.getName(), shouldBackupOldFiles);
				} else {
					if(shouldBackupOldFiles) {
						backupFileIfExists(targetDir, file1.getName());
					}
					copyFileNoOverwrite(directory + "/" + file1.getName() , targetDir, file1.getName());
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Failed to copy resources from '" + directory + "' location to new location '" + targetDir + "'. Reason: " + e.getMessage(), e);
		}
	}
	
	public static void replaceTextInFile(String absoluteFilePath, String textOrRegEx, String newText) {
		try {
			Path path = Paths.get(absoluteFilePath);
			Charset charset = StandardCharsets.UTF_8;
	
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(textOrRegEx, newText);
			Files.write(path, content.getBytes(charset));
		}catch(Exception ex) {
			Assert.fail("Failed to replace text in '" + absoluteFilePath + "' file.", ex);
		}
	}
	
	public static void backupFileIfExists(String path, String fileName) {
		File f = new File(path + File.separator + fileName);
		if(f.exists()) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss");
			String timestamp = sdf.format(Calendar.getInstance().getTime());
			String backupFileName = path + File.separator + fileName + "." + timestamp + ".bak";
			f.renameTo(new File(backupFileName));
		}
	}

	public static void copyClassResoucesNoOverwrite(String classResourceFilePath, String absoluteLocalPath,
			String newfileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try (InputStream resIS = classLoader.getResourceAsStream(classResourceFilePath)) {
			createDirectoriesIfNotExist(absoluteLocalPath);
			String file = absoluteLocalPath + File.separator + newfileName;
			if (!new File(file).exists()) {
				Files.copy(resIS, Path.of(file), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			Assert.fail("Failed to copy '" + classResourceFilePath + "' file on '" + absoluteLocalPath
					+ "' location. Reason: " + e.getMessage(), e);
		}
	}

	public static List<ResourceInfo> listClassResources(String classResourceDirectory) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<ResourceInfo> fileNames = new LinkedList<>();
		try {
			URL resource = classLoader.getResource(classResourceDirectory);
			if(resource == null) { return fileNames; }
			File file = new File(resource.toURI());
			File[] files = file.listFiles();
			
			for(File file1 : files) {
				if(file1.isDirectory()) {
					fileNames.add(new ResourceInfo(file1.getName(), ResourceType.DIRECTORY));
				} else {
					fileNames.add(new ResourceInfo(file1.getName(), ResourceType.FILE));
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Failed to find resources at resource location '" + classResourceDirectory + "'. Reason: " + e.getMessage(), e);
		}
		
		return fileNames;
	}
	
	public static void copyResourcesRecursively(String resourceDir, String targetDir, boolean shouldBackupOldFiles) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			URL resource = classLoader.getResource(resourceDir);
			if(resource == null) { return; }
			File file = new File(resource.toURI());
			File[] files = file.listFiles();
			if(files != null && files.length > 0) {
				createDirectoriesIfNotExist(targetDir);
			}
			
			for(File file1 : files) {
				if(file1.isDirectory()) {
					copyResourcesRecursively(resourceDir + "/" + file1.getName(), targetDir+ "/" + file1.getName(), shouldBackupOldFiles);
				} else {
					if(shouldBackupOldFiles) {
						backupFileIfExists(targetDir, file1.getName());
					}
					copyClassResoucesNoOverwrite(resourceDir + "/" + file1.getName() , targetDir, file1.getName());
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Failed to copy resources from '" + resourceDir + "' location to new location '" + targetDir + "'. Reason: " + e.getMessage(), e);
		}
	}
}
