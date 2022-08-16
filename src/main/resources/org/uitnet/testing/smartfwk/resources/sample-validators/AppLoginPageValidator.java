package validators;

import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.UserProfile;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginPageValidator;

import page_objects.*;

public class AppLoginPageValidator extends LoginPageValidator {

	public AppLoginPageValidator() {
		super(null, null);
	}

	@Override
	public void setInitParams(SmartAppDriver appDriver) {
		this.appDriver = appDriver;
	}

	@Override
	protected void tryLogin(String activeUserProfileName) {
		AppConfig appConfig = appDriver.getAppConfig();
		UserProfile userProfile = appConfig.getUserProfile(activeUserProfileName);

		//TODO: Add code here to loging the system using user profile infomration
		//The below code is just sample code. Correct it as per your application.
		
		SampleAppLoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(5);

		SampleAppLoginPO.Textbox_Username.getValidator(appDriver, null)
				.typeText(userProfile.getAppLoginUserId(), NewTextLocation.replace, 0);
		
		SampleAppLoginPO.Textbox_Password.getValidator(appDriver, null).typeText(userProfile.getAppLoginUserPassword(),
				NewTextLocation.replace, 0);
		
		SampleAppLoginPO.Button_SignIn.getValidator(appDriver, null).click(0);
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		//TODO: Add code here to validate whether the login page is visible.
		//The below code is just sample code. Correct it as per your application.
		SampleAppLoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(2);
	}

	@Override
	public boolean checkLoginPageVisible(String activeUserProfileName) {
		//TODO: Add code here to return true when the login page is visible else return false
		//The below code is just sample code. Correct it as per your application.
		return SampleAppLoginPO.Button_SignIn.getValidator(appDriver, null).isVisible(2);
	}

}
