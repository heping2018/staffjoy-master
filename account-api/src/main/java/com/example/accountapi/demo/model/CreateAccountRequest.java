package com.example.accountapi.demo.model;

import com.example.commonlib.commonlib.validation.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateAccountRequest {
    @NotNull
    private String name;
    @Email(message = "invalid emaill")
    private String emaill;
    @PhoneNumber
    private String phonenumber;
    @AssertTrue(message = "Empty request")
    private boolean isVailRequest(){
        return StringUtils.hasText(name) || StringUtils.hasText(emaill) || StringUtils.hasText(phonenumber);
    }
}
