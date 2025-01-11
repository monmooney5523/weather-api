import java.util.ArrayList;

public class Main {

    public static void main(String[] args){

        City denver = new City("Denver", "CO", "US");
        City parkCity = new City("Park City", "UT", "US");
        City tahoe = new City("Lake Tahoe", "CA", "US");

        ArrayList<City> cities = new ArrayList<>();
        cities.add(denver);
        cities.add(parkCity);
        cities.add(tahoe);

        WeatherAPIHandler weatherAPIHandler = new WeatherAPIHandler(cities);
        try {
            ArrayList<WeatherObject> weatherResponses = weatherAPIHandler.getAllCitiesWeather();
            WeatherAlertHandler weatherAlertHandler = new WeatherAlertHandler(weatherResponses);
            weatherAlertHandler.sendAlert();

        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println("Finished the exercise");
    }
}
