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

    private TimeOutPropertise timeOutPropertise;

    public MappingPropertise copy(){
        MappingPropertise mappingPropertise = new MappingPropertise();
        mappingPropertise.setHost(this.host);
        mappingPropertise.setName(this.name);
        mappingPropertise.setDestinations(this.destinations);
        mappingPropertise.setTimeOutPropertise(this.timeOutPropertise);
        return mappingPropertise;

    }

    /**
     * 私有类型 外部无法引用
     */
    public static class TimeOutPropertise{
        //session超时时间
        private int connect = 20000;
        private int read = 20000;

        public void setConnect(int connect){
            this.connect = connect;
        }
        public int getConnect(){
            return connect;
        }
        public void setRead(int read){
            this.read = read;
        }
        public int getRead(){
            return read;
        }
        @Override
        public String toString(){
            return new StringBuilder("TimeOutPropertise : ")
                    .append("{ " +
                            "connect :")
                    .append(connect)
                    .append(", ")
                    .append("read :")
                    .append(read)
                    .append("}")
                    .toString();
        }

    }
}
