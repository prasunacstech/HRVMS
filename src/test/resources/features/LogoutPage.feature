Feature: Login Functionality

  Background: Successful Login
    When the user enters the valid username
    And the user enters the valid password
    And the user clicks on login button
    And user enters otp
    And user click on verify button
    Then the user should be logged in successfully
    
  @smoke @regression
  Scenario: Successful LogOut
     When the user clicks on image icon
     And  the user clicks on logout 
     And  the user clicks on logout button
     Then the user should navigate to the loginpage