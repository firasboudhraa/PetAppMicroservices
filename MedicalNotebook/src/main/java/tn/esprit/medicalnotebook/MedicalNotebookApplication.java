package tn.esprit.medicalnotebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class MedicalNotebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalNotebookApplication.class, args);
    }

}
