package com.testing.api.requests;

import com.testing.api.models.Resource;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonFileReader;
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

    public Response createResource(Resource resource) {
        return requestPost(ENDPOINT, createBaseHeaders(), resource);
    }

    public Response updateResource(Resource resource, String resourceId) {
        return requestPut(ENDPOINT, createBaseHeaders(), resourceId, resource);
    }

    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    public List<Resource> getResponsesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    public Response updateResourceFromJsonString(String jsonString, String resourceId) {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.updateResource(jsonFile.getResourceByJsonString(jsonString), resourceId);
    }
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
