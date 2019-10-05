package com.example.staffjoy.faraday.mapping;


import com.example.staffjoy.faraday.config.MappingPropertise;
import com.example.staffjoy.faraday.exception.FaradayException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * 校验ip映射
 */
@Slf4j
public class MappingValidator {


    public void validate(List<MappingPropertise> mappingPropertiseList){
        if(!isEmpty(mappingPropertiseList)){
            if(log.isDebugEnabled()){
                log.debug("validate route");
            }
            int listSize = mappingPropertiseList.size();
            mappingPropertiseList.forEach(this :: correctMapping);
            int namesize = mappingPropertiseList.stream()
                    .map(MappingPropertise :: getName)
                    .collect(toSet())
                    .size();
            if(namesize < listSize){
                throw new FaradayException("Duplicated route name is mappings");
            }
            int hostsize = mappingPropertiseList.stream()
                    .map(MappingPropertise::getHost)
                    .collect(toSet())
                    .size();
            if(hostsize < listSize){
                throw new FaradayException("Duplicated route host is mappings");
            }
           //排序
            mappingPropertiseList.sort((map1,map2) ->
                map2.getHost().compareTo(map1.getHost())
            );

        }
    }

    public void correctMapping(MappingPropertise mappingPropertise){
        validatorname(mappingPropertise);
        validatorhost(mappingPropertise);
        validatordestinations(mappingPropertise);
        validatortimeOutPropertise(mappingPropertise);
    }

    /**
     * 校验name
     * @param mappingPropertise
     */
    public void validatorname(MappingPropertise mappingPropertise){
        if(isBlank(mappingPropertise.getName())){
            throw new FaradayException("Empty name of mapping "+mappingPropertise);
        }
    }
    /**
     * 校验host
     * @param mappingPropertise
     */
    public void validatorhost(MappingPropertise mappingPropertise){
        if(isBlank(mappingPropertise.getHost())){
            throw new FaradayException("Empty host of mapping "+mappingPropertise);
        }
    }

    /**
     * 校验destinations
     * @param mappingPropertise
     */
    public void validatordestinations(MappingPropertise mappingPropertise){
        if(isEmpty(mappingPropertise.getDestinations())){
            throw new FaradayException("no destinations of mapping "+mappingPropertise);
        }
        int size = mappingPropertise.getDestinations().size();
        List<String> conrrectedHost = new ArrayList<>(size);
        mappingPropertise.getDestinations().forEach(destination -> {
            if(isBlank(destination)){
                throw new FaradayException("Empty destination of mapping "+mappingPropertise);
            }
            if(!destination.matches(".+://+.")){
                destination = "http://"+destination;
            }
            destination = removeEnd(destination,"/");
            conrrectedHost.add(destination);
        });
        mappingPropertise.setDestinations(conrrectedHost);
    }

    /**
     * 校验timeOutPropertise
     * @param mappingPropertise
     */
    public void validatortimeOutPropertise(MappingPropertise mappingPropertise){
        int connecttime = mappingPropertise.getTimeOutPropertise().getConnect();
        if(connecttime < 0){
            throw new FaradayException("Invalid connecttime connnect value " + connecttime);
        }
        int readtime = mappingPropertise.getTimeOutPropertise().getRead();
        if(readtime < 0){
            throw new FaradayException("Invalid readtime connnect value " + readtime);
        }

    }
}
