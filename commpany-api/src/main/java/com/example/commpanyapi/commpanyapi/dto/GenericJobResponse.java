package com.example.commpanyapi.commpanyapi.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GenericJobResponse extends BaseResponse {
    private JobDto job;
}
