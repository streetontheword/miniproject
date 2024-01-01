package vttp.ssf.miniproject.service;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.miniproject.model.Cat;
import vttp.ssf.miniproject.model.Dog;
import vttp.ssf.miniproject.model.Pet;

@Service
public class petImageService {

    @Value("${dog.api}")
    private String url_dog;

    @Value("${dog.api.image}")
    private String url_dog_image;

    @Value("${cat.api}")
    private String url_cat;

    @Value("${cat.api.image}")
    private String url_cat_image;

    RestTemplate template = new RestTemplate();

    List<Dog> listOfDog = new ArrayList<>();
    List<Cat> listOfCat = new ArrayList<>();

    private Map<String, Dog> DogMap = new HashMap<String, Dog>();

    public Map<String, Dog> getDogMap() {
        return DogMap;
    }

    private Map<String, Cat> catMap = new HashMap<String, Cat>();

    public Map<String, Cat> getCatMap() {
        return catMap;
    }

    public String getImageId(Pet pet) {
        String breed = pet.getBreed();
        if (breed.equals("other")) {
            return "https://img.freepik.com/free-vector/concept-cute-different-pets_23-2148545213.jpg?size=338&ext=jpg&ga=GA1.1.1546980028.1703808000&semt=ais";

        }
        String imageId = DogMap.get(breed).getImageId();
        String callThisUrl = url_dog_image + imageId;
        RequestEntity<Void> req = RequestEntity.get(callThisUrl).build();
        ResponseEntity<String> response = template.exchange(req, String.class);
        String apiPaylod = response.getBody();
        JsonReader jsonReader = Json.createReader(new StringReader(apiPaylod));
        JsonObject jsonObject = jsonReader.readObject();
        String urlForImage = jsonObject.getString("url");
        pet.setImageId(urlForImage);
        // pet.setImageId(urlForImage);
        return urlForImage;
    }

    public List<Dog> getDogBreeds() {

        RequestEntity<Void> req = RequestEntity.get(url_dog).build();

        ResponseEntity<String> response = template.exchange(req, String.class);
        String payLoad = response.getBody().toString();
        // System.out.println("payload: " + payLoad);
        // read Json
        JsonReader jsonReader = Json.createReader(new StringReader(payLoad));
        JsonArray jsonArray = jsonReader.readArray();
        for (JsonValue jsonValue : jsonArray) {
            JsonObject jsonObj = jsonValue.asJsonObject();
            String breedName = jsonObj.getString("name");
            String refId = jsonObj.getString("reference_image_id");
            Dog dog = new Dog();
            dog.setName(breedName);
            dog.setImageId(refId);
            listOfDog.addLast(dog);
            // System.out.println(listOfDog);
            DogMap.put(breedName, dog);
        }
        // getImageUrl(listOfDog);

        return listOfDog;
    }

    public List<Cat> getCatBreeds() {
        RequestEntity<Void> req = RequestEntity.get(url_cat).build();
        ResponseEntity<String> response = template.exchange(req, String.class);
        String payLoad = response.getBody().toString();

        JsonReader jsonReader = Json.createReader(new StringReader(payLoad));
        JsonArray jsonArray = jsonReader.readArray();
        for (JsonValue jsonValue : jsonArray) {
            JsonObject jsonObj = jsonValue.asJsonObject();
            String breedName = jsonObj.getString("name");
            String refId = jsonObj.getString("reference_image_id", "null");
            Cat cat = new Cat();
            cat.setName(breedName);
            cat.setImageId(refId);
            listOfCat.add(cat);
            System.out.println(cat);
            catMap.put(breedName, cat);

        }
        return listOfCat;
    }

    public String getCatImage(Pet pet) {
        String breed = pet.getBreed();
        if (breed.equals("other")) {
            return "https://img.freepik.com/free-vector/concept-cute-different-pets_23-2148545213.jpg?size=338&ext=jpg&ga=GA1.1.1546980028.1703808000&semt=ais";

        }
        String imageId = catMap.get(breed).getImageId();
        String callThisUrl = url_cat_image + imageId;
        RequestEntity<Void> req = RequestEntity.get(callThisUrl).build();
        ResponseEntity<String> response = template.exchange(req, String.class);
        String apiPaylod = response.getBody();
        JsonReader jsonReader = Json.createReader(new StringReader(apiPaylod));
        JsonObject jsonObject = jsonReader.readObject();
        String urlForImage = jsonObject.getString("url");
        pet.setImageId(urlForImage);
        // pet.setImageId(urlForImage);
        return urlForImage;
    }

  
        // how to get the images?
        // public String getImageUrl(List<Dog> dogList) {
        // List<String> allUrl = new ArrayList<>();
        // for (Dog dog : dogList) {
        // String reference = dog.getImageId();
        // String finalUrl = url_dog_image + reference;
        // allUrl.add(finalUrl);
        // }

        // for (String url : allUrl) {
        // RequestEntity<Void> req = RequestEntity.get(url).build();
        // ResponseEntity<String> response = template.exchange(req, String.class);
        // String apiPaylod = response.getBody();
        // JsonReader jsonReader = Json.createReader(new StringReader(apiPaylod));
        // JsonObject jsonObject = jsonReader.readObject();
        // String urlForImage = jsonObject.getString("url");
        // System.out.println(urlForImage);
        // }
        // return "";
        // }

    

    

}
