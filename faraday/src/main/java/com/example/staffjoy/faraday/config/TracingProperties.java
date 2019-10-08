package com.example.staffjoy.faraday.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 调用链跟踪
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TracingProperties {

    private boolean enable;
}
