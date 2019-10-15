package com.example.accountapi.demo.model;

import com.example.commonlib.commonlib.api.BaseResponse;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericAccountResponse extends BaseResponse {
    private AccountDto accountDto;
}
