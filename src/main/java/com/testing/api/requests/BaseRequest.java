package com.testing.api.requests;

import com.github.javafaker.Faker;
import com.testing.api.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseRequest {

    Faker faker = new Faker(new Locale("es_CO"));


    protected Response requestGet(String endpoint, Map<String, ?> headers) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .when()
                          .get(endpoint);
    }

    public Response getAll() {
        return requestGet(getEndpoint(), createBaseHeaders());
    }

    /**
     * Sends a POST request to create a new element using Rest Assured.
     * @param endpoint API URL
     * @param headers A map of headers
     * @param body Model object representing the request body
     * @return Response received from the server
     */
    protected Response requestPost(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .post(endpoint);
    }


    /**
     * Sends a PUT request to update an existing element using Rest Assured.
     * @param endpoint API URL
     * @param headers A map of headers
     * @param id The unique identifier of the record to update
     * @param body Model object representing the request body
     * @return Response received from the server
     */
    protected Response requestPut(String endpoint, Map<String, ?> headers, String id, Object body) {
        String endpointWithId = endpoint + "/" + id;

        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .body(body)
                .when()
                .put(endpointWithId);
    }

    /**
     * Sends a DELETE request to delete an element using Rest Assured.
     * @param endpoint API URL
     * @param headers  A map of headers
     * @param id The unique identifier of the record to delete
     * @return Response received from the server
     */
    protected Response requestDelete(String endpoint, Map<String, ?> headers, String id) {
        String endpointWithId = endpoint + "/" + id;

        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .when()
                          .delete(endpointWithId);
    }

    /**
     * Creates a map of base headers
     * @return Map containing the base headers as key-value pairs
     */
    protected Map<String, String> createBaseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return headers;
    }

    protected abstract  String getEndpoint();

    /**
     * Validates the response against a JSON schema
     * @param response HTTP response
     * @param schemaPath path to the JSON schema file
     * @return True if the response passes the JSON schema validation
     */
    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true; // Return true if the assertion passes
        } catch (AssertionError e) {
            // Assertion failed, return false
            return false;
        }
    }

}
