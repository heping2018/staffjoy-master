package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateShiftRequest {
    public static final long MAX_SHIFT_DURATION = TimeUnit.HOURS.toMillis(23);
    @NotBlank
    private String commpanyId;
    @NotBlank
    private String teamId;
    @NotNull
    private Instant start;
    @NotNull
    private Instant stop;
    @NotBlank
    private String userId;
    @NotBlank
    private String jobId;
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
