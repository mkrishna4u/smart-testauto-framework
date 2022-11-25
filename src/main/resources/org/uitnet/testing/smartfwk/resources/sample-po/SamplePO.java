package page_objects.<app-name>;

import org.uitnet.testing.smartfwk.ui.standard.domobj.*;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.*;

public interface SamplePO {
	// TODO: Remove all these sample page objects and add your application specific page objects
	HyperlinkSD MenuItem_Home = new HyperlinkSD("Home", "<xpath-here>");
	LabelSD PageTitle_Home = new LabelSD("Home", "<xpath-here>");
	TextBoxSD Textbox_HiddenElement = new TextBoxSD("Hidden element", "<xpath-here>");
	
	//TextBoxSD Textbox_<elem-name> = new TextBoxSD("<elem-name>", "<xpath-here>");	
	// ButtonSD Button_<elem-name> = new ButtonSD("<elem-name>", "<xpath-here>");
	
}
