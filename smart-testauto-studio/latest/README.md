# Smart Test Automation Studio (Smart-TestAuto-Studio) / STAS
**STAS** is a very powerful software testing automation tool. It is used to configure multiple software applications into it to perform testing automation across the applications to perform the End-to-End testing in a standard way. Standard way means it does not matter what platform (windows, linux, ios, mac etc.) or what application we use to perform testing automation. The way the testcases / test-scenarios are going to get written will be same and standardized, so that all test engineers can write the test-scenarios in similar fashion to reduce the complexity of the test-scenarios from the maintenance perspective. It uses standardized Cucumber Gherkin language to write the test scenarios. Standardized Cucumber Gherkin Step definitions are available on Github project:

*   [https://github.com/mkrishna4u/smart-testauto-cucumber-defaults-en](https://github.com/mkrishna4u/smart-testauto-cucumber-defaults-en "smart-testauto-cucumber-defaults-en") 


## Why Smart-TestAuto-Studio?
**Smart-TestAuto-Studio** tool provides many capabilities for test engineers to write **zero code or very less code** to perform testing automation. It has standardized Gherkin steps that can be used to automate the following functionalities of a different kind of applications:

1.  Web UI Testing
2.  Android Mobile Testing (Web UI and Native Apps)
3.  iOS Mobile Testing (Web UI and Native Apps)
4.  API Testing (Data format - JSON, XML, Text, attachments (File Upload and File Download))
5.  Database Testing (Currently supporting all relational databases like Oracle, MySQL, PostgreSQL etc.)
6.  File Downloads and its contents verification
7.  File uploads and its contents verification
8.  Remote Machine Data verification
9.  Local Machine Data verification

## How to setup software testing automation project environment?
It provides the command line interface to setup project environment on your laptop or desktop. Steps are given below:

1.  Create a project directory. Let's say "Smart-TestAuto-Studio" directory is created at some location of your choice.
2.  Download the following files from the Github path (for the latest available version): [https://github.com/mkrishna4u/smart-testauto-framework/tree/main/smart-testauto-studio/latest](https://github.com/mkrishna4u/smart-testauto-framework/tree/main/smart-testauto-studio/latest  "Smart TestAuto Studio Latest"):

    -   **pom.xml**
    -   **smart-studio.sh**
    -   **smart-studio.cmd**
    -   **README.md**
    -   **LICENSE**
3.  Open command prompt / Console / Terminal and change directory to **<your-base-path>/Smart-TestAuto-Studio** (your project directory)

    *  Run the following command to create the project setup for your first application:
        -  **smart-studio --init "your-app-name"**        
            > This command will create the full maven project environment using first app. This will generate many directories to manage the full project.
4.  Now you are all set with the maven project. Import this maven project in your favorite Java Code Editor like Eclipse or IntellijIDEA etc. to work on this project. Make sure **Cucumber plugin** is installed on your code editor to write test scenarios using Gherkin language. Also you can convert your project as Cucumber project on code editor for Step Auto Completion in Cucumber feature file.
5.  Update the configuration file as per your application need that are present in **test-config/** directory.

## What you need to know to work on this tool?
There are many tools integrated in this tool to make the test engineers life easy. The most import things that every test engineer must know are:
1.  **Cucumber Gherkin Language:** Study about it on the following URL:   
[https://cucumber.io/docs/gherkin/reference/](https://cucumber.io/docs/gherkin/reference/ "Cucumber Gherkin Language References") 
2.  **JSON and JSON Path:** JSON is a very powerful data format that is used while writing cucumber scenario using STAS tool. For JSON path, please refer [https://github.com/json-path/JsonPath](https://github.com/json-path/JsonPath "JSON Path") link. JSON Path is used to modify the JSON data or retrieve field data from JSON data.
3.  **Basic Knowledge of Java:** Basic knowledge of Java programming is needed to create the page object classes. It only require, how to create class object using parameterized constructor or how to call class methods. Other basic knowledge of Java programming will be useful if you are planning to write your customized cucumber step definitions.
4.  **Different type of data files:** If you are writing data driven test scenarios that requires to read data from Excel, CSV, TXT, XML, JSON, YAML etc. file then you should know the format of these data files. This tool provide ready to use step definition to read the data from these types of files.
5.  **XML and XPATH:** Knowledge of XML file contents are mandatory to work on the user interface and web services (API testing) that uses XML data format. Using XPATH mechanism you can access any element in XML document or modify the contents of XML document. XPATH references: [https://www.w3.org/TR/1999/REC-xpath-19991116/](https://www.w3.org/TR/1999/REC-xpath-19991116/ "W3C XPATH Specification") 
6.  **HTML and XPATH:** For user interface testing automation, STAS tool uses Selenium / Appium internally to perform operation on page object elements like Textbox, Button etc. You can use different type of locators to locate element on the user interface like ID, AutomationID, AccessibilityID, Name, CSS Selector, LinkText, XPATH etc. By default STAS tool uses XPATH mechanism to locate element on user interface as XPATH mechanism is much solid and we can identify any element on user interface. XPATH references: [https://www.w3.org/TR/1999/REC-xpath-19991116/](https://www.w3.org/TR/1999/REC-xpath-19991116/ "W3C XPATH Specification") 
7.  **Basic knowledge of Shell Scripting:** Since STAS uses command line to run the test scenarios. So knowledge of how to run shell script (windows or linux) is required to run the maven commands or STAS provided scripts using command line.
8.  **YAML file:** All the configuration in STAS tool is given in YAML format. It is very simple and standard format to specify the configuration. To know the YAML file format, you can refer any online document.

## Tags supported in STAS Cucumber Feature File to group scenarios
The following Tags are supported in Cucumber Feature File to group the test scenarios. You can create your own tags and run the test scenarios. But STAS tool provides the following tags support out of the box:

A. **@TempScenario:** This tag is generally used by test engineer during development of the test scenario. When the test scenario is in development phase, apply this tag on the cucumber scenario / scenario outline. And this scenario / scenario outline can be run using the following command:
	
	> smart-runner run-temp-scenarios
	
NOTE: Remove this tag once the scenario is successfully developed.

B. **@RegressionTest:** Apply this tag on cucumber scenario / scenario outline to mark that scenario as regression test scenario. When we use the following below command, this will run all the scenarios that are marked @RegressionTest.

	> smart-runner run-regression-tests
	
C. **@SanityTest:** Apply this tag on cucumber scenario / scenario outline to mark that scenario as sanity test scenario. When we use the following below command, this will run all the scenarios that are marked @SanityTest.

	
	> smart-runner run-sanity-tests

D. **@SmokeTest:** Apply this tag on cucumber scenario / scenario outline to mark that scenario as smoke test scenario. When we use the following below command, this will run all the scenarios that are marked @SmokeTest.

	
	> smart-runner run-smoke-tests

E. **@Failed:** Apply this tag on cucumber scenario / scenario outline to mark that scenario as Failed scenario. When we use the following below command, this will run all the scenarios that are marked @Failed.

	
	> smart-runner run-failed-tests        

F. **@Pending:** Apply this tag on cucumber scenario / scenario outline to mark that scenario as Pending scenario. Pending scenario means that it is not implemented and do not run. When we use any command given above, it will not include scenarios that are marked @Pending.

## Find missing step definitions
To find the missing step definitions for the scenarios that are marked as **@TempScenario** in cucumber feature file. Use the following command:

	>  smart-runner find-missing-stepdefs-for-temp-scenarios
	
To find the missing step definitions for all the scenarios, use the following command:  

	>  smart-runner find-all-missing-stepdefs
	

## Standard Step Definition Details (provided by STAS tool)
To find the standard step definitions supported by the STAS tool, please visit [https://github.com/mkrishna4u/smart-testauto-defaultdefs-en](https://github.com/mkrishna4u/smart-testauto-defaultdefs-en "Smart Testauto Default Definitions for English language") link. If you have configured your project in Eclipse or IntellijIDEA code editor then during scenario writing, inside feature file you can see the code completion for the steps. Only thing is that you have to configure Cucumber plugin and make your project as Cucumber project. Also specify the following paths as glue code.

	stepdefs,org.uitnet.testing.smartfwk.core.stepdefs.en
	
We have different type of standard step definitions files using that you can automate any scenario. For more information, please refer **TBD** document. High level overview is given below:
1.  **SmartStepDefs:** This is a main hook file that defines the @Before and @After methods definition that used to run before each scenario and after each scenario completion respectively.
2. **SmartUiBasicAppOperationsStepDefs:** This defines basic UI operations to perform operations on UI like connect to UI application using specified user profile. User profiles are configured in test-config/apps-config/ directory to its specific app. Also it contains step definitions related to opening URL, take screenshot etc.
3. **SmartUiFormElementOperationsStepDefs:** This defines the UI operations like operations on the form elements / page objects like enter the information on page elements, type text on textbox or textarea, extract information from the element, verify information of the elements (i.e. visibility of the element, default information of the element) etc.
4.  **SmartUiMouseOperationsStepDefs:** This defines the mouse operations related step definitions that user can perform on user interface. It includes operations like click, double click, click hold and release, right click, hoverover etc.
5.  **SmartUiTouchScreenOperationsStepDefs:** This defines touch pad / touch screen operations on user interface like tap, swipe, zoom in, zoom out etc operations.
6.  **SmartUiWindowAndFrameOperationsStepDefs:** This defines the step definitions to handle multiple windows, iframes etc. like switching to windows and switching to frame etc.
7.  **SmartUiKeyboardOperationsStepDefs:** This defines the step definitions to handle keyboard related operations like keydown, keyup, keypress, combo keys operations like (CTRL + a, CTRL + v, CTRL + click) etc.
8.  **SmartFileUploadStepDefs:** This defines file upload related operations on UI.
9.  **SmartApiStepDefs:** This defines API Testing related operations and response data verifications. Like HTTP GET, POST, PUT, DELETE etc.
10.  **SmartDatabaseManagementStepDefs:** This defines Database Management related step definition like to access the database information using SQL, update database information using SQL query etc.
11.  **SmartLocalFileManagementStepDefs:** This defines step definitions to verify the downloaded files from UI or API.
12.  **SmartRemoteFileManagementStepDefs:** This defines step definitions to verify the uploaded files on remote remote server using SSH (Secured Shell) protocol.
13.  **SmartExcelDataManagementStepDefs:** This defines step definitions to read and verify the contents of Excel File.
14.  **SmartCsvDataManagementStepDefs:** This defines step definitions to read and verify the contents of CSV (Comma Separated Value) File.
15.  **SmartJsonDataManagementStepDefs:** This defines step definitions to read and verify the contents of JSON File or data. It also defines the steps to update the JSON data.
16.  **SmartYamlDataManagementStepDefs:** This defines step definitions to read and verify the contents of YAML File or data. It also defines the steps to update the YAML data.
17.  **SmartXmlDataManagementStepDefs:** This defines step definitions to read and verify the contents of XML File or data. It also defines the steps to update the XML data.
18.  **SmartTextualDataManagementStepDefs:** This defines step definitions to read and verify the contents of text File or data.
19.  **SmartVariableManagementStepDefs:** This defines step definitions to read and verify the contents of the variables.
	
## How does STAS tool work?
Using the smart step definition (specified above) you can automate 
1. Any user interface like **web or native user interface**. 
2. Automate **API Testing** like REST APIs.
3. Automate **database testing**.
4. Automate **file download or upload testing**.
5. Manage multiple windows and frames for web based applications.
6. Automate mobile native and web applications (Android and iOS both).

## What if any step definition is not present to complete scenario implementation?
This situation may come, in that case you would have two options:
1. **Raise an enhancement** on [https://github.com/mkrishna4u/smart-testauto-cucumber-defaults-en/issues](https://github.com/mkrishna4u/smart-testauto-cucumber-defaults-en/issues "Issues on Smart TestAuto Cucumber Step Definition") link. The Smart TestAuto team will try to add new generic step definitions that can be reused. NOTE: Time for completion of adding new step definition may not be committed as this is a community project. If anyone would like to join to add new step definitions they would be more welcome to join our team.
2. You can create your step definition file in **src/test/java/stepdefs/** directory and implement your step definitions using smart testauto apis. NOTE: If you are writing your custom step or step definition then you should prefix each step or step definition with **[C]** text to make it independent. Else if framework will add new step and step definition then your custom step or step definitions may collide with framework step definition and may result in duplicate step definition error. For example custom step definition should like this:

	Given [C} your step here.
	When [C] your step here.
	Then [C] your step here.
	And [C] your step here.
	
## STAS Documentation
Documentation is available on the following link. TBD???
	
# License
Apache License, 2.0
 
