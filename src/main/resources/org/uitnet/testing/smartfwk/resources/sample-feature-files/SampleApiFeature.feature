# Sample API Feature file
# =======================
# NOTE1: All scenarios should be independent means one scenario should not depend on any other 
#        scenario. So that it can run in parallel.
# NOTE2: Apply the appropriate tags on the scenario to group the test scenarios. 
#        Default tags supported by framework: 
#        @Pending: Scenarios that are not implemented called pending. These will get skipped at run time.
#          Once steps are written then this tag should be removed and apply the @TempScenario tag to perform
#          unit test.
#        @TempScenario: Scenarios that are identified as Temp Test scenarios. Generally testers use this 
#          tag during the scenario development time to perform unit testing. Once unit testing done then 
#          remove this tag and apply the appropriate tag given below.
#        @RegressionTest: Scenarios that are identified as Regression Test scenarios.
#        @SmokeTest: Scenarios that are identified as Smoke Test scenarios.
#        @SanityTest: Scenarios that are identified as Sanity Test scenarios.
# @FeatureTag: Feature tag is the where all scenarios listed in this feature file belong to. For example
#        You can say @UserManagement or @UserRoleManagement etc..
# References: Default cucumber step definitions are present at the following Github path:
#        English: https://github.com/mkrishna4u/smart-testauto-cucumber-stepdefs-en/tree/main/src/main/java/org/uitnet/testing/smartfwk/core/stepdefs/en
#        Make sure your project pom.xml file has the correct version of the "smart-testauto-cucumber-stepdefs-en.version" property.
#        NOTE-A: These step definitions are the default step definitions and you can also be able to write your step definition in the following directory:
#          "src/test/java/stepdefs". Recommended to add prefix [C] to all custom steps implemented in your project to avoid conflict with default step 
#          definitions (Provided by Smart Framework). Like:
#            @When [C] make HTTP POST call on '' server using 'B.json' template.
#            @Then [C] then request is successful.

@FeatureTag
Feature: Title of your feature
  Short description of the feature

  @RegressionTest @SmokeTest @SanityTest @TempScenario
  Scenario: [SampleScenario] Verify get users list based on parameters using HTTP GET and verify response contents.
    When make HTTP GET request on target server [AppName="myapp", TargetServer="myapp-services", TargetURL="get-users?country=US&state=VA"] using [UserProfile="StandardUserProfile"] with header info [Accept="application/json"] and variable info [RespVar="HTTP_RESP_VAR"].
    Then verify "HTTP_RESP_VAR" HTTP response contains HTTPStatusCode=200.
    And verify "HTTP_RESP_VAR" HTTP response contains following header information:
      | Header Name  | Expected Value   | Text Match Mechanism               |
      | Content-Type | application/json | ic-exact-match-with-expected-value |
    And verify "HTTP_RESP_VAR" HTTP response contains JSON data with the following expected params information:
      | Parameter/JSON Path                               | Operator    | Expected Information                                         |
      | {path: "$[*].country", valueType: "string-list"}  | =           | US                                                           |
      | {path: "$[*].state", valueType: "string-list"}    | starts-with | {ev: "VA", textMatchMechanism: "starts-with-expected-value"} |
      | {path: "$[*].userName", valueType: "string-list"} | !=          | {ev: ""}                                                     |      
      
  @RegressionTest @SmokeTest @SanityTest @TempScenario
  Scenario Outline: [SampleScenario] Verify get users list based on different USA State using HTTP GET and verify response contents.
    When make HTTP GET request on target server [AppName="myapp", TargetServer="myapp-services", TargetURL="get-users?country=<Country Code>&state=<State Abbreviation>"] using [UserProfile="StandardUserProfile"] with header info [Accept="application/json"] and variable info [RespVar="HTTP_RESP_VAR"].
    Then verify "HTTP_RESP_VAR" HTTP response contains HTTPStatusCode=200.
    And verify "HTTP_RESP_VAR" HTTP response contains following header information:
      | Header Name  | Expected Value   | Text Match Mechanism               |
      | Content-Type | application/json | ic-exact-match-with-expected-value |
    And verify "HTTP_RESP_VAR" HTTP response contains JSON data with the following expected params information:
      | Parameter/JSON Path                               | Operator    | Expected Information                                                           |
      | {path: "$[*].country", valueType: "string-list"}  | =           | <Country Code>                                                                 |
      | {path: "$[*].state", valueType: "string-list"}    | contains    | {ev: "<State Abbreviation>", textMatchMechanism: "starts-with-expected-value"} |
      
    Examples:
      | Country Code    | State Abbreviation |
      | US              | VA                 |
      | US              | MD                 |
      | IN              | UP                 |
      | IN              | HR                 |
      
      
      
