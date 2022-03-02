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
package org.uitnet.testing.smartfwk.api.core.reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class XmlDocumentReader {
	protected Document xmlDoc;

	public XmlDocumentReader(File xmlFilePath) {
		try {
			Assert.assertNotNull(xmlFilePath, "XML file path cannot be null.");
			xmlDoc = parseXML(Files.readAllBytes(Paths.get(xmlFilePath.getAbsolutePath())));
		} catch (Exception ex) {
			Assert.fail("Failed to parse XML document.", ex);
		}
	}

	public XmlDocumentReader(String xmlAsString) {
		try {
			Assert.assertNotNull(xmlAsString, "XML document cannot be null.");
			Assert.assertNotEquals(xmlAsString.trim(), "", "XML document cannot be empty.");
			xmlDoc = parseXML(xmlAsString.getBytes());
		} catch (Exception ex) {
			Assert.fail("Failed to parse XML document.", ex);
		}
	}

	private Document parseXML(byte[] contents) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = null;
		try (InputStream is = new ByteArrayInputStream(contents)) {
			xmlDocument = builder.parse(is);
		}
		return xmlDocument;
	}

	public Document getDocument() {
		return xmlDoc;
	}
}
