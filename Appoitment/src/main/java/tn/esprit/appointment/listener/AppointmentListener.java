package tn.esprit.appointment.listener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.appointment.service.IAppointmentService;

@Component
public class AppointmentListener {
    @Autowired
    private IAppointmentService appointmentService;

    @RabbitListener(queues = "petservice.queue")
    @Transactional
    public void handlePetServiceDeleted(@Payload String message) {
        Long idService = parseIdFromMessage(message);
        appointmentService.deleteAppointmentByService(idService);
    }

    private Long parseIdFromMessage(String message) {
        return Long.parseLong(
                message.substring(message.indexOf(":") + 1, message.indexOf("}")).trim()
        );
    }
}
