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
     * This is a funtion to create a new element using rest assured
     * @param endpoint api url
     * @param headers a map of headers
     * @param body model object
     * @return Response
     */
    protected Response requestPost(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .body(body)
                          .when()
                          .post(endpoint);
    }

    protected Response requestPut(String endpoint, Map<String, ?> headers, String id, Object body) {
        String updatedEndpoint = endpoint + "/" + id;

        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .body(body)
                .when()
                .put(updatedEndpoint);
    }

    protected Response requestDelete(String endpoint, Map<String, ?> headers) {
        return RestAssured.given()
                          .contentType(Constants.VALUE_CONTENT_TYPE)
                          .headers(headers)
                          .when()
                          .delete(endpoint);
    }

    protected Map<String, String> createBaseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return headers;
    }

    protected abstract  String getEndpoint();

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
