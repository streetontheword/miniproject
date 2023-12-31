// package vttp.ssf.miniproject.service;

// import java.io.StringReader;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.RequestEntity;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;


// import jakarta.json.Json;
// import jakarta.json.JsonArray;
// import jakarta.json.JsonObject;
// import jakarta.json.JsonReader;
// import jakarta.json.JsonValue;

// @Service
// public class DogService {

//     @Value("${dog.api}")
//     private String url_dog;

//     @Value("${dog.api.key}")
//     public String key;

//     RestTemplate template = new RestTemplate();

//     List<String> listOfDogBreeds = new ArrayList<>();

//     public List<String> getDogBreeds() {

//         RequestEntity<Void> req = RequestEntity.get(url_dog)    
//                                                .header("X-RapidAPI-Key", key)
//                                                .header("X-RapidAPI-Host", "dog-breeds2.p.rapidapi.com")
//                                                .build();

//         ResponseEntity<String> response = template.exchange(req, String.class);
//         String payLoad = response.getBody().toString();
//         // System.out.println("payload: " + payLoad);
//         //read Json 
//         JsonReader jsonReader = Json.createReader(new StringReader(payLoad));
//         JsonArray jsonArray = jsonReader.readArray(); 
//         for (JsonValue jsonValue : jsonArray){
//             JsonObject jsonObj = jsonValue.asJsonObject();
//             String breedName = jsonObj.getString("breed");
//             listOfDogBreeds.add(breedName);
//         }



//         return listOfDogBreeds;
//     }

// }
