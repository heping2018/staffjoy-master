package com.example.commpanyapi.commpanyapi.dto;


import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

import static com.example.commpanyapi.commpanyapi.dto.CreateShiftRequest.MAX_SHIFT_DURATION;

/**
 * 换班
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
@Getter
@Setter
public class ShiftDto {
    @NotBlank
    private String id;
    @NotBlank
    private String companyId;
    @NotBlank
    private String teamId;
    @NotBlank
    private String usrId;
    @NotBlank
    private String jobId;
    @NotNull
    private Instant start;
    @NotNull
    private Instant stop;
    @NotNull
    private boolean published;
    @AssertTrue(message = "stop must be after start")
    public boolean stopAfterStart(){
        long duration = stop.toEpochMilli() - start.toEpochMilli();
        return duration > 0;
    }
    @AssertTrue(message = "Shift exceed max allowed hour duration")
    public boolean withInMaxDuration(){
        long duration = stop.toEpochMilli() - start.toEpochMilli();
        return duration <= MAX_SHIFT_DURATION;
    }

}
