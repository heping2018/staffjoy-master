package com.example.accountapi.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPasswordRequest {
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
}
