import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.InputValue;

import com.jayway.jsonpath.DocumentContext;

public class TestInputValue {

	public static void main(String[] args) {
		String jsonInput = "{"
				+ "value: \"testValue\","
				+ "valueType: \"string-list\","
				+ "autoValueInputs: {"
				  + "length:15,"
				  + "maxWordLength: 4,"
				  + "alphabetsLower: \"abcde\","
				  + "includeNumbers: true,"
				  + "includeWhiteSpaces: true"
				+ "}"
				+ "}";
		
		try {
			JsonDocumentReader r = new JsonDocumentReader(jsonInput);
			DocumentContext dc = r.getDocumentContext();
			InputValue iv = dc.read("$", InputValue.class);
			System.out.println(iv.getAutoValueInputs().build());
		} catch(Throwable th) {
			th.printStackTrace();
		}
	}
}
