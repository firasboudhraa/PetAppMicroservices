package tn.esprit.event.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import tn.esprit.event.client.DonationClient;
import tn.esprit.event.entity.Donation;
import tn.esprit.event.entity.Event;
import tn.esprit.event.entity.FullEventResponse;
import tn.esprit.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService{
    @Autowired
    EventRepository EventRepository;
    @Autowired
    DonationClient donationClient;

    public List<Event> retrieveAllEvents() {
        return EventRepository.findAll();
    }
    public Event retrieveEvent(Long eventId) {
        return EventRepository.findById(eventId).get();
    }
    public Event addEvent(Event e) {
        return EventRepository.save(e);
    }
    public void removeEvent(Long eventId) {
        EventRepository.deleteById(eventId);
    }
    public Event modifyEvent(Event event) {
        return EventRepository.save(event);

    }

    @Override
    public FullEventResponse findEventsWithDonations(Long eventId) {
        Event event = EventRepository.findById(eventId)
                .orElse(Event.builder()
                        .nameEvent("NOT_FOUND")
                        .description("NOT_FOUND")
                        .dateEvent(LocalDateTime.now())
                        .location("NOT_FOUND")
                        .build());

        var donations = donationClient.findAllDonationsByEvent(eventId);

        return FullEventResponse.builder()
                .nameEvent(event.getNameEvent())
                .description(event.getDescription())
                .dateEvent(event.getDateEvent())
                .location(event.getLocation())
                .donations(donations)
                .build();
    }
}
