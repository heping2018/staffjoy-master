package com.example.staffjoy.faraday.mapping;


import com.example.staffjoy.faraday.config.FaradayPropertise;
import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.http.HttpProvide;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;



/**
 *
 */
@Slf4j
public abstract class MappingProvider {
    protected   final FaradayPropertise faradayPropertise;
    private List<MappingPropertise> mappingPropertiseList;
    private MappingValidator mappingValidator;
    private HttpProvide httpProvide;

    public MappingProvider(FaradayPropertise faradayPropertise,
                           MappingValidator mappingValidator,
                           HttpProvide httpProvide){
        this.faradayPropertise = faradayPropertise;
        this.mappingValidator = mappingValidator;
        this.httpProvide = httpProvide;
    }



    public MappingPropertise resolverMapping(String host, HttpRequest httpRequest){
        if(shouldupdate(httpRequest)){
            updateMap();
        }
        List<MappingPropertise> mappingPropertiseList1 = mappingPropertiseList.stream()
                .filter(mappingPropertise -> mappingPropertise.getHost().toLowerCase().equals(host.toLowerCase()))
                .collect(Collectors.toList());
        if(isEmpty(mappingPropertiseList1)){
            return null;
        }
        return mappingPropertiseList1.get(0);
    }


    /**
     * 初始化存入本地缓存
     * 转化为restful映射
     */
    @PostConstruct
    public synchronized void updateMap(){
        List<MappingPropertise> mappings = this.retrieveMappings();
        mappingValidator.validate(mappings);
        mappingPropertiseList = mappings;
        httpProvide.updateHttpClient(mappingPropertiseList);
        if(log.isInfoEnabled()){
            log.info("Destination mappings updated: "+mappingPropertiseList);
        }
    }

    public abstract boolean shouldupdate(HttpRequest httpRequest);


    public abstract List<MappingPropertise> retrieveMappings();
}
