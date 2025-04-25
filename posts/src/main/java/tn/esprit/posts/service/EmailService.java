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


    @Autowired
    private UserClient userClient;  // Use the Feign client to interact with the User service

    public void sendPostDeletionEmail(Post post) {
        if (post != null) {
            Long userId = post.getUserId();
            UserDTO user = userClient.getUserById(userId);

            if (user != null) {
                String subject = "Post Deleted Notification";
                String content = "The post with title '" + post.getTitle() + "' has been deleted by one of our admins because it did not comply with our community guidelines or policies. If you believe this was a mistake, feel free to contact us.\n\nThank you for your understanding.\n\nAymen Thabet, FureverBuddy Admin Team";


                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject(subject);
                message.setText(content);

                mailSender.send(message);
            }
        }
    }

}
