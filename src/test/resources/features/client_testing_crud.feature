@active
Feature: Client testing CRUD

  @smoke
  Scenario: Get the list of of clients
    Given there are at least 3 registered clients in the system
    When I retrieve all the list of clients
    Then the response should have a status code of 200
    And validates the response with client JSON schema


