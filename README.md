# Weather API Handler

A Java library for handling weather API requests.

## Overview

This library provides a simple and efficient way to handle weather API requests. It allows you to easily retrieve weather data for a list of cities and provides a flexible way to customize the API requests.

## Features

* Handles weather API requests for a list of cities
* Supports customization of API requests through configuration
* Provides a simple and efficient way to retrieve weather data

## Usage

To use this library, simply create an instance of the `WeatherAPIHandler` class and pass in a list of cities and the weather API URL. You can then call the `getAllCitiesWeather` method to retrieve the weather data for the list of cities.

```java
List<City> cities = Arrays.asList(new City("New York"), new City("Los Angeles"));
WeatherAPIHandler handler = new WeatherAPIHandler(cities, "https://api.openweathermap.org/data/2.5/weather");
List<WeatherObject> weatherData = handler.getAllCitiesWeather();
```

I have also created the `WeatherAlertHandler` class which is used to take the responses from the Weather API, determine the average temperature from those locations and provide an alert with the cities above that threshold.
This alert should be served to the weather-api-submission project, which will check for its validity. 

## Dependencies
This library depends on the following dependencies:

Java 11 or later
OkHttp 4.9.0 or later
Jackson 2.12.3 or later
License
This library is licensed under the MIT License.

Contributing
Contributions are welcome! If you would like to contribute to this library, please fork the repository and submit a pull request.

Note: This README.md file excludes the "hold" package as per your request. If you would like to include it, please let me know!
