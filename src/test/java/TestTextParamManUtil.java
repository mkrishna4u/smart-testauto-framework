import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.utils.TextParamManUtil;
import org.w3c.dom.Document;

public class TestTextParamManUtil {
	public static void main(String[] args) {
		String text = "asdsad d dasd dd adasd TEST_VAR ::: ${TEST_VAR::$.numUsers} dadsdad ff ${TEST_VAR:string:$.numUsers} fsad sadsad"
				+ "dad [${TEST_VAR:string-list:$.users[*].name}] sdsad sdsad";
		String paramName = "TEST_VAR";
		
		System.out.println(TextParamManUtil.extractParamDetailsFromText(text, paramName));
		
		Object jsonObj = new JsonDocumentReader("{numUsers: 2, users: [{name: 'Madhav', age: 12}, {name: 'Anant', age: 10}]}", false).getDocumentContext();
		String text2 = TextParamManUtil.applyParamValueOnText(text, paramName, jsonObj);
		System.out.println("JSON: " + text2);
		
		//-------------- XML test
		String textXml = "sad d dfsdf sdfdsfd TEST_VAR fdsfs ${TEST_VAR:://users/numUsers/text()} dffs"
				+ "df ffdf ${TEST_VAR:string-list://users/user/name/text()} fsdf "
				+ "df dff ${TEST_VAR:string-list://users/user/@id} dsad "
				+ "sd  ${TEST_VAR:string://users/user/@id} ad";
		XmlDocumentReader xmlReader = new XmlDocumentReader(
			"<users>"
			+ "  <numUsers>2</numUsers>"
			+ "  <user id=\"aa\">"
			+ "    <name>Madhav1</name>"
			+ "    <age>36</age>"
			+ "  </user>"
			+ "  <user id=\"bb\">"
			+ "    <name>Anant1</name>"
			+ "    <age>12</age>"
			+ "  </user>"
			+ "</users>"
			);
		Document xmlDoc = xmlReader.getDocument();
		String textXml2 = TextParamManUtil.applyParamValueOnText(textXml, paramName, xmlDoc);
		
		System.out.println("XMLDoc: " + textXml2);
	}
}
