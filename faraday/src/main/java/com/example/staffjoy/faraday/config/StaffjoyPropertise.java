package com.example.staffjoy.faraday.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix="staffjoy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StaffjoyPropertise {

    @NotNull
    private String sginingScoDecret;
}
