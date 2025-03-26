package tn.esprit.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "pet-service" , url="${application.config.services-url}")
public interface PetServiceClient {
    @GetMapping("/{serviceId}/slots")
    List<LocalDateTime> getAvailableSlots(@PathVariable("serviceId") Long serviceId);
}
