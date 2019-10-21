package com.example.commpanyapi.commpanyapi.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericDirectoryResponse extends BaseResponse {
    private DirectoryEntryDto directoryEntryDto;
}
