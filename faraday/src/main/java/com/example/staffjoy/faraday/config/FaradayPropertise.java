package com.example.staffjoy.faraday.config;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("faraday")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FaradayPropertise {
    @NestedConfigurationProperty
    private List<MappingPropertise> mappingPropertise = new ArrayList<>();
    @NestedConfigurationProperty
    private TracingProperties tracing = new TracingProperties();



}
