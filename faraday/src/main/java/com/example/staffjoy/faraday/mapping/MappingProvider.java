package com.example.staffjoy.faraday.mapping;


import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;

import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class MappingProvider {
    private  final FaradayPropertise faradayPropertise;
    private List<MappingPropertise> mappingPropertiseList;

    public MappingProvider(FaradayPropertise faradayPropertise){
        this.faradayPropertise = faradayPropertise;
    }


    public abstract List<MappingPropertise> retrieveMappings();
}
