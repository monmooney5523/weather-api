public class WeatherObject {

    String city;
    double temp;

    public WeatherObject(String city, double temp) {
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public double getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "{\"city\": " + "\"" + city + "\"" + ",\"temp\":" + temp + "}";
    }
}
