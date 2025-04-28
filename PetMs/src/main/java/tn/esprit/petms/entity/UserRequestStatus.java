package tn.esprit.petms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    public UserRequestStatus(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    private Long userId;
    private String status;
}