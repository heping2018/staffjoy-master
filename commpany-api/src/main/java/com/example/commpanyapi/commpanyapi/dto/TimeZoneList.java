package com.example.commpanyapi.commpanyapi.dto;

import lombok.*;

import java.util.List;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneList {
    @Singular
    private List<String> timezone;
}
