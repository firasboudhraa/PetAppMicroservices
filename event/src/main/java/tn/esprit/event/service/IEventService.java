package tn.esprit.event.service;

import tn.esprit.event.entity.Event;
import tn.esprit.event.entity.FullEventResponse;

import java.util.List;

public interface IEventService {
    List<Event> retrieveAllEvents();
    Event retrieveEvent(Long eventId);
    Event addEvent(Event e);
    void removeEvent(Long eventId);
    Event modifyEvent(Event event);
    FullEventResponse findEventsWithDonations(Long eventId);
    Event addRatingToEvent(Long eventId, int rating, String feedback);
    double getAverageRating(Long eventId);
}