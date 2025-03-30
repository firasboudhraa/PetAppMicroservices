package tn.esprit.medicalnotebook.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.medicalnotebook.entity.MedicalRecord;

import java.util.List;

@FeignClient(name = "medical-record-service", url = "${application.config.record-url}")

public interface MedicalRecordClient {
    @GetMapping("/medical-records/byCarnet/{id}")
    List<MedicalRecord> getMedicalRecordsByCarnet(@PathVariable("id") Long id);
}