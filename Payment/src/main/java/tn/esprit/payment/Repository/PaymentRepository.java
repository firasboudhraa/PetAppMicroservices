package tn.esprit.payment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.payment.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}