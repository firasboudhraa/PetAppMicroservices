package tn.esprit.appointment.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.entity.AppointmentStatus;
import tn.esprit.appointment.service.IAppointmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping("/addAppointment")
    public Appointment addAppointment(
            @RequestBody Appointment appointment)  {
        return appointmentService.addAppointment(appointment);
    }

    @PutMapping("/updateAppointment/{id}")
    public Appointment updateAppointment(
            @PathVariable("id") Long id,
            @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id,appointment);
    }

    @DeleteMapping("/deleteAppointment/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/findAppointmentById/{id}")
    public Appointment findAppointmentById(@PathVariable("id") Long id) {
        return appointmentService.findAppointmentById(id);
    }

    @GetMapping("/findAllAppointments")
    public List<Appointment> findAllAppointments() {
        return appointmentService.findAllAppointments();
    }

    @GetMapping("/service/{serviceId}")
    public List<Appointment> getAppointmentsByService(@PathVariable("serviceId") Long serviceId) {
        return appointmentService.getAppointmentsByService(serviceId);
    }

    @PutMapping("/{id}/accept")
    public Appointment acceptAppointment(@PathVariable("id") Long id , @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        return appointmentService.updateAppointmentStatus(id, AppointmentStatus.CONFIRMED, reason);
    }

    @PutMapping("/{id}/reject")
    public Appointment rejectAppointment(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        String reason = body.get("reason");
        return appointmentService.updateAppointmentStatus(id, AppointmentStatus.CANCELLED,reason);
    }

    @GetMapping("/owner/{idOwner}")
    public List<Appointment> getAppointmentsByOwner(@PathVariable("idOwner") Long idOwner) {
        return appointmentService.getAppointmentsByOwner(idOwner);
    }

    @GetMapping("/vet/{idVet}")
    public List<Appointment> getAppointmentsByVet(@PathVariable("idVet") Long idVet) {
        return appointmentService.getAppointmentsByVet(idVet);
    }
    @GetMapping("/pet/{idPet}")
    public List<Appointment> getAppointmentsByPet(@PathVariable("idPet") Long idPet) {
        return appointmentService.getAppointmentsByPet(idPet);
    }

}
