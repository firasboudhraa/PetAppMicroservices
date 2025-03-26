package tn.esprit.petservice.service;

import tn.esprit.petservice.entity.PetService;

import java.time.LocalDateTime;
import java.util.List;

public interface IPetService {
    List<PetService> getAllServices();
    PetService getServiceById(Long id);
    PetService createService(PetService petService);
    PetService updateService(PetService petService);
    void deleteService(Long id);
    List<PetService> getServicesByProvider(Long providerId);
    List<LocalDateTime> getAvailableSlots(Long serviceId);

}
