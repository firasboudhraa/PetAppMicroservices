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

    @PostMapping("/add-appointment")
    public Appointment createAppointment(Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    @PutMapping("/update-appointment/{id}")
    public Appointment updateAppointment(@PathVariable("id") Appointment appointment) {
        return appointmentService.updateAppointment(appointment);
    }

    @DeleteMapping("/delete-appointment/{id}")
    public void deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable("id") Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/service/{serviceId}")
    public List<Appointment> getAppointmentsByService(@PathVariable("serviceId") Long serviceId) {
        return appointmentService.getAppointmentsByService(serviceId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Appointment> getAppointmentsByCustomer(@PathVariable("customerId") Long customerId) {
        return appointmentService.getAppointmentsByCustomer(customerId);
    }

    @GetMapping("/vet/{vetId}")
    public List<Appointment> getAppointmentsByVet(@PathVariable("vetId") Long vetId) {
        return appointmentService.getAppointmentsByVet(vetId);
    }

}
