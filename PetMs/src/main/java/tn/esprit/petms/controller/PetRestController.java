package tn.esprit.petms.controller;

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
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
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

    @GetMapping("/petWithOwner/{ownerId}")
    public List<FullPet> getPetsWithOwner(@PathVariable("ownerId") Long ownerId ){
        return petService.retrievePetsWithOwner(ownerId);
    }

    @GetMapping("/retrieve-pet/{pet-id}")
    public Pet retrievePet(@PathVariable("pet-id") Long idPet){
        return petService.retrievePet(idPet) ;
    }

    @PostMapping("/add-pet")
    @ResponseStatus(HttpStatus.CREATED)
    public Pet addPet(@RequestBody Pet pet){
        System.out.println("Received Pet: " + pet);  // Log the received pet

        return petService.addPet(pet) ;
    }

    @PostMapping("/addWithImage")
    public ResponseEntity<String> addPet(@RequestParam("name") String name,
                                         @RequestParam("species") String species,
                                         @RequestParam("age") int age,
                                         @RequestParam("color") String color,
                                         @RequestParam("sex") String sex,
                                         @RequestParam("ownerId") Long ownerId,
                                         @RequestParam("image") MultipartFile image) {
        try {
            // Save the image and get its path
            String imagePath = saveImage(image);

            // Create the Pet object with the image path
            Pet pet = new Pet(name, species, age, color, sex, ownerId, imagePath);


            // Save the pet to the database
            petService.addPet(pet);

            return ResponseEntity.ok("Pet added successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload image");
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
    public Pet modifyPet(@RequestBody Pet pet){
        return petService.modifyPet(pet) ;
    }


}

