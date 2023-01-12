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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.testng.Assert;

/**
 * This file is used to validate the file contents in different file formats
 * like .docx, .xlsx. .pdf etc..
 * 
 * Note: tesseract should be installed on the system. Please refer following link to install
 * Tesseract on different platforms: https://tesseract-ocr.github.io/tessdoc/Installation.html
 * 
 * @author Madhav Krishna
 *
 */
public class FileContentsValidator {
	private String filePath;
	private String fileContents;
	private String ocrLanguage;

	public FileContentsValidator(String filePath, boolean shouldPrintFileContentsOnConsole) {
		this.filePath = filePath;		
		ocrLanguage = "eng";
		fileContents = extractFileContents();
		if(shouldPrintFileContentsOnConsole) {
			System.out.println(fileContents);
		}
	}

	public FileContentsValidator(String filePath, boolean shouldPrintFileContentsOnConsole, String ocrLanguage) {
		this.filePath = filePath;
		this.ocrLanguage = ocrLanguage;
		fileContents = extractFileContents();
		if(shouldPrintFileContentsOnConsole) {
			System.out.println(fileContents);
		}
	}

	/**
	 * Validates all keywords are presents, order does not matter.
	 * @param keyWords
	 * @return
	 */
	public FileContentsValidator validateAllKeywordsPresent(String... keyWords) {
		validateAtLeastNKeywordPresent(keyWords.length, false, keyWords);
		return this;
	}
	
	/**
	 * Validates all keywords are presents in the same order as given in arguments.
	 * @param keyWords
	 * @return
	 */
	public FileContentsValidator validateAllKeywordsPresentInOrder(String... keyWords) {
		validateAtLeastNKeywordPresent(keyWords.length, true, keyWords);
		return this;
	}

	public FileContentsValidator validateAtLeastNKeywordPresent(int atLeastN, boolean inOrder, String... keyWords) {
		assertNotNull(keyWords);
		List<String> foundKeywords = new LinkedList<>();
		int index = -1;
		int prevIndex = -1;
		for (String keyWord : keyWords) {
			if (foundKeywords.size() == atLeastN) {
				break;
			}
			
			if(inOrder) {
				index = fileContents.indexOf(keyWord, prevIndex);
			} else {
				index = fileContents.indexOf(keyWord);
			}
				
			if (index >= 0) {
				if(inOrder) {
					if(index > prevIndex) {
						foundKeywords.add(keyWord);
						prevIndex = index + keyWord.length();
					}
				} else {
					foundKeywords.add(keyWord);
				}
			}
		}

		if (foundKeywords.size() != atLeastN) {
			Assert.fail("Expected at least " + atLeastN + " keywords to match" + (inOrder ? " in order" : "") + " but matched only " + foundKeywords.size()
					+ " keywords. \nExpected keywords: " + Arrays.asList(keyWords) + ".\n Matched keywords: "
					+ foundKeywords);
		}

		return this;
	}

	public String extractFileContents() {
		try (InputStream fileIs = Files.newInputStream(Paths.get(filePath));
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			TikaConfig config = TikaConfig.getDefaultConfig();
			BodyContentHandler handler = new BodyContentHandler(out);
			Parser parser = new AutoDetectParser(config);
			Metadata meta = new Metadata();
			ParseContext parseContext = new ParseContext();

			PDFParserConfig pdfConfig = new PDFParserConfig();
			pdfConfig.setExtractInlineImages(true);
			pdfConfig.setExtractActions(false);
			pdfConfig.setExtractBookmarksText(false);
			pdfConfig.setExtractMarkedContent(true);
			//pdfConfig.setDetectAngles(true);
			//pdfConfig.setOcrDPI(100);

			TesseractOCRConfig tesserConfig = new TesseractOCRConfig();
			tesserConfig.setLanguage(ocrLanguage);
			tesserConfig.setEnableImagePreprocessing(true);

			parseContext.set(PDFParserConfig.class, pdfConfig);
			parseContext.set(TesseractOCRConfig.class, tesserConfig);
			
			parser.parse(fileIs, handler, meta, parseContext);
			
			String s = new String(out.toByteArray(), Charset.defaultCharset());
			return s;
		} catch (Exception e) {
			Assert.fail("Failed to extract file contents of file '" + filePath + "'.", e);
		}
		return "";
	}

	public static void main(String[] args) {
		try {
//			FileContentsValidator fcv = new FileContentsValidator(
//					"C:\\Program Files\\Microsoft Office\\root\\Office16\\sdxs\\FA000000070\\assets\\src\\assets\\images\\excel-dictate.png", true);
//			fcv.validateAllKeywordsPresentInOrder("that", "type");
			
//			FileContentsValidator fcv = new FileContentsValidator(
//					"C:\\projects\\github\\smart-testauto-framework\\src\\test\\resources\\toung-twisters.pdf", true);
//					
//			fcv.validateAllKeywordsPresentInOrder("Contact", "Support");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
