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
    @Column(name = "rating")
    private List<Integer> ratings = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "event_feedbacks", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "feedback")
    private List<String> feedbacks = new ArrayList<>();

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

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public List<String> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<String> feedbacks) {
        this.feedbacks = feedbacks;
    }

    // Add helper methods
    public void addRating(int rating, String feedback) {
        this.ratings.add(rating);
        this.feedbacks.add(feedback);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }
}