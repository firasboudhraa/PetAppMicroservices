package tn.esprit.medicalnotebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.medicalnotebook.Client.MedicalRecordClient;
import tn.esprit.medicalnotebook.entity.Carnet;
import tn.esprit.medicalnotebook.entity.FullCarnetResponse;
import tn.esprit.medicalnotebook.repository.CarnetRepository;

import java.util.List;
@Service

public class CarnetServiceImp implements ICarnetService {
    @Autowired
    public CarnetRepository carnetRepository;
    @Autowired
    public MedicalRecordClient medicalRecordclient;
    @Override
    public List<Carnet> retrieveAllCarnets() {
        return carnetRepository.findAll();
    }
    @Override

    public Carnet retrieveCarnet(Long cId) {
        return carnetRepository.findById(cId).get();
    }
    @Override
    public Carnet addCarnet(Carnet c) {
        return carnetRepository.save(c);
    }
    @Override
    public void removeCarnet(Long cId) {
        carnetRepository.deleteById(cId);
    }
    @Override
    public Carnet modifyCarnet(Carnet carnet) {
        return carnetRepository.save(carnet);
    }
    @Override

    public FullCarnetResponse getMedicalRecordsByCarnet(Long carnetId) {
        var carnets = carnetRepository.findById(carnetId).get();
        var medicalRecords = medicalRecordclient.getMedicalRecordsByCarnet(carnetId);
        return FullCarnetResponse.builder()
                .name(carnets.getName())
                .medicalRecords(medicalRecords)
                .build();
    }
}
