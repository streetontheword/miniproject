package vttp.ssf.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


    public void savePets(String username, String name, Pet pet){
        petRepo.savePets(username, name, pet);
    }


    //to get individual pets by their name 
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
    

}
