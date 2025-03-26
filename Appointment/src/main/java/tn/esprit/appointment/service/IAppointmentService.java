package tn.esprit.appointment.service;

import tn.esprit.appointment.entity.Appointment;

import java.util.List;

public interface IAppointmentService {
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(Appointment appointment);
    void deleteAppointment(Long id);
    Appointment getAppointmentById(Long id);
    List<Appointment> getAppointmentsByService(Long serviceId);
    List<Appointment> getAppointmentsByCustomer(Long customerId);
    List<Appointment> getAppointmentsByVet(Long vetId);
}
