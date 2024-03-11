package com.testing.api.stepDefinitions;

import com.testing.api.models.Client;
import com.testing.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ClientSteps {
    private static final String CLIENTS_LIST_SCHEMA_PATH = "schemas/clientListSchema.json";
    private static final String CLIENT_SCHEMA_PATH = "schemas/clientSchema.json";
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);
    private final ClientRequest clientRequest = new ClientRequest();
    private Response response;
    private Client client;


    @Given("there are at least {int} registered clients in the system")
    public void thereAreAtLeastRegisteredClientsInTheSystem(int requiredClientsAmount) {
        response = clientRequest.getAll();
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientList = clientRequest.getClientsEntity(response);

        while(clientList.size() < requiredClientsAmount) {
            response = clientRequest.createRandomClient();

            response = clientRequest.getAll();
            clientList = clientRequest.getClientsEntity(response);
        }
        logger.info("There are " + clientList.size() + " registered clients in the system.");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(clientList.size() >= requiredClientsAmount);
    }

    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable expectedData) {
        List<Map<String, String>> clientMap = expectedData.asMaps(String.class, String.class);
        client = Client.builder()
                .name(clientMap.get(0).get("Name"))
                .lastName(clientMap.get(0).get("LastName"))
                .country(clientMap.get(0).get("Country"))
                .city(clientMap.get(0).get("City"))
                .email(clientMap.get(0).get("Email"))
                .phone(clientMap.get(0).get("Phone"))
                .build();
        logger.info("Predefined client created");
    }

    @When("I send a GET request to retrieve all the list of clients")
    public void iSendAGETRequestToRetrieveAllTheListOfClients() {
        response = clientRequest.getAll();
    }

    @When("I send a POST request to create a client")
    public void iSendAPOSTRequestToCreateAClient() {
        response = clientRequest.createClient(client);
    }

    @Then("the clients response should have a status code of {int}")
    public void theClientsResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Then("the new client request should have a status code of {int}")
    public void theNewClientRequestShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @And("validates the response with client list JSON schema")
    public void validatesTheResponseWithClientJSONSchema() {
        Assert.assertTrue(clientRequest.validateSchema(response, CLIENTS_LIST_SCHEMA_PATH));
        logger.info("Schema from clients list was validated");
    }


    @And("the response should include the details of the created client")
    public void theResponseShouldIncludeTheDetailsOfTheCreatedClient() {
        Client returnedClient = clientRequest.getClientEntity(response);
        Assert.assertEquals(client.getName(), returnedClient.getName());
        Assert.assertEquals(client.getLastName(), returnedClient.getLastName());
        Assert.assertEquals(client.getCountry(), returnedClient.getCountry());
        Assert.assertEquals(client.getCity(), returnedClient.getCity());
        Assert.assertEquals(client.getEmail(), returnedClient.getEmail());
        Assert.assertEquals(client.getPhone(), returnedClient.getPhone());
        Assert.assertTrue(NumberUtils.isCreatable(returnedClient.getId()));
        logger.info("The sent client contains the same details as the predefined client.");

    }

    @And("validates the new client response with the client JSON schema")
    public void validatesTheNewClientResponseWithTheClientJSONSchema() {
        Assert.assertTrue(clientRequest.validateSchema(response, CLIENT_SCHEMA_PATH));
        logger.info("Schema from client creation response was validated");
    }



}