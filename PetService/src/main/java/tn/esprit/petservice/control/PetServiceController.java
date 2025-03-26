package tn.esprit.petservice.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.petservice.entity.PetService;
import tn.esprit.petservice.service.IPetService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class PetServiceController {
    @Autowired
    private IPetService petService;

    @GetMapping("/all-services")
    public List<PetService> getAllServices() {
        return petService.getAllServices();
    }

    @GetMapping("/service/{id}")
    public PetService getServiceById( @PathVariable("id") Long id) {
        return petService.getServiceById(id);
    }

    @GetMapping("/provider/{providerId}")
    public List<PetService> getServicesByProvider(@PathVariable("providerId") Long providerId) {
        return petService.getServicesByProvider(providerId);
    }

    @PostMapping("/add-service")
    public PetService createService(@RequestBody PetService petService) {
        return this.petService.createService(petService);
    }

    @PutMapping("/update-service")
    public PetService updateService(@RequestBody PetService petService) {
        return this.petService.updateService(petService);
    }

    @DeleteMapping("/delete-service/{id}")
    public void deleteService(@PathVariable("id") Long id) {
        this.petService.deleteService(id);
    }
    @GetMapping("/{id}/slots")
    public List<LocalDateTime> getAvailableSlots(@PathVariable("id") Long id) {
        return petService.getAvailableSlots(id);
    }
}
