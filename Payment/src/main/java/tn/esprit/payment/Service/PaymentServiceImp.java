package tn.esprit.payment.Service;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.payment.Client.BasketClient;
import tn.esprit.payment.Client.UserClient;
import tn.esprit.payment.Dto.BasketDTO;
import tn.esprit.payment.Dto.UserDTO;
import tn.esprit.payment.Entity.Payment;
import tn.esprit.payment.Repository.PaymentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImp implements IPaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BasketClient basketClient;

    @Autowired
    private UserClient userClient;

    @Override
    public Payment createPayment(Long basketId, Long userId) {
        try {
            // Récupération du panier et de l'utilisateur
            BasketDTO basketDTO = basketClient.getBasketById(basketId);
            UserDTO userDTO = userClient.getUserById(userId);

            // Vérification des valeurs retournées
            if (basketDTO == null) {
                throw new IllegalArgumentException("Basket not found for ID: " + basketId);
            }
            if (userDTO == null) {
                throw new IllegalArgumentException("User not found for ID: " + userId);
            }

            // Création du paiement
            Payment payment = new Payment();
            payment.setUserId(userDTO.getId_User());
            payment.setBasketId(basketDTO.getId_Basket());
            payment.setAmount(basketDTO.getTotal());
            payment.setStatus("pending"); // Statut initial
            payment.setPaymentMethod("carte");
            payment.setPaymentDate(LocalDate.now());

            // Sauvegarde du paiement
            return paymentRepository.save(payment);
        } catch (IllegalArgumentException e) {
            // Gestion des cas où le panier ou l'utilisateur sont introuvables
            throw new RuntimeException("Invalid data: " + e.getMessage(), e);
        } catch (FeignException e) {
            // Gestion des erreurs liées aux appels aux services externes
            throw new RuntimeException("Failed to communicate with external services", e);
        } catch (Exception e) {
            // Gestion des autres exceptions
            throw new RuntimeException("Failed to create payment due to an unknown error", e);
        }
    }


    @Override
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public Payment updatePayment(Long paymentId, Payment payment) {
        Payment existingPayment = getPayment(paymentId);
        existingPayment.setStatus(payment.getStatus());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setAmount(payment.getAmount());

        return paymentRepository.save(existingPayment);
    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment existingPayment = getPayment(paymentId);
        paymentRepository.delete(existingPayment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
