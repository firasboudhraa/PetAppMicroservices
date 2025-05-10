package tn.esprit.medicalnotebook.control;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.medicalnotebook.entity.Carnet;
import tn.esprit.medicalnotebook.entity.FullCarnetResponse;
import tn.esprit.medicalnotebook.service.ICarnetService;

import java.util.List;
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/carnet")
@CrossOrigin(origins = "http://localhost:4200")
public class CarnetController {
    @Autowired
    ICarnetService carnetService;

    @GetMapping("/retrieve-all-carnets")
    public List<Carnet> getCarnets() {
        List<Carnet> listCarnets = carnetService.retrieveAllCarnets();
        return listCarnets;
    }

    @GetMapping("/retrieve-carnet/{carnet-id}")
    public Carnet retrieveCarnet(@PathVariable("carnet-id") Long cId) {
        Carnet carnet = carnetService.retrieveCarnet(cId);
        return carnet;
    }
    @PostMapping("/add-carnet")
    public Carnet addCarnet(@RequestBody Carnet c) {
        Carnet carnet = carnetService.addCarnet(c);
        return carnet;
    }

    @DeleteMapping("/remove-carnet/{carnet-id}")
    public void removeCarnet(@PathVariable("carnet-id") Long cId) {
        carnetService.removeCarnet(cId);
    }

    @PutMapping("/modify-carnet")
    public Carnet modifyCarnet(@RequestBody Carnet c) {
        Carnet carnet = carnetService.modifyCarnet(c);
        return carnet;
    }
    // Récupérer les records associés à un carnet spécifique
    @GetMapping("/{id}/medical-records")
    public FullCarnetResponse getMedicalRecordsByCarnet(@PathVariable Long id) {
        return carnetService.getMedicalRecordsByCarnet(id);
    }
}
