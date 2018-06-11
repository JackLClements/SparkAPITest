Feature: POST /add should return the updated cart contents.
  Cart service should be hardcoded in memory.

  Scenario:
    Given a POST request is sent to http://localhost:4567/cart/add
    Then the cart should have one item included with correct values