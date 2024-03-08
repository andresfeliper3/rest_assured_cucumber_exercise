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
