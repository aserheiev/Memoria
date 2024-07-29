package org.eelcorp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPI2 {
    public static String makeRequest() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("https://api.open-meteo.com/v1/forecast?current=temperature_2m%2Cwind_speed_10m&latitude=52.52&longitude=21.41"))
                .header("Content-Type", "application/json")
                .build();

        // use the client to send the request

        // TODO: Write a Response body handler I guess?????

        // the response:
        return null;
    }

}
