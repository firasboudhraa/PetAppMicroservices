package tn.esprit.product.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/products/images")
public class ImageController {

    private static final String UPLOAD_DIR = "Product/uploads/";

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path file = Paths.get(UPLOAD_DIR + imageName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();

                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
