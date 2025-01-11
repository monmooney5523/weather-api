import java.util.ArrayList;

public class WeatherAlert {

    ArrayList<WeatherObject> citiesAboveThreshold;
    ArrayList<WeatherObject> citiesAtOrBelowThreshold;
    double threshold;

    public WeatherAlert(double threshold) {
        this.citiesAboveThreshold = new ArrayList<>();
        this.citiesAtOrBelowThreshold = new ArrayList<>();
        this.threshold = threshold;
    }

    public void setCitiesAboveThreshold(WeatherObject cityAboveThreshold) {
        this.citiesAboveThreshold.add(cityAboveThreshold);
    }

    public void setCitiesAtOrBelowThreshold(WeatherObject cityAtOrBelowThreshold) {
        this.citiesAtOrBelowThreshold.add(cityAtOrBelowThreshold);
    }

    @Override
    public String toString() {
        return "{" +
                "\"citiesAboveThreshold\":" + citiesAboveThreshold.toString() +
                ", \"citiesAtOrBelowThreshold\":" + citiesAtOrBelowThreshold.toString() +
                ", \"threshold\":" + threshold +
                "}";
    }
}
