@active
Feature: Resource testing CRUD

  @smoke
  Scenario: Get the list of resources
    Given there are at least 5 registered resources in the system
    When I send a GET request to retrieve all the list of resources
    Then the resources response should have a status code of 200
    And validates the response with resource list JSON schema

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
    "active": true
  }
  """
    Then the resources response should have a status code of 200
    And the response should have the following details:
      | Name | Trademark | Stock | Price | Description | Tags | Active |
      | newName | newTradeMark | 1000 | 99.99 | description | NewTag | true |
    And validates the response with the resource JSON schema
