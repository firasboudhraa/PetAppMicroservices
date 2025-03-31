package tn.esprit.appointment.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.service.IAppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping("/addAppointment")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.addAppointment(appointment);
    }

    @PutMapping("/updateAppointment")
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(appointment);
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
}
