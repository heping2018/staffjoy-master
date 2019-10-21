package com.example.commpanyapi.commpanyapi.dto;

import com.example.commonlib.commonlib.api.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String message;
    @Builder.Default
    private ResultCode code = ResultCode.SUCCESS;

    public boolean isSuccess(){
        return code == ResultCode.SUCCESS;
    }
}
