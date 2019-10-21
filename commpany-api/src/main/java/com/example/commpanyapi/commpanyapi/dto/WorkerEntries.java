package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerEntries {
    @NotBlank
    private String companyId;
    @NotBlank
    private String teamId;
    @Builder.Default
    private List<DirectoryEntryDto> works = new ArrayList<>();

}
