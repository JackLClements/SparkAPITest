Feature: POST /clear empty the cart of all contents.
  Cart service should be hardcoded in memory.

  Scenario:
    Given a POST /add request is sent to http://localhost:4567/cart/add followed by a POST /clear
    Then the cart should be empty