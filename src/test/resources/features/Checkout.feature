@checkout @regression
Feature: E-Commerce Checkout Process
  As a registered customer
  I want to complete a checkout with various payment methods
  So that I can purchase products successfully

  Background:
    Given I am on the "https://www.saucedemo.com" website
    And I log in with valid credentials "standard_user" and "secret_sauce"

  @smoke @happy-path
  Scenario: Successful checkout with single item
    Given I add product "Sauce Labs Backpack" to the cart
    When I navigate to the cart
    And I proceed to checkout
    And I enter shipping details:
      | firstName | lastName | zipCode |
      | John      | Doe      | 10001   |
    And I click on Finish button
    Then I should see the order confirmation message "Thank you for your order!"
    And the order should be saved in order history

  @data-driven
  Scenario Outline: Checkout with multiple product combinations
    Given I add product "<product1>" to the cart
    And I add product "<product2>" to the cart
    When I navigate to the cart
    Then I should see "<expectedCount>" items in the cart
    And the cart total should display correctly
    When I proceed to checkout
    And I enter shipping details:
      | firstName   | lastName   | zipCode   |
      | <firstName> | <lastName> | <zipCode> |
    And I click on Finish button
    Then I should see the order confirmation message "Thank you for your order!"

    Examples:
      | product1              | product2             | expectedCount | firstName | lastName | zipCode |
      | Sauce Labs Backpack   | Sauce Labs Bike Light| 2             | Alice     | Smith    | 90210   |
      | Sauce Labs Bolt T-Shirt | Sauce Labs Fleece Jacket | 2        | Bob       | Johnson  | 30301   |
      | Sauce Labs Onesie     | Sauce Labs Backpack  | 2             | Carol     | Williams | 60601   |

  @negative
  Scenario: Checkout fails with missing shipping information
    Given I add product "Sauce Labs Backpack" to the cart
    When I navigate to the cart
    And I proceed to checkout
    And I leave the first name field empty
    And I click on Continue button
    Then I should see an error message "Error: First Name is required"

  @negative
  Scenario: Checkout fails with missing zip code
    Given I add product "Sauce Labs Backpack" to the cart
    When I navigate to the cart
    And I proceed to checkout
    And I enter partial shipping details with missing zip:
      | firstName | lastName |
      | Jane      | Doe      |
    And I click on Continue button
    Then I should see an error message "Error: Postal Code is required"

  @cart-validation
  Scenario: Remove item from cart during checkout flow
    Given I add product "Sauce Labs Backpack" to the cart
    And I add product "Sauce Labs Bike Light" to the cart
    When I navigate to the cart
    And I remove product "Sauce Labs Bike Light" from the cart
    Then the cart should contain only "1" item
    And the cart total should update correctly
