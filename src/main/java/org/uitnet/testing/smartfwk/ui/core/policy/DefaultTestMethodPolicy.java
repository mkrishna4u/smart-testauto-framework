/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.core.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultTestMethodPolicy implements ITestListener {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onTestStart(ITestResult result) {
		AbstractAppConnector baseTest = (AbstractAppConnector) result.getInstance();

		logger.info("TESTCASE(Started): '" + baseTest.getClass().getSimpleName() + "-"
				+ result.getMethod().getMethodName() + "'. Wait for completion...");

		baseTest.captureScreenshot(result.getMethod().getMethodName() + "-Initial");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		AbstractAppConnector baseTest = (AbstractAppConnector) result.getInstance();
		baseTest.captureScreenshot(result.getMethod().getMethodName() + "-Success");
		logger.info("TESTCASE(Finished-Successful): '" + baseTest.getClass().getSimpleName() + "-"
				+ result.getMethod().getMethodName() + "'.");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		AbstractAppConnector baseTest = (AbstractAppConnector) result.getInstance();
		baseTest.captureScreenshot(result.getMethod().getMethodName() + "-Failed");
		logger.info("TESTCASE(Finished-Failed): '" + baseTest.getClass().getSimpleName() + "-"
				+ result.getMethod().getMethodName() + "'.", result.getThrowable());

		baseTest.getAppDriver().refresh();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		AbstractAppConnector baseTest = (AbstractAppConnector) result.getInstance();
		baseTest.captureScreenshot(result.getMethod().getMethodName() + "-Skipped");
		logger.info("TESTCASE(Finished-Skipped): '" + baseTest.getClass().getSimpleName() + "-"
				+ result.getMethod().getMethodName() + "'.", result.getThrowable());

		baseTest.getAppDriver().refresh();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		AbstractAppConnector baseTest = (AbstractAppConnector) result.getInstance();
		baseTest.captureScreenshot(result.getMethod().getMethodName() + "-Failed");
		logger.info("TESTCASE(Finished-Failed): '" + baseTest.getClass().getSimpleName() + "-"
				+ result.getMethod().getMethodName() + "'.", result.getThrowable());

		baseTest.getAppDriver().refresh();
	}

	@Override
	public void onStart(ITestContext context) {
		// DO NOT DO ANYTHING
	}

	@Override
	public void onFinish(ITestContext context) {
		// DO NOT DO ANYTHING
	}

}
