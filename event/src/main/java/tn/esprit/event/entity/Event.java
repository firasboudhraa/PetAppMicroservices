package tn.esprit.event.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private long idEvent;

    @JsonProperty
    private String nameEvent;

    @JsonProperty
    private String description;

    @JsonProperty
    private LocalDateTime dateEvent;

    @JsonProperty
    private String location;

    @JsonProperty
    private float goalAmount;

    @ElementCollection
    @CollectionTable(name = "event_ratings", joinColumns = @JoinColumn(name = "event_id"))
    private List<EventRating> ratings = new ArrayList<>();

    // Constructors
    public Event() {
    }

    public Event(long idEvent, String nameEvent, String description, LocalDateTime dateEvent, String location, float goalAmount) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.description = description;
        this.dateEvent = dateEvent;
        this.location = location;
        this.goalAmount = goalAmount;
    }

    // Builder implementation
    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static class EventBuilder {
        private long idEvent;
        private String nameEvent;
        private String description;
        private LocalDateTime dateEvent;
        private String location;
        private float goalAmount;

        public EventBuilder idEvent(long idEvent) {
            this.idEvent = idEvent;
            return this;
        }

        public EventBuilder nameEvent(String nameEvent) {
            this.nameEvent = nameEvent;
            return this;
        }

        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventBuilder dateEvent(LocalDateTime dateEvent) {
            this.dateEvent = dateEvent;
            return this;
        }

        public EventBuilder location(String location) {
            this.location = location;
            return this;
        }
        public EventBuilder goalAmount(float goalAmount) {
            this.goalAmount = goalAmount;
            return this;
        }

        public Event build() {
            return new Event(idEvent, nameEvent, description, dateEvent, location, goalAmount);
        }
    }

    // Getters and Setters
    public long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(long idEvent) {
        this.idEvent = idEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(float goalAmount) {
        this.goalAmount = goalAmount;
    }

    @Embeddable
    public static class EventRating {
        private int value;
        private String feedback;
        private Long userId;

        // Constructeurs
        public EventRating() {}

        public EventRating(int value, String feedback, Long userId) {
            this.value = value;
            this.feedback = feedback;
            this.userId = userId;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

    // Méthodes pour gérer les ratings
    public void addRating(int value, String feedback, Long userId) {
        // Vérifie si l'utilisateur a déjà noté
        EventRating existingRating = getUserRating(userId);
        if (existingRating != null) {
            existingRating.setValue(value);
            existingRating.setFeedback(feedback);
        } else {
            this.ratings.add(new EventRating(value, feedback, userId));
        }
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
                .mapToInt(EventRating::getValue)
                .average()
                .orElse(0.0);
    }

    public EventRating getUserRating(Long userId) {
        return ratings.stream()
                .filter(r -> r.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}