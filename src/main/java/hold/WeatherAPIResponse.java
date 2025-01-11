package hold;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherAPIResponse {

    @JsonProperty("current")
    private Current current;

    public double getTemp(){
        return current.getTemp();
    }

    private static class Current {
        double temp;

        public double getTemp() {
            return temp;
        }
    }
}
