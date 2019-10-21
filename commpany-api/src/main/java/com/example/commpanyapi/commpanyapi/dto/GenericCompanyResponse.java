package com.example.commpanyapi.commpanyapi.dto;


import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericCompanyResponse extends BaseResponse{
    private CommpanyDto commpanyDto;
}
