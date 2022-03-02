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
package org.uitnet.testing.smartfwk.api.core.validator.xml;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class XmlDocumentValidator {
	protected XmlDocumentReader xmlDocReader;
	protected XPath xPath;

	public XmlDocumentValidator(String xmlAsString) {
		xmlDocReader = new XmlDocumentReader(xmlAsString);
		xPath = XPathFactory.newInstance().newXPath();
	}

	public XmlDocumentValidator(File xmlFilePath) {
		xmlDocReader = new XmlDocumentReader(xmlFilePath);
		xPath = XPathFactory.newInstance().newXPath();
	}

	public Document getDocument() {
		return xmlDocReader.getDocument();
	}
	
	public NodeList findRecordsFor(String elementName, String xmlPath) throws XPathExpressionException {
		NodeList result = null;
		try {
			result = (NodeList) xPath.compile(xmlPath).evaluate(xmlDocReader.getDocument(), XPathConstants.NODESET);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xmlPath + "'.", e);
		}
		
		return result;
	}

	public void validatePathPresent(String elementName, String xmlPath) {
		NodeList result = null;
		try {
			result = (NodeList) xPath.compile(xmlPath).evaluate(xmlDocReader.getDocument(), XPathConstants.NODESET);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xmlPath + "'.", e);
		}
		
		Assert.assertNotNull(result,
				"Element '" + elementName + "' does not exist on XML Path '" + xmlPath + "'.");
		Assert.assertTrue(result.getLength() > 0,
				"Element '" + elementName + "' does not exist on XML Path '" + xmlPath + "'.");
		
	}
	
	public void validateExpectedNRecordsPresent(String elementName, String xmlPath, int expectedN) {
		NodeList result = null;
		try {
			result = (NodeList) xPath.compile(xmlPath).evaluate(xmlDocReader.getDocument(), XPathConstants.NODESET);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xmlPath + "'.", e);
		}
		
		Assert.assertNotNull(result,
				"Element '" + elementName + "' does not exist on XML Path '" + xmlPath + "'.");
		Assert.assertTrue(result.getLength() == expectedN,
				"Element '" + elementName + "' does not have exact '" + expectedN + "' records on XML Path '" + xmlPath + "'. Found: " + result.getLength());
	}
	
	public void validateAtleastNRecordsPresent(String elementName, String xmlPath, int atleastN) {
		NodeList result = null;
		try {
			result = (NodeList) xPath.compile(xmlPath).evaluate(xmlDocReader.getDocument(), XPathConstants.NODESET);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xmlPath + "'.", e);
		}
		
		Assert.assertNotNull(result,
				"Element '" + elementName + "' does not exist on XML Path '" + xmlPath + "'.");
		Assert.assertTrue(result.getLength() >= atleastN,
				"Element '" + elementName + "' does not have atleast '" + atleastN + "' records on XML Path '" + xmlPath + "'. Found: " + result.getLength());
	}
	
	public void validateAtMaxNRecordsPresent(String elementName, String xmlPath, int atMaxN) {
		NodeList result = null;
		try {
			result = (NodeList) xPath.compile(xmlPath).evaluate(xmlDocReader.getDocument(), XPathConstants.NODESET);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xmlPath + "'.", e);
		}
		
		Assert.assertNotNull(result,
				"Element '" + elementName + "' does not exist on XML Path '" + xmlPath + "'.");
		Assert.assertTrue(result.getLength() <= atMaxN,
				"Element '" + elementName + "' have more records than expected('" + atMaxN + "') on XML Path '" + xmlPath + "'. Found: " + result.getLength());
	}

}
