package com.example.accountapi.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrackEventRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String event;
}
