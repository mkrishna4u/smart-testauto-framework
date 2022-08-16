package validators;

import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginSuccessPageValidator;

import page_objects.*;

public class AppLoginSuccessPageValidator extends LoginSuccessPageValidator {

	public AppLoginSuccessPageValidator() {
		super(null, null);
	}

	@Override
	public void setInitParams(SmartAppDriver appDriver) {
		this.appDriver = appDriver;
	}

	@Override
	protected void tryLogout(String activeUserProfileName) {
		//TODO: Add code to logout from UI application.
		//The below code is just sample code. Correct it as per your application.
		SampleAppHomePO.Image_UserIcon.getValidator(appDriver, null).click(2);
		SampleAppHomePO.MENU_Signout.getValidator(appDriver, null).click(2);
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		//TODO: Add code to check after successful login, the home page / main dashboard is visible.
		//The below code is just sample code. Correct it as per your application.
		SampleAppHomePO.Image_UserIcon.getValidator(appDriver, null).validateVisible(2);	
	}

	@Override
	protected boolean checkLoginSuccessPageVisible(String activeUserProfileName) {
		//TODO: Add code here to to return true when the home page / main dashboard is visible else return false.
		//The below code is just sample code. Correct it as per your application.
		return SampleAppHomePO.Image_UserIcon.getValidator(appDriver, null).isVisible(2);
	}
}
