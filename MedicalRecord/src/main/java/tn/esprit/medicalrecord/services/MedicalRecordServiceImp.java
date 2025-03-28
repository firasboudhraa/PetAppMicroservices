package tn.esprit.medicalrecord.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.medicalrecord.entity.MedicalRecord;
import tn.esprit.medicalrecord.repository.MedicalRecordRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicalRecordServiceImp implements IMedicalRecordService {
    @Autowired
    public MedicalRecordRepository medicalRecordRepository;
    @Override
    public List<MedicalRecord> retrieveAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
    @Override

    public MedicalRecord retrieveMedicalRecord(Long cId) {
        return medicalRecordRepository.findById(cId).get();
    }
    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
    @Override
    public void removeMedicalRecord(Long cId) {
        medicalRecordRepository.deleteById(cId);
    }
    @Override
    public MedicalRecord modifyMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
}
