package com.testing.api.utils;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.models.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonReader {

    private final Gson gson;

    public JsonReader() {
        this.gson = new Gson();
    }

    /**
     * Reads a JSON file and deserializes the content into a Client object.
     *
     * @param jsonFileName The file location path of the JSON file
     * @return The Client object deserialized from the JSON file
     */
    public Client getClientByJsonFileName(String jsonFileName) {
        Client client = new Client();
        try (Reader reader = new FileReader(jsonFileName)) {
            client = gson.fromJson(reader, Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * Deserializes a JSON string into a Resource object.
     *
     * @param jsonString The JSON string to deserialize
     * @return The Resource object deserialized from the JSON string
     */
    public Resource getResourceByJsonString(String jsonString) {
        return gson.fromJson(jsonString, Resource.class);
    }
}
