package page_objects.<app-name>;

import org.uitnet.testing.smartfwk.ui.standard.domobj.ButtonSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.LabelSD;

public interface LoginSuccessPO {
	// TODO: Change these elements according to your application
	// Add unique element that is always visible after login (even page is switched)
	// You can change these elements according to your application and accordingly update the AppLoginSuccessPageValidator.java
	LabelSD Label_UniqueElement = new LabelSD("<Unique-element-name-here>", "//span[normalize-space()='<page-tiltle-here>']");
	ButtonSD Button_Logout = new ButtonSD("Logout", "//button[@id='logout']");
}
