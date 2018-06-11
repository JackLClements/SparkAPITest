Feature: GET /contents should return the cart contents.
  Cart service should be hardcoded and empty.

  Scenario:
    Then the cart should have a subtotal of 0 and an empty list
    Given a GET request is sent to http://localhost:4567/cart/contents