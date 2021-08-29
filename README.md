# Smart Software Testing Automation Framework (smart-testauto-fwk)
A software testing automation framework that can be used to smartly test web application softwares that utilize the functionality of the following Java based software testing automation tools: Cucumber, Selenium, SikulliX
Using this software we can easily test the following functionality of the system:

1.  Web User Interface (Running on different browser, supported by selenium)
2.  Perform data testing on user interface against your relational database i.e. Oracle, MySQL, Postgres, MariaDB etc. Use **OrmDatabaseQueryHandler** class to perform CRUD operations on database.
3. Read excel (.xlsx and .xls) files data using **ExcelFileReader** class. Read CSV file data using **CSVFileReader** class.
4.  API Testing (REST API) using **AbstractApiTestHelper** class. This class maintains sessions and supports customizable login(..) and logout() APIs.
5.  Read JSON data using **JsonDocumentReader**. It uses the JSON Path information available on https://github.com/json-path/JsonPath
6. Validate JSON data using **JsonDocumentValidator** class. It uses the JSON Path information available on https://github.com/json-path/JsonPath

## Supported Web Browsers
1.  Chrome
2.  Firefox
3.  Edge
4.  Opera
5.  Safari
6.  Internet Explorer
7.  Custom Webdriver (Remote Web driver)

(Work in progress in supporting other Web Browsers)

# How to start with this framework
Use the **smartfwk-testauto-quickstart-java** github project and follow the instructions there.
Github URL: <https://github.com/mkrishna4u/smart-testauto-quickstart-java>

#License
Apache License, 2.0