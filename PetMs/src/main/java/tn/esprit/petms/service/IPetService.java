package tn.esprit.petms.service;

import org.springframework.stereotype.Service;
import tn.esprit.petms.entity.Pet;

import java.util.List;

public interface IPetService {

    public List<Pet> retrieveAllPets();
    public Pet retrievePet(Long idPet);
    public Pet addPet(Pet p);
    public void removePet(Long idPet);
    public Pet modifyPet(Pet pet);
}
