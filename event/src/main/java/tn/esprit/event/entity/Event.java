package tn.esprit.event.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    // Constructors
    public Event() {
    }

    public Event(long idEvent, String nameEvent, String description, LocalDateTime dateEvent, String location) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.description = description;
        this.dateEvent = dateEvent;
        this.location = location;
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

        public Event build() {
            return new Event(idEvent, nameEvent, description, dateEvent, location);
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
}