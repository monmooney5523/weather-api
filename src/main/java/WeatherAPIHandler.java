import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class WeatherAPIHandler {

    City[] cities;

    String geocodingApiUrl = "http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit=1&appid=YOUR_API_KEY";
    //String weatherApiUrl = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&appid=YOUR_API_KEY";
    String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid=YOUR_API_KEY";

    public WeatherAPIHandler(City[] cities) {
        this.cities = cities;
    }

    public void getAllCitiesLatAndLongt(){

        ObjectMapper objectMapper = new ObjectMapper();

        String apiKey = getAPIKey();

        for(City city : cities){

            String cityName = city.getCityName();
            String encodedCityName = cityName.contains(" ") ? cityName.replace(" ", "%20") : cityName;

            String updatedGeocodeUri = geocodingApiUrl
                    .replace("{city name}", encodedCityName)
                    .replace("{state code}", city.getState())
                    .replace("{country code}", city.getCountry())
                    .replace("YOUR_API_KEY", apiKey);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(updatedGeocodeUri))
                    .build();

            try(HttpClient httpClient = HttpClient.newHttpClient()){
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                JsonNode jsonNode = objectMapper.readTree(response.body());
                JsonNode jsonNodeObject = jsonNode.get(0);
                double lat = jsonNodeObject.get("lat").asDouble();
                double longt = jsonNodeObject.get("lon").asDouble();

                setLatAndLong(city, lat, longt);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void setLatAndLong(City city, double lat, double longt){
        city.setLat(lat);
        city.setLongt(longt);
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

    public ArrayList<WeatherObject> getAllCitiesWeather() throws URISyntaxException {

        ArrayList<WeatherObject> allCitiesWeather = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        String apiKey = getAPIKey();

        //getAllCitiesLatAndLongt();

        for(City city : cities) {

            String cityName = city.getCityName();
            String encodedCityName = cityName.contains(" ") ? cityName.replace(" ", "%20") : cityName;

            String newCityUri = weatherApiUrl
                    .replace("{city}", encodedCityName)
                    .replace("YOUR_API_KEY", apiKey);

//            String newCityUri = weatherApiUrl
//                    .replace("{lat}", String.valueOf(city.getLat()))
//                    .replace("{lon}", String.valueOf(city.getLongt()))
//                    .replace("YOUR_API_KEY", apiKey);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(newCityUri))
                    .timeout(Duration.ofSeconds(60))
                    .GET()
                    .build();

            try(HttpClient httpClient = HttpClient.newHttpClient()){
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if(response.statusCode() == 200) {
                    JsonNode jsonNode = objectMapper.readTree(response.body());
                    double temp = jsonNode.get("main").get("temp").asInt();

                    allCitiesWeather.add(new WeatherObject(city.getCityName(), temp));
                    //WeatherAPIResponse apiResponseObj = objectMapper.readValue(response.body(), hold.WeatherAPIResponse.class);
                    //allApiResponse.add(apiResponseObj);
                }else{
                    //System.out.println("Status code: " + response.statusCode());
                    System.out.println("city: " + city.getCityName() + " " + response.body());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(allCitiesWeather);
        return allCitiesWeather;
    }

}
