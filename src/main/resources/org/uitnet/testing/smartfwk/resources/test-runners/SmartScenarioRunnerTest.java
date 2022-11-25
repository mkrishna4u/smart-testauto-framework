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
package stepdefs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.uitnet.testing.smartfwk.ui.core.defaults.testng.SmartUiTestNGExecutionListener;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * 
 * @author Madhav Krishna
 *
 */
@CucumberOptions(
	features = {
			"cucumber-testcases/e2e" }, 
	plugin = {"pretty", "json:test-results/cucumber-reports/json/smart-testauto-reports.json"}, 
	glue = {"stepdefs", "org.uitnet.testing.smartfwk.core.stepdefs.en"}
	)
@Listeners(SmartUiTestNGExecutionListener.class)
public class SmartScenarioRunnerTest extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}