package com.example.commpanyapi.commpanyapi.dto;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Data
@Getter
@Setter
public class GenericWorkerResponse extends BaseResponse {
    private WorkDto workDto;
}
