package com.testing.api.requests;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonFileReader;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import com.github.javafaker.Faker;


import java.util.List;
import java.util.Locale;

public class ClientRequest extends BaseRequest {
    private static final String ENDPOINT = String.format(Constants.URL, Constants.CLIENTS_PATH);


    /**
     * Creates a new client by sending a POST request
     * @param client Client object containing the details to be created
     * @return Response received from the server
     */
    public Response createClient(Client client) {
        return requestPost(ENDPOINT, createBaseHeaders(), client);
    }

    /**
     * Updates a client by sending a PUT request
     * @param client Client object containing the details to be updated
     * @param clientId The unique identifier of the client to be updated
     * @return Response received from the server
     */
    public Response updateClient(Client client, String clientId) {
        return requestPut(ENDPOINT, createBaseHeaders(), clientId, client);
    }

    /**
     * Deletes a client by sending a DELETE request
     * @param clientId The unique identifier of the client to be deleted
     * @return Response received from the server
     */
    public Response deleteClient(String clientId) {
        return requestDelete(ENDPOINT, createBaseHeaders(), clientId);
    }

    /**
     * Retrieves the Client entity from the HTTP response
     * @param response Response from which to extract the client entity
     * @return Client entity extracted from the response
     */
    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }

    /**
     * Retrieves a list of client entities from the provided response
     *
     * @param response The HTTP response from which to extract the client entities.
     * @return A list of client entities extracted from the response.
     */
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

    public Response createDefaultClient() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createClient(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }


    /**
     * Creates a random client by generating random values for client attributes and
     * sends a POST request to create it.
     *
     * @return Response received from the server
     */
    public Response createRandomClient() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String country = faker.address().country();
        String city = faker.address().cityName();
        String email = faker.internet().emailAddress();
        String phone = faker.phoneNumber().phoneNumber();

        Client client = Client.builder()
                .name(firstName)
                .lastName(lastName)
                .country(country)
                .city(city)
                .email(email)
                .phone(phone)
                .build();
        return this.createClient(client);
    }

    public Client getClientEntity(String clientJson) {
        Gson gson = new Gson();
        return gson.fromJson(clientJson, Client.class);
    }


    @Override
    protected String getEndpoint() {
        return ENDPOINT;
    }
}
