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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.Pet;
import vttp.ssf.miniproject.service.DogService;
import vttp.ssf.miniproject.service.HealthappService;

@Controller
@RequestMapping(path = "/pets")
public class PetController {

    @Autowired
    DogService dogSvc;

    @Autowired
    HealthappService healthAppSvc;

    // later
    // do after person login
    @GetMapping("/add")
    public String getPets(Model m) {
        Pet pet = new Pet();
        List<String> dogBreeds = dogSvc.getDogBreeds();

        m.addAttribute("pet", pet);
        m.addAttribute("listofdogbreeds", dogBreeds);
        return "petinfo";
    }

    @PostMapping("/postInfo")
    public String postAddedInfo(@Valid @ModelAttribute Pet pet, BindingResult result, HttpSession sess, Model m) {
        if (result.hasErrors()) {
            m.addAttribute("pet", pet);
            List<String> dogBreeds = dogSvc.getDogBreeds();
            m.addAttribute("listofdogbreeds", dogBreeds);
            return "petinfo";

        }

        String humanName = (String) sess.getAttribute("username");
        healthAppSvc.savePets(humanName, pet.getName(), pet);

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
    public String updateEntry(@PathVariable("name") String name, Model m, HttpSession sess){
        String humanName = (String) sess.getAttribute("username");
        Pet pet = healthAppSvc.getIndividualPet(humanName, name);
        List<String> dogBreeds = dogSvc.getDogBreeds();

        m.addAttribute("pet", pet);
        m.addAttribute("listofdogbreeds", dogBreeds);

        return "updatepets";

    }
   

}
