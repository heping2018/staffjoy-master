package com.example.commpanyapi.commpanyapi.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommpanyListReponse extends BaseResponse {
    private CommpanyList commpanyList;
}
