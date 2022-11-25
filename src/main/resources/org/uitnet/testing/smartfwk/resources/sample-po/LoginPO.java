package page_objects.<app-name>;

import org.uitnet.testing.smartfwk.ui.standard.domobj.ButtonSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.LabelSD;
import org.uitnet.testing.smartfwk.ui.standard.domobj.TextBoxSD;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.ImageSI;

public interface LoginPO {
	// TODO: Change these elements according to your application
	TextBoxSD Textbox_Username = new TextBoxSD("Username", "//input[@id='username']");
	TextBoxSD Textbox_Password = new TextBoxSD("Password", "//input[@id='password']");
	ButtonSD Button_SignIn = new ButtonSD("Sign in", "//button[@id='signin']");
}
