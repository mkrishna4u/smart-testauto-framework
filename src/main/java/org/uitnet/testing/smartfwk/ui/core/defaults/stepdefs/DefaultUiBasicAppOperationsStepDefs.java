/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.ui.core.defaults.stepdefs;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.DefaultAppConnector;
import org.uitnet.testing.smartfwk.ui.core.SmartAppConnector;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.cache.DefaultSmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCache;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class contains the login steps and the shared steps that can be used in
 * all the scenarios defined in cucumber feature file.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultUiBasicAppOperationsStepDefs {
	private DefaultAppConnector appConnector;
	private Scenario runningScenario;
	private SmartAppDriver appDriver;
	private SmartCache globalCache;

	public DefaultUiBasicAppOperationsStepDefs() {
		appConnector = SmartAppConnector.connect(DefaultInfo.DEFAULT_APP_NAME);
		globalCache = DefaultSmartCache.getInstance();
		globalCache.setAppConnector(appConnector);
	}

	@Before
	public void beforeScenario(Scenario scenario) {
		runningScenario = scenario;

		globalCache.setRunningScenario(scenario);
		globalCache.publish(globalCache);

		if (appConnector != null) {
			appConnector.captureScreenshot(runningScenario, "scenario-STARTED");
		}
	}

	@After
	public void afterScenario(Scenario scenario) {
		if (appConnector != null) {
			appConnector.captureScreenshot(scenario, "scenario-" + scenario.getStatus());
		}
		
		if(appDriver != null) {
			try {
				appDriver.getWebDriver().switchTo().defaultContent();
			}catch(Exception | Error e) { }
		}
	}

	@When("Open {string} application.")
	@Given("{string} application is already opened.")
	@Given("Application {string} is already opened.")
	@When("Switch to {string} application.")
	@When("Connect to {string} application.")
	@Given("Connected to {string} application.")
	public void connect_or_switch_to_application(String appName) {
		appConnector = SmartAppConnector.connect(appName);
		globalCache.setAppConnector(appConnector);
		
		appDriver = appConnector.setActiveUserProfileName(DefaultInfo.DEFAULT_USER_PROFILE_NAME);
		globalCache.setAppDriver(appDriver);
		globalCache.publish(globalCache);
	}

	@Given("User is logged in using {string} user profile.")
	@Given("User is already logged in using {string} user profile.")
	@When("User login using {string} user profile.")
	@When("Login using {string} user profile.")
	@When("User switch to {string} user profile.")
	@When("Switch to {string} user profile.")
	public void user_login_using_user_profile(String userProfileName) {
		appDriver = appConnector.setActiveUserProfileName(userProfileName);
		globalCache.setAppDriver(appDriver);
		globalCache.publish(globalCache);
	}

	@Given("User profile {string} is already activated on {string} application.")
	@Given("{string} user profile is already activated on {string} application.")
	@When("Switch {string} user profile on {string} application.")
	@When("Switch to {string} user profile on {string} application.")
	@When("Activate {string} user profile on {string} application.")
	public void activate_user_profile_on_app(String userProfile, String appName) {
		connect_or_switch_to_application(appName);
		user_login_using_user_profile(userProfile);
	}

	@Given("Switch to {string} application using {string} user profile.")
	@Given("Connect to {string} application using {string} user profile.")
	public void switch_to_application_using_user_profile(String appName, String userProfile) {
		connect_or_switch_to_application(appName);
		user_login_using_user_profile(userProfile);
	}

	@Given("URL {string} is already opened.")
	@Given("{string} URL is already opened.")
	@When("Open {string} URL.")
	public void open_url(String url) {
		appDriver.openURL(url);
	}
	
	@Then("The {string} URL is displayed.")
	@Then("{string} URL is displayed.")
	@Then("The {string} URL is opened.")
	@Then("{string} URL is opened.")
	public void url_is_displayed(String url) {
		// do not do anything
	}

	@Then("Verify {string} page is displayed.")
	public void verify_the_page_is_displayed(String pageName) {
		// do not do anything.
	}

	@Then("The {string} page is displayed.")
	@Then("{string} page is displayed.")
	public void the_page_is_displayed(String pageName) {
		// do not do anything
	}

	@Then("{string} application is switched successfully.")
	@Then("Connected to {string} application successfully.")
	@Then("{string} application is opened successfully.")
	public void application_is_switched_successfully(String appName) {
		if (!StringUtil.isTextMatchedWithExpectedValue(appConnector.getAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}

	@Then("{string} user profile is switched successfully.")
	@Then("{string} user profile is activated successfully.")
	public void user_profile_is_switched_successfully(String userProfile) {
		if (!StringUtil.isTextMatchedWithExpectedValue(appConnector.getActiveUserProfileName(), userProfile,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}
	}

	@Then("{string} user profile is switched successfully on {string} application.")
	@Then("{string} user profile is activated successfully on {string} application.")
	public void user_profile_is_switched_successfully(String userProfile, String appName) {
		if (!StringUtil.isTextMatchedWithExpectedValue(appConnector.getActiveUserProfileName(), userProfile,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(userProfile + " is not activated.");
		}
		
		if (!StringUtil.isTextMatchedWithExpectedValue(appConnector.getAppName(), appName,
				TextMatchMechanism.exactMatchWithExpectedValue)) {
			Assert.fail(appName + " is not activated.");
		}
	}

	@When("Take screenshot.")
	public void take_screenshot() {
		appConnector.captureScreenshot(runningScenario, "scenario-RUNNUNG");
	}

	@Then("Screenshot is taken.")
	public void screenshot_is_taken() {
		// do nothing
	}

}
