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

    @Column(name = "user_id")
    @JsonProperty
    private Long userId;

    @JsonProperty
    private String paymentMethod;

    @JsonProperty
    private String transactionId;

    @JsonProperty
    private String status; // PENDING, COMPLETED, FAILED

    @JsonProperty
    private String badgeLevel;

    // Niveaux de badges et seuils
    public static final String[] BADGE_LEVELS = {
            "Bronze",
            "Silver",
            "Gold",
            "Platinum",
            "Diamond"
    };

    public static final double[] BADGE_THRESHOLDS = {
            50.0,   // Bronze
            200.0,  // Silver
            500.0,  // Gold
            1000.0, // Platinum
            2000.0  // Diamond
    };

    public Donation() {
    }

    public Donation(Long id, float amount, LocalDateTime date, Long eventId, Long userId,
                    String paymentMethod, String transactionId, String status) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.eventId = eventId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
        this.assignBadge();
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
        this.assignBadge();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBadgeLevel() {
        return badgeLevel;
    }

    public void setBadgeLevel(String badgeLevel) {
        this.badgeLevel = badgeLevel;
    }

    // Méthodes pour les badges
    public String calculateBadgeLevel() {
        for (int i = BADGE_THRESHOLDS.length - 1; i >= 0; i--) {
            if (this.amount >= BADGE_THRESHOLDS[i]) {
                return BADGE_LEVELS[i];
            }
        }
        return "New Donor";
    }

    public void assignBadge() {
        this.badgeLevel = this.calculateBadgeLevel();
    }
}