package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.Cat;
import vttp.ssf.miniproject.model.Dog;
import vttp.ssf.miniproject.model.Pet;
// import vttp.ssf.miniproject.service.DogService;
import vttp.ssf.miniproject.service.HealthappService;
import vttp.ssf.miniproject.service.petImageService;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    // @Autowired
    // DogService dogSvc;

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

    @GetMapping("/cat")
    public String getCat(Model m ){
        Pet pet = new Pet();
        List<Cat> listOfCat = petSvc.getCatBreeds();
        m.addAttribute("listofcatbreeds", listOfCat);
        m.addAttribute("pet", pet);
        return "petinfocat";
    }

    @PostMapping("/postInfoCat")
    public String postAddedInfoCat(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m){

        if (result.hasErrors()) {
            m.addAttribute("pet", pet);
            List<Cat> listOfCat = petSvc.getCatBreeds();

            m.addAttribute("listofcatbreeds", listOfCat);
            return "petinfocat";

        }
        String humanName = (String) sess.getAttribute("username");
        String petId = healthAppSvc.getRandomHexString(8);
        pet.setId(petId);
        // petSvc.getImageId(pet);
        // pet.setImageId(petSvc.getImageId(pet)); 
        pet.setImageId(petSvc.getCatImage(pet));
        healthAppSvc.savePets(humanName, petId, pet);
        System.out.println("postInfo " + petId);

        m.addAttribute("pet", pet);
        

        return "redirect:/pets?name=" + humanName;
    }


    @GetMapping("/dog")
    public String getDog(Model m ){
        Pet pet = new Pet();
        List<Dog> listOfDogs = petSvc.getDogBreeds();
        m.addAttribute("listofdogbreeds", listOfDogs);
        m.addAttribute("pet", pet);
        return "petinfo";
    }


    @PostMapping("/postInfoDog")
    public String postAddedInfoDog(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m) {
        if (result.hasErrors()) {
            m.addAttribute("pet", pet);
            List<Dog> listOfDogs = petSvc.getDogBreeds();

            m.addAttribute("listofdogbreeds", listOfDogs);
            return "petinfo";

        }
        String humanName = (String) sess.getAttribute("username");
        String petId = healthAppSvc.getRandomHexString(8);
        pet.setId(petId);
        // petSvc.getImageId(pet);
        pet.setImageId(petSvc.getImageId(pet)); 
        // pet.setImageId(petSvc.getCatImage(pet));
        healthAppSvc.savePets(humanName, petId, pet);
        System.out.println("postInfo " + petId);

        m.addAttribute("pet", pet);
        

        return "redirect:/pets?name=" + humanName;

    }

    @GetMapping("/{name}")
    public String retrieveIndividualPets(@PathVariable("name") String name, HttpSession sess, Model m) {
        Pet pet = new Pet();
        m.addAttribute("pet", pet);
        String humanName = (String) sess.getAttribute("username");
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
        List<Dog> listOfDogs = petSvc.getDogBreeds();
        sess.setAttribute("petid", pet.getId());
        m.addAttribute("pet", pet);
        m.addAttribute("listofdogbreeds", listOfDogs);

        return "updatepets";

    }

    // @GetMapping("/petupdate/{name}")
    // public String updateEntryForCats(@PathVariable("name") String name, Model m, HttpSession sess) {
    //     String humanName = (String) sess.getAttribute("username");
    //     Pet pet = healthAppSvc.getIndividualPet(humanName, name);
    //     List<Cat> listOfCat = petSvc.getCatBreeds();
    //     sess.setAttribute("petid", pet.getId());
    //     m.addAttribute("pet", pet);
    //     m.addAttribute("listofcatbreeds", listOfCat);

    //     return "updatepetscats";

    // }


    @PostMapping("/petsupdated")
    public String updated(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m) {
        if (result.hasErrors()) {
            m.addAttribute("pet", pet);
            List<Dog> listOfDogs = petSvc.getDogBreeds();
            m.addAttribute("listofdogbreeds", listOfDogs);
            return "updatepets.html";
        }

        String petId = (String) sess.getAttribute("petid");
        pet.setId(petId);
        String humanName = (String) sess.getAttribute("username");
        petSvc.getImageId(pet);
        pet.setImageId(petSvc.getImageId(pet)); 
        healthAppSvc.savePets(humanName, petId, pet);

        System.out.println("petsupdated: " + pet.getId());
        m.addAttribute("pet", pet);

        return "redirect:/pets?name=" + humanName;
    }
    

    @PostMapping(path = "/cancel")
    public String cancelUpdate(HttpSession sess) {

        String humanName = (String) sess.getAttribute("username");
        return "redirect:/pets?name=" + humanName;
    }

}
