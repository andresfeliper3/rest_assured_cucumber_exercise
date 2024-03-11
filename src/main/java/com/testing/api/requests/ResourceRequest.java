package com.testing.api.requests;

import com.testing.api.models.Resource;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonReader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceRequest extends BaseRequest {
    private static final String ENDPOINT = String.format(Constants.URL, Constants.RESOURCES_PATH);

    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }

    /**
     * Creates a new resource by sending a POST request
     * @param resource Resource object containing the details to be created
     * @return Response received from the server
     */
    public Response createResource(Resource resource) {
        return requestPost(ENDPOINT, createBaseHeaders(), resource);
    }

    /**
     * Updates a resource by sending a PUT request
     * @param resource Resource object containing the details to be updated
     * @param resourceId The unique identifier of the resource to be updated
     * @return Response received from the server
     */
    public Response updateResource(Resource resource, String resourceId) {
        return requestPut(ENDPOINT, createBaseHeaders(), resourceId, resource);
    }

    /**
     * Retrieves the Resource entity from the HTTP response
     * @param response Response from which to extract the resource entity
     * @return Resource entity extracted from the response
     */
    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    /**
     * Retrieves a list of resource entities from the provided response
     *
     * @param response The HTTP response from which to extract the resource entities.
     * @return A list of client entities extracted from the response.
     */
    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    /**
     * Updates a resource element by sending a PUT request and using a string body request
     * @param jsonString Body request as a string
     * @param resourceId Unique identifier of the resource to update
     * @return Response received from the server
     */
    public Response updateResourceFromJsonString(String jsonString, String resourceId) {
        JsonReader jsonFile = new JsonReader();
        return this.updateResource(jsonFile.getResourceByJsonString(jsonString), resourceId);
    }

    /**
     * Creates a random resource by generating random values for resource attributes and
     * sends a POST request to create it.
     *
     * @return Response received from the server
     */
    public Response createRandomResource() {
        String name = faker.commerce().productName();
        String trademark = faker.company().name();
        long stock = Long.parseLong(faker.random().hex(8));
        double price = Double.parseDouble(faker.commerce().price());
        String description = faker.lorem().sentence();
        String tags = faker.lorem().words(7).toString();
        boolean isActive = false;

        Resource resource = Resource.builder()
                .name(name)
                .trademark(trademark)
                .stock(stock)
                .price(price)
                .description(description)
                .tags(tags)
                .active(isActive)
                .build();
        return this.createResource(resource);
    }
}
