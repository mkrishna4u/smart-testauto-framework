import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.reader.XmlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.utils.VariableExpressionManagerUtil;
import org.w3c.dom.Document;

public class TestVariableExpressionManagerUtil {
	public static void main(String[] args) {
		String text = "asdsad d dasd dd adasd TEST_VAR ::: ${TEST_VAR:::$.numUsers} dadsdad ff ${TEST_VAR:string::$.numUsers} fsad sadsad"
				+ "dad [${TEST_VAR:string-list::$.users[*].name}] sdsad sdsad";
		String paramName = "TEST_VAR";
		
		System.out.println(VariableExpressionManagerUtil.extractVariableDetailsFromText(text, paramName));
		
		Object jsonObj = new JsonDocumentReader("{numUsers: 2, users: [{name: 'Madhav', age: 12}, {name: 'Anant', age: 10}]}", false).getDocumentContext();
		String text2 = VariableExpressionManagerUtil.applyVariableValueOnText(text, paramName, jsonObj);
		System.out.println("JSON: " + text2);
		
		//-------------- XML test
		String textXml = "sad d dfsdf sdfdsfd TEST_VAR fdsfs ${TEST_VAR::://users/numUsers/text()} dffs"
				+ "df ffdf ${TEST_VAR:string-list:://users/user/name/text()} fsdf "
				+ "df dff ${TEST_VAR:string-list:://users/user/@id} dsad "
				+ "sd  ${TEST_VAR:string-list:://users/user/@id} ad";
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
		String textXml2 = VariableExpressionManagerUtil.applyVariableValueOnText(textXml, paramName, xmlDoc);
		
		System.out.println("XMLDoc: " + textXml2);
		
		//---------
		Object jsonObj3 = new JsonDocumentReader("[['null ', 'abc', 'bcd', null, '  ', '  asd  '], ['bb', 'abc', 'bcd', null, '  ', '  asd  ']]", false).getDocumentContext();
		String paramName3 = "DB_TBL_VAR";
		String text3 = "asd asdsad sad ${DB_TBL_VAR:string-list:replaceNullByEmpty:$.[*][5]} sdsad sadsa dsa dsa d";
		String text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		text3 = "asd asdsad sad ${DB_TBL_VAR::replaceNullByEmpty:$.[0][0]} sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		text3 = "asd asdsad sad ${DB_TBL_VAR:string:trim:$.[0][4]} sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		text3 = "asd asdsad sad ${DB_TBL_VAR:string:trimLeft:$.[0][5]} sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		text3 = "asd asdsad sad ${DB_TBL_VAR:string:trimRight:$.[0][5]} sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		text3 = "asd asdsad sad ${DB_TBL_VAR:string:trimNullToEmpty:$.[0][1]} sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, paramName3, jsonObj3);
		System.out.println("text31: " + text31);
		
		String strValue = "kkkk k kk k k kk k";
		text3 = "asd asdsad sad DB_TBL_VAR2 sdsad sadsa dsa dsa d";
		text31 = VariableExpressionManagerUtil.applyVariableValueOnText(text3, "DB_TBL_VAR2", strValue);
		System.out.println("text4: " + text31);
	}
}
