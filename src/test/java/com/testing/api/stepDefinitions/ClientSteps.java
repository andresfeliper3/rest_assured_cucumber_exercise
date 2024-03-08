package com.testing.api.stepDefinitions;

import com.testing.api.models.Client;
import com.testing.api.requests.ClientRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);

    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;
    private Client client;


    @Given("there are at least {int} registered clients in the system")
    public void thereAreAtLeastRegisteredClientsInTheSystem(int requiredClientsAmount) {

        response = clientRequest.getClients();
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientList = clientRequest.getClientsEntity(response);

        while(clientList.size() < requiredClientsAmount) {
            response = clientRequest.createRandomClient();

            response = clientRequest.getClients();
            clientList = clientRequest.getClientsEntity(response);
        }
        logger.info("There are " + clientList.size() + " registered clients in the system.");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(clientList.size() >= requiredClientsAmount);
    }

    @When("I retrieve all the list of clients")
    public void iRetrieveAllTheListOfClients() {
        response = clientRequest.getClients();
        logger.info("Status code is: " + response.statusCode());
    }

    @Then("the response should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @And("validates the response with client JSON schema")
    public void validatesTheResponseWithClientJSONSchema() {
        String path = "schemas/clientListSchema.json";
        Assert.assertTrue(clientRequest.validateSchema(response, path));
        logger.info("Schema from clients list was validated");
    }
}