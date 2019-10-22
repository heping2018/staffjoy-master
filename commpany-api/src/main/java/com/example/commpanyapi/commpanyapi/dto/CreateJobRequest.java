package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateJobRequest {
    @NotBlank
    private String commpanyId;
    @NotBlank
    private String teamId;
    @NotBlank
    private String name;
    @NotBlank
    private String color;
}
