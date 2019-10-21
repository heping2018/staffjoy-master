package com.example.commpanyapi.commpanyapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkDto {
    private String companyId;
    private String teamId;
    private String usrId;
}
