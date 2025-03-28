package tn.esprit.donation.service;

import tn.esprit.donation.entity.Donation;

import java.util.List;

public interface IDonationService {
    public List<Donation> retrieveAllDonations();
    public Donation retrieveDonation(Long donationId);
    public Donation addDonation(Donation d);
    public void removeDonation(Long donationId);
    public Donation modifyDonation(Donation donation);

    public  List<Donation> findAllDonationsByEvent(Long eventId);
}
