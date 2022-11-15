# Smart Software Testing Automation Framework (smart-testauto-framework)
**Data driven End-to-End testing (UI, API, Database and Remote Machine Testing) is made easy. Develop testcases for one environment and run the same testcases on different environments.**

**Appium version 8.1.1 or greater is not compatible with WinAppDriver (from microsoft) so currently Windows native app automation will not work using selenium mechanism but work using *SI page object classes that uses SikuliX**

A software testing automation framework that can be used to smartly test any type of application softwares (i.e. Web Applications, Native Applications, REST Server) that utilize the functionality of the following Java based software testing automation tools: Cucumber, Selenium, SikulliX, Appium etc. It supports **LOW CODE** strategy for easy maintenance of steps definitions and standardizing the way of configuring application data, writing the test scenarios and step definitions.

This tool supports the **real environment** for software testing (Similar way our manual tester performs software testing like data preparation, run test cases (data-driven), data verification and generate reports etc.). Here using this tool we can automate data preparation, test cases execution, data verification and report generation easily.

Using this software we can easily test the following functionality of the software applications:

1.  **Web User Interface** running on different web browsers (like Chrome, Firefox etc.) on different platforms (like windows, mobile, mac, linux, android, iOS etc.)
2.  **Native applications** like **Desktop applications** (like calculator), **Mobile applications** (like calculator)
3.  **Visualization testing / Image recognition testing** (like image based testing using *SI object classes like ImageSI, ButtonSI, TextboxSI object classes etc.). NOTE: To perform visualization testing, application must be launched and visible on PC. Visualization testing can not be performed on headless web browsers.
4.  Perform **Data Driven Testing (DDT)** on user interface against your relational database i.e. Oracle, MySQL, Postgres, MariaDB etc. Use **SmartDatabaseManager** class to perform CRUD operations on database. **SqlDatabaseActionHandler** can be registered with database profiles to perform CRUD operations on relational/SQL databases.
5.  Perform **Data Driven Testing (DDT)** using excel, CSV data, JSON, YAML and XML data files. Reads excel (.xlsx and .xls) files data using **ExcelFileReader** class. Reads CSV file data using **CSVFileReader** class.
6. Perform **Behavior Driven Testing(BDT)** using Cucumber Gherkin feature files.
7.  **API Testing (REST API)** using **SmartApiTestManager** **AbstractApiActionHandler** class. This class maintains sessions and supports customizable login(..) and logout() APIs. Create your own class that extends AbstractApiActionHandler class and use it to perform HTTP operations like GET, POST, PUT, DELETE etc. These api action handler class can be registered with ApiConfig.yaml file.
8.  Read JSON data using **JsonDocumentReader**. It uses the JSON Path information available on https://github.com/json-path/JsonPath
9. Validate JSON data using **JsonDocumentValidator** class. It uses the JSON Path information available on https://github.com/json-path/JsonPath
10.  Read YAML data using **YamlDocumentReader**. It uses the JSON Path information available on https://github.com/json-path/JsonPath
11. Validate YAML data using **YamlDocumentValidator** class. It uses the JSON Path information available on https://github.com/json-path/JsonPath
12.  Read XML data using **XmlDocumentReader**. It uses the XML Path information available on https://www.w3.org/TR/1999/REC-xpath-19991116/
13. Validate XML data using **XmlDocumentValidator** class. It uses the XML Path info
14. <a href="https://quartconsulting.com/smart-test-automation-framework/">Smart Test Automation Framework</a>
15. rmation available on https://www.w3.org/TR/1999/REC-xpath-19991116/
16. <a href="https://quartconsulting.com/automation-testing-tools/">Automation Testing Tools</a>
17. **Configure multiple applications** in a single project and write test scenarios that involves communication among multiple applications and automate test steps in a simple way by adding less code.
18. Support standard way of configuration for applications, application user profiles and application relational database profiles.
19. Write scenarios and its definitions once for any platform and run the same scenarios and definitions on any other platforms for any of the software application without changing the scenario and the step definition (Note the behavior of the application on different platforms should be same but locator may change). In this case you can attach multiple locators (platform specific) to a single UI control (like textbox, buttons etc.).
20. Use **SmartCucumberUiScenarioContext** cucumber CDI (Constructor Dependency Injection) class for UI test step definition and **SmartCucumberApiScenarioContext** cucumber CDI (Constructor Dependency Injection) class for API step definition to share the information from one step to another step in a same scenario and different step definition class files.
21. Platform specific sample driver configuration files (AppDriver.yaml) are present in the following directory **sample-config/apps-drivers**. Copy specific file in your application config directory like **test-config/apps-config/[app-name]/driver-configs** and also copy your application file like ?.app, ?.apk ?.exe, or ?.api etc.
22. Use **TestDataBuilder** class to build randomized test data of any length that may include alphabets, numbers, special characters, newline, whitespaces, leading characters etc.
23. Use **FieldValidator** class to validate the field value as per the expected value or criteria. Also **StringUtil** class is very handy to check the textual / string data.
24. Use **HttResponseValidator** as part of API **HttpResponse** to validate the HTTP response. You can validate the contents of the files like any file .docx, .xlsx, .pptx, .pdf, .gif, .jpeg, .jpg, .png etc.. Many file formats are supported. Also you can validate the HTTP response payload using different validators like **JsonDocumentValidator**, **YamlDocumentValidator**, **XmlDocumentValidator** etc..
25. Use **DownloadedFileValidator** class to validate the downloaded file (downloaded by web browser). If you want to validate the contents of any file (like .docx, .xlsx, .pptx, .pdf, .gif, .jpeg, .jpg, .png etc..) use **FileContentsValidator** class.
26. Use **SmartRemoteMachineManager** class is used to talk to remote machines / servers using SSH to perform uploaded file verifications, execute remote command or download remote file etc.
27. **Multiple environment support**, means same testcases can be executed on different environments like **development, acceptance, pre-production and different web browsers etc.** without changing the code. To do so create environment files in **test-config/apps-config/[app-name]/environments/** directory and also create the different **AppDriver-[env-name].yaml** (if using different app driver), different database profiles in **database-profiles/** (if different database connection required) and different **ApiConfig-[env-name].yaml** in **api-configs/** (if different parameters are used for different environment). During testcase execution time, specify the following system property in **mvn** command **"-Dapps.active.environment=[app-name1]:[environment-name],[app-name2]:[environment-name]"** to activate the environment for the applications configured in project.

This is also a **Configuration Driven Testing (CDT)** framework. Sample configurations are present here that can be used under ./test-config directory to configuration your project environment: 
https://github.com/mkrishna4u/smart-testauto-framework/tree/main/src/main/resources/sample-config

## Integrated software testing tools (High level)
1. Cucumber
2. Selenium
3. SikuliX
4. Appium (For mobile testing)
5. TestNG
6. Maven
7. Relational Database ORM tools
8. Excel / CSV file reader tools
9. Java / JDK: Minimum Version Required = 11
10. Secured Shell (SSH/SFTP)
11. Tesseract-OCR (Ref Link: https://tesseract-ocr.github.io/tessdoc/) - Must be installed separately and environment PATH variable must have the path of this installed directory to perform image, audio and video file content matching. **FileContentsValidator** class will only work along with Tesseract-OCR. 
 
This framework removes the complexity of all other software tools and provides a **Single Homogeneous Platform** for testing automation. Using that you can automate any app on any platform.
  
## Supported Platform Types
1. windows
2. linux
3. mac
4. android-mobile
5. ios-mobile

## Supported Application Types
1. native-app: Like Desktop applications (like calculator) on any platform like windows, mac, android, iOS etc.
2. web-app: Like applications running on web browsers (like Github UI) on any platform like windows, mac, android, iOS etc.

## Supported Web Browsers
1.  Chrome
2.  Firefox
3.  Edge
4.  Opera
5.  Safari
6.  Internet Explorer
7.  Custom Webdriver (Remote Web driver)
8.  Not Applicable: This is set for native applications.

# How to start with this framework
Use the following github quickstart project and follow the instructions there to setup your testing environment:
1. **smart-testauto-quickstart-java(For NonMobile Web Application):** Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-java>

2. **smart-testauto-quickstart-windows-native-java(For windows-native applications):** Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-windows-native-java>

3. **smart-testauto-quickstart-android-native-java(For android-native applications):** Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-android-native-java>

Work in progress to add more quickstart projects for different platforms...

# Documentation & More Details
Please refer <a href="https://github.com/mkrishna4u/smart-testauto-framework/wiki">wiki</a> pages for more details about this framework.

# License
Apache License, 2.0
