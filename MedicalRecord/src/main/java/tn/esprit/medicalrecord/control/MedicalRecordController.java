package tn.esprit.medicalrecord.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.medicalrecord.entity.MedicalRecord;
import tn.esprit.medicalrecord.entity.MedicalRecordType;
import tn.esprit.medicalrecord.services.IMedicalRecordService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/medicalrecord")
public class MedicalRecordController {

    @Autowired
    IMedicalRecordService medicalRecordService;

    // Chemin où l'image sera enregistrée
    private static final String UPLOAD_DIR = "MedicalRecord/uploads/";
    @GetMapping("/retrieve-all-medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {
        List<MedicalRecord> medicalRecordList = medicalRecordService.retrieveAllMedicalRecords();
        return medicalRecordList;
    }

    @GetMapping("/retrieve-medicalRecord/{medicalRecord-id}")
    public MedicalRecord retrieveMedicalRecord(@PathVariable("medicalRecord-id") Long med) {
        return medicalRecordService.retrieveMedicalRecord(med);
    }

    @DeleteMapping("/remove-medicalRecord/{medicalRecord-id}")
    public void removeMedicalRecord(@PathVariable("medicalRecord-id") Long cId) {
        medicalRecordService.removeMedicalRecord(cId);
    }

    @PutMapping("/modify-medicalRecord")
    public MedicalRecord modifyMedicalRecord(@RequestBody MedicalRecord md) {
        return medicalRecordService.modifyMedicalRecord(md);
    }

    @GetMapping("/medical-records/byCarnet/{id}")
    public List<MedicalRecord> getMedicalRecordsByCarnet(@PathVariable("id") Long id) {
        return medicalRecordService.findByCarnetId(id);
    }




    @PostMapping("/add-medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.addMedicalRecord(medicalRecord);
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
    @PostMapping("/New-add-medicalRecord")
    public ResponseEntity<Object> addMedicalRecord(
            @RequestParam("dateTime") String dateTime,
            @RequestParam("type") MedicalRecordType type,
            @RequestParam("description") String description,
            @RequestParam("nextDueDate") String nextDueDate, // 👈 changé
            @RequestParam("carnetId") long carnetId,
            @RequestParam("poids") long poids,
            @RequestParam("image") MultipartFile image) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date parsedDateTime = formatter.parse(dateTime);
            Date parsedNextDueDate = formatter.parse(nextDueDate);

            MedicalRecordType recordType = MedicalRecordType.valueOf(type.toString());

            String imagePath =saveImage(image) ;


            MedicalRecord record = new MedicalRecord();
            record.setDateTime(parsedDateTime);
            record.setType(recordType);
            record.setDescription(description);
            record.setNext_due_date(parsedNextDueDate);
            record.setCarnetId(carnetId);
            record.setPoids(poids);
            record.setImagePath(imagePath);

            MedicalRecord saved = medicalRecordService.addMedicalRecord(record);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Type d'enregistrement médical invalide : " + type);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'ajout", HttpStatus.BAD_REQUEST);
        }
    }

}
