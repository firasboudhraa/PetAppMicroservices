package tn.esprit.petms.controller;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.petms.entity.FullPet;
import tn.esprit.petms.entity.Pet;
import tn.esprit.petms.service.IPetService;
import tn.esprit.petms.service.PetServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pet")
public class PetRestController {
    @Autowired
    PetServiceImpl petService ;
    // private static final String UPLOAD_DIR = "C:/Pi_Dev/";
    private static final String UPLOAD_DIR = "PetMs/uploads/";


    @GetMapping("/retrieve-all-pets")
    public List<Pet> getPets(){
        return petService.retrieveAllPets();
    }

    @GetMapping("/{ownerId}")
    public List<Pet> getPetsByOwnerId(@PathVariable("ownerId") Long ownerId){
        return petService.getPetsByOwnerId(ownerId) ;
    }
    @GetMapping("/petWithOwner/{ownerId}")
    public List<FullPet> getPetsWithOwner(@PathVariable("ownerId") Long ownerId ){
        return petService.retrievePetsWithOwner(ownerId);
    }

    @GetMapping("/retrieve-pet/{pet-id}")
    public Pet retrievePet(@PathVariable("pet-id") Long idPet){
        return petService.retrievePet(idPet) ;
    }

//    @PostMapping("/add-pet")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Pet addPet(@RequestBody Pet pet){
//        System.out.println("Received Pet: " + pet);  // Log the received pet
//
//        return petService.addPet(pet) ;
//    }

    @PostMapping("/addPet")
    public ResponseEntity<Object> addPet(@RequestParam("name") String name,
                                         @RequestParam("species") String species,
                                         @RequestParam("age") int age,
                                         @RequestParam("color") String color,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("description") String description,
                                         @RequestParam("ownerId") Long ownerId,
                                         @RequestParam("forAdoption") boolean forAdoption,
                                         @RequestParam("image") MultipartFile image) {
        try {
            String imagePath = saveImage(image);
            Pet pet = new Pet(name, species, age, color, sex, description , ownerId, imagePath , forAdoption);
            petService.addPet(pet);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Pet added successfully");
            response.put("imagePath", imagePath);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to upload image");
            error.put("error", e.getMessage());

            return ResponseEntity.status(500).body(error);
        }
    }
    private String saveImage(MultipartFile image) throws IOException {
        // Get the original filename of the image
        String fileName = image.getOriginalFilename();

        // Create a Path object to the directory where the image will be stored
        //Path uploadPath = Paths.get(UPLOAD_DIR);
        Path uploadPath = Paths.get(System.getProperty("user.dir"), UPLOAD_DIR);

        // Create the directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create a path for the new file
        Path filePath = uploadPath.resolve(fileName);

        // Save the file locally
        image.transferTo(filePath.toFile());

        // Return the file path (you can use this in the database)
        return fileName;  // You can return a relative URL if you wish
    }


    @DeleteMapping("/delete-pet/{pet-id}")
    public void removePet(@PathVariable("pet-id") Long idPet){
        petService.removePet(idPet);
    }

    @PutMapping("/modify-pet")
    public ResponseEntity<Object> modifyPet(@RequestParam("id") Long id,
                                            @RequestParam("name") String name,
                                            @RequestParam("species") String species,
                                            @RequestParam("age") int age,
                                            @RequestParam("color") String color,
                                            @RequestParam("sex") String sex,
                                            @RequestParam("sex") String description,
                                            @RequestParam("ownerId") Long ownerId,
                                            @RequestParam("forAdoption") boolean forAdoption,
                                            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Pet existingPet = petService.retrievePet(id);
            if (existingPet == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Pet not found"));
            }

            existingPet.setName(name);
            existingPet.setSpecies(species);
            existingPet.setAge(age);
            existingPet.setColor(color);
            existingPet.setSex(sex);
            existingPet.setSex(description);
            existingPet.setOwnerId(ownerId);
            existingPet.setForAdoption(forAdoption);


            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                existingPet.setImagePath(imagePath);
            }

            Pet updatedPet = petService.modifyPet(existingPet);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Pet updated successfully");
            response.put("updatedPet", updatedPet);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Failed to upload image",
                    "error", e.getMessage()
            ));
        }
    }


}

