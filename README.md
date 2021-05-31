# Smart Software Testing Automation Framework (smart-testauto-fwk)
A software testing automation framework that can be used to smartly test web application softwares that utilize the functionality of the following Java based software testing automation tools: Cucumber, Selenium, SikulliX
Using this software we can easily test the following functionality of the system:

1.  Web User Interface (Running on different browser, supported by selenium)
2.  Perform data testing on user interface against your relational database i.e. Oracle, MySQL, Postgres, MariaDB etc.
3. Read excel (.xlsx and .xls) files data using ExcelFileReader class.
4.  API Testing (REST API) using AbstractApiTestHelper class. This class maintains sessions and supports customizable login(..) and logout() APIs.
5.  Read JSON data using JsonDocumentReader. It uses the JSON Path information available on https://github.com/json-path/JsonPath
6. Validate JSON data using JsonDocumentValidator class. It uses the JSON Path information available on https://github.com/json-path/JsonPath

## Supported Web Browsers
1.  Chrome
2.  Firefox
3.  Internet Explorer
(Work in progress in supporting other Web Browsers)

# How to start with this framework
Use the **smartfwk-testauto-quickstart-java** github project and follow the instructions there.

#License
Apache License, 2.0