package hold;

public class GeocodeAPIHandler {

    //String geocodingApiUrl = "http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit=1&appid=YOUR_API_KEY";

    //    public void fetchCitiesGeolocation(){
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String apiKey = getAPIKey();
//
//        for(City city : cities){
//
//            String cityName = city.getCityName();
//            String encodedCityName = cityName.contains(" ") ? cityName.replace(" ", "%20") : cityName;
//
//            String updatedGeocodeUri = geocodingApiUrl
//                    .replace("{city name}", encodedCityName)
//                    .replace("{state code}", city.getState())
//                    .replace("{country code}", city.getCountry())
//                    .replace("YOUR_API_KEY", apiKey);
//            HttpRequest httpRequest = HttpRequest.newBuilder()
//                    .uri(URI.create(updatedGeocodeUri))
//                    .build();
//
//            try(HttpClient httpClient = HttpClient.newHttpClient()){
//                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//                JsonNode jsonNode = objectMapper.readTree(response.body());
//                JsonNode jsonNodeObject = jsonNode.get(0);
//                double lat = jsonNodeObject.get("lat").asDouble();
//                double longt = jsonNodeObject.get("lon").asDouble();
//
//                updateCityCoordinates(city, lat, longt);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//    }

//    public void updateCityCoordinates(City city, double lat, double longt){
//        city.setLat(lat);
//        city.setLongt(longt);
//    }


}
