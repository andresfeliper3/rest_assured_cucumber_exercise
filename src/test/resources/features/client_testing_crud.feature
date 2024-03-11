@active
Feature: Client testing CRUD

  @smoke
  Scenario: Get the list of clients
    Given there are at least 3 registered clients in the system
    When I send a GET request to retrieve all the list of clients
    Then the clients response should have a status code of 200
    And validates the response with client list JSON schema

  @smoke
  Scenario: Get the list of resources
    Given there are at least 5 registered resources in the system
    When I send a GET request to retrieve all the list of resources
    Then the resources response should have a status code of 200
    And validates the response with resource list JSON schema

  @smoke
  Scenario: Create a new client
  Given I have a client with the following details:
    | Name | LastName | Country | City | Email | Phone |
    | Andres | Rincon | Colombia | Cali | andres@gmail.com | 3157642345 |
  When I send a POST request to create a client
  Then the new client request should have a status code of 201
  And the response should include the details of the created client
  And validates the new client response with the client JSON schema

  @smoke
  Scenario: Update the last resource
  Given there are at least 5 registered resources in the system
  And I retrieve the details of the last resource
  When I send a PUT request to update the latest resource
  """
  {
    "name": "newName",
    "trademark": "newTradeMark",
    "stock": 1000,
    "price": 99.99,
    "description": "description",
    "tags": "NewTag",
    "is_active": true
  }
  """
  Then the resources response should have a status code of 200
  And the response should have the following details:
    | Name | Trademark | Stock | Price | Description | Tags | Is_active |
    | newName | newTradeMark | 1000 | 99.99 | description | NewTag | true |
  And validates the response with the resource JSON schema