package com.devmare.hack4bengal.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;
}
