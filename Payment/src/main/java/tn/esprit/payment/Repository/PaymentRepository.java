package tn.esprit.payment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.payment.Entity.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(String status);
}