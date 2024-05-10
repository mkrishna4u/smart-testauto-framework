# Smart Test Auto Studio (STAS)
<i> **A Fully Integrated Testing Automation Platform** </i>

<i>A smart power of testing automation - Standard Test Steps Driven / Codeless / Low Code / Data Wiring Driven / AI Driven / Suggestion Driven / Behavior Driven / Data Driven / Configuration Driven</i>

**STAS** is a very powerful and the smart software testing automation tool that makes End-to-End testing (UI, API, Database, Remote Machine Testing, Local Machine Testing, Messaging Testing) easy on the real integration environments. Develop test scenarios for one environment and run the same scenarios on different environments without changing the test scenarios.

**STAS** provides a way to write **human friendly automated test scenarios** that can be understood by non technical persons and can be easily maintained.

**STAS** is based on **STAF (Smart Testing Automation Framework)** java library. STAF is the brain of STAS tool.

**NOTE:** Appium version 8.1.1 or greater or Selenium 4.2 or Higher are not compatible with WinAppDriver (from microsoft) so currently Windows native application automation will not work using selenium mechanism but work using *SI page object classes that uses SikuliX or you can use RemoteWebDriver for windows native app that is compatible with Selenium 4+.

**NOTE:** Prefer to use "IntelliJ IDEA" code editor to configure STAS project. Install "Cucumber for Java" plugin in it. Import your STAS project in "IntelliJ IDEA" editor you can get step suggestions out of the way while you will write scenarios / scenario outlines in cucumber feature files under "cucumber-testcases/" directory (present under your project directory).

# Sample Scenario Using STAS
It's simple to create the UI/API/DB/Messaging E2E scenarios in Cucumber Gherkin language using STAS Standard Step Definitions. Sample is given below:

![Sample Scenario using STAS](https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/refs/sample-scenario-2.jpg "Sample Scenario using STAS") 
	
For more information on scenario preparation, please refer the following link:

[https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en/blob/main/docs/STAS-StepDefinitions-En-TestDevelopersGuide.pdf](https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en/blob/main/docs/STAS-StepDefinitions-En-TestDevelopersGuide.pdf "STAS Standard Cucumber Step Definitions (English) - Test Developer's Guide") 


# STAS Architecture & Documentation
**STAS** is a well designed testing automation tool that increases the product quality amazingly with less effort and cost. The most important factor of this tool is the faster development of test scenarios and the easy maintenance of test scenarios with less effort which makes it powerful.

**STAS** supports the **Codeless Model / Architecture** by providing the standardized Cucumber/Gherkin based generic step definitions, using that you can create your test scenarios faster and test your application faster.

Please refer the links below to understand STAS tool functionality and how to use it:

[https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/latest/docs/STAS-TestDevelopersGuide.pdf](https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/latest/docs/STAS-TestDevelopersGuide.pdf "STAS Tool - Test Developer's Guide") 

Documentation related to STAS standard cucumber step definitions (English version) (contains Usecases) is present at:

[https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en/blob/main/docs/STAS-StepDefinitions-En-TestDevelopersGuide.pdf](https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en/blob/main/docs/STAS-StepDefinitions-En-TestDevelopersGuide.pdf "STAS Standard Cucumber Step Definitions (English) - Test Developer's Guide") 

![STAS Architecture](https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/refs/stas-architecture.jpg "STAS Architecture") 




# STAS Connectivity and Verifications
![STAS Connectivity](https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/refs/stas-connectivity.jpg "STAS Connectivity") 

# High Level Description
This is a software testing automation framework / tool that can be used to smartly test any type of application softwares (i.e. Web Applications, Native Applications, REST Server) that utilize the functionality of the following Java based software testing automation tools: Cucumber, Selenium, SikulliX, Appium etc. It supports **NO CODE / LOW CODE** strategy for easy maintenance of steps definitions and standardizing the way of configuring application data, writing the test scenarios and step definitions.

This tool supports the **real environment** for software testing automation (Similar way our manual tester performs software testing like data preparation, run test cases (data-driven), data verification and generate reports etc.). Here using this tool we can automate data preparation, test cases execution, data verification and report generation easily.

Using STAF software we can easily test the following functionality of the software applications:

