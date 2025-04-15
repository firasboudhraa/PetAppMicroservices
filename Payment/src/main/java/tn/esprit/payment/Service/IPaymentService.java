package tn.esprit.payment.Service;

import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;
import tn.esprit.payment.Entity.Payment;

import java.util.List;

public interface IPaymentService {

    Payment createPayment(Long basketId, Long userId);
    void validateBasket(Long basketId);
    Payment getPayment(Long paymentId);
    Payment updatePayment(Long paymentId, Payment payment);
    void deletePayment(Long paymentId);
    List<Payment> getAllPayments();
}
