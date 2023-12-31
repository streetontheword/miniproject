package vttp.ssf.miniproject.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void savePets(String username, String id, Pet pet){
        HashOperations<String, String, Pet> hashOps = template.opsForHash();
        hashOps.put(username, id, pet);

    }
    
    //getting indiv pet info by their name 
    public Pet getPetByName(String userName, String petName){
        HashOperations<String, String, Pet> hashOps = template.opsForHash();
        Pet pet = new Pet(); 
        pet = hashOps.get(userName, petName);
        return pet; 
    }

    public Boolean delete(String userName, String id){
        Boolean result = false; 
        HashOperations<String, String, Pet> hashOps = template.opsForHash();

        hashOps.delete(userName, id);
         result = true; 
         return result;  
    }

    public Map<Object, Object> getAllPets(String username){
        Map<Object, Object> map = template.opsForHash().entries(username);
        System.out.println(map);
        return map;
    }

}
