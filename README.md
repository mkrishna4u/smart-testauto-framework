# Smart Software Testing Automation Framework (smart-testauto-fwk)
A software testing automation framework that can be used to smartly test any type of application softwares (i.e. Web Applications, Native Applications) that utilize the functionality of the following Java based software testing automation tools: Cucumber, Selenium, SikulliX, Appium etc. It supports **LOW CODE** strategy for easy maintenance of steps definitions and standardizing the way of configuring application data, writing the test scenarios and step definitions.

Using this software we can easily test the following functionality of the software applications:

1.  **Web User Interface** running on different web browsers (like Chrome, Firefox etc.) on different platforms (like windows, mobile, mac, linux, android, iOS etc.)
2.  **Native applications** like **Desktop applications** (like calculator), **Mobile applications** (like calculator)
3.  **Visualization testing / Image recognition testing** (like image based testing using *SI object classes like ImageSI, ButtonSI, TextboxSI object classes etc.). NOTE: To perform visualization testing, application must be launched and visible on PC. Visualization testing can not be performed on headless web browsers.
4.  Perform **Data Driven Testing (DDT)** on user interface against your relational database i.e. Oracle, MySQL, Postgres, MariaDB etc. Use **OrmDatabaseQueryHandler** class to perform CRUD operations on database.
5.  Perform **Data Driven Testing (DDT)** using excel and CSV data. Reads excel (.xlsx and .xls) files data using **ExcelFileReader** class. Reads CSV file data using **CSVFileReader** class.
6.  **API Testing (REST API)** using **AbstractApiTestHelper** class. This class maintains sessions and supports customizable login(..) and logout() APIs.
7.  Read JSON data using **JsonDocumentReader**. It uses the JSON Path information available on https://github.com/json-path/JsonPath
8. Validate JSON data using **JsonDocumentValidator** class. It uses the JSON Path information available on https://github.com/json-path/JsonPath
9. **Configure multiple applications** in a single project and write test scenarios that involves communication among multiple applications and automate test steps in a simple way by adding less code.
10. Support standard way of configuration for applications, application user profiles and application relational database profiles.
11. Write scenarios and its definitions once for any platform and run the same scenarios and definitions on any other platforms for any of the software application without changing the scenario and the step definition (Note the behavior of the application on different platforms should be same but locator may change). In this case you can attach multiple locators (platform specific) to a single UI control (like textbox, buttons etc.).
12. Single class **SmartAppConnector** is used to connect to any configured application in the project. Please refer supported Platform Types, Application Types and Web Browser Types below to more about your application that you may want to perform testing automation.
13. Platform specific sample driver configuration files (AppDriver.properties) are present in the following directory **sample-config/apps-drivers**. Copy specific file in your application config directory like **test-config/apps-config/<app-name>/** and also copy your application file like ?.app, ?.apk ?.exe, or ?.api etc.

## Integrated software testing tools (High level)
1. Cucumber
2. Selenium
3. SikuliX
4. Appium (For mobile testing)
5. TestNG
6. Maven
7. Relational Database ORM tools
8. Excel / CSV file reader tools
 
This framework removes the complexity of all other software tools and provides a *Single Platform* for testing automation. Using that you can automate any app on any platform.
  
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
