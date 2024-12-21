package com.example.curd.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "user name cannot be null")
    private String name;
    @NotBlank(message = "user email cannot be null")
    @Email(message = "invalid email address")
    private String email;
}
