package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.miniproject.model.Weather;
import vttp.ssf.miniproject.service.WeatherService;

@Controller
@RequestMapping("/")
public class FirstController {

    @Autowired
    WeatherService weatherSvc;

    @GetMapping(path = "/")
    public String homePage(Model m) {
        List<Weather> listOfWeather = weatherSvc.getWeather();
        m.addAttribute("listofweather", listOfWeather);
       
        return "landingpage";
    }

    @PostMapping("/here")
    public String getForecast(@RequestBody MultiValueMap<String, String> body,  Model m) {
        // System.out.println(body);
        // body.getFirst("area");
        String weather = body.getFirst("area");
       
        System.out.println(weather);
    
        List<Weather> listOfWeather = weatherSvc.getWeather();
        m.addAttribute("forecast", weather);
        m.addAttribute("listofweather", listOfWeather);
        

        return "landingpage";
    }

}
