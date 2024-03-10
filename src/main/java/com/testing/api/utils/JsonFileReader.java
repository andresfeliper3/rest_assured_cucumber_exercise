package com.testing.api.utils;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.models.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonFileReader {
    /**
     * This method read a JSON file and deserialize the body into a Client object
     *
     * @param jsonFileName json file location path
     *
     * @return Client : client
     */
    public Client getClientByJson(String jsonFileName) {
        Client client = new Client();
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            client = gson.fromJson(reader, Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    public Resource getResourceByJsonString(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Resource.class);
    }
}
