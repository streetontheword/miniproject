package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.model.Pet;
import vttp.ssf.miniproject.model.Weather;
import vttp.ssf.miniproject.service.HealthappService;
import vttp.ssf.miniproject.service.WeatherService;
import vttp.ssf.miniproject.service.petImageService;

@Controller
@RequestMapping("/")
public class FirstController {

    @Autowired
    WeatherService weatherSvc;

    @Autowired
    HealthappService healthAppSvc;

    @Autowired
    petImageService petSvc;

    @GetMapping(path = "/")
    public String homePage(Model m) {
        List<Weather> listOfWeather = weatherSvc.getWeather();

        m.addAttribute("listofweather", listOfWeather);

        return "landingpage";
    }

    // to give weather
    @PostMapping("/weather")
    public String getForecast(@RequestBody MultiValueMap<String, String> body, Model m) {
        // System.out.println(body);
        // body.getFirst("area");

        String weather = body.getFirst("area");
        System.out.println(weather);

        List<Weather> listOfWeather = weatherSvc.getWeather();
        m.addAttribute("forecast", weather);
        m.addAttribute("listofweather", listOfWeather);

        return "landingpage";
    }


    @GetMapping(path = "/pets")
    public String getPets(@RequestParam String name, Model m, HttpSession sess) {
        // System.out.println(body);
        Pet newPet = new Pet();
        List<Pet> listOfPets = healthAppSvc.getPets(name);
        sess.setAttribute("username", name); // key is username, value is name
        m.addAttribute("retrievedpets", listOfPets);
        m.addAttribute("pet", newPet);
        m.addAttribute("username", name);
        return "pageofpets";
    }

    @GetMapping(path="/Api")
    public String getApi(HttpSession sess, Model m){
        String humanName = (String) sess.getAttribute("username");
        m.addAttribute("username", humanName);

        return "restapi";
    }
    
    @GetMapping(path="/credits")
    public String getCredits(){
        return "credit";
    }
}
