package com.main.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONUtils {

    public static JSONObject getSectionFromJsonFile(String sectionName, String filename) {

        String content;
        JSONObject jsonObject = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            jsonObject = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject sectionValue = ((JSONObject) jsonObject.get(sectionName));
        return sectionValue;
    }

    public static String getJSONArrayAsString(String jsonString, String key) {
        return getJSONArrayFromJSONString(jsonString, key)
                .toString();
    }


    public static JSONArray getJSONArrayFromJSONString(String jsonString, String key) {
        return getJSONObjectFromJsonString(jsonString)
                .getJSONArray(key);

    }

    public static String getValueForAKeyFromJSON(String jsonString, String key) {
        return getJSONObjectFromJsonString(jsonString).getString(key);
    }

    public static JSONObject getJSONObjectFromJsonString(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject;
    }

    public static String getValueFromJSONArray(JSONArray jsonArray, int index, String key) {
        return jsonArray.getJSONObject(index).getString(key);
    }
}
