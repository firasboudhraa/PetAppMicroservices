package tn.esprit.petservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petservice.Client.AppointmentClient;
import tn.esprit.petservice.entity.Appointment;
import tn.esprit.petservice.entity.FullPetServiceResponse;
import tn.esprit.petservice.entity.PetService;
import tn.esprit.petservice.repository.PetServiceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements IPetService {
    @Autowired
    private PetServiceRepository petServiceRepository;

    @Autowired
    private AppointmentClient appointmentClient;
    @Override
    public List<PetService> getAllServices() {
        return petServiceRepository.findAll();
    }

    @Override
    public PetService getServiceById(Long id) {
        return petServiceRepository.findById(id).get();
    }

    @Override
    public PetService createService(PetService petService) {
        return petServiceRepository.save(petService);
    }

    @Override
    public PetService updateService(PetService petService) {
        if (!petServiceRepository.existsById(petService.getIdService())) {
            throw new RuntimeException("Service not found");
        }
        return petServiceRepository.save(petService);
    }

    @Override
    public void deleteService(Long id) {
        petServiceRepository.deleteById(id);
    }

    @Override
    public List<PetService> getServicesByProvider(Long providerId) {
        return petServiceRepository.findByProviderId(providerId);
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(Long serviceId) {
        PetService service = getServiceById(serviceId);
        // Implement logic to get available slots
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime currentSlot = service.getStartDate();

        while (currentSlot.isBefore(service.getEndDate())) {
            slots.add(currentSlot);
            currentSlot = currentSlot.plusMinutes(service.getDurationInMinutes());
        }
        return slots;
    }

    @Override
    public FullPetServiceResponse getServiceWithAppoitment(Long id) {
        var service = petServiceRepository.findById(id).get();
        var appointments = appointmentClient.getAppointmentsByService(id);
        return FullPetServiceResponse.builder()
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .appointments(appointments)
                .address(service.getAddress())
                .durationInMinutes(service.getDurationInMinutes())
                .build();
    }
}
