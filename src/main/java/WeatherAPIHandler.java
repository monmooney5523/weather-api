import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WeatherAPIHandler {

    List<City> cities;

    String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherAPIHandler(List<City> cities) {
        this.cities = cities;
    }

    public String getAPIKey(){
        Properties properties = new Properties();
        try(InputStream inputStream = WeatherAPIHandler.class.getClassLoader().getResourceAsStream("app.properties")){
            if(inputStream == null){
                System.out.println("API Key is null");
            }else{
                properties.load(inputStream);
                return properties.getProperty("apiKey");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<WeatherObject> getAllCitiesWeather() {

        ArrayList<WeatherObject> allCitiesWeather = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        String apiKey = getAPIKey();

        if(cities != null) {
            for (City city : cities) {

                String cityName = city.getCityName();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(UriComponentsBuilder.fromHttpUrl(weatherApiUrl).queryParam("q", cityName).queryParam("appid", apiKey).build().toUri())
                        .timeout(Duration.ofSeconds(60))
                        .GET()
                        .build();

                try (HttpClient httpClient = HttpClient.newHttpClient()) {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        JsonNode jsonNode = objectMapper.readTree(response.body());
                        double temp = jsonNode.path("main").path("temp").asDouble();

                        allCitiesWeather.add(new WeatherObject(city.getCityName(), temp));
                    } else {
                        System.out.println("city: " + city.getCityName() + " " + response.body());
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(allCitiesWeather);
        }else {
            System.out.println("No cities have been added to the list, please try adding some cities");
        }
        return allCitiesWeather;
    }

}
