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

		//TODO: Add code here to loging the system using user profile infomration
		//The below code is just sample code. Correct it as per your application.
		
		/* LoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(5);

		LoginPO.Textbox_Username.getValidator(appDriver, null)
				.typeText(userProfile.getAppLoginUserId(), NewTextLocation.replace, 2);
		
		LoginPO.Textbox_Password.getValidator(appDriver, null).typeText(userProfile.getAppLoginUserPassword(),
				NewTextLocation.replace, 2);
		
		LoginPO.Button_SignIn.getValidator(appDriver, null).click(2); */
		
		throw new PendingException("Please implement tryLogin() method in '" + this.getClass() + "' class.");
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		//TODO: Add code here to validate whether the login page is visible.
		//The below code is just sample code. Correct it as per your application.
		
		// LoginPO.Button_SignIn.getValidator(appDriver, null).validateVisible(2);
		throw new PendingException("Please implement tryLogin() method in '" + this.getClass() + "' class.");
	}

	@Override
	public boolean checkLoginPageVisible(String activeUserProfileName) {
		//TODO: Add code here to return true when the login page is visible else return false
		//The below code is just sample code. Correct it as per your application.
		
		// return LoginPO.Button_SignIn.getValidator(appDriver, null).isVisible(2);
		
		throw new PendingException("Please implement checkLoginPageVisible() method in '" + this.getClass() + "' class.");
	}

}
