package vttp.ssf.miniproject.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.miniproject.service.HealthappService;

@RestController
@RequestMapping
public class PetRestController {

    @Autowired
    HealthappService healthAppSvc; 

    @GetMapping(path="/getJson/{username}")
    public ResponseEntity<Map> getPetJson(@PathVariable("username") String id, Model m, HttpSession sess){
    //    healthAppSvc.getValues(id);
    ResponseEntity<Map> respMap = healthAppSvc.getAllPets(id);
       return respMap;
    }
}
