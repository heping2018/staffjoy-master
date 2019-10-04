package com.example.staffjoy.faraday.config;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MappingPropertise {

    private String name;
    private String host;
    private List<String> destinations = new ArrayList<>();


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    private static class TimeOutPropertise{
        private int connect = 20000;
        private int read = 20000;
    }
}
