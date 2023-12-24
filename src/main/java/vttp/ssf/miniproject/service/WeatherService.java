package vttp.ssf.miniproject.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.miniproject.model.Weather;

@Service
public class WeatherService {
    

    @Value("${weather.api}")
    private String url_weather;


    RestTemplate template = new RestTemplate(); 
    List<Weather> listOfWeather = new ArrayList<>(); 

    

    public List<Weather> getWeather(){
        RequestEntity<Void> req = RequestEntity.get(url_weather).build();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody().toString();
        // System.out.println("payload: " + payload);
        
        //to read the json from weather api 
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jsonReader.readObject(); 
        JsonArray jsonArray = jsonObj.getJsonArray("items");

        for(JsonValue jsonValue: jsonArray){ 
            JsonObject jsonObject = jsonValue.asJsonObject();
            JsonArray jsonForecast = jsonObject.getJsonArray("forecasts");
            // System.out.println("jsonForecast" + jsonForecast);
            for(JsonValue jsonValue1 : jsonForecast){
                JsonObject jsonArea = jsonValue1.asJsonObject();
                String area = jsonArea.getString("area");
                String forecast = jsonArea.getString("forecast");
                // System.out.println("json area: " + area);
                // System.out.println("forecast in area" + forecast);

                Weather weather = new Weather(); 
                weather.setArea(area);
                weather.setForecast(forecast);
                listOfWeather.add(weather);
            

            } 

        }

        return listOfWeather; 
    }
}
