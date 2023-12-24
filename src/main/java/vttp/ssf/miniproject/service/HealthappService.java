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

    public Pet getIndividualPet(String petName){
        return petRepo.getPetByName(petName);
    }
    
}
