package com.example.commpanyapi.commpanyapi.dto;

import com.example.commonlib.commonlib.validation.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewDIrectoryEntry {
    @NotBlank
    private String companyId;
    @Builder.Default
    private String name = "";
    @Email
    private String emaill;
    @PhoneNumber
    private String phoneNumber;
    @Builder.Default
    private String internalId = "";
}
