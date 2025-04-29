package tn.esprit.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime timestamp;
    private boolean seen;
}
