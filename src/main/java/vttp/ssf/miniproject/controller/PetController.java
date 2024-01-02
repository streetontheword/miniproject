package vttp.ssf.miniproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.Cat;
import vttp.ssf.miniproject.model.Dog;
import vttp.ssf.miniproject.model.Pet;
import vttp.ssf.miniproject.model.Weather;
// import vttp.ssf.miniproject.service.DogService;
import vttp.ssf.miniproject.service.HealthappService;
import vttp.ssf.miniproject.service.WeatherService;
import vttp.ssf.miniproject.service.petImageService;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    // @Autowired
    // DogService dogSvc;
    @Autowired
    WeatherService weatherSvc; 

    @Autowired
    HealthappService healthAppSvc;

    @Autowired
    petImageService petSvc;

    // later
    // do after person login
    @GetMapping("/add")
    public String getPets(Model m) {
        // Pet pet = new Pet();
        // // List<String> dogBreeds = dogSvc.getDogBreeds();

        // List<Dog> listOfDogs = petSvc.getDogBreeds();
        // List<Cat> listOfCat = petSvc.getCatBreeds();
        // m.addAttribute("pet", pet);
        // m.addAttribute("listofdogbreeds", listOfDogs);
        // m.addAttribute("listofcatbreeds", listOfCat);

        return "catordog";
    }

    @PostMapping("/pet")
    public String getSpecies(@RequestBody MultiValueMap<String, String> body, Model m, HttpSession sess) {
        Pet pet = new Pet();
        String breed = body.getFirst("breed");
        m.addAttribute("pet", pet);

        if (breed.equals("dog")) {

            List<Dog> listOfDogs = petSvc.getDogBreeds();
            m.addAttribute("listofbreeds", listOfDogs);
            String breeds = body.getFirst("breed");
            sess.setAttribute("breed", breeds);
            return "petinfo";

        } else if (breed.equals("cat")) {
            List<Cat> listOfCat = petSvc.getCatBreeds();
            m.addAttribute("listofbreeds", listOfCat);
            String breeds = body.getFirst("breed");
            sess.setAttribute("breed", breeds);

        }

        return "petinfo";
    }

    @PostMapping("/postInfo")
    public String postAddedInfo(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m) {

        m.addAttribute("pet", pet);
        if (result.hasErrors()) {
            String breed = (String) sess.getAttribute("breed");
            if (breed.equals("dog")) {
                List<Dog> listOfDogs = petSvc.getDogBreeds();
                m.addAttribute("listofbreeds", listOfDogs);
            } else if (breed.equals("cat")) {
                List<Cat> listOfCat = petSvc.getCatBreeds();
                m.addAttribute("listofbreeds", listOfCat);
            }
            return "petinfo";
        }

        String humanName = (String) sess.getAttribute("username");
        String petId = healthAppSvc.getRandomHexString(8);
        pet.setId(petId);
        String breed = (String) sess.getAttribute("breed");
        if (breed.equals("dog")) {
            pet.setImageId(petSvc.getImageId(pet));
            healthAppSvc.savePets(humanName, petId, pet);
            m.addAttribute("pet", pet);
            sess.setAttribute("breed", breed);
            return "redirect:/pets?name=" + humanName;

        } else if (breed.equals("cat")) {
            pet.setImageId(petSvc.getCatImage(pet));
            healthAppSvc.savePets(humanName, petId, pet);
            sess.setAttribute("breed", breed);
            m.addAttribute("pet", pet);

        }
        return "redirect:/pets?name=" + humanName;
        // petSvc.getImageId(pet);
        // pet.setImageId(petSvc.getImageId(pet));
        // pet.setImageId(petSvc.getCatImage(pet));
        // healthAppSvc.savePets(humanName, petId, pet);
        // System.out.println("postInfo " + petId);

        // m.addAttribute("pet", pet);

        // return "redirect:/pets?name=" + humanName;
    }

    // @GetMapping("/dog")
    // public String getDog(Model m) {
    // Pet pet = new Pet();
    // List<Dog> listOfDogs = petSvc.getDogBreeds();
    // m.addAttribute("listofdogbreeds", listOfDogs);
    // m.addAttribute("pet", pet);
    // return "petinfo";
    // }

    // @PostMapping("/postInfoDog")
    // public String postAddedInfoDog(@Valid @ModelAttribute Pet pet, BindingResult
    // result, HttpSession sess, Model m) {
    // if (result.hasErrors()) {
    // m.addAttribute("pet", pet);
    // List<Dog> listOfDogs = petSvc.getDogBreeds();

    // m.addAttribute("listofdogbreeds", listOfDogs);
    // return "petinfo";

    // }
    // String humanName = (String) sess.getAttribute("username");
    // String petId = healthAppSvc.getRandomHexString(8);
    // pet.setId(petId);
    // // petSvc.getImageId(pet);
    // pet.setImageId(petSvc.getImageId(pet));
    // // pet.setImageId(petSvc.getCatImage(pet));
    // healthAppSvc.savePets(humanName, petId, pet);
    // System.out.println("postInfo " + petId);

    // m.addAttribute("pet", pet);

    // return "redirect:/pets?name=" + humanName;

    // }

    @GetMapping("/{name}")
    public String retrieveIndividualPets(@PathVariable("name") String name, HttpSession sess, Model m) {
        Pet pet = new Pet();
        m.addAttribute("pet", pet);
        String humanName = (String) sess.getAttribute("username");
        String breed = (String) sess.getAttribute("breed");
        System.out.println("IS THIS A CAT OR A DOG : " + breed);
        m.addAttribute("petrecord", healthAppSvc.getIndividualPet(humanName, name));
        return "individualpets";
    }

    @GetMapping("/petdelete/{name}")
    public String deletePetEntry(@PathVariable("name") String name, HttpSession sess) {

        String humanName = (String) sess.getAttribute("username");
        healthAppSvc.deletePet(humanName, name);

        return "redirect:/pets?name=" + humanName;
    }

    @GetMapping("/petupdate/{name}")
    public String updateEntryforDogs(@PathVariable("name") String name, Model m, HttpSession sess) {
        String humanName = (String) sess.getAttribute("username");
        Pet pet = healthAppSvc.getIndividualPet(humanName, name);
        String breed = (String) sess.getAttribute("breed");
        System.out.println(breed);
        if (breed.equals("dog")) {
            List<Dog> listOfDogs = petSvc.getDogBreeds();
            sess.setAttribute("petid", pet.getId());
            m.addAttribute("pet", pet);
            m.addAttribute("listofbreeds", listOfDogs);
            return "updatepets";
        } else if (breed.equals("cat")) {
            List<Cat> listOfCats = petSvc.getCatBreeds();
            sess.setAttribute("petid", pet.getId());
            m.addAttribute("pet", pet);
            m.addAttribute("listofbreeds", listOfCats);
        }

        return "updatepets";

    }

    @PostMapping("/petsupdated")
    public String updated(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m) {

        if (result.hasErrors()) {
            String breed = (String) sess.getAttribute("breed");
            if (breed.equals("dog")) {
                List<Dog> listOfDogs = petSvc.getDogBreeds();
                m.addAttribute("listofbreeds", listOfDogs);
            } else if (breed.equals("cat")) {
                List<Cat> listOfCat = petSvc.getCatBreeds();
                m.addAttribute("listofbreeds", listOfCat);
            }
            return "updatepets";
        }

        String petId = (String) sess.getAttribute("petid");
        String breed = (String) sess.getAttribute("breed");
        pet.setId(petId);
        String humanName = (String) sess.getAttribute("username");

        if (breed.equals("dog")){
            pet.setImageId(petSvc.getImageId(pet));
            healthAppSvc.savePets(humanName, petId, pet);
            m.addAttribute("pet", pet);
            return "redirect:/pets?name=" + humanName;
        } else if (breed.equals("cat")){
            pet.setImageId(petSvc.getCatImage(pet));
            healthAppSvc.savePets(humanName, petId, pet);
            m.addAttribute("pet", pet);
            
        }
        return "redirect:/pets?name=" + humanName;

        // pet.setImageId(petSvc.getImageId(pet));
        // healthAppSvc.savePets(humanName, petId, pet);
        
        // m.addAttribute("pet", pet);
        // return "redirect:/pets?name=" + humanName;

        // if (result.hasErrors()) {
        //     m.addAttribute("pet", pet);
        //     List<Dog> listOfDogs = petSvc.getDogBreeds();
        //     m.addAttribute("listofdogbreeds", listOfDogs);
        //     return "updatepets";
        // }

        // String petId = (String) sess.getAttribute("petid");
        // pet.setId(petId);
        // String humanName = (String) sess.getAttribute("username");
        // petSvc.getImageId(pet);
        // pet.setImageId(petSvc.getImageId(pet));
        // healthAppSvc.savePets(humanName, petId, pet);

        // System.out.println("petsupdated: " + pet.getId());
        // m.addAttribute("pet", pet);

        // return "redirect:/pets?name=" + humanName;
    }

    @PostMapping(path = "/cancel")
    public String cancelUpdate(HttpSession sess) {

        String humanName = (String) sess.getAttribute("username");
        return "redirect:/pets?name=" + humanName;
    }

    @PostMapping(path="/bye")
    public String logout(HttpSession sess, Model m){
        sess.invalidate();

        List<Weather> listOfWeather = weatherSvc.getWeather();
        m.addAttribute("listofweather", listOfWeather);
        return "redirect:/";

    }
}
