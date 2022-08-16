package page_objects;

import org.uitnet.testing.smartfwk.ui.standard.domobj.*;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.*;

public interface SampleAppHomePO {
	//TODO: Write your page objects here. Remove the below sampled page objects.
	ImageSD Image_UserIcon = new ImageSD( "User Icon", "//img[@aria-label='User Icon']");
	ButtonSD Button_Signout = new ButtonSD("Sign out", "//button[contains(text(),'Sign out')]");
	
}
