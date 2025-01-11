import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class WeatherAlertHandler {

    double threshold = 0.0;
    ArrayList<WeatherObject> allWeatherObjects;

    public WeatherAlertHandler(ArrayList<WeatherObject> allWeatherObjects) {
        this.allWeatherObjects = allWeatherObjects;
        setThreshold();
    }

    public double calculateAverageTemperature() {
        double sumOfAll = 0.0;

        for (WeatherObject cityWeather : allWeatherObjects) {
            sumOfAll += cityWeather.getTemp();
        }

        return sumOfAll / allWeatherObjects.size();
    }

    public void setThreshold() {
        this.threshold = calculateAverageTemperature();
    }

    public void sendAlert() {
        WeatherAlert weatherAlert = new WeatherAlert(threshold);

        for(WeatherObject weatherObject : allWeatherObjects){
            if(weatherObject.getTemp() <= threshold){
                weatherAlert.setCitiesAtOrBelowThreshold(weatherObject);
            }else{
                weatherAlert.setCitiesAboveThreshold(weatherObject);
            }
        }

        System.out.println(weatherAlert);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/submit"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(weatherAlert.toString()))
                .build();

        try(HttpClient httpClient = HttpClient.newHttpClient()){
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                System.out.println("response sent successfully");
            } else {
                System.out.println("response failed to send, please review");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
