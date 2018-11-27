package com.tripco.t23.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t23.server.HTTP;
import spark.Request;

import java.util.Random;

public class Write {
    private Poem poem;

    /** Handles poem generation request, creating a new poem object from the poem request.
     * Does the conversion from Json to a Java class before planning the trip.
     * @param request
     */
    public Write (Request request, Bot b) {
        // first print the request
        System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        poem = new Poem();


        try {
            // plan the trip.
            Random r = new Random();
            poem.text = new String[r.nextInt(30)+5];
            for(int i = 0; i<poem.text.length; i++) {
                poem.text[i] = b.nextMessage(r.nextInt(400)+50);
                System.out.println(poem.text[i]);
            }
            poem.title = poem.text[0].substring(0, poem.text[0].indexOf(' '));
            poem.author = "Po Bot";
            poem.year = "2018";
            poem.classification = "generated";
            // log something.
            System.out.println(poem.title);
        } catch(Exception e) {
            System.out.println("Plan failure!");
            e.printStackTrace();
            poem = null;
        }


    }

    /** Handles the response for a Poem object.
     * Does the conversion from a Java class to a Json string.*
     */
    public String getPoem () {
        Gson gson = new Gson();
        return (poem==null) ? "{}" : gson.toJson(poem);
    }
}
