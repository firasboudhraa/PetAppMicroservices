package tn.esprit.posts.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormDTO {
    private String name;
    private String email;
    private String subject;
    private String message;
}
