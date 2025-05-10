package tn.esprit.donation.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.donation.entity.Donation;
import tn.esprit.donation.service.IDonationService;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/donation")
public class DonationRestController {
    @Autowired
    IDonationService donationService;

    @GetMapping("/retrieve-all-donations")
    public List<Donation> getDonations() {
        return donationService.retrieveAllDonations();
    }

    @GetMapping("/retrieve-donation/{donation-id}")
    public Donation retrieveDonation(@PathVariable("donation-id") Long dId) {
        return donationService.retrieveDonation(dId);
    }

    @PostMapping("/add-donation")
    public Donation addDonation(@RequestBody Donation d) {
        return donationService.addDonation(d);
    }

    @DeleteMapping("/remove-donation/{donation-id}")
    public void removeDonation(@PathVariable("donation-id") Long dId) {
        donationService.removeDonation(dId);
    }

    @PutMapping("/modify-donation")
    public Donation modifyDonation(@RequestBody Donation d) {
        return donationService.modifyDonation(d);
    }

    @GetMapping("/event/{event-id}")
    public ResponseEntity<List<Donation>> findAllDonations(@PathVariable("event-id") Long eventId) {
        return ResponseEntity.ok(donationService.findAllDonationsByEvent(eventId));
    }

    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<Donation>> findAllDonationsByUser(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(donationService.findAllDonationsByUser(userId));
    }

    @GetMapping("/user/{user-id}/event/{event-id}")
    public ResponseEntity<List<Donation>> findAllDonationsByUserAndEvent(
            @PathVariable("user-id") Long userId,
            @PathVariable("event-id") Long eventId) {
        return ResponseEntity.ok(donationService.findAllDonationsByUserAndEvent(userId, eventId));
    }

    @GetMapping("/badge-levels")
    public ResponseEntity<Map<String, Object>> getBadgeLevels() {
        Map<String, Object> response = new HashMap<>();
        response.put("levels", Donation.BADGE_LEVELS);
        response.put("thresholds", Donation.BADGE_THRESHOLDS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user-id}/top-badge")
    public ResponseEntity<?> getUserTopBadge(@PathVariable("user-id") Long userId) {
        try {
            List<Donation> donations = donationService.findAllDonationsByUser(userId);

            if (donations == null || donations.isEmpty()) {
                return ResponseEntity.ok("New Donor");
            }

            String topBadge = donations.stream()
                    .filter(d -> d != null && "COMPLETED".equals(d.getStatus()))
                    .map(d -> d.getBadgeLevel() != null ? d.getBadgeLevel() : "New Donor")
                    .max(Comparator.comparingInt(b -> {
                        for (int i = 0; i < Donation.BADGE_LEVELS.length; i++) {
                            if (Donation.BADGE_LEVELS[i].equals(b)) {
                                return i;
                            }
                        }
                        return -1;
                    }))
                    .orElse("New Donor");

            return ResponseEntity.ok(topBadge);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing request: " + e.getMessage());
        }
    }
}