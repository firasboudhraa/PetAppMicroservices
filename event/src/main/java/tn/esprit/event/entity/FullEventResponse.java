package tn.esprit.event.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class FullEventResponse {
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

    private List<Donation> donations;

    // Builder implementation
    public static FullEventResponseBuilder builder() {
        return new FullEventResponseBuilder();
    }

    public static class FullEventResponseBuilder {
        private String nameEvent;
        private String description;
        private LocalDateTime dateEvent;
        private String location;
        private float goalAmount;
        private List<Donation> donations;

        public FullEventResponseBuilder nameEvent(String nameEvent) {
            this.nameEvent = nameEvent;
            return this;
        }

        public FullEventResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FullEventResponseBuilder dateEvent(LocalDateTime dateEvent) {
            this.dateEvent = dateEvent;
            return this;
        }

        public FullEventResponseBuilder location(String location) {
            this.location = location;
            return this;
        }
        public FullEventResponseBuilder goalAmount(float goalAmount) {
            this.goalAmount = goalAmount;
            return this;
        }

        public FullEventResponseBuilder donations(List<Donation> donations) {
            this.donations = donations;
            return this;
        }

        public FullEventResponse build() {
            FullEventResponse response = new FullEventResponse();
            response.setNameEvent(nameEvent);
            response.setDescription(description);
            response.setDateEvent(dateEvent);
            response.setLocation(location);
            response.setGoalAmount(goalAmount);
            response.setDonations(donations);
            return response;
        }
    }

    // Getters and Setters
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

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}