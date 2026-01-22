Feature: Login Functionality

  @smoke
  Scenario: Successful Login
    When the user enters the valid username
    And the user enters the valid password
    And the user clicks on login button
    And user enters otp
    And user click on verify button
    Then the user should be logged in successfully
    
  @regression
  Scenario Outline: Login using Excel test data
    When user enters username from excel row <row>
    And user enters password from excel row <row>
    And the user clicks on login button from excel row <row>
    And user enters otp from excel row <row>
    And user click on verify button
    Then the user should be logged in successfully

    Examples: 
      | row |
      |   1 |
      |   2 |
      |   3 |
      |   4 |
