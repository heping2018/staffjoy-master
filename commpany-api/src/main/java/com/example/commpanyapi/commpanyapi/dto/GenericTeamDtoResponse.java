package com.example.commpanyapi.commpanyapi.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericTeamDtoResponse extends BaseResponse{
    private TeamDto teamDto;
}
