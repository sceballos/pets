package net.project.pets.util;

import net.project.pets.data.Config;
import net.project.pets.data.Pet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonFormatter {

    /**
     * @param rawData
     * read data of config Json and return Config object
     */

    public static Config getDataFromConfigJson(String rawData) {

        Config storeConfig;

        boolean chatEnabled = false;
        boolean callEnabled = false;
        String workingHours = "No working hours available";


        try {
            JSONObject configJson = new JSONObject(rawData);
            JSONObject settings = configJson.getJSONObject("settings");

            try {
                chatEnabled = settings.getBoolean("isChatEnabled");
            } catch (JSONException e) {
                chatEnabled = false;
            }

            try {
                callEnabled = settings.getBoolean("isCallEnabled");
            } catch (JSONException e) {
                callEnabled = false;
            }

            try {
                workingHours = settings.getString("workHours");
            } catch (JSONException e) {
                workingHours = "No working hours available";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        storeConfig = new Config(chatEnabled, callEnabled, workingHours);

        return storeConfig;
    }

    /**
     * @param rawData
     * read data of pets data Json and return ArrayList object with data
     */

    public static ArrayList<Pet> getDataFromPetsJson(String rawData) {

        ArrayList<Pet> pets = new ArrayList<>();

        try {
            JSONObject petsJson = new JSONObject(rawData);
            JSONArray petsArray = petsJson.getJSONArray("pets");

            for (int i = 0; i < petsArray.length();i++) {
                JSONObject pet = petsArray.getJSONObject(i);

                String name = "";
                String imageUrl = "";
                String contentUrl = "";
                String dateAdded = "";

                try {
                    name = pet.getString("title");
                } catch (JSONException e) {
                    name = "";
                }

                try {
                    imageUrl = pet.getString("image_url");
                } catch (JSONException e) {
                    imageUrl = "";
                }

                try {
                    contentUrl = pet.getString("content_url");
                } catch (JSONException e) {
                    contentUrl = "";
                }

                try {
                    dateAdded = pet.getString("date_added");
                } catch (JSONException e) {
                    dateAdded = "";
                }

                Pet petObject = new Pet(name, imageUrl, contentUrl, dateAdded);
                pets.add(petObject);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pets;
    }

}
