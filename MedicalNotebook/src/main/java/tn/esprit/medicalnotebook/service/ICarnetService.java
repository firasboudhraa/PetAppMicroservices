package tn.esprit.medicalnotebook.service;


import org.springframework.stereotype.Service;
import tn.esprit.medicalnotebook.Client.MedicalRecordClient;
import tn.esprit.medicalnotebook.entity.Carnet;
import tn.esprit.medicalnotebook.entity.FullCarnetResponse;

import java.util.List;
public interface ICarnetService {
    public List<Carnet> retrieveAllCarnets();
    public Carnet retrieveCarnet(Long carnetId);
    public Carnet addCarnet(Carnet c);
    public void removeCarnet(Long carnetId);
    public Carnet modifyCarnet(Carnet carnet);
    public FullCarnetResponse getMedicalRecordsByCarnet(Long carnetId) ;

    }