1. **Web User Interface** running on different web browsers (like Chrome, Firefox etc.) on different platforms (like windows, mobile, mac, linux, android, iOS etc.)
2. **Native applications** like **Desktop applications** (like calculator), **Mobile applications** (like calculator)
3. **Visualization testing / Image recognition testing** (like image based testing using *SI object classes like ImageSI, ButtonSI, TextboxSI object classes etc.). NOTE: To perform visualization testing, application must be launched and visible on PC. Visualization testing can not be performed on headless web browsers.
4. Perform **Data Driven Testing (DDT)** on user interface against your relational database i.e. Oracle, MySQL, Postgres, MariaDB< MongoDB etc.
5. Perform **Data Driven Testing (DDT)** using Excel, CSV data, JSON, YAML and XML data files.
6. Perform **Behavior Driven Testing(BDT)** using Cucumber Gherkin feature files.
7.  **API Testing (REST API)** using **SmartApiTestManager** **AbstractApiActionHandler** class. This class maintains sessions and supports customizable login(..) and logout() APIs. Create your own class that extends AbstractApiActionHandler class and use it to perform HTTP operations like GET, POST, PUT, DELETE etc. These api action handler class can be registered with ApiConfig.yaml file.
8. **Configure multiple applications** in a single project and write test scenarios that involves communication among multiple applications and automate test steps in a simple way by adding less code.
9. Support standard way of configuration for applications, application user profiles and application relational database profiles.
10. Write scenarios and its definitions once for any platform and run the same scenarios and definitions on any other platforms for any of the software application without changing the scenario and the step definition (Note the behavior of the application on different platforms should be same but locator may change). In this case you can attach multiple locators (platform specific) to a single UI control (like textbox, buttons etc.).
11. Platform specific sample driver configuration files (AppDriver.yaml) are present in the following directory **src/main/java/resources/org/uitnet/testing/smartfwk/resources/sample-test-config/app-drivers/** directory. Copy specific file in your application config directory like **test-config/apps-config/[app-name]/driver-configs** and also copy your application file like ?.app, ?.apk ?.exe, or ?.api etc. and config into AppDriver.yaml file. When you are specifying any path use the following system variable for project root directory: **${project.root.directory}** to avoid hardcoded path in yaml file.
12. **Multiple environment support**, means same testcases can be executed on different environments like **development, acceptance, pre-production and different web browsers etc.** without changing the code. To do so create environment files in **test-config/apps-config/[app-name]/environments/** directory and also create the different **AppDriver-[env-name].yaml** (if using different app driver), different database profiles in **database-profiles/** (if different database connection required) and different **ApiConfig-[env-name].yaml** in **api-configs/** (if different parameters are used for different environment). During testcase execution time, specify the following system property in **mvn** command **"-Dapps.active.environment=[app-name1]:[environment-name],[app-name2]:[environment-name]"** to activate the environment for the applications configured in project.

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


# How to start with this framework tool
To start with this framework / STAS tool is as easy as could be. 

**Download STAS (smart-testauto-studio.zip) from link:** [https://github.com/mkrishna4u/smart-testauto-framework/releases/tag/7.0.0](https://github.com/mkrishna4u/smart-testauto-framework/releases/tag/7.0.0 "STAS 7.0.0") 

Please refer the link below to understand STAS tool functionality and how to use it:

[https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/latest/docs/STAS-TestDevelopersGuide.pdf](https://github.com/mkrishna4u/smart-testauto-framework/blob/main/smart-testauto-studio/latest/docs/STAS-TestDevelopersGuide.pdf "STAS Tool - Test Developer's Guide") 

There are some sample test projects that uses STAS tool:
1. **smart-testauto-quickstart-java(For NonMobile Web Application):** Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-java>

2. **smart-testauto-quickstart-android-native-java(For android-native applications):** Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-android-native-java>

# STAS Connectors List
Official Connectors are given below:

1. STAS Standard Cucumber Step Definitions (English): https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en

2. STAS MongoDB Connector: https://github.com/mkrishna4u/smart-testauto-mongodb-connector

3. STAS Relational Database (i.e. Oracle, MySQL. MariaDB, PostGres etc.) connector: This is in-built in STAS. You do not need any 3rd party library. Only thing you need is Database Driver specific Maven Dependency.

Example MySQL Database Java Driver Maven Dependency:

	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>8.0.33</version>
	</dependency>
	
Also configure the specific connection settings in **test-config/apps-config/&lt;app-name&gt;/database-profile/sample-db.yaml** file for the specific dialect information as sample given below:

	hibernate.dialect: org.hibernate.dialect.MySQLDialect
	hibernate.connection.driver_class: com.mysql.jdbc.Driver
	hibernate.connection.url: jdbc:mysql://localhost:3306/my-db

Hibernate Dialect information for different type of SQL Database Connection: https://docs.jboss.org/hibernate/orm/6.2/userguide/html_single/Hibernate_User_Guide.html#database-dialect

# Questions and community support
Please use the following link if you have any question and need of community support:

[https://github.com/mkrishna4u/smart-testauto-framework/discussions](https://github.com/mkrishna4u/smart-testauto-framework/discussions "STAS/STAF discussions")


# License
Apache License, 2.0; Copyright &copy; Madhav Krishna & Contributors
