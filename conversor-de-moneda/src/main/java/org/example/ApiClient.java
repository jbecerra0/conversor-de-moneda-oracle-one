
package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private final String apiKey;

    public ApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public double getExchangeRate(String base, String target) {
        URI uri = URI.create(String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, base, target));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if ("success".equals(jsonObject.get("result").getAsString())) {
                return jsonObject.get("conversion_rate").getAsDouble();
            } else {
                throw new RuntimeException("Error al obtener el tipo de cambio: " + jsonObject.get("error-type").getAsString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la consulta: " + e.getMessage(), e);
        }
    }
}
