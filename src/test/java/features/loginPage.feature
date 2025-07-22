@loginPage

Feature: Login with Dummy

  Scenario: success login with user and password
    Given get user information from users endpoint
    When user send request login endpoint
    Then return success login and access token information

  Scenario Outline: fail login with invalid information
    Given get wrong user information
    When user send request login endpoint
    Then return bad request <errorMessage>
    Examples:
      | errorMessage          |
      | "Invalid credentials" |

  Scenario Outline: empty information for users with login
    Given get user information from users endpoint
    When user send empty information request login endpoint
    Then return bad request <errorMessage>
    Examples:
      | errorMessage                     |
      | "Username and password required" |

  Scenario Outline: user doesn't go user page for token
    When user send request login endpoint
    Then return bad request <errorMessage>
    Examples:
      | errorMessage          |
      | "Invalid credentials" |