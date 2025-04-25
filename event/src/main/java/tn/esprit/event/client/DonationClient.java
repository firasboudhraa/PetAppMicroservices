package tn.esprit.event.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.event.entity.Donation;

import java.util.List;

@FeignClient(name = "donation-service", url = "${application.config.donations-url}")
public interface DonationClient {
    @GetMapping("/event/{event-id}")
    List<Donation> findAllDonationsByEvent(@PathVariable("event-id") Long eventId);
}
