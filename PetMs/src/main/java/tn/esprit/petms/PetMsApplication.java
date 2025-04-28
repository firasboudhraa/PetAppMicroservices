package tn.esprit.petms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PetMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetMsApplication.class, args);
    }

}
