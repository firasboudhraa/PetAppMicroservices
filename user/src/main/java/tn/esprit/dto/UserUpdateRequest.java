package tn.esprit.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.entity.RoleEnum;

@Getter
@Setter
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleEnum role;
}