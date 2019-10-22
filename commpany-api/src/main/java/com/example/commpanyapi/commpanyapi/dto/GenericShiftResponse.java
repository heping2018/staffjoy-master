package com.example.commpanyapi.commpanyapi.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GenericShiftResponse extends  BaseResponse{
    private ShiftDto shiftDto;

}
