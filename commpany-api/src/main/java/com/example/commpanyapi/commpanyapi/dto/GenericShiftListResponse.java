package com.example.commpanyapi.commpanyapi.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenericShiftListResponse extends BaseResponse {
    private ShiftList shiftList;
}
