package tn.esprit.petservice.service;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Send a message to the RabbitMQ queue
        String message = "{ \"idService\": " + id + " }";
        rabbitMQMessageProducer.publish(
                message,
                "petservice.exchange",
                "petservice.routingkey"
        );
    }

    @Override
    public List<PetService> getServicesByProvider(Long providerId) {
        return petServiceRepository.findByProviderId(providerId);
    }


    @Override
    public List<LocalDateTime> getAvailableSlots(Long serviceId) {
        PetService service = getServiceById(serviceId);
        List<Appointment> appointments = appointmentClient.getAppointmentsByService(serviceId);
        List<LocalDateTime> availableSlots = new ArrayList<>();

        LocalDate currentDate = LocalDate.now(); // Start from today
        LocalTime startTime = service.getStartDate().toLocalTime();
        LocalTime endTime = service.getEndDate().toLocalTime();
        int duration = service.getDurationInMinutes();

        LocalDateTime currentSlot = LocalDateTime.of(currentDate, startTime);
        int maxDays = 30;  // Limit available slots to the next 30 days

        while (availableSlots.size() <= 100 && maxDays > 0) {
            if (currentSlot.isBefore(LocalDateTime.now())) {
                currentSlot = LocalDateTime.of(LocalDate.now(), startTime);
            }

            // Generate slots for the day
            while (currentSlot.toLocalTime().isBefore(endTime)) {
                LocalDateTime finalCurrentSlot = currentSlot;
                boolean isSlotTaken = appointments.stream()
                        .anyMatch(appointment -> appointment.getDateAppointment().isEqual(finalCurrentSlot));

                if (!isSlotTaken) {
                    availableSlots.add(currentSlot);
                }

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
        appointmentClient.acceptAppointment(id, body);
    }

    @Override
    public void rejectAppointment(Long id ,String reason) {
        Map<String, String> body = Map.of("reason", reason);
        appointmentClient.rejectAppointment(id, body);
    }
}
