package com.example.accountapi.demo.model;

import com.example.commonlib.commonlib.validation.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * http请求数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @NotNull
    private String id;
    private String name;
    @Email(message = "invalid emaill")
    private String email;
    @PhoneNumber
    private String phoneNumber;
    @NotEmpty
    private String photoUrl;
    private boolean confirmedActive;
    @NotNull
    private Instant memberSince;
}
