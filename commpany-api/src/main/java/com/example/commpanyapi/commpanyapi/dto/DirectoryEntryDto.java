package com.example.commpanyapi.commpanyapi.dto;


import com.example.commonlib.commonlib.validation.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryEntryDto {
    @NotBlank
    private String usrId;
    @NotBlank
    private String internaId;
    @NotBlank
    private String companyId;
    @NotBlank
    @Builder.Default
    private String name = "";
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @PhoneNumber
    private String phoneNumber;
    private String photoUrl;
    private boolean confirmedAndActive;
}
