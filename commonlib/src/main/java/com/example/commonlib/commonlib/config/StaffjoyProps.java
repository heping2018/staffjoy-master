package com.example.commonlib.commonlib.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@ConfigurationProperties(prefix = "staffjoy.commom")
public class StaffjoyProps {
    private String sentryDsn;
    private String deployEnv;
}
