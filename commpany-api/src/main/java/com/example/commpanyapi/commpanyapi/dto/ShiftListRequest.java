package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShiftListRequest {
    @NotBlank
    private String companyId;
    @NotBlank
    private String teamId;
    @NotBlank
    private String userId;
    @NotBlank
    private String jobId;
    @NotNull
    private Instant shiftStartAfter;
    @NotNull
    private Instant shiftSrartBefore;

    public boolean correctAfterAndBefore(){
        long duration = shiftStartAfter.toEpochMilli() - shiftSrartBefore.toEpochMilli();
        return duration < 0;
    }

}
