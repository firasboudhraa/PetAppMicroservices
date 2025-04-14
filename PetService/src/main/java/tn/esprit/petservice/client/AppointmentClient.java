package tn.esprit.petservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.petservice.entity.Appointment;

import java.util.List;
import java.util.Map;

@FeignClient(name = "appointment-service", url="${application.config.appointments-url}")
public interface AppointmentClient {
    @GetMapping("/service/{serviceId}")
    List<Appointment> getAppointmentsByService(@PathVariable("serviceId") Long serviceId);

    @PutMapping("/{id}/accept")
    Appointment acceptAppointment(@PathVariable("id") Long id , @RequestBody Map<String, String> body);

    @PutMapping("/{id}/reject")
    Appointment rejectAppointment(@PathVariable("id") Long id, @RequestBody Map<String, String> body);
}
