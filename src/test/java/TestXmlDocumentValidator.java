import java.io.File;

import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.xml.XmlDocumentValidator;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;

public class TestXmlDocumentValidator {

	public static void main(String[] args) {
		try {
			XmlDocumentValidator v = new XmlDocumentValidator(
					new File(Locations.getProjectRootDir() + File.separator + "src/test/resources/books.xml"));
			//Object doc = v.findAttributeOrTextValues("book", new ParamPath("//book/@id", "string"));
			Object doc = v.findAttributeOrTextValues("book", new ParamPath("//price/text()", "integer"));
			System.out.println(doc);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
