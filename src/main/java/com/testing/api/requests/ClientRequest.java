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



    public Response createClient(Client client) {
        return requestPost(ENDPOINT, createBaseHeaders(), client);
    }

    public Response updateClient(Client client, String clientId) {
        return requestPut(ENDPOINT, createBaseHeaders(), client);
    }

    public Response deleteClient(String clientId) {
        return requestDelete(ENDPOINT, createBaseHeaders());
    }

    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }

    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

    public Response createDefaultClient() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createClient(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }


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
