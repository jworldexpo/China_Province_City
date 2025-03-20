package com.jx.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for JSON operations.
 * 
 * @author jx
 */
public class JSONFormatUtils {

    /**
     * Writes an object to a JSON file.
     * 
     * @param object The object to be written as JSON
     * @param path The file path where the JSON will be saved
     */
    public static void jsonWriter(Object object, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(object);
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonOutput);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}