package vttp.ssf.miniproject.service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vttp.ssf.miniproject.model.Pet;
import vttp.ssf.miniproject.repository.PetRepo;

@Service
public class HealthappService {

    @Autowired
    PetRepo petRepo; 

    public List<Pet> getPets(String name){
        
        List<Pet> listOfPets = petRepo.getPets(name);
            return listOfPets; 
    }


     public String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

 
    public void savePets(String username, String name, Pet pet){
        petRepo.savePets(username, name, pet);
    }


    //to get individual pets by their id 
    public Pet getIndividualPet(String userName, String petName){
        return petRepo.getPetByName(userName, petName);
    }



    public Boolean deletePet(String userName, String petName){
        Boolean result = false; 
        if (petRepo.delete(userName, petName)){
        result = true; 
        System.out.println("deleted pet = " + result); //not so sure if i need this 
        } 
        
        return result; 
    }
    
    // public List<Object> getValues(String username){
        
    //     System.out.println (petRepo.getValue(username));
       

    //     return petRepo.getValue(username);
    // }


    public ResponseEntity<Map> getAllPets(String username){
        Map<Object, Object> map = petRepo.getAllPets(username);
        ResponseEntity<Map> responseEntity = new ResponseEntity<Map>(map, HttpStatusCode.valueOf(200));
        System.out.println(responseEntity);
        return responseEntity;
    }
}
