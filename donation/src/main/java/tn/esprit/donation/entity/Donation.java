package tn.esprit.donation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @JsonProperty
    private float amount;

    @JsonProperty
    private LocalDateTime date;

    @JsonProperty
    private Long eventId;

    @JsonProperty
    private String paymentMethod;

    @JsonProperty
    private String transactionId;
    @JsonProperty
    private String status; // PENDING, COMPLETED, FAILED


    // Constructeurs
    public Donation() {
    }

    public Donation(Long id, float amount, LocalDateTime date, Long eventId, String paymentMethod, String transactionId, String status) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.eventId = eventId;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", eventId=" + eventId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}