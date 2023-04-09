package validators.<app-name>;

import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginSuccessPageValidator;

import io.cucumber.java.PendingException;

import page_objects.<app-name>.*;

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
		
		AppConfig appConfig = appDriver.getAppConfig();
		if(appConfig.getAppLoginRequired()) {
			LoginSuccessPO.Button_Logout.getValidator(appDriver, null).click(2);
		} else {
			// do nothing.
		}
	}

	@Override
	protected void validateInfo(String activeUserProfileName) {
		//TODO: Add code to check after successful login, the home page / main dashboard is visible.
		//The below code is just sample code. Correct it as per your application.
		AppConfig appConfig = appDriver.getAppConfig();
		if(appConfig.getAppLoginRequired()) {
			LoginSuccessPO.Label_UniqueElement.getValidator(appDriver, null).validateVisible(2);
		} else {
			// do nothing.
		}
		
	}

	@Override
	protected boolean checkLoginSuccessPageVisible(String activeUserProfileName) {
		//TODO: Add code here to to return true when the home page / main dashboard is visible else return false.
		//The below code is just sample code. Correct it as per your application.
		AppConfig appConfig = appDriver.getAppConfig();
		if(appConfig.getAppLoginRequired()) {
			return LoginSuccessPO.Label_UniqueElement.getValidator(appDriver, null).isVisible(2);
		} else {
			return true;
		}
	}
}
