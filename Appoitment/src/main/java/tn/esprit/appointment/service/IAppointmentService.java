package tn.esprit.appointment.service;

import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.entity.AppointmentStatus;

import java.util.List;

public interface IAppointmentService {
    public Appointment addAppointment(Appointment appointment);
    public Appointment updateAppointment(Appointment appointment);
    public void deleteAppointment(Long id);
    public Appointment findAppointmentById(Long id);
    public List<Appointment> findAllAppointments();

    public List<Appointment> getAppointmentsByService(Long serviceId);

    public void deleteAppointmentByService(Long idService);
    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status, String reason);
    public List<Appointment> getAppointmentsByOwner(Long idOwner);
    public List<Appointment> getAppointmentsByVet(Long idVet);
    public List<Appointment> getAppointmentsByPet(Long idPet);

}
