package tn.esprit.product;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductApplication {


    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("Product")
                .filename(".env")
                .load();

        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("EUREKA_HOST", dotenv.get("EUREKA_HOST"));
        System.setProperty("EUREKA_URL", dotenv.get("EUREKA_URL"));
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(ProductApplication.class, args);    }

}
