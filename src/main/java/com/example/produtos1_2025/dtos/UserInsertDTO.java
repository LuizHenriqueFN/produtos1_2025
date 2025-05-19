package com.example.produtos1_2025.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInsertDTO extends UserDTO {
    private String password;
}
