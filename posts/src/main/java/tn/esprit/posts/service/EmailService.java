package tn.esprit.posts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.posts.entity.Post;
import tn.esprit.posts.entity.UserDTO;  // Assuming you are using UserDTO as the data transfer object

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPostDeletionEmail(String title, String author, String email) {
        if (email != null && !email.isEmpty()) {
            String subject = "Post Deleted Notification";
            String content = "Hello " + author + ",\n\n" +
                    "We wanted to let you know that your post titled \"" + title + "\" has been deleted by our admin team because it did not comply with our community guidelines.\n\n" +
                    "If you believe this was a mistake, please contact us.\n\n" +
                    "Thank you for your understanding.\n\n" +
                    "FureverBuddy Admin Team";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);

<<<<<<< HEAD
    public void sendPostDeletionEmail(String title, String firstName, String email) {
        String subject = "Post Deleted Notification";
        String body = "Hello " + firstName + ",\n\n" +
                "Your post titled '" + title + "' has been deleted by our admin team for not complying with our community guidelines.\n" +
                "If you believe this was a mistake, please contact us.\n\n" +
                "Thank you for your understanding.\n\n" +
                "Aymen Thabet, FureverBuddy Admin Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
=======
            mailSender.send(message);
        }
>>>>>>> c3d9507ad79d261ff9347f2c2b56b58830a98c30
    }
}
