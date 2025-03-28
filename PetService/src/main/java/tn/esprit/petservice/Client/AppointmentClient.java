package tn.esprit.petservice.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.petservice.entity.Appointment;

import java.util.List;

@FeignClient(name = "appointment-service", url="${application.config.appointments-url}")
public interface AppointmentClient {
    @GetMapping("/service/{serviceId}")
    List<Appointment> getAppointmentsByService(@PathVariable("serviceId") Long serviceId);
}
