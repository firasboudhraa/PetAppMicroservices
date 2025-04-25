package tn.esprit.payment.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private BasketDTO basketDTO;
    private UserDTO userDTO;
}