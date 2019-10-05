package com.example.staffjoy.faraday.mapping;

import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;

import java.util.List;

public class ConfigrutionMappingProvider extends  MappingProvider{
    public ConfigrutionMappingProvider(FaradayPropertise faradayPropertise) {
        super(faradayPropertise);
    }

    @Override
    public List<MappingPropertise> retrieveMappings() {
        return null;
    }

}
