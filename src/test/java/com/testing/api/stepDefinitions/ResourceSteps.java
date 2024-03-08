package com.testing.api.stepDefinitions;

import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class ResourceSteps {
    private static final Logger logger = LogManager.getLogger(ResourceSteps.class);
    private final ResourceRequest resourceRequest = new ResourceRequest();
    private Response response;

    @Given("there are at least {int} registered resources in the system")
    public void thereAreAtLeastRegisteredResourcesInTheSystem(int requiredResourcesAmount) {
        response = resourceRequest.getAll();
        Assert.assertEquals(200, response.statusCode());

        List<Resource> resourceList = resourceRequest.getResponsesEntity(response);

        while(resourceList.size() < requiredResourcesAmount) {
            response = resourceRequest.createRandomResource();

            response = resourceRequest.getAll();
            resourceList = resourceRequest.getResponsesEntity(response);
        }
        logger.info("There are " + resourceList.size() + " registered resources in the system.");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(resourceList.size() >= requiredResourcesAmount);
    }

    @When("I send a GET request to retrieve all the list of resources")
    public void iSendAGETRequestToRetrieveAllTheListOfResources() {
        response = resourceRequest.getAll();
        logger.info("Status code is: " + response.statusCode());
    }

    @Then("the resources response should have a status code of {int}")
    public void theResourcesResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @And("validates the response with resource list JSON schema")
    public void validatesTheResponseWithResourceListJSONSchema() {
        String path = "schemas/resourceListSchema.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, path));
        logger.info("Schema from resources list was validated");
    }
}
