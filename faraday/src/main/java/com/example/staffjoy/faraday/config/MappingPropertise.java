package com.example.staffjoy.faraday.config;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 静态地址映射类
 */
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MappingPropertise {

    private String name;
    private String host;
    /*
    目标地址集合
     */
    private List<String> destinations = new ArrayList<>();


    public MappingPropertise copy(){
        MappingPropertise mappingPropertise = new MappingPropertise();
        mappingPropertise.setHost(this.host);
        mappingPropertise.setName(this.name);
        mappingPropertise.setDestinations(this.destinations);
        return mappingPropertise;

    }


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
