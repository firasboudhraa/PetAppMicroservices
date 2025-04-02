package tn.esprit.appointment.service;

import tn.esprit.appointment.entity.Appointment;

import java.util.List;

public interface IAppointmentService {
    public Appointment addAppointment(Appointment appointment);
    public Appointment updateAppointment(Appointment appointment);
    public void deleteAppointment(Long id);
    public Appointment findAppointmentById(Long id);
    public List<Appointment> findAllAppointments();

    List<Appointment> getAppointmentsByService(Long serviceId);

    void deleteAppointmentByService(Long idService);
}
