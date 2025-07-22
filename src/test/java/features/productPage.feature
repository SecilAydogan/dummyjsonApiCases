@productPage

Feature: Product Page with Dummy

  Background:
    Given get user information from users endpoint
    When user send request login endpoint
    Then return success login and access token information

  Scenario: count product limit
    Given take token information
    When open product endpoint
    Then return limit count of products