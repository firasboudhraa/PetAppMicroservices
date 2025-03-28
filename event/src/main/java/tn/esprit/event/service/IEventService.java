package tn.esprit.event.service;

import tn.esprit.event.entity.Event;
import tn.esprit.event.entity.FullEventResponse;

import java.util.List;

public interface IEventService {
    public List<Event> retrieveAllEvents();
    public Event retrieveEvent(Long eventId);
    public Event addEvent(Event e);
    public void removeEvent(Long eventId);
    public Event modifyEvent(Event event);

    FullEventResponse findEventsWithDonations(Long eventId);
}
