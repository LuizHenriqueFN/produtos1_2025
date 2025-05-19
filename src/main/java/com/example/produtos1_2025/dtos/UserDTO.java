package com.example.produtos1_2025.dtos;

import com.example.produtos1_2025.entity.User;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;

    @Email(message = "Email inválido!")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();

        user.getRoles().forEach(role ->
                roles.add(new RoleDTO(role))
        );
    }
}
