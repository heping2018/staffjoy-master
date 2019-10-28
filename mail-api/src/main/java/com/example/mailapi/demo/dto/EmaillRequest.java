package com.example.mailapi.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class EmaillRequest {
    @NotBlank(message = "please provide an email")
    private String to;
    @NotBlank(message = "please provide a subject")
    private String subject;
    @NotBlank(message = "please provide valid body")
    @JsonProperty("html_body")
    private String htmlBody;
    private String name;

}
