package tn.esprit.petservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.petservice.client.AppointmentClient;
import tn.esprit.petservice.entity.Appointment;
import tn.esprit.petservice.entity.FullPetServiceResponse;
import tn.esprit.petservice.entity.PetService;
import tn.esprit.petservice.rabbitmq.RabbitMQMessageProducer;
import tn.esprit.petservice.repository.PetServiceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetServiceImpl implements IPetService {
    @Autowired
    private PetServiceRepository petServiceRepository;

    @Autowired
    private final RabbitMQMessageProducer rabbitMQMessageProducer;


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
    public PetService findServiceByName(String name) {
        return petServiceRepository.findFirstByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }


    @Override
    @Transactional
    public PetService createService(PetService petService) {
//        String message = "Service Created";
//        rabbitMQMessageProducer.publish(
//                message,
//                "petservice.exchange",
//                "petservice.routingkey"
//        );
        return petServiceRepository.save(petService);
    }

    @Override
    public PetService updateService(Long id ,PetService petService) {
       PetService existingService = petServiceRepository.findById(id).get();
        existingService.setName(petService.getName());
        existingService.setDescription(petService.getDescription());
        existingService.setPrice(petService.getPrice());
        existingService.setAddress(petService.getAddress());
        existingService.setStartDate(petService.getStartDate());
        existingService.setEndDate(petService.getEndDate());
        existingService.setDurationInMinutes(petService.getDurationInMinutes());
        existingService.setProviderId(petService.getProviderId());

       /* String message = "Service Updated";
        rabbitMQMessageProducer.publish(
                message,
                "petservice.exchange",
                "petservice.routingkey"
        );*/
        return petServiceRepository.save(petService);
    }

    @Override
    public void deleteService(Long id) {

        petServiceRepository.deleteById(id);
        // Send a message to the RabbitMQ queue
       /* String message = "{ \"idService\": " + id + " }";
       rabbitMQMessageProducer.publish(
            message,
                "petservice.exchange",
                "petservice.routingkey"
       );*/
    }

    @Override
    public List<PetService> getServicesByProvider(Long providerId) {
        return petServiceRepository.findByProviderId(providerId);
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(Long serviceId) {
        // Retrieve the pet service and appointments for the given service
        PetService service = getServiceById(serviceId);
        List<Appointment> appointments = appointmentClient.getAppointmentsByService(serviceId);

        // Prepare list of available slots and other needed variables
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDate currentDate = LocalDate.now(); // Start from today
        LocalTime startTime = service.getStartDate().toLocalTime();
        LocalTime endTime = service.getEndDate().toLocalTime();
        int duration = service.getDurationInMinutes();

        // Group appointments by their date for quick lookup
        Map<LocalDate, List<Appointment>> appointmentsByDate = appointments.stream()
                .collect(Collectors.groupingBy(a -> a.getDateAppointment().toLocalDate()));

        // Initialize the starting slot
        LocalDateTime currentSlot = LocalDateTime.of(currentDate, startTime);
        int maxDays = 30;  // Limit available slots to the next 30 days

        // Loop until we gather at least 100 available slots or reach the 30-day limit
        while (availableSlots.size() <= 100 && maxDays > 0) {
            if (currentSlot.isBefore(LocalDateTime.now())) {
                currentSlot = LocalDateTime.of(LocalDate.now(), startTime); // Skip past slots
            }

            // Generate slots for the day
            while (currentSlot.toLocalTime().isBefore(endTime)) {
                // Check if the current slot is already taken
                LocalDate currentSlotDate = currentSlot.toLocalDate();
                LocalDateTime finalCurrentSlot = currentSlot;
                boolean isSlotTaken = appointmentsByDate.getOrDefault(currentSlotDate, Collections.emptyList())
                        .stream()
                        .anyMatch(appointment -> appointment.getDateAppointment().isEqual(finalCurrentSlot));

                if (!isSlotTaken) {
                    availableSlots.add(currentSlot); // Add the slot if it's free
                }

                // Move to the next slot by adding duration
                currentSlot = currentSlot.plusMinutes(duration);
            }

            // Move to the next day
            currentDate = currentDate.plusDays(1);
            currentSlot = LocalDateTime.of(currentDate, startTime);
            maxDays--;
        }

        return availableSlots;
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

    @Override
    public void acceptAppointment(Long id ,String reason) {
        Map<String, String> body = Map.of("reason", reason);
  //      String message = "Appointment Accepted";
//        rabbitMQMessageProducer.publish(
//                message,
//                "appointment.exchange",
//                "appointment.routingkey"
//        );
        appointmentClient.acceptAppointment(id, body);
    }

    @Override
    public void rejectAppointment(Long id ,String reason) {
        Map<String, String> body = Map.of("reason", reason);
        String message = "Appointment Rejected";
//        rabbitMQMessageProducer.publish(
//                message,
//                "appointment.exchange",
//                "appointment.routingkey"
//        );
        appointmentClient.rejectAppointment(id, body);
    }

    @Override
    public List<FullPetServiceResponse> getAllServicesWithAppoitments() {
        return petServiceRepository.findAll().stream()
                .map(service -> {
                    var appointments = appointmentClient.getAppointmentsByService(service.getIdService());
                    return FullPetServiceResponse.builder()
                            .name(service.getName())
                            .description(service.getDescription())
                            .price(service.getPrice())
                            .appointments(appointments)
                            .address(service.getAddress())
                            .durationInMinutes(service.getDurationInMinutes())
                            .build();
                })
                .toList();
    }

    @Override
    public List<FullPetServiceResponse> getAllServicesWithAppoitmentsByProvider(Long idProvider) {
        return petServiceRepository.findByProviderId(idProvider).stream()
                .map(service -> {
                    var appointments = appointmentClient.getAppointmentsByService(service.getIdService());
                    return FullPetServiceResponse.builder()
                            .idService(service.getIdService())
                            .name(service.getName())
                            .description(service.getDescription())
                            .price(service.getPrice())
                            .appointments(appointments)
                            .address(service.getAddress())
                            .durationInMinutes(service.getDurationInMinutes())
                            .build();
                })
                .toList();
    }
}
