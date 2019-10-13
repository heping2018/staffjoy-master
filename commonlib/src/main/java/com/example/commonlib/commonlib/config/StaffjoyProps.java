package com.example.commonlib.commonlib.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@ConfigurationProperties(prefix = "staffjoy.commom")
public class StaffjoyProps {
    @NotBlank
    //发送到sentry所需要的dsn
    private String sentryDsn;
    @NotBlank
    //部署到kubenertes
    private String deployEnv;
}
