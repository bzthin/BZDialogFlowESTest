package org.example;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.io.File;
import java.io.FileInputStream;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.gson.Gson;

public class App
{
    // Provided by consumers
    private static final String LANGUAGE_CODE = "en-US";
    private static final String SERVICE_ACCOUNT_KEYS_PATH = "key-read-only.json";

    // Provided by us
    private static final String SESSION_ID = "123456789";
    private static final List<String> TEXTS = Arrays.asList(
        "Hello",
        "What is your name?",
        "I know how to code in Java");

    public static void main (String[] args) throws IOException
    {
        System.out.println("Dialog Flow Test!");

        try
        {
            FileInputStream stream = new FileInputStream(new File(SERVICE_ACCOUNT_KEYS_PATH));
            Map<String, QueryResult> res = DetectIntentTexts.Detect(
                TEXTS,
                SESSION_ID,
                LANGUAGE_CODE,
                stream);
        }
        catch (final ApiException e)
        {
            System.out.println("Status: " + e.getStatusCode().getCode() + ", Message: " + e.getStatusCode());
        }
        catch (final Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Map<?, ?> ParseSrcAccKeys(final String path)
    {
        // create a reader
        try (Reader reader = Files.newBufferedReader(Paths.get(path)))
        {
            Gson gson = new Gson();
            // convert JSON file to map
            Map<?, ?> m = gson.fromJson(reader, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : m.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            return m;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new HashMap<String, String>();
    }
}
