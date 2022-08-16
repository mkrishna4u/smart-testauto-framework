package page_objects;

import org.uitnet.testing.smartfwk.ui.standard.domobj.*;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.*;

public interface SampleAppLoginPO {
	//TODO: Write your page objects here. Remove the below sampled page objects.
	ImageSI Image_Logo = new ImageSI("Logo", "Logo.jpg", null);
	TextBoxSD Textbox_Username = new TextBoxSD("Username", "//input[@id='username']");
	TextBoxSD Textbox_Password = new TextBoxSD("Password", "//input[@id='password']");
	ButtonSD Button_SignIn = new ButtonSD("Sign in", "//button[@value='Sign in']");

}
