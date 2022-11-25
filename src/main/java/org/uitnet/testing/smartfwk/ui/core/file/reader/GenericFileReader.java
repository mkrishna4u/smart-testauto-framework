package org.uitnet.testing.smartfwk.ui.core.file.reader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.validator.FileContentsValidator;

public class GenericFileReader {
	private String filePath;
	
	public GenericFileReader(String filePath) {
		this.filePath = Locations.getProjectRootDir() + File.separator + filePath;;
	}
	
	public String readDataAsString() {
		try {
			return Files.readString(Path.of(filePath));
		} catch(Exception ex) {
			Assert.fail("Failed to read '" + filePath + "' file contents.", ex);
		}
		return null;
	}
	
	public byte[] readDataAsBytes() {
		try {
			return Files.readAllBytes(Path.of(filePath));
		} catch(Exception ex) {
			Assert.fail("Failed to read '" + filePath + "' file contents.", ex);
		}
		return null;
	}
	
	public List<String> readDataAsLines() {
		try {
			return Files.readAllLines(Path.of(filePath));
		} catch(Exception ex) {
			Assert.fail("Failed to read '" + filePath + "' file contents.", ex);
		}
		return null;
	}
	
	public FileContentsValidator getFileContentsValidator(String filePath) {
		return new FileContentsValidator(filePath, false);
	}
	
	public FileContentsValidator getFileContentsValidator(String filePath, boolean shouldPrintFileContentsOnConsole) {
		return new FileContentsValidator(filePath, shouldPrintFileContentsOnConsole);
	}
	
	public FileContentsValidator getFileContentsValidator(String filePath, boolean shouldPrintFileContentsOnConsole, String ocrLanguage) {
		return new FileContentsValidator(filePath, shouldPrintFileContentsOnConsole, ocrLanguage);
	}
}
