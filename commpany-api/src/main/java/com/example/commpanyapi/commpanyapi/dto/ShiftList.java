package com.example.commpanyapi.commpanyapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftList {
    @Builder.Default
    private List<ShiftDto> shiftDtos = new ArrayList<>();
    private Instant shiftStartAfter;
    private Instant shiftStartBefore;
}
