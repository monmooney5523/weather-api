package hold;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
public class WeatherAggregator {

    public static void main(String[] args) throws Exception {
        String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid=YOUR_API_KEY";
        List<String> cities = Arrays.asList("New York", "Los Angeles", "Miami");

        // Step 1: Fetch weather data
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Double>> cityTemperatures = new HashMap<>();
        Set<String> hotCities = new HashSet<>();
        for (String city : cities) {
            String url = weatherApiUrl.replace("{city}", city);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            Map<String, Object> weatherData = mapper.readValue(response.body(), Map.class);
            Map<String, Object> mainData = (Map<String, Object>) weatherData.get("main");
            double tempInCelsius = (Double) mainData.get("temp") - 273.15; // Convert Kelvin to Celsius

            // Step 2: Process data
            cityTemperatures.putIfAbsent(city, new ArrayList<>());
            cityTemperatures.get(city).add(tempInCelsius);
            if (tempInCelsius > 20) {
                hotCities.add(city);
            }
        }

        // Step 3: Calculate averages
        Map<String, Double> averageTemperatures = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : cityTemperatures.entrySet()) {
            List<Double> temps = entry.getValue();
            averageTemperatures.put(entry.getKey(), temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
        }
        // Step 4: Prepare JSON for submission
        Map<String, Object> processedData = new HashMap<>();
        processedData.put("averages", averageTemperatures);
        processedData.put("hotCities", hotCities);
        String processedJson = mapper.writeValueAsString(processedData);

        // Step 5: Send data back
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/weather-summary"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(processedJson))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response from server: " + postResponse.body());
    }
}
