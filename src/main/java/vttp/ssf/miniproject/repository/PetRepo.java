package vttp.ssf.miniproject.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.miniproject.model.Pet;

@Repository
public class PetRepo {

    @Autowired @Qualifier("redis")
     RedisTemplate<String, Object> template;

    public List<Pet> getPets(String name) {
        List<Pet> listOfPets = new ArrayList<>();
        HashOperations<String, String, Pet> hashOps = template.opsForHash();

        if (template.hasKey(name)) { // this is to check the database for the human username
            for (String nameOfPet : hashOps.keys(name)) {
                Pet pet = hashOps.get(name, nameOfPet); // return the PET object
                listOfPets.add(pet);
            }
            return listOfPets;
        } else {
            return null; 
        }

    }

    public void savePets(String username, String name, Pet pet){
        HashOperations<String, String, Pet> hashOps = template.opsForHash();
        hashOps.put(username, pet.getName(), pet);

    }
    
    //getting indiv pet info by their name 
    public Pet getPetByName(String userName, String petName){
        HashOperations<String, String, Pet> hashOps = template.opsForHash();
        Pet pet = new Pet(); 
        pet = hashOps.get(userName, petName);
        return pet; 
    }

    public Boolean delete(String userName, String petName){
        Boolean result = false; 
        HashOperations<String, String, Pet> hashOps = template.opsForHash();

        hashOps.delete(userName, petName);
         result = true; 
         return result;  
    }

}
