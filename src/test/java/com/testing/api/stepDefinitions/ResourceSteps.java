package com.testing.api.stepDefinitions;

import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ResourceSteps {
    private static final Logger logger = LogManager.getLogger(ResourceSteps.class);
    private final ResourceRequest resourceRequest = new ResourceRequest();
    private Response response;
    private Resource resource;
    private List<Resource> resourceList;

    @Given("there are at least {int} registered resources in the system")
    public void thereAreAtLeastRegisteredResourcesInTheSystem(int requiredResourcesAmount) {
        response = resourceRequest.getAll();
        Assert.assertEquals(200, response.statusCode());

        resourceList = resourceRequest.getResponsesEntity(response);

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

    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheLatestResource(String requestBody) {
        response = resourceRequest.updateResourceFromJsonString(requestBody, resource.getId());
    }

    @Then("the resources response should have a status code of {int}")
    public void theResourcesResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @And("I retrieve the details of the last resource")
    public void iRetrieveTheDetailsOfTheLastResource() {
        resource = resourceList.get(resourceList.size() - 1);
    }

    @And("validates the response with resource list JSON schema")
    public void validatesTheResponseWithResourceListJSONSchema() {
        String path = "schemas/resourceListSchema.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, path));
        logger.info("Schema from resources list was validated");
    }

    @And("the response should have the following details:")
    public void theResponseShouldHaveTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> resourceMap = dataTable.asMaps(String.class, String.class);
        resource = Resource.builder()
                .name(resourceMap.get(0).get("Name"))
                .trademark(resourceMap.get(0).get("Trademark"))
                .stock(Long.parseLong(resourceMap.get(0).get("Stock")))
                .price(Double.parseDouble(resourceMap.get(0).get("Price")))
                .description(resourceMap.get(0).get("Description"))
                .tags(resourceMap.get(0).get("Tags"))
                .isActive(Boolean.parseBoolean(resourceMap.get(0).get("is_active")))
                .build();
        Resource returnedResource = resourceRequest.getResourceEntity(response);

        Assert.assertEquals(resource.getName(), returnedResource.getName());
        Assert.assertEquals(resource.getTrademark(), returnedResource.getTrademark());
        Assert.assertEquals(resource.getStock(), returnedResource.getStock());
        Assert.assertEquals(resource.getPrice(), returnedResource.getPrice(), 0.0);
        Assert.assertEquals(resource.getDescription(), returnedResource.getDescription());
        Assert.assertEquals(resource.getTags(), returnedResource.getTags());
        Assert.assertEquals(resource.isActive(), returnedResource.isActive());
        logger.debug("The response contains the details that were sent in the predefined resource.");
    }

    @And("validates the response with the resource JSON schema")
    public void validatesTheResponseWithTheResourceJSONSchema() {
        String path = "schemas/resourceSchema.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, path));
        logger.info("Schema from resources update response was validated");
    }

}
