package tn.esprit.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // Fetch roles eagerly
    @JoinTable(
            name = "user_roles", // Join table name
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    // One-to-Many relationship with Appointment
    @ElementCollection
    @CollectionTable(name = "user_appointment_references",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "appointment_id")
    private List<Long> idAppointment = new ArrayList<>();

    /*
    // One-to-Many: User can create multiple posts
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    // One-to-Many: User can donate multiple times
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Donation> donations;


    // One-to-Many: User can request multiple pet sittings
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<PetSittingRequest> petSittingRequests;

    // One-to-Many: User can apply for multiple pet adoptions
    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL)
    private List<AdoptionRequest> adoptionRequests;
*/

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


}
