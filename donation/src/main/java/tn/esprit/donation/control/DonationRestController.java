package tn.esprit.donation.control;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.donation.entity.Donation;
import tn.esprit.donation.service.IDonationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@AllArgsConstructor
@RequestMapping("/donation")
public class DonationRestController {
    @Autowired
    IDonationService donationService;
    // http://localhost:8010/donation/retrieve-all-donations
    @GetMapping("/retrieve-all-donations")
    public List<Donation> getdonations() {
        List<Donation> listdonations = donationService.retrieveAllDonations();
        return listdonations;
    }
    // http://localhost:8010/donation/retrieve-donation/1
    @GetMapping("/retrieve-donation/{donation-id}")
    public Donation retrievedonation(@PathVariable("donation-id") Long dId) {
        Donation donation = donationService.retrieveDonation(dId);
        return donation;
    }
    // http://localhost:8010/donation/add-donation
    @PostMapping("/add-donation")
    public Donation adddonation(@RequestBody Donation d) {
        Donation donation = donationService.addDonation(d);
        return donation;
    }
    // http://localhost:8010/donation/remove-donation/{donation-id}
    @DeleteMapping("/remove-donation/{donation-id}")
    public void removedonation(@PathVariable("donation-id") Long dId) {
        donationService.removeDonation(dId);
    }
    // http://localhost:8010/donation/modify-donation
    @PutMapping("/modify-donation")
    public Donation modifydonation(@RequestBody Donation d) {
        Donation donation = donationService.modifyDonation(d);
        return donation;
    }


    @GetMapping("/event/{event-id}")
    public ResponseEntity<List<Donation>> findAllDonations(
            @PathVariable("event-id") Long eventId
    ) {
        return ResponseEntity.ok(donationService.findAllDonationsByEvent(eventId));
    }

}