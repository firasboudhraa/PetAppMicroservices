package tn.esprit.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.appointment.entity.Appointment;
import tn.esprit.appointment.entity.AppointmentStatus;
import tn.esprit.appointment.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentServiceImpl  implements  IAppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public Appointment addAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).get();
    }

    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByService(Long idService) {
        return appointmentRepository.findByIdService(idService);
    }

    @Override
    @Transactional
    public void deleteAppointmentByService(Long idService) {
        appointmentRepository.deleteByIdService(idService);
    }

    @Override
    public Appointment updateAppointmentStatus(Long id, AppointmentStatus status, String reason) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setStatus(status);
        appointment.setReason(reason);
        return appointmentRepository.save(appointment);
    }
}
