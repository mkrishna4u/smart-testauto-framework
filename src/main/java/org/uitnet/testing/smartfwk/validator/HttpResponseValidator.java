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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.PayloadType;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * Used to validate the HTTP Response received by API Action Handler.
 * 
 * @author Madhav Krishna
 *
 */
public class HttpResponseValidator {
	private HttpResponse response;

	public HttpResponseValidator(HttpResponse response) {
		this.response = response;
	}

	public HttpResponseValidator validateExpectedResponseCode(int expectedResponseCode) {
		assertEquals(response.getCode(), expectedResponseCode, "HTTP Response code does not match with expected value.");
		return this;
	}

	public HttpResponseValidator validateExpectedHeaderValue(String headerName, String expectedValue) {
		String actual = response.getHeader(headerName);
		assertEquals(actual, expectedValue, "Header '" + headerName + "' value does not match with expected value.");
		return this;
	}

	public HttpResponseValidator validateExpectedHeaderValue(String headerName, String expectedValue, TextMatchMechanism textMatchMechanism) {
		String actual = response.getHeader(headerName);
		if(!StringUtil.isTextMatchedWithExpectedValue(actual, expectedValue, textMatchMechanism)) {
			Assert.fail("Header '" + headerName + "' actual value '" + actual 
					+ "' does not match with the expected value '" + expectedValue + "' using '" + textMatchMechanism.name() + "' text match mechanism.");
		}
		return this;
	}

	public HttpResponseValidator validateResponsePayloadType(PayloadType expectedPayloadType) {
		assertEquals(response.getPayloadType().name(), expectedPayloadType.name(), "HTTP Response payload type does not match.");
		return this;
	}

	public HttpResponseValidator validateResponseNotNull() {
		assertNotNull(response.getPayload(), "HTTP Response payload should not be null.");
		return this;
	}
	
	public HttpResponseValidator validateResponseIsNull() {
		assertNull(response.getPayload(), "HTTP Response is not null.");
		return this;
	}
	
	public HttpResponseValidator validateContentType(String expectedContentType) {
		validateExpectedHeaderValue("Content-Type", expectedContentType);
		return this;
	}
	
	public HttpResponseValidator validateMinContentLength(long minLength) {
		String contentLength = response.getHeader("Content-Length");
		if(minLength >= 0) {
			assertNotNull(contentLength, "HTTP response content length should not null.");
			if(Long.parseLong(contentLength) < minLength) {
				fail("HTTP response content length should be greater than " + (minLength - 1) + ".");
			}
		}
		return this;
	}

	public HttpResponseValidator validateFileDownloaded(String expectedFileName,
			TextMatchMechanism fileNameMatchMechanism, boolean deleteAfterValidation) {
		validateResponsePayloadType(PayloadType.FILE);
		DownloadedFileValidator.validateFileDownloaded(expectedFileName, response, fileNameMatchMechanism, deleteAfterValidation);
		return this;
	}
	
	/**
	 * Validates the keywords order.
	 * 
	 * @param shouldPrintFileContentsOnConsole
	 * @param keyWords
	 * @return
	 */
	public HttpResponseValidator validateDownloadedFileContainsAllKeywordsInOrder(boolean shouldPrintFileContentsOnConsole, String... keyWords) {
		validateResponsePayloadType(PayloadType.FILE);
		FileContentsValidator fcv = new FileContentsValidator(response.getFilePath(), shouldPrintFileContentsOnConsole);
		fcv.validateAllKeywordsPresentInOrder(keyWords);
		return this;
	}
	
	/**
	 * Does not check the order of keywords.
	 * 
	 * @param shouldPrintFileContentsOnConsole
	 * @param keyWords
	 * @return
	 */
	public HttpResponseValidator validateDownloadedFileContainsAllKeywords(boolean shouldPrintFileContentsOnConsole, String... keyWords) {
		validateResponsePayloadType(PayloadType.FILE);
		FileContentsValidator fcv = new FileContentsValidator(response.getFilePath(), shouldPrintFileContentsOnConsole);
		fcv.validateAllKeywordsPresent(keyWords);
		return this;
	}
	
	public HttpResponseValidator validateDownloadedFileContainsAtLeastNKeyword(boolean shouldPrintFileContentsOnConsole, int atLeastN, boolean inOrder, String... keyWords) {
		validateResponsePayloadType(PayloadType.FILE);
		FileContentsValidator fcv = new FileContentsValidator(response.getFilePath(), shouldPrintFileContentsOnConsole);
		fcv.validateAtLeastNKeywordPresent(atLeastN, inOrder, keyWords);
		return this;
	}
	
	public String getExtractedFileContents() {
		validateResponsePayloadType(PayloadType.FILE);
		FileContentsValidator fcv = new FileContentsValidator(response.getFilePath(), false);
		return fcv.extractFileContents();
	}
}
