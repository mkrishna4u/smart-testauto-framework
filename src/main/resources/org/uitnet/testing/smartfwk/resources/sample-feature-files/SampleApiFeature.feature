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
  Scenario: Title of scenario
    Given I want to write a step with precondition
    And some other precondition
    When I complete action
    And some other action
    And yet another action
    Then I validate the outcomes
    And check more outcomes

  @RegressionTest @SmokeTest @SanityTest @TempScenario
  Scenario Outline: Title of your scenario outline
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step

    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |
