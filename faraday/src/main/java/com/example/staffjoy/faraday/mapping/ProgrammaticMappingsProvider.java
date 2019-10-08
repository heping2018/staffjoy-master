package com.example.staffjoy.faraday.mapping;

import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.example.commonlib.commonlib.service.Service;
import com.example.commonlib.commonlib.service.ServiceDirectory;
import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.HttpProvide;
import org.springframework.http.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 后端服务的映射
 */
public class ProgrammaticMappingsProvider  extends  MappingProvider{
    private EnvConfig envConfig;
    public ProgrammaticMappingsProvider(FaradayPropertise faradayPropertise,
                                        MappingValidator mappingValidator,
                                        HttpProvide httpProvide,
                                        EnvConfig envConfig) {
        super(faradayPropertise, mappingValidator, httpProvide);
        this.envConfig = envConfig;
    }

    @Override
    public boolean shouldupdate(HttpRequest httpRequest) {
        return false;
    }


    @Override
    public List<MappingPropertise> retrieveMappings() {
        Map<String, Service> serviceMap = ServiceDirectory.getMap();
        List<MappingPropertise> mappings = new ArrayList<>();
        for (String key : serviceMap.keySet()){
            String domian = key;
            MappingPropertise mappingPropertise = new MappingPropertise();
            mappingPropertise.setName(domian);
            mappingPropertise.setHost(domian+"."+envConfig.getExternalApex());
            Service service = serviceMap.get(domian);
            String redi = "http://"+service.getBackendDomain();
            mappingPropertise.setDestinations(Arrays.asList(redi));
            mappings.add(mappingPropertise);
        }
        return mappings;
    }
}
