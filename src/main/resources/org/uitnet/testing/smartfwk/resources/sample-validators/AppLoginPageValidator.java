package validators.<app-name>;

import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.UserProfile;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginPageValidator;

import io.cucumber.java.PendingException;

import page_objects.<app-name>.*;

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

		if(appConfig.getAppLoginRequired()) {
			//TODO: Add code here to loging the system using user profile infomration
			//The below code is just sample code. Correct it as per your application.
			
			LoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(5);
	
			LoginPO.Textbox_Username.getValidator(appDriver, null)
					.typeText(userProfile.getAppLoginUserId(), NewTextLocation.replace, 2);
			
			LoginPO.Textbox_Password.getValidator(appDriver, null).typeText(userProfile.getAppLoginUserPassword(),
					NewTextLocation.replace, 2);
			
			LoginPO.Button_SignIn.getValidator(appDriver, null).click(2);
		} else {
			// do nothing
		}
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		//TODO: Add code here to validate whether the login page is visible.
		//The below code is just sample code. Correct it as per your application.
		AppConfig appConfig = appDriver.getAppConfig();
		if(appConfig.getAppLoginRequired()) {
			LoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(2);
		} else {
			// do nothing.
		}
	}

	@Override
	public boolean checkLoginPageVisible(String activeUserProfileName) {
		//TODO: Add code here to return true when the login page is visible else return false
		//The below code is just sample code. Correct it as per your application.
		AppConfig appConfig = appDriver.getAppConfig();
		if(appConfig.getAppLoginRequired()) {
			return LoginPO.Button_SignIn.getValidator(appDriver, null).isVisible(2);
		} else {
			return false;
		}
	}

}
