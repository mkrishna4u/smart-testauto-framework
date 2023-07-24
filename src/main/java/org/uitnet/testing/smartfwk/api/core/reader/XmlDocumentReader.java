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
package org.uitnet.testing.smartfwk.api.core.reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	
	public XmlDocumentReader(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
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
	
	public Object findAttributeOrTextValues(String elementName, ParamPath xpath) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		List<Object> values = new LinkedList<>();
		try {
			NodeList nodes = null;
			try {
				nodes = (NodeList) xPath.compile(xpath.getPath()).evaluate(getDocument(), XPathConstants.NODESET);
				if(nodes == null) {
					return null;
				}
			} catch(Exception ex) {
				if(xpath.getValueType() == ParamValueType.STRING) {
					return xPath.compile(xpath.getPath()).evaluate(getDocument(), XPathConstants.STRING);
				} else if(xpath.getValueType() == ParamValueType.INTEGER) {
					Double d = (Double) xPath.compile(xpath.getPath()).evaluate(getDocument(), XPathConstants.NUMBER);
					if(d != null) {
						return d.longValue();
					} else {
						return d;
					}
				} else if(xpath.getValueType() == ParamValueType.DECIMAL) {
					return xPath.compile(xpath.getPath()).evaluate(getDocument(), XPathConstants.NUMBER);
				} else if(xpath.getValueType() == ParamValueType.BOOLEAN) {
					return xPath.compile(xpath.getPath()).evaluate(getDocument(), XPathConstants.BOOLEAN);
				}
			}
						
			for(int i = 0; i < nodes.getLength(); i++) {
				if(xpath.getValueType() == ParamValueType.STRING) {
					return nodes.item(i).getTextContent();
				} else if(xpath.getValueType() == ParamValueType.STRING_LIST) {
					values.add(nodes.item(i).getTextContent());
				} else if(xpath.getValueType() == ParamValueType.INTEGER) {
					return Integer.valueOf(nodes.item(i).getTextContent());
				} else if(xpath.getValueType() == ParamValueType.INTEGER_LIST) {
					values.add(Integer.valueOf(nodes.item(i).getTextContent()));
				} else if(xpath.getValueType() == ParamValueType.DECIMAL) {
					return Double.valueOf(nodes.item(i).getTextContent());
				} else if(xpath.getValueType() == ParamValueType.DECIMAL_LIST) {
					values.add(Double.valueOf(nodes.item(i).getTextContent()));
				} else if(xpath.getValueType() == ParamValueType.BOOLEAN) {
					return Boolean.valueOf(nodes.item(i).getTextContent());
				} else if(xpath.getValueType() == ParamValueType.BOOLEAN_LIST) {
					values.add(Boolean.valueOf(nodes.item(i).getTextContent()));
				} else {
					Assert.fail("Param value type '" + xpath + "' is not supported.");
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect XML Path '" + xpath.getPath() + "'.", e);
		}
		
		return values;
	}
	
	public static Element getChildElement(Node node, int index) {
		if(node == null) { return null; }
		
		NodeList nodes = node.getChildNodes();
		if(nodes == null) { return null; }
		
		Node n;
		for(int i = 0; i < nodes.getLength(); i++) {
			n = nodes.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				if(index == 0) {
					return (Element)n;
				}
				index--;
			}
		}
		return null;
	}
}
