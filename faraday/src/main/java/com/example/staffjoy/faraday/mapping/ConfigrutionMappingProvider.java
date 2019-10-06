package com.example.staffjoy.faraday.mapping;

import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.HttpProvide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 静态映射
 */
@Slf4j
public class ConfigrutionMappingProvider extends  MappingProvider{

    public ConfigrutionMappingProvider(FaradayPropertise faradayPropertise, MappingValidator mappingValidator, HttpProvide httpProvide) {
        super(faradayPropertise,mappingValidator,httpProvide);
    }

    @Override
    public boolean shouldupdate(HttpRequest httpRequest) {
        return false;
    }

    /**
     * 获取本地文件中的地址映射
     * @return
     */
    @Override
    public List<MappingPropertise> retrieveMappings() {
        if(log.isDebugEnabled()){
            log.debug("{}","获取本地文件中的地址映射");
        }
        return faradayPropertise.getMappingPropertise().stream()
                .map(MappingPropertise::copy)
                .collect(Collectors.toList());
    }

}
